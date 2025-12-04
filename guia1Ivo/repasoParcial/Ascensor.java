package repasoParcial;

public class Ascensor implements Runnable {
    private Edificio edificio;

    public Ascensor(Edificio edificio) {
        this.edificio = edificio;
    }

    @Override
    public void run() {
        while (true) {
            edificio.subirPersonas();
            edificio.moverse();
            edificio.bajarPersonas();

        }

    }
}
