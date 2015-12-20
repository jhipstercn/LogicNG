[![Build Status](https://travis-ci.org/logic-ng/LogicNG.svg)](https://travis-ci.org/logic-ng/LogicNG) ![License](https://img.shields.io/badge/license-Apache%202-blue.svg)

![LogicNG](https://github.com/logic-ng/LogicNG/blob/master/doc/logo/logo_big.png)

## Introduction
LogicNG is a Java Library for creating, manipulating and solving Boolean and Pseudo-Boolean formulas. It includes 100% Java implementations of popular tools like MiniSAT, CleaneLing, Glucose, or OpenWBO.

Its main focus lies on memory-efficient data-structures for Boolean formulas and efficient algorithms for manipulating and solving them.
The library is designed to be used in industrial systems which have to manipulate and solve millions of formulas per day.

## Philosophy
The most important philosophy of the library is to avoid unnecessary object creation.  Therefore formulas can only be generated via formula factories.  A formula factory assures that a formula is only created once in memory.  If another instance of the same formula is created by the user, the already existing one is returned by the factory. This leads to a small memory footprint and fast execution of algorithms.  Formulas can cache the results of algorithms executed on them and since every formula is hold only once in memory it is assured that the same algorithm on the same formula is also executed only once.

Compared to other implementation of logic libraries on the JVM this is a huge memory and performance improvement.

## Release Plan
The current release is declared as 1.0 Release Candidate 1.  The API is considered stable but some testing and documentation has to be finished for a 1.0 version in January 2016.

## Getting Started
The following code creates the Boolean Formula *A and not (B or not C)* programatically:
```java
final FormulaFactory f = new FormulaFactory();
final Literal a = f.literal("a");
final Literal b = f.literal("b");
final Literal notC = f.literal("c", false);
final Formula formula = f.and(a, f.not(f.or(b, notC)));
```
Alternatively you can just parse the formula from a string:
```java
final FormulaFactory f = new FormulaFactory();
final PropositionalParser p = new PropositionalParser(p);
final Formula formula = p.parse("A & ~(B | ~C)");
```
Once you created the formula you can for example convert it to NNF or CNF or solve it with an instance of MiniSAT:
```java
final Formula nnf = formula.nnf();
final Formula cnf = formula.cnf();
final SATSolver miniSat = MiniSat.miniSat(f);
miniSat.add(formula);
final Tristate result = miniSat.sat();
```

## License & Commercial Support
The library is released under the Apache License and therefore is free to use in any private, educational, or commercial project.  Commercial support will be available soon.
