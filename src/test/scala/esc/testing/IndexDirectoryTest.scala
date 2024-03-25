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
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory

/** Test-class for the index operations (index & search).
  */
class IndexDirectoryTest extends AnyFunSuite {
  val path: Path = Files.createTempDirectory("EscDirectoryTest");
  val indexDirectory: Directory = FSDirectory.open(path)

  test("Indexer.IndexPerson.D1") {
    val indexer = IndexFactory.createIndexWithDirectory(
      indexDirectory
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
    indexer.commit()
    indexer.close()
  }
  test("Indexer.IndexPerson.D2") {
    val indexer = IndexFactory.openIndexWithDirectory(
      indexDirectory
    )
    assert(
      indexer.addPerson(
        new IndexPerson(
          "idp1235",
          "exipd1235",
          "Muster Felix",
          List("31.01.1981", "1982"),
          List()
        )
      )
    )
    indexer.commit()
    indexer.close()
  }
  test("Searcher.SearchPerson.D3") {
    val finder = IndexFactory.openIndexForSearchWithDirectory(
      indexDirectory
    )
    assert(finder.getDocCount() > 0)
    assert(finder.getLoadTime() != null)
    assert(
      finder.findPerson("Muster Hans", List("1980"), List("Schweiz")).size > 0
    )
  }
}
