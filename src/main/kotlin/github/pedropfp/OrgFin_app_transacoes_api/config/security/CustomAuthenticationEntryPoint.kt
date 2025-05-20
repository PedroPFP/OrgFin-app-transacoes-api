package github.pedropfp.OrgFin_app_transacoes_api.config.security

import github.pedropfp.OrgFin_app_transacoes_api.model.erro.ErroCampo
import github.pedropfp.OrgFin_app_transacoes_api.model.erro.ErroResposta
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response!!.status = HttpStatus.UNAUTHORIZED.value();
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        val erroResposta: ErroResposta = ErroResposta(HttpStatus.UNAUTHORIZED.value(), "Token de acesso inválido.", listOf(
            ErroCampo("header.Authorization", authException!!.message.toString())
        ))

        response.writer.write(erroResposta.toString())


    }


}