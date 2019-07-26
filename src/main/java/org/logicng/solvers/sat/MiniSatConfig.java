///////////////////////////////////////////////////////////////////////////
//                   __                _      _   ________               //
//                  / /   ____  ____ _(_)____/ | / / ____/               //
//                 / /   / __ \/ __ `/ / ___/  |/ / / __                 //
//                / /___/ /_/ / /_/ / / /__/ /|  / /_/ /                 //
//               /_____/\____/\__, /_/\___/_/ |_/\____/                  //
//                           /____/                                      //
//                                                                       //
//               The Next Generation Logic Library                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////
//                                                                       //
//  Copyright 2015-20xx Christoph Zengler                                //
//                                                                       //
//  Licensed under the Apache License, Version 2.0 (the "License");      //
//  you may not use this file except in compliance with the License.     //
//  You may obtain a copy of the License at                              //
//                                                                       //
//  http://www.apache.org/licenses/LICENSE-2.0                           //
//                                                                       //
//  Unless required by applicable law or agreed to in writing, software  //
//  distributed under the License is distributed on an "AS IS" BASIS,    //
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or      //
//  implied.  See the License for the specific language governing        //
//  permissions and limitations under the License.                       //
//                                                                       //
///////////////////////////////////////////////////////////////////////////

package org.logicng.solvers.sat;

import static org.logicng.solvers.sat.MiniSatConfig.CNFMethod.FACTORY_CNF;
import static org.logicng.solvers.sat.MiniSatConfig.ClauseMinimization.DEEP;

import org.logicng.configurations.Configuration;
import org.logicng.configurations.ConfigurationType;
import org.logicng.formulas.Formula;
import org.logicng.solvers.SATSolver;

/**
 * The configuration object for a MiniSAT-style SAT solver.
 * @version 1.6
 * @since 1.0
 */
public final class MiniSatConfig extends Configuration {

  /**
   * The different methods for clause minimization.
   * <ul>
   * <li> {@code NONE} - no minimization is performed
   * <li> {@code BASIC} - local minimization is performed
   * <li> {@code DEEP} - recursive minimization is performed
   * </ul>
   */
  public enum ClauseMinimization {
    NONE, BASIC, DEEP
  }

  /**
   * The different methods for generating a CNF for a formula to put on the solver.
   * <ul>
   * <li> {@code FACTORY_CNF} calls the {@link Formula#cnf()} method on the formula
   * to convert it to CNF.  Therefore the CNF including all its auxiliary variables will
   * be added to the formula factory.
   * <li> {@code PG_ON_SOLVER} uses a solver-internal implementation of Plaisted-Greenbaum.
   * Auxiliary variables are only added on the solver, not on the factory.  This usually
   * leads to a reduced heap usage and often faster performance.
   * </ul>
   */
  public enum CNFMethod {
    FACTORY_CNF, PG_ON_SOLVER
  }

  final double varDecay;
  final double varInc;
  final ClauseMinimization clauseMin;
  final int restartFirst;
  final double restartInc;
  final double clauseDecay;
  final boolean removeSatisfied;
  final double learntsizeFactor;
  final double learntsizeInc;
  final boolean incremental;
  final boolean initialPhase;
  final boolean proofGeneration;
  final CNFMethod cnfMethod;
  final boolean auxiliaryVariablesInModels;

  /**
   * Constructs a new MiniSAT configuration from a given builder.
   * @param builder the builder
   */
  private MiniSatConfig(final Builder builder) {
    super(ConfigurationType.MINISAT);
    this.varDecay = builder.varDecay;
    this.varInc = builder.varInc;
    this.clauseMin = builder.clauseMin;
    this.restartFirst = builder.restartFirst;
    this.restartInc = builder.restartInc;
    this.clauseDecay = builder.clauseDecay;
    this.removeSatisfied = builder.removeSatisfied;
    this.learntsizeFactor = builder.learntsizeFactor;
    this.learntsizeInc = builder.learntsizeInc;
    this.incremental = builder.incremental;
    this.initialPhase = builder.initialPhase;
    this.proofGeneration = builder.proofGeneration;
    this.cnfMethod = builder.cnfMethod;
    this.auxiliaryVariablesInModels = builder.auxiliaryVariablesInModels;
  }

