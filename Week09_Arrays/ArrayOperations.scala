//> using scala 2.13.18 

// Sl. No. 18 : Arrays in Scala program

object ArrayOperations {

  // Function that takes an integer array and returns the sum of its elements
  def sumOfElements(numbers: Array[Int]): Int = {
    var total = 0
    for (n <- numbers) total += n
    total
  }

  // Function that concatenates all elements of a string array, separated by commas
  def concatenateWithCommas(items: Array[String]): String = items.mkString(", ")

  def main(args: Array[String]): Unit = {
    // Step 1: Create an array of integers
    val numbers: Array[Int] = Array(2, 5, 9, 14, 20)
    println("Step 1: Integer array created")
    println(s"numbers = Array(${numbers.mkString(", ")})")
    println(s"Length of the array = ${numbers.length}")

    // Step 2: Sum of the elements using the user-defined function
    println("\nStep 2: Sum of the integer array")
    println(s"sumOfElements(numbers) = ${sumOfElements(numbers)}")
    println(s"Verification using numbers.sum = ${numbers.sum}")

    // Step 3: Create an array of strings with five fruit names
    val fruits: Array[String] = Array("Apple", "Banana", "Mango", "Orange", "Grapes")
    println("\nStep 3: String array of fruits created")
    println(s"fruits = Array(${fruits.map(f => "\"" + f + "\"").mkString(", ")})")

    // Step 4: Concatenate the fruit names separated by commas
    println("\nStep 4: Concatenated fruit names")
    println(s"concatenateWithCommas(fruits) = ${concatenateWithCommas(fruits)}")

    // Step 5: Iterate over the integer array and print each element
    println("\nStep 5: Iterating over the integer array")
    for (i <- numbers.indices) {
      println(s"Element at index $i = ${numbers(i)}")
    }
  }
}
