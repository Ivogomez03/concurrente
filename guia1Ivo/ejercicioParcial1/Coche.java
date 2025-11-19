package ejercicioParcial1;

public class Coche implements Runnable {
    private Integer id;
    private Integer sentido; // 0 si va de A a B, 1 si va de B a A;
    private Puente puente;

    public Coche(Integer id, Integer sentido, Puente puente) {
        this.id = id;
        this.sentido = sentido;
        this.puente = puente;
    }

    @Override
    public void run() {
        puente.solicitarPaso(sentido, id);
        try {
            Thread.sleep(Ejercicio.tiempoRandom(500, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        puente.termineDeCruzar(sentido, id);

    }
}
