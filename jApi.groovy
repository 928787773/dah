@Grab('io.github.http-builder-ng:http-builder-ng-core:1.0.4')
@Grab(group = 'org.apache.directory.studio', module = 'org.apache.commons.codec', version = '1.8')
@Grab('org.slf4j:slf4j-api:1.6.6')
@Grab('org.slf4j:slf4j-log4j12:1.6.6')
@Grab('log4j:log4j:1.2.16')
import groovyx.net.http.*
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import groovy.io.FileType

import java.util.stream.Collectors

if (!checkVersion(1.0)) return

//读取项目的基础配置
def jsonSlpuer = new JsonSlurper()
def conf = jsonSlpuer.parseText(new File("jCoder.json").text)

//工作模块目录
workModuleFolder = System.getProperty("user.dir") + "\\" + conf.workModuleFolder + "\\src\\main\\java"
println "workModuleFolder  : ${workModuleFolder}"

//工作模块包名
workModulePackage = conf.workModulePackage + (!conf.workModuleSubPackage ? "" : ("." + conf.workModuleSubPackage))
println "workModulePackage : ${workModulePackage}"

//yapiToken
yapiToken = conf.yapiToken
println "yapiToken : ${yapiToken}"

//服务类名
/*def serviceClassName = System.console().readLine 'serviceClassName333  : '
serviceFile = getServiceFile(serviceClassName)

def getServiceFile(_serviceClassName) {
    def packageName = workModulePackage + ".service"
    def path = new File(getPackagePath(workModuleFolder, packageName))
    File file = new File(path, _serviceClassName + ".java");
    if (!file.exists()) {
        throw new RuntimeException("Service ${_serviceClassName} is not found")
    }
    return file;
}

println "your serviceFile : ${serviceFile.getAbsolutePath()}"

genApiForService(serviceFile)*/
//获取接口列表数据
def getApiMap() {
    def requestBody = [
            token: yapiToken,
            limit: 10000
    ]
    def http = HttpBuilder.configure {
        request.uri = 'http://82.156.211.163:3000'
        request.contentType = 'application/json'
    }

    def rtn = http.get {
        request.uri.path = '/api/interface/list'
        request.uri.query = requestBody
        response.parser('application/json') { cc, fs ->
            new JsonSlurper().parse(fs.inputStream)
        }
    }

    if (rtn.errcode == 0) {
        def map = [:];
        rtn.data.list.each() {
            map[it.path] = it._id;
        }
        return map;
    } else {
        return [:]
    }
}

def getApiInfo(id) {
    if (!id) return null;

    def requestBody = [
            token: yapiToken,
            id   : id
    ]
    def http = HttpBuilder.configure {
        request.uri = 'http://82.156.211.163:3000'
        request.contentType = 'application/json'
    }

    def rtn = http.get {
        request.uri.path = '/api/interface/get'
        request.uri.query = requestBody
        response.parser('application/json') { cc, fs ->
            new JsonSlurper().parse(fs.inputStream)
        }
    }

    if (rtn.errcode == 0) {
        return rtn.data;
    } else {
        return null;
    }
}

//路由与接口id的映射
apiMap = getApiMap();

//def apiinfo = getApiInfo(161);
//println apiinfo;

//批量处理工作目录下的所有service
batchHandler();

def batchHandler() {
    def packageName = workModulePackage + ".service"
    def path = new File(getPackagePath(workModuleFolder, packageName))

    def list = []
    path.eachFileRecurse(FileType.FILES) { file ->
        list << file
    }
    list.each {
        //if (it.getAbsolutePath().endsWith("CommonLoginService.java")) {
        println "The serviceFile : ${it.getAbsolutePath()}"
        genApiForService(it)
        println "--------------------------------"
        println ""
        //}
    }
}

