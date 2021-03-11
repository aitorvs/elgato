import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import kotlinx.coroutines.runBlocking
import java.lang.Integer.min
import kotlin.math.max

fun main(args: Array<String>) {
  val version = NoOpCliktCommand::class.java.`package`.implementationVersion
  NoOpCliktCommand(name = "elgato")
    .versionOption(version)
    .subcommands(
      SwitchCommand(),
      AdjustCommand(),
      InfoCommand(),
      SettingsCommand(),
      SetupCommand(),
    )
    .main(args)
}

private class AdjustCommand : NoOpCliktCommand(name = "adjust", help = "Control light switches") {
  init {
    subcommands(
      BrightnessCommand(),
      ColorTemperatureCommand(),
    )
  }

  private class BrightnessCommand : CliktCommand(name = "brightness", help = "Adjust the light brightness") {
    private val command by option().switch(
      "--up" to Command.increase,
      "--down" to Command.decrease
    )

    override fun run() {
      runBlocking { client.fetchValues() }.also { values ->
        val lights = values.lights.toMutableList().map { light ->
          val delta = if (command == Command.increase) 5 else -5
          val newBrightness = max(3, min(100, (light.brightness ?: 0) + delta))
          light.copy(brightness = newBrightness)
        }
        runBlocking { client.sendValues(values.copy(lights = lights)) }
      }
    }
  }

  private class ColorTemperatureCommand : CliktCommand(name = "color", help = "Adjust the light color temperature") {
    private val command by option().switch(
      "--warmer" to Command.increase,
      "--colder" to Command.decrease
    )

    override fun run() {
      runBlocking { client.fetchValues() }.also { values ->
        val lights = values.lights.toMutableList().map { light ->
          val delta = if (command == Command.increase) 10 else -10
          val newTemperature = max(3, min(354, (light.temperature ?: 0) + delta))

          light.copy(temperature = newTemperature)
        }
        runBlocking { client.sendValues(values.copy(lights = lights)) }
      }
    }
  }

  enum class Command {
    increase, decrease
  }
}

private class SwitchCommand : NoOpCliktCommand(name = "switch", help = "Control light switches") {
  init {
    subcommands(
      OnCommand(),
      OffCommand(),
    )
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

