/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.commons

/** This class represent a normalized date.
  *
  * @param year
  *   Number of the year.
  * @param month
  *   Number of the month in the year (1-12)
  * @param day
  *   Number of the day in the month (1-[28|29|30|31])
  * @param dateType
  *   Defines which components are available from the date: 0 = all, 1 =
  *   year/month, 2 = year only, 99 = nothing.
  */
case class NormalizedDate(year: Int, month: Int, day: Int, dateType: Byte)
