package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Corredor {

    private String nombre;
    private int posicion;
    private List<Tirada> tiradas;
    private boolean movedThisTurn;

    public Corredor(String nombre) {
        this.nombre = nombre;
        posicion = 0;
        movedThisTurn = false;
        tiradas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public boolean isMovedThisTurn() {
        return movedThisTurn;
    }

    public void setMovedThisTurn(boolean movedThisTurn) {
        this.movedThisTurn = movedThisTurn;
    }

    public List<Tirada> getTiradas() {
        return tiradas;
    }

    public void setTiradas(List<Tirada> tiradas) {
        this.tiradas = tiradas;
    }

    public void modPosicion(int casillas) {
        posicion += casillas;
        if (tiradas.isEmpty()) {
            tiradas.add(new Tirada(1, casillas));
        } else {
            tiradas.add(new Tirada(tiradas.getLast().getId() + 1, casillas));
        }

        setMovedThisTurn(true);
    }

}
