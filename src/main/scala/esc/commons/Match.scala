/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

/**
 * Class that provides various key figures for a comparison.
 */
case class Match(nofHits : Int,
                 nofHitsWeighted : Double,
                 cov : Double,
                 covWeighted : Double,
                 similarity : Double,
                 matchPairs : List[(String, String)])