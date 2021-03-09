import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
  val version = NoOpCliktCommand::class.java.`package`.implementationVersion
  NoOpCliktCommand(name = "elgato")
    .versionOption(version)
    .subcommands(
      SwitchCommand(),
      InfoCommand(),
      SettingsCommand(),
      SetupCommand(),
    )
    .main(args)
}

private class SwitchCommand : NoOpCliktCommand(name = "switch", help = "Control light switches") {
  init {
    subcommands(
      OnCommand(),
      OffCommand(),
    )
  }
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
    runBlocking { client.turnOn() }
  }
}

private class OffCommand : CliktCommand(name = "off", help = "Turn OFF the light") {
  override fun run() {
    runBlocking { client.turnOff() }
  }
}

private class InfoCommand : CliktCommand(name = "info", help = "Show key light info") {
  override fun run() {
    runBlocking { client.fetchInfo() }.also { println(it) }
  }
}

private class SettingsCommand : CliktCommand(name = "settings", help = "Show the current light settings") {
  override fun run() {
    runBlocking { client.fetchSettings() }.also { println(it) }
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
    println("[Coming soon...]")
  }
}

