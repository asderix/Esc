/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.utils

/**
  * Object providing methods for check an normalizing legal forms.
  */
object LegalForm {
  /**
  * Extension function to check whether a string is a legal form or not.
  * @return true if it match a legal form, otherwise false.
  */
  extension(nameElement : String) def isLegalForm : Boolean = {
      legalFormList.contains(nameElement)
  }

  /**
  * Extension function to convert a string to a legal form in German.
  * @return (true, legalFormNameInDe) if the legal form was found.
  *         (false, originalString) if the legal form was not found.
  */
  extension(nameElement : String) def toDeLegalForm : (Boolean, String) = {
      legalForms.get(nameElement) match {
        case Some(x) => (true, x)
        case None => (false, nameElement)
      }
  }

  // ---
  private val legalForms = Map(
    "ag" -> "ag", "ab" -> "ag", "ad" -> "ag", "asa" -> "ag", "at" -> "ag", "bat" -> "ag", "bhd" -> "ag", "corp" -> "ag", "dd" -> "ag", "inc" -> "ag", "kk" -> "ag", "ltd" -> "ag", "lte" -> "ag", "nv" -> "ag", "nyrt" -> "ag", "oao" -> "ag", "oy" -> "ag", "pao" -> "ag", "pjsc" -> "ag", "plc" -> "ag", "pt" -> "ag", "rt" -> "ag", "sa" -> "ag", "sarf" -> "ag", "sas" -> "ag", "se" -> "ag", "sha" -> "ag", "spa" -> "ag", "ssk" -> "ag", "teo" -> "ag", "zrt" -> "ag", "lc" -> "ag", "ao" -> "ag", "limited" -> "ag", "cifingedig" -> "ag", "cif" -> "ag", "corporation" -> "ag", "aktiengesellschaft" -> "ag", "company" -> "ag",
    "ag_and_co_ohg" -> "ag_and_co_ohg",
    "ag_and_co_ohg" -> "ag_and_co_ohg",
    "ag_and_co_kg" -> "ag_and_co_kg",
    "ag_and_co_kgaa" -> "ag_and_co_kgaa",
    "gmbh" -> "gmbh", "aps" -> "gmbh", "ba" -> "gmbh", "bv" -> "gmbh", "doo" -> "gmbh", "kft" -> "gmbh", "lda" -> "gmbh", "llc" -> "gmbh", "ltda" -> "gmbh", "ood" -> "gmbh", "ooo" -> "gmbh", "oy" -> "gmbh", "sarl" -> "gmbh", "sl" -> "gmbh", "sprl" -> "gmbh", "srl" -> "gmbh", "sro" -> "gmbh", "taa" -> "gmbh", "tob" -> "gmbh", "yk" -> "gmbh", "lc" -> "gmbh", "limited" -> "gmbh", "cifingedig" -> "gmbh", "cif" -> "gmbh",
    "gmbh_and_co_kg" -> "gmbh_and_co_kg",
    "gmbh_and_co_kgaa" -> "gmbh_and_co_kgaa",
    "gmbh_and_co_ohg" -> "gmbh_and_co_ohg", "gmbh_and_co_kg" -> "gmbh_and_co_ohg", "gmbh_and_co_kgaa" -> "gmbh_and_co_ohg",
    "genossenschaft" -> "gen", "gen" -> "gen", "cooperativ" -> "gen", "societe_cooperative" -> "gen", "societa_cooperativa" -> "gen",
    "stiftung" -> "stiftung",  "foundation" -> "stiftung",  "fondation" -> "stiftung", "stiftung_and_co_gkaa" -> "stiftung",
    "stiftung_and_co_kgaa" -> "stiftung_and_co_kgaa",
    "ohg_mbh" -> "ohg_mbh",
    "uc" -> "uc"
  )

  // ---
  private val legalFormList = legalForms.keys.toList

}
