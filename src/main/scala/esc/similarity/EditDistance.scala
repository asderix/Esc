/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.similarity

import scala.math.{max,min, abs}

/**
  * this object provides useful methods using the edit distance as
  * based method.
  */
object EditDistance {

    /**
    * Returns a weighted normalized similarity value between 0 and 1.
    * 0 means no similarity, 1 is 100% similarity/exact match.
    *
    * @param  textA  Text a) for comparison.
    * @param  textB  Text b) for comparison.
    *
    * @return Return the edit distance similarity (value between 0 and 1). 
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
          1-(ed._3/List(textA.length, textB.length).sorted.last) match {
            case e if e >= 0.79 => return e + ((1-e)/2)
            case e if e >= 0.59 => return e + ((1-e)/3)
            case e if e >= 0.39 => return e + ((1-e)/4)
            case e if e >= 0.19 => return e + ((1-e)/5)
            case e => return e + ((1-e)/6)            
          }
        }
        // Same lenght, only wrong letters (Meier vs. Meyer):
        case 0 => {          
          1-(ed._3/List(textA.length, textB.length).sorted.last) match {
            case e if e >= 0.79 => return e + ((1-e)/2.5)
            case e if e >= 0.59 => return e + ((1-e)/3.5)
            case e if e >= 0.39 => return e + ((1-e)/4.5)
            case e if e >= 0.19 => return e + ((1-e)/5.5)
            case e => return e + ((1-e)/6.5)            
          }
        }
        // Both, wrong and additional letters (Meier vs. Meyers):
        case _ => {          
          1-(ed._3/List(textA.length, textB.length).sorted.last) match {
            case e if e >= 0.79 => return e + ((1-e)/4)
            case e if e >= 0.59 => return e + ((1-e)/5)
            case e if e >= 0.39 => return e + ((1-e)/6)
            case e if e >= 0.19 => return e + ((1-e)/7)
            case e => return e + ((1-e)/8)            
          }
        }
      }
    }

    /**
      * Returns three values for the edit distance (a, b, c).
      * a = Edit distance as integer (exmpl. 2)
      * b = The reduction value as double (exmpl. 0.5)
      * c = The netto/weighted edit distance as double, a-b (exampl. 1.5)
      *
      * @param  textA  Text a) for comparison.
      * @param  textB  Text b) for comparison.
      *
      * @return Return a Tuple with the edit distande, reduction value an weighted edit distance.
      */
    def getEditDistance[A](textA: Iterable[A], textB: Iterable[A]): (Int, Double, Double) = {      
      var wr: Double = 0.0
      var lastChar: String = ""
      val ed = ((0 to textB.size).toList /: textA)((prev, x) =>
        (prev zip prev.tail zip textB).scanLeft(prev.head + 1) {                              
          case (h, ((d, v), y)) => {
            min(min(h + 1, v + 1),
              d + (if (x == y) {if (d == 0) lastChar = x.toString; 0} else {
                if (d == 0) {
                    wr = wr + getCharReplacmentReduction(x.toString, y.toString, lastChar)
                    lastChar = x.toString
                  }
                  1
              })
            )                     
          }
        }        
      ) last
        
      (ed, wr, ed - wr)
    }

    // ---
    private def getCharReplacmentReduction(charA: String, charB: String, charL: String): Double = {        
        val pair = (charA, charB, charL)           
        pair match {            
            case ("e", _, "a") => return 0.5
            case (_, "e", "a") => return 0.5

            case ("e", _, "o") => return 0.5
            case (_, "e", "o") => return 0.5
            
            case ("e", _, "u") => return 0.5
            case (_, "e", "u") => return 0.5             

            case (_, "h", "t") => return 0.5
            case ("h", _, "t") => return 0.5    

            case (_, "z", "t") => return 0.5
            case ("z", _, "t") => return 0.5                

            case ("c", "k", _) => return 0.5
            case ("k", "c", _) => return 0.5

            case ("m", "n", _) => return 0.5
            case ("n", "m", _) => return 0.5

            case ("i", "j", _) => return 0.8
            case ("j", "i", _) => return 0.8
            case ("i", "y", _) => return 0.8
            case ("y", "i", _) => return 0.8
            case ("y", "j", _) => return 0.8
            case ("j", "y", _) => return 0.8                        

            case _ => return 0.0
        }        
    }
}