package br.gov.ma.detran.examespraticosmobile.modeloEspecializada;

import android.graphics.Path;

public class CoordenadasFaltaCroqui {
    private float x;
    private float y;
    private String itemAlinea;
    private FingerPath path;

    public CoordenadasFaltaCroqui(float x, float y, String itemAlinea, FingerPath path){
        this.x = x;
        this.y = y;
        this.itemAlinea = itemAlinea;
        this.path = path;
    }

    public float getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getItemAlinea() {
        return itemAlinea;
    }

    public void setItemAlinea(String itemAlinea) {
        this.itemAlinea = itemAlinea;
    }

    public Path getPath() {
        return path.getPath();
    }

    public void setPath(FingerPath path) {
        this.path = path;
    }

}
