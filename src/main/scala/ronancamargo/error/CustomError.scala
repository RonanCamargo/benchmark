package ronancamargo.error

abstract class CustomError(message: String, throwable: Option[Throwable] = None)
    extends RuntimeException(message, throwable.orNull)

final case class MyError(message: String, cause: Throwable) extends CustomError(message, Some(cause))
