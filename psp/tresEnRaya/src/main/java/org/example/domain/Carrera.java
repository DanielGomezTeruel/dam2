package org.example.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carrera {
    private final Random dado = new Random();
    private List<Corredor> corredores;
    private int longitud;
    private boolean completada;


    public Carrera(int longitud) {
        this.longitud = longitud;
        corredores = new ArrayList<>();
        completada = false;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public List<Corredor> getCorredores() {
        return corredores;
    }

    public void setCorredores(List<Corredor> corredores) {
        this.corredores = corredores;
    }

    public boolean addCorredor(Corredor corredor) {
        boolean added = false;
        if (!corredorExists(corredor.getNombre())) {
            corredores.add(corredor);
            added = true;
        }
        return added;
    }

    public boolean corredorExists(String nombre) {
        return !corredores.stream()
                .filter(c -> c.getNombre().equals(nombre))
                .toList().isEmpty();
    }

    public Corredor getCorredorByName(String nombre) {
        return corredores.stream()
                .filter(c -> c.getNombre().equals(nombre))
                .toList().getFirst();
    }

    public String moveCorredor(Corredor corredor) {
        int casillas = dado.nextInt(1, 6);
        corredor.modPosicion(casillas);
        return corredor.getNombre() + " avanza " + casillas + " casillas";
    }

    public void checkTurno() {
        if (corredores.stream()
                .filter(corredor -> !corredor.isMovedThisTurn())
                .toList().isEmpty()
        ) {
            corredores.forEach(corredor -> corredor.setMovedThisTurn(false));
        }
    }

    public String getGanador() {
        return corredores.stream()
                .filter(corredor -> corredor.getPosicion() >= longitud)
                .toList().getFirst().getNombre();
    }

}
