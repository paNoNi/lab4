package lab3;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TspSolution implements Cloneable {

    List<Point> cities;

    public TspSolution() {}
    public TspSolution(List<Point> cities) {
        this.cities = cities;
    }

    public void readTspFile(String pathToFile) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
            int n = 0; // количество городов
            List<Point> cities = new ArrayList<Point>(); // список координат городов
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("DIMENSION")) {
                    n = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("NODE_COORD_SECTION")) {
                    for (int i = 0; i < n; i++) {
                        line = reader.readLine();
                        String[] parts = line.trim().split("\\s+");
                        Point city = new Point(Integer.parseInt(parts[1]), Integer.parseInt((parts[2])));
                        cities.add(city);
                    }
                }
                line = reader.readLine();
            }

            this.cities = cities;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shufflePoints() {
        Collections.shuffle(cities);
    }

    public Double getDistance() {
        double distance = 0;
        for (int i = 1; i < cities.size(); i++) {
            distance += cities.get(i - 1).distance(cities.get(i));
        }
        distance += cities.get(cities.size() - 1).distance(cities.get(0));
        return distance;
    }

    public String toString() {
        return getDistance().toString();
    }

    @Override
    public TspSolution clone() {
        try {
            return (TspSolution) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
