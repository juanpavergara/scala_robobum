package com.s4n.fp.services

import com.s4n.fp.model.{Coordenada, Mina, Terreno}

import scala.util.{Failure, Success, Try}

trait TerrenoService {
  def consultarDimension(t:Terreno): (Int, Int)
  def esCoordenadaValida(t:Terreno, c:Coordenada): Boolean
  def agregarMina(t: Terreno, c:Coordenada): Try[Terreno]
  def hayMina(t:Terreno, c:Coordenada): Try[Option[Mina]]
}

trait TerrenoServiceImpl extends TerrenoService {

  override def consultarDimension(t:Terreno): (Int, Int) ={
    (t.terreno.length, t.terreno(0).length)
  }

  override def esCoordenadaValida(t:Terreno, c:Coordenada): Boolean = {
    val limites = consultarDimension(t)
    c.x < limites._1 && c.x >= 0 && c.y < limites._2 && c.y >= 0
  }

  override def agregarMina(terreno: Terreno, c:Coordenada): Try[Terreno] = {

    if(esCoordenadaValida(terreno, c)) {
      val m = terreno.terreno.clone()
      m(c.x)(c.y) = true
      Success(terreno.copy(terreno=m))
    }else{
      Failure(new Exception(s"Posicion invalida para agregar mina $c"))
    }
  }

  override def hayMina(t:Terreno, c:Coordenada): Try[Option[Mina]] = {
    if(esCoordenadaValida(t,c)){
      if(t.terreno(c.x)(c.y))
        Success(Some(Mina(c)))
      else {
        Success(None)
      }
    }
    else{
      Failure(new Exception("La coordenada consultada no es valida en el terreno: " + c))
    }

  }
}
