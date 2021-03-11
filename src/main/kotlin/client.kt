import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import model.Info
import model.LightValues
import model.Light
import model.Settings


private fun httpClient(): HttpClient {
  return HttpClient(CIO) {
    install(JsonFeature) {
      serializer = KotlinxSerializer()
    }
  }
}

@Suppress("ClassName")
class client {

  companion object {
    private suspend inline fun <reified T> httpGet(path: String): T {
      return httpClient().use { client ->
        client.get("http://${ElGato.address}:${ElGato.port}/$path")
      }
    }

    private suspend inline fun <reified T> httpPut(path: String, body: T? = null) {
      return httpClient().use { client ->
        client.put<Unit>("http://${ElGato.address}:${ElGato.port}/$path") {
          header("Content-Type", "application/json")
          body?.let { this.body = it }
        }
      }
    }

    suspend fun fetchSettings(): Settings {
      return httpGet<Settings>("elgato/lights/settings")
    }

    suspend fun fetchValues(): LightValues {
      return httpGet("elgato/lights")
    }

    suspend fun fetchInfo(): Info {
      return httpGet<Info>("elgato/accessory-info")
    }

    suspend fun sendValues(values: LightValues) {
      return httpPut("elgato/lights", values)
    }

    suspend fun turnOn(){
      val body = LightValues(
        numberOfLights = 1,
        lights = listOf(Light(on = 1))
      )
      return sendValues(body)
    }

    suspend fun turnOff() {
      val body = LightValues(
        numberOfLights = 1,
        lights = listOf(Light(on = 0))
      )
      return sendValues(body)
    }
  }
}
