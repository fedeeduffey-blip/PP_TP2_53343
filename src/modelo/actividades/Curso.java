package modelo.actividades;

import certificacion.Certificable;
import modelo.Estudiante;

public class Curso extends Actividad implements Certificable {
    private int nivel;

    public Curso(int id, String titulo, int nivel, int cupoMaximo) {
        super(id, titulo, cupoMaximo);
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        // Regla exacta según el Manual Teórico (Sección 7.4)
        switch (nivel) {
            case 1:
                return 1000.0;
            case 2:
                return 2000.0;
            case 3:
                return 3000.0;
            default:
                return 0.0;
        }
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO DE CURSO [" + ENTIDAD_EMISORA + "]\n" +
                "Se certifica que " + estudiante.getNombre() +
                " (Legajo: " + estudiante.getLegajo() + ") aprobó el curso '" +
                getTitulo() + "' (Nivel " + nivel + ").";
    }
}