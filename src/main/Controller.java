package main;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.Stack;

public class Controller {
    private final static String ERROR_NON_VALID_DATA = "Некорректный ввод!";
    private final static int DEFAULT_X1 = 100;
    private final static int DEFAULT_Y1 = 400;
    private final static int DEFAULT_X2 = 500;
    private final static int DEFAULT_Y2 = 400;
    private final static int DEFAULT_X3 = 300;
    private final static int DEFAULT_Y3 = 100;
    private final static int DEFAULT_RADIUS = 50;
    private final static int DEFAULT_CENTER_X = 275;
    private final static int DEFAULT_CENTER_Y = 275;
    private final static int DEFAULT_COUNT = 360;
    @FXML
    Canvas canvas;

    @FXML
    TextField dxLabel;
    @FXML
    TextField dyLabel;
    @FXML
    Button moveButton;

    @FXML
    TextField xcrField;
    @FXML
    TextField ycrField;
    @FXML
    TextField arField;
    @FXML
    Button rotateButton;

    @FXML
    TextField xcsField;
    @FXML
    TextField ycsField;
    @FXML
    TextField kxField;
    @FXML
    TextField kyField;
    @FXML
    Button scaleButton;

    @FXML
    Button rollbackButton;
    @FXML
    Button resetButton;
    @FXML
    Label cursorLabel;

    @FXML
    GridPane container;

    private void setup(Figure figure) {
        Polygon cardioid = figure.getCardioid().getPolygon();
        Polygon trianglePol = figure.getTriangle().getPolygon();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image hatch = createHatch();
        ImagePattern pattern = new ImagePattern(hatch, 0, 0, 10, 10, false);
        drawPolygon(trianglePol, gc, pattern);
        drawPolygon(cardioid, gc, Color.RED);
    }

    @FXML
    public void initialize() {
        Point p1 = new Point(DEFAULT_X1, DEFAULT_Y1);
        Point p2 = new Point(DEFAULT_X2, DEFAULT_Y2);
        Point p3 = new Point(DEFAULT_X3, DEFAULT_Y3);
        PointSet pointSet = PointSetContainer.getInstance().getPointSet();
        pointSet.add(p1);
        pointSet.add(p2);
        pointSet.add(p3);

        Triangle triangle = new Triangle(p1, p2, p3);
        Cardioid cardioid = new Cardioid(DEFAULT_RADIUS, new Point(DEFAULT_CENTER_X, DEFAULT_CENTER_Y), DEFAULT_COUNT);
        Figure figure = new Figure(triangle, cardioid);
        setup(figure);

        Stack<Figure> stack = new Stack<>();

        cursorLabel.setWrapText(true);
        canvas.setOnMouseMoved(mouseEvent -> {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) {
                cursorLabel.setText("Текущая позиция курсора:\n" + mouseEvent.getX() + "," + mouseEvent.getY());
            }
        });

        moveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) t -> {
            try {
                double dx = Double.parseDouble(dxLabel.getText());
                double dy = Double.parseDouble(dyLabel.getText());

                Figure f = new Figure(figure);
                stack.push(f);
                figure.move(dx, dy);
                setup(figure);
                updateRollback(stack);
            } catch (Exception e) {
                Platform.runLater(() -> {
                    showError(ERROR_NON_VALID_DATA);
                });
            }
        });

        rotateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) t -> {
            try {
                double xcr = Double.parseDouble(xcrField.getText());
                double ycr = Double.parseDouble(ycrField.getText());
                double ar = Double.parseDouble(arField.getText());
                Figure f = new Figure(figure);
                stack.push(f);
                figure.rotate(new Point(xcr, ycr), ar);
                setup(figure);
                updateRollback(stack);
            } catch (Exception e) {
                showError(ERROR_NON_VALID_DATA);
            }
        });

        scaleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) t -> {
            try {
                double xcs = Double.parseDouble(xcsField.getText());
                double ycs = Double.parseDouble(ycsField.getText());
                double kx = Double.parseDouble(kxField.getText());
                double ky = Double.parseDouble(kyField.getText());
                Figure f = new Figure(figure);
                stack.push(f);
                figure.scale(new Point(xcs, ycs), kx, ky);
                setup(figure);
                updateRollback(stack);
            } catch (Exception e) {
                showError(ERROR_NON_VALID_DATA);
            }
        });

        rollbackButton.setDisable(false);
        rollbackButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (!stack.empty()) {
                Figure last = stack.pop();
                figure.setFigure(last);
                triangle.setP1(figure.getTriangle().getP1());
                triangle.setP2(figure.getTriangle().getP2());
                triangle.setP3(figure.getTriangle().getP3());
                cardioid.setCardioid(figure.getCardioid());

                setup(figure);
                updateRollback(stack);
            }
        });

        resetButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            stack.removeAllElements();
            p1.setWorldX(100);
            p1.setWorldY(400);
            p2.setWorldX(500);
            p2.setWorldY(400);
            p3.setWorldX(300);
            p3.setWorldY(100);

            triangle.setP1(p1);
            triangle.setP2(p2);
            triangle.setP3(p3);

            cardioid.setDefault(DEFAULT_RADIUS, new Point(DEFAULT_CENTER_X, DEFAULT_CENTER_Y), DEFAULT_COUNT);
            figure.setTriangle(triangle);
            figure.setCardioid(cardioid);
            setup(figure);
            updateRollback(stack);
        });

    }

    private void updateRollback(Stack<Figure> stack) {
        if (stack.isEmpty()) {
            rollbackButton.setDisable(true);
        } else {
            rollbackButton.setDisable(false);
        }
    }

    private void drawPolygon(Polygon trianglePol, GraphicsContext gc, Paint pattern) {
        double[] axisTriangle = getComponents(trianglePol, true);
        double[] ordinatesTriangle = getComponents(trianglePol, false);
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(3);
        gc.setFill(pattern);
        gc.fillPolygon(axisTriangle, ordinatesTriangle, axisTriangle.length);
        gc.strokePolygon(axisTriangle, ordinatesTriangle, axisTriangle.length);
    }

    private double[] getComponents(Polygon polygon, boolean isAxis) {
        ObservableList<Double> list = polygon.getPoints();
        int count = list.size() / 2;
        double[] components = new double[count];
        int c = 0;
        for (int i = isAxis ? 0 : 1; i < list.size(); i += 2) {
            components[c] = list.get(i);
            c++;
        }
        return components;
    }

    private Image createHatch() {
        Line fw = new Line(-5, -5, 500, -5);
        fw.setStroke(Color.BLACK);
        fw.setStrokeWidth(0.5);
        Pane pane = new Pane(fw);
        pane.setPrefSize(30, 30);
        return pane.snapshot(null, null);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Произошла ошибка :(");
        alert.setHeaderText("ОШИБКА");
        alert.show();
    }
}
