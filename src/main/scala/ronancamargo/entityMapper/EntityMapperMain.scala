package ronancamargo.entityMapper

import org.openjdk.jmh.annotations._
import ronancamargo.entityMapper.data.{Name, Player}
import ronancamargo.entityMapper.generic.semiauto.deriveEntityMapper

@State(Scope.Thread)
class EntityMapperMain extends EntityMapperSyntax {

  private val player: Player                     = Player("El Diego", 10)
  private val mapper: EntityMapper[Player, Name] = EntityMapper.instance[Player, Name](p => Name(p.name))

  @Benchmark
  def auto(): Unit = {
    import ronancamargo.entityMapper.generic.auto._
    player.to[Name]
  }
  @Benchmark
  def semiauto(): Unit = {
    player.to[Name](deriveEntityMapper)
  }
  @Benchmark
  def manualParam(): Unit = {
    player.to[Name](mapper)
  }

  @Benchmark
  def manualMap(): Unit = {
    Name(player.name)
  }

}
