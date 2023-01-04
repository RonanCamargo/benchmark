package ronancamargo.entityMapper.generic

import ronancamargo.entityMapper.EntityMapper
import shapeless.ops.hlist
import shapeless.{HList, LabelledGeneric}

import scala.annotation.implicitNotFound

trait DerivedEntityMapper[A, B] extends EntityMapper[A, B]

object DerivedEntityMapper {

  implicit def mkDerivedEntityMapper[A, B, HA <: HList, HB <: HList](implicit
      genA: LabelledGeneric.Aux[A, HA],
      genB: LabelledGeneric.Aux[B, HB],
      @implicitNotFound("${B} must be a subset of ${A}. Check field names and types.")
      select: hlist.SelectAll[HA, HB]
  ): DerivedEntityMapper[A, B] = {
    new DerivedEntityMapper[A, B] {
      override def to(from: A): B = genB.from(select(genA.to(from)))
    }
  }

}
