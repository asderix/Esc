/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.index

import esc.configuration._
import esc.commons._
import esc.utils.LegalForm._
import esc.utils.Countries._
import esc.utils.Persistence._
import esc.normalization._
import esc.similarity._
import esc.utils._
import esc.utils.Persistence._
import scala.collection.mutable
import scala.math._
import org.apache.lucene.analysis._
import org.apache.lucene.analysis.standard.StandardAnalyzer._
import org.apache.lucene.document._
import org.apache.lucene.document.Document._
import org.apache.lucene.document.Field._
import org.apache.lucene.index.CorruptIndexException._
import org.apache.lucene.index.IndexWriter._
import org.apache.lucene.store.Directory._
import org.apache.lucene.store.FSDirectory._
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.Term
import org.apache.lucene.util.Version._
import org.apache.lucene.search._
import org.apache.lucene.queryparser.classic._
import org.apache.lucene.analysis._
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper
import org.apache.lucene.analysis.core.WhitespaceAnalyzer

/**
  * Class that provides the necessary functions to find individuals and organizations.
  * As a help you can create an instance of the class using the IndexFactory.
  *
  * @param  indexSearcher  Lucene IndexSearcher.
  * @param  similarityConfig  Optional.Similarity configuration.
  */
class Finder(val indexSearcher : IndexSearcher, val similarityConfig : SimilarityConfig = new SimilarityConfig()) {
    val nameNormalizer = new NameNormalizer(similarityConfig)
    val nameSimilarity = new NameSimilarity(similarityConfig)

    /**
      * Method to find a person by name with countries and dates of birth as filter. Optional
      * you can use label as an additional filter (if is indexed).
      *
      * @param  fullName  The full name of the person, e.g. with middle- or maiden namen. But without title etc.
      * @param  datesOfBirth  A list with known dates of birth. See DataNormalizer for supported string formats. Must not be null, empty list is allowed.
      * @param  countries  A list with known countries. E.g. domicile, citizenship, country of birth etc. ISO-2 codes an names in en, de, fr and it are supported. Must not be null, empty list is allowed.
      * @param  label  Optional. You can provide a label as search filter - if you have used them for indexing.
      *
      * @return Returns a list of FinderMatch with the match details. If no matches are found an empty list is returned.
      */
    def findPerson(fullName : String, datesOfBirth : List[String], countries : List[String], label : String = "") : List[FinderMatch] = {   
        val normName = nameNormalizer.normalizePersonName(fullName)
        val query = createNmQuery(normName, datesOfBirth, countries, Constants.IndexPersonNameTypeCode, label)        
        searchNmQuery(query, normName)
    }

    /**
      * Method to search a person using a lucene query string for the name part.
      *
      * @param  nameQuery  The lucene query string for searching the name.
      * @param  datesOfBirth  A list with known dates of birth. See DataNormalizer for supported string formats. Must not be null, empty list is allowed.
      * @param  countries  A list with known countries. E.g. domicile, citizenship, country of birth etc. ISO-2 codes an names in en, de, fr and it are supported. Must not be null, empty list is allowed.
      * @param  label  Optional. You can provide a label as search filter - if you have used them for indexing.
      *
      * @return Returns a list of FinderMatch with the match details. If no matches are found an empty list is returned.
      */
    def findPersonByIR(nameQuery : String, datesOfBirth : List[String], countries : List[String], label : String = "") : List[FinderMatch] = {
        // Implementation here.
        List[FinderMatch]() 

    }

    /**
      * Method to find an organisation by name with countries and dates of founding as filter. Optional
      * you can use label as an additional filter (if is indexed).
      * 
      * @param  fullName  The full name of the organisation, incl. the legal form.
      * @param  datesOfFounding  A list with known dates of founding. See DataNormalizer for supported string formats. Must not be null, empty list is allowed.
      * @param  countries  A list with known countries. Most likeley the domicile(s). ISO-2 codes an names in en, de, fr and it are supported. Must not be null, empty list is allowed.
      * @param  label  Optional. You can provide a label as search filter - if you have used them for indexing.
      *
      * @return Returns a list of FinderMatch with the match details. If no matches are found an empty list is returned.
      */
    def findOrganisation(fullName : String, datesOfFounding : List[String], countries : List[String], label : String = "") : List[FinderMatch] = {   
        val normName = nameNormalizer.normalizeOrganisationName(fullName)
        val query = createNmQuery(normName, datesOfFounding, countries, Constants.IndexOrganisationNameTypeCode, label)        
        searchNmQuery(query, normName)
    }

    /**
      * Method to find an organisation by lucene query string.
      * 
      * @param  nameQuery  The lucene query string for searching the name part.
      * @param  datesOfFounding  A list with known dates of founding. See DataNormalizer for supported string formats. Must not be null, empty list is allowed.
      * @param  countries  A list with known countries. Most likeley the domicile(s). ISO-2 codes an names in en, de, fr and it are supported. Must not be null, empty list is allowed.
      * @param  label  Optional. You can provide a label as search filter - if you have used them for indexing.
      *
      * @return Returns a list of FinderMatch with the match details. If no matches are found an empty list is returned.
      */
    def findOrganisationByIR(nameQuery : String, datesOfFounding : List[String], countries : List[String], label : String = "") : List[FinderMatch] = {
        // Implementation.
        List[FinderMatch]() 
    }

