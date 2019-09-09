package core;

import main.SqlBuilder;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author dxw
 * @ClassName TableThread.java
 * @Description 表创建线程，一次性创建多个表时使用
 * @createTime 2019-06-21 10:26
 */
public class TableThread extends Thread{

    private SqlBuilder sqlBuilder;

    private SqlSessionFactory sqlSessionFactory;

    Logger logger = LoggerFactory.getLogger(TableThread.class);

    public TableThread(SqlBuilder sqlBuilder, SqlSessionFactory sqlSessionFactory) {
        this.sqlBuilder = sqlBuilder;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void run() {
        //获取连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //创建sql
        String sql = sqlBuilder.getCreateTableSql();
        //执行操作
        PreparedStatement preparedStatement = null;
        try {
            logger.info(sql);
            preparedStatement = sqlSession.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(sqlSession != null ){
                sqlSession.close();
            }
        }
    }
}
