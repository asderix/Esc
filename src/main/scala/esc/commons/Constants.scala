/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

/** This object provides static constants needed in the application.
  */
object Constants {
  val IndexPersonNameTypeCode: String = "p"
  val IndexOrganisationNameTypeCode: String = "o"
  val similaritySourceStringIdentical: String = "stringIdent"
  val similaritySourceLibDb: String = "libDb"
  val similaritySourceOneLetterAbbreviation: String = "oneLetterAbb"
  val similaritySourceCountryDedection: String = "countryDed"
  val similaritySourceLegalFormDedection: String = "legalFormDed"
  val similaritySourceWeightedEditDistance: String = "wEditDist"
  val similaritySourceOwnSimilarity: String = "ownSim"
  val similaritySourceUndefined: String = "undef"
}
