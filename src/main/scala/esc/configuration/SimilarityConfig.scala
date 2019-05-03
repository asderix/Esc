/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.configuration

/**
  * Class for the configuration of the normalizing and similarity stuff.
  * Important:
  * Make sure you use the same configuration for indexing and searching/comparing. Otherwise there may be unwanted side effects.
  *
  * @param  normOrgLegalformWeight  Weight (reduction) of a legal form match (recommended: < 1, default is 0.25).
  * @param  normOrgCountryWeight  Weight (reduction) of a country match (recommended: < 1, default is 0.5).
  * @param  nameElementSimilarityForHit  Minimum similarity to mark as hit. Default is 0.9.
  * @param  matchSelectionMode  Method of how a match is to be determined: 0 = Based on simialrity. 1 = Based on nofHits (number of hits). Default is 0.
  * @param  checkDateForSearchHit  Defines whether the date should be taken into account. Default is true.
  * @param  DateComparisonMethod  Method which date parts are to be compared. Currently only 0 = year is supported. Default is 0.
  * @param  maxDateYearDifferenceForHit  Defines the uncertainty/tolerance in the annual comparison in number of years (+/-). Default is 2.
  * @param  checkCountryForSearchHit  Defines whether the country should be considered. Default is true.
  * @param  similarityValueForSearchHit  Value of the similarity from which the comparison is classified as a hit. Default is 0.9.
  * @param  numberOfHitsForSearchHit  Value of the nofHits (number of hits) from which the comparison is classified as a hit. Default is 2.
  * @param  maxNumberOfCandidatesFromSearch  Defines the maximum number of candidates to be considered by the IR search, from which hits are then determined. Default is 10000
  * @param  searchEntityGroupMode  Defines the field by which the hits are to be grouped. Depends which value is unique: 0 = externalId, 1 = Id. Default is 0.
  * @param  allowOneLetterAbbreviation Defines whether abbreviations with a letter are taken into account. With true, for example, Benjamin is a hit with B. Default is false.
  * @param  oneLetterAbbreviationWeight If abbreviations are taken into account, this value defines the weight (reduction) of such a hit. Default is 0.5.
  */
case class SimilarityConfig(normOrgLegalformWeight : Double = 0.25,
                            normOrgCountryWeight : Double = 0.5,
                            nameElementSimilarityForHit : Double = 0.9,
                            matchSelectionMode : Int = 0,
                            checkDateForSearchHit : Boolean = true,
                            dateComparisonMethod : Int = 0,
                            maxDateYearDifferenceForHit : Int = 2,
                            checkCountryForSearchHit : Boolean = true,                                                        
                            similarityValueForSearchHit : Double = 0.9,
                            numberOfHitsForSearchHit : Int = 2,
                            maxNumberOfCandidatesFromSearch : Int = 10000,
                            searchEntityGroupMode : Int = 0,
                            allowOneLetterAbbreviation : Boolean = false,
                            oneLetterAbbreviationWeight : Double = 0.5
                        )