package github.pedropfp.OrgFin_app_transacoes_api.model.mapper

import github.pedropfp.OrgFin_app_transacoes_api.model.Transacao
import github.pedropfp.OrgFin_app_transacoes_api.model.dto.TransacaoDTO
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

@Mapper
interface TransacaoMapper {

    fun TransacaoToTransacaoDTO(transacao: Transacao):TransacaoDTO
    fun TransacaoDTOToTransacao(transacaoDTO: TransacaoDTO): Transacao


}