package br.com.rchlo.store.config.Security;

import br.com.rchlo.store.config.Security.service.AuthenticationService;
import br.com.rchlo.store.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {


    private AuthenticationService autenticacaoService;

    public SecurityConfigurations(AuthenticationService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    //Config de autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // desabilita CSRF
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole(Role.ADMIN.toString())
                .and().httpBasic();// habilita Basic Authentication

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    /*public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }*/
}
