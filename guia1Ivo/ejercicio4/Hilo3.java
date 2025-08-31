package ejercicio4;

class Hilo3 implements Runnable {

    private Ejercicio4 ejercicio4;

    public Hilo3(Ejercicio4 ejercicio4) {
        this.ejercicio4 = ejercicio4;
    }

    @Override
    public void run() {
        do {
            ejercicio4.downSemaforo(3);
            System.out.print("H");
            System.out.print("I");

        } while (true);

    }
}