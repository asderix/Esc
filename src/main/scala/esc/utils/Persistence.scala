/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.utils

import upickle.default._

/**
  * Object providing methods for serialization and deserialization.
  */
object Persistence {
  extension(normalizedNameVector: Vector[Vector[(String, Double, Byte)]]) def toCompactJson: String = upickle.default.write(normalizedNameVector)
  extension(normalizedNameVectorJson: String) def toNormalizedNameVector: Vector[Vector[(String, Double, Byte)]] =
      upickle.default
        .read[Vector[Vector[(String, Double, Byte)]]](normalizedNameVectorJson)
}
