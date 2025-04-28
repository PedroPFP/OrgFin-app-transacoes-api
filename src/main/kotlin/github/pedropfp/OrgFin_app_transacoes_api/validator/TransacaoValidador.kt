package github.pedropfp.OrgFin_app_transacoes_api.validator

import io.awspring.cloud.dynamodb.DynamoDbTemplate
import org.springframework.beans.factory.annotation.Autowired

class TransacaoValidador {

    @Autowired
    lateinit var dynamoDbTemplate: DynamoDbTemplate

}