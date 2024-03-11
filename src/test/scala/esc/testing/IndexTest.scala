/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.testing

import org.scalatest.funsuite.AnyFunSuite
import esc.similarity._
import esc.index._
import esc.commons._
import esc.configuration._
import java.nio.file._

/** Test-class for the index operations (index & search).
  */
class IndexTest extends AnyFunSuite {
  val path: Path = Files.createTempDirectory("EscTest");
  nameElementSimilarityDb.addNameElementSimilarity("bcde", "qrst", 0.99)

  test("Indexer.IndexPerson.1") {
    val indexer = IndexFactory.openOrCreateIndex(
      path.toFile().getAbsolutePath(),
      "TestIndex"
    )
    assert(
      indexer.addPerson(
        new IndexPerson(
          "idp1234",
          "exipd1234",
          "Muster hans",
          List("31.01.1980", "1981"),
          List()
        )
      )
    )
    assert(
      indexer.addPerson(
        new IndexPerson(
          "idp1235",
          "exipd1235",
          "Muster hans",
          List("31.01.1980", "1981"),
          List("Deutschland")
        )
      )
    )
    assert(
      indexer.addPerson(
        new IndexPerson(
          "idp1236",
          "exipd1236",
          "Tobias Müller",
          List("31.03.1990"),
          List("Italien")
        )
      )
    )
    assert(
      indexer.addPerson(
        new IndexPerson(
          "idp1237",
          "exipd1237",
          "Vannistleroy Ivanovic",
          List(),
          List()
        )
      )
    )
    assert(
      indexer.addPerson(
        new IndexPerson(
          "idp1238",
          "exipd1238",
          "Hansjakob Huber",
          List(),
          List()
        )
      )
    )
    assert(
      indexer.addPerson(
        new IndexPerson("idp1239", "exipd1239", "Nonsense Mike", List(), List())
      )
    )
    assert(
      indexer.addPerson(
        new IndexPerson("idpCountryC", "exipd1239", "Only Country", List(), List("Vereinigte Arabische Emirate"))
      )
    )
    assert(
      indexer.addPerson(
        new IndexPerson("idpAddressSim1", "exipd1250", "Bill Mustermann", List(), List("Schweiz")) // demokratische republik kongo
      )
    )
    assert(
      indexer.addPerson(
        new IndexPerson("idpAddressSim2", "exipd1251", "Rebeka Hasenfuss", List(), List("Schweiz")) // demokratische republik kongo
      )
    )
    assert(
      indexer.addPerson(
        new IndexPerson("idpOwnSim1", "exipdOwnSim1", "Hannes Bcde", List(), List("demokratische republik kongo"))
      )
    )
    
    indexer.commit()
    indexer.close()
    val finder = IndexFactory.openIndexForSearch(
      path.toFile().getAbsolutePath(),
      "TestIndex",
      new SimilarityConfig(),
      List("nonsense"),
      List("hansjakob")
    )
    assert(finder.getDocCount() > 0)
    assert(finder.getLoadTime() != null)
    assert(
      finder.findPerson("Muster Hans", List("1979"), List("Schweiz")).size > 0
    )
    assert(finder.findPerson("Muster Hans", List("1990"), List()).size == 0)
    assert(finder.findPersonByIR("muste* Hans", List("1980"), List()).size > 0)
    assert(finder.findPersonByIR("muste*", List("1980"), List()).size > 0)
    assert(finder.findPersonByIR("muter Hans", List("1980"), List()).size > 0)
    assert(
      finder.findPersonByIR("+Hans jakob +muster ", List(), List()).size > 0
    )
    assert(finder.findPersonByIR("han*", List(), List()).size > 0)
    assert(finder.findPersonByIR("Must*+Han*", List(), List()).size > 0)
    assert(finder.findPersonByIR("Hans+Muster", List(), List()).size > 0)
    assert(finder.findPersonByIR("Hans+Jakob", List(), List()).size == 0)

    assert(finder.findPerson("Only Country", List(), List("Vereinigte Arabische Emirate")).size > 0)
    assert(finder.findPerson("Only Country", List(), List("CH")).size == 0)

    assert(
      finder
        .findByAddress(
          "Herr und Frau \n Muster Hans & Hannelore \n Bahnhofstrasse 24, 8000 Zürich Schweiz"
        )
        .size > 0
    )
    assert(
      finder
        .findByAddress(
          "Herr und Frau \n Musster Hans & Hannelore \n Bahnhofstrasse 24, 8000 Zürich Schweiz"
        )
        .size > 0
    )
    assert(
      finder
        .findByAddress(
          "Herr und Frau \n Muster Peter & Hannelore \n Bahnhofstrasse 24, 8000 Zürich Schweiz"
        )
        .size == 0
    )
    assert(
      finder
        .findByAddress(
          "Herr und Frau \n Muster Hans Via Testi 10 1234 Rom Italy"
        )
        .size > 0
    )
    assert(
      finder
        .findByAddress(
          "Mr. \n Tobias Müller \n 48 Statestreet \n 12345 Cityname \n Australia"
        )
        .size == 0
    )
    assert(
      finder
        .findByAddress(
          "Mr. \n Tobias Müller \n Hauptstrasse 24 \n CH-3000 Bern"
        )
        .size > 0
    )
    assert(
      finder
        .findByAddress(
          "Mr. \n Tobias Müller \n Hauptstrasse 24 \n 3000 Bern Italien"
        )
        .size > 0
    )
    assert(
      finder
        .findByAddress(
          "Mr. \n Tobias Müller \n Hauptstrasse 24 \n 3000 Bern Schweiz"
        )
        .size == 0
    )
    assert(
      finder
        .findByAddress(
          "Vannistleroy Ivanovic \n Hauptstrasse 24 \n 3000 Bern Schweiz"
        )
        .size > 0
    )
    assert(
      finder
        .findByAddress(
          "Vanistleray Iwanovic \n Hauptstrasse 24 \n 3000 Bern Schweiz"
        )
        .size > 0
    )
    assert(
      finder
        .findByAddress("Hansjakob Maria Jesus \n Hauptstrasse 24 \n 3000 Bern")
        .size > 0
    )
    assert(
      finder
        .findByAddress("Nonsense Mike \n Hauptstrasse 24 \n 3000 Bern")
        .size == 0
    )
    assert(
      finder
        .findByAddress("Hasenfuss Becki \n Hauptstrasse 24 \n 3000 Bern \n Schweiz")
        .size > 0
    )
    assert(
      finder
        .findByAddress("William Mustermann \n Hauptstrasse 24 \n 3000 Bern")
        .size > 0
    )    
    assert(
      finder
        .findByAddress("Hauptstrasse 7b \n Qrst Hannes \n 3000 City \n Demokratische Republik Kongo")
        .size > 0
    )   
    assert(
      finder
        .findByAddress("Hauptstrasse 7b \n Qrst Hannes \n 3000 City \n Schweiz")
        .size < 1
    )   
  }
  test("Indexer.IndexOrganisation.1") {
    val indexer = IndexFactory.openOrCreateIndex(
      path.toFile().getAbsolutePath(),
      "TestIndex"
    )
    assert(
      indexer.addOrganisation(
        new IndexOrganisation(
          "ido1234",
          "exido1234",
          "Meier Holding (Schweiz) AG",
          List(),
          List("Switzerland", "Deutschland")
        )
      )
    )
    indexer.commit()
    indexer.close()
    val finder = IndexFactory.openIndexForSearch(
      path.toFile().getAbsolutePath(),
      "TestIndex"
    )
    assert(
      finder
        .findOrganisation("Meyer Holding (Schweiz) AG", List(), List("CH"))
        .size > 0
    )
    assert(
      finder
        .findOrganisation("Meier Holding (Schweiz) AG", List(), List("Italien"))
        .size == 0
    )
    assert(
      finder.findOrganisationByIR("Meie* +Holding", List(), List()).size > 0
    )
    assert(finder.findOrganisationByIR("Meiers GmbH", List(), List()).size == 0)
  }
  test("Indexer.UpdatePerson.1") {
    val indexer = IndexFactory.openOrCreateIndex(
      path.toFile().getAbsolutePath(),
      "TestIndex"
    )
    assert(
      indexer.updatePerson(
        new IndexPerson(
          "idp1234",
          "exipd1234",
          "Muster Hansjakob",
          List("31.01.1982", "1981"),
          List()
        )
      )
    )
    indexer.commit()
    indexer.close()
    val finder = IndexFactory.openIndexForSearch(
      path.toFile().getAbsolutePath(),
      "TestIndex"
    )
    assert(
      finder
        .findPerson("Muster Hans-Jakob", List("1984"), List("Schweiz"))
        .size > 0
    )
  }
  test("Indexer.UpdateOrganisation.1") {
    val indexer = IndexFactory.openOrCreateIndex(
      path.toFile().getAbsolutePath(),
      "TestIndex"
    )
    assert(
      indexer.updateOrganisation(
        new IndexOrganisation(
          "ido1234",
          "exido1234",
          "Meier Holding (Schweiz) AG",
          List(),
          List("Switzerland", "Italien")
        )
      )
    )
    indexer.commit()
    indexer.close()
    val finder = IndexFactory.openIndexForSearch(
      path.toFile().getAbsolutePath(),
      "TestIndex"
    )
    assert(
      finder
        .findOrganisation("Meier Holding (Schweiz) AG", List(), List("Italien"))
        .size > 0
    )
  }
}
