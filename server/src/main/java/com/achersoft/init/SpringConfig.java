package com.achersoft.init;

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
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableScheduling
@MapperScan(basePackageClasses = { ImporterMapper.class,
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
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="System"> 
    @Bean
    public DataSource dataSource() {
        MysqlConnectionPoolDataSource src = new MysqlConnectionPoolDataSource();
        src.setURL("jdbc:mysql://localhost:3306/titan");
        src.setUser("root");
        src.setPassword("zxcxcv");
        return src;
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

    
    
    /*@Bean
    public EstaffPropertiesManager estaffPropertiesManager(ConfigurationMapper mapper) {
        return new EstaffPropertiesManager(mapper);
    }

    @Bean(name = "userAuthenticators")
    public  Map<String, Authenticator> userAuthenticators(UserAuthenticationMapper mapper) {
        Map<String, Authenticator> authenticators = new HashMap();
        for(ActiveAuthenticator auth : mapper.getAuthenticators()){
            if(auth.getType() == LDAP)
                try {
                    authenticators.put(auth.getId(), new LdapAuthenticator(auth.getId(), mapper));
                } catch (NamingException ex) {
                    Logger.getLogger(SpringConfig.class.getName()).log(Level.SEVERE, null, ex);
                }
            if(auth.getType() == LOCAL)
                authenticators.put(auth.getId(), new EstaffAuthenticator(mapper));
        }  
        return authenticators;
    }
    
    @Bean(name = "skillsMap")
    public  Map<String, String> skillsMap(ConfigurationMapper mapper) {
        Map<String, String> skills = new HashMap();
        for(SkillCategory skill : mapper.getSkillCategories())
            skills.put(skill.getId(), skill.getValue());
        return skills;
    }
    
    @Bean(name = "systemProperties")
    public Properties systemProperties() throws Exception {
        File configDir = new File(System.getProperty("catalina.base"), "conf");
        File configFile = new File(configDir, "estaff.properties");
        InputStream stream = new FileInputStream(configFile);
        Properties props = new Properties();
        props.load(stream);
        return props;
    }
    
    @Bean
    public DataSource dataSource(Properties systemProperties) {
        MysqlConnectionPoolDataSource src = new MysqlConnectionPoolDataSource();
        src.setURL("jdbc:" + systemProperties.get("dataSource.database") + 
                   "://" + systemProperties.get("dataSource.url") +
                   ":" + systemProperties.get("dataSource.port") +
                   "/" + systemProperties.get("dataSource.schema"));
        src.setUser(systemProperties.get("dataSource.user").toString());
        src.setPassword(systemProperties.get("dataSource.password").toString());
        return src;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(Properties systemProperties) {
        return new DataSourceTransactionManager(dataSource(systemProperties));
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(Properties systemProperties) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource(systemProperties));
        return sessionFactory;
    }
    
    @Bean
    public ElasticSearchService elasticSearchService(Properties systemProperties) throws Exception {
        ImmutableSettings.Builder settingsBuilder = ImmutableSettings.settingsBuilder();
        String value;
        if ( (value = systemProperties.getProperty("es.cluster.name")) != null )
            settingsBuilder.put("cluster.name", value);
        if ( (value = systemProperties.getProperty("es.node.name")) != null )
            settingsBuilder.put("node.name", value);
        settingsBuilder.put("client.transport.sniff", true);  // Find all other nodes in cluster so requests will be round-robin'd
        return new ElasticSearchService(systemProperties, settingsBuilder.build());
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    }
    
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserPrincipalProvider userPrincipalProvider() {
        return new UserPrincipalProvider();
    }*/
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="DTO Prototypes"> 
  /*  @Bean
    @Scope("prototype")
    public PositionListDTO positionListDTO() {
        return new PositionListDTO();
    }*/
    

    // </editor-fold>
}
