package app;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.terasoluna.gfw.common.date.jodatime.DefaultJodaTimeDateFactory;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;

@Configuration
@PropertySource("classpath:todo-infra.properties")
public class EnvConfig {
	
	@Value("${database.driverClassName}")
	private String driverClassName;
	
	@Value("${database.url}")
	private String url;
	
	@Value("${database.username}")
	private String username;
	
	@Value("${database.password}")
	private String password;
	
	@Value("${cp.maxActive}")
	private Integer maxTotal;
	
	@Value("${cp.maxIdle}")
	private Integer maxIdle;
	
	@Value("${cp.minIdle}")
	private Integer minIdle;
	
	@Value("${cp.maxWait}")
	private Integer maxWaitMillis;
	
	@Bean(name="dateFactory")
	public DefaultJodaTimeDateFactory dateFactory() {
		return new DefaultJodaTimeDateFactory();
	}

	@Bean(name="realDataSource", destroyMethod="close")
	public BasicDataSource realDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDefaultAutoCommit(false);
		dataSource.setMaxTotal(maxTotal);
		dataSource.setMaxIdle(maxIdle);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxWaitMillis(maxWaitMillis);
		return dataSource;
	}
	
	@Bean(name="dataSource")
	@Primary
	public DataSource dataSource() {
		DataSource dataSource = new Log4jdbcProxyDataSource(realDataSource());
		return dataSource;
	}
	
	@Bean(name="transactionManager")
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager =  new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
	
//	@Value("classpath:database/H2-schema.sql")
//	private Resource schemaScript;
//	
//	@Value("classpath:database/H2-dataload.sql")
//	private Resource dataScript;
//	
//	@Inject
//	@Bean(name="dataSourceInitializer")
//	public DataSourceInitializer dataSourceInitializer(@Named("dataSource") DataSource dataSource) {
//	    final DataSourceInitializer initializer = new DataSourceInitializer();
//	    initializer.setDataSource(dataSource);
//	    initializer.setDatabasePopulator(databasePopulator());
//	    return initializer;
//	}
//
//	private DatabasePopulator databasePopulator() {
//	    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//	    populator.setContinueOnError(true);
//	    populator.setSqlScriptEncoding("UTF-8");
//	    populator.addScript(schemaScript);
//	    populator.addScript(dataScript);
//	    return populator;
//	}

}
