// Sl. No. 25 : Precise fruit price statistics with Spire, data manipulation with Saddle, trends with Vegas

import org.saddle._
import org.saddle.io._
import spire.math.Rational
import vegas._
import java.io.{File, PrintWriter}

object FruitPriceStatistics extends App {

  // Exact mean and population variance using Spire rationals (no floating point rounding)
  def exactMean(xs: Seq[Rational]): Rational = xs.reduce(_ + _) / Rational(xs.size)

  def exactVariance(xs: Seq[Rational]): Rational = {
    val m = exactMean(xs)
    xs.map(x => (x - m) * (x - m)).reduce(_ + _) / Rational(xs.size)
  }

  // Step 1: Load the dataset with Saddle
  val raw: Frame[Int, Int, String] = CsvParser.parse(CsvFile("../data/fruit_prices.csv"))
  val data: Frame[Int, String, String] = raw.withColIndex(0).resetRowIndex
  println("Step 1: Dataset loaded into a Saddle Frame")
  println(s"Shape: ${data.numRows} rows x ${data.numCols} columns")

  // Step 2: Reshape into a Date x Fruit price matrix with Saddle
  val fruitNames = data.firstCol("Fruit").toVec.toSeq.distinct.sorted
  val priceMatrix: Frame[String, String, Double] = Frame(fruitNames.map { fruit =>
    val rows = data.rfilter(r => r.first("Fruit").get == fruit)
    val prices: Vec[Double] = rows.firstCol("Price").toVec.map(_.toDouble)
    fruit -> Series(prices, Index(rows.firstCol("Date").toVec.toSeq: _*))
  }: _*)
  println("\nStep 2: Price matrix (Date x Fruit, Rs per kg) built with Saddle")
  priceMatrix.print(nrows = 10)

  // Step 3: Mean and variance per fruit with Spire Rational arithmetic
  println("\nStep 3: Exact statistics with Spire (population variance)")
  println(f"${"Fruit"}%-7s ${"Mean (exact Rational)"}%-22s ${"Mean"}%9s ${"Variance"}%10s ${"Std Dev"}%8s")
  val stats = fruitNames.map { fruit =>
    val prices = data.rfilter(r => r.first("Fruit").get == fruit).firstCol("Price").toVec.toSeq.map(p => Rational(BigDecimal(p)))
    val mean = exactMean(prices)
    val variance = exactVariance(prices)
    println(f"$fruit%-7s ${mean.toString}%-22s ${mean.toDouble}%9.3f ${variance.toDouble}%10.3f ${math.sqrt(variance.toDouble)}%8.3f")
    (fruit, mean, variance)
  }

  // Step 4: Precision check - Spire exact result vs Double arithmetic
  val appleDoubles = priceMatrix.firstCol("Apple").toVec.toSeq
  val doubleMean = appleDoubles.sum / appleDoubles.size
  println("\nStep 4: Precision check for Apple prices")
  println(s"Spire Rational mean : ${stats.head._2}  =  ${stats.head._2.toBigDecimal(20, java.math.RoundingMode.HALF_UP)}")
  println(s"Double mean         : $doubleMean")
  println(s"Spire variance      : ${stats.head._3}")

  // Step 5: Visualize price trends over time with Vegas (one line per fruit)
  val trendRecords = for {
    i <- 0 until data.numRows
    row = data.rowAt(i)
  } yield Map("Date" -> row.first("Date").get, "Fruit" -> row.first("Fruit").get, "Price" -> row.first("Price").get.toDouble)

  val trendPlot = Vegas("Fruit Price Trends", width = 700.0, height = 380.0)
    .withData(trendRecords)
    .encodeX("Date", Temporal, timeUnit = TimeUnit.Yearmonth, title = "Month")
    .encodeY("Price", Quant, title = "Price (Rs per kg)")
    .encodeColor("Fruit", Nominal)
    .mark(Line)
    .configMark(strokeWidth = 3.0)

  // Step 6: Visualize mean price with variance (from Spire) as a bar chart
  val statRecords = stats.map { case (f, m, v) => Map("Fruit" -> f, "MeanPrice" -> m.toDouble, "Variance" -> v.toDouble) }
  val meanPlot = Vegas("Mean Price per Fruit", width = 500.0, height = 320.0)
    .withData(statRecords)
    .encodeX("Fruit", Nominal)
    .encodeY("MeanPrice", Quant, title = "Mean Price (Rs per kg)")
    .encodeColor("Variance", Quant)
    .mark(Bar)

  new File("output").mkdirs()
  for ((name, plot) <- Seq("fruit_price_trends.html" -> trendPlot, "fruit_mean_price.html" -> meanPlot)) {
    val out = new PrintWriter(s"output/$name")
    out.write(plot.html.pageHTML())
    out.close()
  }
  println("\nStep 5-6: Vegas charts saved to output/fruit_price_trends.html and output/fruit_mean_price.html")
}
