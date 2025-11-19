package ejercicioListas5;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tabla {
    HashMap<Integer, Object> tabla = new HashMap<Integer, Object>();
    private final Lock lock = new ReentrantLock();

    public Object ComputacionalmenteCostoso(Object v) {
        // simular calculo costoso
        return v;
    }

    private void actualizarEntrada(int i) {
        while (true) {

            Object v1 = tabla.get(i);
            Object v2 = ComputacionalmenteCostoso(v1);
            lock.lock();

            try {
                Object valorActual = tabla.get(i);
                if (valorActual == v1) {
                    tabla.put(i, v2);
                    return;
                }
            } finally {
                lock.unlock();
            }
        }
    } // no libre de inanicion, si la entrada cambia constantementes (alta
      // contencion), se queda esperando indefinidamente

}
