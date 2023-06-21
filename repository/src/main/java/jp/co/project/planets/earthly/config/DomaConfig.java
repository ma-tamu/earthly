package jp.co.project.planets.earthly.config;

import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.annotation.PostConstruct;

/**
 * doma config
 */
@Configuration("domaConfig")
@ConfigurationProperties("spring.datasource.hikari")
public class DomaConfig extends HikariConfig implements Config {

    private DataSource dataSource;

    @PostConstruct
    public void postConstruct() {
        this.dataSource = new TransactionAwareDataSourceProxy(new HikariDataSource(this));
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Dialect getDialect() {
        return new MysqlDialect();
    }
}
