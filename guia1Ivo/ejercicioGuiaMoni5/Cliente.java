package ejercicioGuiaMoni5;

public class Cliente implements Runnable {
    private Peluqueria peluqueria;
    private int peluqueroAsignado;

    public Cliente(Peluqueria peluqueria, int peluqueroAsignado) {
        this.peluqueria = peluqueria;
        this.peluqueroAsignado = peluqueroAsignado;
    }

    @Override
    public void run() {
        peluqueria.cortarseElPelo(peluqueroAsignado);

    }

}
