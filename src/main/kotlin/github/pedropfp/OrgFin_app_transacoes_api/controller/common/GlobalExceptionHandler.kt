package github.pedropfp.OrgFin_app_transacoes_api.controller.common

import github.pedropfp.OrgFin_app_transacoes_api.model.erro.ErroCampo
import github.pedropfp.OrgFin_app_transacoes_api.model.erro.ErroResposta
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import software.amazon.awssdk.services.cognitoidentityprovider.model.UserNotFoundException
import java.util.stream.Collectors

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErroResposta{
    val erros = e.getFieldErrors()
        var listaErros = erros.stream().map {fe -> ErroCampo(fe.field, fe.defaultMessage.toString())}.collect(Collectors.toList());
        return ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(),"Erro de validação", listaErros );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleCognitoIdentityProviderException(e: UserNotFoundException):ErroResposta {
        var listaErros = listOf(ErroCampo("id_usuario",e.message.toString()))
        return ErroResposta(HttpStatus.UNAUTHORIZED.value(),"Usuário inexistente no banco de dados.", listaErros );
    }
}