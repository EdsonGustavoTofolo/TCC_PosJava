package br.edu.utfpr.pb.projetojsp.framework;

import br.edu.utfpr.pb.projetojsp.repository.UsuarioRepository;
import br.edu.utfpr.pb.projetojsp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Edson on 28/05/2017.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().accessDeniedPage("/erro403").and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/index/")
                .failureUrl("/login?error=bad_credentials").permitAll()
                .and().authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/index/**").hasRole("USER")
                .antMatchers("/categoria/**").hasRole("USER")
                .antMatchers("/produto/**").hasRole("ADMIN")
                .antMatchers("/carros/**").hasAnyRole("USER,ADMIN,GERENTE")
                .antMatchers("/**").hasRole("USER");
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new UsuarioService(usuarioRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/resources/**");
    }
}
