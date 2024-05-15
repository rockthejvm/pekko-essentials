package playground

import org.apache.pekko.actor.typed.ActorSystem
import org.apache.pekko.actor.typed.scaladsl.Behaviors

import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._

object Playground {

  def main(args: Array[String]): Unit = {
    val root = ActorSystem(
      Behaviors.receive[String] { (context, message) =>
        context.log.info(s"Just received: $message")
        Behaviors.same
      },
      "DummySystem")

    root ! "Hey, Akka!"

    implicit val ec: ExecutionContext = root.executionContext
    root.scheduler.scheduleOnce(3.seconds, () => root.terminate())
  }
}

object Jeg {
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.util._
  def main(args: Array[String]): Unit = {
    val promise: Promise[Int] = Promise[Int]()
    val future: Future[Int] = promise.future

    // thread 1 - "consumer"
      future.onComplete{
        case Success(r) => println("[consumer] I've received " + r)
        case Failure(e) => println("[consumer] failed due to " + e)
      }

    // thread 2 - "producer"
    Future {
      println("[producer] long computation...")
      Thread.sleep(1000)
      //    promise.success(42)
      promise.failure( new ArrayIndexOutOfBoundsException)
      println("[producer] done")
    }

    Thread.sleep(10000)
  }
}