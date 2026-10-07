package problemas.restaurante;

public class Pedido {

    private final String infoCliente;
    private final TipoPlato tipoPlato;

    public Pedido(String infoCliente, TipoPlato tipoPlato) {
        this.infoCliente = infoCliente;
        this.tipoPlato = tipoPlato;
    }

    public String getInfoCliente() {
        return infoCliente;
    }

    public TipoPlato getTipoPlato() {
        return tipoPlato;
    }
}
