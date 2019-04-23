/**
  * author:   Ronny Fuchs, info@asderix.com
  * licencs:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.utils

/**
  * Object with some basics functions which are useful to have
  * per object reference.
  */ 
object BasicFunctions {

  /**
    * Check whetever a string is null or empty (true) or not (false).
    */
  def isNullOrEmpty(s: String) = Option(s).forall(_.isEmpty)
}
