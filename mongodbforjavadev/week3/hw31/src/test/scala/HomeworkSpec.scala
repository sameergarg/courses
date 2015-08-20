import Homework._
import org.scalatest.{Matchers, FlatSpec}
import reactivemongo.bson.{BSONValue, BSONDouble, BSONDocument, BSONArray}

/**
 *
 */
class HomeworkSpec extends FlatSpec with Matchers {

  val scores: BSONArray = BSONArray(
    BSONDocument("type" -> "homework", "score" -> BSONDouble(2.0)),
    BSONDocument("type" -> "homework", "score" -> BSONDouble(9.0)),
    BSONDocument("type" -> "homework", "score" -> BSONDouble(7.0)),
    BSONDocument("type" -> "exam", "score" -> BSONDouble(5.0))
  )
  
  "BSONArray of scores" should "be split by type" in {
    val scoresByType: (Stream[BSONValue], Stream[BSONValue]) = partitionScoresByType(scores,"homework")
    scoresByType shouldBe
      (
        Stream(
        BSONDocument("type" -> "homework", "score" -> BSONDouble(2.0)),
        BSONDocument("type" -> "homework", "score" -> BSONDouble(9.0)),
        BSONDocument("type" -> "homework", "score" -> BSONDouble(7.0))
        ),
        Stream(
          BSONDocument("type" -> "exam", "score" -> BSONDouble(5.0))
        )
      )
  }

  it should "be sorted by score ascending" in {


    val sorted: Stream[BSONValue] = sortScores(partitionScoresByType(scores, "homework")._1)

    sorted.toList.foreach(doc => println(s"Left: ${BSONDocument pretty doc.asInstanceOf[BSONDocument]}"))

    sorted shouldBe Stream(
      BSONDocument("type" -> "homework", "score" -> BSONDouble(2.0)),
      BSONDocument("type" -> "homework", "score" -> BSONDouble(7.0)),
      BSONDocument("type" -> "homework", "score" -> BSONDouble(9.0))
    )
  }

  it should "remove the lowest score" in {
    sortScores(partitionScoresByType(scores, "homework")._1).tail.toList shouldBe Stream(
      BSONDocument("type" -> "homework", "score" -> BSONDouble(7.0)),
      BSONDocument("type" -> "homework", "score" -> BSONDouble(9.0))
    )
  }

}
