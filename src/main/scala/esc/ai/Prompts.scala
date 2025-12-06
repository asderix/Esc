/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.ai

object Prompts {
    val describeMatchTemplate: String =
    """Describe the similarity of this two names:
      |{NAME_PAIR}    
      Focus on name parts which are spelled different but are similar for some reasons. Name order doesn't matter. Check if the reason could be:
      |Transliteration
      |Shortform
      |Different language
      |Typo
      |Provide your answer in the following language:
      |{USER_LANG}
      """.stripMargin

    val assessMatchTemplate: String =
    """You are a name matching expert. Assess whether {NAME_PAIR} could be considered as semantically similar.
        Name order doesn't matter.
        Respond concisely with one of the following phrases based on your decision:
        1. 'The names are likely similar: YES'
        2. 'The names are clearly NOT equal: NO'
        Decision:
    """.stripMargin

    val translateTextTemplate: String =
    """Just translate the following text into {USER_LANG}:
      |{TEXT}
      """.stripMargin

    val transliterateTemplate: String =
    """
    You are a transliteration engine. You output only Latin characters.
    Task: Convert the input name into Latin alphabet (standard English transliteration).
    Rules:
    - Output ONLY the transliterated name
    - No explanation, no labels, no extra words

    Input: {TEXT}
    Output: 
      """.stripMargin
}