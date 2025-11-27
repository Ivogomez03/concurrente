
/*
Ejercicio 1: Monitores

Una planta donde realizan la verificación vehicular, tiene 3 estaciones de trabajo enumeradas de 0 a 2, donde 
se inspeccionan de manera individual a cada vehículo.

Cada vehículo que llega a la planta, se dirige a la administración donde se le toma toda la info, paga 
y se le asigna la estación que coincide con la que tiene la menor cantidad de vehículos asignados en ese momento.

Una vez que el vehículo tiene asignada la estación, se dirige a la misma y espera a que lo llamen para ingresar 
a realizar la verificación.

Los vehículos de propiedad estatal, como patrulleros o camionetas de la APSV, también deben pasar la 
revisión vehicular y tienen prioridad de atención, por lo que, tanto al dirigirse a la administración como 
a la estación donde serán atendidos, son colocados al frente de la cola de espera. Si varios vehículos estatales 
se encuentran esperando, estos serán atendidos en orden de llegada y luego se continuará con la atención de los 
vehículos particulares.

Finalizada la verificación, la estación le entrega un comprobante que indica si pasó la revisión o no, 
y el vehículo se retira de la estación y la planta.

Al final del día la planta administración debe conocer cuántas verificaciones se realizaron y 
cuántas de ellas fueron aprobadas.

A) Modelar el problema usando monitores. B) Proponer una solución (sin desarrollar) que permita mejorar el 
número de vehículos verificados, suponiendo que cada vehículo consume el mismo tiempo durante la verificación.
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Administracion {
    private List<Estacion> estaciones = new ArrayList<>(3);
    private Lock lock;
    private Condition esperarParticular;
    private Condition esperarEstatal;
    private int cantidadEstatales;
    private int cantidadParticulares;
    private int verificaciones;
    private int aprobadas;

    public Administracion() {
        estaciones.add(new Estacion(0, this));
        estaciones.add(new Estacion(0, this));
        estaciones.add(new Estacion(0, this));

        this.lock = new ReentrantLock();
        this.esperarParticular = lock.newCondition();
        this.esperarEstatal = lock.newCondition();
        this.cantidadEstatales = 0;
        this.cantidadParticulares = 0;
        this.verificaciones = 0;
        this.aprobadas = 0;
    }

    public void incrementarVerificacion() {
        this.verificaciones++;
    }

    public void incrementarAprobadas() {
        this.aprobadas++;
    }

    public boolean revisionyAprobacion() {
        Random random = new Random();

        if (random.nextDouble() < 0.5) {
            return true;
        } else
            return false;
    }

    public List<Estacion> getEstaciones() {
        return this.estaciones;
    }

    public void tomarInfoyAsignarEstacion(Vehiculo vehiculo) throws InterruptedException {
        lock.lock();
        try {
            if (vehiculo.getTipoDeVehiculo() == 0) {
                while (cantidadEstatales > 0)
                    esperarEstatal.await();

                cantidadEstatales++;

                System.out.println("Tomando datos y asignando estacion");

                if (estaciones.get(0).getCantidadDeVehiculos() > estaciones.get(1).getCantidadDeVehiculos()) {
                    if (estaciones.get(2).getCantidadDeVehiculos() > estaciones.get(1).getCantidadDeVehiculos()) {

                        vehiculo.asignarEstacion(1);
                        vehiculo.asignarEstacionObjeto(estaciones.get(1));

                        estaciones.get(1).incrementarCantidadDeVehiculos();

                        System.out.println("Asignamos estacion 1 al vehiculo con id " + vehiculo.getId());
                    } else {

                        vehiculo.asignarEstacion(2);
                        vehiculo.asignarEstacionObjeto(estaciones.get(2));

                        estaciones.get(2).incrementarCantidadDeVehiculos();

                        System.out.println("Asignamos estacion 2 al vehiculo con id " + vehiculo.getId());
                    }
                } else {
                    if (estaciones.get(2).getCantidadDeVehiculos() > estaciones.get(0).getCantidadDeVehiculos()) {

                        vehiculo.asignarEstacion(0);
                        vehiculo.asignarEstacionObjeto(estaciones.get(0));

                        estaciones.get(0).incrementarCantidadDeVehiculos();

                        System.out.println("Asignamos estacion 0 al vehiculo con id " + vehiculo.getId());
                    } else {

                        vehiculo.asignarEstacion(2);
                        vehiculo.asignarEstacionObjeto(estaciones.get(2));

                        estaciones.get(2).incrementarCantidadDeVehiculos();

                        System.out.println("Asignamos estacion 2 al vehiculo con id " + vehiculo.getId());
                    }
                }

                if (cantidadEstatales > 0) {
                    cantidadEstatales--;
                    esperarEstatal.signalAll();

                    // levanto para que pasen a la admin y yo me voy a pasar a realizar
                    // verificacion

                } else {
                    cantidadEstatales--;
                    esperarParticular.signalAll();
                    // levanto para que pasen a la admin y yo me voy a pasar a realizar
                    // verificacion

                }

            } else { // si es particular
                while (cantidadEstatales > 0 || cantidadParticulares > 0)
                    esperarParticular.await();

                cantidadParticulares++;

                System.out.println("Tomando datos y asignando estacion");

                if (estaciones.get(0).getCantidadDeVehiculos() > estaciones.get(1).getCantidadDeVehiculos()) {
                    if (estaciones.get(2).getCantidadDeVehiculos() > estaciones.get(1).getCantidadDeVehiculos()) {

                        vehiculo.asignarEstacion(1);
                        vehiculo.asignarEstacionObjeto(estaciones.get(1));

                        estaciones.get(1).incrementarCantidadDeVehiculos();

                        System.out.println("Asignamos estacion 1 al vehiculo con id " + vehiculo.getId());
                    } else {

                        vehiculo.asignarEstacion(2);
                        vehiculo.asignarEstacionObjeto(estaciones.get(2));

                        estaciones.get(2).incrementarCantidadDeVehiculos();

                        System.out.println("Asignamos estacion 2 al vehiculo con id " + vehiculo.getId());
                    }
                } else {
                    if (estaciones.get(2).getCantidadDeVehiculos() > estaciones.get(0).getCantidadDeVehiculos()) {

                        vehiculo.asignarEstacion(0);
                        vehiculo.asignarEstacionObjeto(estaciones.get(0));

                        estaciones.get(0).incrementarCantidadDeVehiculos();

                        System.out.println("Asignamos estacion 0 al vehiculo con id " + vehiculo.getId());
                    } else {

                        vehiculo.asignarEstacion(2);
                        vehiculo.asignarEstacionObjeto(estaciones.get(2));

                        estaciones.get(2).incrementarCantidadDeVehiculos();

                        System.out.println("Asignamos estacion 2 al vehiculo con id " + vehiculo.getId());
                    }
                }

                if (cantidadEstatales > 0) {
                    cantidadParticulares--;
                    esperarEstatal.signalAll();

                    // levanto uno para que pase a la admin y yo me voy a pasar a realizar
                    // verificacion
                } else {
                    cantidadParticulares--;
                    esperarParticular.signalAll();
                    // levanto uno para que pase a la admin y yo me voy a pasar a realizar
                    // verificacion

                }
            }

        } finally {
            lock.unlock();
        }

    }

}
