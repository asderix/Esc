/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.testing

import java.nio.file.Paths
import org.scalatest.funsuite.AnyFunSuite
import esc.normalization._
import esc.ai._

/** Test-class for normalization tests.
  */
class NormalizationTest extends AnyFunSuite {
  AiAgent.loadModel(Paths.get("../models/Mistral-Small-3.2-24B-Instruct-2506-Q4_0.gguf").toAbsolutePath.normalize().toString)  
  
  // -- Names -- //
  val normalizer = new NameNormalizer
  test("Normalization.PersonName.1") {
    val normalizer = new NameNormalizer
    assert(
      normalizer
        .normalizePersonName("RÒnald Möhler-Møller")
        .normNames
        .toString == "Vector(Vector((ronald,1.0,1), (mohlermoller,1.0,1)), Vector((ronald,1.0,1), (mohler,1.0,1), (moller,0.5,1)))"
    )
  }
  test("Normalization.PersonName.2") {
    val normalizer = new NameNormalizer
    assert(
      normalizer
        .normalizePersonName("Hans-Peter Meyer")
        .normNames
        .toString == "Vector(Vector((hanspeter,1.0,1), (meyer,1.0,1)), Vector((hans,0.6,1), (peter,0.3,1), (meyer,1.0,1)))"                  
    )
  }
  test("Normalization.PersonName.3") {
    val normalizer = new NameNormalizer
    assert(
      normalizer
        .normalizePersonName("Sœur Saßenach mariª")
        .normNames
        .toString == "Vector(Vector((soeursassenach,1.0,1), (maria,1.0,1)), Vector((soeur,1.0,1), (sassenachmaria,1.0,1)), Vector((soeur,1.0,1), (sassenach,1.0,1), (maria,1.0,1)))"
                     
    )
  }
  test("Normalization.PersonName.4") {
    val normalizer = new NameNormalizer
    assert(
      normalizer
        .normalizePersonName("Van de Saar Nikolowitsch")
        .normNames
        .toString == "Vector(Vector((vandesaar,1.0,1), (nikolovich,1.0,1)), Vector((vande,0.19999999999999996,1), (saarnikolovich,1.0,1)), Vector((vande,0.19999999999999996,1), (saar,1.0,1), (nikolovich,1.0,1)))"
    )
  }
  test("Normalization.PersonName.5") {
    val normalizer = new NameNormalizer
    assert(
      normalizer
        .normalizePersonName("Al-Afrabi Mohammad")
        .normNames
        .toString == "Vector(Vector((alafrabi,1.0,1), (mohammad,1.0,1)), Vector((al,0.19999999999999996,1), (afrabi,1.0,1), (mohammad,1.0,1)))"                     
    )
  }
  test("Normalization.OrgName.1") {
    val normalizer = new NameNormalizer
    assert(
      normalizer
        .normalizeOrganisationName("Huber Office (Schweiz) AG")
        .normNames
        .toString == "Vector(Vector((huberoffice,1.0,1), (ch,0.5,3), (ag,0.25,2)), Vector((huber,1.0,1), (office,0.5,1), (ch,0.5,3), (ag,0.25,2)))"
    )
  }
  test("Normalization.OrgName.2") {
    val normalizer = new NameNormalizer
    assert(
      normalizer
        .normalizeOrganisationName("Huber-Müller - - Meier AG")
        .normNames
        .toString == "Vector(Vector((hubermuller,1.0,1), (meier,0.5,1), (ag,0.25,2)), Vector((huber,1.0,1), (mullermeier,0.5,1), (ag,0.25,2)), Vector((huber,1.0,1), (muller,0.3333333333333333,1), (meier,0.3333333333333333,1), (ag,0.25,2)))"
    )
  }

  // BestGuessTransliteration
  test("Normalization.BestGuessTransliteration.1") {
    val n = Transliterator.transToLatinBestGuess("محمد علي")
    assert(
      n == "Muhammad Ali" || n == "Ali Muhammad"
    )
  }
  test("Normalization.BestGuessTransliteration.2") {
    assert(
      Transliterator.transToLatinBestGuess("Владимир Путин") == "Vladimir Putin"
    )
  }
  test("Normalization.BestGuessTransliteration.3") {
    assert(
      Transliterator.transToLatinBestGuess("Hans Vögeli") == "Hans Vögeli"
    )
  }
  test("Normalization.BestGuessTransliteration.4") {
    assert(
      Transliterator.transToLatinBestGuess("習近平 / 习近平") == "Xi Jinping"
    )
  }
  test("Normalization.BestGuessTransliteration.5") {
    val n = Transliterator.transToLatinBestGuess("山田 太郎")
    assert(
       n == "Yamada Taro" || n == "Yamada Tarou"
    )
  }
  test("Normalization.BestGuessTransliteration.6") {
    assert(
      Transliterator.transToLatinBestGuess("ராமன் குமார்") == "raman kumar"
    )
  }
  test("Normalization.BestGuessTransliteration.7") {
    assert(
      Transliterator.transToLatinBestGuess("Γεώργιος Παπαδόπουλος") == "Georgios Papadopoulos"
    )
  }

