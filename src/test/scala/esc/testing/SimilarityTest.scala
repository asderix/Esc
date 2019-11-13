/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.testing

import org.scalatest.funsuite.AnyFunSuite
import esc.normalization._
import esc.similarity._
import esc.configuration._

/**
  * Test-class for normalization tests.
  */    
 class SimilarityTest extends AnyFunSuite {   
   val similarity = new NameSimilarity  
   val similarity2 = new NameSimilarity(SimilarityConfig().copy(allowOneLetterAbbreviation = true))  

   // -- Person names -- //  
   test("Similarity.PersonName.1") {
       assert(similarity.getPersonNameSimilarity("von Allmen Beat", "Vonallmen Beat").similarity == 1.0)
   }
   test("Similarity.PersonName.2") {
       assert(similarity.getPersonNameSimilarity("Hanspeter Meyer-Burger", "Hans-Peter Meyer-Burger").similarity == 1.0)
   }
   test("Similarity.PersonName.3") {
       assert(similarity.getPersonNameSimilarity("Thomas Müller", "thomas muller").similarity == 1.0)
   }
   test("Similarity.PersonName.4") {
       assert(similarity.getPersonNameSimilarity("Boris Nikolaevic Elcin", "boris jelzin").similarity > 0.91)
   }
   test("Similarity.PersonName.5") {
       assert(similarity.getPersonNameSimilarity("Boris Nikolaevič El'cin", "boris jelzin").similarity > 0.91)
   }
   test("Similarity.PersonName.6") {
       assert(similarity.getPersonNameSimilarity("Wladimir Wladimirowitsch Putin", "Vladimir Putin").similarity > 0.92)
   }
   test("Similarity.PersonName.7") {
       assert(similarity.getPersonNameSimilarity("Hans-Peter von Allmen", "Peter von Allmen").similarity > 0.90)
   }
   test("Similarity.PersonName.8") {
       assert(similarity.getPersonNameSimilarity("bill Gates", "William Henry Gates").similarity > 0.92)
   }
   test("Similarity.PersonName.9") {
       assert(similarity.getPersonNameSimilarity("Gerhard Schröder", "Gerhard Fritz Kurt Schröder").similarity > 0.92)
   }
   test("Similarity.PersonName.11") {
       assert(similarity.getPersonNameSimilarity("Hu Jintao", "Hu Chintao").similarity > 0.99)
   }
   test("Similarity.PersonName.12") {
       assert(similarity.getPersonNameSimilarity("Marlone Corti", "Marlone Conti").similarity < 0.72)
   }
   test("Similarity.PersonName.13") {
       assert(similarity.getPersonNameSimilarity("Daniel Müller", "Daniela Müller").similarity < 0.72)
   }
   test("Similarity.PersonName.14") {
       assert(similarity.getPersonNameSimilarity("Dong Fang", "Tung Fang").similarity > 0.99)
   }
   test("Similarity.PersonName.15") {
       assert(similarity.getPersonNameSimilarity("Wladimir jewtuschenkow", "Vladimir yevtushenkov").similarity > 0.99)
   }
   test("Similarity.PersonName.16") {
       assert(similarity.getPersonNameSimilarity("Heinrich B. Vonhuben", "Heinrich Benno Vonhuben").similarity < 0.95)
   }
   test("Similarity.PersonName.17") {
       assert(similarity2.getPersonNameSimilarity("Heinrich B. Vonhuben", "Heinrich Benno Vonhuben").similarity > 0.99)
   }

   // -- Organisation names -- //
   test("Similarity.OrgName.1") {
        assert(similarity.getOrganisationNameSimilarity("UBS (Schweiz) AG", "UBS (Switzerland) Ltd.").similarity == 1.0)
    }
    test("Similarity.OrgName.2") {
        assert(similarity.getOrganisationNameSimilarity("Schneider Treuhand AG", "Treuhand Schnyder AG").similarity < 0.70)
    }
    test("Similarity.OrgName.3") {
        assert(similarity.getOrganisationNameSimilarity("Luchs + Co. GmbH", "Fuchs AG").similarity < 0.20)
    }
    test("Similarity.OrgName.4") {
        assert(similarity.getOrganisationNameSimilarity("Microsoft (Schweiz) LLC", "Microspot (Schweiz) GmbH").similarity < 0.70)
    }
    test("Similarity.OrgName.5") {
        assert(similarity.getOrganisationNameSimilarity("Huber Holding AG", "Miller Holding AG").similarity < 0.70)
    }
 }

