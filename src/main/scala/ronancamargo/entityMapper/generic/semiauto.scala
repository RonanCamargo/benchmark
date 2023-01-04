package ronancamargo.entityMapper.generic

import ronancamargo.entityMapper.EntityMapper
import shapeless.Lazy

import scala.annotation.implicitNotFound

object semiauto {
  def deriveEntityMapper[A, B](implicit
      @implicitNotFound(
        "EntityMapper semiauto derivation failure. ${B} must be a subset of ${A}. Check field names and types."
      ) entityMapper: Lazy[DerivedEntityMapper[A, B]]
  ): EntityMapper[A, B] =
    entityMapper.value
}
