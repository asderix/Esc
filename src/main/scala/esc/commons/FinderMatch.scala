/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

/**
 * Class that provides the match information of a Finder result.
 * @param  id  The id of the found person.
 * @param  externalId  The external id of the found person.
 * @param  foundName  The original name of the found person.
 * @param  matchResult  The matchResult object of the match.
 */
case class FinderMatch(id: String,
                       externalId : String,
                       foundName : String,
                       matchResult : Match)