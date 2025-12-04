package ejercicioParcialMonos;

public class Monkey implements Runnable {
    private Cuerda cuerda;
    private int id;
    private int destino;

    public Monkey(Cuerda cuerda, int id, int destino) {
        this.cuerda = cuerda;
        this.id = id;
        this.destino = destino;
    }

    @Override
    public void run() {
        cuerda.cruzarCuerda(destino, id);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cuerda.llegarAdestino(destino, id);
    }

}