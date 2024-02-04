/** author: Ronny Fuchs, info@asderix.com license: Apache license 2.0 -
  * https://www.apache.org/licenses/
  */

package esc.commons

/** Representing a normalized address.
  *
  * Use the AddressNormalizer class to get a valid instance of this class.
  *
  * @param address
  *   The normalized address string.
  *
  * @param countries
  *   ISO-2 code of the countries the address is related to.
  */
case class NormalizedAddress(
    address: String,
    addressItems: List[String],
    countries: List[String]
)
