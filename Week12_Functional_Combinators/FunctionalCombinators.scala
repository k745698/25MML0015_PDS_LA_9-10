//> using scala 2.13.18

// Sl. No. 21 : Exploring advanced functional combinators

object FunctionalCombinators {

  def main(args: Array[String]): Unit = {
    // Step 1: List of integers from 1 to 10
    val numbers: List[Int] = (1 to 10).toList
    println("Step 1: List of integers")
    println(s"numbers = $numbers")

    // Step 2: filter - keep only even numbers
    val evens = numbers.filter(n => n % 2 == 0)
    println("\nStep 2: filter (even numbers)")
    println(s"numbers.filter(_ % 2 == 0) = $evens")

    // Step 3: map - multiply each element by 3
    val tripled = numbers.map(n => n * 3)
    println("\nStep 3: map (multiply by 3)")
    println(s"numbers.map(_ * 3) = $tripled")

    // Step 4: flatMap - pair every integer with its square
    val pairs: List[(Int, Int)] = numbers.flatMap(n => List((n, n * n)))
    println("\nStep 4: flatMap (number paired with its square)")
    println(s"numbers.flatMap(n => List((n, n * n))) =\n$pairs")

    // Step 5: foldLeft - product of all elements
    val product: Long = numbers.foldLeft(1L)((acc, n) => acc * n)
    println("\nStep 5: foldLeft (product of all elements)")
    println(s"numbers.foldLeft(1L)(_ * _) = $product")

    // Step 6: Further combinators beyond the basic set
    println("\nStep 6: Additional combinators")
    println(s"scanLeft running product   = ${numbers.scanLeft(1L)(_ * _).tail}")
    println(s"partition (even, odd)      = ${numbers.partition(_ % 2 == 0)}")
    println(s"collect squares of odds    = ${numbers.collect { case n if n % 2 == 1 => n * n }}")
    println(s"reduce (sum)               = ${numbers.reduce(_ + _)}")
    println(s"chained filter-map-sum     = ${numbers.filter(_ % 2 == 0).map(_ * 3).sum}")
  }
}
