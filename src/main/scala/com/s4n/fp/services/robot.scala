package com.s4n.fp.services

import com.s4n.fp.model.Movimientos.Movimiento
import com.s4n.fp.model._

trait RobotService {
  def mover(r:Robot, m:Movimiento): Robot
}

trait RobotServiceImpl extends RobotService {
  object posicionService extends PosicionServiceImpl

  override def mover(r:Robot, m:Movimiento): Robot = {
    val nuevaPosicion = posicionService.cambiar(r.posicion, m)
    r.copy(posicion = nuevaPosicion)
  }
}


