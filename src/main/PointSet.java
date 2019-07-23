package main;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class PointSet {
    private final ArrayList<Point> points;
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public PointSet() {
        points = new ArrayList<>();
    }

    public boolean contains(Point point) {
        for (Point p : points) {
            if (point.getWorldX() == p.getWorldX() && point.getWorldY() == p.getWorldY()) {
                return true;
            }
        }
        return false;
    }

    public Point get(int index) {
        return points.get(index);
    }

    void add(Point point) {
        points.add(point);
    }

    private void updateMinMax() {
        minX = Double.MAX_VALUE;
        maxX = Double.MIN_VALUE;
        minY = Double.MAX_VALUE;
        maxY = Double.MIN_VALUE;


        for (Point p : points) {
            double curX = p.getWorldX();
            double curY = p.getWorldY();
            if (curX < minX) {
                minX = curX;
            }

            if (curY < minY) {
                minY = curY;
            }

            if (curX > maxX) {
                maxX = curX;
            }

            if (curY > maxY) {
                maxY = curY;
            }
        }
    }

    public double getMinimumX() {
        updateMinMax();
        return minX;
    }

    public double getMinimumY() {
        updateMinMax();
        return minY;
    }

    public double getMaximumX() {
        updateMinMax();
        return maxX;
    }

    public double getMaximumY() {
        updateMinMax();
        return maxY;
    }

    public int indexOf(Point point) {
        int res = 0;
        for (int i = 0; i < points.size(); i++) {
            if (point.isEqualTo(points.get(i))) {
                return i;
            }
        }
        return res;
    }
}
