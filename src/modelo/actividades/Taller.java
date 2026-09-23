package modelo.actividades;

import certificacion.Certificable;
import modelo.Estudiante;

public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, boolean requiereNotebook, int cupoMaximo) {
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        return requiereNotebook ? 800.0 : 400.0;
    }

    @Override
    public String getTipo() {
        return "Taller";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO DE TALLER [" + ENTIDAD_EMISORA + "]\n" +
                "Se certifica que " + estudiante.getNombre() +
                " (Legajo: " + estudiante.getLegajo() + ") asistió al taller '" +
                getTitulo() + "'.";
    }
}

