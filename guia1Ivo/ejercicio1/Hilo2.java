package ejercicio1;

class Hilo2 implements Runnable {

    private Ejercicio1 ejercicio1;

    public Hilo2(Ejercicio1 ejercicio1) {
        this.ejercicio1 = ejercicio1;
    }

    @Override
    public void run() {
        System.out.println("E");
        ejercicio1.downSemaforo(2);
        System.out.println("F");
        ejercicio1.upSemaforo(1);
        System.out.println("G");
    }
}