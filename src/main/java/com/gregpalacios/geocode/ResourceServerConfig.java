package com.gregpalacios.geocode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

//Segunda Clase
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Autowired
    private ResourceServerTokenServices tokenServices;
	
    @Value("${security.jwt.resource-ids}")
    private String resourceIds;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
                http
                .exceptionHandling().authenticationEntryPoint(new AuthException())
                .and()
                .requestMatchers()
                .and()
                .authorizeRequests()             
                .antMatchers("/api/usuarios/**" ).permitAll()
                .antMatchers("/api/admin/usuarios/**" ).authenticated()
                .antMatchers("/api/login/perfil/**" ).authenticated()
                .antMatchers("/api/bancos/**" ).authenticated()
                .antMatchers("/api/bomberos/**" ).authenticated()
                .antMatchers("/api/comisarias/**" ).authenticated()
                .antMatchers("/api/farmacias/**" ).authenticated()
                .antMatchers("/api/tiendas/**" ).authenticated()
                .antMatchers("/api/eventos/**" ).authenticated()
                .antMatchers("/api/incidencias/**" ).authenticated()
                .antMatchers("/api/contactos/**" ).authenticated()
                .antMatchers("/api/tokens/anular/**" ).permitAll()
                .antMatchers("/api/tokens/**" ).authenticated();
    }

}