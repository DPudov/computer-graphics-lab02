package main;

class PointSetContainer {
    private final PointSet pointSet;
    private static final PointSetContainer ourInstance = new PointSetContainer();

    static PointSetContainer getInstance() {
        return ourInstance;
    }

    private PointSetContainer() {
        pointSet = new PointSet();
    }

    PointSet getPointSet() {
        return pointSet;
    }

}
