/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

/**
 * Class that provides various key figures for a comparison. This class would be used
 * by the NameSimilarity class. You can only consume them as a result.
 *
 * @param  nofHits  Number of hits, count of matching name elements.
 * @param  nofHitsWeighted  Number of hits, count of matching name elements, but weighted. Some matches has a lower weight than 1.
 * @param  cov  Coverage. Number of hits in relation to the longer name.
 * @param  covWeighted  Coverage. Number of hits weighted in relation to the longer name, but weighted number of name elements.
 * @param  similarity  Depending on the ratio of the length - nofHits the similarity is calculated as follows: covW + ((1-covW)/x
 * @param  matchPairs  A list that contains all pairs of name elements that were recognized as hits.
 */
case class Match(nofHits : Int,
                 nofHitsWeighted : Double,
                 cov : Double,
                 covWeighted : Double,
                 similarity : Double,
                 matchPairs : List[(String, String)])