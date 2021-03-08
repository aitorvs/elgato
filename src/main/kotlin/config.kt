import com.natpryce.konfig.*
import java.io.File

private val elgato_host = Key("elgato.host", stringType)
private val elgato_port = Key("elgato.port", intType)

private val config = EnvironmentVariables() overriding
    ConfigurationProperties.fromFile(File("/usr/local/etc/elgato.properties"))

class ElGato {
  companion object {
    val address = config[elgato_host]
    val port = config[elgato_port]
  }
}