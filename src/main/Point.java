package main;

import java.text.DecimalFormat;

public class Point implements Movable, Scalable, Rotatable, Drawable {
    private double worldX;
    private double worldY;

    Point(Point another) {
        this.worldX = another.worldX;
        this.worldY = another.worldY;
    }

    Point(double worldX, double worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
    }

    double getWorldX() {
        return worldX;
    }

    double getWorldY() {
        return worldY;
    }


    boolean isEqualTo(Point other) {
        return worldX == other.getWorldX() && worldY == other.getWorldY();
    }

    void setWorldX(double worldX) {
        this.worldX = worldX;
    }

    void setWorldY(double worldY) {
        this.worldY = worldY;
    }

    void move(double dx, double dy) {
        this.worldX += dx;
        this.worldY += dy;
    }

    void rotate(Point center, double theta) {
        double xc = center.getWorldX();
        double yc = center.getWorldY();
        double cos_theta = Math.cos(Math.toRadians(theta));
        double sin_theta = Math.sin(Math.toRadians(theta));
        move(-xc, -yc);
        double x = getWorldX();
        double y = getWorldY();
        setWorldX(x * cos_theta - y * sin_theta);
        setWorldY(y * cos_theta + x * sin_theta);
        move(xc, yc);
    }

    void scale(Point center, double kx, double ky) {
        double xc = center.getWorldX();
        double yc = center.getWorldY();
        setWorldX(xc + kx * (worldX - xc));
        setWorldY(ky * worldY + (1 - ky) * yc);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#0.000");
        return "( " + df.format(worldX) + " ; " + df.format(worldY) + " )";
    }
}
