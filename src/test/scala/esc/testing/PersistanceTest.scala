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
import esc.utils._
import esc.utils.Persistence._
import esc.utils.Persistence
import java.nio.file._

/** Test-class for the persistance methods.
  */
class PersistanceTest extends AnyFunSuite {
  val path: Path = Files.createTempDirectory("EscTest")
  test("Persistance.Match.1") {
    val indexer = IndexFactory.openOrCreateIndex(
      path.toFile().getAbsolutePath(),
      "TestIndex"
    )
    indexer.addPerson(
      new IndexPerson(
        "idp1234",
        "exipd1234",
        "Muster hans",
        List("31.01.1980", "1981"),
        List()
      )
    )
    indexer.commit()
    indexer.close()
    val finder = IndexFactory.openIndexForSearch(
      path.toFile().getAbsolutePath(),
      "TestIndex"
    )    
    assert(
     finder.findPerson("Muster Hans", List("1980"), List()).toCompactJson == "[{\"id\":\"idp1234\",\"externalId\":\"exipd1234\",\"foundName\":\"Muster hans\",\"matchResult\":{\"nofHits\":2,\"nofHitsWeighted\":2,\"cov\":1,\"covWeighted\":1,\"similarity\":1,\"matchPairs\":[[\"hans\",\"hans\",1,\"stringIdent\"],[\"muster\",\"muster\",1,\"stringIdent\"]]}}]"

    )
  }
}
