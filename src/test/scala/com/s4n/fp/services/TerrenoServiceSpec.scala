package com.s4n.fp.services

import com.s4n.fp.model.{Coordenada, Mina, Terreno}
import org.scalatest._

import scala.util.{Try, Failure, Success}

class TerrenoServiceSpec extends FunSuite with Matchers {

  test ("Se debe agregar una mina 1" ){
    val terreno = Terreno(Array.ofDim(3, 3))
    object service extends TerrenoServiceImpl
    val terrenoConMina = service.agregarMina(terreno, Coordenada(0, 0))
    val x = terrenoConMina.get.terreno(0)(0)
    assert(x)
  }

  test ("Se debe agregar una mina 2" ){
    val terreno = Terreno(Array.ofDim(3, 3))
    object service extends TerrenoServiceImpl
    val terrenoConMina = service.agregarMina(terreno, Coordenada(0, 0))
    val x = terrenoConMina.get.terreno(1)(0)
    assert(!x)
  }

  test ("Debe fallar agregar una mina en posicion invalida" ){
    val terreno = Terreno(Array.ofDim(3, 3))
    object service extends TerrenoServiceImpl
    val terrenoConMina = service.agregarMina(terreno, Coordenada(4, 4))

    terrenoConMina match {
      case Success(x) => assert(false)
      case Failure(e) => assert(true)
    }
  }

  test ("Se debe agregar una mina y encontrarla" ){
    val terreno = Terreno(Array.ofDim(3, 3))
    object service extends TerrenoServiceImpl
    val terrenoConMina = service.agregarMina(terreno, Coordenada(0, 0))
    val x = terrenoConMina.get.terreno(0)(0)
    val b = service.hayMina(terrenoConMina.get, Coordenada(0, 0))
    b match {
      case Success(minaOpt) => assertResult(Option(Mina(Coordenada(0, 0))))(minaOpt)
    }

  }

}
