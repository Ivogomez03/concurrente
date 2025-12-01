package ejercicioGuiaMoni9;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
Considerar el siguiente problema: Se desea modelar un bote utilizado por personas para cruzar un rıo.
En todo momento el bote se encuentra en una de las dos costas del rıo (norte o sur). Las personas que
llegan a una de las costas pueden abordar el bote si el mismo se encuentra en esa orilla y aun tiene
lugar disponible (el bote tiene una capacidad fija que se define al momento de construccion). El bote
puede atravesar el rıo solo cuando tiene una autorizacion y cuando esta completo. Cuando llega a la
orilla, descarga a todas las personas y carga a las que estan esperando. Cuando se llene y vuelva a tener
autorizacion, volvera a cruzar el rıo en direccion opuesta. Notar que la autorizacion puede llegar antes
o despues de que este completo.
a) Modelar el problema utilizando monitores
b) Su modelo contempla el hecho de que las personas que esperan deberıan ingresar en el orden en
que arribaron? Como modificarıa la solucion para considerar ademas esta restriccion?.

*/

public class Rio {
    private Lock lock;
    private Condition noPuedoSubir;
    private Condition personasEsperanCruce;
    private Condition boteNoPuedeAtravesar;
    private Condition esperandoAqueBajen;
    private int capacidad;
    private int costaBote; // 0 para sur, 1 para norte
    private int personasEnBote;
    private boolean autorizacionFlag = true;

    public Rio(int capacidad) {
        this.capacidad = capacidad;
        this.lock = new ReentrantLock();
        this.noPuedoSubir = lock.newCondition();
        this.personasEsperanCruce = lock.newCondition();
        this.boteNoPuedeAtravesar = lock.newCondition();
        this.esperandoAqueBajen = lock.newCondition();
        this.costaBote = 0;
        this.personasEnBote = 0;
    }

    public void abordarBote(int costaPersona, int id) {
        lock.lock();
        try {
            System.out.println("Persona " + id + " quiere subir en costa " + costaPersona + ".");

            while (personasEnBote == capacidad || costaBote != costaPersona) {
                noPuedoSubir.await();
            }

            personasEnBote++;

            System.out.println("Persona " + id + " ha subido al bote en costa " + costaPersona + ", esperando cruce");

            if (personasEnBote == capacidad) {
                boteNoPuedeAtravesar.signal();
            }

            personasEsperanCruce.await();

            costaPersona = 1 - costaPersona;

            System.out.println("Persona " + id + " bajando del bote en costa " + costaPersona);

            personasEnBote--;

            if (personasEnBote == 0) {
                esperandoAqueBajen.signal();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void atravesarRio() {
        lock.lock();
        try {

            while (!autorizacionFlag || personasEnBote < capacidad) {
                System.out.println("Bote descansando en costa " + costaBote);
                boteNoPuedeAtravesar.await();
            }

            System.out.println("Bote cruzando la orilla");

            System.out.println("Bote llego a la orilla");

            personasEsperanCruce.signalAll();

            esperandoAqueBajen.await();

            costaBote = 1 - costaBote;

            noPuedoSubir.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
