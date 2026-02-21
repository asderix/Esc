/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.testing
import esc.ai._

object TestEnv:

    /**
    * Call:
    * export API_KEY=YOUR-KEY
    * export API_URL=YOUR-URL
    * sbt test
    *
    */

    var isInitialized: Boolean = false

    lazy val apiKey: String = sys.env.getOrElse("API_KEY",
        throw new RuntimeException("Missing API_KEY"))

    lazy val apiUrl: String = sys.env.getOrElse("API_URL",
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent")

    /* Define with which LLMRunner do you want to test: */
    def init(): Unit =
        if isInitialized then
            return

        /* Simple Gemini Runner */
        val geminiRunner = new SimpleGeminiRunner(apiUrl, apiKey)
        LMRunnerService.setLMRunner(geminiRunner)
        
        /* or */
        /* Local Runner */
        //LMRunnerService.loadModel(Paths.get("../models/Mistral-Small-3.2-24B-Instruct-2506-Q4_0.gguf").toAbsolutePath.normalize().toString) 

        isInitialized = true

