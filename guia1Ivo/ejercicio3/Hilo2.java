package ejercicio3;

class Hilo2 implements Runnable {

    private Ejercicio3 ejercicio3;

    public Hilo2(Ejercicio3 ejercicio3) {
        this.ejercicio3 = ejercicio3;
    }

    @Override
    public void run() {
        ejercicio3.downSemaforo(2);
        System.out.print("I ");
        ejercicio3.upSemaforo(3);
        ejercicio3.downSemaforo(2);
        System.out.print("OK ");
    }
}