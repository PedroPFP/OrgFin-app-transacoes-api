package github.pedropfp.OrgFin_app_transacoes_api.model.dto

import github.pedropfp.OrgFin_app_transacoes_api.model.TipoTransacao
import github.pedropfp.OrgFin_app_transacoes_api.model.Transacao
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

import java.util.UUID

data class TransacaoDTO(
    val idTransacao: UUID?,

    @field:NotNull(message = "Campo obrigatório")
    val idUsuario: UUID,
    @field:NotNull(message = "Campo obrigatório")
    val tipo: TipoTransacao,
    @field:NotNull(message = "Campo obrigatório")
    @field:Min(0)
    val valor: Double,
    @field:NotBlank(message = "Campo obrigatório")
    val nome: String,
    val descricao: String?,
    val tagGenero: String?
){
    fun mapToTransacao(): Transacao {
        var transacao: Transacao = Transacao()
        transacao.tipo = this.tipo
        transacao.idUsuario = this.idUsuario
        transacao.valor = this.valor
        transacao.nome = this.nome
        transacao.descricao = this.descricao
        transacao.tagGenero = this.tagGenero
        return transacao
    }
}