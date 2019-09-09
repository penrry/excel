package main;

import core.Column;
import core.Table;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Properties;

/**
 * @author wpy
 * @ClassName SqlBuilder.java
 * @Description SQL语句创建器
 * @createTime 2019-06-05 14:46
 */
public class SqlBuilder {

    private Table table;

    private Properties properties;

    public SqlBuilder(Table table){
        this.table = table;
    }

    public SqlBuilder(Table table, Properties properties) {
        this.table = table;
        this.properties = properties;
    }

    public String getCreateTableSql(){
        StringBuilder builder = new StringBuilder(10000);
        builder.append("create table `" + table.getTableName() +"`");
        builder.append(" ( ");
        builder.append("`id` varchar(64) NOT NULL  COMMENT '主键ID' ,");

        for (Column column : table.getColumnList()) {
            //去除空的实体column
            if (checkColumn(column)) {

                builder.append(column.getColumnName()+" ");
                //部分数据类型特殊处理
                if(checkUnSetLengthType(column.getColumnType())){
                    builder.append(column.getColumnType()+ " ");
                }
                else {
//                    builder.append(column.getColumnType()+"("+column.getColumnLength() + ")" + " ");
                    builder.append(column.getColumnType()+ " ");
                }
//                判断字段是否允许设为空值
                if((Integer.parseInt(column.getColumnLimitNull()))==0){
                    builder.append("not null  ");
                }
//                System.out.println("------------limitNull---------------");
//                System.out.println(column.getColumnLimitNull());

                builder.append("comment '" + column.getColumnComment() + "'" +" ");

                builder.append(",");
            }
            System.out.println(table.getTableName());
        }


        //去除最后一个逗号
//        int i = builder.lastIndexOf(",");
//        builder.delete(i,i+1);
        builder.append("PRIMARY KEY (`id`) ");

        builder.append(" ) ");
<<<<<<< HEAD

        System.out.println("--------------builder----------------");
        System.out.println(builder.toString().toLowerCase());
        return builder.toString().toLowerCase();


=======
        builder.append(" comment = '" + table.getTableComment() + "' ");
        return builder.toString();
>>>>>>> fbef8d326d0a72ddcd5ee29565527983d18c1854
    }


    /**
     * 检查每列数据是否齐全，不齐全将不会加入到sql语句拼接中
     *
     * @param column
     * @return
     */
    private boolean checkColumn(Column column) {
        if(StringUtils.isEmpty(column.getColumnName())){
            return false;
        }
        if(StringUtils.isEmpty(column.getColumnType())){
            return false;

        }
<<<<<<< HEAD
//        System.out.println("------------columnTYpe---------------------");
//        System.out.println(StringUtils.isEmpty(column.getColumnType()));

=======
        //todo
>>>>>>> fbef8d326d0a72ddcd5ee29565527983d18c1854
//        if(StringUtils.isEmpty(column.getColumnLength())){
//            return false;
//        }
        return true;
    }

    /**
     * 当数据类型不需要设置长度时返回true，否则返回false
     *
     * @param type
     * @return
     */
    private boolean checkUnSetLengthType(String type){
        if(StringUtils.isEmpty(type)){
            return false;
        }
        if(type.compareToIgnoreCase("DATETIME") == 0){
            return true;
        }
        if(type.compareToIgnoreCase("DATE")==0){
            return true;
        }
        if(type.compareToIgnoreCase("TIME")==0){
            return true;
        }
        if(type.compareToIgnoreCase("YEAR")==0){
            return true;
        }
        if(type.compareToIgnoreCase("TIMESTAMP")==0){
            return true;
        }
        if(type.compareToIgnoreCase("TEXT")==0){
            return true;
        }
        //--------------number字段---------------------------
        if(type.compareToIgnoreCase("NUMBER")==0){
            return true;
        }
        //--------------------end----------------------------
        if(type.compareToIgnoreCase("DOUBLE") == 0){
            String doubleSetting = properties.getProperty("type_double_auto");
            if(doubleSetting != null && doubleSetting.equals("1")){
                return true;
            }
            return false;
        }
        if(type.compareToIgnoreCase("FLOAT") == 0){
            String floatSetting = properties.getProperty("type_float_auto");
            if(floatSetting != null && floatSetting.equals("1")){
                return true;
            }
            return false;
        }
        return false;
    }
}
