package main;

import core.Table;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import util.ExcelDataTranslateTableTool;
import util.ReadExcelTool;

import java.io.File;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

/**
 * @author dxw
 * @ClassName Main.java
 * @Description 系统运行入口
 * @createTime 2019-06-05 14:11
 */
public class Main {

    public static void main(String[] args) throws Exception {
        //连接到文件
        File tableTemplate = Resources.getResourceAsFile("table_template.xlsx").getAbsoluteFile();
        //获取表格数据
        List<String[]> data = ReadExcelTool.readExcel(tableTemplate);
        //解析数据
        Table table = ExcelDataTranslateTableTool.getTable(data);
        //获取数据库配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //获取自定义参数化配置
        Properties custom = Resources.getResourceAsProperties("setting.properties");
        //获取连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建sql
        SqlBuilder sqlBuilder = new SqlBuilder(table,custom);
        String sql = sqlBuilder.getCreateTableSql();
        //执行操作
        PreparedStatement preparedStatement = sqlSession.getConnection().prepareStatement(sql);
        System.out.println(sql);
        preparedStatement.executeUpdate();
        //关闭
        if(preparedStatement != null){
            preparedStatement.close();
        }
        if(sqlSession != null ){
            sqlSession.close();
        }

    }
}
