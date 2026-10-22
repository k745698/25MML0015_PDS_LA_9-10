//> using scala 2.13.18
//> using dep org.scalanlp::breeze:2.1.0
//> using dep org.plotly-scala::plotly-render:0.8.5

// Sl. No. 24 : Revenue per fruit with Breeze and a bar chart with Plotly-Scala

import breeze.linalg.{DenseVector, sum}
import plotly._
import plotly.element._
import plotly.layout._
import scala.io.Source

object FruitRevenue {

  case class Sale(date: String, fruit: String, price: Double, quantity: Double)

  def main(args: Array[String]): Unit = {
    // Step 1: Load the dataset
    val source = Source.fromFile("data/fruit_prices.csv")
    val sales = source.getLines().drop(1).map(_.split(",")).map { c =>
      Sale(c(0), c(1), c(2).toDouble, c(3).toDouble)
    }.toList
    source.close()
    println(s"Step 1: Loaded ${sales.size} records from data/fruit_prices.csv")
    sales.take(4).foreach(s => println(f"  ${s.date}  ${s.fruit}%-6s  price=${s.price}%7.2f  qty=${s.quantity}%5.0f"))

    // Step 2: Breeze vectors of price and quantity for the whole dataset
    val prices = DenseVector(sales.map(_.price): _*)
    val quantities = DenseVector(sales.map(_.quantity): _*)
    val revenueVec = prices *:* quantities // element-wise multiplication
    println("\nStep 2: Breeze element-wise multiplication (price *:* quantity)")
    println(s"  prices     (first 4) = ${prices(0 to 3)}")
    println(s"  quantities (first 4) = ${quantities(0 to 3)}")
    println(s"  revenue    (first 4) = ${revenueVec(0 to 3)}")

    // Step 3: Total revenue for each fruit type
    val fruits = sales.map(_.fruit).distinct.sorted
    val revenueByFruit: Seq[(String, Double)] = fruits.map { fruit =>
      val rows = sales.filter(_.fruit == fruit)
      val p = DenseVector(rows.map(_.price): _*)
      val q = DenseVector(rows.map(_.quantity): _*)
      fruit -> sum(p *:* q)
    }
    println("\nStep 3: Total revenue by fruit (Rs)")
    revenueByFruit.foreach { case (f, r) => println(f"  $f%-7s ${r}%,12.2f") }
    println(f"  ${"TOTAL"}%-7s ${sum(revenueVec)}%,12.2f")

    // Step 4: Bar chart of revenue per fruit with Plotly-Scala
    val bar = Bar(revenueByFruit.map(_._1), revenueByFruit.map(_._2))
      .withName("Revenue")
      .withText(revenueByFruit.map { case (_, r) => f"Rs $r%,.0f" })
      .withTextposition(BarTextPosition.Outside)
      .withMarker(Marker().withColor(Color.RGBA(56, 86, 35, 0.85)))
    val layout = Layout()
      .withTitle("Total Revenue by Fruit Type (Jan - Oct 2026)")
      .withXaxis(Axis().withTitle("Fruit"))
      .withYaxis(Axis().withTitle("Revenue (Rs)"))
      .withWidth(800)
      .withHeight(480)
    new java.io.File("output").mkdirs()
    Plotly.plot("output/fruit_revenue_bar.html", Seq(bar), layout,
      openInBrowser = false, addSuffixIfExists = false)
    println("\nStep 4: Plotly bar chart saved to output/fruit_revenue_bar.html")
  }
}
