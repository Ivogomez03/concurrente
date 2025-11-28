import java.util.Random;

public class Estacion implements Runnable {

    private int id;
    private Planta planta;

    public Estacion(Planta planta, int id) {
        this.id = id;
        this.planta = planta;
    }

    public boolean revisionyAprobacion() {
        Random random = new Random();

        if (random.nextDouble() < 0.5) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void run() {
        while (true) {
            planta.realizarVerificacion(id);
            boolean aprobada = revisionyAprobacion();
            planta.terminarVerificacion(aprobada, id);

        }

    }
}
