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
//  Copyright 2015 Christoph Zengler                                     //
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

package org.logicng.cardinalityconstraints;

import org.logicng.collections.ImmutableFormulaList;
import org.logicng.formulas.Literal;

import java.util.Arrays;
import java.util.Collection;

/**
 * The interface for at-most-k (AMK) cardinality constraints.
 * @author Christoph Zengler
 * @version 1.0
 * @since 1.0
 */
public abstract class CCAtMostK {

  /**
   * Builds a cardinality constraint of the form {@code lit_1 + lit_2 + ... + lit_n <= k}.
   * @param lits the literals {@code lit_1 ... lit_n}
   * @param rhs  the right hand side {@code k} of the constraint
   * @return the CNF encoding of the cardinality constraint
   * @throws IllegalArgumentException if the right hand side of the cardinality constraint is negative
   */
  public abstract ImmutableFormulaList build(Collection<Literal> lits, int rhs);

  /**
   * Builds a cardinality constraint of the form {@code lit_1 + lit_2 + ... + lit_n <= k}.
   * @param lits the literals {@code lit_1 ... lit_n}
   * @param rhs  the right hand side {@code k} of the constraint
   * @return the CNF encoding of the cardinality constraint
   * @throws IllegalArgumentException if the right hand side of the cardinality constraint is negative
   */
  public ImmutableFormulaList build(final Literal[] lits, int rhs) {
    return this.build(Arrays.asList(lits), rhs);
  }
}