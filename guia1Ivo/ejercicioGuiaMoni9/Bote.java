package ejercicioGuiaMoni9;

public class Bote implements Runnable {
    private Rio rio;

    public Bote(Rio rio) {
        this.rio = rio;

    }

    @Override
    public void run() {
        while (true) {
            rio.atravesarRio();
        }
    }

}
