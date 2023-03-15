package lab3;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class TspFactory extends AbstractCandidateFactory<TspSolution> {

    String pathToFile;

    public TspFactory(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public TspSolution generateRandomCandidate(Random random) {
        TspSolution solution = new TspSolution();
        solution.readTspFile(this.pathToFile);
        solution.shufflePoints();
        return solution;
    }
}

