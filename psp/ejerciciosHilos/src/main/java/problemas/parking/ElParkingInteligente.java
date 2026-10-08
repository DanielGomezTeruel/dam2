package problemas.parking;

public class ElParkingInteligente {
    static void main(String[] args) {
        Parking parking = new Parking();

        parking.crearCoches();

        parking.esperarFinal();

        parking.debug();
    }
}
