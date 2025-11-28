
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

public class Administracion implements Runnable {
    private Planta planta;

    public Administracion(Planta planta) {

        this.planta = planta;

    }

    @Override
    public void run() {
        while (true) {
            planta.asignarEstacion();
        }

    }

}
