package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.{DeterministicReactionNetwork, Model}

class Replication
  extends DeterministicReactionNetwork("Brusselator") {
  
  val ATP = Species("ATP")
  val H20 = Species("H2O")
  val ADP = Species("ADP")
  val H = Species("H")
  val PI = Species("PI")
  val DR5P = Species("DR5P")
  val NAD = Species("NAD")
  val AMP = Species("AMP")
  val dRibose5P_dRibose5P = Species("dRibose5P_dRibose5P")
  val NMN = Species("NMN")
  val DAMP = Species("DAMP")
  val DATP = Species("DATP")
  val dApdAp = Species("dApdAp")
  val PPI = Species("PPI")
  val DCMP = Species("DCMP")
  val DCTP = Species("DCTP")
  val dCpdCp = Species("dCpdCp")
  val PPI = Species("PPI")
  val DGMP = Species("DGMP")
  val DGTP = Species("DGTP")
  val dGpdGp = Species("dGpdGp")
  val DTMP = Species("DTMP")
  val DTTP = Species("DTTP")
  val dTpdTp = Species("dTpdTp")
  val ApAp = Species("ApAp")
  val CMP = Species("CMP")
  val CTP = Species("CTP")
  val CpCp = Species("CpCp")
  val GMP = Species("GMP")
  val GTP = Species("GTP")
  val GpGp = Species("GpGp")
  val UMP = Species("UMP")
  val UTP = Species("UTP")
  val UpUp = Species("UpUp")

  val DNA_POLYMERASE_CORE = Species("DNA_POLYMERASE_CORE")
  val DNA_POLYMERASE_GAMMA_COMPLEX = Species("DNA_POLYMERASE_GAMMA_COMPLEX")
  val MG_001_MONOMER = Species("MG_001_MONOMER")
  val MG_250_MONOMER = Species("MG_250_MONOMER")
  val DNA_POLYMERASE_2CORE_BETA_CLAMP_GAMMA_COMPLEX_PRIMASE = Species("DNA_POLYMERASE_2CORE_BETA_CLAMP_GAMMA_COMPLEX_PRIMASE")
  val DNA_POLYMERASE_CORE = Species("DNA_POLYMERASE_CORE")
  val DNA_POLYMERASE_GAMMA_COMPLEX = Species("DNA_POLYMERASE_GAMMA_COMPLEX")
  val DNA_POLYMERASE_CORE_BETA_CLAMP_GAMMA_COMPLEX = Species("DNA_POLYMERASE_CORE_BETA_CLAMP_GAMMA_COMPLEX")
  val DNA_POLYMERASE_CORE_BETA_CLAMP_PRIMASE = Species("DNA_POLYMERASE_CORE_BETA_CLAMP_PRIMASE")
  val DNA_POLYMERASE_HOLOENZYME = Species("DNA_POLYMERASE_HOLOENZYME")
  val MG_001_DIMER = Species("MG_001_DIMER")
  val MG_091_TETRAMER = Species("MG_091_TETRAMER")
  val MG_091_OCTAMER = Species("MG_091_OCTAMER")
  val MG_094_HEXAMER = Species("MG_094_HEXAMER")
  val REPLISOME = Species("REPLISOME")

  reactions(
    // -- Chemical reactions --

    // DNA unwinding
    ATP + H2O -> ADP + H + PI at 1.0,
    // DNA ligation
    2 * DR5P + NAD -> AMP + dRibose5P_dRibose5P + H + NMN
      catalysedBy MG_254_MONOMER using QSS(vmax = 2.4, // 1/min
                                           km = 4.1), // nM
    // DNA polymerization (dATP)
    DAMP + DATP -> dApdAp + PPI
      catalysedBy DNA_POLYMERASE_CORE using QSS(vmax = 6000, // 1/min
                                                km = 4.1), // nM
    // DNA polymerization (dCTP)
    DCMP + DCTP -> dCpdCp + PPI
      catalysedBy DNA_POLYMERASE_CORE using QSS(vmax = 6000, // 1/min
                                                km = 4.1), // nM
    // DNA polymerization (dGTP)
    DGMP + DGTP -> dGpdGp + PPI
      catalysedBy DNA_POLYMERASE_CORE using QSS(vmax = 6000, // 1/min
                                                km = 4.1), // nM
    // DNA polymerization (dTTP)
    DTMP + DTTP -> dTpdTp + PPI
      catalysedBy DNA_POLYMERASE_CORE using QSS(vmax = 6000, // 1/min
                                                km = 4.1), // nM
    // DNA primer polymerization (ATP)
    AMP + ATP -> ApAp + PPI
      catalysedBy MG_250_MONOMER using QSS(vmax = 6000, // 1/min
                                           km = 3e5), // nM
    // DNA primer polymerization (CTP)
    CMP + CTP -> CpCp + PPI
      catalysedBy MG_250_MONOMER using QSS(vmax = 6000, // 1/min
                                           km = 3e5), // nM
    // DNA primer polymerization (GTP)
    GMP + GTP -> GpGp + PPI
      catalysedBy MG_250_MONOMER using QSS(vmax = 6000, // 1/min
                                           km = 3e5), // nM
    // DNA primer polymerization (UTP)
    UMP + UTP -> PPI + UpUp
      catalysedBy MG_250_MONOMER using QSS(vmax = 6000, // 1/min
                                           km = 3e5), // nM

    // -- Complex formation reactions --

    // DNA polymerase (2) core, β-clamp, γ-complex, and primase
    2 * DNA_POLYMERASE_CORE + DNA_POLYMERASE_GAMMA_COMPLEX + 2 * MG_001_MONOMER + MG_250_MONOMER ->
      DNA_POLYMERASE_2CORE_BETA_CLAMP_GAMMA_COMPLEX_PRIMASE,
    // DNA polymerase core, β-clamp, and γ-complex
    DNA_POLYMERASE_CORE + DNA_POLYMERASE_GAMMA_COMPLEX + 2 * MG_001_MONOMER -> DNA_POLYMERASE_CORE_BETA_CLAMP_GAMMA_COMPLEX,
    // DNA polymerase core, β-clamp, and primase
    DNA_POLYMERASE_CORE + 2 * MG_001_MONOMER + MG_250_MONOMER -> DNA_POLYMERASE_CORE_BETA_CLAMP_PRIMASE,
    // DNA-directed DNA polymerase holoenzyme
    2 * DNA_POLYMERASE_CORE + DNA_POLYMERASE_GAMMA_COMPLEX + 2 * MG_001_MONOMER -> DNA_POLYMERASE_HOLOENZYME,
    // DNA polymerase III, beta clamp
    2 * MG_001_MONOMER -> MG_001_DIMER,
    // Single-strand binding protein family, octamer
    2 * MG_091_TETRAMER -> MG_091_OCTAMER,
    // Replisome
    2 * DNA_POLYMERASE_CORE + DNA_POLYMERASE_GAMMA_COMPLEX + 4 * MG_001_MONOMER + MG_094_HEXAMER + MG_250_MONOMER -> REPLISOME
  )
}

