package com.springboot.app;

import com.springboot.app.auth.handler.LoginSuccesHandler;
import com.springboot.app.models.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private LoginSuccesHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/css/**", "/js/**","/images/**", "/listar/colaboradores").permitAll()
                /*.antMatchers("/ver/**").hasAnyRole("USER") */
                /*.antMatchers("/uploads/**").hasAnyRole("USER") */
                /*.antMatchers("/form/**").hasAnyRole("ADMIN") */
                /*.antMatchers("/eliminar/**").hasAnyRole("ADMIN") */
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .successHandler(successHandler)
                        .loginPage("/login")
                    .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

        /*
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery("select username, password, enabled from users where username=?")
                .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id = u.id) where u.username=?");
         */

        /*
        PasswordEncoder encoder = this.passwordEncoder;
        User.UserBuilder user = User.builder().passwordEncoder(password ->{
            return encoder.encode(password);
        });

        builder.inMemoryAuthentication()
                .withUser(user.username("Leslie").password("060899").roles("ADMIN", "USER"))
                .withUser(user.username("SUPERVI01").password("12345").roles("USER"))
                .withUser(user.username("SUPERVI02").password("12345").roles("USER"))
                .withUser(user.username("SUPERVI03").password("12345").roles("USER"))
                .withUser(user.username("SUPERVI04").password("12345").roles("USER"))
                .withUser(user.username("SUPERVI05").password("12345").roles("USER"))
        ;
         */

    }
}
