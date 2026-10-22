// Sl. No. 23 : Fruit price analysis with Saddle (data manipulation) and Vegas (visualization)

import org.saddle._
import org.saddle.io._
import vegas._
import java.io.{File, PrintWriter}

object ApplePriceAnalysis extends App {

  // Step 1: Load the fruit price dataset from CSV using Saddle
  val csv = CsvFile("../data/fruit_prices.csv")
  val raw: Frame[Int, Int, String] = CsvParser.parse(csv)
  val fruits: Frame[Int, String, String] = raw.withColIndex(0).resetRowIndex
  println("Step 1: Fruit price dataset loaded with Saddle")
  println(s"Rows: ${fruits.numRows}, Columns: ${fruits.numCols} -> ${fruits.colIx.toSeq.mkString(", ")}")
  fruits.print(nrows = 8)

  // Step 2: Filter the frame to keep only Apple records
  val apples = fruits.rfilter(row => row.first("Fruit").get == "Apple").resetRowIndex
  println("\nStep 2: Records filtered for Apple")
  apples.print(nrows = 10)

  // Step 3: Convert the Price column to Double and calculate statistics
  val applePrices: Series[String, Double] =
    Series(apples.firstCol("Price").mapValues(_.toDouble).toVec, apples.firstCol("Date").toVec.map(identity))
  println("\nStep 3: Apple price series (Date -> Price in Rs/kg)")
  println(applePrices)
  println(f"Average apple price : Rs ${applePrices.mean}%.2f per kg")
  println(f"Minimum apple price : Rs ${applePrices.min.get}%.2f on ${applePrices.minKey.get}")
  println(f"Maximum apple price : Rs ${applePrices.max.get}%.2f on ${applePrices.maxKey.get}")

  // Step 4: Visualize the apple price trend over time using Vegas
  val records = applePrices.toSeq.map { case (date, price) => Map("Date" -> date, "Price" -> price) }
  val plot = Vegas("Apple Price Trend (Jan - Oct 2026)", width = 700.0, height = 380.0)
    .withData(records)
    .encodeX("Date", Temporal, timeUnit = TimeUnit.Yearmonth, title = "Month")
    .encodeY("Price", Quant, title = "Apple Price (Rs per kg)", scale = Scale(zero = false))
    .mark(Line)
    .configMark(strokeWidth = 3.0)

  new File("output").mkdirs()
  val out = new PrintWriter("output/apple_price_trend.html")
  out.write(plot.html.pageHTML())
  out.close()
  println("\nStep 4: Vegas line chart saved to output/apple_price_trend.html")
}
