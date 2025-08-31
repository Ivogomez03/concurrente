package ejercicio4;

class Hilo2 implements Runnable {

    private Ejercicio4 ejercicio4;

    public Hilo2(Ejercicio4 ejercicio4) {
        this.ejercicio4 = ejercicio4;
    }

    @Override
    public void run() {

        do {
            System.out.print("E");
            ejercicio4.upSemaforo(3);
            ejercicio4.downSemaforo(2);
            System.out.print("F");
            System.out.print("G");
            ejercicio4.upSemaforo(1);

        } while (true);
    }
}