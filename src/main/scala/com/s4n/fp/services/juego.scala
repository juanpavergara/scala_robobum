package com.s4n.fp.services

import com.s4n.fp.model._
import com.s4n.fp.model.Movimientos._
import scala.util.{Failure, Success, Try}
import scalaz._

trait JuegoService {
  type Resultado = Set[Mina]
  def agregarMinas(minas: List[Mina], terreno: Terreno): Try[Terreno]
  def ejecutarMovimientos(movimientos: List[Movimiento], robot: Robot, terreno: Terreno): Try[Resultado]
  def operar(minas: List[Mina], terreno: Terreno, movs: List[Movimiento], robot: Robot): Try[Resultado]
}

trait JuegoServiceImpl extends JuegoService with RobotServiceImpl with TerrenoServiceImpl{

  override def ejecutarMovimientos(movimientos: List[Movimiento], robot: Robot, terreno: Terreno): Try[Resultado] = {
    type Estado = (Robot, List[Movimiento], Try[Resultado])

    val identidad = State[Estado, Try[Resultado]] {
      case x:Estado => (x, x._3)
    }

    val ejecutarUnMovimiento = State[Estado,Try[Resultado]] {
      case x:Estado => {

        val robotMovido = mover(x._1, x._2.head)

        println("Robot movido: " + robotMovido)

        hayMina(terreno, robotMovido.posicion.coordenada) match {
          case Success(minaOpt)  => {
            if(minaOpt.isDefined){
              val nuevoResultado = x._3.flatMap(t => Success(t+Mina(robotMovido.posicion.coordenada)) )
              ((robotMovido, x._2.tail, nuevoResultado) , nuevoResultado  )
            }else{
              ((robotMovido,x._2.tail , x._3) , x._3)
            }
          }
          case Failure(e) => {
            println("Hubo falla: "+e.getMessage)
            ((robotMovido, x._2.tail, x._3), Failure(e))
          }
        }
      }
    }

    lazy val ejecutarTodosMovimientos: State[Estado, Try[Resultado]] = State.init.flatMap { estado =>
      if(estado._2.isEmpty) {
        identidad
      } else {
        for {
          a <- ejecutarUnMovimiento
          b <- ejecutarTodosMovimientos
        } yield(b)
      }
    }

    val r: ((Robot, List[Movimiento], Try[Resultado]), Try[Resultado]) = ejecutarTodosMovimientos(robot, movimientos, Success(Set()))
    r._2
  }

  override def agregarMinas(minas: List[Mina], terreno: Terreno): Try[Terreno] = {

    type Estado = (Try[Terreno], List[Mina])

    val terrenoTry = Success(terreno)

    def ponerUnaMinaTry(terrenoTry: Try[Terreno], mina: Mina): Try[Terreno] = {
      terrenoTry match {
        case Success(terr) => agregarMina(terr, mina.coordenada)
        case Failure(ex) => Failure(ex)
      }
    }

    val ponerUnaMina = State[Estado,Try[Terreno]] {
      case x:Estado => ((ponerUnaMinaTry(x._1, x._2.head), x._2.tail), (ponerUnaMinaTry(x._1, x._2.head)))
    }

    val identidad = State[Estado, Try[Terreno]] {
      case x:Estado => (x, x._1)
    }

    lazy val ponerTodasMinas: State[Estado, Try[Terreno]] = State.init.flatMap { estado =>
      if(estado._2.isEmpty || estado._1.isFailure) {
        identidad
      } else {
        for {
          a <- ponerUnaMina
          b <- ponerTodasMinas
        } yield(b)
      }
    }

    val a = ponerTodasMinas((terrenoTry,minas))
    a._2
  }

  override def operar(minas: List[Mina], terreno: Terreno, movs: List[Movimiento], robot: Robot): Try[Resultado] = {

    val res = for {
      t <- agregarMinas(minas, terreno)
      r <- ejecutarMovimientos(movs, robot, t)
    } yield(r)

    res
  }
}