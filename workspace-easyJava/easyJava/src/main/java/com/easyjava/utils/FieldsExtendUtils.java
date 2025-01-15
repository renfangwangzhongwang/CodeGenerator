package com.easyjava.utils;

import com.easyjava.bean.Constants;
import com.easyjava.bean.FieldInfo;
import com.easyjava.builder.BuildComment;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class FieldsExtendUtils {
    public static List<FieldInfo> fieldsExtend(List<FieldInfo> fieldInfoList){
        List<FieldInfo> ExtendList = new ArrayList<>();
        for(FieldInfo fieldInfo : fieldInfoList){
            //ExtendList.add(fieldInfo);
            //如果是String类型附加模糊属性
            if(ArrayUtils.contains(Constants.SQL_STRING_TYPE,fieldInfo.getSqlType())){
                FieldInfo tempField = new FieldInfo();
                tempField.setPropertyName(fieldInfo.getPropertyName()+Constants.SUFFIX_BEAN_QUERY_FUZZY);
                tempField.setJavaType(fieldInfo.getJavaType());
                tempField.setComment(fieldInfo.getComment()+"——模糊匹配（扩展）");
                cloneOthers(tempField,fieldInfo);
                ExtendList.add(tempField);
            }
            //如果是时期类型附加起止属性
            if(ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPES,fieldInfo.getSqlType())||ArrayUtils.contains(Constants.SQL_DATE_TYPES,fieldInfo.getSqlType())){
                FieldInfo tempField1 = new FieldInfo();
                tempField1.setPropertyName(fieldInfo.getPropertyName()+Constants.SUFFIX_BEAN_QUERY_TIME_START);
                tempField1.setJavaType("String");
                tempField1.setComment(fieldInfo.getComment()+"——起始点（扩展）");
                cloneOthers(tempField1,fieldInfo);
                ExtendList.add(tempField1);

                FieldInfo tempField2 = new FieldInfo();
                tempField2.setPropertyName(fieldInfo.getPropertyName()+Constants.SUFFIX_BEAN_QUERY_TIME_END);
                tempField2.setJavaType("String");
                tempField2.setComment(fieldInfo.getComment()+"——结束点（扩展）");
                cloneOthers(tempField2,fieldInfo);
                ExtendList.add(tempField2);
            }
        }
        return ExtendList;
    }

    private static void cloneOthers(FieldInfo clone,FieldInfo cloned) {
        clone.setFieldName(cloned.getFieldName());
        clone.setSqlType(cloned.getSqlType());
        clone.setAutoIncrement(cloned.getAutoIncrement());
    }
}
