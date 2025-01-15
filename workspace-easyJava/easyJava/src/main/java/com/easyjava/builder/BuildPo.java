package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.DateUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;


public class BuildPo {
    private static final Logger logger = LoggerFactory.getLogger(BuildPo.class);
    public static void execute(TableInfo tableInfo){
        File floder = new File(Constants.PO_PATH);
        if(!floder.exists()){
            floder.mkdirs();
        }
        File poFile = new File(floder,tableInfo.getBeanName()+".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out,"utf-8");
            bw = new BufferedWriter(outw);

            //————————————————————————————————————————————————————————————————————————————————————
            //--打包
            bw.write("package "+ Constants.PO_PACKAGE+";");
            bw.newLine();
            bw.newLine();
            //--导包
            //序列化包
            bw.write("import java.io.Serializable;");
            bw.newLine();
            bw.newLine();
            //时间包
            if(tableInfo.getHaveDate()||tableInfo.getHaveDateTime()){
                bw.write("import java.util.Date;");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS+";");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_UNFORMAT_CLASS+";");
                bw.newLine();
                bw.write("import " + Constants.ENUMS_PACKAGE + ".DateTimePatternEnum;");
                bw.newLine();
                bw.write("import " + Constants.UTILS_PACKAGE + ".DateUtils;");
                bw.newLine();
            }
            //数学包
            if(tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }
            //忽略属性导包
            Boolean haveIgnoreBean = false;
            for (FieldInfo field : tableInfo.getFieldList()) {
                if (ArrayUtils.contains(Constants.IGNORE_BEAN_TO_JSON_FILED.split( ","), field.getPropertyName())) {
                    haveIgnoreBean = true;
                    break;
                }
            }
            if (haveIgnoreBean) {
                bw.write(Constants.IGNORE_BEAN_TO_JSON_CLASS+";");
                bw.newLine();
            }

            //--生成类
            //生成类注释
            BuildComment.createClassComment(bw,tableInfo.getComment());
            //生成类声明
            bw.write("public class "+tableInfo.getBeanName()+" implements Serializable{");
            bw.newLine();

            //--生成属性
            for(FieldInfo fieldInfo:tableInfo.getFieldList()){
                //生成属性注释
                BuildComment.createFieldOrMethodComment(bw,fieldInfo.getComment());
                //生成属性时间注解
                if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,fieldInfo.getSqlType())){
                    bw.write("\t"+String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.YYYY_MM_DD_HH_MM_SS));
                    bw.newLine();
                    bw.write("\t"+String.format(Constants.BEAN_DATE_UNFORMAT_EXPRESSION, DateUtils.YYYY_MM_DD_HH_MM_SS));
                    bw.newLine();
                }

                if(ArrayUtils.contains(Constants.SQL_DATE_TYPES,fieldInfo.getSqlType())){
                    bw.write("\t"+String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtils.YYYY_MM_DD));
                    bw.newLine();
                    bw.write("\t"+String.format(Constants.BEAN_DATE_UNFORMAT_EXPRESSION, DateUtils.YYYY_MM_DD));
                    bw.newLine();
                }
                //生成属性忽略注解
                if (ArrayUtils.contains(Constants.IGNORE_BEAN_TO_JSON_FILED.split(","),fieldInfo.getPropertyName())){
                    bw.write("\t"+Constants.IGNORE_BEAN_TO_JSON_EXPRESSION);
                    bw.newLine();
                }
                //生成属性声明
                bw.write("\tprivate "+fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName()+";");
                bw.newLine();
                bw.newLine();
            }
            //生成属性get set方法
            for (FieldInfo field : tableInfo.getFieldList()) {
                String tempField = StringUtils.uperCaseFirstLetter(field.getPropertyName());
                bw.write("\tpublic void set" + tempField + "(" + field.getJavaType() + " " + field.getPropertyName() + ") {");
                bw.newLine();
                bw.write("\t\tthis." + field.getPropertyName() + " = " + field.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                bw.write("\tpublic " + field.getJavaType() + " get" + tempField + "() {");
                bw.newLine();
                bw.write("\t\treturn this." + field.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();
            }

            //--重写toString方法，通过打印对象来查看所有数据
            StringBuffer toString_ = new StringBuffer();
            Integer index = 0;
            for (FieldInfo field : tableInfo.getFieldList()) {
                index++;

                String properName = field.getPropertyName();
                if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,field.getSqlType())){

                    properName = "DateUtils.format("+properName+",DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())";
                }else if(ArrayUtils.contains(Constants.SQL_DATE_TYPES,field.getSqlType())){
                    properName = "DateUtils.format("+properName+",DateTimePatternEnum.YYYY_MM_DD.getPattern())";
                }

                toString_.append(field.getComment() + ":\" + (" + field.getPropertyName() + " == null ? \"空\" : " + properName + ")");
                if (index < tableInfo.getFieldList().size()) {
                    toString_.append(" + ").append("\",");
                }
            }
            String toStringStr = toString_.toString();
            toStringStr = "\"" + toStringStr;
            bw.write("\t@Override");
            bw.newLine();
            bw.write("\tpublic String toString() {");
            bw.newLine();
            bw.write("\t\treturn " + toStringStr + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();

            //--结尾
            bw.write("}");
            bw.flush();

            //————————————————————————————————————————————————————————————————————————————————————
        }catch (Exception e){

        }finally {
            if(bw!=null){
                try{
                    bw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(outw!=null){
                try{
                    outw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(out!=null){
                try{
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
