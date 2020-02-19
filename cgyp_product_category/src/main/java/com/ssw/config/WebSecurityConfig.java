package com.ssw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @BelongsProject: cgyp_product_category
 * @BelongsPackage: com.ssw.config
 * @Author: Wss
 * @CreateTime: 2020-02-08 15:30
 * @Description: WebSecurityConfig
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().anyRequest();
    }
}