//> using scala 2.13.18

// Sl. No. 22 : Case classes in Scala

case class Person(name: String, age: Int, city: String)

object CaseClassPerson {

  // People older than 30
  def olderThan30(people: List[Person]): List[Person] = people.filter(_.age > 30)

  // Group people by their city
  def groupByCity(people: List[Person]): Map[String, List[Person]] = people.groupBy(_.city)

  // "Name is Age years old and lives in City"
  def describe(people: List[Person]): List[String] =
    people.map(p => s"${p.name} is ${p.age} years old and lives in ${p.city}")

  // Sort by age in ascending order
  def sortByAge(people: List[Person]): List[Person] = people.sortBy(_.age)

  def main(args: Array[String]): Unit = {
    // Step 1: List of Person objects
    val people = List(
      Person("Kartik", 24, "Vellore"),
      Person("Anitha", 35, "Chennai"),
      Person("Rahul", 29, "Bengaluru"),
      Person("Meera", 42, "Chennai"),
      Person("Suresh", 31, "Vellore")
    )
    println("Step 1: List of Person case class objects")
    people.foreach(println)

    // Step 2: Filter people older than 30
    println("\nStep 2: People older than 30")
    olderThan30(people).foreach(println)

    // Step 3: Group people by city
    println("\nStep 3: People grouped by city")
    for ((city, group) <- groupByCity(people).toList.sortBy(_._1))
      println(s"$city -> ${group.map(_.name).mkString(", ")}")

    // Step 4: Transform every Person into a sentence
    println("\nStep 4: Person objects transformed into strings")
    describe(people).foreach(println)

    // Step 5: Sort by age in ascending order
    println("\nStep 5: People sorted by age (ascending)")
    sortByAge(people).foreach(p => println(f"${p.name}%-7s ${p.age}%3d  ${p.city}"))

    // Case class features: structural equality and copy
    val updated = people.head.copy(age = 25)
    println(s"\ncopy(): $updated | equality check = ${people.head == Person("Kartik", 24, "Vellore")}")
  }
}
