package github.pedropfp.OrgFin_app_transacoes_api.config.security.local

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenProvider {
    fun validateToken(token: String): Boolean {
        val parts = token.split(".")
        if (parts.size != 3) return false

        try {
            val decoder = Base64.getUrlDecoder()
            val headerJson = String(decoder.decode(parts[0]))
            val payloadJson = String(decoder.decode(parts[1]))

            val mapper = jacksonObjectMapper()
            val typeRef = object : TypeReference<Map<String, Any>>() {}
            val header: Map<String, Any> = mapper.readValue(headerJson, typeRef)
            val payload: Map<String, Any> = mapper.readValue(payloadJson, typeRef)

            return true // estrutura válida
        } catch (e: Exception) {
            return false // base64 inválido ou JSON inválido
        }

    }
}
