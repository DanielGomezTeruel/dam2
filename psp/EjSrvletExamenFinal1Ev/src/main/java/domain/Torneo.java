package domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Torneo {
    private String nombreBiblioteca;
    private boolean finalizado = false;
    private List<String> jugadores;
    private List<Partida> partidas;

    public Torneo(String nombreBiblioteca) {
        this.nombreBiblioteca = nombreBiblioteca;
        jugadores = new ArrayList<>();
        partidas = new ArrayList<>();
    }

    public String getNombreBiblioteca() {
        return nombreBiblioteca;
    }

    public void setNombreBiblioteca(String nombreBiblioteca) {
        this.nombreBiblioteca = nombreBiblioteca;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public List<String> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<String> jugadores) {
        this.jugadores = jugadores;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public void addPartida(Partida partida) {
        if (partidas.isEmpty()) {
            partida.setId(1);
        } else {
            partida.setId(partidas.getLast().getId() + 1);
        }
        partidas.add(partida);
    }

    public void addJugador(String nombre) {
        if (jugadores.stream().filter(jugador -> jugador.equals(nombre)).toList().isEmpty()) {
            jugadores.add(nombre);
        }
    }

    public void deletePlayer(String nombre) {
        jugadores = jugadores.stream().filter(jugador -> !jugador.equals(nombre)).collect(Collectors.toCollection(ArrayList::new));
        partidas = partidas.stream().filter(partida -> !(partida.getJugador1().equals(nombre) || partida.getJugador2().equals(nombre))).collect(Collectors.toCollection(ArrayList::new));
    }

    public Partida getLatestGame() {
        if (!partidas.isEmpty()) {
            return partidas.getLast();
        } else {
            return null;
        }
    }

    public int getWins(String player) {
        int winCount = 0;
        List<Partida> wins = partidas.stream().filter(partida -> partida.getGanador() != null && partida.getGanador().equals(player)).toList();
        if (!wins.isEmpty()) {
            winCount = wins.size();
        }
        return winCount;
    }

    public List<Partida> getPartidasByName(String player) {
        return partidas.stream().filter(partida -> (partida.getJugador1().equals(player) || partida.getJugador2().equals(player))).toList();
    }

    public List<String> getJugadoresByWins() {
        return jugadores.stream().sorted(Comparator.comparing(this::getWins)).toList().reversed();
    }
}
