import groovy.json.JsonSlurper

import java.text.SimpleDateFormat
import java.util.stream.Collectors

if (!checkVersion(1.0)) return

//判断是否在项目文件夹下
/*def srcExists = false
new File(".").eachFile {
    if (it.name.equals("src")) srcExists = true
}
if (!srcExists) {
    println "ERROR. Please execute this script in your project"
    return
}*/

//读取项目的基础配置
def jsonSlpuer = new JsonSlurper()
def conf = jsonSlpuer.parseText(new File("jCoder.json").text)

//工作模块目录
workModuleFolder = System.getProperty("user.dir") + "\\" + conf.workModuleFolder + "\\src\\main\\java"
println "workModuleFolder : ${workModuleFolder}"

//工作模块包名
workModulePackage = conf.workModulePackage + (!conf.workModuleSubPackage ? "" : ("." + conf.workModuleSubPackage))
println "workModulePackage : ${workModulePackage}"

//SDK模块目录
sdkModuleFolder = System.getProperty("user.dir") + "\\" + conf.sdkModuleFolder + "\\src\\main\\java"
println "sdkModuleFolder : ${sdkModuleFolder}"

//SDK模块包名
sdkModulePackage = conf.sdkModulePackage
println "sdkModulePackage : ${sdkModulePackage}"

exceptions = new ArrayList<String>();

//输入路由
println '/**'
println ' * 请输入路由名称，例如:system-user-add'
println ' * 以短横线分隔的多个小写英文单词，建议命名方式为：[端口名]-[模块名]-[功能名]-[操作]'
println ' * 端口名通常为用户群体的分类方式，例如：admin、patient、doctor、common等'
println ' * 模块和功能名根据实际业务进行定义，简单业务也可以合并为一个单词'
println ' * 操作可选谓词：query、get、save、delete、crud，也可以自定义谓词'
println ' */'
def route = System.console().readLine 'Route : '
def ok = route ==~ /^[a-z]{2,}[a-z0-9]*(-[a-z]{2,}[a-z0-9]*){1,5}$/
if (!ok) {
    println "ERROR. Route pattern does not match."
    return
}

route = route?.trim()
println "Your route is : $route"

//输入Entity
println 'Please input entity name, example : UserTab'
def entityName = System.console().readLine 'Entity : '

def packageName = sdkModulePackage + ".entity"
def path = new File(getPackagePath(sdkModuleFolder, packageName))
File file = new File(path, entityName + ".java");
if (!file.exists()) {
    println "ERROR. Entity ${entityName} is not found."
    return
}

entityName = entityName?.trim()

isMongo = entityName ==~ /.*Collection$/

println "Your Entity is : ${entityName}, created from ${isMongo ? "mongo" : "mysql"}"

//生成文件
if (route.endsWith("crud")) {
    def ops = ['query', 'get', 'save', 'delete', 'set-sort', 'set-status'];
    ops.forEach(op -> {
        def routeTmp = route.replaceAll("-crud", "-" + op)
        def qoClassFullName = generateQO(routeTmp, entityName)
        def dtoClassFullName = generateDTO(routeTmp, entityName)
        generateService(routeTmp, qoClassFullName, dtoClassFullName, entityName)
    })
} else {
    def qoClassFullName = generateQO(route, entityName)
    def dtoClassFullName = generateDTO(route, entityName)
    generateService(route, qoClassFullName, dtoClassFullName, entityName)
}
/*if (exceptions.size() > 0) {
    throw new Exception(exceptions.stream().collect(Collectors.joining(", ")));
}*/
println "success"

/**
 * 生成 qo
 * @param table
 * @param dir
 * @return
 */
def generateQO(route, entityName) {
    def qoClassName

    if (route.endsWith("-save")) {
        qoClassName = entityName.replaceAll("Entity", "EditQO").replaceAll("Collection", "EditQO");
    } else if (route.endsWith("-query")) {
        qoClassName = entityName.replaceAll("Entity", "QueryQO").replaceAll("Collection", "QueryQO");
    } else if (route.endsWith("-get")) {
        qoClassName = entityName.replaceAll("Entity", "IdQO").replaceAll("Collection", "IdQO");
    } else if (route.endsWith("-delete")) {
        qoClassName = entityName.replaceAll("Entity", "IdQO").replaceAll("Collection", "IdQO");
    } else if (route.endsWith("-set-sort")) {
        return isMongo ? "com.qrqy.dp.mongo.BaseMongoSetSortQO" : "com.qrqy.dp.mysql.BaseMysqlSetSortQO";
    } else if (route.endsWith("-set-status")) {
        return isMongo ? "com.qrqy.dp.mongo.BaseMongoSetStatusQO" : "com.qrqy.dp.mysql.BaseMysqlSetStatusQO";
    } else {
        qoClassName = camelCase(route, true) + "QO"
    }

    def packageName = workModulePackage + ".qo"

    def path = new File(getPackagePath(workModuleFolder, packageName))
    if (!path.exists()) {
        path.mkdirs();
    }
    File file = new File(path, qoClassName + ".java");
    if (file.exists())
        exceptions.add("Qo file exists. " + file.getName());
    else {
        PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))
        output.withPrintWriter { out -> generateQO(out, route, packageName, qoClassName, entityName) }
    }
    println "create vo : " + file.absolutePath
    return packageName + "." + qoClassName;
}

