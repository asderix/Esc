/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.index

import esc.configuration._
import esc.commons._
import esc.utils.LegalForm._
import esc.utils.Countries._
import esc.utils.Persistence._
import esc.normalization._
import esc.similarity._
import scala.collection.mutable
import org.apache.lucene.analysis.standard.StandardAnalyzer._
import org.apache.lucene.document._
import org.apache.lucene.document.Document._
import org.apache.lucene.document.Field._
import org.apache.lucene.index.CorruptIndexException._
import org.apache.lucene.index.IndexWriter._
import org.apache.lucene.index.Term
import org.apache.lucene.store.Directory._
import org.apache.lucene.store.FSDirectory._
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.util.Version._

/** Class that provides the necessary functions to index individuals and
  * organisations. As a help you can create an instance of the class using the
  * IndexFactory (recommended).
  *
  * @param indexWriter
  *   Lucene IndexWriter.
  * @param similarityConfig
  *   Optional. A SimilarityConfiguration.
  */
class Indexer(
    val indexWriter: IndexWriter,
    val similarityConfig: SimilarityConfig = new SimilarityConfig()
) {
  private val nameNormalizer = new NameNormalizer(similarityConfig)

  /** Close the index.
    */
  def close(): Boolean = {
    indexWriter.close()
    true
  }

  /** Commit the changes to the index.
    */
  def commit(): Boolean = {
    indexWriter.commit()
    true
  }

  /** Add a person to the index. If a person has several names (e.g. alternative
    * spellings, abbreviations or artist names etc.), the person must be added
    * several times. You can add the same id several times. But this is not
    * recommended! You get problems when updating the person by id. Recommended
    * solution: Add different ids (e.g. with post-fix) and use the externalId as
    * your unique id. You can configure (SimilarityConfig) whether the best
    * match is selected/grouped by id oder externalId.
    *
    * @param person
    *   The person object for adding to the index.
    */
  def addPerson(person: IndexPerson): Boolean = {
    val normalizedName = nameNormalizer.normalizePersonName(person.fullName)

    indexWriter.addDocument(
      createDocument(
        person.id,
        person.externalId,
        person.fullName,
        normalizedName,
        normalizedName.normNames.toCompactJson,
        Constants.IndexPersonNameTypeCode,
        person.datesOfBirth,
        person.countries,
        person.label
      )
    )
    true
  }

  /** Add an organisation to the index. If an organisation has several names
    * (e.g. alternative spellings, abbreviations etc.), the organisation must be
    * added several times. You can add the same id several times. But this is
    * not recommended! You get problems when updating the person by id.
    * Recommended solution: Add different ids (e.g. with post-fix) and use the
    * externalId as your unique id. You can configure (SimilarityConfig) whether
    * the best match is selected/grouped by id oder externalId.
    *
    * @param organisation
    *   The organisation object for adding to the index.
    */
  def addOrganisation(organisation: IndexOrganisation): Boolean = {
    val normalizedName =
      nameNormalizer.normalizeOrganisationName(organisation.fullName)

    indexWriter.addDocument(
      createDocument(
        organisation.id,
        organisation.externalId,
        organisation.fullName,
        normalizedName,
        normalizedName.normNames.toCompactJson,
        Constants.IndexOrganisationNameTypeCode,
        organisation.datesOfFounding,
        organisation.countries,
        organisation.label
      )
    )
    true
  }

  /** Update a person.
    *
    * @param person
    *   IndexPerson with the new values. The id is relevant for selection the
    *   person to update.
    */
  def updatePerson(person: IndexPerson): Boolean = {
    val normalizedName = nameNormalizer.normalizePersonName(person.fullName)

    indexWriter.updateDocument(
      new Term("id", person.id),
      createDocument(
        person.id,
        person.externalId,
        person.fullName,
        normalizedName,
        normalizedName.normNames.toCompactJson,
        Constants.IndexPersonNameTypeCode,
        person.datesOfBirth,
        person.countries,
        person.label
      )
    )
    true
  }

  /** Update an organisation.
    *
    * @param organisation
    *   IndexOrganisation with the new values. The id is relevant for selection
    *   the person to update.
    */
  def updateOrganisation(organisation: IndexOrganisation): Boolean = {
    val normalizedName =
      nameNormalizer.normalizeOrganisationName(organisation.fullName)

    indexWriter.updateDocument(
      new Term("id", organisation.id),
      createDocument(
        organisation.id,
        organisation.externalId,
        organisation.fullName,
        normalizedName,
        normalizedName.normNames.toCompactJson,
        Constants.IndexOrganisationNameTypeCode,
        organisation.datesOfFounding,
        organisation.countries,
        organisation.label
      )
    )
    true
  }

  /** Removes a person from the index.
    *
    * @param personId
    *   The id of the person (the IndexPerson.id attribute when added).
    */
  def removePerson(personId: String): Boolean = {
    indexWriter.deleteDocuments(new Term("id", personId))

    true
  }

  /** Removes an organisation from the index.
    *
    * @param organisationId
    *   The id of the organisation (the IndexOrganisation.id attribute when
    *   added).
    */
  def removeOrganisation(organisationId: String): Boolean = {
    indexWriter.deleteDocuments(new Term("id", organisationId))

    true
  }

  //
  private def createDocument(
      id: String,
      externalId: String,
      fullName: String,
      normalizedName: NormalizedName,
      jsonName: String,
      nameType: String,
      dates: List[String],
      countries: List[String],
      label: String
  ): Document = {
    val doc = new Document()

    doc.add(new StringField("id", id, Field.Store.YES))
    doc.add(new StringField("exid", externalId, Field.Store.YES))
    doc.add(new StringField("name", fullName, Field.Store.YES))
    doc.add(new TextField("ir", fullName, Field.Store.NO))

    var nameDoc = mutable.ListBuffer.empty[String]
    for (variant <- normalizedName.normNames) {
      for (element <- variant) {
        element._3 match {
          case 1 => {
            nameNormalizer.persNameElementReducedWeight(element._1, 1.0) match {
              case (_, false) => {
                nameDoc += nameElementSimilarityDb
                  .getMatchList(
                    element._1,
                    similarityConfig.nameElementSimilarityForHit
                  )
                  .mkString(" ")
                nameDoc += element._1.sliding(3).toList.mkString(" ")
              }
              case (_, true) =>
            }
          }
          case _ => nameDoc += element._1
        }
      }
    }
    doc.add(new TextField("doc", nameDoc.mkString(" "), Field.Store.NO))
    doc.add(new StringField("json", jsonName, Field.Store.YES))

    val csList = countries
      .map(c =>
        TextNormalizer.normalize(c).toIsoCountry match {
          case (true, x)  => x
          case (false, x) => " "
        }
      )
      .filter(_ != " ")
    csList.length match {
      case 0 => doc.add(new TextField("cs", "xx", Field.Store.NO))
      case _ =>
        doc.add(new TextField("cs", csList.mkString(" "), Field.Store.YES))
    }

    val ysList = dates
      .map(y => {
        val nd = DateNormalizer.normalizeDate(y)
        nd.dateType match {
          case 99 => " "
          case _  => nd.year.toString
        }
      })
      .filter(_ != " ")
    ysList.length match {
      case 0 => doc.add(new TextField("ys", "9999", Field.Store.NO))
      case _ =>
        doc.add(new TextField("ys", ysList.mkString(" "), Field.Store.NO))
    }

    doc.add(new StringField("l", label, Field.Store.YES))
    doc.add(new StringField("t", nameType, Field.Store.YES))
    doc
  }
}
