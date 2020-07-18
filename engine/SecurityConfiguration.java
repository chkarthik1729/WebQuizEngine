package engine;

import engine.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired private UserService userService;

    public SecurityConfiguration() {
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/actuator/shutdown", "/api/register");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/register", "/actuator/shutdown").permitAll()
        .antMatchers("/**").authenticated()
        .and().httpBasic();

        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .userDetailsService(userService)
        .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}