package com.hypnotoid.MySite;

import com.hypnotoid.MySite.repositories.UserRepository;
import com.hypnotoid.MySite.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan
public class WebSecurityConfig /* extends WebSecurityConfigurerAdapter */ {
    // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService(userRepository);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //Some magic stuff
                .csrf().disable()
                //require authorisation
                .authorizeRequests()
                //add required roles here
                //Registration related
                .antMatchers("/register").hasAnyAuthority(Roles.ANONYMOUS.getName())
                .antMatchers("/registerSuccess").hasAnyAuthority(Roles.ANONYMOUS.getName())
                .antMatchers("/registerProcess").hasAnyAuthority(Roles.ANONYMOUS.getName())
                //Product list related
                .antMatchers("/productsList").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/productsListAdd20").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/productsListSave").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/productsListDelete").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/productsListFind").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/productsListSaveProcess").hasAnyAuthority(Roles.EDITOR.getName())
                //User Products list related
                .antMatchers("/userProductsList").hasAnyAuthority(Roles.USER.getName())
                .antMatchers("/userProductsListFind").hasAnyAuthority(Roles.USER.getName())

                //User list related
                .antMatchers("/usersList").hasAnyAuthority(Roles.EDITOR.getName())
                //.antMatchers("/usersListAdd").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/usersListDelete").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/usersListSaveProcess").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/usersListSave").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/usersListEdit").hasAnyAuthority(Roles.EDITOR.getName())
                //Admin page
                .antMatchers("/admin").hasAnyAuthority(Roles.ADMIN.getName())
                .antMatchers("/adminEncrypt").hasAnyAuthority(Roles.ADMIN.getName())
                //Cart page
                .antMatchers("/cart").hasAnyAuthority(Roles.USER.getName())
                .antMatchers("/cartOrder").hasAnyAuthority(Roles.USER.getName())
                .antMatchers("/cartDelete").hasAnyAuthority(Roles.USER.getName())
                .antMatchers("/cartEdit").hasAnyAuthority(Roles.USER.getName())
                .antMatchers("/buy").hasAnyAuthority(Roles.USER.getName())
                //User page
                .antMatchers("/user").hasAnyAuthority(Roles.USER.getName())
                .antMatchers("/userDelete").hasAnyAuthority(Roles.USER.getName())
                .antMatchers("/userEdit").hasAnyAuthority(Roles.USER.getName())
                //Orders pages
                .antMatchers("/orders").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/orderDelete").hasAnyAuthority(Roles.EDITOR.getName())
                .antMatchers("/myOrders").hasAnyAuthority(Roles.USER.getName())
                .antMatchers("/usersListSave").hasAnyAuthority(Roles.EDITOR.getName())
                //Public pages
                .antMatchers("/").permitAll().antMatchers("/error").permitAll()
                .antMatchers("/styles.css").permitAll()
                //blacklist others
                .anyRequest().hasAnyAuthority(Roles.ADMIN.getName())
                //and
                .and().formLogin().defaultSuccessUrl("/").permitAll()
                //and
                .and().logout().permitAll().logoutSuccessUrl("/")
                //and
                .and().exceptionHandling().accessDeniedPage("/403")

        ;
        return http.build();
    }


    //@Autowired
    public WebSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
