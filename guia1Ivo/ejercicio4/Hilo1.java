package ejercicio4;

class Hilo1 implements Runnable {
    private Ejercicio4 ejercicio4;

    public Hilo1(Ejercicio4 ejercicio4) {
        this.ejercicio4 = ejercicio4;
    }

    @Override
    public void run() {

        do {
            System.out.print("A");
            ejercicio4.upSemaforo(2);
            System.out.print("B");
            ejercicio4.downSemaforo(1);
            System.out.print("C");
            System.out.print("D");

        } while (true);

    }

}