package lab3;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TspMutation implements EvolutionaryOperator<TspSolution> {

    int mutCount = 1;

    public TspMutation(int mutCount) {
        this.mutCount = mutCount;
    }

    public List<TspSolution> apply(List<TspSolution> population, Random random) {
        int citiesCount = population.get(0).cities.size();
        for (TspSolution tspSolution : population) {
            for (int i = 0; i < mutCount; i++) {
                int firstPos = random.nextInt(citiesCount);
                int secondPos = random.nextInt(citiesCount);
                Collections.swap(tspSolution.cities, firstPos, secondPos);
            }
        }
        return population;
    }
}
