/*
 *  MOIS Examples: Ordinary Differential Equations
 *  Copyright (C) 2014 University of Edinburgh School of Informatics
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.{Accumulator, Process, ConstraintViolation, StepHandler, Math, Model, ODE, VarCalc}
import spire.implicits._
import uk.ac.ed.inf.mois.implicits._

class Bollenbach(
  val delta: Double,
  val eps_c: Double,
  val eps_r: Double,
  val eps_p: Double,
  val k_deg: Double,
  val k_p0: Double,
  val k_v: Double,
  val M_a: Double,
  val N_rrn: Double,
  val p_o: Double,
  val p_r: Double,
  val rho: Double,
  val s_r0: Double,
  val tau_C0: Double,
  val tau_D0: Double,
  val v_a: Double
  ) extends ODE with VarCalc with Math {

  annotate("title", "Nonoptimal Microbial Response to Antibiotics Underlies Suppressive Drug Interactions")
  annotate("author", List("Tobias Bollenbach", "Selwyn Quan", "Remy Chait", "Roy Kishony"))
  annotate("url", List("https://www.cell.com/cell/pdf/S0092-8674(09)01315-4.pdf",
                       "https://www.cell.com/cms/attachment/604695/4793645/mmc2.pdf"))

  val g = Double("g") default(1.0)
  g.annotate("long_name", "Growth Rate")
  g.annotate("units", "1/h")

  val p = Double("p") default(2.4e6)
  p must (_ >= 0.0)
  p.annotate("long_name", "Proteins per cell")

  val c = Double("c") default(1.8)
  c must (_ >= 0.0)
  c.annotate("long_name", "Genome equivalends of DNA per cell")

  val r = Double("r") default(1.35e4)
  r must (_ >= 0.0)
  r.annotate("long_name", "Ribosomes per cell")

  val a = Double("a") default(1.0)
  a must (_ >= 0.0)
  a.annotate("long_name", "Resources per cell")

  val s_ropt = Double("s_ropt")
  s_ropt.annotate("long_name", "Optimal regulation of ribosome synthesis")
  s_ropt.dimension()

  @inline final def V = k_v * (p + p_r*r)
  @inline final def eta = p_r * s_r / (s_p + p_r*s_r)
  @inline final def f_res = (a/V) / (M_a + a/V)
  @inline final def N_o = exp(g*(tau_D + tau_C))
  @inline final def N_f = 2 * exp(g*tau_D) * (-1 + exp(g*tau_C))
  @inline final def tau_C = tau_C0/(f_res * delta)
  @inline final def tau_D = tau_D0/f_res

//  def s_p = (1-eta) * k_p0 * f_res * rho * r
  @inline final def s_p = k_p0 * f_res * rho * r - p_r * s_r
  @inline final def s_c = N_f / (2 * tau_C)
  @inline final def s_r = N_rrn * N_o * min(s_ropt, s_r0 * f_res)
  @inline final def s_a = v_a

  d(p) := s_p - g*p
  d(c) := s_c - g*c
  d(r) := s_r - g*r
  d(a) := s_a - (g + k_deg)*a - (eps_p*s_p + eps_r*s_r + eps_c*s_c)

  calc(g) := s_p / (N_o * p_o)

  def debug(t: Double) {
    println(s"s_p = ${s_p}")
    println(s"N_o = ${N_o}")
    println(s"p_o = ${p_o}")
    //println(s"$t ${state.toList}")
//    throw new Exception("asda")
  }
}

class BollenbachModel extends Model {
  val delta = Double("delta") default(1.0)
  delta.annotate("long_name", "Relative change of DNA synthesis rate (< 1.0 with antibiotics")

  val eps_c = Double("epsilon_c") default(0.039)
  eps_c.annotate("long_name", "Resources consumed to make one chromosome")

  val eps_p = Double("epsilon_p") default(8.1e-7)
  eps_c.annotate("long_name", "Resources consumed to make one protein")

  val eps_r = Double("epsilon_r") default(2.2e-5)
  eps_c.annotate("long_name", "Resources consumed to make one ribosome")

  val k_deg = Double("k_deg") default(0.12)
  k_deg.annotate("long_name", "Resource degradation rate")
  k_deg.annotate("units", "1/h")

  val k_p0 = Double("k_p0") default(0.059)
  k_p0.annotate("long_name", "Maximal rate of protein synthesis per ribosome")
  k_p0.annotate("units", "1/s")

  val k_v = Double("k_v") default(3.73e-7)
  k_v.annotate("long_name", "Cell volume per protein")
  k_v.annotate("units", "1e-6 m^3")

  val M_a = Double("M_a") default(0.53)
  M_a.annotate("long_name", "Resource concentration where chain elongation rates are half max")
  M_a.annotate("units", "1e-6 m^-3")

  val N_rrn = Double("N_rrn") default(7.0)
  N_rrn.annotate("long_name", "Number of rrn operons per chromosome")

  val p_o = Double("p_o") default(9.9e5)
  p_o.annotate("long_name", "Protein per replication origin")

  val p_r = Double("p_r") default(20.7)
  p_r.annotate("long_name", "Amount of protein per ribosome")

  val rho = Double("rho") default(1.0)
  rho.annotate("long_name", "Fraction of functional ribosomes (< 1.0 with antibiotic)")

  val s_r0 = Double("s_r0") default(72.0)
  s_r0.annotate("long_name", "Maximal rate of ribosome synthesis per rrn operon")
  s_r0.annotate("units", "1/min")

  val tau_C0 = Double("tau_C0") default(33.0)
  tau_C0.annotate("long_name", "Minimalreplication time of chromosome")
  tau_C0.annotate("units", "60 s")

  val tau_D0 = Double("tau_D0") default(16.0)
  tau_D0.annotate("long_name", "Minimal delay before cell division")
  tau_D0.annotate("units", "60 s")

  val v_a = Double("v_a") default(2.42)
  v_a.annotate("long_name", "Resource influx")
  v_a.annotate("units", "1/h")

  val s_rmin = Int("s_rmin") default(-100)
  val s_rmax = Int("s_rmax") default(100)

  lazy val exemplar = new Bollenbach(delta, eps_c, eps_p, eps_r, k_deg, k_p0, k_v, M_a, N_rrn,
                                p_o, p_r, rho, s_r0, tau_C0, tau_D0, v_a)
  lazy val process = new ReplayProcess(exemplar)
  override def init(t: Double) {
    super.init(t)
//  XXX why was this here?
//    process.dimension(exemplar.s_ropt)
  }

  override def run(t: Double, tau: Double, n: Int) {
    var g_max = -math.exp(100)
    var best: Accumulator = null

    for (s_ropt <- s_rmin.value until s_rmax.value) {
      val attempt = new Bollenbach(delta, eps_c, eps_p, eps_r, k_deg, k_p0, k_v, M_a, N_rrn,
                                   p_o, p_r, rho, s_r0, tau_C0, tau_D0, v_a)
      val acc = new Accumulator
      attempt.addStepHandler(acc)
      attempt.s_ropt := s_ropt

      try {
        attempt.init(t)
        var i = 0
        val dt = tau/n
        while (i < n) {
          attempt(t + i*dt, dt)
          i += 1
        }
        attempt.finish

        println(s"${s_ropt} -> g: ${attempt.g.value} (${g_max})... ")

        if (attempt.g.value > g_max) {
          g_max = attempt.g.value
          println(s"\nnew best: ${s_ropt} ${g_max}")
          best = acc
        }
      } catch {
        case e: ConstraintViolation =>
          println(s"${s_ropt} -> $e")
      }
    }
    process.replay(best)
  }
}

trait Debug extends Process {
  def debug(t: Double): Unit

  object debugHandler extends StepHandler {
    def init(t: Double, p: Process) {
      println("debug init...")
      debug(t)
    }
    def handleStep(t: Double, p: Process) {
      debug(t)
    }
  }
  addStepHandler(debugHandler)
}

class ReplayProcess(proc: Process) extends Process {
  override def toString = proc.toString
  merge(proc)

  def replay(acc: Accumulator) {
    for( (t, vs) <- acc.history ) {
      this.state <<< vs
      for (sh <- stepHandlers) {
        sh.handleStep(t, this)
      }
    }
  }
}
