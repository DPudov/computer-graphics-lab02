package main;

import javafx.scene.shape.Polygon;

class Cardioid implements Movable, Rotatable, Scalable, Drawable {
    private Point[] points;

    Cardioid(Cardioid cardioid) {
        Point[] ps = cardioid.points;
        this.points = new Point[ps.length];
        for (int i = 0; i < ps.length; i++) {
            this.points[i] = new Point(ps[i]);
        }
    }

    void setDefault(double radius, Point center, int count) {
        if (count > 0) {
            double xc = center.getWorldX();
            double yc = center.getWorldY();
            points = new Point[count];
            int i = 0;
            for (double theta = 0; theta < 360.0 && i < count; theta += 360.0 / count) {
                double rads = Math.toRadians(theta);
                double sin_theta = Math.sin(rads);
                double cos_theta = Math.cos(rads);
                double alpha = radius + radius * sin_theta;
                double x = xc + alpha * sin_theta;
                double y = yc + alpha * cos_theta;
                points[i] = new Point(x, y);
                i++;
            }
        }
    }

    Cardioid(double radius, Point center, int count) {
        if (count > 0) {
            double xc = center.getWorldX();
            double yc = center.getWorldY();
            points = new Point[count];
            int i = 0;
            for (double theta = 0; theta < 360.0 && i < count; theta += 360.0 / count) {
                double rads = Math.toRadians(theta);
                double sin_theta = Math.sin(rads);
                double cos_theta = Math.cos(rads);
                double alpha = radius + radius * sin_theta;
                double x = xc + alpha * sin_theta;
                double y = yc + alpha * cos_theta;
                points[i] = new Point(x, y);
                i++;
            }
        }
    }

    void setCardioid(Cardioid cardioid) {
        Point[] ps = cardioid.points;
        this.points = new Point[ps.length];
        for (int i = 0; i < ps.length; i++) {
            this.points[i] = new Point(ps[i]);
        }
    }

    void move(double dx, double dy) {
        Point[] array = new Point[points.length];
        System.arraycopy(points, 0, array, 0, points.length);
        for (Point p : array) {
            p.move(dx, dy);
        }
        points = array;
    }

    void rotate(Point center, double theta) {
        for (Point p : points) {
            p.rotate(center, theta);
        }
    }

    void scale(Point center, double kx, double ky) {
        for (Point p : points) {
            p.scale(center, kx, ky);
        }
    }

    Polygon getPolygon() {
        Polygon cardioid = new Polygon();
        for (Point p : points) {
            cardioid.getPoints().addAll(p.getWorldX(), p.getWorldY());
        }
        return cardioid;
    }
}
