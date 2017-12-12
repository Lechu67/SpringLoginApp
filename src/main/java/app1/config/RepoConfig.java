package app1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@ComponentScan("app1.repository")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class RepoConfig {

    /*For information:
        -application.properties should be in ressource !
        -there are two ways of getting value from file application.propeties
            -Using @Value(${}) on a field
            -(RECOMMENDED BY SPRING) Using the environnement class, which must be injected: here @Autowired or @Resource works
        -The class where we use values from application.properties must be annotated with @PropertySource*/

    /*@Value("${datasource.url}")
    private String DB_URL;
    @Value("${datasource.username}")
    private String DB_USER;
    @Value("${datasource.password}")
    private String DB_PASSWORD;
    @Value("${datasource.driver-class-name}")
    private String DB_DRIVER;*/

    private static final String DB_DRIVER = "datasource.driver-class-name";
    private static final String DB_URL = "datasource.url";
    private static final String DB_USER = "datasource.username";
    private static final String DB_PASSWORD = "datasource.password";

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty(DB_DRIVER));
        dataSource.setUrl(environment.getProperty(DB_URL));
        dataSource.setUsername(environment.getProperty(DB_USER));
        dataSource.setPassword(environment.getProperty(DB_PASSWORD));
        return dataSource;
    }
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }


}
