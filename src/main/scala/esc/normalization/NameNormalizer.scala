/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.normalization

import esc.configuration._
import esc.utils.LegalForm._
import esc.utils.Countries._
import esc.commons._
import scala.collection.mutable

/**
  * This class provides the most needed methods for normalizing names.
  */
class NameNormalizer(val similarityConfig : SimilarityConfig = new SimilarityConfig()) {

  /**
    * This method normalize a person name.
    * @param fullName String representing the full name of a person.
    * @return
    */
  def normalizePersonName(fullName: String): NormalizedName = {
    require(fullName.length > 1)

    val mutWhitespaceVariations = mutable.ArrayBuffer.empty[mutable.ArrayBuffer[String]]
    val whitespaceSplit = TextNormalizer.normalize(fullName).split(" ").map(e => TextNormalizer.normalizeNameElement(e)).toVector

    whitespaceSplit.length match {
      case x if x > 2 => buildWhitespaceVariations(whitespaceSplit, mutWhitespaceVariations, 0, whitespaceSplit.length)
      case _ => mutWhitespaceVariations += whitespaceSplit.to[mutable.ArrayBuffer]
    }

    val whitespaceVariations = mutWhitespaceVariations.map(_.toVector).toVector
    val mutNameElementCombinations = mutable.ArrayBuffer.empty[mutable.ArrayBuffer[(String, Double, Byte)]]

    whitespaceVariations.foreach(e => {
      buildPersonNameElementCombinations(e, mutNameElementCombinations)
    })

    val nameElementCombinations = mutNameElementCombinations.map(_.toVector).toVector

    NormalizedName(nameElementCombinations, fullName)
  }

  /**
    * This method normalize an organisation name.
    * @param fullName String representing a full organisation name.
    * @return
    */
  def normalizeOrganisationName(fullName: String): NormalizedName = {
    require(fullName.length > 1)

    val mutWhitespaceVariations = mutable.ArrayBuffer.empty[mutable.ArrayBuffer[String]]
    val whitespaceSplit = TextNormalizer.normalizeWithLegalForm(fullName).split(" ").map(e => TextNormalizer.normalizeNameElement(e)).toVector

    whitespaceSplit.length match {
      case x if x > 2 => buildWhitespaceVariations(whitespaceSplit, mutWhitespaceVariations, 0, whitespaceSplit.length)
      case _ => mutWhitespaceVariations += whitespaceSplit.to[mutable.ArrayBuffer]
    }

    val whitespaceVariations = mutWhitespaceVariations.map(_.toVector).toVector
    val mutNameElementCombinations = mutable.ArrayBuffer.empty[mutable.ArrayBuffer[(String, Double, Byte)]]

    whitespaceVariations.foreach(e => {
      buildOrganisationNameElementCombinations(e, mutNameElementCombinations)
    })

    val nameElementCombinations = mutNameElementCombinations.map(_.toVector).toVector

    NormalizedName(nameElementCombinations, fullName)
  }

  // ---
  private def buildWhitespaceVariations(whitespaceSplit: Vector[String], mutWhitespaceCombinations: mutable.ArrayBuffer[mutable.ArrayBuffer[String]],
                                counter: Int, length: Int): Unit = {
    val whitespaceCombination = mutable.ArrayBuffer.empty[String]
    whitespaceSplit.zipWithIndex.foreach {      
      case e if e._2 == counter && counter + 1 < length => {
        (persNameElementReducedWeight(e._1, 1.0)._2, persNameElementReducedWeight(whitespaceSplit(e._2 + 1), 1.0)._2) match {
          case (false, true) => whitespaceCombination += e._1
          case _ => {            
            (e._1.isCountry, whitespaceSplit(e._2 + 1).isCountry) match {
              case (true, _) => whitespaceCombination += e._1
              case (_, true) => whitespaceCombination += e._1
              case _ => whitespaceCombination += e._1 + whitespaceSplit(e._2 + 1)
            }            
          }
        }        
      }
      case e if e._2 == counter + 1 =>
      case e => whitespaceCombination += e._1
    }

    mutWhitespaceCombinations += whitespaceCombination

    if (counter + 1 < length)
      buildWhitespaceVariations(whitespaceSplit, mutWhitespaceCombinations, counter + 1, length)
  }

