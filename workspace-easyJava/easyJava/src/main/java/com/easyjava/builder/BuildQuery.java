package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.FieldsExtendUtils;
import com.easyjava.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuildQuery {
    private static final Logger logger = LoggerFactory.getLogger(BuildQuery.class);
    public static void execute(TableInfo tableInfo){
        File floder = new File(Constants.QUERY_PATH);
        if(!floder.exists()){
            floder.mkdirs();
        }
        String className = tableInfo.getBeanName()+Constants.SUFFIX_BEAN_QUERY;
        File QueryFile = new File(floder,className+".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(QueryFile);
            outw = new OutputStreamWriter(out,"utf-8");
            bw = new BufferedWriter(outw);

            //————————————————————————————————————————————————————————————————————————————————————
            //--打包
            bw.write("package "+ Constants.QUERY_PACKAGE+";");
            bw.newLine();
            bw.newLine();

            //--导包
            //日期包
            if(tableInfo.getHaveDate()||tableInfo.getHaveDateTime()){
                bw.write("import java.util.Date;");
                bw.newLine();
            }
            //数学包
            if(tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }
            //--类注释生成
            BuildComment.createClassComment(bw,tableInfo.getComment()+"查询对象");

            //--类生成
            bw.write("public class "+className+" extends BaseQuery{");
            bw.newLine();

            List<FieldInfo> totalFieldList = Stream.concat(tableInfo.getFieldList().stream(), tableInfo.getFieldExtendList().stream()).collect(Collectors.toList());
            //生成属性
            for(FieldInfo fieldInfo:totalFieldList){
                BuildComment.createFieldOrMethodComment(bw,fieldInfo.getComment());

                bw.write("\tprivate "+fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName()+";");
                bw.newLine();
                bw.newLine();
            }

            //get set方法
            for (FieldInfo field : totalFieldList) {
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
            //结尾
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
