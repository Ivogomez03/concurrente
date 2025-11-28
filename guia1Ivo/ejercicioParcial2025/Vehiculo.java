
public class Vehiculo implements Runnable {
    private int tipoDeVehiculo; // 0 para estatal, 1 para particular.
    private int id;
    private int estacion;
    private Planta planta;

    public Vehiculo(int tipoDeVehiculo, int id, Planta planta) {
        this.tipoDeVehiculo = tipoDeVehiculo;
        this.id = id;

        this.planta = planta;
    }

    @Override
    public void run() {
        planta.irAdministracion(this);
        planta.irEstacion(this, estacion);

    }

    public int getId() {
        return this.id;
    }

    public int getTipoDeVehiculo() {
        return this.tipoDeVehiculo;
    }

    public void asignarEstacion(int estacion) {
        this.estacion = estacion;
    }

}
