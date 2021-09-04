import com.intellij.database.model.DasTable
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

import java.text.SimpleDateFormat
import java.util.stream.Collectors
import groovy.json.JsonSlurper

if (!checkVersion(1.0)) {
    throw new Exception("groovy script expired, please download a newer version");
    return
}

typeMapping = [
        (~/(?i)tinyint|smallint|int/): "Integer",
        (~/(?i)bigint/)              : "Long",
        (~/(?i)float|double/)        : "Double",
        (~/(?i)decimal/)             : "BigDecimal",
        (~/(?i)datetime|timestamp/)  : "LocalDateTime",
        (~/(?i)date/)                : "LocalDate",
        (~/(?i)time/)                : "LocalTime",
        (~/(?i)/)                    : "String"
]

baseDir = "D:\\workspace_java\\qcloud-dp-demo" // 项目根路径
if (!baseDir.endsWith("\\"))
    baseDir = baseDir + "\\"

//读取项目的基础配置
def jsonSlpuer = new JsonSlurper()
def conf = jsonSlpuer.parseText(new File(new File(baseDir), "jcoder.json").text)
sdkModuleFolder = baseDir + conf.sdkModuleFolder
sdkModulePackage = conf.sdkModulePackage

exceptions = new ArrayList<String>();

SELECTION.filter { it instanceof DasTable }.each {
    def dir = sdkModuleFolder + "\\src\\main\\java"
    def entityClassFullName = generateEntity(it, dir)
    def repositoryClassFullName = generateRepository(it, dir, entityClassFullName)
    def managerClassFullName = generateManager(it, dir, entityClassFullName, repositoryClassFullName)
    if (exceptions.size() > 0) {
        throw new Exception(exceptions.stream().collect(Collectors.joining(", ")));
    }

}

def log(message) {
    def logName = "d:\\groovy.log";
    FileWriter writer = new FileWriter(logName, true);
    writer.write(message);
    writer.write("\r\n")
    writer.close()
}

/**
 * 生成实体
 * @param table
 * @param dir
 * @return
 */
def generateEntity(table, dir) {
    def className = camelCase(table.getName(), true) + "Entity"

    def fields = calcFields(table)
    def packageName = sdkModulePackage + ".entity"

    def path = new File(dir + getPackagePath(packageName))
    if (!path.exists()) {
        path.mkdirs();
    }
    File file = new File(path, className + ".java");
    if (file.exists() && false)
        exceptions.add("Entity file exists. " + file.getName());
    else {
        PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))
        output.withPrintWriter { out -> generateEntity(out, table.getName(), packageName, className, fields) }
    }

    return packageName + "." + className;
}

def generateEntity(out, table, packageName, className, fields) {
    //判断是否包含通用字段
    commonFields = ["note", "sort", "version", "createdBy", "createdAt", "updatedBy", "updatedAt", "deletedFlag"];
    def commonFieldCount = 0;
    //包含的数据类型，便于引入对应的包
    Set types = new HashSet()
    fields.each() {
        if (commonFields.contains(it.name))
            commonFieldCount++;
        else
            types.add(it.type)
    }

    //start
    out.println """package ${packageName};

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;"""

    //是否继承通用字段类
    if (commonFieldCount >= 8)
        out.println "import com.qrqy.dp.entity.CommonEntity;"
    else
        out.println "import com.qrqy.dp.entity.BaseEntity;"

    //引入类型包
    if (types.contains("LocalDateTime") || types.contains("LocalDate") | types.contains("LocalTime")) {
        out.println "import java.time.*;"
    }
    if (types.contains("BigDecimal")) {
        out.println "import java.math.BigDecimal;"
    }
    if (types.contains("InputStream")) {
        out.println "import java.io.InputStream;"
    }

    out.println """
/**
 * @author : Luis
 * @date : ${getCurrentTime()}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "${table}")
@EntityListeners(AuditingEntityListener.class)
public class ${className} extends ${commonFieldCount >= 8 ? "CommonEntity" : "BaseEntity"} implements Serializable{

    private static final long serialVersionUID = ${getRandom()}L;
"""
    fields.each() {
        if (!commonFields.contains(it.name) || commonFieldCount < 8) {
            // 输出注释
            if (it.comment != null && !it.comment.toString().trim().equals("")) {
                out.println "\t/**"
                out.println "\t * ${it.comment.toString()}"
                out.println "\t */"
            }
            if (it.key) {
                out.println "    @Id";
                out.println "    @GeneratedValue(strategy = GenerationType.IDENTITY)";
            }
            if (it.annos != "") out.println "   ${it.annos}"
            out.println "    private ${it.type} ${it.name};"
        }
    }

    out.println ""
    out.println "}"
}


