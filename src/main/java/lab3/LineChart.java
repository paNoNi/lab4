package lab3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

class DrawPanel extends JPanel {


    private TspSolution points;

    double maxX;
    double maxY;


    public DrawPanel(double maxX, double maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void addPoints(TspSolution points) {
        this.repaint();
        this.points = points;
    }

    private void doDrawing(Graphics g) {

        Dimension size = this.getSize();
        int borderW = (int) (size.getWidth() * .1);
        int borderH = (int) (size.getHeight() * .1);
        Graphics2D g2d = (Graphics2D) g;
        float[] dash4 = {4f, 4f, 1f};
        g2d.setStroke(new BasicStroke(2));
        for (int i = 0; i < points.cities.size(); i++) {
            Point firstCity = points.cities.get(i);
            Point secondCity = points.cities.get((i + 1) % points.cities.size());

            g2d.setColor(new Color(0, 0, 255));
            g2d.drawLine(
                    (int) (borderW + ((firstCity.x / this.maxX) * (size.getWidth() * 0.7))),
                    (int) (borderH + ((firstCity.y / this.maxY) * (size.getHeight() * 0.7))),
                    (int) (borderW + ((secondCity.x / this.maxX) * (size.getWidth() * 0.7))),
                    (int) (borderH + ((secondCity.y / this.maxY) * (size.getHeight() * 0.7)))
            );

            int d = (int) (size.getWidth() * .005);

            g2d.setColor(new Color(0, 125, 125));
            g2d.drawOval(
                    (int) (borderW + (firstCity.x / this.maxX) * size.getWidth() * 0.7 - d / 2),
                    (int) (borderH + (firstCity.y / this.maxY) * size.getHeight() * 0.7 - d / 2),
                    d, d);
        }
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, (int) (size.getWidth() * .03)));
        g2d.drawString("Current distance:" + Math.round(this.points.getDistance()),
                (int) Math.ceil(this.getSize().getWidth() * 0.07),
                (int) Math.ceil(this.getSize().getHeight() * 0.07));

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}

public class LineChart extends JFrame {

    private DrawPanel graphic = null;

    public void initUI() {

        setTitle("Line chart");
//        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(720, 480));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updateData(TspSolution points) {

        if (this.graphic == null) {
            double maxX = -1.;
            double maxY = -1.;
            for (int i = 0; i < points.cities.size(); i++) {
                if (points.cities.get(i).getX() > maxX &&
                        points.cities.get(i).getY() > maxY ||
                        (maxX == -1.|| maxY == -1.)) {
                    maxX = points.cities.get(i).getX();
                    maxY = points.cities.get(i).getY();
                }
            }
            this.graphic = new DrawPanel(maxX, maxY);
            graphic.addPoints(points);
            add(graphic);
            pack();
        } else {
            ((DrawPanel) this.getContentPane().findComponentAt(0, 0)).addPoints(points);
            repaint();
        }

    }


}

