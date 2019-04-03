/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
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

  println("# Type -pnorm '[name]' for person name normalization")
  println("# Type -onorm '[name]' for organisation name normalization")
  println("# Type -pnm '[nameA]','[nameB]' for person name name-matching.")
  println("# Type -onm '[nameA]','[nameB]' for organisation name name-matching.")
  println("# Type exit for exit.")
  println("")
  
  val norm = new NameNormalizer
  val sim = new NameSimilarity

  var go : Boolean = true
  while(go) {
    val input = scala.io.StdIn.readLine("> ") 
    if (input.contains("-pnorm ")) {
      val cString = input.replace("-pnorm ", "")
      println(norm.normalizePersonName(cString))

    }

    if (input.contains("-onorm ")) {
      val cString = input.replace("-onorm ", "")
      println(norm.normalizeOrganisationName(cString))

    }

    if (input.contains("-pnm ")) {
      val cString = input.replace("-pnm ", "")
      val names = cString.split(",")
      println(sim.getPersonNameSimilarity(names(0), names(1)).toString)

    }

    if (input.contains("-onm ")) {
      val cString = input.replace("-onm ", "")
      val names = cString.split(",")
      println(sim.getOrganisationNameSimilarity(names(0), names(1)).toString)
    }

    if (input == "exit") {
      go = false
      println("Bye bye ...")
    }
  }
}
