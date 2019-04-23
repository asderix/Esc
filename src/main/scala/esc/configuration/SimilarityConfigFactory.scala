/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.configuration

/**
  * Sugar object for creating SimilarityConfig using Java.
  * For using Scala "new SimilarityConfig()"" is exactly the same.
  */
object SimilarityConfigFactory {    

    /**
      * Creates a new SimilarityConfig object with default values.
      *
      * @return Returns a SimilarityConfig object.
      */
    def createDefaultConfig() : SimilarityConfig = {
        new SimilarityConfig()
    }
}