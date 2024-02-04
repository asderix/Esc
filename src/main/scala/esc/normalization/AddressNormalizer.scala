/** author: Ronny Fuchs, info@asderix.com license: Apache license 2.0 -
  * https://www.apache.org/licenses/
  */

package esc.normalization

import esc.configuration._
import esc.utils.LegalForm._
import esc.utils.Countries._
import esc.commons._
import esc.normalization._
import scala.collection.mutable._

/** This class provides the most needed methods for normalizing an address.
  *
  * @param similarityConfig
  *   Optional. A SimilarityConfiguration.
  * @param stopWords
  *   Optional. A list with words which sould be ignored for matching.
  * @param hitWords
  *   Optional. A list with words which should be result in a hit, even they
  *   occurs only one time.
  */
class AddressNormalizer(
    val similarityConfig: SimilarityConfig = new SimilarityConfig(),
    val stopWords: List[String] = List[String](),
    val hitWords: List[String] = List[String]()
) {

  /** This method normalize a address.
    *
    * @param address
    *   String representing the full address as a string.
    *
    * @return
    *   Returns a NormalizedAddress object.
    */
  def normalizeAddress(address: String): NormalizedAddress = {
    require(address.length > 1)
    require(stopWords != null)
    require(hitWords != null)
    val countries = new ListBuffer[String]()
    val normAddress = TextNormalizer
      .normalize(address)
      .split(" ")
      .filter(e => {
        var filter = true
        (
          similarityConfig.checkCountyForAdressSearch,
          e.size > 3,
          e.toIsoCountry,
          stopWords.contains(e)
        ) match {
          case (true, true, (true, c), _) => {
            countries += c
            filter = false
          }
          case (_, _, (_, _), true) => filter = false
          case default              => filter = true
        }
        filter
      })
      .flatMap(e => {
        hitWords.contains(e) match {
          case true =>
            List.fill(similarityConfig.numberOfHitsForAddressSearchHit)(e)
          case false => List(e)
        }
      })
    new NormalizedAddress(address, normAddress.toList, countries.toList)
  }
}
