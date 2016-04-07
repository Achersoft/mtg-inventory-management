package com.achersoft.init;

import com.achersoft.configuration.PropertiesManager;
import com.achersoft.mtg.card.CardService;
import com.achersoft.mtg.card.CardServiceImpl;
import com.achersoft.mtg.card.dao.Set;
import com.achersoft.mtg.card.persistence.CardMapper;
import com.achersoft.mtg.importer.CardImporterService;
import com.achersoft.mtg.importer.CardImporterServiceImpl;
import com.achersoft.mtg.importer.persistence.ImporterMapper;
import com.achersoft.mtg.price.MTGPriceSync;
import com.achersoft.mtg.price.persistence.PriceMapper;
import com.achersoft.rest.services.ImporterRestService;
import com.achersoft.security.UserAuthenticationService;
import com.achersoft.security.UserAuthenticationServiceImpl;
import com.achersoft.security.authenticator.Authenticator;
import com.achersoft.security.providers.SignatureServiceProvider;
import com.achersoft.security.providers.UserPrincipalProvider;
import com.achersoft.user.UserService;
import com.achersoft.user.UserServiceImpl;
import com.achersoft.user.persistence.UserMapper;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableScheduling
@MapperScan(basePackageClasses = { UserMapper.class,
                                   ImporterMapper.class,
                                   CardMapper.class,
                                   PriceMapper.class} )
public class SpringConfig {
    
    // <editor-fold defaultstate="collapsed" desc="REST Gateways"> 
    @Bean
    public ImporterRestService importerRestService() {
        return new ImporterRestService();
    }
    
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Services"> 
    @Bean
    public CardImporterService cardImporterService() {
        return new CardImporterServiceImpl();
    }
    
    @Bean
    public CardService cardService() {
        return new CardServiceImpl();
    }
    
    @Bean
    public UserAuthenticationService userAuthenticationService() {
        return new UserAuthenticationServiceImpl();
    }
    
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="System"> 
    @Bean
    public DataSource dataSource() {
        DataSource datasource = new DataSource();
        PoolProperties p = new PoolProperties();
        p.setUrl("jdbc:" + "mysql" + 
                 "://" + "localhost" +
                 ":" + "3306" +
                 "/" + "titan");
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername("root");
        p.setPassword("zxcxcv");
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
            "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
            "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

        datasource.setPoolProperties(p);
        return datasource;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }
    
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    }
    
    @Bean(name = "setList")
    public  Map<String, List<Set>> setList(CardMapper mapper) {
        Map<String, List<Set>> setLists = new HashMap();
        setLists.put("English", mapper.getSets("English"));
        setLists.put("Russian", mapper.getSets("Russian"));
        setLists.put("Japanese", mapper.getSets("Japanese"));
        setLists.put("Korean", mapper.getSets("Korean"));
        setLists.put("Spanish", mapper.getSets("Spanish"));
        setLists.put("German", mapper.getSets("German"));
        setLists.put("Portuguese", mapper.getSets("Portuguese (Brazil)"));
        setLists.put("French", mapper.getSets("French"));
        setLists.put("Italian", mapper.getSets("Italian"));
        return setLists;
    }
    
    @Bean
    public MTGPriceSync MTGPriceSync() {
        return new MTGPriceSync();
    }
    
    @Bean
    public SecretKeySpec signatureKey() throws Exception {
        byte[] key = new byte[256];
        new SecureRandom().nextBytes(key);
        return new SecretKeySpec(key, "HmacSHA256");
    }
    
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SignatureServiceProvider signatureServiceProvider(SecretKeySpec signatureKey) throws Exception {
        return new SignatureServiceProvider(signatureKey);
    }
     
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserPrincipalProvider userPrincipalProvider() {
        return new UserPrincipalProvider();
    }
    
    @Bean
    public Authenticator authenticator() {
        return new Authenticator();
    }
    
    @Bean
    public PropertiesManager propertiesManager() {
        return new PropertiesManager();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="DTO Prototypes"> 
  /*  @Bean
    @Scope("prototype")
    public PositionListDTO positionListDTO() {
        return new PositionListDTO();
    }*/
    

    // </editor-fold>
}
