package esc.application

import esc.commons.NormalizedName
import esc.normalization._
import esc.utils.Persistence._

object EscApp extends App {
  println("hello ronny from Esc!")

  val nn1 = new NameNormalizer().normalizePersonName("RÒnald Möhler-Møller")

  val nn2 = new NameNormalizer().normalizePersonName("Ronny Fuchs")
  val nn3 = new NameNormalizer().normalizePersonName("Romina Wladimir-Wladimir")
  val nn4 = new NameNormalizer().normalizePersonName("Van de Saar Nikolowitsch")
  val nn5 = new NameNormalizer().normalizePersonName("Jean-Jack Meier Müller")

  val on1 = new NameNormalizer().normalizeOrganisationName("Huber-Müller - - Meier AG")
  println(on1.normNames)
  
  //println(on1.normNames.toCompactJson)

  //val nn6 = new NameNormalizer().normalizePersonName("A")
  //val nn6 = new NameNormalizer().normalizePersonName("Hans-Jakob-Toni Huber")

  /*
  println(nn3.sourceName)
  println(nn3.normNames)
  println(nn4.normNames)
  println(nn5.normNames)
*/

  /*
   val countries = Map(
    "ad" -> "ad", "andorra" -> "ad", "andorre" -> "ad", "principat dandorra" -> "ad",
    "ae" -> "ae", "are" -> "ae", "uae" -> "ae", "vereinigte arabische emirate" -> "ae", "emirati arabi uniti" -> "ae", "united arab emirates" -> "ae", "emirats arabes unis" -> "ae"
  )
*/
  /*
   val countryList = countries.keys.toList

  println(countryList)

  //println(nn3.normNames)
  println()
  val json = nn3.normNames.toCompactJson
  //println(json)
  println()
  val obj = json.toNormalizedNameVector
  val nnn = NormalizedName(obj, "test")
  //println(nnn.normNames)

*/

  //val obj = Persistence.getNormalizedPersonName(json)
  //println(obj.normNames)

  //println(nn6.normNames)

  //val women = Vector("Wilma")
  //val man = Vector("Kurt", "Hans")
  //val c = women zip man

  //println(c)

  //val l = List(List("Romina"),List("Fuchs-Rey", "FuchsRey"),List("Müller-Meier","MüllerMeier"))
 // val l = List(List(Vector("Romina")),List(Vector("Fuchs", "Rey"), Vector("FuchsRey")),List(Vector("Müller", "Meier"), Vector("MüllerMeier")))
  //val t = combinationList(l)
  //println(t)

  def combinationList[T](ls: List[List[T]]): List[List[T]] = ls match {
    case Nil => Nil::Nil
    case head :: tail => val rec = combinationList[T](tail)
      rec.flatMap(r => head.map(t => t::r))
  }

  // Ev. in Basic-Funktion auslagern
  def isNullOrEmpty(x: String) = Option(x).forall(_.isEmpty)
}


