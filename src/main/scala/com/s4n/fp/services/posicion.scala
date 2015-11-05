package com.s4n.fp.services

import com.s4n.fp.model.Movimientos.Movimiento
import com.s4n.fp.model.{Movimientos, Coordenada, Orientacion, Posicion}

trait PosicionService {
  def cambiar(posicion: Posicion)(f:Posicion=>Posicion):Posicion
  def cambiar(posicion: Posicion, movimiento: Movimiento):Posicion
}

trait PosicionServiceImpl extends PosicionService {

  def cambiar(posicion: Posicion)(f:Posicion=>Posicion) = f(posicion)

  def cambiar(posicion: Posicion, movimiento: Movimiento) = {
    movimiento match {
      case Movimientos.A => cambiar(posicion)(moverAdelante)
      case Movimientos.I => cambiar(posicion)(moverIzquierda)
      case Movimientos.D => cambiar(posicion)(moverDerecha)
    }
  }

  def moverAdelante(posicion: Posicion): Posicion = {
    posicion.orientacion match {
      case Orientacion.Norte => posicion.copy(coordenada = posicion.coordenada.copy(y=posicion.coordenada.y+1))
      case Orientacion.Sur => posicion.copy(coordenada = posicion.coordenada.copy(y=posicion.coordenada.y-1))
      case Orientacion.Este => posicion.copy(coordenada = posicion.coordenada.copy(x=posicion.coordenada.x+1))
      case Orientacion.Oeste => posicion.copy(coordenada = posicion.coordenada.copy(x=posicion.coordenada.x-1))
    }
  }

  def moverDerecha(posicion: Posicion): Posicion = {
    posicion.orientacion match {
      case Orientacion.Norte => posicion.copy(orientacion = Orientacion.Este)
      case Orientacion.Sur => posicion.copy(orientacion = Orientacion.Oeste)
      case Orientacion.Este => posicion.copy(orientacion = Orientacion.Sur)
      case Orientacion.Oeste => posicion.copy(orientacion = Orientacion.Norte)
    }
  }

  def moverIzquierda(posicion: Posicion): Posicion = {
    posicion.orientacion match {
      case Orientacion.Norte => posicion.copy(orientacion = Orientacion.Oeste)
      case Orientacion.Sur => posicion.copy(orientacion = Orientacion.Este)
      case Orientacion.Este => posicion.copy(orientacion = Orientacion.Norte)
      case Orientacion.Oeste => posicion.copy(orientacion = Orientacion.Sur)
    }
  }
}
