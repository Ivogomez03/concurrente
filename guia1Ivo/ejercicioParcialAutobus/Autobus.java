public class Autobus implements Runnable {
    private EstacionAutobus estacion;

    public Autobus(EstacionAutobus estacion) {
        this.estacion = estacion;
    }

    @Override
    public void run() {

        estacion.autobusLlega();

    }

}
