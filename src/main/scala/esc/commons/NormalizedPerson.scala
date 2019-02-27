/**
  * author:   Ronny Fuchs, info@asderix.com
  * licence:  Apache license 2.0 - https://www.apache.org/licenses/
  */

/**
  * We will see if this class is needed in future or not.
  * If need, then create also one for NormalizedOrganisation.
  */

package esc.commons

case class NormalizedPerson(names : Vector[NormalizedName], countries : Vector[String],
                            dobs : Vector[NormalizedDate])
