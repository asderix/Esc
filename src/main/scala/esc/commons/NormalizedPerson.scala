package esc.commons

case class NormalizedPerson(names : Vector[NormalizedName], countries : Vector[String],
                            dobs : Vector[NormalizedDate])
