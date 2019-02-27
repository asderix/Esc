/**
  * author:   Ronny Fuchs, info@asderix.com
  * licence:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.configuration

/**
  * Case class for the configuration of the similarity stuff.
  *
  * At the moment only parameters are implemented which are also
  * used for the normalization.
  */
case class SimilarityConfig(normOrgLegalformWeight : Double = 0.5, normOrgCountryWeight : Double = 0.5)