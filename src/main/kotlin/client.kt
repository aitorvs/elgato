import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*

@Suppress("ClassName")
class client {

  companion object {
    private suspend fun httpGet(path: String): String {
      return HttpClient(CIO).use { client ->
        client.get("http://${ElGato.address}:${ElGato.port}/$path")
      }
    }

    private suspend fun httpPut(path: String, body: String? = null) {
      HttpClient(CIO).use { client ->
        client.put<Unit>("http://${ElGato.address}:${ElGato.port}/$path") {
//          header("Content-Type", "application/json")
          body?.let { this.body = it }
        }
      }
    }

    suspend fun fetchSettings(): String {
      return httpGet("elgato/lights/settings")
    }

    suspend fun fetchInfo(): String {
      return httpGet("elgato/accessory-info")
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
