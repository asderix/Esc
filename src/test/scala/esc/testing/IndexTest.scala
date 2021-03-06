/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.testing

import org.scalatest.funsuite.AnyFunSuite
import esc.similarity._
import esc.index._
import esc.commons._
import java.nio.file._

/**
  * Test-class for the index operations (index & search).
  */    
 class IndexTest extends AnyFunSuite {
     val path = Files.createTempDirectory("EscTest");     

     test("Indexer.IndexPerson.1") {     
        val indexer = IndexFactory.openOrCreateIndex(path.toFile().getAbsolutePath(), "TestIndex")     
        assert(indexer.addPerson(new IndexPerson("idp1234", "exipd1234", "Muster hans", List("31.01.1980", "1981"), List())))
        indexer.commit()
        indexer.close()
        val finder = IndexFactory.openIndexForSearch(path.toFile().getAbsolutePath(), "TestIndex")
        assert(finder.getDocCount() > 0)
        assert(finder.getLoadTime() != null)
        assert(finder.findPerson("Muster Hans", List("1979"), List("Schweiz")).size > 0)
        assert(finder.findPerson("Muster Hans", List("1990"), List()).size == 0)
        assert(finder.findPersonByIR("muste* Hans", List("1980"), List()).size > 0)
        assert(finder.findPersonByIR("muste*", List("1980"), List()).size > 0)
        assert(finder.findPersonByIR("muter Hans", List("1980"), List()).size > 0)
        assert(finder.findPersonByIR("+Hans jakob +muster ", List(), List()).size > 0)
        assert(finder.findPersonByIR("han*", List(), List()).size > 0)
        assert(finder.findPersonByIR("Must*+Han*", List(), List()).size > 0)
        assert(finder.findPersonByIR("Hans+Muster", List(), List()).size > 0)
        assert(finder.findPersonByIR("Hans+Jakob", List(), List()).size == 0)
     }
     test("Indexer.IndexOrganisation.1") {          
        val indexer = IndexFactory.openOrCreateIndex(path.toFile().getAbsolutePath(), "TestIndex")     
        assert(indexer.addOrganisation(new IndexOrganisation("ido1234", "exido1234", "Meier Holding (Schweiz) AG", List(), List("Switzerland", "Deutschland"))))
        indexer.commit()
        indexer.close()
        val finder = IndexFactory.openIndexForSearch(path.toFile().getAbsolutePath(), "TestIndex")
        assert(finder.findOrganisation("Meyer Holding (Schweiz) AG", List(), List("CH")).size > 0)
        assert(finder.findOrganisation("Meier Holding (Schweiz) AG", List(), List("Italien")).size == 0)
        assert(finder.findOrganisationByIR("Meie* +Holding", List(), List()).size > 0)
        assert(finder.findOrganisationByIR("Meiers GmbH", List(), List()).size == 0)
     }
     test("Indexer.UpdatePerson.1") {
        val indexer = IndexFactory.openOrCreateIndex(path.toFile().getAbsolutePath(), "TestIndex")     
        assert(indexer.updatePerson(new IndexPerson("idp1234", "exipd1234", "Muster Hansjakob", List("31.01.1982", "1981"), List())))
        indexer.commit()
        indexer.close()
        val finder = IndexFactory.openIndexForSearch(path.toFile().getAbsolutePath(), "TestIndex")
        assert(finder.findPerson("Muster Hans-Jakob", List("1984"), List("Schweiz")).size > 0)
     }
     test("Indexer.UpdateOrganisation.1") {          
        val indexer = IndexFactory.openOrCreateIndex(path.toFile().getAbsolutePath(), "TestIndex")     
        assert(indexer.updateOrganisation(new IndexOrganisation("ido1234", "exido1234", "Meier Holding (Schweiz) AG", List(), List("Switzerland", "Italien"))))
        indexer.commit()
        indexer.close()
        val finder = IndexFactory.openIndexForSearch(path.toFile().getAbsolutePath(), "TestIndex")
        assert(finder.findOrganisation("Meier Holding (Schweiz) AG", List(), List("Italien")).size > 0)
     }
 }