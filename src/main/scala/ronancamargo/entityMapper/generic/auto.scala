package ronancamargo.entityMapper.generic

import cats.Functor
import ronancamargo.entityMapper.EntityMapper
import shapeless.Lazy

import scala.annotation.implicitNotFound

object auto {
  implicit def autoDeriveEntityMapper[A, B](implicit
      @implicitNotFound(
        "EntityMapper auto derivation failure. ${B} must be a subset of ${A}. Check field names and types."
      )
      entityMapper: Lazy[DerivedEntityMapper[A, B]]
  ): EntityMapper[A, B] =
    entityMapper.value

  implicit def autoLiftF[F[_] : Functor, A, B](implicit entityMapper: EntityMapper[A, B]): EntityMapper[F[A], F[B]] =
    entityMapper.liftF[F]

}