    // ---
    private def searchNmQuery(booleanQuery : BooleanQuery, normNameA : NormalizedName) : List[FinderMatch] = {
        val hits = indexSearcher.search(booleanQuery, similarityConfig.maxNumberOfCandidatesFromSearch);

        if (hits.totalHits.value < 1)
            return List[FinderMatch]()            

        val mutAllMatches = mutable.ListBuffer.empty[(String, FinderMatch)]
        for (scoreDoc <- hits.scoreDocs) {
            val doc = indexSearcher.doc(scoreDoc.doc)
            val normNameB = NormalizedName(doc.get("json").toNormalizedNameVector, "")            
            val simMatch = nameSimilarity.getNameSimilarity(normNameA, normNameB)                  

            similarityConfig.matchSelectionMode match {                
                case 1 if simMatch.nofHits >= similarityConfig.numberOfHitsForSearchHit => {                    
                    val id = doc.get("id")
                    val exid = doc.get("exid")
                    val name = doc.get("name")
                    val finderMatch = FinderMatch(id, exid, name, simMatch)
                    val grpId = similarityConfig.searchEntityGroupMode match {
                        case 1 => id
                        case _ => exid
                    }
                    mutAllMatches += ((grpId, finderMatch))
                }
                case _ if simMatch.similarity >= similarityConfig.similarityValueForSearchHit => {                    
                    val id = doc.get("id")
                    val exid = doc.get("exid")
                    val name = doc.get("name")
                    val finderMatch = FinderMatch(id, exid, name, simMatch)
                    val grpId = similarityConfig.searchEntityGroupMode match {
                        case 1 => id
                        case _ => exid
                    }
                    mutAllMatches += ((grpId, finderMatch))
                } 
                case _ =>            
            }            
        }

        var mutListResult = mutable.ListBuffer.empty[FinderMatch]

        for ((k,v) <- mutAllMatches.groupBy(_._1)) {
            similarityConfig.matchSelectionMode match {
                case 1 => mutListResult += v.sortBy(_._2.matchResult.nofHits).reverse.head._2
                case _ => mutListResult += v.sortBy(_._2.matchResult.similarity).reverse.head._2
            }            
        }            

        mutListResult.toList
    }

    // ---
    private def createNmQuery(normName : NormalizedName, dates : List[String], countries : List[String], nameType : String, label : String) : BooleanQuery = {
        BooleanQuery.setMaxClauseCount(Int.MaxValue)           
        
        val csList = "cs:xx" :: countries.map(
        c => TextNormalizer.normalize(c).toIsoCountry match {
          case(true, x) => "cs:" + x
          case(false, x) => "_"
        }).filter(_ != "_")
        
        val ysList = dates.map(y => {
        val nd = DateNormalizer.normalizeDate(y)
        nd.dateType match {
          case 99 => 9999
          case _ => nd.year
        }}).filter(_ != 9999).sorted

        var mutWithDate : Boolean = true
        var mutDateBuilder = new mutable.StringBuilder
        ysList.length match {
            case 0 => mutWithDate = false
            case _ => {
                mutDateBuilder ++= "((ys:["
                mutDateBuilder ++= (ysList.head - similarityConfig.maxDateYearDifferenceForHit).toString
                mutDateBuilder ++= " TO "
                mutDateBuilder ++= (ysList.last + similarityConfig.maxDateYearDifferenceForHit).toString
                mutDateBuilder ++= "]) OR ys:9999)"
            }
        }
        
        val nameQuery = new BooleanQuery.Builder()
        var mutMinShoudMatchList = new mutable.ListBuffer[Double]
        for (variant <- normName.normNames) {
            var mutMinShouldMatchCounter : Double = 0.0
            for (element <- variant) {
                element._3 match {
                    case 1 => {                            
                        nameNormalizer.persNameElementReducedWeight(element._1, 1.0) match {
                            case (_, false) => {                                                                
                                nameElementSimilarityDb.getMatchList(element._1, similarityConfig.nameElementSimilarityForHit).foreach(s =>
                                    nameQuery.add(new TermQuery(new Term("doc", s)), BooleanClause.Occur.SHOULD)
                                )                                
                                val triGList = element._1.sliding(3).toList
                                triGList.foreach(s =>
                                    nameQuery.add(new TermQuery(new Term("doc", s)), BooleanClause.Occur.SHOULD)
                                )
                                mutMinShouldMatchCounter = mutMinShouldMatchCounter + (triGList.length/2)
                            }
                            case (_, true) =>
                        }                 
                    }                    
                    case _ => nameQuery.add(new TermQuery(new Term("doc", element._1)), BooleanClause.Occur.SHOULD)
                }
            }
            mutMinShoudMatchList += mutMinShouldMatchCounter
        }
        nameQuery.setMinimumNumberShouldMatch(floor(mutMinShoudMatchList.sorted.head - 0.5).toInt match {
            case x if x < 1 => 1
            case x => x
        })

        val findQuery = new BooleanQuery.Builder()
        findQuery.add(new TermQuery(new Term("t", nameType)), BooleanClause.Occur.MUST)
        findQuery.add(nameQuery.build(), BooleanClause.Occur.MUST)

        val queryParser = new QueryParser("doc", new PerFieldAnalyzerWrapper(new WhitespaceAnalyzer()))

        if (csList.length > 1)
            findQuery.add(queryParser.parse(csList.mkString(" ")), BooleanClause.Occur.MUST)

        if (mutWithDate)
            findQuery.add(queryParser.parse(mutDateBuilder.toString), BooleanClause.Occur.MUST)

        if (!BasicFunctions.isNullOrEmpty(label))
            findQuery.add(new TermQuery(new Term("l", label)), BooleanClause.Occur.MUST)

        findQuery.build()
    }
}