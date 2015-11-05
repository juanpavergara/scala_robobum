package com.s4n.fp.model

import Orientacion.Orientacion
import com.s4n.fp.model.Movimientos.Movimiento
import com.s4n.fp.services.TerrenoServiceImpl
import scala.util.{Success, Try, Failure}
import scalaz.State

object Orientacion extends Enumeration {
  type Orientacion = Value
  val Norte, Sur, Este, Oeste = Value
}

object Movimientos extends Enumeration {
  type Movimiento = Value
  val I, D, A = Value
}

case class Terreno(terreno: Array[Array[Boolean]])
case class Coordenada(x:Int, y:Int)
case class Mina(coordenada:Coordenada)
case class Robot(posicion:Posicion)
case class Posicion(coordenada:Coordenada, orientacion:Orientacion)
case class Juego(robot:Robot, terreno:Terreno, minas:List[Mina], movimientos: List[Movimiento])


