package github.pedropfp.OrgFin_app_transacoes_api.controller

import github.pedropfp.OrgFin_app_transacoes_api.model.Transacao
import github.pedropfp.OrgFin_app_transacoes_api.model.dto.TransacaoDTO
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
    fun buscarTransacao(@PathVariable("idTransacao") idTransacao:UUID, @PathVariable("idUsuario") idUsuario:UUID):ResponseEntity<TransacaoDTO?>{
        var result = transacaoService.buscarPorIdTransacaoEIdUsuario(idTransacao, idUsuario)
            ?: return ResponseEntity.notFound().build()
        var response = result.mapearParaDto()
        return ResponseEntity.ok(response)

    }

    @GetMapping("/{id}")
    fun buscarTransacoes(@PathVariable("id") idUsuario: UUID):ResponseEntity<List<TransacaoDTO?>>{
        var dtos = ArrayList<TransacaoDTO>()
        val result = transacaoService.buscarTransacoesPorUsuario(idUsuario);
        if(result.isEmpty())
            return ResponseEntity.notFound().build()
        result.forEach{ it ->
            dtos.add(it!!.mapearParaDto())
        }
        return ResponseEntity.ok(dtos)
    }

    @DeleteMapping("/{idTransacao}/{idUsuario}")
    fun deletarTransacao(@PathVariable("idTransacao") idTransacao:UUID, @PathVariable("idUsuario") idUsuario:UUID):ResponseEntity<Void> {
        val result = transacaoService.deletarTransacaoPorIdTransacaoEIdUsuario(idTransacao, idUsuario)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun alterarTransacao(@RequestBody transacaoDto: TransacaoDTO, @PathVariable("id") id:UUID):ResponseEntity<Void> {
        var transacao = transacaoDto.mapToTransacao()
        transacao.idTransacao = id
        var transacaoCadastrada = transacaoService.buscarPorIdTransacaoEIdUsuario(transacao.idTransacao, transacao.idUsuario)
            ?: return ResponseEntity.notFound().build()
        var result = transacaoService.atualizarTransacao(transacao)
        ?: return ResponseEntity.notFound().build()

        return ResponseEntity.noContent().build()
    }

}