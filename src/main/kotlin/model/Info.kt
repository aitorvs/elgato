package model

import kotlinx.serialization.Serializable

@Serializable
data class Info(
  val productName: String,
  val hardwareBoardType: Int,
  val firmwareBuildNumber: Int,
  val firmwareVersion: String,
  val serialNumber: String,
  val displayName: String,
  val features: List<String>,
)
