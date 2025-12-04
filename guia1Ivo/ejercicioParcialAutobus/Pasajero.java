public class Pasajero implements Runnable {

    private EstacionAutobus estacion;
    private int id;

    public Pasajero(EstacionAutobus estacion, int id) {
        this.id = id;
        this.estacion = estacion;
    }

    @Override
    public void run() {
        estacion.subirAlAutobus(id);

    }

}
