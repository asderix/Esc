/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

case class MatchExplanation(
    nameType: String,
    sourceNameA: String,
    sourceNameB: String,
    nameTextNormalizedA: String,
    nameTextNormalizedB: String,
    nameNormalizedNameA: Vector[Vector[NormalizedNameExplaint]],
    nameNormalizedNameB: Vector[Vector[NormalizedNameExplaint]],
    nofHits: Int,
    nofHitsWeighted: Double,
    cov: Double,
    covWeighted: Double,
    similarity: Double,
    matchPairs: List[MatchPairsExplaint]
)
