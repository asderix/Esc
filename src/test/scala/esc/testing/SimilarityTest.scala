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
import esc.ai._

/** Test-class for normalization tests.
  */
class SimilarityTest extends AnyFunSuite {
  TestEnv.init()
  val similarity = new NameSimilarity
  val similarity2 = new NameSimilarity(
    SimilarityConfig().copy(allowOneLetterAbbreviation = true)
  )

  // -- Person names -- //
  test("Similarity.PersonName.1") {
    assert(
      similarity
        .getPersonNameSimilarity("von Allmen Beat", "Vonallmen Beat")
        .similarity == 1.0
    )
  }
  test("Similarity.PersonName.2") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Hanspeter Meyer-Burger",
          "Hans-Peter Meyer-Burger"
        )
        .similarity == 1.0
    )
  }
  test("Similarity.PersonName.3") {
    assert(
      similarity
        .getPersonNameSimilarity("Thomas Müller", "thomas muller")
        .similarity == 1.0
    )
  }
  test("Similarity.PersonName.4") {
    assert(
      similarity
        .getPersonNameSimilarity("Boris Nikolaevic Elcin", "boris jelzin")
        .similarity > 0.91
    )
  }
  test("Similarity.PersonName.5") {
    assert(
      similarity
        .getPersonNameSimilarity("Boris Nikolaevič El'cin", "boris jelzin")
        .similarity > 0.91
    )
  }
  test("Similarity.PersonName.6") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Wladimir Wladimirowitsch Putin",
          "Vladimir Putin"
        )
        .similarity > 0.92
    )
  }
  test("Similarity.PersonName.7") {
    assert(
      similarity
        .getPersonNameSimilarity("Hans-Peter von Allmen", "Peter von Allmen")
        .similarity > 0.90
    )
  }
  test("Similarity.PersonName.8") {
    assert(
      similarity
        .getPersonNameSimilarity("bill Gates", "William Henry Gates")
        .similarity > 0.92
    )
  }
  test("Similarity.PersonName.9") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Gerhard Schröder",
          "Gerhard Fritz Kurt Schröder"
        )
        .similarity > 0.92
    )
  }
  test("Similarity.PersonName.11") {
    assert(
      similarity
        .getPersonNameSimilarity("Hu Jintao", "Hu Chintao")
        .similarity > 0.99
    )
  }
  test("Similarity.PersonName.12") {
    assert(
      similarity
        .getPersonNameSimilarity("Marlone Corti", "Marlone Conti")
        .similarity < 0.72
    )
  }
  test("Similarity.PersonName.13") {
    assert(
      similarity
        .getPersonNameSimilarity("Daniel Müller", "Daniela Müller")
        .similarity < 0.72
    )
  }
  test("Similarity.PersonName.14") {
    assert(
      similarity
        .getPersonNameSimilarity("Dong Fang", "Tung Fang")
        .similarity > 0.99
    )
  }
  test("Similarity.PersonName.15") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Wladimir jewtuschenkow",
          "Vladimir yevtushenkov"
        )
        .similarity > 0.99
    )
  }
  test("Similarity.PersonName.16") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Heinrich B. Vonhuben",
          "Heinrich Benno Vonhuben"
        )
        .similarity < 0.95
    )
  }
  test("Similarity.PersonName.17") {
    assert(
      similarity2
        .getPersonNameSimilarity(
          "Heinrich B. Vonhuben",
          "Heinrich Benno Vonhuben"
        )
        .similarity > 0.99
    )
  }
  test("Similarity.PersonName.18") {
    assert(
      similarity.getPersonNameSimilarity("Morgan", "Morgan").similarity > 0.99
    )
  }
  test("Similarity.PersonName.19") {
    assert(
      similarity
        .getPersonNameSimilarity("Morgan Morgan", "Morgan Morgan")
        .similarity > 0.99
    )
  }
  test("Similarity.PersonName.20") {
    assert(similarity.getPersonNameSimilarity("abc", " abc").similarity > 0.99)
  }
  test("Similarity.PersonName.21") {
    assert(
      similarity
        .getPersonNameSimilarity("Roger Rogers", "Roger Müller")
        .similarity < 0.90
    )
  }
  test("Similarity.PersonName.22") {
    assert(similarity.getPersonNameSimilarity("Taçi", "Taci").similarity > 0.99)
  }
  test("Similarity.PersonName.23") {
    assert(
      similarity
        .getPersonNameSimilarity("André Roğğenmoser", "Andre Roggenmoser")
        .similarity > 0.99
    )
  }
  test("Similarity.PersonName.24") {
    assert(
      similarity
        .getPersonNameSimilarity("Daniela Mæder", "Daniela Maeder")
        .similarity > 0.99
    )
  }
  test("Similarity.PersonName.25") {
    assert(
      similarity
        .getPersonNameSimilarity("محمد علي", "محمد علي")
        .similarity > 0.95
    )
  }
  test("Similarity.PersonName.26") {
    assert(
      similarity
        .getPersonNameSimilarity(
          Transliterator.transToLatin("Владимир Путин"),
          "Vladimir Putin"
        )
        .similarity > 0.98
    )
  }
  test("Similarity.PersonName.27") {
    assert(
      similarity
        .getPersonNameSimilarity(
          Transliterator.transToLatin("عبد الله فاطمة"),
          "Abdallah Fatima"
        )
        .similarity > 0.95
    )
  }
  test("Similarity.PersonName.28") {
    assert(
      similarity
        .getPersonNameSimilarity(
          Transliterator.transToLatin("محمد علي"),
          "Muhammad Ali"
        )
        .similarity > 0.98
    )
  }
  test("Similarity.PersonName.29") {
    assert(
      similarity
        .getPersonNameSimilarity(
          Transliterator.transToLatin("山田 太郎"),
          "Shantian Tai Lang"
        )
        .similarity > 0.98
    )
  }
  test("Similarity.PersonName.30") {
    assert(
      similarity
        .getPersonNameSimilarity("Al-Charfawi", "Charafawi")
        .similarity > 0.95
    )
  }
  test("Similarity.PersonName.31") {
    assert(
      similarity
        .getPersonNameSimilarity("al-hasan", "alhassan")
        .similarity > 0.95
    )
  }
  test("Similarity.PersonName.32") {
    assert(
      similarity.getPersonNameSimilarity("aliev", "alijew").similarity > 0.95
    )
  }

  // -- Person names - special testset -- //
  test("Similarity.Testset.PersonName.1") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Alexander Sachartschenko",
          "Alexander Zacharczenko"
        )
        .similarity > 0.91
    )
  }
  test("Similarity.Testset.PersonName.2") {
    assert(
      similarity
        .getPersonNameSimilarity("Alexej Semenov", "Alexej Semjonow")
        .similarity > 0.91
    )
  }
  test("Similarity.Testset.PersonName.3") {
    assert(
      similarity
        .getPersonNameSimilarity("Al-Charfawi", "Charafāwī")
        .similarity > 0.91
    )
  }
  test("Similarity.Testset.PersonName.4") {
    assert(
      similarity
        .getPersonNameSimilarity("Amina Rokia", "Amina Ruqayya")
        .similarity > 0.91
    )
  }
  test("Similarity.Testset.PersonName.5") {
    assert(
      similarity
        .getPersonNameSimilarity("André Huber", "Andrea Huber")
        .similarity < 0.90
    )
  }
  test("Similarity.Testset.PersonName.6") {
    assert(
      similarity
        .getPersonNameSimilarity("Annelies Müller", "Anne1ies Mül1er")
        .similarity > 0.91
    )
  }
  test("Similarity.Testset.PersonName.7") {
    assert(
      similarity
        .getPersonNameSimilarity("Anemarie von Allmen", "Anemaria von Allmen")
        .similarity > 0.91
    )
  }
  test("Similarity.Testset.PersonName.8") {
    assert(
      similarity.getPersonNameSimilarity("Anh Mai", "Muk Anh").similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.9") {
    assert(
      similarity
        .getPersonNameSimilarity("Anne-Marie Brechbühl", "Annemarie Brechbühl")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.10") {
    assert(
      similarity
        .getPersonNameSimilarity("Arjun Rangaiah", "Arjun Rangayya")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.11") {
    assert(
      similarity
        .getPersonNameSimilarity("Artur Anatol Saizew", "Zajcev Artur")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.12") {
    assert(
      similarity
        .getPersonNameSimilarity("Aubert Dreyfuss", "Hubert Dreyfuss")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.13") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Bauyrzhan Schaqijanow",
          "Bauyrzhan Zhakiyanov"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.14") {
    assert(
      similarity
        .getPersonNameSimilarity("Bendix Kallaher", "Benedict Kallaher")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.15") {
    assert(
      similarity
        .getPersonNameSimilarity("Bibiana Zurbuchen", "Viviana Zurbucken")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.16") {
    assert(
      similarity
        .getPersonNameSimilarity("Bill Gates", "William Henry Gates")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.17") {
    assert(
      similarity
        .getPersonNameSimilarity("Bob Mc Gregor", "Robert McGregor")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.18") {
    assert(
      similarity
        .getPersonNameSimilarity("Brian Killer", "Kilian O'Brian")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.19") {
    assert(
      similarity
        .getPersonNameSimilarity("Cheung Li", "Zoeng Li")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.20") {
    assert(
      similarity
        .getPersonNameSimilarity("Christian Blocher", "Christian Locher")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.21") {
    assert(
      similarity
        .getPersonNameSimilarity("Christian Meier", "Christine Meier")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.22") {
    assert(
      similarity
        .getPersonNameSimilarity("Chun Lee", "Tang Lee")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.23") {
    assert(
      similarity
        .getPersonNameSimilarity("Daniel Burchart", "Daniel Burghart")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.24") {
    assert(
      similarity
        .getPersonNameSimilarity("Daniela Meyer", "Daniel Meier")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.25") {
    assert(
      similarity
        .getPersonNameSimilarity("Dario Zimmermann", "Darius Zimmerman")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.26") {
    assert(
      similarity
        .getPersonNameSimilarity("Davide Marino", "Mario David Rosso")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.27") {
    assert(
      similarity
        .getPersonNameSimilarity("Diana Saxer-Bühler", "Diana Sager")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.28") {
    assert(
      similarity
        .getPersonNameSimilarity("Diego Füglisthaler", "dIeGo fUgLiStHaLeR")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.29") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Dimitri Boris Schirjajew",
          "Boris Dimitri Shiryaev"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.30") {
    assert(
      similarity
        .getPersonNameSimilarity("Dimitri Schukow", "Dimitri Zhukov")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.31") {
    assert(
      similarity
        .getPersonNameSimilarity("Dirk Moser", "Dietrich Moser Heiniger")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.32") {
    assert(
      similarity
        .getPersonNameSimilarity("Djamel Ben Salah", "Dschamal Ben Salah")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.33") {
    assert(
      similarity
        .getPersonNameSimilarity("Dominik Peyer", "Dominik Meyer")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.34") {
    assert(
      similarity
        .getPersonNameSimilarity("Doris Schwab", "Doris Schwan")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.35") {
    assert(
      similarity
        .getPersonNameSimilarity("Dschingis Khan", "Zengiz Khan")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.36") {
    assert(
      similarity
        .getPersonNameSimilarity("Dung Pan", "Poon Dung")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.37") {
    assert(
      similarity
        .getPersonNameSimilarity("Freddy Hinkeler", "Hinkeler Freddy")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.38") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Gerhard Kurt Fritz Schröder",
          "Schröder Gerhard"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.39") {
    assert(
      similarity
        .getPersonNameSimilarity("Hana Sachiko", "Hana Szacsiko")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.40") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Hans-Peter Dallmayer",
          "Fritz Dallmayer-Peter"
        )
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.41") {
    assert(
      similarity
        .getPersonNameSimilarity("Hans-Peter Müller", "Hanspeter Müller-Meyer")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.42") {
    assert(
      similarity
        .getPersonNameSimilarity("Hu Jintao", "Hu Chintao")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.43") {
    assert(
      similarity
        .getPersonNameSimilarity("Jahn Tobias Birchmeier", "Tobias Hahn")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.44") {
    assert(
      similarity
        .getPersonNameSimilarity("Jean-Pierre Mullier", "Jeanpierre Mullier")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.45") {
    assert(
      similarity
        .getPersonNameSimilarity("José Maria Fernandez", "José Hernandez")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.46") {
    assert(
      similarity
        .getPersonNameSimilarity("Karim Hussein", "Karim Husain")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.47") {
    assert(
      similarity
        .getPersonNameSimilarity("Kenji Shohjiroh", "Kenji Shojiro")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.48") {
    assert(
      similarity
        .getPersonNameSimilarity("Kim Seok", "Kim Sook")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.49") {
    assert(
      similarity
        .getPersonNameSimilarity("Lee Seung-chan", "Lee Szungcshan")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.50") {
    assert(
      similarity
        .getPersonNameSimilarity("Leonid Sbarsski", "Leonid Zbarskij")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.51") {
    assert(
      similarity
        .getPersonNameSimilarity("Lorenzo Nicolai", "Nicole Lorenzo")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.52") {
    assert(
      similarity
        .getPersonNameSimilarity("Marco Kammen-Zind", "Mirco Kammenzind")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.53") {
    assert(
      similarity
        .getPersonNameSimilarity("Maria Moralez", "mª Moralez")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.54") {
    assert(
      similarity
        .getPersonNameSimilarity("Marlone Corti", "Marlone Conti")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.55") {
    assert(
      similarity
        .getPersonNameSimilarity("Markus Pričinenko", "Markus Prychynenko")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.56") {
    assert(
      similarity
        .getPersonNameSimilarity("Matthew Morgan", "Morgan Matter")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.57") {
    assert(
      similarity
        .getPersonNameSimilarity("Mike Haller", "Michael Hasler")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.58") {
    assert(
      similarity
        .getPersonNameSimilarity("Miriam Staub", "Miriam Straub")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.59") {
    assert(
      similarity
        .getPersonNameSimilarity("Møller Peter", "Möller Peter")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.60") {
    assert(
      similarity
        .getPersonNameSimilarity("Morgan Morgan", "Morgan Morgan")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.61") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Muhammad Ali",
          Transliterator.transToLatinBestGuess("محمد علي")
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.62") {
    assert(
      similarity
        .getPersonNameSimilarity("Nicole Schreiber", "Nicole Schreier")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.63") {
    assert(
      similarity
        .getPersonNameSimilarity("Nils Zürcher", "nils      zurcher")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.64") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Oussama Ahmed Al-Farouq",
          "Usama Ahmed Al-Farouq"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.65") {
    assert(
      similarity
        .getPersonNameSimilarity("Paolo di Caprio", "di Caprio Paolo")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.66") {
    assert(
      similarity
        .getPersonNameSimilarity("Rolf van der Saar", "Ives Wolf-van der Saar")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.67") {
    assert(
      similarity
        .getPersonNameSimilarity("Roberto Carlos", "R0berto Carl0s")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.68") {
    assert(
      similarity
        .getPersonNameSimilarity("Roman Sastawny", "Roman Zastavny")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.69") {
    assert(
      similarity
        .getPersonNameSimilarity("Sæbjørn Peterson", "Saebjörn Peterson")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.70") {
    assert(
      similarity
        .getPersonNameSimilarity("Sophie Cœur", "Sofie Coeur")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.71") {
    assert(
      similarity.getPersonNameSimilarity("Suharto", "Suharto").similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.72") {
    assert(
      similarity
        .getPersonNameSimilarity("Thomas Schweizer", "Schweiger Thomas")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.73") {
    assert(
      similarity
        .getPersonNameSimilarity("ṭḥomasẓ      kamiński", "Thomasz Kaminski")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.74") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Tobias Yousuf-von Huben",
          "Yusuf-von Huben Tobias"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.75") {
    assert(
      similarity
        .getPersonNameSimilarity("Van der Berg Hilde", "Van der Wal Heidi")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.76") {
    assert(
      similarity
        .getPersonNameSimilarity("Willhelm Schafer", "Schärer Willhelm")
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.PersonName.77") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Wladimir Jewtuschenkow",
          "Vladimir Yevtushenkov"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.78") {
    assert(
      similarity
        .getPersonNameSimilarity("Yang Ming", "Yeung Ming")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.79") {
    assert(
      similarity
        .getPersonNameSimilarity("Yuri Evtushenkov", "Yuri Jewtuschenkow")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.80") {
    assert(
      similarity
        .getPersonNameSimilarity("Xavier Riva-Verdi", "Xavier Riva--Verdi-")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.81") {
    assert(
      similarity
        .getPersonNameSimilarity("Zayed Khalid", "Zeid Khalid")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.82") {
    assert(
      similarity
        .getPersonNameSimilarity("Viktor Sawerucha", "Viktor Zaveroukha")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.83") {
    assert(
      similarity
        .getPersonNameSimilarity("Vitali Rascislaŭ", "Vitali Rasszislaw")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.84") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Vladimir Putin",
          Transliterator.transToLatinBestGuess("Владимир Путин")
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.85") {
    assert(
      similarity
        .getPersonNameSimilarity(
          "Volodymyr Schtschurowskyj",
          "Volodymyr Shchurovskyi"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.86") {
    assert(
      similarity
        .getPersonNameSimilarity("Yuriy Serafimovič", "Yuriy Serafymovych")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.87") {
    assert(
      similarity
        .getPersonNameSimilarity(
          Transliterator.transToLatinBestGuess("孔丘"),
          "K'ung Ch'iu"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.PersonName.88") {
    assert(
      similarity
        .getPersonNameSimilarity(
          Transliterator.transToLatinBestGuess("张伟"),
          "Zhāng Wěi"
        )
        .similarity > 0.91
    )
  }

  // -- Organisation names -- //
  test("Similarity.OrgName.1") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "UBS (Schweiz) AG",
          "UBS (Switzerland) Ltd."
        )
        .similarity == 1.0
    )
  }
  test("Similarity.OrgName.2") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Schneider Treuhand AG",
          "Treuhand Schnyder AG"
        )
        .similarity < 0.70
    )
  }
  test("Similarity.OrgName.3") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Luchs + Co. GmbH", "Fuchs AG")
        .similarity < 0.20
    )
  }
  test("Similarity.OrgName.4") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Microsoft (Schweiz) LLC",
          "Microspot (Schweiz) GmbH"
        )
        .similarity < 0.70
    )
  }
  test("Similarity.OrgName.5") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Huber Holding AG", "Miller Holding AG")
        .similarity < 0.70
    )
  }
  test("Similarity.OrgName.6") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Pharma Discounter AG",
          "pharmaceutical Discounter AG"
        )
        .similarity > 0.90
    )
  }

  // -- Organisation names - testset -- //
  test("Similarity.Testset.OrgName.1") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Abc Consulting Group",
          "Xiao Group Consulting"
        )
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.OrgName.2") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Biladis Jama'atu", "Jama Atu Biladis")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.3") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Boriokudan", "Yakuza")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.4") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Buchdruck (in Gruendung) AG",
          "Drinks4U AG (in Gründung)"
        )
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.OrgName.5") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Coop Genossenschaft",
          "Migros Genossenschaft"
        )
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.OrgName.6") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Coop Genossenschaft",
          "Coop Cooperative"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.7") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Deutsche Bank AG", "DeutscheBank AG")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.8") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Ernst & Young GmbH", "Ernst and Young")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.9") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Fondation Beyeler", "Beyeler Stiftung")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.10") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Jem’mah Ansar", "Jemmah Ansar")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.11") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Microsoft Schweiz GmbH",
          "Microsoft (Schweiz) GmbH"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.12") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Müller Immobilien GmbH & Co. KG",
          "Meier Immobilien GmbH & Co. KG"
        )
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.OrgName.13") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Oracle Corp.", "Oracle Corporation")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.14") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Private Finance Strittmatter GmbH",
          "AG Koller Private Finance"
        )
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.OrgName.15") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Sberbank (Switzerland) AG",
          "Sberbank Europe AG"
        )
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.OrgName.16") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Schneider Treuhand AG",
          "Treuhand Schnyder AG"
        )
        .similarity < 0.90
    )
  }

  test("Similarity.Testset.OrgName.17") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Smith & Son Limited",
          "Smith + Sohn GmbH"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.18") {
    assert(
      similarity
        .getOrganisationNameSimilarity("Straßenbau AG", "Strassenbau AG")
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.19") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "T-Systems Schweiz AG",
          "T-Systems Suisse SA"
        )
        .similarity > 0.91
    )
  }

  test("Similarity.Testset.OrgName.20") {
    assert(
      similarity
        .getOrganisationNameSimilarity(
          "Villiger Treuhand Holding AG",
          "Thomson Treuhand Holding AG"
        )
        .similarity < 0.90
    )
  }

  // -- Own name element similarities --//
  nameElementSimilarityDb.addNameElementSimilarity("abcd", "wxyz", 0.99)
  nameElementSimilarityDb.addNameElementSimilarity("Ronny", "Ronald", 0.01)

  test("Similarity.PersonName.100") {
    assert(
      similarity
        .getPersonNameSimilarity("Hans Abcd", "Hans Wxyz")
        .similarity > 0.98
    )
  }

  test("Similarity.PersonName.101") {
    assert(
      similarity
        .getPersonNameSimilarity("Ronny Somename", "Ronald Somename")
        .similarity < 0.72
    )
  }

  test("Similarity.PersonName.102") {
    nameElementSimilarityDb.removeNameElementSimilarity("abcd", "wxyz")
    assert(
      similarity
        .getPersonNameSimilarity("Hans Abcd", "Hans Wxyz")
        .similarity < 0.75
    )
  }

  test("Similarity.PersonName.103") {
    nameElementSimilarityDb.removeNameElementSimilarity("Ronald", "Ronny")
    assert(
      similarity
        .getPersonNameSimilarity("Ronny Somename", "Ronald Somename")
        .similarity > 0.99
    )
  }

  // -- MatchPairs -- //
  test("Similarity.PersonName.200") {
    assert(
      similarity
        .getPersonNameSimilarity("Bill Somename", "William Somename")
        .matchPairs
        .toString() == "List((somename,somename,1.0,stringIdent), (bill,william,0.97,libDb))"
    )
  }

  // -- AiAgent assessment
  test("Similarity.AiAgentAssessment.1") {
    assert(
      AiAgent.assessMatch(
        similarity.explainPersonNameSimilarity("William Morgan", "Bill Morgan")
      )
    )
  }

  test("Similarity.AiAgentAssessment.2") {
    assert(
      AiAgent.assessMatch(
        similarity.explainPersonNameSimilarity("Heidi Müller", "Daniel Graf")
      ) == false
    )
  }
}
