package ejercicio5;

class Hilo1 implements Runnable {
    private Ejercicio5 ejercicio5;

    public Hilo1(Ejercicio5 ejercicio5) {
        this.ejercicio5 = ejercicio5;
    }

    @Override
    public void run() {
        /*
         * Consigna A
         * do {
         * if (ejercicio5.obtenerContador() == 1) {
         * ejercicio5.upSemaforo(2);
         * ejercicio5.restablecerContador();
         * ejercicio5.downSemaforo(1);
         * }
         * 
         * System.out.print("A");
         * 
         * ejercicio5.aumentarContador();
         * 
         * } while (true);
         */
        do {

            System.out.print("A");
            ejercicio5.upSemaforo(2);
            ejercicio5.downSemaforo(1);

        } while (true);
    }

}