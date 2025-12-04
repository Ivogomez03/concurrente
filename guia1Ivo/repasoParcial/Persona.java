package repasoParcial;

class Persona implements Runnable {

    private int id;
    private int piso; //
    private Edificio edificio;

    public Persona(int id, int piso, Edificio edificio) {
        this.edificio = edificio;
        this.id = id;
        this.piso = piso;

    }

    @Override
    public void run() {
        edificio.subir(piso, id);

    }

}