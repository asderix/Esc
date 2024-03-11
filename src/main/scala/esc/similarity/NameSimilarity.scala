/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.similarity

import scala.collection.mutable
import esc.configuration._
import esc.commons._
import esc.normalization._

/** This class provides methods to calculate the similarity of two normalized
  * names.
  *
  * @param similarityConfig
  *   Optional. A SimilarityConfiguration.
  */
class NameSimilarity(
    val similarityConfig: SimilarityConfig = new SimilarityConfig()
) {

  /** Standard method to calculate the simialrity of two person names. It
    * returns a Match object that provides various key figures to match.
    *
    * @param nameA
    *   Name a) as a full name.
    * @param nameB
    *   Name b) as a full name.
    *
    * @return
    *   Retun a Match object with the relevant match key figures.
    */
  def getPersonNameSimilarity(nameA: String, nameB: String): Match = {
    val normalizer = new NameNormalizer
    getNameSimilarity(
      normalizer.normalizePersonName(nameA),
      normalizer.normalizePersonName(nameB)
    )
  }

  /** Standard method to calculate the similarity of two organisation names. It
    * returns a Match object that provides various key figures to match.
    *
    * @param nameA
    *   Name a) as a full name.
    * @param nameB
    *   Name b) as a full name.
    */
  def getOrganisationNameSimilarity(nameA: String, nameB: String): Match = {
    val normalizer = new NameNormalizer
    getNameSimilarity(
      normalizer.normalizeOrganisationName(nameA),
      normalizer.normalizeOrganisationName(nameB)
    )
  }

  /** Returns comprehensive information on a personname hit. Not as performant and
    * therefore not suitable for batch processing. Use this function if a user
    * cannot understand the hit and needs more detailed information.
    * You can use toCompactJson method on MatchExplanation to get a json string
    * of the object.
    * @param nameA
    * @param nameB
    */
  def explainPersonNameSimilarity(
      nameA: String,
      nameB: String
  ): MatchExplanation = {
    val similarityConfig: SimilarityConfig = new SimilarityConfig()
    val nameNormalizer = new NameNormalizer(similarityConfig)
    val normalizedNameA = nameNormalizer.normalizePersonName(nameA)
    val normalizedNameB = nameNormalizer.normalizePersonName(nameB)
    explainNameSimilarity(
      normalizedNameA,
      normalizedNameB,
      Constants.IndexPersonNameTypeCode
    )
  }

  /** Returns comprehensive information on a organisation hit. Not as performant and
    * therefore not suitable for batch processing. Use this function if a user
    * cannot understand the hit and needs more detailed information.
    * You can use toCompactJson method on MatchExplanation to get a json string
    * of the object.
    * @param nameA
    * @param nameB
    */
  def explainOrganisationNameSimilarity(
      nameA: String,
      nameB: String
  ): MatchExplanation = {
    val similarityConfig: SimilarityConfig = new SimilarityConfig()
    val nameNormalizer = new NameNormalizer(similarityConfig)
    val normalizedNameA = nameNormalizer.normalizeOrganisationName(nameA)
    val normalizedNameB = nameNormalizer.normalizeOrganisationName(nameB)
    explainNameSimilarity(
      normalizedNameA,
      normalizedNameB,
      Constants.IndexOrganisationNameTypeCode
    )
  }

  /** Returns comprehensive information on a hit. Not as performant and
    * therefore not suitable for batch processing. Use this function if a user
    * cannot understand the hit and needs more detailed information.
    * @param normalizedNameA
    * @param normalizedNameB
    */
  def explainNameSimilarity(
      normalizedNameA: NormalizedName,
      normalizedNameB: NormalizedName,
      personNameType: String
  ): MatchExplanation = {
    val matchResult = getNameSimilarity(normalizedNameA, normalizedNameB)
    MatchExplanation(
      personNameType,
      normalizedNameA.sourceName,
      normalizedNameB.sourceName,
      TextNormalizer.normalize(normalizedNameA.sourceName),
      TextNormalizer.normalize(normalizedNameB.sourceName),
      normalizedNameA.normNames.map { innerVector =>
        innerVector.map { case (name, weight, elementType) =>
          NormalizedNameExplaint(name, weight, elementType)
        }
      },
      normalizedNameB.normNames.map { innerVector =>
        innerVector.map { case (name, weight, elementType) =>
          NormalizedNameExplaint(name, weight, elementType)
        }
      },
      matchResult.nofHits,
      matchResult.nofHitsWeighted,
      matchResult.cov,
      matchResult.covWeighted,
      matchResult.similarity,
      matchResult.matchPairs.map {
        case (elementA, elementB, similarity, source) =>
          MatchPairsExplaint(elementA, elementB, similarity, source)
      }
    )
  }

  /** Main method to calculate the similarity of two normalized names. It
    * returns a Match object that provides various key figures to match.
    * Attention: Make sure that you only compare the same name types (person or
    * organization). Maybe it's better to use the specific method (person or
    * organisation).
    *
    * @param normNameA
    *   NormalizedName a
    * @param normNameB
    *   NormalizedName b
    *
    * @return
    *   Return a Match object with the relevant key figures of the match.
    */
  def getNameSimilarity(
      normNameA: NormalizedName,
      normNameB: NormalizedName
  ): Match = {
    val mutMatchList = mutable.ListBuffer.empty[Match]
    for (normNamesA <- normNameA.normNames) {
      var wSumA: Double = 0.0
      for (nameA <- normNamesA) wSumA = wSumA + nameA._2

      for (normNamesB <- normNameB.normNames) {
        var wSumB: Double = 0.0
        for (nameB <- normNamesB) wSumB = wSumB + nameB._2

        val mutFile =
          mutable.ArrayBuffer.empty[(String, String, Double, Double, String)]
        val pairs = normNamesA.flatMap(_normNamesA =>
          normNamesB.map(_normNamesB => _normNamesA -> _normNamesB)
        )

        for (pair <- pairs) {
          pair match {
            case ((a, aw, 1), (b, bw, 1)) => {
              a == b match {
                case (true) =>
                  mutFile += ((
                    a,
                    b,
                    1.0,
                    List(aw, bw).sorted.head,
                    Constants.similaritySourceStringIdentical
                  ))
                case (false) => {
                  nameElementSimilarityDb.getKnownSimilarity(a, b) match {
                    case (true, v, q) =>
                      mutFile += ((a, b, v, List(aw, bw).sorted.head, q))
                    case (false, _, _) => {
                      similarityConfig.allowOneLetterAbbreviation match {
                        case (true) => {
                          (a.length(), b.length()) match {
                            case (1, _) =>
                              if (a(0) == b(0))
                                mutFile += ((
                                  a,
                                  b,
                                  1.0,
                                  List(aw, bw).sorted.head,
                                  Constants.similaritySourceOneLetterAbbreviation
                                ))
                            case (_, 1) =>
                              if (b(0) == a(0))
                                mutFile += ((
                                  a,
                                  b,
                                  1.0,
                                  List(aw, bw).sorted.head,
                                  Constants.similaritySourceOneLetterAbbreviation
                                ))
                            case _ =>
                              mutFile += ((
                                a,
                                b,
                                EditDistance.getEditDistanceSimilarity(a, b),
                                List(aw, bw).sorted.head,
                                Constants.similaritySourceWeightedEditDistance
                              ))
                          }
                        }
                        case _ =>
                          mutFile += ((
                            a,
                            b,
                            EditDistance.getEditDistanceSimilarity(a, b),
                            List(aw, bw).sorted.head,
                            Constants.similaritySourceWeightedEditDistance
                          ))
                      }
                    }
                  }
                }
              }
            }
            case ((a, aw, 2), (b, bw, 2)) => {
              a == b match {
                case (true) =>
                  mutFile += ((
                    a,
                    b,
                    1.0,
                    similarityConfig.normOrgLegalformWeight,
                    Constants.similaritySourceLegalFormDedection
                  ))
                case (false) =>
                  mutFile += ((
                    a,
                    b,
                    0.0,
                    similarityConfig.normOrgLegalformWeight,
                    Constants.similaritySourceLegalFormDedection
                  ))
              }
            }
            case ((a, aw, 3), (b, bw, 3)) => {
              a == b match {
                case (true) =>
                  mutFile += ((
                    a,
                    b,
                    1.0,
                    similarityConfig.normOrgCountryWeight,
                    Constants.similaritySourceCountryDedection
                  ))
                case (false) =>
                  mutFile += ((
                    a,
                    b,
                    0.0,
                    similarityConfig.normOrgCountryWeight,
                    Constants.similaritySourceCountryDedection
                  ))
              }
            }
            case _ =>
          }
        }

        val mutFileR = mutFile.sortBy(_._3).reverse
        val mutCleanFile =
          mutable.ArrayBuffer.empty[(String, String, Double, Double, String)]
        val mutCleanFileResult = getCleanMatchArray(mutFileR, mutCleanFile)

        var nofHits: Integer = 0
        var nofHitsWeighted: Double = 0.0
        var simSum: Double = 0.0
        val matchPairs =
          mutable.ArrayBuffer.empty[(String, String, Double, String)]

        for (e <- mutCleanFileResult) {
          e match {
            case ((_, _, s, _, _))
                if s < similarityConfig.nameElementSimilarityForHit =>
            case ((ea, eb, s, w, q)) => {
              nofHits = nofHits + 1
              nofHitsWeighted = nofHitsWeighted + w
              simSum = simSum + (s * w)
              matchPairs += ((ea, eb, s, q))
            }
          }
        }

        val cov: Double = nofHits.toDouble / (List(
          normNamesA.length,
          normNamesB.length
        ).sorted.reverse.head)
        val covW: Double = simSum / (List(wSumA, wSumB).sorted.reverse.head)

        val sim = (List(
          normNamesA.length,
          normNamesB.length
        ).sorted.head - nofHits) match {
          case (0) => covW + ((1 - covW) / 1.3)
          case (1) => covW + ((1 - covW) / 2.3)
          case (_) => covW + ((1 - covW) / 7.3)
        }

        val _match =
          Match(nofHits, nofHitsWeighted, cov, covW, sim, matchPairs.toList)

        mutMatchList += _match
      }
    }

    similarityConfig.matchSelectionMode match {
      case 0 => mutMatchList.sortBy(_.similarity).reverse.head
      case 1 => mutMatchList.sortBy(_.nofHits).reverse.head
      case _ => mutMatchList.sortBy(_.similarity).reverse.head
    }
  }

  // ---
  private def getCleanMatchArray(
      orig: mutable.ArrayBuffer[(String, String, Double, Double, String)],
      clean: mutable.ArrayBuffer[(String, String, Double, Double, String)]
  ): mutable.ArrayBuffer[(String, String, Double, Double, String)] = {
    val pair = orig.head
    clean += pair
    val tail = orig.tail
    val tClean = tail.filter(_._1 != pair._1).filter(_._2 != pair._2)

    tClean.isEmpty match {
      case true => clean
      case _    => getCleanMatchArray(tClean, clean)
    }
  }
}