/** ReplicationModel is used generically in test:run because it is a
  * decent model to test things like graphing out.
  */
class ReplicationModel extends Model {
  val process = new Replication
  import process._
  ATP := 1.0
  H20 := 1.0
  ADP := 1.0
  H := 1.0
  PI := 1.0
  DR5P := 1.0
  NAD := 1.0
  AMP := 1.0
  dRibose5P_dRibose5P := 1.0
  NMN := 1.0
  DAMP := 1.0
  DATP := 1.0
  dApdAp := 1.0
  PPI := 1.0
  DCMP := 1.0
  DCTP := 1.0
  dCpdCp := 1.0
  PPI := 1.0
  DGMP := 1.0
  DGTP := 1.0
  dGpdGp := 1.0
  DTMP := 1.0
  DTTP := 1.0
  dTpdTp := 1.0
  ApAp := 1.0
  CMP := 1.0
  CTP := 1.0
  CpCp := 1.0
  GMP := 1.0
  GTP := 1.0
  GpGp := 1.0
  UMP := 1.0
  UTP := 1.0
  UpUp := 1.0

  DNA_POLYMERASE_CORE := 1.0
  DNA_POLYMERASE_GAMMA_COMPLEX := 1.0
  MG_001_MONOMER := 1.0
  MG_250_MONOMER := 1.0
  DNA_POLYMERASE_2CORE_BETA_CLAMP_GAMMA_COMPLEX_PRIMASE := 1.0
  DNA_POLYMERASE_CORE := 1.0
  DNA_POLYMERASE_GAMMA_COMPLEX := 1.0
  DNA_POLYMERASE_CORE_BETA_CLAMP_GAMMA_COMPLEX := 1.0
  DNA_POLYMERASE_CORE_BETA_CLAMP_PRIMASE := 1.0
  DNA_POLYMERASE_HOLOENZYME := 1.0
  MG_001_DIMER := 1.0
  MG_091_TETRAMER := 1.0
  MG_091_OCTAMER := 1.0
  MG_094_HEXAMER := 1.0
  REPLISOME := 1.0
}
