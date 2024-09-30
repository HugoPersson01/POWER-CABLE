package se.kth.adamfhhugoper.labb3.Shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class Circle extends FillableShape{

    private double diameter;

    public Circle(double x, double y, double diameter, Color color) {
        super(x, y, true,color);
        this.diameter = diameter;
    }

    public Circle(){
        super();
        this.diameter = 10;
    }

    public double getDiameter() {
        return diameter;
    }


    public void setDiameter(double diameter) {
        if (diameter < 0) diameter = 0;
        this.diameter = diameter;
    }



    @Override
    public void paint(GraphicsContext gc) {
        if (isFilled() != false) {
            gc.setFill(getColor());
            gc.fillOval(getX(), getY(), (diameter), (diameter));
        } else {
            gc.setStroke(getColor());
            gc.strokeOval(getX(), getY(), (diameter), (diameter));
        }
    }

    public void move(long elapsedTimeNs) {
        super.move(elapsedTimeNs);
    }





    @Override
    public void constrain(double boxX, double boxY, double boxWidth, double boxHeight){
        super.constrain(boxX,boxY,boxWidth,boxHeight);

        if (getX() > boxX){
            this.setVelocity(getDx(),getDy());
        } else if (getX() < boxX) {
            this.setVelocity(-getDx(),getDy());
        }
        if (getX()+diameter < boxWidth){
            this.setVelocity(getDx(),getDy());
        } else if(getX()+diameter > boxWidth){
            this.setVelocity(-getDx(),getDy());
        }

        if (getY() < boxY){
            this.setVelocity(getDx(),getDy());
        } else if (getY() > boxY) {
            this.setVelocity(getDx(),-getDy());
        }
        if (getY()+diameter < boxHeight){
            this.setVelocity(getDx(),getDy());
        } else if(getX()+diameter > boxHeight){
            this.setVelocity(getDx(),-getDy());
        }




/*
        if (getX() > boxX || (getX()+diameter) < boxWidth){
            this.setVelocity(getDx(),getDy());
        } else if(getX() < boxX || (getX()+diameter) > boxWidth){
            this.sbetVelocity(-getDx(),getDy());
        }

        if (getY() > boxY || (getY()+diameter) < boxHeight){
            this.setVelocity(getDx(),getDy());
        } else if (getY() < boxY || (getY()+diameter) > boxHeight){
            this.setVelocity(getDx(),-getDy());
        }

 */

    }






    @Override
    public String toString() {
        return  super.toString()+
                "diameter = " + diameter;
    }
}
