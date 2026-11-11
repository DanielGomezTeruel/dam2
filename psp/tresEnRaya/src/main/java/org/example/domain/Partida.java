package org.example.domain;

public class Partida {

    private int id = 0;
    private String jugador1;
    private String jugador2;
    private String marca1;
    private String marca2;

    private String turno;

    private int numMovimientos;

    private String ganador;

    private String[] tablero;

    private boolean finalizada;

    public Partida(String jugador1, String jugador2, String marca1, String marca2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.marca1 = marca1;
        this.marca2 = marca2;
        turno = Math.random()>0.5?jugador1:jugador2;
        numMovimientos = 0;
        tablero = new String[]{"-", "-", "-", "-", "-", "-", "-", "-", "-"};
        finalizada = false;
    }

    public String getJugador1() {
        return jugador1;
    }

    public void setJugador1(String jugador1) {
        this.jugador1 = jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public void setJugador2(String jugador2) {
        this.jugador2 = jugador2;
    }

    public String getMarca1() {
        return marca1;
    }

    public void setMarca1(String marca1) {
        this.marca1 = marca1;
    }

    public String getMarca2() {
        return marca2;
    }

    public void setMarca2(String marca2) {
        this.marca2 = marca2;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getNumMovimientos() {
        return numMovimientos;
    }

    public void setNumMovimientos(int numMovimientos) {
        this.numMovimientos = numMovimientos;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public String[] getTablero() {
        return tablero;
    }

    public void setTablero(String[] tablero) {
        this.tablero = tablero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }

    public void addMovimiento() {
        numMovimientos++;
    }

}
