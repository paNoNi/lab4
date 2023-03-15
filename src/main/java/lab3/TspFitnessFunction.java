package lab3;

import org.uncommons.watchmaker.framework.FitnessEvaluator;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TspFitnessFunction implements FitnessEvaluator<TspSolution> {

    double min_distance = 10e30;

    public TspFitnessFunction(String problem) {
    }

    public double getFitness(TspSolution solution, List<? extends TspSolution> list) {

        if (solution.getDistance() < min_distance) {
            min_distance = solution.getDistance();
        }
        return solution.getDistance();
    }

    public boolean isNatural() {
        return false;
    }
}