  /**
   * Returns whether the solver is incremental or not
   * @return {@code true} if the solver is incremental, {@code false} otherwise
   */
  public boolean incremental() {
    return this.incremental;
  }

  /**
   * Returns the initial phase of the solver.
   * @return the initial phase of the solver
   */
  public boolean initialPhase() {
    return this.initialPhase;
  }

  /**
   * Returns whether proof generation should be performed or not.
   * @return whether proof generation should be performed or not
   */
  public boolean proofGeneration() {
    return this.proofGeneration;
  }

  /**
   * Returns the CNF method which should be used.
   * @return the CNF method
   */
  public CNFMethod getCnfMethod() {
    return this.cnfMethod;
  }

  /**
   * Returns whether auxiliary Variables should be included in the model or not.
   * @return whether auxiliary Variables should be included in the model or not
   */
  public boolean isAuxiliaryVariablesInModels() {
    return this.auxiliaryVariablesInModels;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("MiniSatConfig{").append(String.format("%n"));
    sb.append("varDecay=").append(this.varDecay).append(String.format("%n"));
    sb.append("varInc=").append(this.varInc).append(String.format("%n"));
    sb.append("clauseMin=").append(this.clauseMin).append(String.format("%n"));
    sb.append("restartFirst=").append(this.restartFirst).append(String.format("%n"));
    sb.append("restartInc=").append(this.restartInc).append(String.format("%n"));
    sb.append("clauseDecay=").append(this.clauseDecay).append(String.format("%n"));
    sb.append("removeSatisfied=").append(this.removeSatisfied).append(String.format("%n"));
    sb.append("learntsizeFactor=").append(this.learntsizeFactor).append(String.format("%n"));
    sb.append("learntsizeInc=").append(this.learntsizeInc).append(String.format("%n"));
    sb.append("incremental=").append(this.incremental).append(String.format("%n"));
    sb.append("initialPhase=").append(this.initialPhase).append(String.format("%n"));
    sb.append("proofGeneration=").append(this.proofGeneration).append(String.format("%n"));
    sb.append("cnfMethod=").append(this.cnfMethod).append(String.format("%n"));
    sb.append("auxiliaryVariablesInModels=").append(this.auxiliaryVariablesInModels).append(String.format("%n"));
    sb.append("}").append(String.format("%n"));
    return sb.toString();
  }

  /**
   * The builder for a MiniSAT configuration.
   */
  public static class Builder {
    private double varDecay = 0.95;
    private double varInc = 1.0;
    private ClauseMinimization clauseMin = DEEP;
    private int restartFirst = 100;
    private double restartInc = 2.0;
    private double clauseDecay = 0.999;
    private boolean removeSatisfied = true;
    private double learntsizeFactor = 1.0 / 3.0;
    private double learntsizeInc = 1.1;
    private boolean incremental = true;
    private boolean initialPhase = false;
    private boolean proofGeneration = false;
    CNFMethod cnfMethod = FACTORY_CNF;
    boolean auxiliaryVariablesInModels = true;

    /**
     * Sets the variable activity decay factor to a given value. The default value is 0.95.
     * @param varDecay the value (should be in the range 0..1)
     * @return the builder
     */
    public Builder varDecay(final double varDecay) {
      this.varDecay = varDecay;
      return this;
    }

    /**
     * Sets the initial value to bump a variable with each time it is used in conflict resolution to a given value.
     * The default value is 1.0.
     * @param varInc the value
     * @return the builder
     */
    public Builder varInc(final double varInc) {
      this.varInc = varInc;
      return this;
    }

    /**
     * Sets the clause minimization method. The default value is {@code DEEP}.
     * @param ccmin the value
     * @return the builder
     */
    public Builder clMinimization(final ClauseMinimization ccmin) {
      this.clauseMin = ccmin;
      return this;
    }