  // ---
  private def buildPersonNameElementCombinations(whitespaceVariations: Vector[String],
                                         mutNameElementCombinations: mutable.ArrayBuffer[mutable.ArrayBuffer[(String, Double, Byte)]]): Unit = {

    val mutNameElementVariationsList = mutable.ListBuffer.empty[mutable.ListBuffer[Vector[String]]]

    whitespaceVariations.foreach(e => {
      val nameElementHyphenSplit = e.split("-").toVector
      val mutNameElementVariations = mutable.ListBuffer.empty[Vector[String]]
      nameElementHyphenSplit.length match {
        case 1 => mutNameElementVariations += Vector(nameElementHyphenSplit(0))
        case _ =>
          val mutNameElementSubVariations = mutable.ArrayBuffer.empty[mutable.ArrayBuffer[String]]
          buildWhitespaceVariations(nameElementHyphenSplit, mutNameElementSubVariations, 0, nameElementHyphenSplit.length)
          mutNameElementSubVariations.foreach(v => {
            mutNameElementVariations += v.toVector
          })
      }
      mutNameElementVariationsList += mutNameElementVariations
    })

    val nameElementVariationsList = mutNameElementVariationsList.map(_.toList).toList
    val nameElementCombinationsList = combineSubLists(nameElementVariationsList)

    nameElementCombinationsList.foreach(cl => {
      val mutNameElement = mutable.ArrayBuffer.empty[(String, Double, Byte)]
      cl.foreach(e => {
        e.length match {
          case 1 =>
            mutNameElement += ((e(0), persNameElementReducedWeight(e(0), 1.0)._1, 1))
          case _ =>
            e.zipWithIndex.foreach(he => {
              he._2 match {
                case 0 => mutNameElement += ((he._1, firstNameElementReducedWeight(he._1, 1.0), 1))                
                case _ => mutNameElement += ((he._1, firstNameElementReducedWeight(he._1, 1.0 / e.length), 1))
              }
            })
        }
      })
      mutNameElementCombinations += mutNameElement
    })
  }

  // ---
  private def buildOrganisationNameElementCombinations(whitespaceVariations: Vector[String],
                                                 mutNameElementCombinations: mutable.ArrayBuffer[mutable.ArrayBuffer[(String, Double, Byte)]]): Unit = {

    val mutNameElementVariationsList = mutable.ListBuffer.empty[mutable.ListBuffer[Vector[String]]]

    whitespaceVariations.foreach(e => {
      val nameElementHyphenSplit = e.split("-").toVector
      val mutNameElementVariations = mutable.ListBuffer.empty[Vector[String]]
      nameElementHyphenSplit.length match {
        case 1 => mutNameElementVariations += Vector(nameElementHyphenSplit(0))
        case _ =>
          val mutNameElementSubVariations = mutable.ArrayBuffer.empty[mutable.ArrayBuffer[String]]
          buildWhitespaceVariations(nameElementHyphenSplit, mutNameElementSubVariations, 0, nameElementHyphenSplit.length)
          mutNameElementSubVariations.foreach(v => {
            mutNameElementVariations += v.toVector
          })
      }
      mutNameElementVariationsList += mutNameElementVariations
    })

    val nameElementVariationsList = mutNameElementVariationsList.map(_.toList).toList
    val nameElementCombinationsList = combineSubLists(nameElementVariationsList)

    nameElementCombinationsList.foreach(cl => {
      val mutNameElement = mutable.ArrayBuffer.empty[(String, Double, Byte)]
      cl.foreach(e => {
        e.length match {
          case 1 =>
            val v = getOrgNameElementTypeWeightNorm(e(0), 1.0)
            mutNameElement += ((v._3, v._2, v._1))
          case _ =>
            e.zipWithIndex.foreach(he => {
              he._2 match {
                case 0 =>                  
                  val v = getOrgNameElementTypeWeightNorm(he._1, 1.0)
                  mutNameElement += ((v._3, v._2, v._1))
                case _ =>
                  val v = getOrgNameElementTypeWeightNorm(he._1, 1.0 / e.length)
                  mutNameElement += ((v._3, v._2, v._1))
              }
            })
        }
      })
      mutNameElementCombinations += mutNameElement
    })
  }

  // ---
  private def combineSubLists[T](list: List[List[T]]): List[List[T]] = list match {
    case Nil => Nil :: Nil
    case head :: tail => val rec = combineSubLists[T](tail)
      rec.flatMap(r => head.map(t => t :: r))
  }

  // ---
  private def persNameElementReducedWeight(nameElement : String, initialWeight : Double) : (Double, Boolean) = {
    var reducedWeight : Double = 1.0
    var isReduced : Boolean = false
    nameElement match {
      case "der" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "von" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "van" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "del" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "di" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "de" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "da" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "zu" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "zur" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "ar" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "al" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "bin" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "el" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "abd" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "abdel" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "abdul" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "vonder" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "vonde" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "vander" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "vande" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "dos" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "dr" => reducedWeight = initialWeight - 0.8; isReduced = true
      case "prof" => reducedWeight = initialWeight - 0.8; isReduced = true
      case _ => reducedWeight = initialWeight
    }

    if(reducedWeight < 0) reducedWeight = 0.0

    (reducedWeight, isReduced)
  }

