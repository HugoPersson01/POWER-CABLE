package se.kth.adamfhhugoper.labb3.Shapes;

import javafx.scene.paint.Color;

abstract class FillableShape extends Shape{

    private boolean filled;

    protected FillableShape(double x, double y, boolean filled, Color color) {
        super(x, y, color);
        this.filled = filled;
    }

    protected FillableShape() {
        super();
        this.filled = false;
    }

    public boolean isFilled() { // getter
        return filled;
    }

    public void setFilled(boolean filled) { // setter
        this.filled = filled;
    }











}
