package com.easyjava.builder;

import com.easyjava.bean.Constants;
import com.easyjava.utils.DateUtils;

import java.io.BufferedWriter;
import java.util.Date;

public class BuildComment {
    public static void createClassComment(BufferedWriter bw,String classComment) throws Exception{
        bw.write("/**");
        bw.newLine();
        bw.write(" * @Description:"+classComment);
        bw.newLine();
        bw.write(" * @author:"+ Constants.AUTHOR_COMMENT);
        bw.newLine();
        bw.write(" * @date:" + DateUtils.formatDate(new Date(),DateUtils._YYYYMMDD));
        bw.newLine();
        bw.write(" */");
        bw.newLine();
    }

    public static void createFieldOrMethodComment(BufferedWriter bw, String Comment) throws Exception{
        bw.write("\t/**");
        bw.newLine();
        if(Comment==null){
            bw.write("\t * ");
        }else {
            bw.write("\t * "+Comment);
        }
        bw.newLine();
        bw.write("\t */");
        bw.newLine();
    }


}
