package ronancamargo

import org.openjdk.jmh.annotations.Benchmark

import shapeless._
import org.openjdk.jmh.annotations._
import error.implicits._
import error.MyError

@State(Scope.Thread)
class Main {

  val someError = new RuntimeException("RIP")

  @Benchmark
  def normalError(): Unit = MyError(someError.getMessage(), someError)

  @Benchmark
  def extensionError(): Unit = someError.errorTo[MyError]

}
