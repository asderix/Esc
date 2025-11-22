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
          mutable.ArrayBuffer.empty[(String, String, Double, Double, String, Int, Int)]
        val pairs = normNamesA.flatMap(_normNamesA =>
          normNamesB.map(_normNamesB => _normNamesA -> _normNamesB)
        )

        val pairsWithIndex = for {
          _normNamesA <- normNamesA.zipWithIndex
          _normNamesB <- normNamesB.zipWithIndex
        } yield (_normNamesA, _normNamesB)

        for (pair <- pairsWithIndex) {
          pair match {
            case (((a, aw, 1), idxA), ((b, bw, 1), idxB)) => {
              a == b match {
                case (true) =>
                  mutFile += ((
                    a,
                    b,
                    1.0,
                    List(aw, bw).sorted.head,
                    Constants.similaritySourceStringIdentical,
                    idxA,
                    idxB
                  ))
                case (false) => {
                  nameElementSimilarityDb.getKnownSimilarity(a, b) match {
                    case (true, v, q) =>
                      mutFile += ((a, b, v, List(aw, bw).sorted.head, q, idxA, idxB))
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
                                  Constants.similaritySourceOneLetterAbbreviation,
                                  idxA,
                                  idxB
                                ))
                            case (_, 1) =>
                              if (b(0) == a(0))
                                mutFile += ((
                                  a,
                                  b,
                                  1.0,
                                  List(aw, bw).sorted.head,
                                  Constants.similaritySourceOneLetterAbbreviation,
                                  idxA,
                                  idxB
                                ))
                            case _ =>
                              mutFile += ((
                                a,
                                b,
                                EditDistance.getEditDistanceSimilarity(a, b),
                                List(aw, bw).sorted.head,
                                Constants.similaritySourceWeightedEditDistance,
                                idxA,
                                idxB
                              ))
                          }
                        }
                        case _ =>
                          mutFile += ((
                            a,
                            b,
                            EditDistance.getEditDistanceSimilarity(a, b),
                            List(aw, bw).sorted.head,
                            Constants.similaritySourceWeightedEditDistance,
                            idxA,
                            idxB
                          ))
                      }
                    }
                  }
                }
              }
            }
            case (((a, aw, 2), idxA), ((b, bw, 2), idxB)) => {
              a == b match {
                case (true) =>
                  mutFile += ((
                    a,
                    b,
                    1.0,
                    similarityConfig.normOrgLegalformWeight,
                    Constants.similaritySourceLegalFormDedection,
                    idxA,
                    idxB
                  ))
                case (false) =>
                  mutFile += ((
                    a,
                    b,
                    0.0,
                    similarityConfig.normOrgLegalformWeight,
                    Constants.similaritySourceLegalFormDedection,
                    idxA,
                    idxB
                  ))
              }
            }
            case (((a, aw, 3), idxA), ((b, bw, 3), idxB)) => {
              a == b match {
                case (true) =>
                  mutFile += ((
                    a,
                    b,
                    1.0,
                    similarityConfig.normOrgCountryWeight,
                    Constants.similaritySourceCountryDedection,
                    idxA,
                    idxB
                  ))
                case (false) =>
                  mutFile += ((
                    a,
                    b,
                    0.0,
                    similarityConfig.normOrgCountryWeight,
                    Constants.similaritySourceCountryDedection,
                    idxA,
                    idxB
                  ))
              }
            }
            case _ =>
          }
        }

        val mutFileR = mutFile.sortBy(_._3).reverse        
        val mutCleanFileResult = getCleanMatchArray(mutFileR)

        var nofHits: Integer = 0
        var nofHitsWeighted: Double = 0.0
        var simSum: Double = 0.0
        val matchPairs =
          mutable.ArrayBuffer.empty[(String, String, Double, String)]

        for (e <- mutCleanFileResult) {
          e match {
            case ((_, _, s, _, _, _, _))
                if s < similarityConfig.nameElementSimilarityForHit =>
            case ((ea, eb, s, w, q, _, _)) => {
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
    orig: mutable.ArrayBuffer[(String, String, Double, Double, String, Int, Int)]
  ): mutable.ArrayBuffer[(String, String, Double, Double, String, Int, Int)] = {
    
    val usedIndexA = mutable.Set[Int]()
    val usedIndexB = mutable.Set[Int]()
    val clean = mutable.ArrayBuffer.empty[(String, String, Double, Double, String, Int, Int)]

    for (pair @ (a, b, s, w, q, idxA, idxB) <- orig) {
        if (!usedIndexA.contains(idxA) && !usedIndexB.contains(idxB)) {
            clean += pair
            usedIndexA += idxA
            usedIndexB += idxB
        }
    }
    
    clean
  }
}
