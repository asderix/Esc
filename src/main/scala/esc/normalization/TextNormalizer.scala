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
    //val regexPattern = "[^abcdefghijklmnopqrstuvwxyz0123456789 -]".r
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

    // Arabic chars from transliteration
    mutNormString = mutNormString.replace("ʿbd", "abd")
    mutNormString = mutNormString.replace("ʿly", "ali")
    
    // Special characters that are relevant for the name comparison
    mutNormString = mutNormString.replace("&", "plus")
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
    mutNormString = mutNormString.replaceAll(" von *der$", "vonder")

    mutNormString = mutNormString.replaceAll("^von *de ", "vonde ")
    mutNormString = mutNormString.replaceAll(" von *de ", " vonde ")
    mutNormString = mutNormString.replaceAll(" von *de$", " vonde")
  

    mutNormString = mutNormString.replaceAll("^van *der ", "vander ")
    mutNormString = mutNormString.replaceAll(" van *der ", " vander ")
    mutNormString = mutNormString.replaceAll(" van *der$", "vander")

    mutNormString = mutNormString.replaceAll("^van *de ", "vande ")
    mutNormString = mutNormString.replaceAll(" van *de ", " vande ")
    mutNormString = mutNormString.replaceAll(" van *de$", " vande")

    mutNormString = mutNormString.replaceAll("^an *der ", "ander ")
    mutNormString = mutNormString.replaceAll(" an *der ", " ander ")
    mutNormString = mutNormString.replaceAll(" an *der$", " ander")

    mutNormString = mutNormString.replaceAll("^de *la ", "dela ")  
    mutNormString = mutNormString.replaceAll(" de *la ", " dela ")
    mutNormString = mutNormString.replaceAll(" de *la$", "dela")    

    // Only standard Latin letters and numbers, hyphens and spaces
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
    mutNormString = mutNormString.replace("gmbh *and *co *kg", "gmbh_and_co_kg")
    mutNormString =
      mutNormString.replace("gmbh *and *co *ohg", "gmbh_and_co_ohg")
    mutNormString =
      mutNormString.replace("gmbh *and *co *kgaa", "gmbh_and_co_kgaa")

    mutNormString = mutNormString.replace("ohg *mbh", "ohg_mbh")

    mutNormString = mutNormString.replace("ag *and *co *ohg", "ag_and_co_ohg")
    mutNormString = mutNormString.replace("ag *and *co *kgaa", "ag_and_co_kgaa")
    mutNormString = mutNormString.replace("ag *and *co *kg", "ag_and_co_kg")

    mutNormString =
      mutNormString.replace("stiftung *and *co *kgaa", "stiftung_and_co_gkaa")

    mutNormString = mutNormString.replace("co-operative", "cooperative")
    mutNormString =
      mutNormString.replace("societe *cooperative", "societe_cooperative")
    mutNormString =
      mutNormString.replace("societa *cooperativa", "societa_cooperativa")

    mutNormString = mutNormString.replace("company *limited", "lc")
    mutNormString = mutNormString.replace("company *ltd", "lc")
    mutNormString = mutNormString.replace("limited *company", "lc")
    mutNormString = mutNormString.replace("public *limited *company", "plc")
    mutNormString = mutNormString.replace("company *corp", "corp")
    mutNormString = mutNormString.replace("unlimited *company", "uc")
    mutNormString = mutNormString.replace("incorporated company", "inc")

    mutNormString = mutNormString.replace("( *a o)$", "ao")

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
    mutNormNameElement = mutNormNameElement.replaceAll("vych$", "vich")
    mutNormNameElement = mutNormNameElement.replaceAll("vitch$", "vich")
    mutNormNameElement = mutNormNameElement.replaceAll("off$", "ov")
    mutNormNameElement = mutNormNameElement.replaceAll("eff$", "ev")
    mutNormNameElement = mutNormNameElement.replaceAll("ow$", "ov")
    mutNormNameElement = mutNormNameElement.replaceAll("owna$", "ovna")
    mutNormNameElement = mutNormNameElement.replaceAll("ew$", "ev")
    mutNormNameElement = mutNormNameElement.replaceAll("jew$", "ev")
    mutNormNameElement = mutNormNameElement.replaceAll("schew$", "chev")

    mutNormNameElement = mutNormNameElement.replaceAll("czuk$", "chuk")
    mutNormNameElement = mutNormNameElement.replaceAll("vets$", "wez")

    mutNormNameElement = mutNormNameElement.replaceAll("sky$", "ski")
    mutNormNameElement = mutNormNameElement.replaceAll("skij$", "ski")
    mutNormNameElement = mutNormNameElement.replaceAll("skii$", "ski")

    mutNormNameElement = mutNormNameElement.replaceAll("yuk$", "uk")
    mutNormNameElement = mutNormNameElement.replaceAll("juk$", "uk")

    // Some standardizations in relation to the Chinese
    mutNormNameElement = mutNormNameElement.replaceAll("-tao", "tao")

    mutNormNameElement
  }
}
