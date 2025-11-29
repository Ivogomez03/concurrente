package ejercicioGuiaMoni6;

public class Orador implements Runnable {
    private Auditorio auditorio;

    public Orador(Auditorio auditorio) {
        this.auditorio = auditorio;
    }

    @Override
    public void run() {
        while (true) {
            auditorio.entrarAlAuditorioParaDarCharla();
            auditorio.terminarCharla();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