/**
 * 生成 Repository
 * @param table
 * @param dir
 * @return
 */
def generateRepository(table, dir, entityClassFullName) {
    def className = camelCase(table.getName(), true)
    className += "Repository"

    def packageName = sdkModulePackage + ".repository"

    def path = new File(dir + getPackagePath(packageName))
    if (!path.exists()) {
        path.mkdirs();
    }

    File file = new File(path, className + ".java");
    if (file.exists() && false)
        exceptions.add("Repository file exists. " + file.getName());
    else {
        PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))
        output.withPrintWriter { out -> generateRepository(out, packageName, className, entityClassFullName) }
    }

    return packageName + "." + className;
}

def generateRepository(out, packageName, className, entityClassFullName) {
    def entityClassName = getClassShortName(entityClassFullName);

    out.println """package ${packageName};

import ${entityClassFullName};
import com.qrqy.dp.mysql.IBaseMysqlRepository;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : ${getCurrentTime()}
 */
@Component
public interface ${className} extends IBaseMysqlRepository<${entityClassName}, Integer> {

}"""
}


/**
 * 生成 Manager
 * @param table
 * @param dir
 * @return
 */
def generateManager(table, dir, entityClassFullName, repositoryClassFullName) {
    def className = camelCase(table.getName(), true)
    className += "Manager"

    def packageName = sdkModulePackage + ".manager"

    def path = new File(dir + getPackagePath(packageName))
    if (!path.exists()) {
        path.mkdirs();
    }

    File file = new File(path, className + ".java");
    if (file.exists() && false)
        exceptions.add("Manager file exists. " + file.getName());
    else {
        PrintWriter output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"))
        output.withPrintWriter { out -> generateManager(out, packageName, className, entityClassFullName, repositoryClassFullName) }
    }

    return packageName + "." + className;
}

def generateManager(out, packageName, className, entityClassFullName, repositoryClassFullName) {
    def entityClassName = getClassShortName(entityClassFullName)
    def repositoryClassName = getClassShortName(repositoryClassFullName)

    out.println """package ${packageName};

import ${entityClassFullName};
import ${repositoryClassFullName};
import com.qrqy.dp.mysql.BaseMysqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author : Luis
 * @date : ${new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())}
 */
@Component
public class ${className} extends BaseMysqlManager<${repositoryClassName}, ${entityClassName}> {
    @Autowired
    private ${repositoryClassName} repository;

    @Override
    protected ${repositoryClassName} getRepository() {
        return repository;
    }

    //todo 可在这里增加业务方法，不限于 BaseManager 提供的基础方法

}"""
}

/**
 * 生成类注释
 * @param out
 */
def generateComment(out) {
    out.println "/**"
    out.println " * @author : Luis"
    out.println " * @date : " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
    out.println " */"
}


def calcFields(table) {
    def idFound = 0;
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        def fieldName = camelCase(col.getName(), false);
        fields += [
                name   : fieldName,
                colName: col.getName(),
                type   : typeStr,
                key    : fieldName.endsWith("Id") && idFound++ == 0,
                comment: col.getComment(),
                annos  : "\t@Column(name = \"" + col.getName() + "\")"]
    }
}

def camelCase(str, capitalize) {
    str = str.replaceAll("ibd_", "").replaceAll("sy_", "").replaceAll("dp_", "");
    def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
            .collect { Case.LOWER.apply(it).capitalize() }
            .join("")
            .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
    s = capitalize || s.length() == 1 ? s : Case.LOWER.apply(s[0]) + s[1..-1]
    return s;
}


def getClassShortName(classFullName) {
    String[] qoArray = classFullName.split("\\.")
    return qoArray[qoArray.length - 1]
    //return classFullName.replaceAll(".*\\.", "")
}

def getCurrentTime() {
    new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date())
}

def getRandom() {
    Math.abs(new Random().nextLong())
}

def getPackagePath(packageName) {
    "\\" + packageName.replaceAll("\\.", "\\\\")
}

def checkVersion(curVersion) {
    def versionsJson = "http://groovy.al1.qrqy.net/version.json".toURL().text;
    def jsonSlpuer = new JsonSlurper()
    def versions = jsonSlpuer.parseText(versionsJson)
    if (curVersion < versions.jDaoMySQL) {
        println "The script expired， please download a newer version";
        return false;
    }
    return true;
}