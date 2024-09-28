package com.scm.scm20.config;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.scm20.services.impl.SecurityCustomUserDetailService;





@Configuration
public class SecurityConfig {
  
    //user create and login using java code with in memeory service
    //@Bean
    //public UserDetailsService userDetailsService(){
     // UserDetails user1=User
      //.withUsername("admin123")
      //.password("admin123")
      //.roles("ADMIN","USER")
     // .build();
    // UserDetails user2=User
        //.withUsername("user123")
        //.password("password")
        //.roles("ADMIN","USER")
        //.build();
        //var inMemoryUserDetailsManager=new InMemoryUserDetailsManager(user1,user2);
        //return inMemoryUserDetailsManager;
    //}
    @Autowired
    private SecurityCustomUserDetailService userDetailService;
    @Autowired
    private OAuthAuthenticationSuccessHandler handler;
    //configuration of  authentication provider spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
      //user detail service ka object
      daoAuthenticationProvider.setUserDetailsService(userDetailService);

      //password encoder ka object
      daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
      return daoAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
      //configuration
      //url configuration is donw ehich url is public or private
      httpSecurity.authorizeHttpRequests(authorize -> {
        //authorize.requestMatchers("/home","/register","/services").permitAll();
        authorize.requestMatchers("/user/**").authenticated();
        authorize.anyRequest().permitAll();
      });
      //form  default login:
      //agar hame kuch bhi change karna hua to hm yha ayenge : form login related



      httpSecurity.formLogin(formLogin->{
        //
        formLogin.loginPage("/login");
        formLogin.loginProcessingUrl("/authenticate");
        //formLogin.successForwardUrl("/user/profile");
        formLogin.successForwardUrl("/user/profile");
        //formLogin.failureForwardUrl("/login?error=true");
        //formLogin.defaultSuccessUrl("/home");
        formLogin.usernameParameter("email");
        formLogin.passwordParameter("password");
        //formLogin.failureHandler( new AuthenticationFailureHandler() {

          //@Override
         // public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
           //   AuthenticationException exception) throws IOException, ServletException {
            
           // throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
          //}
          
        
          
       // });
        

        //formLogin.successHandler(new AuthenticationSuccessHandler() {

         // @Override
         // public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
             // Authentication authentication) throws IOException, ServletException {
            
            //throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
          //}
          
        //});

        




      });
      httpSecurity.csrf(AbstractHttpConfigurer:: disable);
      //httpSecurity.logout(logoutForm->{
       // logoutForm.logoutUrl("/do-logout");
       // logoutForm.logoutSuccessUrl("/login?logout=true");

     // });


      //oauth2 configuration


     httpSecurity.oauth2Login(oauth ->{
       oauth.loginPage("/login");
       oauth.successHandler(handler);

       
        
       
    });
    httpSecurity.logout(logoutForm->{
      logoutForm.logoutUrl("/do-logout");
      logoutForm.logoutSuccessUrl("/login?logout=true");

    });
     //httpSecurity.oauth2Login(Customizer.withDefaults());
     
      return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }
    
   
}
