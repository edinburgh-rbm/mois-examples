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

import uk.ac.ed.inf.mois.{Accumulator, BaseProcess, ConstraintViolation, StepHandler, Math, Model, ODE, VarCalc}

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
  ) extends ODE("Bollenbach") with VarCalc with Math {

  Annotate("title", "Nonoptimal Microbial Response to Antibiotics Underlies Suppressive Drug Interactions")
  Annotate("author", List("Tobias Bollenbach", "Selwyn Quan", "Remy Chait", "Roy Kishony"))
  Annotate("url", List("https://www.cell.com/cell/pdf/S0092-8674(09)01315-4.pdf",
		       "https://www.cell.com/cms/attachment/604695/4793645/mmc2.pdf"))

  val g = Double("g") := 1.0
  g.Annotate("long_name", "Growth Rate")
  g.Annotate("units", "1/h")

  val p = Double("p") := 2.4e6
  p must (_ >= 0.0)
  p.Annotate("long_name", "Proteins per cell")

  val c = Double("c") := 1.8
  c must (_ >= 0.0)
  c.Annotate("long_name", "Genome equivalends of DNA per cell")

  val r = Double("r") := 1.35e4
  r must (_ >= 0.0)
  r.Annotate("long_name", "Ribosomes per cell")

  val a = Double("a") := 1.0
  a must (_ >= 0.0)
  a.Annotate("long_name", "Resources per cell")

  val s_ropt = Double("s_ropt")
  s_ropt.Annotate("long_name", "Optimal regulation of ribosome synthesis")
  Dimension(s_ropt)

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
    println(s"$t ${state.toList}")
//    throw new Exception("asda")
  }
}

class BollenbachModel extends Model {
  val delta = Double("delta") := 1.0
  delta.Annotate("long_name", "Relative change of DNA synthesis rate (< 1.0 with antibiotics")

  val eps_c = Double("epsilon_c") := 0.039
  eps_c.Annotate("long_name", "Resources consumed to make one chromosome")

  val eps_p = Double("epsilon_p") := 8.1e-7
  eps_c.Annotate("long_name", "Resources consumed to make one protein")

  val eps_r = Double("epsilon_r") := 2.2e-5
  eps_c.Annotate("long_name", "Resources consumed to make one ribosome")

  val k_deg = Double("k_deg") := 0.12
  k_deg.Annotate("long_name", "Resource degradation rate")
  k_deg.Annotate("units", "1/h")

  val k_p0 = Double("k_p0") := 0.059
  k_p0.Annotate("long_name", "Maximal rate of protein synthesis per ribosome")
  k_p0.Annotate("units", "1/s")

  val k_v = Double("k_v") := 3.73e-7
  k_v.Annotate("long_name", "Cell volume per protein")
  k_v.Annotate("units", "1e-6 m^3")

  val M_a = Double("M_a") := 0.53
  M_a.Annotate("long_name", "Resource concentration where chain elongation rates are half max")
  M_a.Annotate("units", "1e-6 m^-3")

  val N_rrn = Double("N_rrn") := 7.0
  N_rrn.Annotate("long_name", "Number of rrn operons per chromosome")

  val p_o = Double("p_o") := 9.9e5
  p_o.Annotate("long_name", "Protein per replication origin")

  val p_r = Double("p_r") := 20.7
  p_r.Annotate("long_name", "Amount of protein per ribosome")

  val rho = Double("rho") := 1.0
  rho.Annotate("long_name", "Fraction of functional ribosomes (< 1.0 with antibiotic)")

  val s_r0 = Double("s_r0") := 72.0
  s_r0.Annotate("long_name", "Maximal rate of ribosome synthesis per rrn operon")
  s_r0.Annotate("units", "1/min")

  val tau_C0 = Double("tau_C0") := 33.0
  tau_C0.Annotate("long_name", "Minimalreplication time of chromosome")
  tau_C0.Annotate("units", "60 s")

  val tau_D0 = Double("tau_D0") := 16.0
  tau_D0.Annotate("long_name", "Minimal delay before cell division")
  tau_D0.Annotate("units", "60 s")

  val v_a = Double("v_a") := 2.42
  v_a.Annotate("long_name", "Resource influx")
  v_a.Annotate("units", "1/h")

  val s_rmin = Int("s_rmin") := -100
  val s_rmax = Int("s_rmax") := 100

  val exemplar = new Bollenbach(delta, eps_c, eps_p, eps_r, k_deg, k_p0, k_v, M_a, N_rrn,
				p_o, p_r, rho, s_r0, tau_C0, tau_D0, v_a)
  val process = new ReplayProcess(exemplar.name, exemplar)
  process.Dimension(exemplar.s_ropt)

  override def run(t: Double, tau: Double) {
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
	attempt(t, tau)
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

trait Debug extends BaseProcess {
  def debug(t: Double): Unit

  object debugHandler extends StepHandler {
    def init(t: Double, p: BaseProcess) {
      println("debug init...")
      debug(t)
    }
    def handleStep(t: Double, p: BaseProcess) {
      debug(t)
    }
  }
  addStepHandler(debugHandler)
}

class ReplayProcess(val name: String, proc: BaseProcess) extends BaseProcess {
  override def toString = proc.toString
  leftMerge(proc)
  def step(t: Double, tau: Double) {
    throw new IllegalArgumentException("ReplayProcess is not expected to step")
  }

  def replay(acc: Accumulator) {
    for( (t, vs) <- acc.history ) {
      this <<< vs
      for (sh <- stepHandlers) {
        sh.handleStep(t, this)
      }
    }
  }
}