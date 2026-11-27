package com.projet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.projet.repository")
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(valeur("CLINIQUE_DB_DRIVER", "db.driver", "com.mysql.cj.jdbc.Driver"));
        dataSource.setUrl(valeur("CLINIQUE_DB_URL", "db.url", "jdbc:mysql://localhost:3306/clinique"));
        dataSource.setUsername(valeur("CLINIQUE_DB_USERNAME", "db.username", "root"));
        dataSource.setPassword(valeur("CLINIQUE_DB_PASSWORD", "db.password", ""));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Priorite a la variable d'environnement, puis au fichier properties, puis
     * a la valeur par defaut.
     */
    private String valeur(String variableEnvironnement, String propriete, String defaut) {
        String valeur = System.getenv(variableEnvironnement);
        if (valeur == null || valeur.isBlank()) {
            valeur = environment.getProperty(propriete);
        }
        return (valeur == null || valeur.isBlank()) ? defaut : valeur;
    }
}
