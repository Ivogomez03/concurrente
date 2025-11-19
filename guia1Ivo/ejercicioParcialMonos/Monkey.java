package ejercicioParcialMonos;

public class Monkey implements Runnable {
    private Integer id;
    private String destino;
    private Ravine ravine;

    public Monkey(Integer id, String destino, Ravine ravine) {
        this.id = id;
        this.destino = destino;
        this.ravine = ravine;
    }

    @Override
    public void run() {
        ravine.WaitUntilSafeToCross(destino, id);
        ravine.CrossRavine(id, destino);
        try {
            Thread.sleep(Ejercicio.tiempoRandom(1000, 3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ravine.DoneWithCrossing(destino, id);

    }

}
