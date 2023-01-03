package ronancamargo.error
import shapeless._

object implicits {
  implicit final class DarumaErrorWithCauseOps(private val throwable: Throwable) extends AnyVal {

    type CustomErrorWithCause   = String :: Throwable :: HNil
    type CustomErrorWithMessage = String :: HNil

    def errorTo[E <: CustomError](implicit
        gen: Generic.Aux[E, CustomErrorWithCause]
    ): E =
      gen.from(throwable.getMessage :: throwable.getCause :: HNil)
  }
}
