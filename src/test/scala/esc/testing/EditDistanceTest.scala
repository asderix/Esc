/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.testing

import org.scalatest.funsuite.AnyFunSuite
import esc.similarity._

/**
  * Test-class for edit distance tests.
  */    
 class EditDistanceTest extends AnyFunSuite {
   test("EditDistance.Same.1") {          
     assert(EditDistance.getEditDistanceSimilarity("Mustermann", "Mustermann") == 1.0)
   }
   test("EditDistance.Same.2") {          
     assert(EditDistance.getEditDistanceSimilarity("Zumhofen", "Zumhofen") == 1.0)
   }
   test("EditDistance.AddLetters.1") {          
     assert(EditDistance.getEditDistanceSimilarity("muller", "mueller") == 0.9642857142857143)
   }
   test("EditDistance.AddLetters.2") {          
     assert(EditDistance.getEditDistanceSimilarity("meier", "meiers") == 0.9166666666666667)
   }
   test("EditDistance.AddLetters.3") {          
     assert(EditDistance.getEditDistanceSimilarity("meiers", "meierso") == 0.9285714285714286)
   }
   test("EditDistance.AddLetters.4") {          
     assert(EditDistance.getEditDistanceSimilarity("ab", "abc") == 0.7777777777777778)
   }
   test("EditDistance.AddLetters.5") {          
     assert(EditDistance.getEditDistanceSimilarity("thomas", "tomas") == 0.9583333333333333)
   }
   test("EditDistance.AddLetters.6") {          
     assert(EditDistance.getEditDistanceSimilarity("jakob", "jackob") == 0.9583333333333333)
   }
   test("EditDistance.WrongLetters.1") {          
     assert(EditDistance.getEditDistanceSimilarity("Meier", "Meyer") == 0.976)
   }
   test("EditDistance.WrongLetters.2") {          
     assert(EditDistance.getEditDistanceSimilarity("claudia", "klaudia") == 0.9571428571428572)
   }
   test("EditDistance.WrongLetters.3") {          
     assert(EditDistance.getEditDistanceSimilarity("Stephan", "Steffan") == 0.7959183673469388)
   }
   test("EditDistance.WrongLetters.4") {          
     assert(EditDistance.getEditDistanceSimilarity("roni", "romi") == 0.925)
   }
   test("EditDistance.WrongAndAddLetters.1") {          
     assert(EditDistance.getEditDistanceSimilarity("Meier", "Meyers") == 0.8500000000000001)
   }
 }
