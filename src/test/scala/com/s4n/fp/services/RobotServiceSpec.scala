package com.s4n.fp.services

import com.s4n.fp.model.Orientacion._
import com.s4n.fp.model._
import org.scalatest.{Matchers, FunSuite}

class RobotServiceSpec extends FunSuite with Matchers {

  test ("El robot se debe mover ante un movimiento A" ){

    object service extends RobotServiceImpl
    val robot = Robot(Posicion(Coordenada(0,0), Orientacion.Norte))
    val robotResultado = service.mover(robot, Movimientos.A)
    assert(robotResultado == Robot(Posicion(Coordenada(0,1), Orientacion.Norte)))

    val robotResultado2 = service.mover(robotResultado, Movimientos.A)
    assert(robotResultado2 == Robot(Posicion(Coordenada(0,2), Orientacion.Norte)))

  }

  test ("El robot solo debe girar ante un movimiento I o D" ) {

    object service extends RobotServiceImpl
    val robot = Robot(Posicion(Coordenada(0, 0), Orientacion.Norte))
    val robotResultado = service.mover(robot, Movimientos.D)
    assert(robotResultado == Robot(Posicion(Coordenada(0, 0), Orientacion.Este)))

    val robotResultado2 = service.mover(robotResultado, Movimientos.D)
    assert(robotResultado2 == Robot(Posicion(Coordenada(0, 0), Orientacion.Sur)))

    val robotResultado3 = service.mover(robotResultado2, Movimientos.I)
    assert(robotResultado3 == Robot(Posicion(Coordenada(0, 0), Orientacion.Este)))

    val robotResultado4 = service.mover(robotResultado3, Movimientos.I)
    assert(robotResultado4 == Robot(Posicion(Coordenada(0, 0), Orientacion.Norte)))

  }
}
