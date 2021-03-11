package model

import kotlinx.serialization.Serializable

//{"numberOfLights":1,"lights":[{"on":1,"brightness":3,"temperature":344}]}
@Serializable
data class LightValues (
  val numberOfLights: Int,
  val lights: List<Light>
)

@Serializable
data class Light(
  val on: Int? = null,
  val brightness: Int? = null,
  val temperature: Int? = null,
)