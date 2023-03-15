package lab3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TspAlg {

    public static void main(String[] args) {
        String problem = "XQF131"; // name of problem or path to input file

        int populationSize = 3000; // size of population
        int generations = 100000; // number of generations
        int mutCount = 1;
        int numberOfCrossoverPoints = 5;
        int runCount = 2;


        final int[] bestIter = new int[runCount];
        final double[] bestDistance = new double[runCount];

        for (int i = 0; i < runCount; i++) {

            Random random = new Random(); // random

            CandidateFactory<TspSolution> factory = new TspFactory(args[0]); // generation of solutions

            ArrayList<EvolutionaryOperator<TspSolution>> operators = new ArrayList<EvolutionaryOperator<TspSolution>>();
            operators.add(new TspCrossover(numberOfCrossoverPoints)); // Crossover
            operators.add(new TspMutation(mutCount)); // Mutation
            EvolutionPipeline<TspSolution> pipeline = new EvolutionPipeline<TspSolution>(operators);

            SelectionStrategy<Object> selection = new RouletteWheelSelection(); // Selection operator

            FitnessEvaluator<TspSolution> evaluator = new TspFitnessFunction(problem); // Fitness function

            EvolutionEngine<TspSolution> algorithm = new SteadyStateEvolutionEngine<TspSolution>(
                    factory, pipeline, evaluator, selection, populationSize, false, random);

            final LineChart ex = new LineChart();
            ex.initUI();
            ex.setVisible(true);

            final int finalI = i;
            algorithm.addEvolutionObserver(new EvolutionObserver() {
                public void populationUpdate(PopulationData populationData) {
                    TspSolution best = (TspSolution)populationData.getBestCandidate();
                    System.out.println("\tGen:"+ populationData.getGenerationNumber() + " Best solution = " + best.toString());
                    ex.updateData(best);
                    if (best.getDistance() < bestDistance[finalI] || bestDistance[finalI] == 0.0) {
                        bestDistance[finalI] = best.getDistance();
                        bestIter[finalI] = populationData.getGenerationNumber();
                    }
                }
            });

            TerminationCondition terminate = new GenerationCount(generations);
            algorithm.evolve(populationSize, 300, terminate);
        }

        double meanBestDistance = 0;
        double meanBestGen = 0;

        for (int i = 0; i < runCount; i++) {
            meanBestDistance += bestDistance[i];
            meanBestGen += bestIter[i];
        }

        meanBestGen /= runCount;
        meanBestDistance /= runCount;

        System.out.println("Average of the best generations: " + meanBestGen +
                ", Average of the best distances: " + meanBestDistance);
    }

}
