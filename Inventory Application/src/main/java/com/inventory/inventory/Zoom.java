package com.inventory.inventory;
import javafx.scene.transform.Scale;
public class Zoom {
    //x and y are static across all objects so zoom amount is consistent
    static double x = 1, y = 1;
    double zoomAmount;
    Scale scale;

    //zoom amount controls how much it zooms, 0.1 is equal to 10%.
    public Zoom(double zoomAmount){
        this.zoomAmount = zoomAmount;
    }

    public void zoomIn(){
        this.x += zoomAmount;
        this.y += zoomAmount;

    }
    public void zoomOut(){
        this.x -= zoomAmount;
        this.y -= zoomAmount;
    }

    /*
    Zoom z = new Zoom(.5);
    z.zoomIn();
    z.zoomOut();
    scene.getRoot().getTransforms().setAll(z.getScale());
    ^^is how you use
     */
    public Scale getScale(){
        scale = new Scale(x, y);
        scale.setPivotX(0);
        scale.setPivotY(0);
        return scale;
    }
}
