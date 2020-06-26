package org.logicng.solvers.functions;

import static org.logicng.datastructures.Tristate.FALSE;
import static org.logicng.datastructures.Tristate.UNDEF;

import org.logicng.collections.LNGIntVector;
import org.logicng.datastructures.Tristate;
import org.logicng.formulas.Literal;
import org.logicng.solvers.MiniSat;
import org.logicng.solvers.sat.MiniSatStyleSolver;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * A solver function which returns all unit propagated literals on level 0 of the current
 * formula on the solver.  If the formula is UNSAT, {@code null} will be returned.
 * @version 2.0.0
 * @since 2.0.0
 */
public class UpZeroLiteralsFunction implements SolverFunction<SortedSet<Literal>> {

    @Override
    public SortedSet<Literal> apply(final MiniSat solver, final Consumer<Tristate> resultSetter) {
        if (solver.getResult() == UNDEF) {
            throw new IllegalStateException("Cannot get unit propagated literals on level 0 as long as the formula is not solved.  Call 'sat' first.");
        }
        if (solver.getResult() == FALSE) {
            return null;
        }
        final LNGIntVector literals = solver.underlyingSolver().upZeroLiterals();
        final SortedSet<Literal> upZeroLiterals = new TreeSet<>();
        for (int i = 0; i < literals.size(); ++i) {
            final int lit = literals.get(i);
            upZeroLiterals.add(solver.factory().literal(solver.underlyingSolver().nameForIdx(MiniSatStyleSolver.var(lit)), !MiniSatStyleSolver.sign(lit)));
        }
        return upZeroLiterals;
    }
}