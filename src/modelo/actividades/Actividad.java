package modelo.actividades;

import modelo.Estudiante;
import modelo.Inscripcion;
import excepciones.CupoExcedidoException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public abstract class Actividad {
    private int id;
    private String titulo;
    private int cupoMaximo;

    public static final int CUPO_MINIMO;

    private List<Inscripcion> inscripciones;

    static {
        CUPO_MINIMO = 5;
        System.out.println("Inicializador estático: se cargó la clase modelo.actividades.Actividad.");
    }

    public Actividad(int id, String titulo, int cupo) {

        this.id = id;
        this.titulo = titulo;

        this.cupoMaximo = (cupo > CUPO_MINIMO)
                        ? cupo
                        : CUPO_MINIMO;

        this.inscripciones = new ArrayList<>();
    }
    public String getTitulo() {
        return titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public int getId() {
        return id;
    }

    public Inscripcion inscribir(Estudiante estudiante)
            throws CupoExcedidoException {

        if (inscripciones.size() >= cupoMaximo) {

            throw new CupoExcedidoException(
                    "No se puede inscribir al estudiante "
                            + estudiante.getNombre()
                            + ". Cupo máximo alcanzado.");
        }

        Inscripcion inscripcion =
                new Inscripcion(
                        this,
                        estudiante,
                        LocalDate.now(),
                        "REGISTRADA"
                );

        inscripciones.add(inscripcion);

        return inscripcion;
    }

    public void mostrarInscripciones() {

        System.out.println("Inscripciones de: " + titulo);

        for (Inscripcion inscripcion : inscripciones) {

            System.out.println(
                    "- "
                            + inscripcion.getEstudiante().getNombre()
                            + " | "
                            + inscripcion.getEstado()
                            + " | "
                            + inscripcion.getFecha()
            );
        }
    }
    public abstract double calcularCostoMateriales();
    public abstract String getTipo();

    public final void mostrarIdentificacion() {
        System.out.println("- " + getTipo() + ": " + titulo + " (id=" + id + ")" + " - Cupo máximo: " + cupoMaximo);
    }
}
