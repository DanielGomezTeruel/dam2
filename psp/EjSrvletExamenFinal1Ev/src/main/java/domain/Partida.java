package domain;

import java.util.ArrayList;
import java.util.List;

public class Partida {

    private int id = 0;
    private String jugador1;
    private String jugador2;
    private int intentosJ1 = 0;
    private int intentosJ2 = 0;

    private String turno;

    private List<String> historialMovimientos;

    private String ganador;

    private String palabra;

    private String incognita;

    private boolean finalizada;

    private List<Character> letrasProbadas;

    public Partida(String jugador1, String jugador2, String palabra) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.palabra = palabra;

        turno = Math.random() > 0.5 ? jugador1 : jugador2;
        historialMovimientos = new ArrayList<>();
        letrasProbadas = new ArrayList<>();
        incognita = crearIncognita();
        finalizada = false;
    }

    private String crearIncognita() {
        StringBuilder sb = new StringBuilder();
        sb.repeat("_", palabra.length());
        return sb.toString();
    }

    public int getIntentosJ1() {
        return intentosJ1;
    }

    public void setIntentosJ1(int intentosJ1) {
        this.intentosJ1 = intentosJ1;
    }

    public int getIntentosJ2() {
        return intentosJ2;
    }

    public void setIntentosJ2(int intentosJ2) {
        this.intentosJ2 = intentosJ2;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public List<String> getHistorialMovimientos() {
        return historialMovimientos;
    }

    public void setHistorialMovimientos(List<String> historialMovimientos) {
        this.historialMovimientos = historialMovimientos;
    }

    public String getIncognita() {
        return incognita;
    }

    public void setIncognita(String incognita) {
        this.incognita = incognita;
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

    public List<Character> getLetrasProbadas() {
        return letrasProbadas;
    }

    public void setLetrasProbadas(List<Character> letrasProbadas) {
        this.letrasProbadas = letrasProbadas;
    }

    public void addMovimiento(String movimiento) {
        historialMovimientos.add(movimiento);
    }

    public void registrarIntento(String jugador, String guess) {
        if (jugador.equals(jugador1)) {
            intentosJ1++;
        } else {
            intentosJ2++;
        }
        historialMovimientos.add("Movimiento " + (historialMovimientos.size() + 1) + ":" + jugador + " ha probado " + guess);
    }

    public void letraAdivinada(char c, int i) {
        if (i == palabra.length()) {
            incognita = incognita.substring(0, i) + c;
        } else if (i != 0) {
            incognita = incognita.substring(0, i) + c + incognita.substring(i + 1);
        } else {
            incognita = c + incognita.substring(i + 1);
        }

    }

    public boolean letraProbada(char c) {
        if (!letrasProbadas.contains(c)) {
            letrasProbadas.add(c);
            return false;
        } else {
            return true;
        }
    }

    public void switchTurno() {
        if (turno.equals(jugador1)) {
            turno = jugador2;
        } else {
            turno = jugador1;
        }
    }
}
