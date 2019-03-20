/**
  * author:   Ronny Fuchs, info@asderix.com
  * licence:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.similarity

import esc.configuration._
import esc.commons._

/**
  * This class provides methods to calculate the similarity of two 
  * normalized names.
  * Attention:
  * Make sure that you only compare the same name type: person or organisation.
  */
class TextElementSimilarity(val similarityConfig : SimilarityConfig = new SimilarityConfig()) {
  def getNameSimilarity(normNameA : NormalizedName, normNameB : NormalizedName) : Double = {

    // Not implemented yet!!!
    return 0.9

  }
}