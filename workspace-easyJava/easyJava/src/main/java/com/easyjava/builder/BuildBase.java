package com.easyjava.builder;

import com.easyjava.bean.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class BuildBase {
    private static Logger logger = LoggerFactory.getLogger(BuildBase.class);
    public static void execute() {
        List<String> headerInfoList = new ArrayList();

        headerInfoList.add("package "+Constants.ENUMS_PACKAGE);
        build(headerInfoList,"DateTimePatternEnum", Constants.ENUMS_PATH);

        headerInfoList.clear();

        headerInfoList.add("package "+Constants.UTILS_PACKAGE);
        build(headerInfoList,"DateUtils", Constants.UTILS_PATH);

        headerInfoList.clear();

        headerInfoList.add("package "+Constants.MAPPER_PACKAGE);
        build(headerInfoList,"BaseMapper", Constants.MAPPER_PATH);

        headerInfoList.clear();

        headerInfoList.add("package "+Constants.ENUMS_PACKAGE);
        build(headerInfoList,"PageSize", Constants.ENUMS_PATH);

        headerInfoList.clear();

        headerInfoList.add("package "+Constants.QUERY_PACKAGE);
        headerInfoList.add("import "+Constants.ENUMS_PACKAGE+".PageSize");
        build(headerInfoList,"SimplePage", Constants.QUERY_PATH);


        headerInfoList.clear();

        headerInfoList.add("package "+Constants.QUERY_PACKAGE);
        build(headerInfoList,"BaseQuery", Constants.QUERY_PATH);

        headerInfoList.clear();
        //PaginationResultVO
        headerInfoList.add("package "+Constants.VO_PACKAGE);
        build(headerInfoList,"PaginationResultVO", Constants.VO_PATH);

        headerInfoList.clear();

        headerInfoList.add("package "+Constants.EXCEPTION_PACKAGE);
        headerInfoList.add("import "+Constants.ENUMS_PACKAGE+"."+"ResponseCodeEnum");
        build(headerInfoList,"BusinessException", Constants.EXCEPTION_PATH);

        headerInfoList.clear();

        headerInfoList.add("package "+Constants.ENUMS_PACKAGE);
        build(headerInfoList,"ResponseCodeEnum", Constants.ENUMS_PATH);

        headerInfoList.clear();

        headerInfoList.add("package "+Constants.CONTROLLER_PACKAGE);
        headerInfoList.add("import "+Constants.ENUMS_PACKAGE+"."+"ResponseCodeEnum");
        headerInfoList.add("import "+Constants.VO_PACKAGE+"."+"ResponseVO");
        build(headerInfoList,"ABaseController", Constants.CONTROLLER_PATH);

        headerInfoList.clear();

        headerInfoList.add("package "+Constants.VO_PACKAGE);
        build(headerInfoList,"ResponseVO", Constants.VO_PATH);

        headerInfoList.clear();

        headerInfoList.add("package "+Constants.CONTROLLER_PACKAGE);
        headerInfoList.add("import "+Constants.ENUMS_PACKAGE+"."+"ResponseCodeEnum");
        headerInfoList.add("import "+Constants.VO_PACKAGE+"."+"ResponseVO");
        headerInfoList.add("import "+Constants.EXCEPTION_PACKAGE+"."+"BusinessException");
        headerInfoList.add("import org.slf4j.Logger;");
        headerInfoList.add("import org.slf4j.LoggerFactory;");
        headerInfoList.add("import org.springframework.dao.DuplicateKeyException;");
        headerInfoList.add("import org.springframework.validation.BindException;");
        headerInfoList.add("import org.springframework.web.bind.annotation.ExceptionHandler;");
        headerInfoList.add("import org.springframework.web.bind.annotation.RestControllerAdvice;");
        headerInfoList.add("import org.springframework.web.servlet.NoHandlerFoundException;");
        headerInfoList.add("import javax.servlet.http.HttpServletRequest;");
        build(headerInfoList,"AdlobalExceptionHandlerController", Constants.CONTROLLER_PATH);


    }
    private static void build(List<String> headerInfoList ,String fileName, String outPutPath) {
        File folder = new File(outPutPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File javaFile = new File(outPutPath, fileName + ".java");
        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        InputStream in = null;
        InputStreamReader inr = null;
        BufferedReader bf = null;

        try {
            out = new FileOutputStream(javaFile);
            outw = new OutputStreamWriter(out,"utf-8");
            bw = new BufferedWriter(outw);

            String templatePath = BuildBase.class.getClassLoader().getResource("template/"+fileName+".txt").getPath();
            in = new FileInputStream(templatePath);
            inr = new InputStreamReader(in,"utf-8");
            bf = new BufferedReader(inr);

            for (String head:headerInfoList){
                bw.write(head+";");
                bw.newLine();
                bw.newLine();

            }

            String lineInfo = null;
            while ((lineInfo = bf.readLine())!=null){
                bw.write(lineInfo);
                bw.newLine();
            }
            bw.flush();
        } catch (Exception e) {
            logger.error("生成基础类：{},失败;",fileName,e);

        } finally {
            if(bf != null){
                try {
                    bf.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(inr != null){
                try {
                    inr.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(in != null){
                try {
                    in.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(bw != null){
                try {
                    bw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(outw != null){
                try {
                    outw.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(out != null){
                try {
                    out.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
