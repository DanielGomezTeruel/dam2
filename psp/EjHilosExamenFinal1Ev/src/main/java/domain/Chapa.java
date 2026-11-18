package domain;

import java.util.Random;

public class Chapa {
    private int id;
    private int posicion;
    private boolean movido;


    public Chapa(int id) {
        this.id = id;
        posicion = 0;
        movido = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public boolean isMovido() {
        return movido;
    }

    public void setMovido(boolean movido) {
        this.movido = movido;
    }

    public int mover(Random r) {
        int movimiento = r.nextInt(1, 21);
        posicion += movimiento;
        return movimiento;
    }
}

