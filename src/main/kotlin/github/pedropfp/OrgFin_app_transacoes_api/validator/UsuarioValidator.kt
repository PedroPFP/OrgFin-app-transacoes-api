package github.pedropfp.OrgFin_app_transacoes_api.validator

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminGetUserRequest

@Component
class UsuarioValidator {

    @Value("\${spring.cloud.aws.regions.static}")
    lateinit var regiao: String

    @Value("\${spring.cloud.aws.cognito.user-pool-id}")
    lateinit var userPoolId: String

    fun validarUsuario(userName: String) {

        val client: CognitoIdentityProviderClient =
            CognitoIdentityProviderClient.builder().region(Region.of(regiao)).build()

        val userRequest = AdminGetUserRequest.builder()
            .username(userName)
            .userPoolId(userPoolId)
            .build()

        client.adminGetUser(userRequest)

    }


}