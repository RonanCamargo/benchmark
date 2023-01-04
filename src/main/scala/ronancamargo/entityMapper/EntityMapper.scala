package ronancamargo.entityMapper

import cats.Functor

/** Typeclass to transform types
  *
  * @tparam A
  *   From type
  * @tparam B
  *   To type
  */
trait EntityMapper[A, B] { self =>
  def to(from: A): B

  def map[C](f: B => C): EntityMapper[A, C] =
    EntityMapper.instance[A, C] { a: A => f(self.to(a)) }

  def andThen[C](mapper: EntityMapper[B, C]): EntityMapper[A, C] =
    EntityMapper.instance { a: A => mapper.to(self.to(a)) }

  def liftF[F[_]](implicit F: Functor[F]): EntityMapper[F[A], F[B]] = {
    EntityMapper.instance[F[A], F[B]] { fa: F[A] => F.map(fa)(self.to) }
  }
}

object EntityMapper {

  /** Alternative constructor for an [[EntityMapper]]
    * @param f
    *   Function to map between entities
    * @tparam A
    *   From type
    * @tparam B
    *   To type
    */
  def instance[A, B](f: A => B): EntityMapper[A, B] = (from: A) => f(from)
}
