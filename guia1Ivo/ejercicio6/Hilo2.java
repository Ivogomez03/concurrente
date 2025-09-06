package ejercicio6;

class Hilo2 implements Runnable {
    private Ejercicio6 ejercicio6;

    public Hilo2(Ejercicio6 ejercicio6) {
        this.ejercicio6 = ejercicio6;
    }

    @Override
    public void run() {

        while (true) {
            ejercicio6.downSemaforo(2);
            ejercicio6.sumarEnesimoImpar_N2();

            ejercicio6.upSemaforo(1);

        }

    }

}