package github.pedropfp.OrgFin_app_transacoes_api.config.security.local

import github.pedropfp.OrgFin_app_transacoes_api.config.security.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@Profile("local")
class LocalSecurityConfiguration (private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint, private val jwtTokenProvider:JwtTokenProvider) {

    val jwtFilter: JwtAuthenticationFilter = JwtAuthenticationFilter(jwtTokenProvider)

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/actuator/health").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, BearerTokenAuthenticationFilter::class.java)
            .build()
    }

}