def genApiForService(File serviceFile) {
    def text = serviceFile.text;

    //接口路由、说明
    def matcherRoute = text =~ "(?ms)/\\*\\*(.*)\\*\\s*route\\s*:\\s*(\\S*)"
    def title = null;
    def route = null;
    if (matcherRoute.find()) {
        title = matcherRoute.group(1)
        route = matcherRoute.group(2)
    }
    title = title?.trim() ?: route
    title = title.replaceAll("\\s", "").replaceAll("\\*", "")

    //swagger中的说明
    def matcherTitle = text =~ "@Api\\(.*value\\s*=\\s*\"(\\S*)\".*\\)"
    if (matcherTitle.find()) {
        def titleTmp = matcherTitle.group(1)
        if (titleTmp?.trim())
            title = titleTmp;
    }

    //swagger中的标签
    def tagArray;
    def matcherTags = text =~ "@Api\\(.*tags\\s*=\\s*\\{(.*?)\\}.*\\)"
    if (matcherTags.find()) {
        String[] tags = matcherTags.group(1).split(",")
        for (int i = 0; i < tags.length; i++) {
            tags[i] = tags[i].replaceAll("\"", "").trim()
        }
        tagArray = tags;
    }
    tagArray = tagArray ?: new String[0];

    //qo全类名
    //def matcherQo = text =~ "import\\s*(${workModulePackage}.qo.\\S*QO);"
    def matcherQo = text =~ "import\\s*(\\S*QO);"
    def qoClassFullName = null;
    while (matcherQo.find()) {
        qoClassFullName = matcherQo.group(1)
    }

    //dto全类名
    def matcherDto = text =~ "import\\s*(${workModulePackage}.dto.\\S*DTO);"
    def dtoClassFullName = null;
    while (matcherDto.find()) {
        dtoClassFullName = matcherDto.group(1)
    }
    println "title : ${title}"
    println "route : ${route}"
    println "qo    : ${qoClassFullName}"
    println "dto   : ${dtoClassFullName}"
    println "tag   : ${tagArray}"

    def yapiId = apiMap["/v1/" + route]
    def requestBody = getApiInfo(yapiId);
    def exists = requestBody != null;

    if (requestBody) {
//        println "qoClass:${requestBody["req_body_other"].class}"
//        println "qo:${requestBody["req_body_other"]}"
        requestBody["id"] = yapiId;
        requestBody["title"] = title;
        requestBody["token"] = yapiToken;
        requestBody["req_body_other"] = getQoParams(qoClassFullName, requestBody["req_body_other"]);
        requestBody["res_body_is_json_schema"] = true;
        requestBody["res_body"] = getDtoParams(dtoClassFullName);
    } else {
        requestBody = [
                title                  : title,
                method                 : "POST",
                path                   : "/v1/" + route,
                catid                  : 41,
                token                  : yapiToken,
                tag                    : tagArray,
                req_body_type          : "json",
                req_body_other         : getQoParams(qoClassFullName, null),
                res_body_is_json_schema: true,
                res_body               : getDtoParams(dtoClassFullName)
        ]
    }
    //yapi创建接口
    def http = HttpBuilder.configure {
        request.uri = 'http://82.156.211.163:3000'
        request.contentType = 'application/json'
    }
    def url = exists ? '/api/interface/up' : '/api/interface/save';
    println "url: ${url}"
    def rtn = http.post() {
        request.uri.path = url
        request.body = requestBody
        response.parser('application/json') { cc, fs ->
            new JsonSlurper().parse(fs.inputStream)
        }
    }

    println rtn
}