def generateQO(out, String route, String packageName, String qoClassName, String entityName) {
    def entityPath = new File(getPackagePath(sdkModuleFolder, sdkModulePackage + ".entity"))
    File entityFile = new File(entityPath, entityName + ".java");

    def fieldList

    out.println """package ${packageName};

import com.qrqy.dp.qo.PageableQO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;"""
    //从 Tab 复制字段
    if (route.endsWith("-query")) {
        fieldList = getFields(entityFile, false)
    } else if (route.endsWith("-get")) {
        fieldList = getFields(entityFile, true)
    } else if (route.endsWith("-save")) {
        fieldList = getFields(entityFile, false)
    } else if (route.endsWith("-delete")) {
        fieldList = getFields(entityFile, true)
    } else {
        fieldList = getFields(entityFile, false)
    }
    if (fieldList.stream().anyMatch(it -> it.contains("Local"))) {
        out.println "import java.time.*;"
    }
    if (fieldList.stream().anyMatch(it -> it.contains("BigDecimal"))) {
        out.println "import java.math.BigDecimal;"
    }

    out.println ""
    generateComment(out, route, 'QO')
    out.println "@Data"
    out.println "public class $qoClassName extends PageableQO {"
    out.println ""

    //从 Tab 复制字段
    fieldList.forEach(line -> out.println line)

    out.println "}"
}

/**
 * 生成 dto
 * @param table
 * @param dir
 * @return
 */
def generateDTO(route, entityName) {
    def dtoClassName
    if (route.endsWith("-save")) {
        return null;
    } else if (route.endsWith("-delete")) {
        return null;
    } else if (route.endsWith("-query")) {
        dtoClassName = entityName.replaceAll("Entity", "ListDTO").replaceAll("Collection", "ListDTO");
    } else if (route.endsWith("-get")) {
        dtoClassName = entityName.replaceAll("Entity", "DetailDTO").replaceAll("Collection", "DetailDTO");
    } else if (route.endsWith("-set-sort")) {
        return null;
    } else if (route.endsWith("-set-status")) {
        return null;
    } else {
        dtoClassName = camelCase(route, true) + "DTO"
    }

    def packageName = workModulePackage + ".dto"

    def path = new File(getPackagePath(workModuleFolder, packageName))
    if (!path.exists()) {
        path.mkdirs();
    }
    File file = new File(path, dtoClassName + ".java");
    if (file.exists())
        exceptions.add("DTO file exists. " + file.getName());
    else {
        PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))
        output.withPrintWriter { out -> generateDTO(out, route, packageName, dtoClassName, entityName) }
    }
    println "create dto : " + file.absolutePath
    return packageName + "." + dtoClassName;
}

def generateDTO(out, route, packageName, dtoClassName, entityName) {
    def entityPath = new File(getPackagePath(sdkModuleFolder, sdkModulePackage + ".entity"))
    File entityFile = new File(entityPath, entityName + ".java");
    def fieldList = getFields(entityFile, false)

    out.println """package ${packageName};

import com.qrqy.dp.dto.IBaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;"""

    if (fieldList.stream().anyMatch(it -> it.contains("Local"))) {
        out.println "import java.time.*;"
        out.println "import com.fasterxml.jackson.annotation.JsonFormat;"
    }
    if (fieldList.stream().anyMatch(it -> it.contains("BigDecimal"))) {
        out.println "import java.math.BigDecimal;"
    }

    out.println ""
    generateComment(out, route)
    out.println "@Data"
    out.println "public class $dtoClassName implements IBaseDTO, Serializable {"
    out.println "    private static final long serialVersionUID = ${getRandom()}L;"
    out.println ""

    //从 Tab 复制字段
    getFields(entityFile, false).forEach(l -> {
        String line = l;
        if (line.contains("LocalDateTime"))
            out.println '\t@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")'
        else if (line.contains("LocalDate"))
            out.println '\t@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")'
        else if (line.contains("LocalTime"))
            out.println '\t@JsonFormat(timezone = "GMT+8", pattern = "HH:mm")'
        out.println line
    })

    out.println "}"
}

/**
 * 生成 Service
 * @param table
 * @param dir
 * @return
 */
