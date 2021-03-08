import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.int
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
  val version = NoOpCliktCommand::class.java.`package`.implementationVersion
  NoOpCliktCommand(name = "elgato")
    .versionOption(version)
    .subcommands(
      OnCommand(),
      OffCommand(),
      InfoCommand(),
      SettingsCommand(),
      SetupCommand(),
    )
    .main(args)
}

private class OnCommand : CliktCommand(name = "on", help = "Turn ON the light") {
  private val brightness by option(
    "-b", "--brightness",
    help = "Set the brightness of the light, [0, 100]"
  ).int()
  private val color by option(
    "-c", "--color",
    help = "Set the color temperature of the light, [2900K, 7000K]"
  ).int()

  override fun run() {
    // {"lights":[{"brightness":10,"temperature":162,"on":1}],"numberOfLights":1}
    val status = runBlocking {
      HttpClient(CIO).use { client ->
        client.put<Unit>("http://${ElGato.address}:${ElGato.port}/elgato/lights") {
          header("Content-Type", "application/json")
          body = "{\"lights\":[{\"on\":1}],\"numberOfLights\":1}"
        }
      }
    }
  }
}

private class OffCommand : CliktCommand(name = "off", help = "Turn OFF the light") {
  override fun run() {
    val status = runBlocking {
      HttpClient(CIO).use { client ->
        client.put<Unit>("http://${ElGato.address}:${ElGato.port}/elgato/lights") {
          header("Content-Type", "application/json")
          body = "{\"lights\":[{\"on\":0}],\"numberOfLights\":1}"
        }
      }
    }
  }
}

private class InfoCommand : CliktCommand(name = "info", help = "Show key light info") {
  override fun run() {
    val response = runBlocking {
      HttpClient(CIO).use { client ->
        client.get<String>("http://${ElGato.address}:${ElGato.port}/elgato/accessory-info")
      }
    }.also { println(it) }
  }
}

private class SettingsCommand : CliktCommand(name = "settings", help = "Show the current light settings") {
  override fun run() {
    val response = runBlocking {
      HttpClient(CIO).use { client ->
        client.get<String>("http://${ElGato.address}:${ElGato.port}/elgato/lights/settings")
      }
    }.also { println(it) }
  }
}

private class SetupCommand : CliktCommand(name = "setup", help = "Set-up the key light") {
  private val address by option(
    "-a", "--address",
    help = "IP address of the key light device"
  ).required()
  private val port by option(
    "-p", "--port",
    help = "Port of the key light device"
  ).int()

  override fun run() {
    println("INFO")
  }
}