//组织qo参数
def getQoParams(qoClassFullName, original) {
    if (!qoClassFullName?.trim()) {
        //println "start getQoParams, qoClassFullName is null"
        return "";
    }

    if (qoClassFullName.endsWith("BaseMongoSetStatusQO") || qoClassFullName.endsWith("BaseMysqlSetStatusQO")) {
        if (original?.trim()) {
            return original
        } else {
            return """{
/* entityId */
"id":"",
/* 状态 */
"status":""
}""";
        }
    } else if (qoClassFullName.endsWith("BaseMongoSetSortQO") || qoClassFullName.endsWith("BaseMysqlSetSortQO")) {
        if (original?.trim()) {
            return original
        } else {
            return """{
/* entityId */
"id":"",
/* 排序 */
"sort":""
}""";
        }
    } else {
        def params = new ArrayList();
        File file = new File(getPackagePath(workModuleFolder, qoClassFullName) + ".java")

        def bodyMatcher = file.text =~ "(?ms)public\\s*?class .*?\\{([\\s|\\S]*)\\}"
        if (!bodyMatcher.find()) {
            println "find entity body error"
            return null;
        }
        def body = bodyMatcher.group(1)

        //println body;
        def rtn = ""
        def matcher = body =~ "(?ms)(@ApiModelProperty\\(.*?\\)).*?private\\s*?(\\S+?)\\s*?(\\S+?)\\s*?;"
        while (matcher.find()) {

            //println "matcher.group(1) : " + matcher.group();
            def anno = matcher.group(1)
            def type = matcher.group(2)
            def name = matcher.group(3)

            def matcherMessage = anno =~ "@ApiModelProperty\\(.*value\\s*=\\s*\"(\\S*)\".*\\)"
            def message = name;
            if (matcherMessage.find()) {
                message = matcherMessage.group(1)
            }

            def matcherExample = anno =~ "@ApiModelProperty\\(.*example\\s*=\\s*\"(\\S*)\".*\\)"
            def example = "";
            if (matcherExample.find()) {
                example = matcherExample.group(1)
            }

            if (rtn != "") rtn += ",\r\n"
            //查找原始文件中是否有该参数，如果有，用原始文件的值替换example
            def paramMatcher = original =~ "\"${name}\":\"(.*)\""
            if (paramMatcher.find()) {
                example = paramMatcher.group(1)
            }
            rtn += "/* ${message} */\r\n"
            rtn += "\"${name}\":\"${example}\""
        }
        return "{\r\n${rtn}\r\n}";
    }
}

//组织dto参数
def getDtoParams(dtoClassFullName) {
    if (!dtoClassFullName?.trim()) {
        //println "start getDtoParams, dtoClassFullName is null"
        return "";
    }

    File file = new File(getPackagePath(workModuleFolder, dtoClassFullName) + ".java")

    def bodyMatcher = file.text =~ "(?ms)public\\s*?class .*\\{(.*)\\}"
    if (!bodyMatcher.find()) {
        println "find entity body error"
        return null;
    }
    def body = bodyMatcher.group(1)

    def params = new HashMap();
    def matcher = body =~ "(?ms)(@.*?)private\\s*?(\\S+?)\\s*?(\\S+?)\\s*?;"
    while (matcher.find()) {
        def anno = matcher.group(1)
        def type = matcher.group(2)
        def name = matcher.group(3)

        def matcherMessage = anno =~ "@ApiModelProperty\\(.*value\\s*=\\s*\"(\\S*)\".*\\)"
        def message = name;
        if (matcherMessage.find()) {
            message = matcherMessage.group(1)
        }

        params.put(name, [
                type       : type,
                description: message
        ])
    }

    def rtn = [
            "type"      : "object",
            "properties": params
    ];
    return JsonOutput.toJson(rtn)
}


/**
 * 得到包所在的路径
 * @param workModuleFolder
 * @param packageName
 * @return
 */
def getPackagePath(String workModuleFolder, String packageName) {
    String[] vars = packageName.split("\\.")
    return workModuleFolder + File.separator + Arrays.stream(vars).collect(Collectors.joining(File.separator));
}


def checkVersion(curVersion) {
    def versionsJson = "http://groovy.al1.qrqy.net/version.json".toURL().text;
    def jsonSlpuer = new JsonSlurper()
    def versions = jsonSlpuer.parseText(versionsJson)
    if (curVersion < versions.jcoder) {
        println "当前脚本已经过期，请下载最新版";
        return false;
    }
    return true;
}