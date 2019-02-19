/**
  * author:   Ronny Fuchs, info@asderix.com
  * licence:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.normalization

import java.text.Normalizer
import java.util.Locale

object TextNormalizer {

  /**
    * Default normalize method. Most likely for person names.
    * @param text The string, a full name, to normalize.
    * @return
    */
  def normalize(text: String) : String = {
    val regexPattern = "[^abcdefghijklmnopqrstuvwxyz0123456789 -]".r
    var mutNormString = text.toLowerCase(Locale.ENGLISH)

    // Relevant special characters from the extended Latin alphabet which have no decomposition in the NFD form
    mutNormString = mutNormString.replace("ø", "o")
    mutNormString = mutNormString.replace("œ", "oe")
    mutNormString = mutNormString.replace("ß", "ss")
    mutNormString = mutNormString.replace("æ", "ae")
    mutNormString = mutNormString.replace("¢", "c")

    // Special characters that are relevant for the name comparison
    mutNormString = mutNormString.replace("&", "and")
    mutNormString = mutNormString.replace("+", "and")
    mutNormString = mutNormString.replace("@", "at")
    mutNormString = mutNormString.replace("\n", " ")

    // No unnecessary repetitions and useless special character constellations
    mutNormString = mutNormString.replaceAll("- -", "-")
    mutNormString = mutNormString.replaceAll(" +", " ")
    mutNormString = mutNormString.replaceAll("-+", "-")
    mutNormString = mutNormString.replaceAll(" *- *", "-")
    mutNormString = mutNormString.replaceAll("^-", "")
    mutNormString = mutNormString.replaceAll("-$", "")

    // Spelling with special characters
    mutNormString = mutNormString.replace("mª", "maria")

    // several name elements that only make sense together and represent one element
    mutNormString = mutNormString.replaceAll(" von *der ", " vonder ")
    mutNormString = mutNormString.replaceAll("^von *der ", "vonder ")

    mutNormString = mutNormString.replaceAll(" von *de ", " vonde ")
    mutNormString = mutNormString.replaceAll("^von *de ", "vonde ")

    mutNormString = mutNormString.replaceAll(" van *der ", " vander ")
    mutNormString = mutNormString.replaceAll("^van *der ", "vander ")

    mutNormString = mutNormString.replaceAll(" van *de ", " vande ")
    mutNormString = mutNormString.replaceAll("^van *de ", "vande ")

    mutNormString = mutNormString.replaceAll(" de *la ", " dela ")
    mutNormString = mutNormString.replaceAll("^de *la ", "dela ")

    // Normalized in NFD form (decomposition), in lower case letters
    mutNormString = Normalizer.normalize(mutNormString, Normalizer.Form.NFD).toLowerCase(Locale.ENGLISH).trim

    // Only standard Latin letters and numbers, hyphens and spaces
    mutNormString = regexPattern replaceAllIn(mutNormString, "")
    mutNormString
  }

  /**
    * Special normalize method for organisation names. This method take care of some legal forms
    * with more than one word/name element. Example: GmbH & Co. KG. This method first call
    * normalize itself.
    * @param text The string, full name, to normalize.
    * @return
    */
  def normalizeWithLegalForm(text : String) : String = {
    var mutNormString = normalize(text)

    // Compact common legal forms
    mutNormString = mutNormString.replace("gmbh *and *co *kg", "gmbh_and_co_kg")
    mutNormString = mutNormString.replace("gmbh *and *co *ohg", "gmbh_and_co_ohg")
    mutNormString = mutNormString.replace("gmbh *and *co *kgaa", "gmbh_and_co_kgaa")

    mutNormString = mutNormString.replace("ohg *mbh", "ohg_mbh")

    mutNormString = mutNormString.replace("ag *and *co *ohg", "ag_and_co_ohg")
    mutNormString = mutNormString.replace("ag *and *co *kgaa", "ag_and_co_kgaa")
    mutNormString = mutNormString.replace("ag *and *co *kg", "ag_and_co_kg")

    mutNormString = mutNormString.replace("stiftung *and *co *kgaa", "stiftung_and_co_gkaa")

    mutNormString = mutNormString.replace("co-operativ", "cooperativ")
    mutNormString = mutNormString.replace("societe *cooperative", "societe_cooperative")
    mutNormString = mutNormString.replace("societa *cooperativa", "societa_cooperativa")

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

  /**
    * This method normalize a single name element - not a full name. Actually there are some normalizations
    * for Russian names.
    * @param nameElement String representing the name element.
    * @return
    */
  def normalizeNameElement(nameElement : String) : String = {
    var mutNormNameElement = nameElement

    // Some standardizations in relation to the Russian
    mutNormNameElement = mutNormNameElement.replaceAll("witsch${1}", "vic")
    mutNormNameElement = mutNormNameElement.replaceAll("witsch-{1}", "vic-")
    mutNormNameElement = mutNormNameElement.replaceAll("vich${1}", "vic")
    mutNormNameElement = mutNormNameElement.replaceAll("vich-{1}", "vic-")
    mutNormNameElement = mutNormNameElement.replaceAll("off${1}", "ov")
    mutNormNameElement = mutNormNameElement.replaceAll("off-{1}", "ov-")
    mutNormNameElement = mutNormNameElement.replaceAll("ew${1}", "ev")
    mutNormNameElement = mutNormNameElement.replaceAll("ew-{1}", "ev-")
    mutNormNameElement = mutNormNameElement.replaceAll("ow${1}", "ov")
    mutNormNameElement = mutNormNameElement.replaceAll("ow-{1}", "ov-")
    mutNormNameElement = mutNormNameElement.replaceAll("^wl{1}", "vl")
    mutNormNameElement = mutNormNameElement.replaceAll("-wl{1}", "-vl")
    mutNormNameElement = mutNormNameElement.replaceAll("^iw{1}", "iv")
    mutNormNameElement = mutNormNameElement.replaceAll("-iw{1}", "-iv")
    mutNormNameElement = mutNormNameElement.replaceAll("^wi{1}", "vi")
    mutNormNameElement = mutNormNameElement.replaceAll("-wi{1}", "-vi")

    mutNormNameElement
  }
}
