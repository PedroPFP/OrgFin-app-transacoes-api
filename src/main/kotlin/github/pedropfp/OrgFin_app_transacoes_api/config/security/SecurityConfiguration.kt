package github.pedropfp.OrgFin_app_transacoes_api.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .oauth2ResourceServer {
                it.jwt {
                    it.jwkSetUri("https://cognito-idp.sa-east-1.amazonaws.com/sa-east-1_v4yhpN41u/.well-known/jwks.json")
                }
                it.authenticationEntryPoint(customAuthenticationEntryPoint)
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/actuator/health").permitAll()
                    .anyRequest().authenticated()
            }
            .build()
    }

}