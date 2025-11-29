package ejercicioGuiaMoni5;

public class Peluquero implements Runnable {
    private Peluqueria peluqueria;
    private int id;

    public Peluquero(Peluqueria peluqueria, int id) {
        this.peluqueria = peluqueria;
        this.id = id;
    }

    @Override
    public void run() {
        while (true) {
            peluqueria.empezarCorte(id);
            // cortar
            peluqueria.terminarCorte(id);
        }
    }

}
