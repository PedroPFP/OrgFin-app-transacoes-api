package github.pedropfp.OrgFin_app_transacoes_api.model

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey
import java.time.LocalDate
import java.util.UUID

@DynamoDbBean
class Transacao() {
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("id_transacao")
    var idTransacao: UUID? = UUID.randomUUID();

    @get:DynamoDbAttribute("id_usuario")
    @get:DynamoDbSortKey
    var idUsuario: String = "";

    @get:DynamoDbAttribute("tp_transacao")
    lateinit var tipo: TipoTransacao;

    @get:DynamoDbAttribute("vl_transacao")
    @NotNull
    @NotEmpty
    var valor: Double = 0.0;

    @get:DynamoDbAttribute("dt_transacao")
    var data: LocalDate = LocalDate.now()

    @get:DynamoDbAttribute("nm_transacao")
    lateinit var nome: String

    @get:DynamoDbAttribute("desc_transacao")
    var descricao: String? = null

    @get:DynamoDbAttribute("tag_genero_transacao")
    var tagGenero: String? = null

    @get:DynamoDbAttribute("dt_criacao")
    var dataCriacao: LocalDate = LocalDate.now()

    @get:DynamoDbAttribute("dt_atualizacao")
    var dataUltimaAtualizacao: LocalDate = LocalDate.now()

}
