/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.ai

import java.net.URI
import java.net.http.{HttpClient, HttpRequest, HttpResponse}
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers
import upickle.default.{ReadWriter, macroRW}
import esc.configuration._

case class Part(text: String)
object Part:
  given ReadWriter[Part] = macroRW

case class Content(parts: Seq[Part], role: Option[String])
object Content:
  given ReadWriter[Content] = macroRW

case class Candidate(content: Content, finishReason: Option[String], index: Int)
object Candidate:
  given ReadWriter[Candidate] = macroRW

case class GeminiResponse(candidates: Seq[Candidate])
object GeminiResponse:
  given ReadWriter[GeminiResponse] = macroRW

/**
* A simple LMRunner implementation which call the Gemini API for
* LLM stuff. You need at least a valid Gemini ApiKey.
*
* Usage:
* <code>
* val g = new SimpleGeminiRunner("path", "apiKey")
* LMRunner.setLMRunner(g)
* </cod>
*
*/
class SimpleGeminiRunner(apiUrl: String, apiKey: String) extends LMRunner:
    private var _aiConfig: AiConfig = new AiConfig()
    val client = HttpClient.newHttpClient()
    
    override def aiConfig: AiConfig = _aiConfig

    /**
    * Set a new AiConfig to the object. The config itself has
    * no impact of this implementation. It's just used to give
    * the actual config back.
    *
    */
    override def changeAiConfig(newAiConfig: AiConfig): Unit =
        _aiConfig = newAiConfig        

    /**
    * Not implemented beacause it's not needed for the Gemini API.
    *
    */
    override def loadModel(path: String): Unit = synchronized:
        ()

    /**
    * Execute the given text prompt using the Gemini API.
    * 
    * @param maxTokens
    *  Default is None. But this value is not used for the Gemini API yet.
    */
    override def prompt(prompt: String, maxTokens: Option[Int] = None): String = synchronized:
        val jsonPayload =
            s"""
            |{
            |  "contents": [
            |    {
            |      "parts": [
            |        { "text": "$prompt" }
            |      ]
            |    }
            |  ]
            |}
            |""".stripMargin
        val request = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl))
            .header("Content-Type", "application/json")
            .header("x-goog-api-key", apiKey)
            .POST(BodyPublishers.ofString(jsonPayload))
            .build()

        val response = client.send(request, BodyHandlers.ofString())

        val parsed = upickle.default.read[GeminiResponse](response.body())
        val text = parsed.candidates
            .flatMap(_.content.parts)
            .map(_.text)
            .mkString("\n")

        text
