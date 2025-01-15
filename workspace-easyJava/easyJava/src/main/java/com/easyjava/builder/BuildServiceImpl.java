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

public class BuildServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(BuildService.class);
    public static void execute(TableInfo tableInfo){
        File floder = new File(Constants.SERVICE_IMPL_PATH);
        if(!floder.exists()){
            floder.mkdirs();
        }
        String interfaceName = tableInfo.getBeanName()+"Service";
        String className = tableInfo.getBeanName()+"ServiceImpl";
        File serviceFile = new File(floder,className+".java");
        String mapperName = tableInfo.getBeanName() + Constants.SUFFIX_MAPPER;
        String mapperBeanName = StringUtils.lowerCaseFirstLetter(mapperName);

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;
        try{
            out = new FileOutputStream(serviceFile);
            outw = new OutputStreamWriter(out,"utf-8");
            bw = new BufferedWriter(outw);

            //————————————————————————————————————————————————————————————————————————————————————
            //--打包
            bw.write("package "+ Constants.SERVICE_IMPL_PACKAGE+";");
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
            bw.write("import "+ Constants.QUERY_PACKAGE + ".SimplePage;");
            bw.newLine();
            bw.write("import "+ Constants.ENUMS_PACKAGE + ".PageSize;");
            bw.newLine();
            bw.write("import "+ Constants.VO_PACKAGE + ".PaginationResultVO;");
            bw.newLine();
            bw.write("import "+ Constants.PO_PACKAGE + "." + tableInfo.getBeanName() +";");
            bw.newLine();
            bw.write("import "+ Constants.QUERY_PACKAGE + "." + tableInfo.getBeanParamName() +";");
            bw.newLine();
            bw.write("import "+ Constants.QUERY_PACKAGE + "." + tableInfo.getBeanParamName() +";");
            bw.newLine();
            bw.write("import "+ Constants.MAPPER_PACKAGE + "." + mapperName +";");
            bw.newLine();
            bw.write("import "+ Constants.SERVICE_PACKAGE + "." + interfaceName +";");
            bw.newLine();
            bw.write("import org.springframework.stereotype.Service;");
            bw.newLine();
            bw.write("import javax.annotation.Resource;");
            bw.newLine();
            bw.write("import java.util.List;");
            bw.newLine();

            //--生成类
            //生成类注释
            BuildComment.createClassComment(bw,tableInfo.getComment()+"Service");
            //生成类注解
            bw.write("@Service(\""+StringUtils.lowerCaseFirstLetter(interfaceName)+"\")");
            bw.newLine();
            //生成类声明
            bw.write("public class "+className+" implements "+interfaceName+"{");
            bw.newLine();
            bw.newLine();

            bw.write("\t@Resource");
            bw.newLine();
            bw.write("\tprivate "+mapperName+"<"+tableInfo.getBeanName()+","+tableInfo.getBeanParamName() + "> " +mapperBeanName+";");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,"根据条件查询列表");
            bw.write("\tpublic List<"+tableInfo.getBeanName()+"> findListByParam("+tableInfo.getBeanParamName()+" query){");
            bw.newLine();
            bw.write("\t\treturn this."+mapperBeanName+".selectList(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,"根据条件查询数量");
            bw.write("\tpublic Integer findCountByParam("+ tableInfo.getBeanParamName() + " query){");
            bw.newLine();
            bw.write("\t\treturn this."+mapperBeanName+".selectCount(query);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,"分页查询");
            bw.write("\tpublic PaginationResultVO<"+tableInfo.getBeanName()+"> findListByPage("+tableInfo.getBeanParamName()+" query) {");
            bw.newLine();
            bw.write(  "\t\tInteger count = this.findCountByParam(query);");
            bw.newLine();
            bw.write(  "\t\tInteger pageSize = query.getPageSize() == null?PageSize.SIZE15.getSize():query.getPageSize();");
            bw.newLine();
            bw.write(  "\t\tSimplePage page = new SimplePage(query.getPageNo(), count, pageSize);");
            bw.newLine();
            bw.write(  "\t\tquery.setSimplePage(page);");
            bw.newLine();
            bw.write(  "\t\tList<"+ tableInfo.getBeanName()+"> list = this.findListByParam(query);");
            bw.newLine();
            bw.write(  "\t\tPaginationResultVO<"+ tableInfo.getBeanName()+"> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(),page.getPageTotal(),list);");
                    bw.newLine();
            bw.write(  "\t\treturn result;");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,  "新增");
            bw.write("\tpublic Integer add(" + tableInfo.getBeanName() + " bean){");
            bw.newLine();
            bw.write("\t\treturn this." + mapperBeanName + ".insert(bean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw, "批量新增");
            bw.write("\tpublic Integer addBatch(List<" + tableInfo.getBeanName() + "> listBean){");
            bw.newLine();
            bw.write("\t\tif (listBean == null || listBean.isEmpty()) {");
            bw.newLine();
            bw.write("\t\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn this."+mapperBeanName+".insertBatch(listBean);");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();
            bw.newLine();

            BuildComment.createFieldOrMethodComment(bw,  "批量新增或修改");
            bw.write("\tpublic Integer addOrUpdateBatch(List<" + tableInfo.getBeanName() + "> listBean){");
            bw.newLine();
            bw.write("\t\tif (listBean == null || listBean.isEmpty()) {");
            bw.newLine();
            bw.write("\t\t\treturn 0;");
            bw.newLine();
            bw.write("\t\t}");
            bw.newLine();
            bw.write("\t\treturn this."+mapperBeanName+".insertOrUpdateBatch(listBean);");
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
                bw.write("\tpublic "+tableInfo.getBeanName()+" get"+tableInfo.getBeanName()+"By"+methodName+"("+methodParams+"){");
                bw.newLine();
                bw.write("\t\treturn this."+mapperBeanName+".selectBy"+methodName+"("+params+");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();

                BuildComment.createFieldOrMethodComment(bw, "根据"+methodName+"更新");
                bw.write("\tpublic Integer update"+tableInfo.getBeanName()+"By"+methodName+"("+tableInfo.getBeanName()+" bean, "+methodParams+"){");
                bw.newLine();
                bw.write("\t\treturn this."+mapperBeanName+".updateBy"+methodName+"(bean,"+params+");");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();

                BuildComment.createFieldOrMethodComment(bw, "根据"+methodName+"删除");
                bw.write("\tpublic Integer delete"+tableInfo.getBeanName()+"By"+methodName+"("+methodParams+"){");
                bw.newLine();
                bw.write("\t\treturn this."+mapperBeanName+".deleteBy"+methodName+"("+params+");");
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
