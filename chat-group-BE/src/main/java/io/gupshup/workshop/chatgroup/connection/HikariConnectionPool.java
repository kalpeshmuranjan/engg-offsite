package io.gupshup.workshop.chatgroup.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariConnectionPool {
    private HikariDataSource hikariDataSource;


    public HikariConnectionPool () {
    }


    public HikariConnectionPool (String driverClassName, String jdbcUrl, String userName, String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("hikari");
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(password);
        hikariConfig.setAutoCommit(true);
        if (jdbcUrl.toLowerCase().contains("mysql")) {
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            hikariConfig.addDataSourceProperty("useSSL", false);
            hikariConfig.addDataSourceProperty("rewriteBatchedStatements", true);
            hikariConfig.addDataSourceProperty("logSlowQueries", true);
            hikariConfig.addDataSourceProperty("slowQueryThresholdMillis", 5000);
            hikariConfig.addDataSourceProperty("gatherPerfMetrics", true);
            hikariConfig.addDataSourceProperty("reportMetricsIntervalMillis", 30000);
        }

        this.instantiateDataSource(hikariConfig);
    }




    private void instantiateDataSource (HikariConfig hikariConfig) {
        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }


    public HikariConnectionPool (Properties properties) {
        HikariConfig config = new HikariConfig(properties);
        this.instantiateDataSource(config);
    }


    public Connection getConnection () {
        try {
            synchronized (HikariConnectionPool.class) {
                if (this.hikariDataSource != null && this.hikariDataSource.isRunning()) {
                    return this.hikariDataSource.getConnection();
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            throw new RuntimeException(sqlException);
        }
        return null;
    }


    public void shutdown () {
        synchronized (HikariConnectionPool.class) {
            if (this.hikariDataSource != null && !this.hikariDataSource.isClosed()) {
                this.hikariDataSource.close();
                this.hikariDataSource = null;
            }
        }
    }
}
