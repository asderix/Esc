/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.utils

import upickle.default._
import esc.commons._
import esc.commons.MatchPairsExplaint
import esc.commons.NormalizedNameExplaint
import esc.commons.MatchExplanation

/** Object providing methods for serialization and deserialization.
  */
object Persistence {
  implicit val normalizedNameExplaintRW
      : upickle.default.ReadWriter[NormalizedNameExplaint] =
    upickle.default.macroRW[NormalizedNameExplaint]
  implicit val matchPairsExplaintRW
      : upickle.default.ReadWriter[MatchPairsExplaint] =
    upickle.default.macroRW[MatchPairsExplaint]
  implicit val matchExplanationRW
      : upickle.default.ReadWriter[MatchExplanation] =
    upickle.default.macroRW[MatchExplanation]
  implicit val matchRW: upickle.default.ReadWriter[Match] =
    upickle.default.macroRW[Match]
  implicit val finderMatchRW: upickle.default.ReadWriter[FinderMatch] =
    upickle.default.macroRW[FinderMatch]

  extension (normalizedNameVector: Vector[Vector[(String, Double, Byte)]])
    def toCompactJson: String = upickle.default.write(normalizedNameVector)
  extension (normalizedNameVectorJson: String)
    def toNormalizedNameVector: Vector[Vector[(String, Double, Byte)]] =
      upickle.default
        .read[Vector[Vector[(String, Double, Byte)]]](normalizedNameVectorJson)
  extension (matchExplanation: MatchExplanation)
    def toCompactJson: String = upickle.default.write(matchExplanation)
  extension (escMatch: Match)
    def toCompactJson: String = upickle.default.write(escMatch)
  extension (finderMatch: FinderMatch)
    def toCompactJson: String = upickle.default.write(finderMatch)
  extension (finderMatchList: List[FinderMatch])
    def toCompactJson: String = upickle.default.write(finderMatchList)
}
