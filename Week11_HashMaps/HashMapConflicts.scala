//> using scala 2.13.18

// Sl. No. 20 : Handling collisions and resolving conflicts in HashMaps

import scala.collection.mutable

object HashMapConflicts {

  // Merge two grade maps; when a student appears in both, keep the higher grade
  def mergeKeepHigher(a: mutable.HashMap[String, Int],
                      b: mutable.HashMap[String, Int]): mutable.HashMap[String, Int] = {
    val merged = mutable.HashMap[String, Int]() ++= a
    for ((name, grade) <- b) {
      merged.get(name) match {
        case Some(existing) => merged(name) = math.max(existing, grade)
        case None           => merged(name) = grade
      }
    }
    merged
  }

  // Chaining for a single key: every grade is kept in a list instead of being overwritten
  def addWithChaining(map: mutable.HashMap[String, List[Int]], name: String, grade: Int): Unit =
    map(name) = map.getOrElse(name, List.empty[Int]) :+ grade

  // A small hash table that resolves bucket collisions by chaining (each bucket is a List)
  class ChainedHashTable(capacity: Int) {
    private val buckets = Array.fill(capacity)(List.empty[(String, Int)])

    def bucketOf(key: String): Int = math.abs(key.hashCode % capacity)

    def put(key: String, value: Int): Unit = {
      val b = bucketOf(key)
      buckets(b) = buckets(b) :+ (key -> value)
    }

    def get(key: String): List[Int] =
      buckets(bucketOf(key)).collect { case (k, v) if k == key => v }

    def show(): Unit =
      for (i <- buckets.indices) {
        val chain = if (buckets(i).isEmpty) "empty" else buckets(i).map { case (k, v) => s"($k, $v)" }.mkString(" -> ")
        println(s"  bucket $i : $chain")
      }
  }

  def main(args: Array[String]): Unit = {
    // Step 1: Create a HashMap of student grades and insert a duplicate key
    val grades = mutable.HashMap[String, Int]()
    grades += ("Arun" -> 78)
    grades += ("Divya" -> 85)
    grades += ("Kartik" -> 91)
    grades += ("Meena" -> 67)
    println("Step 1: Student grades HashMap")
    println(s"Before duplicate insert : $grades")
    grades += ("Arun" -> 88) // duplicate key with a different grade
    println("Inserted duplicate key (\"Arun\" -> 88)")
    println(s"After duplicate insert  : $grades")
    println(s"Size remains ${grades.size} because the duplicate key overwrote the old value")

    // Step 2: Merge two HashMaps, resolving conflicts by taking the higher grade
    val sectionB = mutable.HashMap("Divya" -> 79, "Kartik" -> 95, "Rahul" -> 72, "Meena" -> 81)
    println("\nStep 2: Merging two HashMaps (conflict -> higher grade wins)")
    println(s"Section A : $grades")
    println(s"Section B : $sectionB")
    val merged = mergeKeepHigher(grades, sectionB)
    for (name <- merged.keys.toList.sorted) println(f"  $name%-7s -> ${merged(name)}")

    // Step 3: Chaining - keep every grade of a student in a list
    val history = mutable.HashMap[String, List[Int]]()
    val attempts = List("Arun" -> 78, "Divya" -> 85, "Arun" -> 88, "Kartik" -> 91, "Divya" -> 79, "Arun" -> 93)
    for ((name, grade) <- attempts) addWithChaining(history, name, grade)
    println("\nStep 3: Chaining multiple values for a single key")
    for (name <- history.keys.toList.sorted) println(f"  $name%-7s -> ${history(name)}")

    // Step 4: Bucket-level collision handling with a chained hash table
    val table = new ChainedHashTable(capacity = 4)
    for ((name, grade) <- attempts ++ List("Meena" -> 67, "Rahul" -> 72)) table.put(name, grade)
    println("\nStep 4: Chained hash table with 4 buckets (collisions share a bucket)")
    table.show()
    println(s"table.get(\"Arun\") = ${table.get("Arun")}")
  }
}
