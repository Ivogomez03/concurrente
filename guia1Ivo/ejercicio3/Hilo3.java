package ejercicio3;

class Hilo3 implements Runnable {

    private Ejercicio3 ejercicio3;

    public Hilo3(Ejercicio3 ejercicio3) {
        this.ejercicio3 = ejercicio3;
    }

    @Override
    public void run() {
        ejercicio3.downSemaforo(3);
        System.out.print("O ");
        ejercicio3.upSemaforo(1);
        ejercicio3.upSemaforo(2);
        System.out.print("OK ");

    }
}