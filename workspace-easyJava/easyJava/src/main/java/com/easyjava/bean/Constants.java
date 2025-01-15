package com.easyjava.bean;

import com.easyjava.utils.PropertiesUtils;

public class Constants {
    public static String AUTHOR_COMMENT;
    public static Boolean IGNORE_TABLE_PREFIX;
    public static String SUFFIX_BEAN_QUERY;
    public static String SUFFIX_BEAN_QUERY_FUZZY;
    public static String SUFFIX_BEAN_QUERY_TIME_START;
    public static String SUFFIX_BEAN_QUERY_TIME_END;

    public static String SUFFIX_MAPPER;

    //基础文件路径
    public static String JAVA_BASE_PATH;
    //基础资源路径
    public static String RESOURCES_BASE_PATH;
    //基础包路径
    public static String PACKAGE_BASE;
    //po路径和包
    public static String PO_PATH;
    public static String PO_PACKAGE;

    public static String VO_PATH;
    public static String VO_PACKAGE;


    public static String QUERY_PATH;
    public static String QUERY_PACKAGE;

    public static String UTILS_PATH;
    public static String UTILS_PACKAGE;

    public static String ENUMS_PATH;
    public static String ENUMS_PACKAGE;

    public static String EXCEPTION_PATH;

    public static String EXCEPTION_PACKAGE;

    public static String MAPPER_PATH;
    public static String MAPPER_PACKAGE;

    public static String MAPPER_XML_PATH;
    public static String MAPPER_XML_PACKAGE;

    public static String SERVICE_PATH;
    public static String SERVICE_PACKAGE;

    public static String CONTROLLER_PATH;
    public static String CONTROLLER_PACKAGE;


    public static String SERVICE_IMPL_PATH;
    public static String SERVICE_IMPL_PACKAGE;




    //需要忽略的属性
    public static String IGNORE_BEAN_TO_JSON_FILED;
    public static String IGNORE_BEAN_TO_JSON_EXPRESSION;
    public static String IGNORE_BEAN_TO_JSON_CLASS;
    //日期序列化，反序列化
    public static String BEAN_DATE_FORMAT_EXPRESSION;
    public static String BEAN_DATE_FORMAT_CLASS;
    public static String BEAN_DATE_UNFORMAT_EXPRESSION;
    public static String BEAN_DATE_UNFORMAT_CLASS;

    private static String getCompleteJavaPath(String key){
        return (JAVA_BASE_PATH +"/"+PACKAGE_BASE+"/"+PropertiesUtils.getString(key)).replace(".","/");
    }

    private static String getCompleteResourcesPath(String key){
        return (RESOURCES_BASE_PATH +"/"+PACKAGE_BASE+"/"+PropertiesUtils.getString(key)).replace(".","/");
    }
    private static String getCompletePackage(String key){
        return PACKAGE_BASE+'.'+PropertiesUtils.getString(key);
    }

    static {
        //作者注释
        AUTHOR_COMMENT = PropertiesUtils.getString("author.comment");
        //忽略前缀
        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtils.getString("ignore.table.prefix"));
        //扩展字段
        SUFFIX_BEAN_QUERY = PropertiesUtils.getString("suffix.bean.query");
        SUFFIX_BEAN_QUERY_FUZZY = PropertiesUtils.getString("suffix.bean.query.fuzzy");
        SUFFIX_BEAN_QUERY_TIME_START = PropertiesUtils.getString("suffix.bean.query.time.start");
        SUFFIX_BEAN_QUERY_TIME_END = PropertiesUtils.getString("suffix.bean.query.time.end");
        SUFFIX_MAPPER = PropertiesUtils.getString("suffix.mapper");
        //日期序列化，反序列化
        BEAN_DATE_FORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.format.expression");
        BEAN_DATE_FORMAT_CLASS = PropertiesUtils.getString("bean.date.format.class");
        BEAN_DATE_UNFORMAT_EXPRESSION = PropertiesUtils.getString("bean.date.unformat.expression");
        BEAN_DATE_UNFORMAT_CLASS = PropertiesUtils.getString("bean.date.unformat.class");
        //转json时忽略的字段和类
        IGNORE_BEAN_TO_JSON_FILED = PropertiesUtils.getString("ignore.bean.tojson.filed");
        IGNORE_BEAN_TO_JSON_CLASS = PropertiesUtils.getString("ignore.bean.tojson.class");
        IGNORE_BEAN_TO_JSON_EXPRESSION = PropertiesUtils.getString("ignore.bean.tojson.expression");
        //java文件路径
        JAVA_BASE_PATH = PropertiesUtils.getString("java.base.path");
        //resources文件路径
        RESOURCES_BASE_PATH = PropertiesUtils.getString("resources.base.path");
        //包基本路径
        PACKAGE_BASE = PropertiesUtils.getString("package.base.path");
        //--文件路径和包路径
        PO_PATH = getCompleteJavaPath("package.po");
        PO_PACKAGE = getCompletePackage("package.po");

        VO_PATH = getCompleteJavaPath("package.vo");
        VO_PACKAGE = getCompletePackage("package.vo");

        QUERY_PATH = getCompleteJavaPath("package.query");
        QUERY_PACKAGE = getCompletePackage("package.query");

        UTILS_PATH = getCompleteJavaPath("package.utils");
        UTILS_PACKAGE = getCompletePackage("package.utils");

        ENUMS_PATH = getCompleteJavaPath("package.enums");
        ENUMS_PACKAGE = getCompletePackage("package.enums");

        EXCEPTION_PATH = getCompleteJavaPath("package.exception");
        EXCEPTION_PACKAGE = getCompletePackage("package.exception");

        MAPPER_PATH = getCompleteJavaPath("package.mapper");
        MAPPER_PACKAGE = getCompletePackage("package.mapper");

        SERVICE_IMPL_PATH = getCompleteJavaPath("package.service.impl");
        SERVICE_IMPL_PACKAGE = getCompletePackage("package.service.impl");

        SERVICE_PATH = getCompleteJavaPath("package.service");
        SERVICE_PACKAGE = getCompletePackage("package.service");

        CONTROLLER_PATH = getCompleteJavaPath("package.controller");
        CONTROLLER_PACKAGE = getCompletePackage("package.controller");

        MAPPER_XML_PATH = getCompleteResourcesPath("package.mapper");
        MAPPER_XML_PACKAGE = getCompletePackage("package.mapper");




    }

    public final static String[] SQL_DATE_TIME_TYPES = new String[]{"datetime", "timestamp"};

    public final static String[] SQL_DATE_TYPES = new String[]{"date"};

    public static final String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};

    public static final String[] SQL_STRING_TYPE = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};

    // Integer
    public static final String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint"};

    // Long
    public static final String[] SQL_LONG_TYPE = new String[]{"bigint"};

    public static void main(String[] args) {
        System.out.println(MAPPER_XML_PACKAGE);
    }
}
