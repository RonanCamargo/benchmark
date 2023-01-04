package ronancamargo.entityMapper

import ronancamargo.entityMapper.EntityMapperSyntax.EntityMapperOps

trait EntityMapperSyntax {
  implicit def entityMapperSyntax[A](from: A): EntityMapperOps[A] = new EntityMapperOps[A](from)
}

object EntityMapperSyntax {
  final class EntityMapperOps[A](private val from: A) extends AnyVal {
    def to[B](implicit entityMapper: EntityMapper[A, B]): B = entityMapper.to(from)

    def to_[B](implicit entityMapper: EntityMapper[A, B]): B = entityMapper.to(from)
  }
}
