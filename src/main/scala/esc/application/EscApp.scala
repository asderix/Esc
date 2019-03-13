/**
  * author:   Ronny Fuchs, info@asderix.com
  * licence:  Apache license 2.0 - https://www.apache.org/licenses/
  */

/**
  * During the basic development this ist only for playing.
  * Later it will comes to a simple command-line application
  * for a direct usage of the library.
*/

package esc.application

import esc.commons.NormalizedName
import esc.normalization._
import esc.utils.Persistence._
import esc.utils.BasicFunctions
import esc.similarity._

import java.security.MessageDigest
import scala.math.{max,min}

object EscApp extends App {
  println("")
  println("#################################################################################################################")
  println("# Hello from ESC!                                                                                               #")
  println("#################################################################################################################")
  println("")

  /*
  println("# Type nnp [name] for person name normalization.")
  println("# Type nne [name] for entity name normalization.")
  println("# Type dn [date] for date normalization.")
  println("# Type cn [country name] for country normalization.")
  println("# Type end. for exit.")
  println("######################################################")
  println("")
  */

  println("")
  println("Press enter to exit.")
  val ending = scala.io.StdIn.readLine("> ")

}
