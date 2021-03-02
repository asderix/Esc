/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

/**
 * Class that provides the match information of a Finder result.
 * @param  id  The id of the found person or organisation.
 * @param  externalId  The external id of the found person or organisation.
 * @param  foundName  The original name of the found person or organisation.
 * @param  matchResult  The MatchResult object of the match itself.
 */
case class FinderMatch(id: String,
                       externalId : String,
                       foundName : String,
                       matchResult : Match)