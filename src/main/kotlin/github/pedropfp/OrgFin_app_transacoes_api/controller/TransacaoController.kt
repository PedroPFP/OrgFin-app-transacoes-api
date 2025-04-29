package github.pedropfp.OrgFin_app_transacoes_api.controller

import github.pedropfp.OrgFin_app_transacoes_api.model.Transacao
import github.pedropfp.OrgFin_app_transacoes_api.model.dto.TransacaoDTO
import github.pedropfp.OrgFin_app_transacoes_api.model.erro.ErroCampo
import github.pedropfp.OrgFin_app_transacoes_api.model.erro.ErroResposta
import github.pedropfp.OrgFin_app_transacoes_api.service.TransacaoService
import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
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
import java.util.UUID
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RestController
@RequestMapping("v1/transacao")
@AllArgsConstructor
class TransacaoController {

    @Autowired
    lateinit var transacaoService: TransacaoService

    @PostMapping
    fun salvarTransacao(@RequestBody transacaoDto: TransacaoDTO): ResponseEntity<Void> {

        val transacao: Transacao = transacaoDto.mapToTransacao()
        val result = transacaoService.salvar(transacao)

        val uri: URI =
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}/{idUsuario}")
                .buildAndExpand(result.idTransacao, result.idUsuario)
                .toUri()

        return ResponseEntity.created(uri).build()
    }

    @GetMapping("/{idTransacao}/{idUsuario}")
    fun buscarTransacao(
        @PathVariable("idTransacao") idTransacao: UUID,
        @PathVariable("idUsuario") idUsuario: UUID
    ): ResponseEntity<out Any> {

        var result = transacaoService.buscarPorIdTransacaoEIdUsuario(idTransacao, idUsuario)

        var casoNotFound = verificarSeFoiEncontradoERetornarNotFound(
            result,
            listOf(
                ErroCampo(campo = "id_transacao", erro = "Verifique se o id da transação está correto."),
                ErroCampo(campo = "id_usuario", erro = "Verifique se o id do usuário está correto.")
            ),
            "Nenhuma transação encontrada."
        )
            if (casoNotFound!=null)
                return ResponseEntity.status(casoNotFound!!.status).body(casoNotFound)

        var response = result?.mapearParaDto()
        return ResponseEntity.ok(response)

    }

    @GetMapping("/{id}")
    fun buscarTransacoes(@PathVariable("id") idUsuario: UUID): ResponseEntity<out Any> {
        var dtos = ArrayList<TransacaoDTO>()
        val result = transacaoService.buscarTransacoesPorUsuario(idUsuario);

        var casoNotFound = verificarSeFoiEncontradoERetornarNotFound(
            result,
            listOf(
                ErroCampo(campo = "id_usuario", erro="Verifique se o id do usuário está correto.")
            ),
            "Nenhuma transação encontrada."
        )
        if (casoNotFound!=null)
            return ResponseEntity.status(casoNotFound!!.status).body(casoNotFound)

        result.forEach { it ->
            dtos.add(it!!.mapearParaDto())
        }
        return ResponseEntity.ok(dtos)
    }

    @DeleteMapping("/{idTransacao}/{idUsuario}")
    fun deletarTransacao(
        @PathVariable("idTransacao") idTransacao: UUID,
        @PathVariable("idUsuario") idUsuario: UUID
    ): ResponseEntity<out Any> {

        val result = transacaoService.deletarTransacaoPorIdTransacaoEIdUsuario(idTransacao, idUsuario)

        var casoNotFound = verificarSeFoiEncontradoERetornarNotFound(
            result,
            listOf(
                ErroCampo("id_transacao", "Verifique se o id da transação está correto."),
                ErroCampo("id_usuario", "Verifique se o id do usuário está correto.")
            ),
            "Nenhuma transação encontrada para ser deletada."
        )
        if (casoNotFound!=null)
            return ResponseEntity.status(casoNotFound!!.status).body(casoNotFound)

        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun alterarTransacao(@RequestBody transacaoDto: TransacaoDTO, @PathVariable("id") id: UUID): ResponseEntity<out Any> {

        var transacao = transacaoDto.mapToTransacao()
        transacao.idTransacao = id

        var transacaoCadastrada =
            transacaoService.buscarPorIdTransacaoEIdUsuario(transacao.idTransacao, transacao.idUsuario)

        var casoNotFound = verificarSeFoiEncontradoERetornarNotFound(
            transacaoCadastrada,
            listOf(
                ErroCampo("id_transacao", "Verifique se o id da transação está correto."),
                ErroCampo("id_usuario", "Verifique se o id do usuário está correto.")
            ),
            "Nenhuma transação encontrada."
        )
        if (casoNotFound!=null)
            return ResponseEntity.status(casoNotFound!!.status).body(casoNotFound)

        var result = transacaoService.atualizarTransacao(transacao)

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