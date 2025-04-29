package github.pedropfp.OrgFin_app_transacoes_api.model.dto

import github.pedropfp.OrgFin_app_transacoes_api.model.TipoTransacao
import github.pedropfp.OrgFin_app_transacoes_api.model.Transacao
import java.util.UUID

data class TransacaoDTO(
    val idTransacao: UUID?,
    val idUsuario: UUID,
    val tipo: TipoTransacao,
    val valor: Double,
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