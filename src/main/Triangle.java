package main;

import javafx.scene.shape.Polygon;

class Triangle implements Movable, Rotatable, Scalable, Drawable {
    private Point p1;
    private Point p2;
    private Point p3;

    Triangle(Triangle triangle) {
        this.p1 = new Point(triangle.p1);
        this.p2 = new Point(triangle.p2);
        this.p3 = new Point(triangle.p3);
    }

    Point getP1() {
        return p1;
    }

    Point getP2() {
        return p2;
    }

    Point getP3() {
        return p3;
    }

    void setP1(Point p1) {
        this.p1 = p1;
    }

    void setP2(Point p2) {
        this.p2 = p2;
    }

    void setP3(Point p3) {
        this.p3 = p3;
    }

    Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    void move(double dx, double dy) {
        Point point1 = p1;
        Point point2 = p2;
        Point point3 = p3;
        point1.move(dx, dy);
        point2.move(dx, dy);
        point3.move(dx, dy);
        p1 = point1;
        p2 = point2;
        p3 = point3;
    }

    void rotate(Point center, double theta) {
        p1.rotate(center, theta);
        p2.rotate(center, theta);
        p3.rotate(center, theta);
    }

    void scale(Point center, double kx, double ky) {
        p1.scale(center, kx, ky);
        p2.scale(center, kx, ky);
        p3.scale(center, kx, ky);
    }

    Polygon getPolygon() {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(p1.getWorldX(), p1.getWorldY(),
                p2.getWorldX(), p2.getWorldY(),
                p3.getWorldX(), p3.getWorldY());

        return triangle;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "p1=" + p1 +
                ", p2=" + p2 +
                ", p3=" + p3 +
                '}';
    }

}
