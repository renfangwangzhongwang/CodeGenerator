package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.bean.TableInfo;
import com.easyjava.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public class BuildMapper {
    private static final Logger logger = LoggerFactory.getLogger(BuildMapper.class);
    public static void execute(TableInfo tableInfo){
        File floder = new File(Constants.MAPPER_PATH);
        if(!floder.exists()){
            floder.mkdirs();
        }
        String className = tableInfo.getBeanName()+Constants.SUFFIX_MAPPER;
        File mapperFile = new File(floder,className+".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(mapperFile);
            outw = new OutputStreamWriter(out,"utf-8");
            bw = new BufferedWriter(outw);

            //————————————————————————————————————————————————————————————————————————————————————
            //--打包
            bw.write("package "+ Constants.MAPPER_PACKAGE+";");
            bw.newLine();
            bw.newLine();
            //--导包
            bw.write("import org.apache.ibatis.annotations.Param;");
            bw.newLine();
            bw.newLine();
            //时间包
            if(tableInfo.getHaveDate()||tableInfo.getHaveDateTime()){
                bw.write("import java.util.Date;");
                bw.newLine();
            }
            //数学包
            if(tableInfo.getHaveBigDecimal()){
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }
            //--生成类
            //生成类注释
            BuildComment.createClassComment(bw,tableInfo.getComment()+"Mapper");
            //生成类声明
            bw.write("public interface "+className+"<T, P> extends BaseMapper{");
            bw.newLine();

            //--生成属性
            //遍历这张表的所有索引
            for(Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()){
                List<FieldInfo> fieldInfoList = entry.getValue();
                Integer index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuilder methodParam = new StringBuilder();
                //遍历这个索引需要的所有字段
                for (FieldInfo fieldInfo : fieldInfoList){
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    if(index < fieldInfoList.size()){
                        methodName.append("And");
                    }

                    methodParam.append("@Param(\""+fieldInfo.getPropertyName()+"\") "+fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName());
                    if(index<fieldInfoList.size()){
                        methodParam.append(", ");
                    }
                }
                //查询方法
                BuildComment.createFieldOrMethodComment(bw,"根据"+methodName+"查询");
                bw.write("\tT selectBy"+methodName+"("+methodParam+");");
                bw.newLine();
                bw.newLine();
                //更新方法
                BuildComment.createFieldOrMethodComment(bw,"根据"+methodName+"更新");
                bw.write("\tInteger updateBy"+methodName+"(@Param(\"bean\") T t, "+methodParam+");");
                bw.newLine();
                bw.newLine();
                //删除方法
                BuildComment.createFieldOrMethodComment(bw,"根据"+methodName+"查询");
                bw.write("\tInteger deleteBy"+methodName+"("+methodParam+");");
                bw.newLine();
                bw.newLine();
            }


            //--结尾
            bw.write("}");
            bw.flush();

            //————————————————————————————————————————————————————————————————————————————————————
        }catch (Exception e){
            logger.info("创建mapper失败");
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
