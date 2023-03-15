package lab3;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TspCrossover extends AbstractCrossover<TspSolution> {
    protected TspCrossover(int numberOfCrossoverPoints) {
        super(numberOfCrossoverPoints);
    }

    protected List<TspSolution> mate(TspSolution p1, TspSolution p2, int numberOfCrossoverPoints, Random random) {
        int randomPos = random.nextInt(p1.cities.size() - numberOfCrossoverPoints);
        List<TspSolution> children = new ArrayList<TspSolution>();
        children.add(getNewChild(p1, p2, randomPos, numberOfCrossoverPoints));
        children.add(getNewChild(p2, p1, randomPos, numberOfCrossoverPoints));

        return children;
    }


    private TspSolution getNewChild(TspSolution firstParent,
                                                   TspSolution secondParent,
                                                   int pos,
                                                   int numberOfCrossoverPoints) {
        List<Point> childCities = new ArrayList<Point>();
        List<Point> subListOfCities = firstParent.cities.subList(pos, pos + numberOfCrossoverPoints);
        int currentChildPos = 0;
        for (int i = 0; i < secondParent.cities.size(); i++) {
            int cityIndex = subListOfCities.indexOf(secondParent.cities.get(i));
            if (cityIndex == -1) {
                childCities.add(currentChildPos, secondParent.cities.get(i));
                currentChildPos += 1;
            }
        }
        childCities.addAll(pos, subListOfCities);
        return new TspSolution(childCities);
    }
}