  // ---
  private def firstNameElementReducedWeight(nameElement : String, initialWeight : Double) : Double = {
    var reducedWeight : Double = 1.0
    nameElement match {
      case "pierre" => reducedWeight = initialWeight - 0.40
      case "peter" => reducedWeight = initialWeight - 0.40
      case "yves" => reducedWeight = initialWeight - 0.40
      case "sofia" => reducedWeight = initialWeight - 0.40
      case "sofie" => reducedWeight = initialWeight - 0.40      
      case "sophie" => reducedWeight = initialWeight - 0.40
      case "karl" => reducedWeight = initialWeight - 0.40
      case "carl" => reducedWeight = initialWeight - 0.40
      case "hans" => reducedWeight = initialWeight - 0.40
      case "jean" => reducedWeight = initialWeight - 0.40
      case "ann" => reducedWeight = initialWeight - 0.40
      case "anna" => reducedWeight = initialWeight - 0.40
      case "anne" => reducedWeight = initialWeight - 0.40
      case "klara" => reducedWeight = initialWeight - 0.40
      case "mia" => reducedWeight = initialWeight - 0.40
      case "marie" => reducedWeight = initialWeight - 0.40
      case "claude" => reducedWeight = initialWeight - 0.40
      case _ => reducedWeight = persNameElementReducedWeight(nameElement, initialWeight)._1
    }

    if(reducedWeight < 0) reducedWeight = 0
    reducedWeight
  }

  // ---
  private def orgNameElementReducedWeight(nameElement : String, initialWeight : Double) : Double = {
    var reducedWeight : Double = 1.0
    nameElement match {
      case "organisation" => reducedWeight = initialWeight - 0.5
      case "organization" => reducedWeight = initialWeight - 0.5
      case "grundung" => reducedWeight = initialWeight - 0.5
      case "gruendung" => reducedWeight = initialWeight - 0.5
      case "umgebung" => reducedWeight = initialWeight - 0.5
      case "office" => reducedWeight = initialWeight - 0.5
      case "treuhand" => reducedWeight = initialWeight - 0.5
      case "versicherung" => reducedWeight = initialWeight - 0.5
      case "insurance" => reducedWeight = initialWeight - 0.5
      case "bank" => reducedWeight = initialWeight - 0.5
      case "liquidation" => reducedWeight = initialWeight - 0.5
      case "holding" => reducedWeight = initialWeight - 0.5
      case "international" => reducedWeight = initialWeight - 0.5
      case "finanz" => reducedWeight = initialWeight - 0.5
      case "finance" => reducedWeight = initialWeight - 0.5
      case "finanzen" => reducedWeight = initialWeight - 0.5
      case "consulting" => reducedWeight = initialWeight - 0.5
      case "group" => reducedWeight = initialWeight - 0.5
      case "groupe" => reducedWeight = initialWeight - 0.5
      case "gruppe" => reducedWeight = initialWeight - 0.5
      case "assoziation" => reducedWeight = initialWeight - 0.5
      case "management" => reducedWeight = initialWeight - 0.5
      case "trading" => reducedWeight = initialWeight - 0.5
      case "development" => reducedWeight = initialWeight - 0.5
      case "mining" => reducedWeight = initialWeight - 0.5
      case "commercial" => reducedWeight = initialWeight - 0.5
      case "trust" => reducedWeight = initialWeight - 0.5
      case "air" => reducedWeight = initialWeight - 0.5
      case "immobilien" => reducedWeight = initialWeight - 0.5
      case "personalvorsorgestiftung" => reducedWeight = initialWeight - 0.5
      case "sammelstiftung" => reducedWeight = initialWeight - 0.5
      case "asset" => reducedWeight = initialWeight - 0.5
      case "privat" => reducedWeight = initialWeight - 0.5
      case "private" => reducedWeight = initialWeight - 0.5
      case "securities" => reducedWeight = initialWeight - 0.5
      case "security" => reducedWeight = initialWeight - 0.5
      case "service" => reducedWeight = initialWeight - 0.5
      case "services" => reducedWeight = initialWeight - 0.5
      case "center" => reducedWeight = initialWeight - 0.5
      case "club" => reducedWeight = initialWeight - 0.5
      case "klub" => reducedWeight = initialWeight - 0.5
      case "in" => reducedWeight = initialWeight - 0.8
      case "for" => reducedWeight = initialWeight - 0.8
      case "the" => reducedWeight = initialWeight - 0.8
      case "de" => reducedWeight = initialWeight - 0.8
      case _ => reducedWeight = initialWeight
    }

    if(reducedWeight < 0) reducedWeight = 0.0
    reducedWeight
  }

  // ---
  private def getOrgNameElementTypeWeightNorm(nameElement : String, initWeight : Double) : (Byte, Double, String) = {
    nameElement.toDeLegalForm match {
      case (true, x: String) => return (2, similarityConfig.normOrgLegalformWeight, x)
      case (_, _) =>
    }

    nameElement.toIsoCountry match {
      case (true, x: String) => return (3, similarityConfig.normOrgCountryWeight, x)
      case (_, _) =>
    }

    (1, orgNameElementReducedWeight(nameElement, initWeight), nameElement)
  }
}
