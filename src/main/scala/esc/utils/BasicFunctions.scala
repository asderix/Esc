/**
  * author:   Ronny Fuchs, info@asderix.com
  * licence:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.utils

import java.security.MessageDigest

/**
  * Object with some basics functions which are useful to have
  * per object reference.
  */ 
object BasicFunctions {
  // ---
  private val _sha1MessageDigest = MessageDigest.getInstance("SHA-1")

  /**
    * Check whetever a string is null or empty (false) or not (true).
    */
  def isNullOrEmpty(x: String) = Option(x).forall(_.isEmpty)

  /**
    * Return the SHA-1 digest from two sorted and concated strings.
    * as BigInt.
    */
  def getTextPairHash(textA: String, textB: String): BigInt = {
    BigInt(_sha1MessageDigest.digest(Seq(textA, textB).sorted.mkString.getBytes("UTF-8")))
  }
}
