package ejercicio5;

class Hilo2 implements Runnable {

    private Ejercicio5 ejercicio5;

    public Hilo2(Ejercicio5 ejercicio5) {
        this.ejercicio5 = ejercicio5;
    }

    @Override
    public void run() {
        // Consigna A

        do {
            ejercicio5.downSemaforo(2);

            System.out.print("B");

            ejercicio5.upSemaforo(1);
        } while (true);

        // Consigna B
        // do {
        // ejercicio5.downSemaforo(2);
        // System.out.print("B");
        // ejercicio5.upSemaforo(1);
        // } while (true);
    }
}