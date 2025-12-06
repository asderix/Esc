/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.normalization

import com.ibm.icu.text.Transliterator => ICUTransliterator
import esc.ai._

object Transliterator {
    private val RULE_SET = "Any-Latin; NFD; Latin-ASCII"

    private lazy val defaultTransliterator: ICUTransliterator = ICUTransliterator.getInstance(RULE_SET)

    private lazy val scriptToTransliterator: Map[String, ICUTransliterator] = Map(
        "arabic"     -> ICUTransliterator.getInstance("Arabic-Latin; NFD; Latin-ASCII"),
        "cyrillic"   -> ICUTransliterator.getInstance("Cyrillic-Latin; NFD; Latin-ASCII"),
        "japanese"   -> ICUTransliterator.getInstance("Kana-Latin; NFD; Latin-ASCII"),
        "chinese"    -> ICUTransliterator.getInstance("Han-Latin; NFD; Latin-ASCII"),
        "greek"      -> ICUTransliterator.getInstance("Greek-Latin; NFD; Latin-ASCII"),
        "korean"     -> ICUTransliterator.getInstance("Hangul-Latin; NFD; Latin-ASCII"),
        "thai"       -> ICUTransliterator.getInstance("Thai-Latin; NFD; Latin-ASCII"),
        "armenian"   -> ICUTransliterator.getInstance("Armenian-Latin; NFD; Latin-ASCII"),
        "hebrew"     -> ICUTransliterator.getInstance("Hebrew-Latin; NFD; Latin-ASCII"),
        "devanagari" -> ICUTransliterator.getInstance("Devanagari-Latin; NFD; Latin-ASCII"),
        "georgian"   -> ICUTransliterator.getInstance("Georgian-Latin; NFD; Latin-ASCII"),
        "latin"      -> ICUTransliterator.getInstance("Latin-ASCII"),
        
        "ethiopic"   -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "lao"        -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "tibetan"    -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "myanmar"    -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "khmer"      -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "bengali"    -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "gujarati"   -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "gurmukhi"   -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "kannada"    -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "malayalam"  -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "tamil"      -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "telugu"     -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),
        "sinhala"    -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII"),

