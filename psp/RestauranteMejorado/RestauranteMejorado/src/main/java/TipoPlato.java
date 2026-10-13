enum TipoPlato {
    ENSALADA(2000, 5),    // 2 segundos
    PASTA(3000, 12),       // 3 segundos
    PIZZA(4000, 14),       // 4 segundos
    CARNE(5000, 22);       // 5 segundos

    private final int tiempoMs;
    private final long precio;

    TipoPlato(int tiempoMs, long precio) {
        this.tiempoMs = tiempoMs;
        this.precio = precio;
    }

    public int getTiempoMs() {
        return tiempoMs;
    }

    public long getPrecio(){return precio;}
}