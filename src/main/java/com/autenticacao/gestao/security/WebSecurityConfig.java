package com.autenticacao.gestao.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableGlobalMethodSecurity(
    //securedEnabled =  true,
    //jsr250Enabled = true,
    prePostEnabled = true
)

public class WebSecurityConfig {
    //@Autowired
    //UserDetailsServiceImpl userDetailsService;
  
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
   
	//private JWTAuthenticationFilter jwtAuthenticationFilter;



    @Bean
  public JWTAuthenticationFilter authenticationJwtTokenFilter() {
    return new JWTAuthenticationFilter();
  }

//  @Override
//  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//  }
  
    
    //metodo par??o pra configurar o nosso CustomUserDetailsService com nosso metodo de codificar a senha
  @Bean
  public DaoAuthenticationProvider authenticationProvider() throws Exception {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(customUserDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

//  @Bean
//  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }
  
  //metodo padrao ele ?? obrigatorio para trabalhar com aautentica????o no login
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) 
		  throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.cors().and().csrf().disable()
//      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//      .antMatchers("/api/test/**").permitAll()
//      .anyRequest().authenticated();
//
//    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//  }
  
  
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	  
	  //parte padr??o do m??todo
    http.cors().and().csrf().disable()
        .exceptionHandling()//.authenticationEntryPoint(authEntryPointJwt)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        
        /*
         * Daqui para baixo ?? onde vou fazer as minhas valida????es 
         * aqui vou informar quais rotas n??o precisa de autentica????o
         */
      
        .authorizeRequests()
        .antMatchers("/api/**").permitAll()
        .antMatchers("/api/login/**").permitAll()
        .antMatchers("/api/usuario/**").permitAll()
        .antMatchers("/api/test/**").permitAll()
        .anyRequest().authenticated();
    
    http.authenticationProvider(authenticationProvider());


    /*
     * aqui eu informo que antes de qualquer requisi????o http,
     *  o sistema vai usar os meus filtros pre definidos
     */
 http.addFilterBefore(authenticationJwtTokenFilter(), 
    		UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
  }

  }
  
    /*
     * Metodo que devolve a estamcia do objeto que sabe devolver o o nosso padr??o de
     * codifica????o
     * isso n??o tem nada aver com o JWT
     * aqui ser?? usado para codifica????o da senha do usuario gerando um hsh
     * ele vem da classe Usuario service
     */

   // @Bean
   

    /*
     * cors -> ?? para acessar o back-end sem precisar do https no front-end, sendo
     * assim vc pode acessar por http ou https
     * csrf esta desabilitado
     * exceptionHandling em caso de erro vai lan??ar ecess??o
     */


    // metodo padr??o para configurar o nosso custom com o nosso metodo de
    // codificador senha.
 

   


