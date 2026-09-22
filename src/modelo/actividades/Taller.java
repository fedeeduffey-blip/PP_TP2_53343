package modelo.actividades;

public class Taller extends Actividad {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, boolean requiereNotebook, int cupo) {
        super(id, titulo, cupo);
        this.requiereNotebook = requiereNotebook;
    }

    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }

    public void setRequiereNotebook(boolean requiereNotebook) {
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        if (requiereNotebook) {
            return 5000.0;
        }
        return 2000.0;
    }

    @Override
    public String getTipo() {
        return this.getClass().getSimpleName();
    }
}

