package ejercicioParcialAutobus;

public class Autobus implements Runnable {
    private Integer asientosDisponibles;
    private Integer id;
    private Estacion estacion;

    public Autobus(Integer asientosDisponibles, Integer id, Estacion estacion) {
        this.id = id;
        this.asientosDisponibles = asientosDisponibles;
        this.estacion = estacion;
    }

    @Override
    public void run() {
        estacion.autobusLlega(id, asientosDisponibles);

        estacion.autobusSeVa(id, asientosDisponibles);

    }

}
