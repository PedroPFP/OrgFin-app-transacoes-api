package github.pedropfp.OrgFin_app_transacoes_api.model.dto

import org.springframework.http.HttpStatus

data class ErroResposta(val status: Int, val mensagem:String, val erros:List<ErroCampo>) {

    companion object {
        fun respostaPadrao(mensagem: String):ErroResposta{
            return ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, listOf())
        }

        fun conflito(mensagem: String):ErroResposta{
           return ErroResposta(HttpStatus.CONFLICT.value(),mensagem, listOf())
        }
    }
}
