/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.similarity

import scala.math.{max, min, abs}
import scala.language.postfixOps

/** this object provides useful methods using the edit distance as based method.
  */
object EditDistance {

  /** Returns a weighted normalized similarity value between 0 and 1. 0 means no
    * similarity, 1 is 100% similarity/exact match.
    *
    * @param textA
    *   Text a) for comparison.
    * @param textB
    *   Text b) for comparison.
    *
    * @return
    *   Return the edit distance similarity (value between 0 and 1).
    */
  def getEditDistanceSimilarity(textA: String, textB: String): Double = {
    val ed = getEditDistance(textA, textB)

    // Distance is 0:
    ed._1 match {
      case 0 => return 1.0
      case _ =>
    }

    abs(textA.length - textB.length) match {
      // Only additional letters, no wrong letters (Meier vs. Meiers):
      case ed._1 => {
        1 - (ed._3 / List(textA.length, textB.length).sorted.last) match {
          case e if e >= 0.79 => return e + ((1 - e) / 2)
          case e if e >= 0.59 => return e + ((1 - e) / 3)
          case e if e >= 0.39 => return e + ((1 - e) / 4)
          case e if e >= 0.19 => return e + ((1 - e) / 5)
          case e              => return e + ((1 - e) / 6)
        }
      }
      // Same lenght, only wrong letters (Meier vs. Meyer):
      case 0 => {
        1 - (ed._3 / List(textA.length, textB.length).sorted.last) match {
          case e if e >= 0.79 => return e + ((1 - e) / 2.5)
          case e if e >= 0.59 => return e + ((1 - e) / 3.5)
          case e if e >= 0.39 => return e + ((1 - e) / 4.5)
          case e if e >= 0.19 => return e + ((1 - e) / 5.5)
          case e              => return e + ((1 - e) / 6.5)
        }
      }
      // Both, wrong and additional letters (Meier vs. Meyers):
      case _ => {
        1 - (ed._3 / List(textA.length, textB.length).sorted.last) match {
          case e if e >= 0.79 => return e + ((1 - e) / 4)
          case e if e >= 0.59 => return e + ((1 - e) / 5)
          case e if e >= 0.39 => return e + ((1 - e) / 6)
          case e if e >= 0.19 => return e + ((1 - e) / 7)
          case e              => return e + ((1 - e) / 8)
        }
      }
    }
  }

  /** Returns three values for the edit distance (a, b, c). a = Edit distance as
    * integer (exmpl. 2) b = The reduction value as double (exmpl. 0.5) c = The
    * netto/weighted edit distance as double, a-b (exampl. 1.5)
    *
    * @param textA
    *   Text a) for comparison.
    * @param textB
    *   Text b) for comparison.
    * @param reductionFn
    *   Optional. Internal function is used as default.
    *
    * @return
    *   Return a Tuple with the edit distande, reduction value an weighted edit
    *   distance.
    */
  def getEditDistance(
    textA: Iterable[Char],
    textB: Iterable[Char],
    reductionFn: (Char, Char, Option[Char]) => Double = getCharReplacementReduction
  ): (Double, Double, Double) = {

    val aSeq = textA.toIndexedSeq
    val bSeq = textB.toIndexedSeq

    var prevRow = (0 to bSeq.size).map(d => (d.toDouble, 0.0)).toVector
    var prevPrevRow = Vector.fill(bSeq.size + 1)((Double.MaxValue, 0.0))

    def getStepCost(char: Char, prevChar: Option[Char]): Double = {
      val isOptional = (char == 'e' && prevChar.exists(Set('a', 'o', 'u', 'i').contains)) ||
                      (char == 'h' && prevChar.exists(Set('t', 'r', 'l', 'n', 'p', 'c').contains)) ||
                      (char == 'j' && prevChar.exists(Set('i', 'y').contains)) ||                      
                      (char == 's' && prevChar.contains('t')) ||
                      (prevChar.contains(char))
      if (isOptional) 0.2 else 1.0
    }

    for (i <- 1 to aSeq.size) {
      val charA = aSeq(i - 1)
      val optPrevA = if (i > 1) Some(aSeq(i - 2)) else None
      
      val firstColCost = getStepCost(charA, optPrevA)
      var currentRow = Vector((prevRow(0)._1 + firstColCost, prevRow(0)._2 + (1.0 - firstColCost)))

      for (j <- 1 to bSeq.size) {
        val charB = bSeq(j - 1)
        val optPrevB = if (j > 1) Some(bSeq(j - 2)) else None

        if (charA == charB) {
          currentRow = currentRow :+ prevRow(j - 1)
        } else {
          // Substitution:
          val red = reductionFn(charA, charB, optPrevA)
          val subDist = prevRow(j - 1)._1 + (1.0 - red)
          val subRed  = prevRow(j - 1)._2 + red

          // Insertion:
          val insStep = if (optPrevB.contains(charB)) 0.1 else getStepCost(charB, optPrevB)
          val insDist = currentRow(j - 1)._1 + insStep
          val insRed  = currentRow(j - 1)._2 + (1.0 - insStep)

          // Deletion:
          val delStep = if (optPrevA.contains(charA)) 0.1 else getStepCost(charA, optPrevA)
          val delDist = prevRow(j)._1 + delStep
          val delRed  = prevRow(j)._2 + (1.0 - delStep)

          // Choose option:
          var (minDist, finalRed) = 
            if (subDist <= insDist && subDist <= delDist) (subDist, subRed)
            else if (insDist <= delDist) (insDist, insRed)
            else (delDist, delRed)

          // Transposition:
          if (i > 1 && j > 1 && charA == bSeq(j - 2) && charB == aSeq(i - 2)) {
            val transDist = prevPrevRow(j - 2)._1 + 0.5
            if (transDist < minDist) {
              minDist = transDist
              finalRed = prevPrevRow(j - 2)._2 + 0.5
            }
          }

          currentRow = currentRow :+ (minDist, finalRed)
        }
      }
      prevPrevRow = prevRow
      prevRow = currentRow
    }

    val (weightedDist, totalRed) = prevRow.last
    (weightedDist + totalRed, totalRed, weightedDist)
  }

  private def getCharReplacementReduction(
    charA: Char,
    charB: Char,
    charL: Option[Char]
  ): Double = {
    (charA, charB) match {
      case ('p', 'f') | ('f', 'p') | 
          ('c', 'k') | ('k', 'c') |
          ('z', 't') | ('t', 'z') => 0.9

      case ('s', 'z') | ('z', 's') |
          ('i', 'y') | ('y', 'i') |
          ('i', 'j') | ('j', 'i') |
          ('j', 'y') | ('y', 'j') |
          ('m', 'n') | ('n', 'm') |
          ('v', 'f') | ('f', 'v') |
          ('v', 'w') | ('w', 'v') => 0.8

      case ('0', 'o') | ('o', '0') | 
          ('1', 'l') | ('l', '1') |
          ('2', 'z') | ('z', '2') |
          ('5', 's') | ('s', '5') |
          ('8', 'b') | ('b', '8') => 0.8
      
      case _ => 0.0
    }
  }
}
