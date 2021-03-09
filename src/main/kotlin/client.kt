import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import model.Info
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

    private suspend fun httpPut(path: String, body: String? = null) {
      return httpClient().use { client ->
        client.put<Unit>("http://${ElGato.address}:${ElGato.port}/$path") {
//          header("Content-Type", "application/json")
          body?.let { this.body = it }
        }
      }
    }

    suspend fun fetchSettings(): Settings {
      return httpGet<Settings>("elgato/lights/settings")
    }

    suspend fun fetchInfo(): Info {
      return httpGet<Info>("elgato/accessory-info")
    }

    // {"lights":[{"brightness":10,"temperature":162,"on":1}],"numberOfLights":1}
    suspend fun turnOn(){
      val body = "{\"lights\":[{\"on\":1}],\"numberOfLights\":1}"
      return httpPut("elgato/lights", body)
    }

    suspend fun turnOff(){
      val body = "{\"lights\":[{\"on\":0}],\"numberOfLights\":1}"
      return httpPut("elgato/lights", body)
    }
  }
}
