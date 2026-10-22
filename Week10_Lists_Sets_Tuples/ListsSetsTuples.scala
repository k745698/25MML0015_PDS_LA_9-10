//> using scala 2.13.18

// Sl. No. 19 : Lists, Sets and Tuples in Scala

object ListsSetsTuples {

  // Function that returns a new list with every element squared
  def squareElements(numbers: List[Int]): List[Int] = numbers.map(n => n * n)

  // Function that returns the intersection of two sets
  def intersection[T](first: Set[T], second: Set[T]): Set[T] = first.intersect(second)

  def main(args: Array[String]): Unit = {
    // Step 1: List of the first five prime numbers
    val primes: List[Int] = List(2, 3, 5, 7, 11)
    println("Step 1: List of the first five prime numbers")
    println(s"primes = $primes")

    // Step 2: Square each element using the function
    println("\nStep 2: Squared list")
    println(s"squareElements(primes) = ${squareElements(primes)}")
    println(s"Original list is unchanged (immutable) = $primes")

    // Step 3: Set of unique characters from "hello world" (space removed)
    val text = "hello world"
    val helloChars: Set[Char] = text.filterNot(_.isWhitespace).toSet
    println("\nStep 3: Set of unique characters")
    println(s"Characters in \"$text\" = ${text.length} (including the space)")
    println(s"Unique characters = $helloChars")
    println(s"Number of unique characters = ${helloChars.size}")

    // Step 4: Intersection of two sets
    val scalaChars: Set[Char] = "scala developer".filterNot(_.isWhitespace).toSet
    println("\nStep 4: Intersection of two sets")
    println(s"Set A (\"hello world\")     = $helloChars")
    println(s"Set B (\"scala developer\") = $scalaChars")
    println(s"intersection(A, B)        = ${intersection(helloChars, scalaChars)}")

    // Step 5: Tuple with an integer, a string and a boolean
    val record: (Int, String, Boolean) = (26, "Scala", true)
    println("\nStep 5: Tuple with three elements")
    println(s"record = $record")
    println(s"record._1 (Int)     = ${record._1}")
    println(s"record._2 (String)  = ${record._2}")
    println(s"record._3 (Boolean) = ${record._3}")
  }
}
