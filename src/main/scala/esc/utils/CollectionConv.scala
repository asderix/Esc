/**
  * author:   Ronny Fuchs, info@asderix.com
  * licencs:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.utils

import esc.commons.FinderMatch
import scala.collection.JavaConverters._
import java.util.{List => JavaList}
import java.util.{Map => JavaMap}

/**
  * Object with basic functionalities to convert Scala collections
  * to Java collections and vice versa.
  */ 
object CollectionConv {

  /**
    * Converts a java.collection.List<String> to a Scala List.
    *
     * @param  jla  Java List to convert.
     * @return Scala List.
    */
  def toScalaList(jla : JavaList[String]) : List[String] = {
      jla.asScala.toList
  }

  /**
    * Return an emtpy Scala List[String]
    *
    * @return Scala List.
    */
  def emptyScalaStringList() : List[String] = {
      List.empty[String]
  }

  /**
    * Converts a Scala FinderMatch List to a Java List.
    *
     * @param  sl  Scala List to convert.
     * @return Java List.
    */
  def toJavaList(sl : List[FinderMatch]) : JavaList[FinderMatch] = {
      sl.asJava
  }
}
