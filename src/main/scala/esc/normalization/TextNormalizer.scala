/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.normalization

import java.text.Normalizer
import java.util.Locale

object TextNormalizer {

  /** Default normalize method.
    *
    * @param text
    *   The string, e.g. a full name, to normalize.
    * @return
    *   Return the normalized String.
    */
  def normalize(text: String): String = {
    val regexPattern = "[^\\p{L}0-9 \\-]".r

    // Normalized in NFKD form (decomposition), compatibily mode, in lower case letters
    var mutNormString = Normalizer
      .normalize(text, Normalizer.Form.NFKD)
      .toLowerCase(Locale.ENGLISH)

    // Relevant special characters from the extended Latin alphabet which have no decomposition in the NFKD form
    mutNormString = mutNormString.replace("ø", "o")
    mutNormString = mutNormString.replace("œ", "oe")
    mutNormString = mutNormString.replace("ß", "ss")
    mutNormString = mutNormString.replace("æ", "ae")
    mutNormString = mutNormString.replace("¢", "c")    
    mutNormString = mutNormString.replace("ə", "a")
    mutNormString = mutNormString.replace("ı", "i")
    mutNormString = mutNormString.replace("ł", "l") // Sometimes w, but more often l, as in: Łukasz
    mutNormString = mutNormString.replace("θ", "th")
    mutNormString = mutNormString.replace("ʼ", "")

    // Arabic chars from transliteration
    mutNormString = mutNormString.replace("ʿbd", "abd")
    mutNormString = mutNormString.replace("ʿly", "ali")
    
    // Special characters that are relevant for the name comparison
    mutNormString = mutNormString.replace("&", "and")
    mutNormString = mutNormString.replace("+", "plus")
    mutNormString = mutNormString.replace("@", "at")
    mutNormString = mutNormString.replace("\n", " ")

    // No unnecessary repetitions and useless special character constellations
    mutNormString = mutNormString.replaceAll("- -", "-")
    mutNormString = mutNormString.replaceAll(" +", " ")
    mutNormString = mutNormString.replaceAll("-+", "-")
    mutNormString = mutNormString.replaceAll(" *- *", "-")
    mutNormString = mutNormString.replaceAll("^-", "")
    mutNormString = mutNormString.replaceAll("-$", "")
    
    // several name elements that only make sense together and represent one element
    mutNormString = mutNormString.replaceAll("^von *der ", "vonder ")
    mutNormString = mutNormString.replaceAll(" von *der ", " vonder ")
    mutNormString = mutNormString.replaceAll(" von *der$", " vonder")

    mutNormString = mutNormString.replaceAll("^von *de ", "vonde ")
    mutNormString = mutNormString.replaceAll(" von *de ", " vonde ")
    mutNormString = mutNormString.replaceAll(" von *de$", " vonde")

    mutNormString = mutNormString.replaceAll("^van *der ", "vander ")
    mutNormString = mutNormString.replaceAll(" van *der ", " vander ")
    mutNormString = mutNormString.replaceAll(" van *der$", " vander")

    mutNormString = mutNormString.replaceAll("^van *de ", "vande ")
    mutNormString = mutNormString.replaceAll(" van *de ", " vande ")
    mutNormString = mutNormString.replaceAll(" van *de$", " vande")

    mutNormString = mutNormString.replaceAll("^an *der ", "ander ")
    mutNormString = mutNormString.replaceAll(" an *der ", " ander ")
    mutNormString = mutNormString.replaceAll(" an *der$", " ander")

    mutNormString = mutNormString.replaceAll("^de *la ", "dela ")  
    mutNormString = mutNormString.replaceAll(" de *la ", " dela ")
    mutNormString = mutNormString.replaceAll(" de *la$", " dela")

    mutNormString = mutNormString.replaceAll("^de *los ", "delos ")  
    mutNormString = mutNormString.replaceAll(" de *los ", " delos ")
    mutNormString = mutNormString.replaceAll(" de *los$", " delos")

    mutNormString = mutNormString.replaceAll("^ad-din ", "addin ")  
    mutNormString = mutNormString.replaceAll(" ad-din ", " addin ")
    mutNormString = mutNormString.replaceAll(" ad-din$", " addin")

    mutNormString = mutNormString.replaceAll("^al-din ", "aldin ")  
    mutNormString = mutNormString.replaceAll(" al-din ", " aldin ")
    mutNormString = mutNormString.replaceAll(" al-din$", " aldin")

    // Only standard alphabets letters and numbers, hyphens and spaces
    mutNormString = regexPattern replaceAllIn (mutNormString, "")
    mutNormString.trim
  }

  /** Deletes all whitespaces and hypthens (" ", "-") in a text.
    *
    * @param text
    *   The string, e.g. a full name, to normalize.
    * @return
    *   Return a normalized String.
    */
  def normalizeForSimpleSimilarity(text: String): String = {
    val regexPattern = "[ \\-]+".r
    var mutNormString = normalize(text)
    mutNormString = regexPattern replaceAllIn (mutNormString, "")
    mutNormString.trim
  }

