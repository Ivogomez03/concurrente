package ejercicioGuiaMoni9;

public class Persona implements Runnable {
    private Rio rio;
    private int costa;
    private int id;

    public Persona(Rio rio, int costa, int id) {
        this.id = id;
        this.costa = costa;
        this.rio = rio;
    }

    @Override
    public void run() {
        rio.abordarBote(costa, id);

    }

}
