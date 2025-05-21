package github.pedropfp.OrgFin_app_transacoes_api.config.security.local

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthenticationFilter(private val jwtTokenProvider:JwtTokenProvider) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getJwtFromRequest(request)

        if (!token.isNullOrBlank() && jwtTokenProvider.validateToken(token)){
            val authentication = UsernamePasswordAuthenticationToken("userLocal",null, listOf())
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)

    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank()) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}