package com.easyjava;

import com.easyjava.bean.TableInfo;
import com.easyjava.builder.*;

import java.sql.SQLException;
import java.util.List;

public class RunApplication {
    public static void main(String[] args) {
        try {
            List<TableInfo> tableInfoList = BuildTable.getTables();
            for(TableInfo tableInfo:tableInfoList){
                BuildPo.execute(tableInfo);
                BuildQuery.execute(tableInfo);
                BuildMapper.execute(tableInfo);
                BuildMapperXML.execute(tableInfo);
                BuildService.execute(tableInfo);
                BuildServiceImpl.execute(tableInfo);
                BuildController.execute(tableInfo);
            }
            BuildBase.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
