package ejercicioGuiaMoni7;

public class Jugador implements Runnable {
    private int id;
    private int monto;
    private String palabra;
    private Juego juego;

    public Jugador(Juego juego, String palabra, int monto, int id) {
        this.id = id;
        this.juego = juego;
        this.palabra = palabra;
        this.monto = monto;

    }

    @Override
    public void run() {
        while (!juego.concluido()) {
            juego.apostar(palabra, monto, id);
        }

    }

    public int getId() {
        return this.id;
    }

}
