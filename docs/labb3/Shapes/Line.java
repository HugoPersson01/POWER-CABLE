package se.kth.adamfhhugoper.labb3.Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line extends Shape {

    private double x2, y2;

    public Line(double x, double y, double x2, double y2, Color color) {
        super(x, y, color);
        this.x2 = x2;
        this.y2 = y2;

    }
    public Line() {
        super();
        this.x2 = 100;
        this.y2 = 100;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        // TODO: implementera inte negativa värden, EXCEPTION?
        if (x2 < 0) x2 = 0;
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        // TODO: implementera inte negativa värden
        if (y2 < 0) y2 = 0;
        this.y2 = y2;
    }


    @Override
    public void move(long elapsedTimeNs) {
        super.move(elapsedTimeNs);

        x2 += getDx() * elapsedTimeNs / BILLION;
        y2 += getDy() * elapsedTimeNs / BILLION;
    }

    @Override
    protected void constrain(double boxX, double boxY, double boxWidth, double boxHeight) {
        super.constrain(boxX, boxY, boxWidth, boxHeight);

        // TODO: MOVE AND CONSTRAIN,

        if (x2 < boxX || getX() < boxX) {
            this.setVelocity(getDx(),getDy());
        } else if (x2 > boxWidth) {
            this.setVelocity((-getDx()),getDy());
        }
        if (y2 < boxY || getY() < boxY) {
            this.setVelocity(getDx(),getDy());
        } else if (y2 > boxHeight) {
            this.setVelocity(getDx(),(-getDy()));
        }
    }

    @Override
    public void paint(GraphicsContext gc) {
        // TODO: Implement!
        gc.setStroke(getColor());
        gc.strokeLine(getX(), getY(), x2, y2);
    }

    @Override
    public String toString() {
        return super.toString() +
                ", x2: " + x2 +
                ", y2: " + y2;
    }
}
