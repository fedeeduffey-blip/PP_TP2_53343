package modelo;

import java.time.LocalDate;
import modelo.actividades.Actividad;
public class Inscripcion {
    private Actividad actividad;
    private Estudiante estudiante;
    private LocalDate fecha;
    private String estado;

    public Inscripcion(
            Actividad actividad,
            Estudiante estudiante,
            LocalDate fecha,
            String estado) {

        this.actividad = actividad;
        this.estudiante = estudiante;
        this.fecha = fecha;
        this.estado = estado;
    }

    public void confirmar() {
        this.estado = "CONFIRMADA";
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }
}
