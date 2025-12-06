/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.ai

import esc.commons._
import esc.configuration._
import esc.utils.Persistence._

object AiAgent {

    private def fillTemplate(template: String, values: Map[String, String]): String =
        values.foldLeft(template) { case (acc, (key, value)) =>
            acc.replace(s"{$key}", value)
    }

    /**
    * Experimental!
    * Try to explain while a match of two names make sense.
    * The LLM has sometimes problem if the name structure is different.
    *
    * @param userLang
    *  Default is English. Set an other language if you want to show the
    *  message directly to a end user with a different language.
    *
    */
    def explainMatch(matchExpl: MatchExplanation, userLang: String = "English"): String = {
        val prompt = if (matchExpl.similarity < LMRunner.aiConfig.agentSimilarityThresholdForHitToExplain) {
            fillTemplate(Prompts.translateTextTemplate,
                Map("TEXT" -> s"According to the name matching algorithm, this is not a match. The similarity is less than: ${LMRunner.aiConfig.agentSimilarityThresholdForHitToExplain}",
                    "USER_LANG" -> userLang))
        } else {
                fillTemplate(Prompts.describeMatchTemplate,
                Map("NAME_PAIR" -> s"$matchExpl.sourceNameA,$matchExpl.sourceNameB",
                    "USER_LANG" -> userLang))
        }
        
        val result = LMRunner.prompt(prompt)
        result
    }

    /**
    * This is very experimental!
    * The LLM try to verify is a match correct or not.
    * The return value is a boolean. Don't trust this assessment
    * without verification.
    *
    */
    def assessMatch(matchExpl: MatchExplanation): Boolean = {
        val prompt = fillTemplate(Prompts.assessMatchTemplate,
                Map("NAME_PAIR" -> s"name A: [ ${matchExpl.sourceNameA} ] and name B: [ ${matchExpl.sourceNameB} ]"))
        val result = LMRunner.prompt(prompt, Some(128))
        result.toLowerCase.contains("yes")
    }

    /**
    * Transliterate or transcript a given text into the latein alphabet
    * in a English language context. This takes some time but give you
    * better results for some scripts like arabic or chinese then
    * transcription with the ICU library.
    *
    */
    def transToLatin(text: String): String = {
        val prompt = fillTemplate(Prompts.transliterateTemplate,
                Map("TEXT" -> text))
        
        val result = LMRunner.prompt(prompt, Some(50))
        result.trim
    }

    /**
    * Just call loadModel of LMRunner. It's sugar for you
    * not to use LMRunner directly.
    *
    */
    def loadModel(path: String) = synchronized {
        LMRunner.loadModel(path)
    }

    /**
    * Just call changeAiConfig of LMRunner. It's sugar for you
    * not to use LMRunner directly.
    *
    */
    def changeAiConfig(newAiConfig: AiConfig) = {
        LMRunner.changeAiConfig(newAiConfig)
    }        
}