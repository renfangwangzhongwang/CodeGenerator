package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildMapperXML {
    private static final Logger logger = LoggerFactory.getLogger(BuildMapperXML.class);
    private static final String BASE_COLUMN_LIST = "base_column_list";

    private static final String BASE_QUERY_CONDITION_FILED = "base_query_condition";

    private static final String BASE_QUERY_CONDITION_FILED_EXTEND = "base_query_condition_extend";

    private static final String QUERY_CONDITION = "query_condition";
    public static void execute(TableInfo tableInfo){
        File floder = new File(Constants.MAPPER_XML_PATH);
        if(!floder.exists()){
            floder.mkdirs();
        }
        String className = tableInfo.getBeanName()+Constants.SUFFIX_MAPPER;
        File mapperXMLFile = new File(floder,className+".xml");
        //创建输出流
        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(mapperXMLFile);
            outw = new OutputStreamWriter(out,"utf-8");
            bw = new BufferedWriter(outw);

            //————————————————————————————————————————————————————————————————————————————————————
            //--开头
            //配置
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bw.newLine();
            bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"");
            bw.newLine();
            bw.write("\t\t\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
            bw.newLine();
            //打包
            bw.write("<mapper namespace=\""+ Constants.MAPPER_XML_PACKAGE+"."+className+"\">");
            bw.newLine();
            //开始映射
            bw.write("\t<!--实体映射-->");
            bw.newLine();
            String poClass = Constants.PO_PACKAGE + "." + tableInfo.getBeanName();
            bw.write("\t<resultMap id=\"base_result_map\" type=\"" + poClass + "\">");
            bw.newLine();

            //数据准备
            FieldInfo keyfield = null;
            if(tableInfo.getKeyIndexMap().get("PRIMARY").size()==1&&tableInfo.getKeyIndexMap().get("PRIMARY")!=null){
                keyfield = tableInfo.getKeyIndexMap().get("PRIMARY").get(0);
                bw.write("\t\t<!--"+keyfield.getComment()+"-->");
                bw.newLine();
                bw.write("\t\t<id column=\""+keyfield.getFieldName()+"\" property=\""+keyfield.getPropertyName()+"\"/>");
                bw.newLine();
            };
            //映射内容
            for (FieldInfo fieldInfo:tableInfo.getFieldList()){
                if(fieldInfo!=keyfield){
                    bw.write("\t\t<!--"+fieldInfo.getComment()+"-->");
                    bw.newLine();
                    bw.write("\t\t<result column=\""+fieldInfo.getFieldName()+"\" property=\""+fieldInfo.getPropertyName()+"\"/>");
                    bw.newLine();
                }

            }
            //结束映射
            bw.write("\t</resultMap>");
            bw.newLine();
            bw.newLine();


            //通用查询列
            bw.write("\t<!--通用查询结果列-->");
            bw.newLine();
            bw.write("\t<sql id=\""+BASE_COLUMN_LIST+"\">");
            bw.newLine();
            StringBuilder columnBuilder = new StringBuilder();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()){
                columnBuilder.append(fieldInfo.getFieldName()).append(",");
            }
            String columnBuilderStr = columnBuilder.substring(0,columnBuilder.lastIndexOf(","));
            bw.write("\t\t"+columnBuilderStr);
            bw.newLine();
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //基础查询条件
            bw.write("\t<!--基础查询条件-->");
            bw.newLine();
            bw.write("\t<sql id=\""+ BASE_QUERY_CONDITION_FILED +"\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldList()){
                String stringQuery = "";
                if(ArrayUtils.contains(Constants.SQL_STRING_TYPE,fieldInfo.getSqlType())){
                    stringQuery = " and query."+fieldInfo.getPropertyName()+"!=''";
                }
                bw.write("\t\t<if test=\"query."+fieldInfo.getPropertyName()+" != null"+stringQuery+"\">");
                bw.newLine();
                bw.write("\t\t\tand "+fieldInfo.getFieldName()+" = #{query."+fieldInfo.getPropertyName()+"}");
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //扩展查询条件
            bw.write("\t<!--扩展查询条件-->");
            bw.newLine();
            bw.write("\t<sql id=\""+ BASE_QUERY_CONDITION_FILED_EXTEND +"\">");
            bw.newLine();
            for (FieldInfo fieldInfo : tableInfo.getFieldExtendList()){
                String andWhere = "";
                if(ArrayUtils.contains(Constants.SQL_STRING_TYPE,fieldInfo.getSqlType())){
                    andWhere = " and "+fieldInfo.getFieldName()+" like concat('%', #{query."+fieldInfo.getPropertyName()+"}, '%')";
                }else if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,fieldInfo.getSqlType())||ArrayUtils.contains(Constants.SQL_DATE_TYPES,fieldInfo.getSqlType())){
                    if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_START)) {
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " >= str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d') ]]>";
                    } else if (fieldInfo.getPropertyName().endsWith(Constants.SUFFIX_BEAN_QUERY_TIME_END)) {
                        andWhere = "<![CDATA[ and " + fieldInfo.getFieldName() + " < date_sub(str_to_date(#{query." + fieldInfo.getPropertyName() + "}, '%Y-%m-%d'),"+"interval -1 day) ]]>";
                    }
                }
                bw.write("\t\t<if test=\"query."+fieldInfo.getPropertyName()+" != null and query."+fieldInfo.getPropertyName()+"!=''\">");
                bw.newLine();
                bw.write("\t\t\t"+andWhere);
                bw.newLine();
                bw.write("\t\t</if>");
                bw.newLine();
            }
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //通用查询条件
            bw.write("\t<!--通用查询条件-->");
            bw.newLine();
            bw.write("\t<sql id=\""+ QUERY_CONDITION +"\">");
            bw.newLine();
            bw.write("\t\t<where>");
            bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION_FILED+"\"/>");
            bw.newLine();
            bw.write("\t\t\t<include refid=\"" + BASE_QUERY_CONDITION_FILED_EXTEND+"\"/>");
            bw.newLine();
            bw.write("\t\t</where>");
            bw.newLine();
            bw.write("\t</sql>");
            bw.newLine();
            bw.newLine();

            //查询列表
            bw.write("\t<!--查询列表-->");
            bw.newLine();
            bw.write("\t<select id=\"selectList\" resultMap=\"base_result_map\">");
            bw.newLine();
            bw.write("\t\tSELECT <include refid=\"" + BASE_COLUMN_LIST + "\"/> FROM " + tableInfo.getTableName() + " <include refid=\"" + QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t\t<if test=\"query.orderBy != null\">ORDER BY ${query.orderBy}</if>");
            bw.newLine();
            bw.write("\t\t<if test=\"query.simplePage != null\">LIMIT #{query.simplePage.start}, #{query.simplePage.end}</if>");
            bw.newLine();
            bw.write("\t</select>");
            bw.newLine();
            bw.newLine();

            //查询列表
            bw.write("\t<!--查询数量-->");
            bw.newLine();
            bw.write("\t<select id=\"selectCount\" resultType=\"java.lang.Integer\">");
            bw.newLine();
            bw.write("\t\tSELECT count(1) FROM "+tableInfo.getTableName()+" <include refid=\"" + QUERY_CONDITION + "\"/>");
            bw.newLine();
            bw.write("\t</select>");
            bw.newLine();
            bw.newLine();

            // 单条插入
            bw.newLine();
            bw.write("\t<!--插入（匹配有值的字段）-->");
            bw.newLine();
            bw.write("\t<insert id=\"insert\" parameterType=\"" + Constants.PO_PACKAGE + "." + tableInfo.getBeanName() + "\">");
            bw.newLine();

            FieldInfo autoIncrementField = null;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (fieldInfo.getAutoIncrement() != null && fieldInfo.getAutoIncrement()) {
                    autoIncrementField = fieldInfo;
                    break;
                }
            }
            if (autoIncrementField != null) {
                bw.write("\t\t<selectKey keyProperty=\"bean." + autoIncrementField.getPropertyName() + "\" resultType=\"" + autoIncrementField.getJavaType() + "\" order=\"AFTER\">");
                bw.newLine();
                bw.write("\t\t\tSELECT LAST_INSERT_ID()");
                bw.newLine();
                bw.write("\t\t</selectKey>");
                bw.newLine();
            }

            bw.write("\t\tINSERT INTO "+tableInfo.getTableName()+"");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.newLine();
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
                bw.newLine();
                bw.write("\t\t\t</if>");

            }
            bw.newLine();
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.newLine();
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!=null\">");
                bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
                bw.newLine();
                bw.write("\t\t\t</if>");
            }
            bw.newLine();
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\t</insert>");
            bw.newLine();
            bw.newLine();

            //插入或更新
            bw.newLine();
            bw.write("\t<!--插入或更新（匹配有值的字段）-->");
            bw.newLine();
            bw.write("\t<insert id=\"insertOrUpdate\" parameterType=\"" + poClass + "\">");
            bw.newLine();

            bw.write("\t\tINSERT INTO "+tableInfo.getTableName()+"");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.newLine();
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + " != null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() + ",");
                bw.newLine();
                bw.write("\t\t\t</if>");

            }
            bw.newLine();
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                bw.newLine();
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!=null\">");
                bw.newLine();
                bw.write("\t\t\t\t#{bean." + fieldInfo.getPropertyName() + "},");
                bw.newLine();
                bw.write("\t\t\t</if>");
            }
            bw.newLine();
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\ton DUPLICATE key update");
            bw.newLine();
            Map<String, String> keyTempMap = new HashMap();
            for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()){
                List<FieldInfo> fieldInfoList = entry.getValue();
                for (FieldInfo item : fieldInfoList) {
                    keyTempMap.put(item.getFieldName(), item.getFieldName());
                }
            }

            bw.write("\t\t<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");
            bw.newLine();
            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if(keyTempMap.get(fieldInfo.getFieldName())!= null){
                    continue;
                }
                bw.newLine();
                bw.write("\t\t\t<if test=\"bean." + fieldInfo.getPropertyName() + "!=null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() +" = VALUES("+fieldInfo.getFieldName()+ "),");
                bw.newLine();
                bw.write("\t\t\t</if>");
            }
            bw.newLine();
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\t</insert>");
            bw.newLine();
            bw.newLine();

            //数据准备
            List<FieldInfo> fieldPrimaryInfo = tableInfo.getKeyIndexMap().get("PRIMARY");
            String keyContent = "";
            if(fieldPrimaryInfo.size()==1){
                keyContent= "\" useGeneratedKeys=\"true\" keyProperty=\""+fieldPrimaryInfo.get(0).getFieldName();
            }
            StringBuffer insertFieldBuffer = new StringBuffer();
            StringBuffer insertPropertyBuffer = new StringBuffer();
            for(FieldInfo fieldInfo : tableInfo.getFieldList()){
                if(fieldInfo.getAutoIncrement()){
                    continue;
                }
                insertFieldBuffer.append(fieldInfo.getFieldName()).append(",");
                insertPropertyBuffer.append("#{item."+fieldInfo.getPropertyName()+"}").append(",");
            }
            String insertFieldBufferStr = insertFieldBuffer.substring(0,insertFieldBuffer.lastIndexOf(","));
            String insertPropertyBufferStr = insertPropertyBuffer.substring(0,insertPropertyBuffer.lastIndexOf(","));

            //批量插入
            bw.write("\t<!-- 添加(批量插入) -->");
            bw.newLine();
            bw.write("\t\t<insert id=\"insertBatch\" parameterType=\""+poClass + keyContent+"\">");
            bw.newLine();
            bw.write("\t\t\tINSERT INTO "+tableInfo.getTableName()+"("+insertFieldBufferStr+")values");
            bw.newLine();
            bw.write("\t\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");
            bw.newLine();
            bw.write("\t\t\t\t("+insertPropertyBufferStr+")");
            bw.newLine();
            bw.write("\t\t\t</foreach>");
            bw.newLine();
            bw.write("\t\t</insert>");
            bw.newLine();
            bw.newLine();

            //批量插入或更新
            bw.write("\t<!-- 添加(批量插入或更新) -->");
            bw.newLine();
            bw.write("\t\t<insert id=\"insertOrUpdateBatch\" parameterType=\""+poClass + "\">");
            bw.newLine();
            bw.write("\t\t\tINSERT INTO "+tableInfo.getTableName()+"("+insertFieldBufferStr+")values");
            bw.newLine();
            bw.write("\t\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">");
            bw.newLine();
            bw.write("\t\t\t\t("+insertPropertyBufferStr+")");
            bw.newLine();
            bw.write("\t\t\t</foreach>");
            bw.newLine();
            bw.write("\t\ton DUPLICATE key update");
            bw.newLine();
            bw.write("\t\t<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");
            bw.newLine();
            for(FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if(keyTempMap.get(fieldInfo.getFieldName())!= null){
                    continue;
                }
                bw.newLine();
                //bw.write("\t\t\t<if test=\"item." + fieldInfo.getPropertyName() + "!=null\">");
                bw.newLine();
                bw.write("\t\t\t\t" + fieldInfo.getFieldName() +" = VALUES("+fieldInfo.getFieldName()+ "),");
                bw.newLine();
                //bw.write("\t\t\t</if>");
            }
            bw.newLine();
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t\t</insert>");
            bw.newLine();
            bw.newLine();


            //根据索引更新
            for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();
                Integer index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuilder paramsNames = new StringBuilder();
                for (FieldInfo fieldInfo:keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    paramsNames.append(fieldInfo.getFieldName()+"=#{"+fieldInfo.getPropertyName()+"}");
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                        paramsNames.append(" and ");
                    }
                }
                bw.write("\t<!-- 根据"+methodName+"查询 -->");
                bw.newLine();
                bw.write("\t<select id=\"selectBy"+methodName+"\" resultMap=\"base_result_map\">");
                bw.newLine();
                bw.write("\t\tselect <include refid=\""+ BASE_COLUMN_LIST +"\"/> from " +tableInfo.getTableName() +" where "+ paramsNames);
                bw.newLine();
                bw.write("\t</select>");
                bw.newLine();
                bw.newLine();

                bw.write("\t<!-- 根据"+methodName+"更新 -->");
                bw.newLine();
                bw.write("\t<update id=\"updateBy" + methodName + "\" parameterType=\""+poClass+"\">");
                bw.newLine();
                bw.write("\t\tupdate "+ tableInfo.getTableName());
                bw.newLine();
                bw.write("\t\t<set>");
                bw.newLine();
                for(FieldInfo fieldInfo : tableInfo.getFieldList()){
                    bw.write("\t\t\t<if test=\"bean."+fieldInfo.getPropertyName()+" != null\">");
                    bw.newLine();
                    bw.write("\t\t\t\t"+fieldInfo.getFieldName()+" = #{bean." + fieldInfo.getPropertyName()+"},");
                    bw.newLine();
                    bw.write("\t\t\t</if>");
                    bw.newLine();

                }
                bw.write("\t\t</set>");
                bw.newLine();
                bw.write("\t\twhere " + paramsNames);
                bw.newLine();
                bw.write("\t</update>");
                bw.newLine();
                bw.newLine();

                bw.write("\t<!-- 根据"+methodName+"删除 -->");
                bw.newLine();
                bw.write("\t<delete id=\"deleteBy"+methodName+"\">");
                bw.newLine();
                bw.write("\t\tdelete from "+ tableInfo.getTableName() +" where " + paramsNames);
                bw.newLine();
                bw.write("\t</delete>");
                bw.newLine();
                bw.newLine();
            }

            //--结尾
            bw.write("</mapper>");
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
