package ejercicio7;

class Persona implements Runnable {
    private final Ejercicio7 ejercicio7;
    private Integer id;

    public Persona(Ejercicio7 ejercicio7, Integer id) {
        this.ejercicio7 = ejercicio7;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            ejercicio7.downSemaforo("Subir");
            ejercicio7.upSemaforo("Barco");
            ejercicio7.downSemaforo("Bajar");
        }

    }
}