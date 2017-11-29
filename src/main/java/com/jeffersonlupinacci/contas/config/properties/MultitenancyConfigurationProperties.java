package com.jeffersonlupinacci.contas.config.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;

/* configuração da propriedade de bancos de dados para o arquivo application.yml */

@ConfigurationProperties(prefix = "registrodebancos")
public class MultitenancyConfigurationProperties {
    
    private BancoDeDados bancoPadrao;

    private List<BancoDeDados> bancos = new ArrayList<BancoDeDados>();
    
    @PostConstruct
    public void init() {
        List<BancoDeDados> tcs = bancos.stream().filter(tc -> tc.isDefault()).collect(Collectors.toCollection(ArrayList::new));
        if (tcs.size() > 1) {
            throw new IllegalStateException("verifique as configuracoes, existe mais de um banco padrao.");
        }
        this.bancoPadrao = tcs.get(0);
    }
       
    public List<BancoDeDados> getBancos() {
        return bancos;
    }

    public void setBancos(List<BancoDeDados> bancos) {
        this.bancos = bancos;
    }

    public BancoDeDados getBancoPadrao() {
        return bancoPadrao;
    }

    public static class BancoDeDados {
        
        private String name;       
        private boolean isDefault;        
        private String driverClassName;
        private String url;
        private String username;
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }

    }

}