
public class Vehiculo implements Runnable {
    private int tipoDeVehiculo; // 0 para estatal, 1 para particular.
    private int id;
    private int estacion;
    private Administracion administracion;
    private Estacion estacionObjeto;

    public Vehiculo(int tipoDeVehiculo, int id, Administracion administracion) {
        this.tipoDeVehiculo = tipoDeVehiculo;
        this.id = id;
        this.administracion = administracion;
    }

    public void irAdministracion() {
        administracion.pushearVehiculo(this);
    }

    public void irEstacionAsignada() {
        estacionObjeto.pushearVehiculo(this);
    }

    @Override
    public void run() {
        irAdministracion();
        irEstacionAsignada();

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

    public void asignarEstacionObjeto(Estacion estacionObjeto) {
        this.estacionObjeto = estacionObjeto;
    }

    public int getEstacion() {
        return this.estacion;
    }

}
