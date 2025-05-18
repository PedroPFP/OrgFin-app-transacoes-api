package github.pedropfp.OrgFin_app_transacoes_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping

@SpringBootApplication
class OrgFinAppTransacoesApiApplication

fun main(args: Array<String>) {
	runApplication<OrgFinAppTransacoesApiApplication>(*args)
}