import java.sql.Date
import java.util.UUID
import kotlin.Double

data class Transacao(
        val id: UUID,
        val idUsuario: UUID,
        val tipo: String,
        val data: Date,
        val valor: Double,
        val titulo: String,
        val descricao: String
) {}