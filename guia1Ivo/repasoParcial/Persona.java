package repasoParcial;

class Persona implements Runnable {

    private String nombre;
    private String piso;
    private Ascensor ascensor;

    public Persona(String nombre, Ascensor ascensor, String piso) {
        this.nombre = nombre;
        this.ascensor = ascensor;
        this.piso = piso;

    }

    @Override
    public void run() {
        ascensor.subirse(piso, nombre);
        ascensor.bajarse(nombre);
    }
}