/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.normalization

import com.ibm.icu.text.Transliterator => ICUTransliterator

object Transliterator {
    private val RULE_SET = "Any-Latin; NFD; Latin-ASCII"

    private lazy val defaultTransliterator: ICUTransliterator = ICUTransliterator.getInstance(RULE_SET)

    private lazy val scriptToTransliterator: Map[String, ICUTransliterator] = Map(
        "arabic"   -> ICUTransliterator.getInstance("Arabic-Latin; NFD; Latin-ASCII"),
        "cyrillic" -> ICUTransliterator.getInstance("Cyrillic-Latin; NFD; Latin-ASCII"),
        "japanese" -> ICUTransliterator.getInstance("Kana-Latin; NFD; Latin-ASCII"),
        "chinese"  -> ICUTransliterator.getInstance("Han-Latin; NFD; Latin-ASCII"),
        "greek"  -> ICUTransliterator.getInstance("Greek-Latin; NFD; Latin-ASCII"),
        "korean"  -> ICUTransliterator.getInstance("Hangul-Latin; NFD; Latin-ASCII"),
        "default"  -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII")
    )

    private def detectScript(s: String): String = {
        val arabic   = "\\p{IsArabic}".r
        val cyrillic = "\\p{IsCyrillic}".r
        val hiragana = "\\p{IsHiragana}".r
        val katakana = "\\p{IsKatakana}".r
        val han      = "\\p{IsHan}".r
        val greek    = "\\p{IsGreek}".r
        val hangul   = "\\p{IsHangul}".r

        if (arabic.findFirstIn(s).isDefined) "arabic"
        else if (cyrillic.findFirstIn(s).isDefined) "cyrillic"
        else if (hiragana.findFirstIn(s).isDefined || katakana.findFirstIn(s).isDefined) "japanese"
        else if (han.findFirstIn(s).isDefined) "chinese"
        else if (greek.findFirstIn(s).isDefined) "greek"
        else if (hangul.findFirstIn(s).isDefined) "korean"
        else "default"
    }

    /** Transliteration method for any script to latin.
      *
      * @param text
      *   The string, e.g. a full name, to transliterate into latin.
      * @return
      *   Return the latin encoded String.
      */
    def transToLatin(text: String): String = {
        val translator = scriptToTransliterator.getOrElse(detectScript(text), defaultTransliterator)
        translator.transliterate(text)
    }
}