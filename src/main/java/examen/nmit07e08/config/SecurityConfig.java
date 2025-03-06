package examen.nmit07e08.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPointJwt))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Un usuario no autenticado, sólo podrá iniciar sesión/registrarse y ver la los movimientos y cuentas incluso la infomación de uno en específico  
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cuentas/**" ,"/movimientos/**").permitAll()
                        
                        // Los usuarios podrán crear cuentas - pasarán a ser titualares de esta 
                        .requestMatchers(HttpMethod.POST, "/cuentas/").hasAnyRole("USUARIO", "TITULAR", "ADMIN")
                        
                        // Solo los titulares de una cuenta podrán eliminar su propia cuenta y los admin ambas independientemente si son titulares de esa cuenta
                        .requestMatchers(HttpMethod.DELETE, "/cuentas/**").hasAnyRole("TITULAR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/cuentas/**").hasAnyRole("TITULAR", "ADMIN")

                        // Los titulares podrán CRU de movimientos de sus propias cuentas, pero los administradores  CRUD de todas las cuentas
                        .requestMatchers(HttpMethod.POST, "/movimientos/**").hasAnyRole("TITULAR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/movimientos/**").hasAnyRole("TITULAR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/movimientos/**").hasAnyRole( "ADMIN")

                        // Gestión de usuarios: El administrador es el único que puede hacer CRUD de usuarios
                        .requestMatchers( "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers("/h2-console/**").permitAll()


                        
                ); 
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
        http.cors(Customizer.withDefaults());
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); 
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}