def generateService(String route, String qoClassFullName, String dtoClassFullName, String entityName) {

    def className = camelCase(route, true) + "Service"

    def packageName = workModulePackage + ".service"

    def path = new File(getPackagePath(workModuleFolder, packageName))
    if (!path.exists()) {
        path.mkdirs();
    }

    File file = new File(path, className + ".java");
    if (file.exists())
        exceptions.add("Service file exists. " + file.getName());
    else {
        PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))
        output.withPrintWriter { out -> generateService(out, route, packageName, className, qoClassFullName, dtoClassFullName, entityName) }
    }
    println "create service : " + file.absolutePath
    return packageName + "." + className;
}

def generateService(out, String route, String packageName, String className, String qoClassFullName, String dtoClassFullName, String entityName) {
    def qoClassName = getClassShortName(qoClassFullName);

    def dtoClassName
    if (dtoClassFullName?.trim()) {
        dtoClassName = getClassShortName(dtoClassFullName)
    }

    def managerClassFullName = sdkModulePackage + ".manager." + entityName.replaceAll("Entity", "Manager").replaceAll("Collection", "Manager")
    def managerClassName = getClassShortName(managerClassFullName)

    def entityClassFullName = sdkModulePackage + ".entity." + entityName
    def entityClassName = getClassShortName(entityClassFullName)

    if (route.endsWith("-query")) {
        out.println """package ${packageName};

import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import ${entityClassFullName};
import ${managerClassFullName};
import ${qoClassFullName};
import ${dtoClassFullName};"""
        if(isMongo){
            out.println """import com.qrqy.dp.mongo.BaseMongoQuery;
import org.springframework.data.mongodb.core.query.Query;"""
        }
        else{
            out.println """import com.qrqy.dp.mysql.BaseMysqlQuery;
import org.springframework.data.jpa.domain.Specification;"""
        }

    } else if (route.endsWith("-get")) {
        out.println """package ${packageName};

import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import ${entityClassFullName};
import ${managerClassFullName};
import ${qoClassFullName};
import ${dtoClassFullName};"""
    } else if (route.endsWith("-save")) {
        out.println """package ${packageName};

import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import ${entityClassFullName};
import ${managerClassFullName};
import ${qoClassFullName};"""
    } else if (route.endsWith("-delete")) {
        out.println """package ${packageName};

import com.qrqy.dp.result.CommonObjectResult;
import io.swagger.annotations.Api;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import ${managerClassFullName};
import ${qoClassFullName};"""
    } else if (route.endsWith("-set-sort")) {
        out.println """package ${packageName};

import io.swagger.annotations.Api;
import ${isMongo ? "com.qrqy.dp.mongo.BaseMongoSetSortQO" : "com.qrqy.dp.mysql.BaseMysqlSetSortQO"};
import com.qrqy.dp.result.CommonObjectResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import ${managerClassFullName};"""
    } else if (route.endsWith("-set-status")) {
        out.println """package ${packageName};

import io.swagger.annotations.Api;
import com.qrqy.dp.result.CommonObjectResult;
import ${isMongo ? "com.qrqy.dp.mongo.BaseMongoSetStatusQO" : "com.qrqy.dp.mysql.BaseMysqlSetStatusQO"};
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import ${managerClassFullName};"""
    } else {
        out.println """package ${packageName};

import io.swagger.annotations.Api;
import com.qrqy.dp.mysql.BaseMysqlQuery;
import com.qrqy.dp.result.CommonPagingResult;
import com.qrqy.dp.result.ICommonResult;
import com.qrqy.dp.security.IBaseUser;
import com.qrqy.dp.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import ${entityClassFullName};
import ${managerClassFullName};
import ${qoClassFullName};
import ${dtoClassFullName};"""
    }

    out.println """
/**
 * route : ${route}
 *
 * @author : QRQY
 * @date : ${getCurrentTime()}
 */

@Service
@Slf4j
@Validated
@Api(value = "这里是功能说明", tags = {"运营端", "直播", "这里放筛选标签"})
public class ${className} implements IBaseService<${qoClassName}> {
    @Autowired
    private ${managerClassName} manager;

    @Override
    public ICommonResult execute(@Valid ${qoClassName} qo, HttpServletRequest request, IBaseUser curUser, String version) {
        this.validate(qo, curUser);"""

    if (route.endsWith("-query")) {
        if(isMongo)
            out.println "        Query query = new BaseMongoQuery(qo)"
        else
            out.println "        Specification query = new BaseMysqlQuery(qo)"
        out.println """                .append("createdBy", curUser.getUserId());

        Page<${entityClassName}> page = manager.query(query, qo.getPageable(), curUser);

        List<${dtoClassName}> content = page.getContent().stream().map(t -> {
            ${dtoClassName} dto = new ${dtoClassName}();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());

        return new CommonPagingResult<>(content, page);"""
    } else if (route.endsWith("-get")) {
        out.println """
        ${entityClassName} entity = manager.get(qo.getId());
        ${dtoClassName} dto = new ${dtoClassName}();
        BeanUtils.copyProperties(entity, dto);
        return new CommonObjectResult<>(dto);"""
    } else if (route.endsWith("-save")) {
        out.println """
        ${entityClassName} entity;
        if (null == qo.getId()) {
            entity = new ${entityClassName}();
        } else {
            entity = manager.get(qo.getId());
        }
        BeanUtils.copyProperties(qo, entity, "status");
        entity.setStatus(null);
        manager.save(entity, curUser);

        return new CommonObjectResult<>("success");"""
    } else if (route.endsWith("-delete")) {
        out.println """
        throw new UnsupportedOperationException("删除方法尚未实现");
        //manager.delete(qo.getId());
        //return new CommonObjectResult<>("success");"""
    } else if (route.endsWith("-set-sort")) {
        out.println """
        manager.setSort(qo.getId(), qo.getSort());
        return new CommonObjectResult<>("success");"""
    } else if (route.endsWith("-set-status")) {
        out.println """
        throw new UnsupportedOperationException("设置状态方法尚未实现");
        //manager.setStatus(qo.getId(), qo.getStatus());
        //return new CommonObjectResult<>("success");"""
    } else {
        out.println """
        Specification query = new DynamicQuery(qo)
                .append("createdBy", curUser.getUserId());

        Page<${entityClassName}> page = manager.query(query, qo.getPageable(), curUser);

        List<${dtoClassName}> content = page.getContent().stream().map(t -> {
            ${dtoClassName} dto = new ${dtoClassName}();
            BeanUtils.copyProperties(t, dto);
            return dto;
        }).collect(Collectors.toList());

        return new CommonPagingResult<>(content, page);"""
    }
    out.println """
    }
    /**
     * 业务上的校验可以放在这里
     * @param qo
     * @param curUser
     */
    @Override
    public void validate(${qoClassName} qo, IBaseUser curUser) {
        /*if (!qo.getPassword().equals(qo.getConfirmPassword())) {
            throw new BizValidationException("confirmPassord", "两次输入的密码不一致");
        }*/
    }
}"""
}