  // -- Dates -- //
  test("Normalization.Date.mMMMdyyyy") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("May 13, 2019")
        .toString == "NormalizedDate(2019,5,13,0)"
    )
  }
  test("Normalization.Date.ddMMyyyy") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("31.12.2019")
        .toString == "NormalizedDate(2019,12,31,0)"
    )
  }
  test("Normalization.Date.dMMyyyy") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("1.12.2019")
        .toString == "NormalizedDate(2019,12,1,0)"
    )
  }
  test("Normalization.Date.ddMyyyy") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("31.1.2019")
        .toString == "NormalizedDate(2019,1,31,0)"
    )
  }
  test("Normalization.Date.dMyyyy") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("1.1.2019")
        .toString == "NormalizedDate(2019,1,1,0)"
    )
  }
  test("Normalization.Date.yyyyMMdd") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("2019/12/31")
        .toString == "NormalizedDate(2019,12,31,0)"
    )
  }
  test("Normalization.Date.yyyyMMd") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("2019/12/1")
        .toString == "NormalizedDate(2019,12,1,0)"
    )
  }
  test("Normalization.Date.yyyyMdd") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("2019/7/31")
        .toString == "NormalizedDate(2019,7,31,0)"
    )
  }
  test("Normalization.Date.yyyyMd") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("2019/7/7")
        .toString == "NormalizedDate(2019,7,7,0)"
    )
  }
  test("Normalization.Date.yyyyMM") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("2019.12")
        .toString == "NormalizedDate(2019,12,0,1)"
    )
  }
  test("Normalization.Date.yyyyM") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("2019.7")
        .toString == "NormalizedDate(2019,7,0,1)"
    )
  }
  test("Normalization.Date.yyyy") {
    val normalizer = DateNormalizer
    assert(
      normalizer.normalizeDate("1998").toString == "NormalizedDate(1998,0,0,2)"
    )
  }
  test("Normalization.Date.yyyyMMdds") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("20191231")
        .toString == "NormalizedDate(2019,12,31,0)"
    )
  }
  test("Normalization.Date.mMMMdyyyyInvalid") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("May 32, 2019")
        .toString == "NormalizedDate(0,0,0,99)"
    )
  }
  test("Normalization.Date.ddMMyyyyInvalid") {
    val normalizer = DateNormalizer
    assert(
      normalizer
        .normalizeDate("31.13.2019")
        .toString == "NormalizedDate(0,0,0,99)"
    )
  }
  // -- Addresses -- //
  val addressNormalizer = new AddressNormalizer
  test("Normalization.Address.1") {
    assert(
      addressNormalizer
        .normalizeAddress(
          "Herr und Frau\nSœur Saßenach und Thomas\nIrgendeinestr. 24\n8000 Zürich\nSchweiz"
        )
        .addressItems
        .toString == "List(herr, und, frau, soeur, sassenach, und, thomas, irgendeinestr, 24, 8000, zurich)"
    )
    assert(
      addressNormalizer
        .normalizeAddress(
          "Herr und Frau\nSœur Saßenach und Thomas\nIrgendeinestr. 24\n8000 Zürich\nSchweiz"
        )
        .countries
        .toString == "List(ch)"
    )
  }
  test("Normalization.Address.2") {
    assert(
      addressNormalizer
        .normalizeAddress("Mr.\nRubens Ykai\n847 Super-Street\nAU-78954 Sidney")
        .addressItems
        .toString == "List(mr, rubens, ykai, 847, super-street, au-78954, sidney)"
    )
    assert(
      addressNormalizer
        .normalizeAddress("Mr.\nRubens Ykai\n847 Super-Street\nAU-78954 Sidney")
        .countries
        .toString == "List()"
    )
  }
  test("Normalization.Address.3") {
    assert(
      addressNormalizer
        .normalizeAddress(
          "Mr Jochen Andersson, Bareggweg 124b, 1010 Wien Austria"
        )
        .addressItems
        .toString == "List(mr, jochen, andersson, bareggweg, 124b, 1010, wien)"
    )
    assert(
      addressNormalizer
        .normalizeAddress(
          "Mr Jochen Andersson, Bareggweg 124b, 1010 Wien Austria"
        )
        .countries
        .toString == "List(at)"
    )
  }
  test("Normalization.Address.4") {
    assert(
      addressNormalizer
        .normalizeAddress("Mr\nNguyen\n47896 swasi\nEswatini")
        .countries
        .toString == "List(sz)"
    )
  }
}
