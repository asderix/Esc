/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

/** Representing a normalized name. For person- and organisation names.
  *
  * Use the NameNormalizer class to get a valid instance of this class.
  *
  * @param normNames
  *   String = the name element, Double = the weight of the name element, Byte =
  *   the type of the name element: 1 = normal, 2 = legal form, 3 = country
  * @param sourceName
  *   The original full name.
  */
case class NormalizedName(
    normNames: Vector[Vector[(String, Double, Byte)]],
    sourceName: String
)

/**
  * Case class represent the normNames field of NormalizedName in a readable form
  * used for explanation and better serialization.
  * 
  * @param nameElement
  * @param weight
  * @param elementType
  */
case class NormalizedNameExplaint(
    nameElement: String,
    weight: Double,
    elementType: Byte
)
