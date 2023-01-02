package ronancamargo

import org.openjdk.jmh.annotations.Benchmark

import shapeless._
import org.openjdk.jmh.annotations._

abstract class CustomError(message: String, throwable: Option[Throwable] = None)
    extends RuntimeException(message, throwable.orNull)
case class MyError(message: String, cause: Throwable) extends CustomError(message, Some(cause))

object Implicits {
  implicit final class DarumaErrorWithCauseOps(private val throwable: Throwable) extends AnyVal {

    type CustomErrorWithCause   = String :: Throwable :: HNil
    type CustomErrorWithMessage = String :: HNil

    def errorTo[E <: CustomError](implicit
        gen: Lazy[Generic.Aux[E, CustomErrorWithCause]]
    ): E =
      gen.value.from(throwable.getMessage :: throwable.getCause :: HNil)
  }
}

@State(Scope.Thread)
class Main {

  val someError = new RuntimeException("RIP")

  @Benchmark
  def normalError(): Unit = {
    MyError(someError.getMessage(), someError)
  }

  @Benchmark
  def extensionError(): Unit = {
    import Implicits._
    someError.errorTo[MyError]
  }
}
