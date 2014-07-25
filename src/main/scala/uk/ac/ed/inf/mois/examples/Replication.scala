package uk.ac.ed.inf.mois.examples

import uk.ac.ed.inf.mois.{DeterministicReactionNetwork, Model}

class Replication
  extends DeterministicReactionNetwork("Brusselator") {
  
  val ATP = Specie("ATP")
  val H20 = Specie("H2O")
  val ADP = Specie("ADP")
  val H = Specie("H")
  val PI = Specie("PI")
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
// DNA unwinding
    ATP + H2O -> ADP + H + PI at 1.0,
// DNA ligationi
    (2) DR5P + NAD ⇒ AMP + dRibose5P_dRibose5P + H + NMN
// DNA polymerization (dATP)
    DAMP + DATP -> dApdAp + PPI
// DNA polymerization (dCTP)
    DCMP + DCTP -> dCpdCp + PPI
// DNA polymerization (dGTP)
    DGMP + DGTP -> dGpdGp + PPI
// DNA polymerization (dTTP)
    DTMP + DTTP -> dTpdTp + PPI
// DNA primer polymerization (ATP)
    AMP + ATP -> ApAp + PPI
// DNA primer polymerization (CTP)
    CMP + CTP -> CpCp + PPI
// DNA primer polymerization (GTP)
    GMP + GTP -> GpGp + PPI
// DNA primer polymerization (UTP)
    UMP + UTP -> PPI + UpUp

//// Now the complex formation reactions

//    DNA polymerase (2) core, β-clamp, γ-complex, and primase
    (2) DNA_POLYMERASE_CORE + DNA_POLYMERASE_GAMMA_COMPLEX + (2) MG_001_MONOMER + MG_250_MONOMER -> DNA_POLYMERASE_2CORE_BETA_CLAMP_GAMMA_COMPLEX_PRIMASE
//    DNA polymerase core, β-clamp, and γ-complex
    DNA_POLYMERASE_CORE + DNA_POLYMERASE_GAMMA_COMPLEX + (2) MG_001_MONOMER -> DNA_POLYMERASE_CORE_BETA_CLAMP_GAMMA_COMPLEX
//    DNA polymerase core, β-clamp, and primase  
    DNA_POLYMERASE_CORE + (2) MG_001_MONOMER + MG_250_MONOMER -> DNA_POLYMERASE_CORE_BETA_CLAMP_PRIMASE
//    DNA-directed DNA polymerase holoenzyme
    (2) DNA_POLYMERASE_CORE + DNA_POLYMERASE_GAMMA_COMPLEX + (2) MG_001_MONOMER -> DNA_POLYMERASE_HOLOENZYME
//    DNA polymerase III, beta clamp
    (2) MG_001_MONOMER -> MG_001_DIMER
//    single-strand binding protein family, octamer
    (2) MG_091_TETRAMER -> MG_091_OCTAMER
//    Replisome
    (2) DNA_POLYMERASE_CORE + DNA_POLYMERASE_GAMMA_COMPLEX + (4) MG_001_MONOMER + MG_094_HEXAMER + MG_250_MONOMER -> REPLISOME

  )
}

/**
 * BrusselatorModel is used generically in test:run because it is a
 * decent model to test things like graphing out.
 */
class BrusselatorModel extends Model {
  val process = new Brusselator
  import process._
  A := 1.0
  B := 1.7
  X := 1.0
  Y := 1.0
}
