/**
  * author:   Ronny Fuchs, info@asderix.com
  * licence:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.utils

/**
  * Object with some basics functions which are useful to have
  * per object reference.
  */ 
object BasicFunctions {
  def isNullOrEmpty(x: String) = Option(x).forall(_.isEmpty)
}
