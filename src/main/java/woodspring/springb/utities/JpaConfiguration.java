package woodspring.springb.utities;

import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"woodspring.springb.entity", "woodspring.springb.dao"})
@EnableJpaRepositories(basePackages = "woodspring.springb.dao", 
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EntityScan(basePackages = "woodspring.springb.entity")
@EnableTransactionManagement
@EnableAutoConfiguration
//@EntityScan(basePackages = {"woodspring.springz.dto"})
public class JpaConfiguration {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private Environment environment;
	
//	@Autowired
//	private EntityManagerFactory entityMgrFactory;
	
	@Value("${datasource.woodspring.maxPoolSize:10}")
    private int maxPoolSize;
    /*
     * Here you can specify any provider specific properties.
     */
	/*
     * Populate SpringBoot DataSourceProperties object directly from application.yml 
     * based on prefix.Thanks to .yml, Hierachical data is mapped out of the box with matching-name
     * properties of DataSourceProperties object].
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.woodspring")
    public DataSourceProperties dataSourceProperties(){
        return new DataSourceProperties();
    }
	
    /*
     * Configure HikariCP pooled DataSource.
     */
    @Bean
    public DataSource dataSource() {
        DataSourceProperties dataSourceProperties = dataSourceProperties();
            HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
                    .create(dataSourceProperties.getClassLoader())
                    .driverClassName(dataSourceProperties.getDriverClassName())
                    .url(dataSourceProperties.getUrl())
                    .username(dataSourceProperties.getUsername())
                    .password(dataSourceProperties.getPassword())
                    .type(HikariDataSource.class)
                    .build();
            dataSource.setMaximumPoolSize(maxPoolSize);
            return dataSource;
    }
    /*
     * Entity Manager Factory setup.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(new String[] { "woodspring.springb.entity" });
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());
        return factoryBean;
    }
 
    /*
     * Provider specific adapter.
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        return hibernateJpaVendorAdapter;
    }
    
    private Properties jpaProperties() {
        Properties properties = new Properties();
        //String envStr = new Gson().toJson( environment);
        //System.out.println("environment:["+envStr+"]");
        System.out.println("environment:ActiveProfiles["+environment.getActiveProfiles()
        +"]"+ environment.getRequiredProperty("datasource.woodspring.url")
        +"]");
        properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.woodspring.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("datasource.woodspring.hibernate.hbm2ddl.method"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.woodspring.hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.woodspring.hibernate.format_sql"));
        properties.put("hibernate.current_session_context_class", environment.getRequiredProperty("datasource.woodspring.hibernate.current_session"));
        if(StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.woodspring.defaultSchema"))){
            properties.put("hibernate.default_schema", environment.getRequiredProperty("datasource.woodspring.defaultSchema"));
        }
        return properties;
    }
    
    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
  
    @Bean
	public SessionFactory getSessionFactory() {
//	    if (entityMgrFactory.unwrap(SessionFactory.class) == null) {
//	        throw new NullPointerException("factory is not a hibernate factory");
//	    }
    	SessionFactory retSession = null;
	    try {
			if (entityManagerFactory().getNativeEntityManagerFactory().unwrap(SessionFactory.class) == null) {
			    throw new NullPointerException("factory is not a hibernate factory");
			}
			retSession = entityManagerFactory().getNativeEntityManagerFactory().unwrap(SessionFactory.class);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return retSession;
	}
//    @Bean
//    public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
//        HibernateJpaSessionFactoryBean fact = new HibernateJpaSessionFactoryBean();
//        fact.setEntityManagerFactory(emf);
//        return fact;
//    }
    
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
//            }
//        };
//    }
}
