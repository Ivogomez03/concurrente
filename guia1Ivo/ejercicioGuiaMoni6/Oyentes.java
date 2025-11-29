package ejercicioGuiaMoni6;

public class Oyentes implements Runnable {
    private Auditorio auditorio;

    public Oyentes(Auditorio auditorio) {
        this.auditorio = auditorio;
    }

    @Override
    public void run() {
        auditorio.entrarAlaCharla();
    }
}
