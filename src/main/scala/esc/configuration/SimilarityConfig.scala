/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.configuration

/**
  * Case class for the configuration of the similarity stuff.
  *
  * normOrgLegalformWeight: Weight of a legal form match
  * normOrgCountryWeight: Weight of a country match
  * nameElementSimilarityForHit: Minimum similarity to mark as hit.
  * matchSelectionMode:
  * 0 = Similarity
  * 1 = Number of hits (not weighted)
  */
case class SimilarityConfig(normOrgLegalformWeight : Double = 0.25,
                            normOrgCountryWeight : Double = 0.5,
                            nameElementSimilarityForHit : Double = 0.9,
                            matchSelectionMode : Int = 0
                        )