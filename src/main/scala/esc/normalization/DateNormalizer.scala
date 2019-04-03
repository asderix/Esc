/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.normalization

import esc.configuration._
import esc.commons._
import esc.utils._
import scala.collection.mutable
import java.time._
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
  * This class provides the most needed method for normalizing dates.
  */
class DateNormalizer() {
  private val yyyyMMdds = raw"[0-9]{8}".r
  private val mMMMdyyyy = raw"[a-zA-Z]+ {1}\d{1,2},{1} {1}\d{4}".r
  private val ddMMyyyy = raw"[0-9]{2}.[0-9]{2}.[0-9]{4}".r
  private val dMMyyyy = raw"[0-9]{1}.[0-9]{2}.[0-9]{4}".r
  private val ddMyyyy = raw"[0-9]{2}.[0-9]{1}.[0-9]{4}".r
  private val dMyyyy = raw"[0-9]{1}.[0-9]{1}.[0-9]{4}".r
  private val yyyyMMdd = raw"[0-9]{4}.[0-9]{2}.[0-9]{2}".r
  private val yyyyMMd = raw"[0-9]{4}.[0-9]{2}.[0-9]{1}".r
  private val yyyyMd = raw"[0-9]{4}.[0-9]{1}.[0-9]{1}".r
  private val yyyyMdd = raw"[0-9]{4}.[0-9]{1}.[0-9]{2}".r
  private val yyyyMM = raw"[0-9]{4}.[0-9]{2}".r
  private val yyyyM = raw"[0-9]{4}.[0-9]{1}".r
  private val yyyy = raw"[0-9]{4}".r

  /**
    * Returns a NormalizedDate object for a given string.
    * The following date formats are supported:
    * - yyyyMMdd = 20191231
    * - MMMM d, yyyy = May 1, 2019
    * - dd.MM.yyyy/d.MM.yyyy/dd.M.yyyy/d.M.yyyy = 31.12.2019
    * - yyyy.MM.dd/yyyy.MM.d/yyyy.M.d/yyyy.M.dd = 2019.12.31
    * - yyyy.MM/yyyy.M = 2019.12
    * - yyyy = 2019
    * All variants with dots (.) are also supported with hyphen (-)
    * and slash (/) as seperator.
    * @param dateString The string representing the date
    * @return
    */
  def normalizeDate(dateString : String) : NormalizedDate = {
    dateString match {
      case dateString if BasicFunctions.isNullOrEmpty(dateString) => return NormalizedDate(0, 0, 0, 99)
      case _ => 
    }
    var mutDateString = dateString.replace("-", ".").replace("/", ".")
    mutDateString match {
      case yyyyMMdds(_*) =>
        try {                        
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.BASIC_ISO_DATE)
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)            
        }
      case mMMMdyyyy(_*) =>
        try {
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH))
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)
        }
      case ddMMyyyy(_*) =>
        try {
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)
        }
      case dMMyyyy(_*) =>
        try {
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.ofPattern("d.MM.yyyy"))
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)
        }
      case ddMyyyy(_*) =>
        try {
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.ofPattern("dd.M.yyyy"))
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)
        }
      case dMyyyy(_*) =>
        try {
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.ofPattern("d.M.yyyy"))
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)
        }
      case yyyyMMdd(_*) =>
        try {
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.ENGLISH))
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)
        }
      case yyyyMMd(_*) =>
        try {
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.ofPattern("yyyy.MM.d", Locale.ENGLISH))
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)
        }
      case yyyyMdd(_*) =>
        try {
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.ofPattern("yyyy.M.dd", Locale.ENGLISH))
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)
        }
      case yyyyMd(_*) =>
        try {
          val fromCustomPattern = LocalDate.parse(mutDateString, DateTimeFormatter.ofPattern("yyyy.M.d", Locale.ENGLISH))
          return NormalizedDate(fromCustomPattern.getYear(), fromCustomPattern.getMonth().getValue(), fromCustomPattern.getDayOfMonth(), 0)
        } catch {
          case _: Throwable => return NormalizedDate(0, 0, 0, 99)
        }
      case yyyyMM(_*) =>
        val na = mutDateString.split("\\.")
        na(1).toInt match {
          case x if (x < 13 && x > 0) => return NormalizedDate(na(0).toInt, x, 0, 1)
          case _ => return NormalizedDate(0, 0, 0, 99)
        }
      case yyyyM(_*) =>
        val na = mutDateString.split("\\.")
        na(1).toInt match {
          case x if (x < 13 && x > 0) => return NormalizedDate(na(0).toInt, x, 0, 1)
          case _ => return NormalizedDate(0, 0, 0, 99)
        }
      case yyyy(_*) =>          
        return NormalizedDate(mutDateString.toInt, 0, 0, 2)        
      case _ => return NormalizedDate(0, 0, 0, 99)
    }        
  }
}