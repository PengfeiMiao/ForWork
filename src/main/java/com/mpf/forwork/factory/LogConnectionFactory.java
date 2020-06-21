package com.mpf.forwork.factory;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: xy
 * @create: 2019-05-13
 */
public class LogConnectionFactory {

    public static final String CONFIG_FILE = "log4j.properties";
    private DataSource dataSource = null;

    private LogConnectionFactory() {
        Properties properties = new Properties();
        //加载连接池配置文件

        try {
            properties.load(LogConnectionFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
            properties.setProperty("user", properties.getProperty("log4j.appender.user"));
            // or get properties from some configuration file
            properties.setProperty("password", properties.getProperty("log4j.appender.password"));

            GenericObjectPool<PoolableConnection> pool = new GenericObjectPool<PoolableConnection>();
            DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
                    properties.getProperty("log4j.appender.URL"), properties
            );
            new PoolableConnectionFactory(
                    connectionFactory, pool, null, "SELECT 1",
                    3, false, false, Connection.TRANSACTION_READ_COMMITTED
            );

            this.dataSource = new PoolingDataSource(pool);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Connection getDatabaseConnection() throws SQLException {
        return Singleton.INSTANCE.dataSource.getConnection();
    }

    private static interface Singleton {
        final LogConnectionFactory INSTANCE = new LogConnectionFactory();
    }
}
