package ejercicioParcialAutobus;

public class Pasajero implements Runnable {

    private Integer id;
    private Estacion estacion;

    public Pasajero(Integer id, Estacion estacion) {
        this.id = id;
        this.estacion = estacion;
    }

    @Override
    public void run() {
        estacion.solicitarSubirse(id);

    }

}
