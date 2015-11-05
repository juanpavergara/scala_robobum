package com.s4n.fp.services

import com.s4n.fp.model._
import org.scalatest.{Matchers, FunSuite}
import com.s4n.fp.services._
import scala.util.{Failure, Success, Try}

class JuegoServiceSpec extends FunSuite with Matchers {

  test ("Se debe agregar una mina al terreno de juego" ){
    object service extends JuegoServiceImpl

    val minas = List(Mina(Coordenada(1,1)))
    val terreno = Terreno(Array.ofDim(3,3))

    val res = service.agregarMinas(minas, terreno)

    res match {
      case Success(terr) => assert(terr.terreno(1)(1))
      case Failure(ex) => assert(false)
    }
  }

  test ("Se debe agregar varias minas al terreno de juego" ){
    object service extends JuegoServiceImpl

    val minas = List(Mina(Coordenada(1,1)), Mina(Coordenada(0,0)), Mina(Coordenada(2,2)))
    val terreno = Terreno(Array.ofDim(3,3))

    val res = service.agregarMinas(minas, terreno)

    res match {
      case Success(terr) =>{
        assert(terr.terreno(0)(0))
        assert(terr.terreno(1)(1))
        assert(terr.terreno(2)(2))
      }
      case Failure(ex) => assert(false)
    }
  }

  test ("El resultado al intentar agregar una mina en posicion invalida debe ser una falla" ){
    object service extends JuegoServiceImpl

    val minas = List(Mina(Coordenada(1,1)), Mina(Coordenada(4,4)), Mina(Coordenada(0,0)))
    val terreno = Terreno(Array.ofDim(3,3))

    val res = service.agregarMinas(minas, terreno)

    res match {
      case Success(terr) =>{ assert(false)
      }
      case Failure(ex) => assert(ex.getMessage === "Posicion invalida para agregar mina Coordenada(4,4)")
    }
  }

  test ("Intento de ejecutar el juego" ){
    object service extends JuegoServiceImpl

    val minas = List(Mina(Coordenada(0,0)), Mina(Coordenada(0,1)), Mina(Coordenada(0,2)))
    val terreno = Terreno(Array.ofDim(3,3))
    val robot = Robot(Posicion(Coordenada(0,0), Orientacion.Norte))
    val movimientos = List(Movimientos.A, Movimientos.A)

    val resultado = service.operar(minas, terreno, movimientos, robot)

    resultado match {
      case Success(r) => {
        println("Resultado del juego: " + r)
        assert(true)
      }
      case Failure(ex) => {
        println("Resultado del juego: " + ex.getMessage)
        assert(false)
      }
    }
  }
}
