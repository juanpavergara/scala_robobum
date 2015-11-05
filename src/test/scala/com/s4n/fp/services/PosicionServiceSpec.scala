package com.s4n.fp.services

import com.s4n.fp.model._
import com.s4n.fp.model.Movimientos._
import org.scalatest.{Matchers, FunSuite}

class PosicionServiceSpec extends FunSuite with Matchers {

  test ("Se debe mover hacia delante" ){
    val posicion = Posicion(Coordenada(0,0), Orientacion.Norte)
    object service extends PosicionServiceImpl
    val posicionResultante = service.cambiar(posicion, Movimientos.A)
    assert(posicionResultante.orientacion == Orientacion.Norte)
    assert(posicionResultante.coordenada.y == 1)
  }

  test ("Se debe mover hacia la derecha" ){
    val posicion = Posicion(Coordenada(0,0), Orientacion.Norte)
    object service extends PosicionServiceImpl
    val posicionResultante = service.cambiar(posicion, Movimientos.D)
    assert(posicionResultante.orientacion == Orientacion.Este)
    assert(posicionResultante.coordenada.x == 0)
    assert(posicionResultante.coordenada.y == 0)
  }

  test ("Se debe mover hacia la izquierda" ){
    val posicion = Posicion(Coordenada(0,0), Orientacion.Norte)
    object service extends PosicionServiceImpl
    val posicionResultante = service.cambiar(posicion, Movimientos.D)
    assert(posicionResultante.orientacion == Orientacion.Este)
    assert(posicionResultante.coordenada.x == 0)
    assert(posicionResultante.coordenada.y == 0)
  }
}
