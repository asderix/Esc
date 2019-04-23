/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

/**
 * Class represents a person to index.
 * @param  id  The id of the person.
 * @param  externalId  The external id of the person.
 * @param  fullName  The full name of the person. More than one name? See Indexer class for the req. options.
 * @param  datesOfBirth  List of dates. Not null, empty allowed. Format see supported date formats by the DateNormalizer.
 * @param  countries  List of countries. Not null, empty allowed. Format: ISO-2, name in en, de, fr, it.
 * @param  label  Optional string for a category. You can use it as a filter by searching an organisation.
 */
case class IndexPerson(id: String,
                 externalId : String,
                 fullName : String,
                 datesOfBirth : List[String],
                 countries : List[String],
                 label : String = "")