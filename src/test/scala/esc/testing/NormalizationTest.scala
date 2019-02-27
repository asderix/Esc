/**
  * author:   Ronny Fuchs, info@asderix.com
  * licence:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.testing

import org.scalatest.FunSuite
import esc.normalization._

/**
  * Test-class for normalization tests.
  */    
 class NormalizationTest extends FunSuite {
   // -- Names -- //
   test("Normalization.PersonName.1") {
       val normalizer = new NameNormalizer    
     assert(normalizer.normalizePersonName("RÒnald Möhler-Møller").normNames.toString == "Vector(Vector((ronald,1.0,1), (mohlermoller,1.0,1)), Vector((ronald,1.0,1), (mohler,1.0,1), (moller,0.5,1)))")
   }
   test("Normalization.PersonName.2") {
       val normalizer = new NameNormalizer    
     assert(normalizer.normalizePersonName("Hans-Peter Meyer").normNames.toString == "Vector(Vector((hanspeter,1.0,1), (meyer,1.0,1)), Vector((hans,0.5,1), (peter,0.5,1), (meyer,1.0,1)))")
   }
   test("Normalization.PersonName.3") {
       val normalizer = new NameNormalizer    
     assert(normalizer.normalizePersonName("Sœur Saßenach mª").normNames.toString == "Vector(Vector((soeursassenach,1.0,1), (maria,1.0,1)), Vector((soeur,1.0,1), (sassenachmaria,1.0,1)), Vector((soeur,1.0,1), (sassenach,1.0,1), (maria,1.0,1)))")
   }
   test("Normalization.PersonName.4") {
       val normalizer = new NameNormalizer    
     assert(normalizer.normalizePersonName("Van de Saar Nikolowitsch").normNames.toString == "Vector(Vector((vandesaar,1.0,1), (nikolovic,1.0,1)), Vector((vande,0.5,1), (saarnikolovic,1.0,1)), Vector((vande,0.5,1), (saar,1.0,1), (nikolovic,1.0,1)))")
   }

   test("Normalization.OrgName.1") {
       val normalizer = new NameNormalizer    
     assert(normalizer.normalizeOrganisationName("Huber Office (Schweiz) AG").normNames.toString == "Vector(Vector((huberoffice,1.0,1), (ch,0.5,3), (ag,0.5,2)), Vector((huber,1.0,1), (officeschweiz,1.0,1), (ag,0.5,2)), Vector((huber,1.0,1), (office,0.5,1), (schweizag,1.0,1)), Vector((huber,1.0,1), (office,0.5,1), (ch,0.5,3), (ag,0.5,2)))")
   }
   test("Normalization.OrgName.2") {
       val normalizer = new NameNormalizer    
     assert(normalizer.normalizeOrganisationName("Huber-Müller - - Meier AG").normNames.toString == "Vector(Vector((hubermuller,1.0,1), (meier,0.5,1), (ag,0.5,2)), Vector((huber,1.0,1), (mullermeier,0.5,1), (ag,0.5,2)), Vector((huber,1.0,1), (muller,0.3333333333333333,1), (meier,0.3333333333333333,1), (ag,0.5,2)))")
   }
   // -- Dates -- //
   test("Normalization.Date.mMMMdyyyy") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("May 13, 2019").toString == "NormalizedDate(2019,5,13,0)")
   }
   test("Normalization.Date.ddMMyyyy") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("31.12.2019").toString == "NormalizedDate(2019,12,31,0)")
   }
   test("Normalization.Date.dMMyyyy") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("1.12.2019").toString == "NormalizedDate(2019,12,1,0)")
   }
   test("Normalization.Date.ddMyyyy") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("31.1.2019").toString == "NormalizedDate(2019,1,31,0)")
   }
   test("Normalization.Date.dMyyyy") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("1.1.2019").toString == "NormalizedDate(2019,1,1,0)")
   }
   test("Normalization.Date.yyyyMMdd") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("2019/12/31").toString == "NormalizedDate(2019,12,31,0)")
   }
   test("Normalization.Date.yyyyMMd") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("2019/12/1").toString == "NormalizedDate(2019,12,1,0)")
   }
   test("Normalization.Date.yyyyMdd") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("2019/7/31").toString == "NormalizedDate(2019,7,31,0)")
   }
   test("Normalization.Date.yyyyMd") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("2019/7/7").toString == "NormalizedDate(2019,7,7,0)")
   }
   test("Normalization.Date.yyyyMM") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("2019.12").toString == "NormalizedDate(2019,12,0,1)")
   }
   test("Normalization.Date.yyyyM") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("2019.7").toString == "NormalizedDate(2019,7,0,1)")
   }
   test("Normalization.Date.yyyy") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("1998").toString == "NormalizedDate(1998,0,0,2)")
   }
   test("Normalization.Date.yyyyMMdds") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("20191231").toString == "NormalizedDate(2019,12,31,0)")
   }
   test("Normalization.Date.mMMMdyyyyInvalid") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("May 32, 2019").toString == "NormalizedDate(0,0,0,99)")
   }
   test("Normalization.Date.ddMMyyyyInvalid") {
     val normalizer = new DateNormalizer
     assert(normalizer.normalizeDate("31.13.2019").toString == "NormalizedDate(0,0,0,99)")
   }
 }

