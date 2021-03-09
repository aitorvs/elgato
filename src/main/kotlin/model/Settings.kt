package model

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
  val powerOnBehavior: Int,
  val powerOnBrightness: Int,
  val powerOnTemperature: Int,
  val switchOnDurationMs: Int,
  val switchOffDurationMs: Int,
  val colorChangeDurationMs: Int,
)
