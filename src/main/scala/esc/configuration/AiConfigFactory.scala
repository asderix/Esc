/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */
package esc.configuration

/** Sugar object for creating AiConfig when using Java. For using Scala
  * "new SimilarityConfig()" is exactly the same.
  */
object AiConfigFactory {

  /** Creates a new AiConfig object with default values.
    *
    * @return
    *   Returns a AiConfig object.
    */
  def createDefaultConfig(): AiConfig = {
    new AiConfig()
  }
}
