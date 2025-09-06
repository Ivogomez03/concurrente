package ejercicio5;

class Hilo1 implements Runnable {
    private Ejercicio5 ejercicio5;

    public Hilo1(Ejercicio5 ejercicio5) {
        this.ejercicio5 = ejercicio5;
    }

    @Override
    public void run() {

        do {

            ejercicio5.downSemaforo(1);

            System.out.print("A");

            ejercicio5.upSemaforo(2);

        } while (true);

        // Consigna B
        // do {

        // System.out.print("A");
        // ejercicio5.upSemaforo(2);
        // ejercicio5.downSemaforo(1);

        // } while (true);
    }

}