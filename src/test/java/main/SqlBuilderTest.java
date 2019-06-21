package main;

import core.Column;
import core.Table;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class SqlBuilderTest extends TestCase {

    @Test
    public void testGetCreateTableSql() {

        Table table = new Table();
        table.setTableName("1");
        table.setTableComment("111");
        ArrayList<Column> columnList = new ArrayList<>();
        Column e1 = new Column();
        e1.setColumnName("1");
        e1.setColumnType("int");
        e1.setColumnLength("2");
        e1.setColumnComment("1");
        columnList.add(e1);
        Column e2 = new Column();
        e2.setColumnName("2");
        e2.setColumnType("varchar");
        e2.setColumnLength("2");
        e2.setColumnComment("1");
        columnList.add(e2);
        table.setColumnList(columnList);
        SqlBuilder sqlBuilder = new SqlBuilder(table);
        System.out.println(sqlBuilder.getCreateTableSql());
    }
}