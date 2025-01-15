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

public class BuildController {
    private static final Logger logger = LoggerFactory.getLogger(BuildService.class);
    public static void execute(TableInfo tableInfo){
        File floder = new File(Constants.CONTROLLER_PATH);
        if(!floder.exists()){
            floder.mkdirs();
        }
        String className = tableInfo.getBeanName()+"Controller";
        File controllerFile = new File(floder,className+".java");
        String serviceName = tableInfo.getBeanName() + "Service";
        String serviceBeanName = StringUtils.lowerCaseFirstLetter(serviceName);

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(controllerFile);
            outw = new OutputStreamWriter(out,"utf-8");
            bw = new BufferedWriter(outw);

            //————————————————————————————————————————————————————————————————————————————————————
            //--打包
            bw.write("package "+ Constants.CONTROLLER_PACKAGE+";");
            bw.newLine();
            bw.newLine();
            //--导包
            bw.write("import "+ Constants.PO_PACKAGE + "." + tableInfo.getBeanName() +";");
            bw.newLine();
            bw.write("import "+ Constants.QUERY_PACKAGE + "." + tableInfo.getBeanParamName() +";");
            bw.newLine();
            bw.write("import "+ Constants.VO_PACKAGE + ".ResponseVO;");
            bw.newLine();
            bw.write("import "+ Constants.SERVICE_PACKAGE + "." + serviceName +";");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RestController;");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RequestMapping;");
            bw.newLine();
            bw.write("import org.springframework.web.bind.annotation.RequestBody;");
            bw.newLine();
            bw.write("import javax.annotation.Resource;");
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();

            //--生成类
            //生成类注释
            BuildComment.createClassComment(bw,tableInfo.getComment()+"Controller");
            //生成类注解
            bw.write("@RestController");
            bw.newLine();
            bw.write("@RequestMapping(\"/"+StringUtils.lowerCaseFirstLetter(tableInfo.getBeanName())+"\")");
            bw.newLine();
            //生成类声明
            bw.write("public class "+className+" extends ABaseController{");
            bw.newLine();
            bw.newLine();

            bw.write("\t@Resource");
            bw.newLine();
            bw.write("\tprivate " + serviceName +" "+ serviceBeanName+";");
            bw.newLine();
            bw.newLine();

            bw.write("\t@RequestMapping(\"loadDataList\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO loadDataList(" + tableInfo.getBeanParamName() + " query) {");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(" + serviceBeanName + ".findListByPage(query));");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,  "新增");
            bw.write("\t@RequestMapping(\"add\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO add(" + tableInfo.getBeanName() + " bean){");
            bw.newLine();
            bw.write("\t\tthis." + serviceBeanName + ".add(bean);");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(null);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw, "批量新增");
            bw.write("\t@RequestMapping(\"addBatch\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO addBatch(@RequestBody List<" + tableInfo.getBeanName() + "> listBean){");
            bw.newLine();
            bw.write("\t\tthis."+serviceBeanName+".addBatch(listBean);");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(null);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,  "批量新增或修改");
            bw.write("\t@RequestMapping(\"addOrUpdateBatch\")");
            bw.newLine();
            bw.write("\tpublic ResponseVO addOrUpdateBatch(@RequestBody List<" + tableInfo.getBeanName() + "> listBean){");
            bw.newLine();
            bw.write("\t\tthis."+serviceBeanName+".addOrUpdateBatch(listBean);");
            bw.newLine();
            bw.write("\t\treturn getSuccessResponseVO(null);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();


            //根据索引更新
            for (Map.Entry<String, List<FieldInfo>> entry : tableInfo.getKeyIndexMap().entrySet()) {
                List<FieldInfo> keyFieldInfoList = entry.getValue();
                Integer index = 0;
                StringBuilder methodName = new StringBuilder();
                StringBuilder methodParams = new StringBuilder();
                StringBuilder params = new StringBuilder();
                for (FieldInfo fieldInfo:keyFieldInfoList) {
                    index++;
                    methodName.append(StringUtils.uperCaseFirstLetter(fieldInfo.getPropertyName()));
                    methodParams.append(fieldInfo.getJavaType()+" "+fieldInfo.getPropertyName());
                    params.append(fieldInfo.getPropertyName());
                    if (index < keyFieldInfoList.size()) {
                        methodName.append("And");
                        methodParams.append(", ");
                        params.append(", ");
                    }
                }

                BuildComment.createFieldOrMethodComment(bw, "根据"+methodName+"查询");
                bw.write("\t@RequestMapping(\"get"+tableInfo.getBeanName()+"By"+methodName+"\")");
                bw.newLine();
                bw.write("\tpublic ResponseVO get"+tableInfo.getBeanName()+"By"+methodName+"("+methodParams+"){");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(this."+serviceBeanName+".get"+tableInfo.getBeanName()+"By"+methodName+"("+params+"));");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();

                BuildComment.createFieldOrMethodComment(bw, "根据"+methodName+"更新");
                bw.write("\t@RequestMapping(\"update"+tableInfo.getBeanName()+"By"+methodName+"\")");
                bw.newLine();
                bw.write("\tpublic ResponseVO update"+tableInfo.getBeanName()+"By"+methodName+"(@RequestBody "+tableInfo.getBeanName()+" bean, "+methodParams+"){");
                bw.newLine();
                bw.write("\t\tthis."+serviceBeanName+".update"+tableInfo.getBeanName()+"By"+methodName+"(bean,"+params+");");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(null);");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();

                BuildComment.createFieldOrMethodComment(bw, "根据"+methodName+"删除");
                bw.write("\t@RequestMapping(\"delete"+tableInfo.getBeanName()+"By"+methodName+"\")");
                bw.newLine();
                bw.write("\tpublic ResponseVO delete"+tableInfo.getBeanName()+"By"+methodName+"("+methodParams+"){");
                bw.newLine();
                bw.write("\t\tthis."+serviceBeanName+".delete"+tableInfo.getBeanName()+"By"+methodName+"("+params+");");
                bw.newLine();
                bw.write("\t\treturn getSuccessResponseVO(null);");
                bw.newLine();
                bw.write("\t}");
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