        "default"    -> ICUTransliterator.getInstance("Any-Latin; NFD; Latin-ASCII")
    )

    // --
    private def detectScript(s: String): String = {
        val arabic   = "\\p{IsArabic}".r
        val cyrillic = "\\p{IsCyrillic}".r
        val hiragana = "\\p{IsHiragana}".r
        val katakana = "\\p{IsKatakana}".r
        val han      = "\\p{IsHan}".r
        val greek    = "\\p{IsGreek}".r
        val hangul   = "\\p{IsHangul}".r
        val thai       = "\\p{IsThai}".r
        val armenian   = "\\p{IsArmenian}".r
        val hebrew     = "\\p{IsHebrew}".r
        val devanagari = "\\p{IsDevanagari}".r  
        val latin      = "\\p{IsLatin}".r
        val georgian   = "\\p{IsGeorgian}".r
        val ethiopic   = "\\p{IsEthiopic}".r 
        val lao        = "\\p{IsLao}".r
        val tibetan    = "\\p{IsTibetan}".r
        val myanmar    = "\\p{IsMyanmar}".r
        val khmer      = "\\p{IsKhmer}".r
        val bengali    = "\\p{IsBengali}".r
        val gujarati   = "\\p{IsGujarati}".r
        val gurmukhi   = "\\p{IsGurmukhi}".r
        val kannada    = "\\p{IsKannada}".r
        val malayalam  = "\\p{IsMalayalam}".r
        val tamil      = "\\p{IsTamil}".r
        val telugu     = "\\p{IsTelugu}".r
        val sinhala    = "\\p{IsSinhala}".r

        if (arabic.findFirstIn(s).isDefined) "arabic"
        else if (cyrillic.findFirstIn(s).isDefined) "cyrillic"
        else if (hiragana.findFirstIn(s).isDefined || katakana.findFirstIn(s).isDefined) "japanese"
        else if (han.findFirstIn(s).isDefined) "chinese"
        else if (greek.findFirstIn(s).isDefined) "greek"
        else if (hangul.findFirstIn(s).isDefined) "korean"
        else if (thai.findFirstIn(s).isDefined) "thai"
        else if (armenian.findFirstIn(s).isDefined) "armenian"
        else if (hebrew.findFirstIn(s).isDefined) "hebrew"
        else if (devanagari.findFirstIn(s).isDefined) "devanagari"
        else if (latin.findFirstIn(s).isDefined) "latin"
        else if (georgian.findFirstIn(s).isDefined) "georgian"
        else if (ethiopic.findFirstIn(s).isDefined) "ethiopic"
        else if (lao.findFirstIn(s).isDefined) "lao"
        else if (tibetan.findFirstIn(s).isDefined) "tibetan"
        else if (myanmar.findFirstIn(s).isDefined) "myanmar"
        else if (khmer.findFirstIn(s).isDefined) "khmer"
        else if (bengali.findFirstIn(s).isDefined) "bengali"
        else if (gujarati.findFirstIn(s).isDefined) "gujarati"
        else if (gurmukhi.findFirstIn(s).isDefined) "gurmukhi"
        else if (kannada.findFirstIn(s).isDefined) "kannada"
        else if (malayalam.findFirstIn(s).isDefined) "malayalam"
        else if (tamil.findFirstIn(s).isDefined) "tamil"
        else if (telugu.findFirstIn(s).isDefined) "telugu"
        else if (sinhala.findFirstIn(s).isDefined) "sinhala"
        else "default"
    }

    /** Transliteration method for any script to latin using the ICU lib.
      * Remark: For some scripts like arabic oder chinese the method
      * transToLatinWithAi gives better results in most cases.
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

    /** Transliteration method for any script to latin using the AiAgent.
      * Remark: Make sure you have loaded the model to the AiAgent before.
      * For batch processing with different scripts it's recommended to
      * use the transToLatinBestGuess method which have a better performance.
      *
      * @param text
      *   The string, e.g. a full name, to transliterate into latin.
      * @return
      *   Return the latin encoded String.
      */
    def transToLatinWithAi(text: String): String = {
      val detectedScript = detectScript(text)
      detectedScript match {
        case "default" => {
          val translator = scriptToTransliterator.getOrElse(detectedScript, defaultTransliterator)
          translator.transliterate(text)
        }
        case "latin" => text
        case _ => {
          println("Aitrans " + text)
          AiAgent.transToLatin(text)
        }
      }      
    }

    /** Transliteration method for any script to latin using the ICU lib or the AiAgent.
      * Choose the fastest and best way based on the script.
      * Remark: Make sure you have loaded the model to the AiAgent before.
      *
      * @param text
      *   The string, e.g. a full name, to transliterate into latin.
      * @return
      *   Return the latin encoded String.
      */
    def transToLatinBestGuess(text: String): String = {
      val detectedScript = detectScript(text)
      detectedScript match {        
        case "latin" => text
        case "cyrillic" => {
          val translator = scriptToTransliterator.getOrElse(detectedScript, defaultTransliterator)
          translator.transliterate(text)
        }
        case "greek" => {
          val translator = scriptToTransliterator.getOrElse(detectedScript, defaultTransliterator)
          translator.transliterate(text)
        }
        case "hebrew" => {
          val translator = scriptToTransliterator.getOrElse(detectedScript, defaultTransliterator)
          translator.transliterate(text)
        }
        case "armenian" => {
          val translator = scriptToTransliterator.getOrElse(detectedScript, defaultTransliterator)
          translator.transliterate(text)
        }
        case "georgian" => {
          val translator = scriptToTransliterator.getOrElse(detectedScript, defaultTransliterator)
          translator.transliterate(text)
        }
        case "devanagari" => {
          val translator = scriptToTransliterator.getOrElse(detectedScript, defaultTransliterator)
          translator.transliterate(text)
        }
        case "korean" => {
          val translator = scriptToTransliterator.getOrElse(detectedScript, defaultTransliterator)
          translator.transliterate(text)
        }
        case "tamil" => {
          val translator = scriptToTransliterator.getOrElse(detectedScript, defaultTransliterator)
          translator.transliterate(text)
        }
        case _ => {
          AiAgent.transToLatin(text)
        }
      }      
    }
}