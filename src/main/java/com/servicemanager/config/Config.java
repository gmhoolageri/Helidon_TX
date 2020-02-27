package com.servicemanager.config;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class Config {

    @Inject
    @ConfigProperty(name = "javax.sql.DataSource.otmsmDataSource.dataSourceClassName")
    private String dataSourceClassName;

    @Inject
    @ConfigProperty(name = "javax.sql.DataSource.otmsmDataSource.dataSource.url")
    private String url;

    @Inject
    @ConfigProperty(name = "javax.sql.DataSource.otmsmDataSource.dataSource.user")
    private String user;

    @Inject
    @ConfigProperty(name = "javax.sql.DataSource.otmsmDataSource.dataSource.password")
    private String password;

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}
