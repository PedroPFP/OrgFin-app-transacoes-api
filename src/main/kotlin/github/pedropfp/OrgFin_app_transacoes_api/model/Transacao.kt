package github.pedropfp.OrgFin_app_transacoes_api.model

import github.pedropfp.OrgFin_app_transacoes_api.model.dto.TransacaoDTO
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey
import java.time.LocalDate
import java.util.UUID

@DynamoDbBean
@AllArgsConstructor
@NoArgsConstructor
class Transacao() {
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("id_transacao")
    var idTransacao: UUID = UUID.randomUUID();

    @get:DynamoDbAttribute("id_usuario")
    @get:DynamoDbSortKey
    lateinit var idUsuario: UUID;

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

    fun mapearParaDto(): TransacaoDTO {
        var dto = TransacaoDTO(
            this.idTransacao,
            this.idUsuario,
            this.tipo,
            this.valor,
            this.nome,
            this.descricao,
            this.tagGenero
        )
        return dto
    }

}
