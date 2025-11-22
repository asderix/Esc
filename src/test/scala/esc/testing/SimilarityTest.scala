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
   test("Similarity.PersonName.18") {
       assert(similarity.getPersonNameSimilarity("Morgan", "Morgan").similarity > 0.99)
   }
   test("Similarity.PersonName.19") {
       assert(similarity.getPersonNameSimilarity("Morgan Morgan", "Morgan Morgan").similarity > 0.99)
   }
   test("Similarity.PersonName.20") {
       assert(similarity.getPersonNameSimilarity("abc", " abc").similarity > 0.99)
   }
   test("Similarity.PersonName.21") {
       assert(similarity.getPersonNameSimilarity("Roger Rogers", "Roger Müller").similarity < 0.90)
   }
   test("Similarity.PersonName.22") {
       assert(similarity.getPersonNameSimilarity("Taçi", "Taci").similarity > 0.99)
   }
   test("Similarity.PersonName.23") {
       assert(similarity.getPersonNameSimilarity("André Roğğenmoser", "Andre Roggenmoser").similarity > 0.99)
   }
   test("Similarity.PersonName.24") {
       assert(similarity.getPersonNameSimilarity("Daniela Mæder", "Daniela Maeder").similarity > 0.99)
   }
   test("Similarity.PersonName.25") {
       assert(similarity.getPersonNameSimilarity("محمد علي", "محمد علي").similarity > 0.95)
   }
   test("Similarity.PersonName.26") {
       assert(similarity.getPersonNameSimilarity(Transliterator.transToLatin("Владимир Путин"), "Vladimir Putin").similarity > 0.98)
   }
   test("Similarity.PersonName.27") {
       assert(similarity.getPersonNameSimilarity(Transliterator.transToLatin("عبد الله فاطمة"), "Abdallah Fatima").similarity > 0.95)
   }
   test("Similarity.PersonName.28") {
       assert(similarity.getPersonNameSimilarity(Transliterator.transToLatin("محمد علي"), "Muhammad Ali").similarity > 0.98)
   }
   test("Similarity.PersonName.29") {       
       assert(similarity.getPersonNameSimilarity(Transliterator.transToLatin("山田 太郎"), "Shantian Tai Lang").similarity > 0.98)
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
    test("Similarity.OrgName.6") {
        assert(similarity.getOrganisationNameSimilarity("Pharma Discounter AG", "pharmaceutical Discounter AG").similarity > 0.90)
    }

    // -- Own name element similarities --//
    nameElementSimilarityDb.addNameElementSimilarity("abcd", "wxyz", 0.99)
    nameElementSimilarityDb.addNameElementSimilarity("Ronny", "Ronald", 0.01)

    test("Similarity.PersonName.100") {
       assert(similarity.getPersonNameSimilarity("Hans Abcd", "Hans Wxyz").similarity > 0.98)
    }

    test("Similarity.PersonName.101") {
       assert(similarity.getPersonNameSimilarity("Ronny Somename", "Ronald Somename").similarity < 0.72)
    }    

    test("Similarity.PersonName.102") {
        nameElementSimilarityDb.removeNameElementSimilarity("abcd", "wxyz")
        assert(similarity.getPersonNameSimilarity("Hans Abcd", "Hans Wxyz").similarity < 0.75)
    }

    test("Similarity.PersonName.103") {
        nameElementSimilarityDb.removeNameElementSimilarity("Ronald", "Ronny")
        assert(similarity.getPersonNameSimilarity("Ronny Somename", "Ronald Somename").similarity > 0.99)
    }

    // -- MatchPairs -- //
    test("Similarity.PersonName.200") {
       assert(similarity.getPersonNameSimilarity("Bill Somename", "William Somename").matchPairs.toString() == "List((somename,somename,1.0,stringIdent), (bill,william,0.97,libDb))")
    }
 }