  /** Special normalize method for organisation names. This method take care of
    * some legal forms with more than one word/name element. Example: GmbH & Co.
    * KG. This method first call normalize itself.
    *
    * @param text
    *   The string, e.g. a full name, to normalize.
    * @return
    *   Return a normalized String.
    */
  def normalizeWithLegalForm(text: String): String = {
    var mutNormString = normalize(text)

    // Compact common legal forms
    mutNormString = mutNormString.replaceAll("\\bgmbh *and *co *kg\\b", "gmbh_and_co_kg")
    mutNormString =
      mutNormString.replaceAll("\\bgmbh *and *co *ohg\\b", "gmbh_and_co_ohg")
    mutNormString =
      mutNormString.replaceAll("\\bgmbh *and *co *kgaa\\b", "gmbh_and_co_kgaa")

    mutNormString = mutNormString.replaceAll("\\bohg *mbh\\b", "ohg_mbh")

    mutNormString = mutNormString.replaceAll("\\bag *and *co *ohg\\b", "ag_and_co_ohg")
    mutNormString = mutNormString.replaceAll("\\bag *and *co *kgaa\\b", "ag_and_co_kgaa")
    mutNormString = mutNormString.replaceAll("\\bag *and *co *kg\\b", "ag_and_co_kg")

    mutNormString =
      mutNormString.replaceAll("\\bstiftung *and *co *kgaa\\b", "stiftung_and_co_gkaa")

    mutNormString = mutNormString.replace("\\bco-operative\\b", "cooperative")
    mutNormString =
      mutNormString.replaceAll("\\bsociete *cooperative\\b", "societe_cooperative")
    mutNormString =
      mutNormString.replaceAll("\\bsocieta *cooperativa\\b", "societa_cooperativa")

    mutNormString = mutNormString.replaceAll("\\bcompany *limited\\b", "lc")
    mutNormString = mutNormString.replaceAll("\\bcompany *ltd\\b", "lc")
    mutNormString = mutNormString.replaceAll("\\blimited *company\\b", "lc")
    mutNormString = mutNormString.replaceAll("\\bpublic *limited *company\\b", "plc")
    mutNormString = mutNormString.replaceAll("\\bcompany *corp\\b", "corp")
    mutNormString = mutNormString.replaceAll("\\bunlimited *company\\b", "uc")
    mutNormString = mutNormString.replaceAll("\\bincorporated *company\\b", "inc")

    mutNormString = mutNormString.replaceAll("\\b( *a o)\\b", "ao")

    mutNormString
  }

  /** This method normalize a single name element - not a full name. Actually
    * there are some normalizations for Russian and Chinese names.
    *
    * @param nameElement
    *   String representing the name element.
    * @return
    *   Return a normalized String.
    */
  def normalizeNameElement(nameElement: String): String = {
    var mutNormNameElement = nameElement

    // Some standardizations in relation to the Russian and others
    mutNormNameElement = mutNormNameElement.replaceAll("witsch$", "vich")
    mutNormNameElement = mutNormNameElement.replaceAll("wjtsch$", "vich")
    mutNormNameElement = mutNormNameElement.replaceAll("witsj$", "vich")
    mutNormNameElement = mutNormNameElement.replaceAll("wicz$", "vich")
    mutNormNameElement = mutNormNameElement.replaceAll("witz$", "vitz") //
    mutNormNameElement = mutNormNameElement.replaceAll("vych$", "vich")
    mutNormNameElement = mutNormNameElement.replaceAll("vitch$", "vich")
    mutNormNameElement = mutNormNameElement.replaceAll("off$", "ov")
    mutNormNameElement = mutNormNameElement.replaceAll("eff$", "ev")
    mutNormNameElement = mutNormNameElement.replaceAll("ow$", "ov")
    mutNormNameElement = mutNormNameElement.replaceAll("owna$", "ovna")
    mutNormNameElement = mutNormNameElement.replaceAll("ew$", "ev")
    mutNormNameElement = mutNormNameElement.replaceAll("aw$", "av")
    mutNormNameElement = mutNormNameElement.replaceAll("jew$", "ev")
    mutNormNameElement = mutNormNameElement.replaceAll("schew$", "chev")
    mutNormNameElement = mutNormNameElement.replaceAll("wa$", "va")

    mutNormNameElement = mutNormNameElement.replaceAll("czuk$", "chuk")
    mutNormNameElement = mutNormNameElement.replaceAll("vets$", "wez")

    mutNormNameElement = mutNormNameElement.replaceAll("yuk$", "uk")
    mutNormNameElement = mutNormNameElement.replaceAll("juk$", "uk")

    mutNormNameElement = mutNormNameElement.replaceAll("yova$", "ova")

    // Some standardizations in relation to the Chinese
    mutNormNameElement = mutNormNameElement.replaceAll("-tao$", "tao")

    mutNormNameElement
  }
}
