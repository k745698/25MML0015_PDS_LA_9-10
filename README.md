# PDS Scala Lab – Programming for Data Science Lab (MACSE502)

Scala exercise programs for Weeks 9–14 (Sl. No. 18–25) of the 2026 Fall semester lab record.
**Student:** K V Kartik (26MML0015) · **Faculty:** Dr M Rajasekhara Babu, School of CSE, VIT Vellore

| Sl. No. | Week | Topic | Folder | How it runs |
|---|---|---|---|---|
| 18 | 09 | Arrays in Scala | [Week09_Arrays](Week09_Arrays) | scala-cli (Scala 2.13) |
| 19 | 10 | Lists, sets and tuples | [Week10_Lists_Sets_Tuples](Week10_Lists_Sets_Tuples) | scala-cli (Scala 2.13) |
| 20 | 11 | HashMap conflicts and collision chaining | [Week11_HashMaps](Week11_HashMaps) | scala-cli (Scala 2.13) |
| 21 | 12 | Functional combinators | [Week12_Functional_Combinators](Week12_Functional_Combinators) | scala-cli (Scala 2.13) |
| 22 | 13 | Case classes | [Week13_Case_Classes](Week13_Case_Classes) | scala-cli (Scala 2.13) |
| 23 | 13 | Saddle + Vegas: apple price analysis | [Week13_Saddle_Vegas](Week13_Saddle_Vegas) | sbt (Scala 2.11, Java 8) |
| 24 | 14 | Breeze + Plotly-Scala: revenue per fruit | [Week14_Breeze_Plotly](Week14_Breeze_Plotly) | scala-cli (Scala 2.13) |
| 25 | 14 | Spire + Saddle + Vegas: precise price statistics | [Week14_Spire_Saddle_Vegas](Week14_Spire_Saddle_Vegas) | sbt (Scala 2.11, Java 8) |

The shared dataset is [data/fruit_prices.csv](data/fruit_prices.csv), with monthly price and quantity records for four fruits from January to October 2026.

## Running

scala-cli programs (run them from the repository root, because Sl. No. 24 reads `data/fruit_prices.csv`):

```
scala-cli run Week09_Arrays/ArrayOperations.scala
scala-cli run Week14_Breeze_Plotly/FruitRevenue.scala      # writes output/fruit_revenue_bar.html
```

Saddle and Vegas are published only for Scala 2.11, so Sl. No. 23 and 25 are sbt projects that need a Java 8 JDK:

```
cd Week13_Saddle_Vegas
sbt -java-home "$(cs java-home --jvm zulu:8)" run         # writes output/apple_price_trend.html
```

Spire is pinned to 0.14.1, because 0.15 and later pull in a cats-kernel version that conflicts with Vegas's circe dependency.

Generated charts (`output/`), screenshots and the lab record documents are not tracked in this repository.
