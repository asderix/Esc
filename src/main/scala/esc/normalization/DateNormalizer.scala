/**
  * author:   Ronny Fuchs, info@asderix.com
  * licence:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.normalization

import esc.configuration._
import esc.commons._
import scala.collection.mutable
import java.time._
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
  * This class provide the most needed method for normalizing dates.
  */
class DateNormalizer() {
  private val MMMMdyyyy = raw"[a-zA-Z]+ {1}\d{1},{1} {1}\d{4}".r

    def normalizeDate(dateString : String) : NormalizedDate = {
      dateString match {
        case MMMMdyyyy(_*) =>
          try {
            val fromCustomPattern = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MMMM d, yyyy"))
            return NormalizedDate(fromCustomPattern.year, fromCustomPattern.month, fromCustomPattern.day)
          } catch {
            case _: Throwable => 
          }
      }

      

        return NormalizedDate(1983, 6, 28, 0)
    }
}