    /**
     * Sets the base restart interval to the given value. The default value is 100.
     * @param restartFirst the value (should be at least 1)
     * @return the builder
     */
    public Builder restartFirst(final int restartFirst) {
      this.restartFirst = restartFirst;
      return this;
    }

    /**
     * Sets the restart interval increase factor to the given value. The default value is 2.0.
     * @param restartInc the value (should be at least 1)
     * @return the builder
     */
    public Builder restartInc(final double restartInc) {
      this.restartInc = restartInc;
      return this;
    }

    /**
     * Sets the clause activity decay factor to a given value. The default value is 0.999.
     * @param clauseDecay the value (should be in the range 0..1)
     * @return the builder
     */
    public Builder clauseDecay(final double clauseDecay) {
      this.clauseDecay = clauseDecay;
      return this;
    }

    /**
     * If turned on, the satisfied original clauses will be removed when simplifying on level 0, when turned off,
     * only the satisfied learnt clauses will be removed.  The default value is {@code true}.
     * @param removeSatisfied {@code true} if the original clauses should be simplified, {@code false} otherwise
     * @return the builder
     */
    public Builder removeSatisfied(final boolean removeSatisfied) {
      this.removeSatisfied = removeSatisfied;
      return this;
    }

    /**
     * Sets the initial limit for learnt clauses as a factor of the original clauses to the given value.  The default
     * value is 1/3.
     * @param learntsizeFactor the value
     * @return the builder
     */
    public Builder lsFactor(final double learntsizeFactor) {
      this.learntsizeFactor = learntsizeFactor;
      return this;
    }

    /**
     * Sets the factor by which the limit for learnt clauses is multiplied every restart to a given value. The default
     * value is 1.1.
     * @param learntsizeInc the value
     * @return the builder
     */
    public Builder lsInc(final double learntsizeInc) {
      this.learntsizeInc = learntsizeInc;
      return this;
    }

    /**
     * Turns the incremental mode of the solver off and on.  The default value is {@code true}.
     * @param incremental {@code true} if incremental mode is turned on, {@code false} otherwise
     * @return the builder
     */
    public Builder incremental(final boolean incremental) {
      this.incremental = incremental;
      return this;
    }

    /**
     * Sets the initial phase of the solver.  The default value is {@code true}.
     * @param initialPhase the initial phase
     * @return the builder
     */
    public Builder initialPhase(final boolean initialPhase) {
      this.initialPhase = initialPhase;
      return this;
    }

    /**
     * Sets whether the information for generating a proof with DRUP should be recorded or not.  The default
     * value is {@code false}.
     * @param proofGeneration {@code true} if proof generating information should be recorded, {@code false} otherwise
     * @return the builder
     */
    public Builder proofGeneration(final boolean proofGeneration) {
      this.proofGeneration = proofGeneration;
      return this;
    }

    /**
     * Sets the CNF method for converting formula which are not in CNF for the solver.  The default value
     * is {@code FACTORY_CNF}.
     * @param cnfMethod the CNF method
     * @return the builder
     */
    public Builder cnfMethod(final CNFMethod cnfMethod) {
      this.cnfMethod = cnfMethod;
      return this;
    }

    /**
     * Sets whether auxiliary variables (CNF, cardinality constraints, pseudo-Boolean constraints) should
     * be included in methods like {@link SATSolver#model()} or {@link SATSolver#enumerateAllModels()}.  If
     * set to {@code true}, all variables will be included in these methods,  if set to {@code false}, variables
     * starting with "@RESERVED_CC_", "@RESERVED_PB_", and "@RESERVED_CNF_" will be excluded from the models.
     * The default value is {@code true}.
     * @param auxiliaryVariablesInModels {@code true} if auxiliary variables should be included in the models,
     *                                   {@code false} otherwise
     * @return the builder
     */
    public Builder auxiliaryVariablesInModels(final boolean auxiliaryVariablesInModels) {
      this.auxiliaryVariablesInModels = auxiliaryVariablesInModels;
      return this;
    }

    /**
     * Builds the MiniSAT configuration.
     * @return the configuration
     */
    public MiniSatConfig build() {
      return new MiniSatConfig(this);
    }
  }
}
