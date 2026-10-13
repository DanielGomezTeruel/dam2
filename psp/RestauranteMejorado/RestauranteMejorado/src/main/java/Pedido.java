import java.util.concurrent.CountDownLatch;

public class Pedido {

    private final String infoCliente;
    private final TipoPlato tipoPlato;
    private final CountDownLatch listo;

    public Pedido(String infoCliente, TipoPlato tipoPlato, CountDownLatch listo) {
        this.infoCliente = infoCliente;
        this.tipoPlato = tipoPlato;
        this.listo = listo;
    }

    public String getInfoCliente() {
        return infoCliente;
    }

    public TipoPlato getTipoPlato() {
        return tipoPlato;
    }

    public void listo(){
        listo.countDown();
    }
}