def managerAddSaveMethod() {

}
/**
 * 生成类注释
 * @param out
 */
def generateComment(out, route) {
    out.println "/**"
    out.println " * route : ${route}"
    out.println " * @author : QRQY"
    out.println " * @date : " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
    out.println " */"
}

def generateComment(out, route, type) {
    out.println "/**"
    if (type?.trim() && "Service".equals(type))
        out.println " * route : ${route}"
    if (type?.trim() && "QO".equals(type))
        out.println " * 参数支持动态查询后缀：Is、Eq、Neq、StartWith、EndWith、Like、Gt、Gte、Lt、Lte、Null、NotNull、In、NotIn"
    out.println " * @author : QRQY"
    out.println " * @date : " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
    out.println " */"
}

/**
 * 转为驼峰命名法
 * @param name
 * @param capitalize
 * @return
 */
def camelCase(String name, boolean capitalize) {
    String[] vars = name.split("-|_");
    String s = Arrays.stream(vars)
            .filter(t -> t != null && !t.trim().equals(""))
            .map(t -> {
                String lower = t.toLowerCase();
                return lower.substring(0, 1).toUpperCase() + lower.substring(1);
            }).collect(Collectors.joining());
    if (capitalize || s.length() == 1)
        return s;
    else
        return s.substring(0, 1).toLowerCase() + s.substring(1);
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


/**
 * 从 Entity 文件获取 Field 内容，用于生成 qo 或 dto
 * @param sourceFile
 * @return
 */
def getFields(File sourceFile, boolean first) {

    def body = sourceFile.text =~ "(?ms)public\\s*?class .*\\{(.*)\\}"
    if (!body.find()) {
        println "find entity body error"
        return null;
    }
    def item = body.group(1)

    def list = new ArrayList();
    def paramMatcher = item =~ "(?ms)(/\\*\\*(.*?)\\*/)[\\s\\S]*?(private.*?;)"
    while (paramMatcher.find()) {
        def mess = paramMatcher.group(2)
        if (mess) {
            mess = mess.replaceAll("\\*", "").replaceAll("\\s", "")
            list.add("\t@ApiModelProperty(value = \"${mess}\", example = \"\")")
        }
        def field = paramMatcher.group(3)
        list.add("\t${field}\n\r")
    }
    return list.grep {
        !(it =~ /serialVersionUID/)
    }
}

def getClassShortName(classFullName) {
    String[] qoArray = classFullName.split("\\.")
    return qoArray[qoArray.length - 1]
}

def getCurrentTime() {
    new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
}

def getRandom() {
    Math.abs(new Random().nextLong())
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