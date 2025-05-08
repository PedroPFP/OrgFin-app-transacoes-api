package github.pedropfp.OrgFin_app_transacoes_api.model.mapper

import github.pedropfp.OrgFin_app_transacoes_api.model.Transacao
import github.pedropfp.OrgFin_app_transacoes_api.model.dto.TransacaoDTO
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface TransacaoMapper {

    fun transacaoToTransacaoDTO(transacao: Transacao):TransacaoDTO
    fun transacaoDTOToTransacao(transacaoDTO: TransacaoDTO): Transacao


}