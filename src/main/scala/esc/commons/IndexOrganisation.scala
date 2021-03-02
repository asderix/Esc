/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

/**
 * Class represents an organisation to index.
 * @param  id  The id of the organisation.
 * @param  externalId  The external id of the organisation.
 * @param  fullName  The full name (inclusive legal form) of the organisation.
 * @param  datesOfFounding  List of dates. Not null, empty allowed. Format see supported date formats by the DateNormalizer.
 * @param  countries  List of countries. Not null, empty allowed. Format: ISO-2, ISO-3 or name in en, de, fr or it (not recommended).
 * @param  label  Optional string for a category. You can use it as a filter by searching an organisation.
 */
case class IndexOrganisation(id: String,
                 externalId : String,
                 fullName : String,
                 datesOfFounding : List[String],
                 countries : List[String],
                 label : String = "")