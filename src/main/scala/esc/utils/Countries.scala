/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.utils

object Countries {

  /**
    * Implicit function to check whether a string is a country (true) or not (false).
    * @param nameElement String to check for a country.
    */
  implicit class CountryList(nameElement : String) {

    /**
      * Implicit function to check whether a string is a country or not.
      * @return true if it match a country, otherwise false.
      */
    def isCountry : Boolean = {
      countryList.contains(nameElement)
    }

    /**
      * Implicit function to check whether a string is a country or not.
      * @return true if the country was found and the 2 letter iso code.
      *         false if not found and the original nameElement string.
      */
    def toIsoCountry : (Boolean, String) = {
      countries.get(nameElement) match {
        case Some(x) => (true, x)
        case None => (false, nameElement)
      }
    }
  }

  // ---
  private val countries = Map(
              "ac" -> "ac", "ascension" -> "ac", "asc" -> "ac",
              "ad" -> "ad", "andorra" -> "ad", "andorre" -> "ad", "principat d'andorra" -> "ad", "and" -> "ad",
              "ae" -> "ae", "vereinigte arabische emirate" -> "ae", "emirati arabi uniti" -> "ae", "united arab emirates" -> "ae", "emirats arabes unis" -> "ae", "are" -> "ae", "uae" -> "ae",
              "af" -> "af", "afghanistan" -> "af", "afg" -> "af",
              "ag" -> "ag", "antigua und barbuda" -> "ag", "antigua e barbuda" -> "ag", "antigua and barbuda" -> "ag", "antigua-et-barbuda" -> "ag", "atg" -> "ag", "ant" -> "ag",
              "ai" -> "ai", "anguilla" -> "ai", "aia" -> "ai",
              "al" -> "al", "albanien" -> "al", "albania" -> "al", "albanie" -> "al", "sqipni" -> "al", "alb" -> "al",
              "am" -> "am", "armenien" -> "am", "armenia" -> "am", "aarmenie" -> "am", "hayastani hanrapetout youn" -> "am", "arm" -> "am",
              "ao" -> "ao", "angola" -> "ao", "ago" -> "ao", "ang" -> "ao",
              "aq" -> "aq", "antarktika" -> "aq", "antartide" -> "aq", "antarctica" -> "aq", "antarctique" -> "aq", "ata" -> "aq",
              "ar" -> "ar", "argentinien" -> "ar", "argentina" -> "ar", "argentine" -> "ar", "arg" -> "ar",
              "as" -> "as", "amerikanisch-samoa" -> "as", "samoa americane" -> "as", "american samoa" -> "as", "samoa americaines" -> "as", "asm" -> "as", "asa" -> "as",
              "at" -> "at", "österreich" -> "at", "austria" -> "at", "autriche" -> "at", "aut" -> "at", "osterreich" -> "at",
              "au" -> "au", "australien" -> "au", "australia" -> "au", "australie" -> "au", "aus" -> "au",
              "aw" -> "aw", "aruba" -> "aw", "abw" -> "aw", "aru" -> "aw",
              "ax" -> "ax", "island" -> "ax", "aland" -> "ax", "isole iland" -> "ax", "aland islands" -> "ax", "iles aland" -> "ax", "ala" -> "ax",
              "az" -> "az", "aserbaidschan" -> "az", "azerbaigian" -> "az", "azerbaijan" -> "az", "azerbaidjan" -> "az", "aze" -> "az",
              "ba" -> "ba", "bosnien und herzegowina" -> "ba", "bosnia ed erzegovina" -> "ba", "bosnia and herzegovina" -> "ba", "bosnie-herzegovine" -> "ba", "republika bosna i hercegovina" -> "ba", "bosnia and herzegovina" -> "ba", "republika srpska" -> "ba", "bih" -> "ba", "bosnien" -> "ba",
              "bb" -> "bb", "barbados" -> "bb", "barbade" -> "bb", "brb" -> "bb", "bar" -> "bb",
              "bd" -> "bd", "bangladesch" -> "bd", "bangladesh" -> "bd", "bgd" -> "bd", "ban" -> "bd",
              "be" -> "be", "belgien" -> "be", "belgio" -> "be", "belgium" -> "be", "belgique" -> "be", "bel" -> "be",
              "bf" -> "bf", "burkina faso" -> "bf", "bfa" -> "bf", "bur" -> "bf", "burkina" -> "bf",
              "bg" -> "bg", "bulgarien" -> "bg", "bulgaria" -> "bg", "bulgarie" -> "bg", "bgr" -> "bg", "bul" -> "bg",
              "bh" -> "bh", "bahrain" -> "bh", "bahrein" -> "bh", "mamlakat al-bahrayn" -> "bh", "bhr" -> "bh", "brn" -> "bh",
              "bi" -> "bi", "burundi" -> "bi", "uburundi" -> "bi", "bdi" -> "bi",
              "bj" -> "bj", "benin" -> "bj", "ben" -> "bj",
              "bl" -> "bl", "saint-barthelemy" -> "bl", "blm" -> "bl", "st. barthelemy" -> "bl",
              "bm" -> "bm", "bermuda" -> "bm", "bermudes" -> "bm", "bmu" -> "bm", "ber" -> "bm",
              "bn" -> "bn", "brunei darussalam" -> "bn", "brunei" -> "bn", "negara brunei darussalam" -> "bn", "brn" -> "bn", "bru" -> "bn",
              "bo" -> "bo", "bolivien" -> "bo", "bolivia" -> "bo", "bolivie" -> "bo", "bol" -> "bo",
              "bq" -> "bq", "bonaire, sint eustatius und saba (niederlande)" -> "bq", "bonaire, sint eustatius und saba" -> "bq", "isole bes" -> "bq", "bonaire, sint eustatius and saba" -> "bq", "bonaire" -> "bq", "sint-eustatius" -> "bq", "bes" -> "bq",
              "br" -> "br", "brasilien" -> "br", "brasile" -> "br", "brazil" -> "br", "bresil" -> "br", "republica federativa do brasil" -> "br", "bra" -> "br",
              "bs" -> "bs", "bahamas" -> "bs", "commonwealth of the bahamas" -> "bs", "bhs" -> "bs", "bah" -> "bs",
              "bt" -> "bt", "bhutan" -> "bt", "bhoutan" -> "bt", "druk yul" -> "bt", "btn" -> "bt", "bhu" -> "bt",
              "bv" -> "bv", "bouvetinsel" -> "bv", "isola bouvet" -> "bv", "bouvet island" -> "bv", "bouvet" -> "bv", "bvt" -> "bv",
              "bw" -> "bw", "botswana" -> "bw", "lefatshe la botswana" -> "bw", "bwa" -> "bw", "bot" -> "bw",
              "by" -> "by", "belarus" -> "by", "weissrussland" -> "by", "bielorussia" -> "by", "bielorussie" -> "by", "blr" -> "by", "bys" -> "by",
              "bz" -> "bz", "belize" -> "bz", "blz" -> "bz", "biz" -> "bz",
              "ca" -> "ca", "kanada" -> "ca", "canada" -> "ca", "can" -> "ca",
              "cc" -> "cc", "kokosinseln" -> "cc", "isole cocos e keeling" -> "cc", "cocos (keeling) islands" -> "cc", "iles cocos" -> "cc", "cck" -> "cc",
              "cd" -> "cd", "demokratische republik kongo" -> "cd", "kongo" -> "cd", "repubblica democratica del congo" -> "cd", "democratic republic of the congo" -> "cd", "la republique democratique du congo" -> "cd", "congo, the democratic republic of the" -> "cd", "cod" -> "cd",
              "cf" -> "cf", "zentralafrikanische republik" -> "cf", "repubblica centrafricana" -> "cf", "central african republic" -> "cf", "centraficaine" -> "cf", "caf" -> "cf", "zentralafrika" -> "cf",
              "cg" -> "cg", "republik kongo" -> "cg", "repubblica del congo" -> "cg", "congo" -> "cg", "republic of congo" -> "cg", "cog" -> "cg", "cgo" -> "cg",
              "ch" -> "ch", "schweiz" -> "ch", "conföderatio helvetica" -> "ch", "svizzera" -> "ch", "switzerland" -> "ch", "swiss" -> "ch", "suisse" -> "ch", "schweizerische eidgenossenschaft" -> "ch", "schweizerisch" -> "ch", "che" -> "ch", "sui" -> "ch",
              "ci" -> "ci", "elfenbeinküste" -> "ci", "costa d'avorio" -> "ci", "cote d'ivoire" -> "ci", "civ" -> "ci", "elfenbeinkuste" -> "ci",
              "ck" -> "ck", "cookinseln" -> "ck", "isole cook" -> "ck", "cook islands" -> "ck", "iles cook" -> "ck", "cok" -> "ck",
              "cl" -> "cl", "chile" -> "cl", "cile" -> "cl", "chili" -> "cl", "chl" -> "cl", "chi" -> "cl",
              "cm" -> "cm", "kamerun" -> "cm", "camerun" -> "cm", "cameroon" -> "cm", "cameroun" -> "cm", "cmr" -> "cm",
              "cn" -> "cn", "volksrepublik china" -> "cn", "china" -> "cn", "cina" -> "cn", "chine" -> "cn", "macau" -> "cn", "chn" -> "cn",
              "co" -> "co", "kolumbien" -> "co", "colombia" -> "co", "colombie" -> "co", "col" -> "co",
              "cr" -> "cr", "costa rica" -> "cr", "republica de costa rica" -> "cr", "cri" -> "cr", "crc" -> "cr",
              "cu" -> "cu", "kuba" -> "cu", "cuba" -> "cu", "republica de cuba" -> "cu", "cub" -> "cu",
              "cv" -> "cv", "kap verde" -> "cv", "capo verde" -> "cv", "cap-vert" -> "cv", "cabo verde" -> "cv", "cape verde" -> "cv", "cpv" -> "cv",
              "cw" -> "cw", "curacao" -> "cw", "cuw" -> "cw",
              "cx" -> "cx", "weihnachtsinsel" -> "cx", "isola di natale" -> "cx", "christmas island" -> "cx", "ile christmas" -> "cx", "cxr" -> "cx",
              "cy" -> "cy", "zypern" -> "cy", "cipro" -> "cy", "cyprus" -> "cy", "chypre" -> "cy", "cyp" -> "cy",
              "cz" -> "cz", "tschechische republik" -> "cz", "tschechien" -> "cz", "repubblica ceca" -> "cz", "czech republic" -> "cz", "tcheque" -> "cz", "cesky republika" -> "cz", "cze" -> "cz",
              "de" -> "de", "deutschland" -> "de", "deutsche" -> "de", "germania" -> "de", "germany" -> "de", "allemagne" -> "de", "deu" -> "de", "ger" -> "de",
              "dj" -> "dj", "dschibuti" -> "dj", "gibuti" -> "dj", "djibouti" -> "dj", "gabuutih ummuuno" -> "dj", "dji" -> "dj",
              "dk" -> "dk", "dänemark" -> "dk", "danimarca" -> "dk", "denmark" -> "dk", "danemark" -> "dk", "kongeriget danmark" -> "dk", "dnk" -> "dk", "den" -> "dk",
              "dm" -> "dm", "dominica" -> "dm", "dominique" -> "dm", "commonwealth of dominica" -> "dm", "dma" -> "dm",
              "do" -> "do", "dominikanische republik" -> "do", "repubblica dominicana" -> "do", "dominican republic" -> "do", "dominicaine" -> "do", "republique dominicaine" -> "do", "republica dominicana" -> "do", "dom" -> "do",
              "dz" -> "dz", "algerien" -> "dz", "algeria" -> "dz", "algerie" -> "dz", "dza" -> "dz", "alg" -> "dz",
              "ec" -> "ec", "ecuador" -> "ec", "equateuer" -> "ec", "republica del equador" -> "ec", "ecu" -> "ec",
              "ee" -> "ee", "estland" -> "ee", "estonia" -> "ee", "estonie" -> "ee", "eesti" -> "ee", "est" -> "ee",
              "eg" -> "eg", "ägypten" -> "eg", "egitto" -> "eg", "egypt" -> "eg", "egypte" -> "eg", "egy" -> "eg", "agypten" -> "eg",
              "eh" -> "eh", "westsahara" -> "eh", "sahara occidentale" -> "eh", "western sahara" -> "eh", "sahara occidental" -> "eh", "esh" -> "eh",
              "er" -> "er", "eritrea" -> "er", "erythree" -> "er", "eri" -> "er",
              "es" -> "es", "spanien" -> "es", "spagna" -> "es", "spain" -> "es", "espagne" -> "es", "espana" -> "es", "esp" -> "es",
              "et" -> "et", "äthiopien" -> "et", "etiopia" -> "et", "ethiopia" -> "et", "ethiopie" -> "et", "eth" -> "et", "athiopien" -> "et",
              "fi" -> "fi", "finnland" -> "fi", "finlandia" -> "fi", "finland" -> "fi", "suomen tasavalta" -> "fi", "fin" -> "fi",
              "fj" -> "fj", "fidschi" -> "fj", "figi" -> "fj", "fiji" -> "fj", "matanitu ko viti" -> "fj", "fji" -> "fj", "fij" -> "fj",
              "fk" -> "fk", "falklandinseln" -> "fk", "isole falkland" -> "fk", "falkland islands" -> "fk", "falkland islands (malvinas)" -> "fk", "iles falkland" -> "fk", "flk" -> "fk",
              "fm" -> "fm", "mikronesien" -> "fm", "micronesia" -> "fm", "federated states of micronesia" -> "fm", "micronesie" -> "fm", "fsm" -> "fm",
              "fo" -> "fo", "faroer" -> "fo", "isole feroer" -> "fo", "faroe islands" -> "fo", "iles feroe" -> "fo", "fro" -> "fo",
              "fr" -> "fr", "frankreich" -> "fr", "francia" -> "fr", "france" -> "fr", "republique francaise" -> "fr", "fra" -> "fr",
              "ga" -> "ga", "gabun" -> "ga", "gabon" -> "ga", "republique gabonaise" -> "ga", "gab" -> "ga",
              "gb" -> "gb", "vereinigtes königreich grossbritannien und nordirland" -> "gb", "grossbritannien" -> "gb", "regno unito" -> "gb", "united kingdom" -> "gb", "royaume-uni" -> "gb", "alderney" -> "gb", "british indian ocean terr" -> "gb", "uk" -> "gb", "gbr" -> "gb",
              "gd" -> "gd", "grenada" -> "gd", "commonwealth of grenada" -> "gd", "grd" -> "gd", "grn" -> "gd",
              "ge" -> "ge", "georgien" -> "ge", "georgia" -> "ge", "georgie" -> "ge", "geo" -> "ge",
              "gf" -> "gf", "französisch-guayana" -> "gf", "guyana francese" -> "gf", "french guiana" -> "gf", "guyane" -> "gf", "guyane francaise" -> "gf", "guf" -> "gf",
              "gg" -> "gg", "guernsey" -> "gg", "ggy" -> "gg",
              "gh" -> "gh", "ghana" -> "gh", "gha" -> "gh",
              "gi" -> "gi", "gibraltar" -> "gi", "gibilterra" -> "gi", "gib" -> "gi",
              "gl" -> "gl", "grönland" -> "gl", "groenlandia" -> "gl", "greenland" -> "gl", "groenland" -> "gl", "kalaallit nunaat" -> "gl", "grl" -> "gl", "gronland" -> "gl",
              "gm" -> "gm", "gambia" -> "gm", "republic of gambia" -> "gm", "gmb" -> "gm",
              "gn" -> "gn", "guinea" -> "gn", "guinee" -> "gn", "republique de guinnee" -> "gn", "gin" -> "gn", "gui" -> "gn",
              "gp" -> "gp", "guadeloupe" -> "gp", "guadalupa" -> "gp", "glp" -> "gp",
              "gq" -> "gq", "aquatorialguinea" -> "gq", "guinea equatoriale" -> "gq", "equatorial guinea" -> "gq", "guinee" -> "gq", "guinee equatoriale" -> "gq", "gnq" -> "gq", "geq" -> "gq",
              "gr" -> "gr", "griechenland" -> "gr", "grecia" -> "gr", "greece" -> "gr", "grece" -> "gr", "grc" -> "gr", "gre" -> "gr",
              "gs" -> "gs", "südgeorgien und die südlichen sandwichinseln" -> "gs", "georgia del sud e isole sandwich" -> "gs", "south georgia and the south sandwich islands" -> "gs", "georgie du sud et les iles sandwich du sud" -> "gs", "sgs" -> "gs",
              "gt" -> "gt", "guatemala" -> "gt", "repeblica de guatemala" -> "gt", "gtm" -> "gt", "gua" -> "gt",
              "gu" -> "gu", "guam" -> "gu", "gum" -> "gu",
              "gw" -> "gw", "guinea-bissau" -> "gw", "guinee bissau" -> "gw", "repeblica da guin-bissau" -> "gw", "gnb" -> "gw", "gbs" -> "gw",
              "gy" -> "gy", "guyana" -> "gy", "guy" -> "gy",
              "hk" -> "hk", "hongkong" -> "hk", "hong kong" -> "hk", "hkg" -> "hk",
              "hm" -> "hm", "heard und mcdonaldinseln" -> "hm", "isole heard e mcdonald" -> "hm", "heard island and mcdonald islands" -> "hm", "heard et mcdonald" -> "hm", "ile heard et mcdonald" -> "hm", "hmd" -> "hm",
              "hn" -> "hn", "honduras" -> "hn", "republica de honduras" -> "hn", "hnd" -> "hn",  "hon" -> "hn",
              "hr" -> "hr", "kroatien" -> "hr", "croazia" -> "hr", "croatia" -> "hr", "croatie" -> "hr", "republika hrvatska" -> "hr", "hrv" -> "hr",
              "ht" -> "ht", "haiti" -> "ht", "hti" -> "ht", "hai" -> "ht",
              "hu" -> "hu", "ungarn" -> "hu", "ungheria" -> "hu", "hungary" -> "hu", "hongerie" -> "hu", "magyar" -> "hu", "hun" -> "hu",
              "id" -> "id", "indonesien" -> "id", "indonesia" -> "id", "indonesie" -> "id", "republik indonesia" -> "id", "idn" -> "id",
              "ie" -> "ie", "irland" -> "ie", "irlanda" -> "ie", "ireland" -> "ie", "irlande" -> "ie", "eire" -> "ie", "irl" -> "ie",
              "il" -> "il", "israel" -> "il", "israele" -> "il", "medinat yisrael" -> "il", "isr" -> "il",
              "im" -> "im", "insel man" -> "im", "isola di man" -> "im", "isle of man" -> "im", "ile de man" -> "im", "imn" -> "im",
              "in" -> "in", "indien" -> "in", "india" -> "in", "inde" -> "in", "ind" -> "in",
              "io" -> "io", "britisches territorium im indischen ozean" -> "io", "territorio britannico dell'oceano indiano" -> "io", "british indian ocean territory" -> "io", "territoire britannique de l'ocean indien" -> "io", "ocean indien" -> "io", "iot" -> "io",
              "iq" -> "iq", "irak" -> "iq", "iraq" -> "iq", "irq" -> "iq", "" -> "iq",
              "ir" -> "ir", "islamische republik iran" -> "ir", "iran" -> "ir", "islamic republic of iran" -> "ir", "irn" -> "ir",  "iri" -> "ir",
              "is" -> "is", "island" -> "is", "islanda" -> "is", "iceland" -> "is", "islande" -> "is", "isl" -> "is",
              "it" -> "it", "italien" -> "it", "italia" -> "it", "italy" -> "it", "italie" -> "it", "ita" -> "it",
              "je" -> "je", "jersey" -> "je", "jey" -> "je",
              "jm" -> "jm", "jamaika" -> "jm", "giamaica" -> "jm", "jamaica" -> "jm", "jamaique" -> "jm", "jam" -> "jm",
              "jo" -> "jo", "jordanien" -> "jo", "giordania" -> "jo", "jordan" -> "jo", "jordanie" -> "jo", "al mamlakah al urduniyah al hashimiyah" -> "jo", "jor" -> "jo",
              "jp" -> "jp", "japan" -> "jp", "giappone" -> "jp", "japon" -> "jp", "nihon koku" -> "jp", "jpn" -> "jp",
              "ke" -> "ke", "kenia" -> "ke", "kenya" -> "ke", "jamhuri ya kenya" -> "ke", "ken" -> "ke",
              "kg" -> "kg", "kirgisistan" -> "kg", "kirghizistan" -> "kg", "kyrgyzstan" -> "kg", "kgz" -> "kg",
              "kh" -> "kh", "kambodscha" -> "kh", "cambogia" -> "kh", "cambodia" -> "kh", "cambodge" -> "kh", "khm" -> "kh", "cam" -> "kh",
              "ki" -> "ki", "kiribati" -> "ki", "kir" -> "ki",
              "km" -> "km", "komoren" -> "km", "comore" -> "km", "comoros" -> "km", "camores" -> "km", "com" -> "km",
              "kn" -> "kn", "st kitts und nevis" -> "kn", "saint kitts e nevis" -> "kn", "saint kitts and nevis" -> "kn", "saint-christophe-et-nieves" -> "kn", "saint-kitts-et-nevis" -> "kn", "kna" -> "kn", "skn" -> "kn",
              "kp" -> "kp", "demokratische volksrepublik korea" -> "kp", "nordkorea" -> "kp", "corea del nord" -> "kp", "northkorea" -> "kp", "democratic people's republic of korea" -> "kp", "core du nord" -> "kp", "republique populaire democratique de coree" -> "kp",  "korea, democratic people's republic of" -> "kp", "prk" -> "kp",
              "kr" -> "kr", "republik korea" -> "kr", "südkorea" -> "kr", "corea del sud" -> "kr", "southkorea" -> "kr", "republic of korea" -> "kr", "core du sud" -> "kr", "republique de coree" -> "kr",  "daehan min-guk" -> "kr", "korea, republic of" -> "kr", "kor" -> "kr",
              "kw" -> "kw", "kuwait" -> "kw", "koweit" -> "kw", "dawlat al kuwayt" -> "kw", "kwt" -> "kw", "kuw" -> "kw",
              "ky" -> "ky", "kaimaninseln" -> "ky", "isole cayman" -> "ky", "cayman islands" -> "ky", "iles caimans" -> "ky", "caimanes" -> "ky", "cym" -> "ky", "cay" -> "ky",
              "kz" -> "kz", "kasachstan" -> "kz", "kazakistan" -> "kz", "kazakhstan" -> "kz", "qazaqstan respublkas" -> "kz", "kaz" -> "kz",
              "la" -> "la", "demokratische volksrepublik laos" -> "la", "laos" -> "la", "lao people's democratic republic" -> "la", "lao" -> "la",
              "lb" -> "lb", "libanon" -> "lb", "libano" -> "lb", "lebanon" -> "lb", "liban" -> "lb", "lbn" -> "lb", "lib" -> "lb",
              "lc" -> "lc", "st lucia" -> "lc", "santa lucia" -> "lc", "saint lucia" -> "lc", "sainte-lucie" -> "lc", "lca" -> "lc",
              "li" -> "li", "liechtenstein" -> "li", "fürstentum liechtenstein" -> "li", "lie" -> "li",
              "lk" -> "lk", "sri lanka" -> "lk", "prajatantrika samajavadi janarajaya" -> "lk", "lka" -> "lk", "sri" -> "lk",
              "lr" -> "lr", "liberia" -> "lr", "republic of liberia" -> "lr", "lbr" -> "lr",
              "ls" -> "ls", "lesotho" -> "ls", "mmuso wa lesotho" -> "ls", "lso" -> "ls",
              "lt" -> "lt", "litauen" -> "lt", "lituania" -> "lt", "lithuania" -> "lt", "lituanie" -> "lt", "lietuvos respublika" -> "lt", "ltu" -> "lt",
              "lu" -> "lu", "luxemburg" -> "lu", "lussemburgo" -> "lu", "luxembourg" -> "lu", "grossherzogtum luxembourg" -> "lu", "lux" -> "lu",
              "lv" -> "lv", "lettland" -> "lv", "lettonia" -> "lv", "latvia" -> "lv", "lettonie" -> "lv", "latvijas" -> "lv", "lva" -> "lv", "lat" -> "lv",
              "ly" -> "ly", "libyen" -> "ly", "libia" -> "ly", "libya" -> "ly", "libye" -> "ly", "libyienne" -> "ly", "lby" -> "ly",
              "ma" -> "ma", "marokko" -> "ma", "marocco" -> "ma", "maroc" -> "ma", "morocco" -> "ma", "mar" -> "ma",
              "mc" -> "mc", "monaco" -> "mc", "principato di monaco" -> "mc", "mco" -> "mc", "mon" -> "mc",
              "md" -> "md", "moldawien" -> "md", "republik moldau" -> "md", "moldavia" -> "md", "moldova" -> "md", "republic of moldova" -> "md", "moldavie" -> "md", "republica moldova" -> "md",  "mda" -> "md",
              "me" -> "me", "montenegro" -> "me", "yugoslavia & montenegro" -> "me", "serbia & montenegro" -> "me", "mne" -> "me",
              "mf" -> "mf", "saint-martin" -> "mf", "saint martin (french part)" -> "mf", "maf" -> "mf",
              "mg" -> "mg", "madagaskar" -> "mg", "madagascar" -> "mg", "mdg" -> "mg", "mad" -> "mg",
              "mh" -> "mh", "marshallinseln" -> "mh", "isole marshall" -> "mh", "marshall islands" -> "mh", "marshall" -> "mh", "iles marshall" -> "mh", "mhl" -> "mh",
              "mk" -> "mk", "nordmazedonien" -> "mk", "repubblica di macedonia" -> "mk", "macedonia" -> "mk", "the former yugoslav republic of macedonia" -> "mk", "macedoine" -> "mk", "mkd" -> "mk",
              "ml" -> "ml", "mali" -> "ml", "republique du mali" -> "ml", "mli" -> "ml", "466" -> "ml",
              "mm" -> "mm", "myanmar" -> "mm", "birmania" -> "mm", "birmanie" -> "mm", "burma" -> "mm", "mmr" -> "mm", "mya" -> "mm",
              "mn" -> "mn", "mongolei" -> "mn", "mongolia" -> "mn", "mongolie" -> "mn", "mongol uls" -> "mn", "mng" -> "mn", "mgl" -> "mn",
              "mo" -> "mo", "macao" -> "mo", "macao" -> "mo", "macao" -> "mo", "mac" -> "mo",
              "mp" -> "mp", "nordliche marianen" -> "mp", "isole marianne settentrionali" -> "mp", "northern mariana islands" -> "mp", "iles mariannes du nord" -> "mp", "commonwealth of the northern mariana islands" -> "mp", "mnp" -> "mp",
              "mq" -> "mq", "martinique" -> "mq", "martinica" -> "mq", "mtq" -> "mq",
              "mr" -> "mr", "mauretanien" -> "mr", "mauritania" -> "mr", "mauritania" -> "mr", "mauritanie" -> "mr", "republique islamique de mauritanie" -> "mr", "mrt" -> "mr", "mtn" -> "mr",
              "ms" -> "ms", "montserrat" -> "ms", "msr" -> "ms",
              "mt" -> "mt", "malta" -> "mt", "malte" -> "mt", "repubblika ta' malta" -> "mt", "mlt" -> "mt",
              "mu" -> "mu", "mauritius" -> "mu", "maurice" -> "mu", "mus" -> "mu", "mri" -> "mu",
              "mv" -> "mv", "malediven" -> "mv", "maldive" -> "mv", "maldives" -> "mv", "mdv" -> "mv",
              "mw" -> "mw", "malawi" -> "mw", "dziko la malai" -> "mw", "mwi" -> "mw", "maw" -> "mw",
              "mx" -> "mx", "mexiko" -> "mx", "messico" -> "mx", "mexico" -> "mx", "mexique" -> "mx", "estados unidos mexicanos" -> "mx", "mex" -> "mx",
              "my" -> "my", "malaysia" -> "my", "malesia" -> "my", "malaisie" -> "my", "mys" -> "my", "mas" -> "my",
              "mz" -> "mz", "mosambik" -> "mz", "mozambico" -> "mz", "mozambique" -> "mz", "repeblica de mosambique" -> "mz", "moz" -> "mz",
              "na" -> "na", "namibia" -> "na", "namibie" -> "na", "nam" -> "na",
              "nc" -> "nc", "neukaledonien" -> "nc", "nuova caledonia" -> "nc", "new caledonia" -> "nc", "nouvelle-caledonie" -> "nc", "ncl" -> "nc",
              "ne" -> "ne", "niger" -> "ne", "ner" -> "ne", "nig" -> "ne",
              "nf" -> "nf", "norfolkinsel" -> "nf", "isola norfolk" -> "nf", "norfolk island" -> "nf", "ile norfolk" -> "nf", "nfk" -> "nf",
              "ng" -> "ng", "nigeria" -> "ng", "nga" -> "ng", "ngr" -> "ng",
              "ni" -> "ni", "nicaragua" -> "ni", "republica de nicaragua" -> "ni", "nic" -> "ni", "nca" -> "ni",
              "nl" -> "nl", "niederlande" -> "nl", "holland" -> "nl", "paesi bassi" -> "nl", "netherlands" -> "nl", "pays-bas" -> "nl", "nederland" -> "nl", "nld" -> "nl", "ned" -> "nl",
              "no" -> "no", "norwegen" -> "no", "norvegia" -> "no", "norway" -> "no", "norvege" -> "no", "kongeriket norge" -> "no", "nor" -> "no",
              "np" -> "np", "nepal" -> "np", "npl" -> "np",  "nep" -> "np",
              "nr" -> "nr", "nauru" -> "nr", "ripublik naoero" -> "nr", "nru" -> "nr",
              "nu" -> "nu", "niue" -> "nu", "niu" -> "nu",
              "nz" -> "nz", "neuseeland" -> "nz", "nuova zelanda" -> "nz", "new zealand" -> "nz", "nouvelle-zelande" -> "nz", "new zeland" -> "nz", "nzl" -> "nz",
              "om" -> "om", "oman" -> "om", "saltanat uman" -> "om", "omn" -> "om", "oma" -> "om",
              "pa" -> "pa", "panama" -> "pa", "panama" -> "pa", "repeblica de panama" -> "pa", "pan" -> "pa",
              "pe" -> "pe", "peru" -> "pe", "perou" -> "pe", "peru" -> "pe", "per" -> "pe",
              "pf" -> "pf", "französisch-polynesien" -> "pf", "polinesia francese" -> "pf", "french polynesia" -> "pf", "polynesie francaise" -> "pf", "pyf" -> "pf",
              "pg" -> "pg", "papua-neuguinea" -> "pg", "papua nuova guinea" -> "pg", "papua new guinea" -> "pg", "papouasie-nouvelle-guinee" -> "pg", "png" -> "pg",
              "ph" -> "ph", "philippinen" -> "ph", "filippine" -> "ph", "philippines" -> "ph", "pilipinas" -> "ph", "phl" -> "ph", "phi" -> "ph",
              "pk" -> "pk", "pakistan" -> "pk", "pak" -> "pk",
              "pl" -> "pl", "polen" -> "pl", "polonia" -> "pl", "poland" -> "pl", "plogne" -> "pl", "polska" -> "pl", "pol" -> "pl",
              "pm" -> "pm", "st pierre und miquelon" -> "pm", "saint-pierre e miquelon" -> "pm", "saint pierre and miquelon" -> "pm", "saint-pierre-et-miquelon" -> "pm", "spm" -> "pm",
              "pn" -> "pn", "pitcairninseln" -> "pn", "isole pitcairn" -> "pn", "pitcairn" -> "pn", "pcn" -> "pn",
              "pr" -> "pr", "puerto rico" -> "pr", "estado libre asociado de puerto rico" -> "pr", "pri" -> "pr", "" -> "pr", "pur" -> "pr",
              "ps" -> "ps", "palastinensische autonomiegebiete" -> "ps", "palestina" -> "ps", "palestinian territory" -> "ps", "palestine" -> "ps", "territoire palestinien" -> "ps", "pse" -> "ps", "ple" -> "ps",
              "pt" -> "pt", "portugal" -> "pt", "portogallo" -> "pt", "prt" -> "pt", "por" -> "pt",
              "pw" -> "pw", "palau" -> "pw", "palaos" -> "pw", "republic of palau" -> "pw", "plw" -> "pw",
              "py" -> "py", "paraguay" -> "py", "pry" -> "py", "par" -> "py",
              "qa" -> "qa", "katar" -> "qa", "qatar" -> "qa", "dawlat qatar" -> "qa", "qat" -> "qa",
              "re" -> "re", "reunion" -> "re", "riunione" -> "re", "reu" -> "re",
              "ro" -> "ro", "rumanien" -> "ro", "romania" -> "ro", "roumanie" -> "ro", "rou" -> "ro", "rom" -> "ro",
              "rs" -> "rs", "serbien" -> "rs", "serbia" -> "rs", "serbie" -> "rs", "republika srbija" -> "rs", "srb" -> "rs",
              "ru" -> "ru", "russische föderation" -> "ru", "russland" -> "ru", "russia" -> "ru", "russian federation" -> "ru", "russie" -> "ru", "rus" -> "ru", "rissische foderation" -> "ru",
              "rw" -> "rw", "ruanda" -> "rw", "rwanda" -> "rw", "rwa" -> "rw",
              "sa" -> "sa", "saudi-arabien" -> "sa", "arabia saudita" -> "sa", "saudi arabia" -> "sa", "arabie saoudite" -> "sa", "mamlakah al `arabiyahas su`udiyah" -> "sa", "sau" -> "sa", "ksa" -> "sa",
              "sb" -> "sb", "salomonen" -> "sb", "isole salomone" -> "sb", "solomon islands" -> "sb", "iles salomon" -> "sb", "slb" -> "sb", "sol" -> "sb",
              "sc" -> "sc", "seychellen" -> "sc", "seychelles" -> "sc", "repiblik sesel" -> "sc", "syc" -> "sc", "sey" -> "sc",
              "sd" -> "sd", "sudan" -> "sd", "soudan" -> "sd", "jumhuriyat al-sudan" -> "sd", "sdn" -> "sd", "sud" -> "sd",
              "se" -> "se", "schweden" -> "se", "svezia" -> "se", "sweden" -> "se", "suede" -> "se", "sverige" -> "se", "swe" -> "se",
              "sg" -> "sg", "singapur" -> "sg", "singapore" -> "sg", "singapour" -> "sg", "sgp" -> "sg", "sin" -> "sg",
              "sh" -> "sh", "st helena" -> "sh", "sant'elena" -> "sh", "saint helena, ascension and tristan da cunha" -> "sh", "saint helena" -> "sh", "sainte-helene" -> "sh", "saint helena" -> "sh", "shn" -> "sh",
              "si" -> "si", "slowenien" -> "si", "slovenia" -> "si", "slovenie" -> "si", "republika slovenija" -> "si", "svn" -> "si", "slo" -> "si",
              "sj" -> "sj", "svalbard und jan mayen" -> "sj", "svalbard e jan mayen" -> "sj", "svalbard and jan mayen" -> "sj", "svalbard et ile jan mayen" -> "sj", "sjm" -> "sj",
              "sk" -> "sk", "slowakei" -> "sk", "slovacchia" -> "sk", "slovakia" -> "sk", "slovaquie" -> "sk", "slovensky republika" -> "sk", "svk" -> "sk",
              "sl" -> "sl", "sierra leone" -> "sl", "sle" -> "sl",
              "sm" -> "sm", "san marino" -> "sm", "saint-marin" -> "sm", "smr" -> "sm",
              "sn" -> "sn", "senegal" -> "sn", "sen" -> "sn",
              "so" -> "so", "somalia" -> "so", "somalie" -> "so", "soomaaliya" -> "so", "som" -> "so",
              "sr" -> "sr", "suriname" -> "sr", "republiek suriname" -> "sr", "sur" -> "sr",
              "ss" -> "ss", "sudsudan" -> "ss", "sudan del sud" -> "ss", "south sudan" -> "ss", "soudan du sud" -> "ss", "republic of south sudan" -> "ss", "ssd" -> "ss",
              "st" -> "st", "sao tome und principe" -> "st", "sao tome e principe" -> "st", "sao tome and principe" -> "st", "sao tome-et-principe" -> "st", "stp" -> "st",
              "sv" -> "sv", "el salvador" -> "sv", "salvador" -> "sv", "republica de el salvador" -> "sv", "slv" -> "sv", "esa" -> "sv",
              "sx" -> "sx", "sint maarten" -> "sx", "sint maarten" -> "sx", "sint maarten (dutch part)" -> "sx", "saint-martin (partie neerlandaise)" -> "sx", "sxm" -> "sx",
              "sy" -> "sy", "syrien" -> "sy", "siria" -> "sy", "syrian arab republic" -> "sy", "syrian" -> "sy", "syrie" -> "sy", "syrienne" -> "sy", "syr" -> "sy",
              "sz" -> "sz", "swasiland" -> "sz", "swaziland" -> "sz", "umbuso swatini" -> "sz", "swz" -> "sz",
              "tc" -> "tc", "turks- und caicosinseln" -> "tc", "turks e caicos" -> "tc", "turks and caicos islands" -> "tc", "iles turks et caiques" -> "tc", "turks & caicos islands" -> "tc", "tca" -> "tc",
              "td" -> "td", "tschad" -> "td", "ciad" -> "td", "chad" -> "td", "tchad" -> "td", "jumhuriyat tshad" -> "td", "tcd" -> "td", "cha" -> "td",
              "tf" -> "tf", "französische süd- und antarktisgebiete" -> "tf", "terre australi e antartiche francesi" -> "tf", "french southern territories" -> "tf", "terres australes francaises" -> "tf", "atf" -> "tf",
              "tg" -> "tg", "togo" -> "tg", "tgo" -> "tg", "tog" -> "tg",
              "th" -> "th", "thailand" -> "th", "thailandia" -> "th", "thailande" -> "th", "muang thai" -> "th", "tha" -> "th",
              "tj" -> "tj", "tadschikistan" -> "tj", "tagikistan" -> "tj", "tajikistan" -> "tj", "tadjikistan" -> "tj", "jumhurii tojikiston" -> "tj", "tjk" -> "tj",
              "tk" -> "tk", "tokelau" -> "tk", "tokelau" -> "tk", "tkl" -> "tk",
              "tl" -> "tl", "osttimor" -> "tl", "timor est" -> "tl", "timor-leste" -> "tl", "timor oriental" -> "tl", "timor-leste" -> "tl", "repeblika demokratika timor lorosa'e" -> "tl", "tp" -> "tl", "tls" -> "tl", "tmp" -> "tl",
              "tm" -> "tm", "turkmenistan" -> "tm", "tkm" -> "tm",
              "tn" -> "tn", "tunesien" -> "tn", "tunisia" -> "tn", "tunisie" -> "tn", "tun" -> "tn",
              "to" -> "to", "tonga" -> "to", "pule'anga fakatu'i 'o tonga" -> "to", "ton" -> "to", "tga" -> "to",
              "tr" -> "tr", "turkei" -> "tr", "turchia" -> "tr", "turkey" -> "tr", "turquie" -> "tr", "turkiye cumhuriyeti" -> "tr", "tur" -> "tr",
              "tt" -> "tt", "trinidad und tobago" -> "tt", "trinidad e tobago" -> "tt", "trinidad and tobago" -> "tt", "trinite-et-tobago" -> "tt", "republic of trinidad and tobago" -> "tt", "tto" -> "tt", "tri" -> "tt",
              "tv" -> "tv", "tuvalu" -> "tv", "tuv" -> "tv",
              "tw" -> "tw", "taiwan" -> "tw", "taiwan, province of china" -> "tw", "twn" -> "tw", "tpe" -> "tw",
              "tz" -> "tz", "vereinigte republik tansania" -> "tz", "tansania" -> "tz", "tanzania" -> "tz", "united republic of tanzania" -> "tz", "tanzanie" -> "tz", "jamhuri ya muungano wa tanzania" -> "tz", "tanzania, united republic of" -> "tz", "tza" -> "tz", "tan" -> "tz",
              "ua" -> "ua", "ukraine" -> "ua", "ucraina" -> "ua",  "ukraine" -> "ua", "ukr" -> "ua",
              "ug" -> "ug", "uganda" -> "ug", "ouganda" -> "ug", "jamhuri ya uganda" -> "ug", "uga" -> "ug",
              "um" -> "um", "united states minor outlying islands" -> "um", "isole minori degli stati uniti" -> "um", "united states minor outlying islands" -> "um", "iles mineueres eloignees de etwa-unis" -> "um", "umi" -> "um",
              "us" -> "us", "vereinigte staaten von amerika" -> "us", "amerika" -> "us", "stati uniti d'america" -> "us", "united states" -> "us", "etats-unis" -> "us", "usa" -> "us",
              "uy" -> "uy", "uruguay" -> "uy", "repeblica oriental del uruguay" -> "uy", "ury" -> "uy", "uru" -> "uy",
              "uz" -> "uz", "usbekistan" -> "uz", "uzbekistan" -> "uz", "ouzbekistan" -> "uz", "o'zbekiston respublikasi" -> "uz", "uzb" -> "uz",
              "va" -> "va", "vatikanstadt" -> "va", "citta del vaticano" -> "va", "holy see" -> "va", "vatican city state" -> "va", "saint-siege" -> "va", "vat" -> "va",
              "vc" -> "vc", "st. vincent und die grenadinen" -> "vc", "saint vincent e grenadine" -> "vc", "saint vincent and the grenadines" -> "vc", "saint-vincent-et-les grenadines" -> "vc", "st. vincent & the grenadines" -> "vc", "vct" -> "vc", "vin" -> "vc",
              "ve" -> "ve", "venezuela" -> "ve", "bolivarian republic of venezuela" -> "ve", "ven" -> "ve",
              "vg" -> "vg", "britische jungferninseln" -> "vg", "isole vergini britanniche" -> "vg", "virgin islands, british" -> "vg", "iles vierges britanniques" -> "vg", "vgb" -> "vg", "ivb" -> "vg",
              "vi" -> "vi", "amerikanische jungferninseln" -> "vi", "isole vergini americane" -> "vi", "virgin islands, u.s." -> "vi",  "iles vierges des etats-unis" -> "vi", "vir" -> "vi", "isv" -> "vi",
              "vn" -> "vn", "vietnam" -> "vn", "viet nam" -> "vn", "vnm" -> "vn", "vie" -> "vn",
              "vu" -> "vu", "vanuatu" -> "vu", "ripablik blong vanuatu" -> "vu", "vut" -> "vu", "van" -> "vu",
              "wf" -> "wf", "wallis und futuna" -> "wf", "wallis e futuna" -> "wf", "wallis and futuna" -> "wf", "wallis-et-futuna" -> "wf", "wlf" -> "wf",
              "ws" -> "ws", "samoa" -> "ws", "wsm" -> "ws",  "sam" -> "ws",
              "ye" -> "ye", "jemen" -> "ye", "yemen" -> "ye", "yem" -> "ye",
              "yt" -> "yt", "mayotte" -> "yt", "myt" -> "yt",
              "za" -> "za", "südafrika" -> "za", "sudafrica" -> "za", "south africa" -> "za", "afrique du sud" -> "za", "afrika-borwa" -> "za", "zaf" -> "za", "rsa" -> "za", "sudafrika" -> "za",
              "zm" -> "zm", "sambia" -> "zm", "zambia" -> "zm", "zambie" -> "zm", "zmb" -> "zm",  "zam" -> "zm",
              "zw" -> "zw", "simbabwe" -> "zw", "zimbabwe" -> "zw", "zwe" -> "zw",  "zim" -> "zw",
              "xk" -> "xk", "kosovo" -> "xk", "republik kosovo" -> "xk", "république du kosovo" -> "xk", "le kosovo" -> "xk", "il kosovo" -> "xk", "cossovo" -> "xk", "republic of kosovo" -> "xk", "kosova" -> "xk", "kosovë" -> "xk", "republika e kosovës" -> "xk", "republika kosovo" -> "xk"
  )

  // ---
  private val countryList = countries.keys.toList
}
