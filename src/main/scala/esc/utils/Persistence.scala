/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.utils

import spray.json._
import DefaultJsonProtocol._

/**
  * Object providing methods for serialization and deserialization.
  */
object Persistence {
  implicit class NormalizedNameVectorSerializer(normalizedNameVector: Vector[Vector[(String, Double, Byte)]])
  {
    /**
      * Convert a normalizedNameVector to a json. So it's easy to persist it.
      * @return Json.
      */
    def toCompactJson : String = normalizedNameVector.toJson.compactPrint
  }

  implicit class NormalizedNameVectorDeserializer(normalizedNameVectorJson: String)
  {
    /**
      * Convert a json representing a normalizedNameVector to a normalizedNameVector. So it's easy to
      * deserialize a persistent normalizedNameVector.
      * @return normalizedNameVector.
      */
    def toNormalizedNameVector : Vector[Vector[(String, Double, Byte)]] = normalizedNameVectorJson.parseJson.convertTo[Vector[Vector[(String, Double, Byte)]]]
  }

  // Todo: Add persistence for country/country-vector
  // Todo: Add persistence for date/date-vector

}
