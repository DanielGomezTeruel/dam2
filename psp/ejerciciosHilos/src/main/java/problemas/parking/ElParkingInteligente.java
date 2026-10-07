package problemas.parking;

public class ElParkingInteligente {
    public static void main(String[] args) {
        Parking parking = new Parking();

        parking.crearCoches();

        parking.esperarFinal();

        parking.debug();
    }
}
