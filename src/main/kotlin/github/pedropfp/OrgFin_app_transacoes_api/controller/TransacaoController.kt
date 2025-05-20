package github.pedropfp.OrgFin_app_transacoes_api.controller

import github.pedropfp.OrgFin_app_transacoes_api.model.Transacao
import github.pedropfp.OrgFin_app_transacoes_api.model.dto.TransacaoDTO
import github.pedropfp.OrgFin_app_transacoes_api.model.erro.ErroCampo
import github.pedropfp.OrgFin_app_transacoes_api.model.erro.ErroResposta
import github.pedropfp.OrgFin_app_transacoes_api.model.mapper.TransacaoMapper
import github.pedropfp.OrgFin_app_transacoes_api.service.TransacaoService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.time.LocalDate
import java.util.UUID

@RestController
@RequestMapping("v1/transacao")
class TransacaoController {

    @Autowired
    lateinit var transacaoService: TransacaoService

    @Autowired
    lateinit var transacaoMapper: TransacaoMapper

    @PostMapping
    fun salvarTransacao(@RequestBody @Valid transacaoDto: TransacaoDTO): ResponseEntity<Void> {
        val transacao: Transacao = transacaoMapper.transacaoDTOToTransacao(transacaoDto)

        transacao.idUsuario = SecurityContextHolder.getContext().authentication.name

        transacao.idTransacao = UUID.randomUUID()
        val result = transacaoService.salvar(transacao)

        val uri: URI =
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}/{idUsuario}")
                .buildAndExpand(result.idTransacao, result.idUsuario)
                .toUri()

        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/{idTransacao}")
    fun buscarTransacao(
        @PathVariable("idTransacao") idTransacao: UUID
    ): ResponseEntity<out Any> {
        var result = transacaoService.buscarPorIdTransacaoEIdUsuario(idTransacao,SecurityContextHolder.getContext().authentication.name)

        var casoNotFound = verificarSeFoiEncontradoERetornarNotFound(
            result,
            listOf(
                ErroCampo(campo = "id_transacao", erro = "Verifique se o id da transação está correto.")
            ),
            "Nenhuma transação encontrada."
        )
            if (casoNotFound!=null)
                return ResponseEntity.status(casoNotFound.status).body(casoNotFound)

        var response = transacaoMapper.transacaoToTransacaoDTO(result!!)
        return ResponseEntity.ok(response)

    }

    @GetMapping()
    fun buscarTransacoes(): ResponseEntity<out Any> {
        var dtos = ArrayList<TransacaoDTO>()
        val result = transacaoService.buscarTransacoesPorUsuario(SecurityContextHolder.getContext().authentication.name);

        var casoNotFound = verificarSeFoiEncontradoERetornarNotFound(
            result,
            listOf(),
            "Nenhuma transação encontrada."
        )
        if (casoNotFound!=null)
            return ResponseEntity.status(casoNotFound.status).body(casoNotFound)

        result.forEach { it ->
            dtos.add(transacaoMapper.transacaoToTransacaoDTO(it!!))
        }
        return ResponseEntity.ok(dtos)
    }

    @DeleteMapping("/{idTransacao}")
    fun deletarTransacao(
        @PathVariable("idTransacao") idTransacao: UUID
    ): ResponseEntity<out Any> {
        val result = transacaoService.deletarTransacaoPorIdTransacaoEIdUsuario(idTransacao,SecurityContextHolder.getContext().authentication.name)

        var casoNotFound = verificarSeFoiEncontradoERetornarNotFound(
            result,
            listOf(
                ErroCampo("id_transacao", "Verifique se o id da transação está correto.")
            ),
            "Nenhuma transação encontrada para ser deletada."
        )
        if (casoNotFound!=null)
            return ResponseEntity.status(casoNotFound.status).body(casoNotFound)

        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun alterarTransacao(@RequestBody @Valid transacaoDto: TransacaoDTO, @PathVariable("id") id: UUID): ResponseEntity<out Any> {
        var transacao = transacaoMapper.transacaoDTOToTransacao(transacaoDto)
        transacao.idTransacao = id
        transacao.idUsuario = SecurityContextHolder.getContext().authentication.name

        var transacaoCadastrada =
            transacaoService.buscarPorIdTransacaoEIdUsuario(transacao.idTransacao!!, transacao.idUsuario)

        var casoNotFound = verificarSeFoiEncontradoERetornarNotFound(
            transacaoCadastrada,
            listOf(
                ErroCampo("id_transacao", "Verifique se o id da transação está correto.")
            ),
            "Nenhuma transação encontrada."
        )
        if (casoNotFound!=null)
            return ResponseEntity.status(casoNotFound.status).body(casoNotFound)

        transacao.dataUltimaAtualizacao = LocalDate.now()

        transacaoService.atualizarTransacao(transacao)

        return ResponseEntity.noContent().build()
    }

    private fun verificarSeFoiEncontradoERetornarNotFound(
        result: Transacao?,
        erros: List<ErroCampo>,
        mensagem: String
    ): ErroResposta? {
        if (result == null) {
            val naoEncontrado = ErroResposta.naoEncontrado(mensagem, erros)
            return naoEncontrado
        }
        return null;
    }

    private fun verificarSeFoiEncontradoERetornarNotFound(
        result: List<Transacao?>,
        erros: List<ErroCampo>,
        mensagem: String
    ): ErroResposta? {
        if (result.isEmpty()) {
            val naoEncontrado = ErroResposta.naoEncontrado(mensagem, erros)
            return naoEncontrado
        }
        return null;
    }

}