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

package org.logicng.formulas;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Unit Tests for the class {@link Or}.
 * @author Christoph Zengler
 * @version 1.0
 * @since 1.0
 */
public class OrTest {

  @Test
  public void testType() {
    Assert.assertEquals(FType.OR, F.OR1.type());
  }

  @Test
  public void testCreator() {
    Assert.assertEquals(F.FALSE, F.f.or());
    Assert.assertEquals(F.TRUE, F.f.or(F.TRUE));
    Assert.assertEquals(F.FALSE, F.f.or(F.FALSE));
    Assert.assertEquals(F.TRUE, F.f.or(F.TRUE, F.FALSE));
    Assert.assertEquals(F.TRUE, F.f.or(F.FALSE, F.TRUE));
    Assert.assertEquals(F.NA, F.f.or(F.NA));
    Assert.assertEquals(F.OR1, F.f.or(F.X, F.Y, F.X, F.Y, F.X));
    Assert.assertEquals(F.OR1, F.f.or(F.f.or(F.X, F.Y), F.X, F.f.or(F.X, F.Y)));
    Assert.assertEquals(F.OR1, F.f.or(F.FALSE, F.X, F.Y, F.FALSE));
    Assert.assertEquals(F.NA, F.f.or(F.NA, F.NA, F.NA));
    Assert.assertEquals(F.NA, F.f.or(F.NA, F.NA, F.FALSE, F.FALSE));
    Assert.assertEquals(F.TRUE, F.f.or(F.NA, F.NA, F.TRUE, F.FALSE));
    List<Literal> lits = new LinkedList<>();
    lits.add(F.X);
    lits.add(F.Y);
    Assert.assertEquals(F.OR1, F.f.or(lits));
    Assert.assertEquals(F.TRUE, F.f.or(F.A, F.B, F.X, F.TRUE));
    Assert.assertEquals(F.f.or(F.A, F.B, F.X, F.Y), F.f.or(F.f.or(F.A, F.B), F.f.or(F.X, F.Y)));
    Assert.assertEquals(F.OR3, F.f.or(F.f.and(F.A, F.B), F.f.or(F.f.and(F.f.and(F.NA, F.NB)), F.f.and(F.f.or(F.NA, F.FALSE), F.NB))));
    Assert.assertEquals(F.OR1, F.f.naryOperator(FType.OR, Arrays.asList(F.X, F.Y, F.X, F.Y, F.X)));
  }

  @Test
  public void testComplementaryCheck() {
    Assert.assertEquals(F.TRUE, F.f.or(F.A, F.NA));
    Assert.assertEquals(F.TRUE, F.f.or(F.A, F.B, F.f.or(F.C, F.X, F.NB)));
    Assert.assertEquals(F.TRUE, F.f.or(F.A, F.B, F.f.or(F.NX, F.B, F.X)));
    Assert.assertEquals(F.OR1, F.f.or(F.X, F.Y, F.f.and(F.NX, F.B, F.X)));
  }

  @Test
  public void testVariables() {
    Assert.assertEquals(2, F.OR2.variables().size());
    SortedSet<Literal> lits = new TreeSet<>(Arrays.asList(F.X, F.Y));
    Assert.assertEquals(lits, F.OR2.variables());

    Formula or = F.f.or(F.A, F.A, F.B, F.IMP3);
    Assert.assertEquals(4, or.variables().size());
    lits = new TreeSet<>(Arrays.asList(F.A, F.B, F.X, F.Y));
    Assert.assertEquals(lits, or.variables());
  }

  @Test
  public void testLiterals() {
    Assert.assertEquals(2, F.OR2.literals().size());
    SortedSet<Literal> lits = new TreeSet<>(Arrays.asList(F.NX, F.NY));
    Assert.assertEquals(lits, F.OR2.literals());

    Formula or = F.f.or(F.A, F.A, F.B, F.f.implication(F.NB, F.NA));
    Assert.assertEquals(4, or.literals().size());
    lits = new TreeSet<>(Arrays.asList(F.A, F.NA, F.B, F.NB));
    Assert.assertEquals(lits, or.literals());
  }

  @Test
  public void testToString() {
    Assert.assertEquals("x | y", F.OR1.toString());
    Assert.assertEquals("~x | ~y", F.OR2.toString());
    Assert.assertEquals("a & b | ~a & ~b", F.OR3.toString());
    Assert.assertEquals("a | b | ~x | ~y", F.f.or(F.A, F.B, F.NX, F.NY).toString());
    Assert.assertEquals("(a => b) | (~a => ~b)", F.f.or(F.IMP1, F.IMP2).toString());
    Assert.assertEquals("(a <=> b) | (~a <=> ~b)", F.f.or(F.EQ1, F.EQ2).toString());
  }

  @Test
  public void testEquals() {
    Assert.assertEquals(F.OR1, F.f.or(F.X, F.Y));
    Assert.assertEquals(F.OR3, F.f.or(F.AND1, F.AND2));
    Assert.assertEquals(F.OR2, F.OR2);
    Assert.assertEquals(F.f.or(F.A, F.NB, F.AND1, F.NX), F.f.or(F.NX, F.A, F.NB, F.AND1));
    Assert.assertNotEquals(F.OR1, null);
    Assert.assertNotEquals(F.OR1, F.A);
    Assert.assertNotEquals(F.OR1, F.OR2);
    Assert.assertNotEquals(F.OR1, F.f.or(F.A, F.B, F.C));
  }

  @Test
  public void testHash() {
    Formula or = F.f.or(F.AND1, F.AND2);
    Assert.assertEquals(F.OR3.hashCode(), or.hashCode());
    Assert.assertEquals(F.OR3.hashCode(), or.hashCode());
    Assert.assertEquals(F.OR2.hashCode(), F.f.or(F.NX, F.NY).hashCode());
  }

  @Test
  public void testNumberOfAtoms() {
    Assert.assertEquals(2, F.OR1.numberOfAtoms());
    Assert.assertEquals(2, F.OR2.numberOfAtoms());
    Assert.assertEquals(4, F.OR3.numberOfAtoms());
  }
}