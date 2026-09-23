package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import modelo.actividades.Actividad;

public class Inscripcion implements Serializable {
    private static final long serialVersionUID = 1L;

    private Actividad actividad;
    private Estudiante estudiante;
    private LocalDate fecha;
    private String estado;

    // Atributo agregado para el Ejercicio 4
    private TicketDeAcceso ticket;

    public Inscripcion(Actividad actividad, Estudiante estudiante, LocalDate fecha, String estado) {
        this.actividad = actividad;
        this.estudiante = estudiante;
        this.fecha = fecha;
        this.estado = estado;
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

    public Actividad getActividad() {
        return actividad;
    }

    public TicketDeAcceso getTicket() {
        return ticket;
    }

    // Método para confirmar la inscripción y generar el ticket anidado
    public void confirmar() {
        this.estado = "CONFIRMADA";
        this.ticket = new TicketDeAcceso();
    }

    // ======================================
    // CLASE ANIDADA MIEMBRO: TicketDeAcceso
    // ======================================
    public final class TicketDeAcceso implements Serializable {
        private static final long serialVersionUID = 1L;

        private String idTicket;
        private LocalDate fechaEmision;

        public TicketDeAcceso() {
            // Acceso directo a los atributos privados de la clase externa (Inscripcion)
            this.idTicket = "TICKET-" + actividad.getId() + "-" + estudiante.getLegajo() + "-" + System.currentTimeMillis();
            this.fechaEmision = LocalDate.now();
            System.out.println("  [TICKET CREADO] -> " + idTicket + " para " + estudiante.getNombre());
        }

        public String getIdTicket() {
            return idTicket;
        }

        public void enviarTicket() {
            System.out.println("  [Hilo-Envio-Tickets] -> Enviando " + idTicket +
                    " a " + estudiante.getNombre() +
                    " (" + estudiante.getLegajo() + ") para: " + actividad.getTitulo());
        }
    }
}