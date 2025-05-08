package github.pedropfp.OrgFin_app_transacoes_api.service

import github.pedropfp.OrgFin_app_transacoes_api.model.Transacao
import io.awspring.cloud.dynamodb.DynamoDbTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import software.amazon.awssdk.enhanced.dynamodb.Expression
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.UUID

@Service
class TransacaoService {

    @Autowired
    lateinit var dynamoDbTemplate: DynamoDbTemplate

    fun salvar(transacao: Transacao): Transacao {
        return dynamoDbTemplate.save(transacao)
    }

    fun buscarPorIdTransacaoEIdUsuario(idTransacao:UUID, idUsuario:String): Transacao? {
        return dynamoDbTemplate.load(
            Key.builder().partitionValue(idTransacao.toString()).sortValue(idUsuario.toString())
                .build(), Transacao().javaClass
        )
    }

    fun buscarTransacoesPorUsuario(idUsuario: String): List<Transacao?> {
        var expression: Expression =
            Expression.builder().expression("#idUsuario = :idUsuario")
                .putExpressionName("#idUsuario","id_usuario")
                .putExpressionValue(":idUsuario", AttributeValue.builder().s(idUsuario.toString()).build()).build()

        var scanRequest: ScanEnhancedRequest = ScanEnhancedRequest.builder().filterExpression(expression).build()
        val pages = dynamoDbTemplate.scan(scanRequest, Transacao().javaClass)
        var transacoes: ArrayList<Transacao> = ArrayList<Transacao>()
        pages.items().stream().forEach(transacoes::add)

        return transacoes
    }

    fun deletarTransacaoPorIdTransacaoEIdUsuario(idTransacao:UUID, idUsuario:String): Transacao? {
        return dynamoDbTemplate.delete(
            Key.builder().partitionValue(idTransacao.toString()).sortValue(idUsuario.toString())
                .build(), Transacao().javaClass
        )
    }

    fun atualizarTransacao(transacao:Transacao):Transacao? {
        return dynamoDbTemplate.update(transacao)
    }

}