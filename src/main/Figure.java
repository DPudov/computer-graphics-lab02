package main;

class Figure implements Scalable, Movable, Rotatable, Drawable {
    private Triangle triangle;
    private Cardioid cardioid;

    Figure(Figure another) {
        this.triangle = new Triangle(another.triangle);
        this.cardioid = new Cardioid(another.cardioid);
    }

    Figure(Triangle triangle, Cardioid cardioid) {
        this.triangle = triangle;
        this.cardioid = cardioid;
    }

    void setFigure(Figure figure) {
        this.triangle = new Triangle(figure.triangle);
        this.cardioid = new Cardioid(figure.cardioid);
    }

    void move(double dx, double dy) {
        triangle.move(dx, dy);
        cardioid.move(dx, dy);
    }

    void scale(Point center, double kx, double ky) {
        triangle.scale(center, kx, ky);
        cardioid.scale(center, kx, ky);
    }

    void rotate(Point center, double theta) {
        triangle.rotate(center, theta);
        cardioid.rotate(center, theta);
    }

    Triangle getTriangle() {
        return triangle;
    }

    void setTriangle(Triangle triangle) {
        this.triangle = triangle;
    }

    Cardioid getCardioid() {
        return cardioid;
    }

    void setCardioid(Cardioid cardioid) {
        this.cardioid = cardioid;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "triangle=" + triangle +
                ", cardioid=" + cardioid +
                '}';
    }
}
