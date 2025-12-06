/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.testing

import org.scalatest.funsuite.AnyFunSuite
import esc.normalization._
import esc.similarity._
import esc.configuration._
import esc.utils._
import esc.utils.Persistence._
import esc.ai._

/**
  * Test-class for explain function tests.
  */    
 class SimilarityExplainTest extends AnyFunSuite {   
   val similarity = new NameSimilarity  
   val similarity2 = new NameSimilarity(SimilarityConfig().copy(allowOneLetterAbbreviation = true))

   // -- Person names -- //  
   test("SimilarityExplain.PersonName.1") {
    assert(similarity.explainPersonNameSimilarity("Hans Muster", "Hans Muster").similarity == 1.0)
   }
   test("SimilarityExplain.PersonName.2") {
    assert(similarity.explainPersonNameSimilarity("Hans Muster", "Hans Muster").toCompactJson == "{\"nameType\":\"p\",\"sourceNameA\":\"Hans Muster\",\"sourceNameB\":\"Hans Muster\",\"nameTextNormalizedA\":\"hans muster\",\"nameTextNormalizedB\":\"hans muster\",\"nameNormalizedNameA\":[[{\"nameElement\":\"hans\",\"weight\":1,\"elementType\":1},{\"nameElement\":\"muster\",\"weight\":1,\"elementType\":1}]],\"nameNormalizedNameB\":[[{\"nameElement\":\"hans\",\"weight\":1,\"elementType\":1},{\"nameElement\":\"muster\",\"weight\":1,\"elementType\":1}]],\"nofHits\":2,\"nofHitsWeighted\":2,\"cov\":1,\"covWeighted\":1,\"similarity\":1,\"matchPairs\":[{\"elementA\":\"muster\",\"elementB\":\"muster\",\"similarity\":1,\"sourceOfSimilarity\":\"stringIdent\"},{\"elementA\":\"hans\",\"elementB\":\"hans\",\"similarity\":1,\"sourceOfSimilarity\":\"stringIdent\"}]}")
   }

   // -- Organisation names -- //  
   test("SimilarityExplain.OrganisationName.1") {
    assert(similarity.explainOrganisationNameSimilarity("UBS (Schweiz) AG", "UBS (Switzerland) ltd.").similarity == 1.0)
   }

   // -- AiAgent
   test("SimilarityExplainAi.Print.1") {
    println(AiAgent.explainMatch(similarity.explainPersonNameSimilarity("Vladimir Yevtushenkov", "Wladimir Jewtuschenkow")))
    assert(true)
   }
   test("SimilarityExplainAi.Print.2") {
    println(AiAgent.explainMatch(similarity.explainPersonNameSimilarity("Vladimir Yevtushenkov", "Wladimir Jewtuschenkow"), "Deutsch"))
    assert(true)
   }
 }
