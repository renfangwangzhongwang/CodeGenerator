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
import java.util.List;
import java.util.Map;

public class BuildService {
    private static final Logger logger = LoggerFactory.getLogger(BuildService.class);
    public static void execute(TableInfo tableInfo){
        File floder = new File(Constants.SERVICE_PATH);
        if(!floder.exists()){
            floder.mkdirs();
        }
        String interfaceName = tableInfo.getBeanName()+"Service";
        File serviceFile = new File(floder,interfaceName+".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(serviceFile);
            outw = new OutputStreamWriter(out,"utf-8");
            bw = new BufferedWriter(outw);

            //————————————————————————————————————————————————————————————————————————————————————
            //--打包
            bw.write("package "+ Constants.SERVICE_PACKAGE+";");
            bw.newLine();
            bw.newLine();
            //--导包
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
            //po包
            bw.write("import "+ Constants.VO_PACKAGE + ".PaginationResultVO;");
            bw.newLine();
            //po包
            bw.write("import "+ Constants.PO_PACKAGE + "." + tableInfo.getBeanName() +";");
            bw.newLine();
            //query包
            bw.write("import "+ Constants.QUERY_PACKAGE + "." + tableInfo.getBeanParamName() +";");
            bw.newLine();
            bw.newLine();

            bw.write("import java.util.List;");
            bw.newLine();

            //--生成类
            //生成类注释
            BuildComment.createClassComment(bw,tableInfo.getComment()+"Service");
            //生成类声明
            bw.write("public interface "+interfaceName+" {");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,"根据条件查询列表");
            bw.write("\tList<"+tableInfo.getBeanName()+"> findListByParam("+tableInfo.getBeanParamName()+" query);");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,"根据条件查询数量");
            bw.write("\tInteger findCountByParam("+ tableInfo.getBeanParamName() + " query);");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,"分页查询");
            bw.write("\tPaginationResultVO<"+tableInfo.getBeanName()+"> findListByPage("+tableInfo.getBeanParamName()+" query);");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,  "新增");
            bw.write("\tInteger add(" + tableInfo.getBeanName() + " bean);");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw, "批量新增");
            bw.write("\tInteger addBatch(List<" + tableInfo.getBeanName() + "> listBean);");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,  "批量新增或修改");
            bw.write("\tInteger addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean);");
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
                    paramsNames.append(fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                        paramsNames.append(", ");
                    }
                }

                BuildComment.createFieldOrMethodComment(bw, "根据"+methodName+"查询");
                bw.write("\t"+tableInfo.getBeanName()+" get"+tableInfo.getBeanName()+"By"+methodName+"("+paramsNames+");");
                bw.newLine();
                bw.newLine();

                BuildComment.createFieldOrMethodComment(bw, "根据"+methodName+"更新");
                bw.write("\tInteger update"+tableInfo.getBeanName()+"By"+methodName+"("+tableInfo.getBeanName()+" bean, "+paramsNames+");");
                bw.newLine();
                bw.newLine();

                BuildComment.createFieldOrMethodComment(bw, "根据"+methodName+"删除");
                bw.write("\tInteger delete"+tableInfo.getBeanName()+"By"+methodName+"("+paramsNames+");");
                bw.newLine();
                bw.newLine();
            }

            //--结尾
            bw.write("}");
            bw.flush();

            //————————————————————————————————————————————————————————————————————————————————————
        }catch (Exception e){
            logger.info("创建service失败");
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
