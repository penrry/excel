package util;

import core.Column;
import core.Table;
import javafx.scene.control.Tab;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author dxw
 * @ClassName ExcelDataTranslateTableTool.java
 * @Description 调用ReadExcelTool工具类读取数据并封装为table
 * @createTime 2019-06-05 13:52
 */
public class ExcelDataTranslateTableTool {

    /**
     * 要求集合中的每个字符串数组值依次为 表名 表描述 列名 字段类型 长度 列的描述 对应的值
     * 表名 和 表描述 只获取集合中的第一个字符数数组的值
     *
     * @param data
     * @return
     */
    public static Table getTable(List<String[]> data) throws Exception {
        Table table = new Table();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                if (StringUtils.isEmpty(data.get(0)[0])) {
                    throw new Exception("无法从excel数据中获取到表名");
                }
                table.setTableName(data.get(0)[0]);
                table.setTableComment(data.get(0)[1]);
                Column column = getColumn(data.get(0));
                table.getColumnList().add(column);
            } else {
                table.getColumnList().add(getColumn(data.get(i)));
            }
        }
        return table;
    }

    /**
     * 从字符串数组中获取数据并将其封装为 Column 对象
     *
     * @param data
     * @return
     */
    private static Column getColumn(String[] data) {
        Column column = new Column();
        //获取列名
        column.setColumnName(data[2]);
        //获取字段类型
        column.setColumnType(data[3]);
        //获取字段长度
        column.setColumnLength(data[4]);
        //获取字段描述
        column.setColumnComment(data[5]);
        return column;
    }

    public static List<Table> getTables(List<String[]> data) throws Exception {
        List<Table> tables = new ArrayList<>();
        Map<String, List<String[]>> map = new HashMap<String, List<String[]>>();
        String tableName = "";
        int begin = 0;
        int end = 0;
        for (int i = 0; i < data.size(); i++) {
            String name = data.get(i)[0];
            //表分割处理
            if(!StringUtils.isEmpty(name) && !tableName.equals(name)){
                //分割
                if(i != 0){
                    end = i;
                    map.put(tableName,data.subList(begin,end));
                    begin = i;
                }
                //下一张表的表名
                tableName = name;
            }
            //处理最后一个表
            if(i == data.size() - 1){
                end = data.size();
                map.put(tableName,data.subList(begin,end));
            }
        }
        Set<Map.Entry<String, List<String[]>>> set = map.entrySet();
        for (Map.Entry<String, List<String[]>> stringListEntry : set) {
            List<String[]> value = stringListEntry.getValue();
            Table table = getTable(value);
            tables.add(table);
        }
        return tables;
    }
}
