package ejercicioGuiaMoni9;

public class Persona implements Runnable {
    private Integer id;
    private Integer costa; // costa 0 o costa 1
    private Viaje viaje;

    public Persona(Integer id, Integer costa, Viaje viaje) {
        this.id = id;
        this.costa = costa;
        this.viaje = viaje;
    }

    @Override
    public void run() {
        try {
            viaje.subir(costa, id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
