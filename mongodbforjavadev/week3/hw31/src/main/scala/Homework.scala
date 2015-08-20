import akka.actor.Status.Success
import play.api.libs.iteratee.{Iteratee, Enumerator}
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONValue, BSONArray, BSONDocument}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 *
 */
object Homework extends App {

  def connect() {
    // gets an instance of the driver
    // (creates an actor system)
    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))

    // Gets a reference to the database "plugin"
    val db = connection("school")

    // Gets a reference to the collection "acoll"
    // By default, you get a BSONCollection.
    val collection = db("students")

    removeLowestHomeworkScore(collection)
  }

  def removeLowestHomeworkScore(collection: BSONCollection) = {
    val query = BSONDocument()
    val projection = BSONDocument("_id" -> 1, "scores" -> 1)

    val studentsEnumerator: Enumerator[BSONDocument]  = collection.find(query, projection).cursor[BSONDocument].enumerate()

    val processStudents: Iteratee[BSONDocument, Unit] = Iteratee.foreach{ student =>
      val studentId = student.getAs[Double]("_id").get
      val scores = student.getAs[BSONArray]("scores").get

      val selector = BSONDocument("_id" -> studentId)
      val modifier:BSONDocument = BSONDocument(
        "$set" -> BSONDocument(
          "scores" -> removeLowestScoreFor(scores,"homework")
        )
      )
      collection.update(selector, modifier)
    }
    studentsEnumerator run processStudents

  }
  
  def removeLowestScoreFor(scores: BSONArray, scoreType: String) = {
    val leftScoreTypeRightOthers = partitionScoresByType(scores, scoreType)
    leftScoreTypeRightOthers._2 ++ sortScores(leftScoreTypeRightOthers._1).drop(1)
  }
  
  def partitionScoresByType:(BSONArray, String) => (Stream[BSONValue],Stream[BSONValue]) =
    (scores,scoreType) => scores.values
                                .partition(score =>
                                    score.seeAsOpt[BSONDocument]
                                          .get
                                          .getAs[String]("type")
                                          .get == "homework")
                                          



  def sortScores(scores: Stream[BSONValue]): Stream[BSONValue] = {
    scores.sortWith((score1, score2) => score1.seeAsOpt[BSONDocument].get.getAs[Double]("score").get
      <= (score2.seeAsOpt[BSONDocument].get.getAs[Double]("score").get))
  }
  connect()

}


