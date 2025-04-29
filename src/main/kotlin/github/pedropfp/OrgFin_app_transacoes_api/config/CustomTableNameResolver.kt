package github.pedropfp.OrgFin_app_transacoes_api.config

import github.pedropfp.OrgFin_app_transacoes_api.model.Transacao
import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomTableNameResolver: DynamoDbTableNameResolver {

    @Value("\${spring.cloud.aws.dynamodb.table-prefix}")
    lateinit var prefixo:String

    @Value("\${spring.cloud.aws.dynamodb.table-sufix}")
    lateinit var sufixo:String

    @Override
    override fun <T : Any?> resolve(clazz: Class<T>): String {
        var nomeTabela = ""
        if(clazz == Transacao().javaClass)
            nomeTabela = "transacoes";
        else
            nomeTabela = clazz.simpleName.lowercase(Locale.getDefault())

        return "${prefixo}${nomeTabela}${sufixo}"
    }
}