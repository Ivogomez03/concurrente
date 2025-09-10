package ejercicio7;

class Persona implements Runnable {
    private final Ejercicio7 ejercicio7;

    public Persona(Ejercicio7 ejercicio7) {
        this.ejercicio7 = ejercicio7;

    }

    @Override
    public void run() {
        while (true) {
            ejercicio7.downSemaforo("Subir");
            ejercicio7.upSemaforo("Barco");
            ejercicio7.downSemaforo("Bajar");
            ejercicio7.upSemaforo("Barco");
        }

    }
}