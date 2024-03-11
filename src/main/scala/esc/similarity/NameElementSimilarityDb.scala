/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.similarity

import esc.utils.BasicFunctions
import esc.configuration._
import esc.normalization._
import esc.commons._
import scala.collection.mutable

/** Object providing methods for known similarity name element pairs.
  */
object nameElementSimilarityDb {

  /** Return if a similarity is know or not and the similarity value. Similarity
    * of a no knowing combination is -99.999.
    *
    * @param nameElementA
    *   The name element a) for the comparison. Name element, e.g. john, not a
    *   full name.
    * @param nameElementB
    *   The name element b) for the comparison. Name element, e.g. john, not a
    *   full name.
    *
    * @return
    *   Return a Tuple with Boolean if is the similarity known or not
    *   (true/false) and the similarity value. If not know, the value is
    *   -99.999.
    */
  def getKnownSimilarity(
      nameElementA: String,
      nameElementB: String
  ): (Boolean, Double, String) = {
    mutOwnNameElementSimilarities.getOrElse(
      Seq(nameElementA, nameElementB).sorted.mkString("."),
      -99.999
    ) match {
      case r if r != -99.999 =>
        return (true, r, Constants.similaritySourceOwnSimilarity)
      case _ => ()
    }

    nameElementSimilarities.getOrElse(
      Seq(nameElementA, nameElementB).sorted.mkString("."),
      -99.999
    ) match {
      case r if r == -99.999 =>
        return (false, r, Constants.similaritySourceUndefined)
      case r => return (true, r, Constants.similaritySourceLibDb)
    }
  }

  /** Return a list with matching name elements for a given name element.
    * MatchLevel is an equal or bigger value of similarity. For matchLevel it is
    * recommended to use the value of
    * SimilarityConfig.nameElementSimilarityForHit. This is also used as the
    * default.
    *
    * @param nameElement
    *   The name element for which you asked for known hits.
    * @param matchLevel
    *   The similarity value from which you speak from a hit. E.g. 0.9. Use the
    *   value from SimilarityConfig.nameElementSimilarityForHit for consistence.
    *
    * @return
    *   Return a list of String with found known matches.
    */
  def getMatchList(
      nameElement: String,
      matchLevel: Double = new SimilarityConfig().nameElementSimilarityForHit
  ): List[String] = {
    var mutMatchPairs = mutable.ListBuffer.empty[(String, String)]
    for ((k, v) <- nameElementSimilarities) {
      v match {
        case s if s >= matchLevel => {
          val arr = k.split("\\.")
          mutMatchPairs += ((arr(0), arr(1)))
        }
        case _ =>
      }
    }

    var mutMatchOwnPairs = mutable.ListBuffer.empty[(String, String)]
    for ((k, v) <- mutOwnNameElementSimilarities) {
      v match {
        case s if s >= matchLevel => {
          val arr = k.split("\\.")
          mutMatchOwnPairs += ((arr(0), arr(1)))
        }
        case _ =>
      }
    }

    val matchPairs = mutMatchPairs.toList
    val filteredPairs1 = matchPairs.filter(_._1 == nameElement)
    val filteredPairs2 = matchPairs.filter(_._2 == nameElement)

    val matchOwnPairs = mutMatchOwnPairs.toList
    val filteredOwnPairs1 = matchOwnPairs.filter(_._1 == nameElement)
    val filteredOwnPairs2 = matchOwnPairs.filter(_._2 == nameElement)

    val filteredPairs =
      filteredPairs1 ++ filteredPairs2 ++ filteredOwnPairs1 ++ filteredOwnPairs2
    filteredPairs.flatMap(t => List(t._1, t._2)).distinct
  }

  /** Add a similarity value between two name elements. similarity: Value
    * between 0 and 1.
    *
    * @param nameElementA
    * @param nameElementB
    * @param similarity
    */
  def addNameElementSimilarity(
      nameElementA: String,
      nameElementB: String,
      similarity: Double
  ): Unit = {
    require(nameElementA != null)
    require(nameElementB != null)
    assert(
      similarity >= 0 && similarity <= 1,
      s"The value of similarity' ($similarity) must be between 0 and 1."
    )
    mutOwnNameElementSimilarities += (Seq(
      TextNormalizer.normalizeNameElement(
        TextNormalizer.normalize(nameElementA)
      ),
      TextNormalizer.normalizeNameElement(
        TextNormalizer.normalize(nameElementB)
      )
    ).sorted.mkString(".") -> similarity)
  }

  /** Remove a similarity value between to name elements.
    *
    * @param nameElementA
    * @param nameElementB
    */
  def removeNameElementSimilarity(
      nameElementA: String,
      nameElementB: String
  ): Unit = {
    require(nameElementA != null)
    require(nameElementB != null)

    mutOwnNameElementSimilarities -= Seq(
      TextNormalizer.normalizeNameElement(
        TextNormalizer.normalize(nameElementA)
      ),
      TextNormalizer.normalizeNameElement(
        TextNormalizer.normalize(nameElementB)
      )
    ).sorted.mkString(".")

  }

  private val nameElementSimilarities =
    NameElementSimilarityIndex1.nameElementSimilarities ++ NameElementSimilarityIndex2.nameElementSimilarities

  private val mutOwnNameElementSimilarities = mutable.Map[String, Double]()
}
