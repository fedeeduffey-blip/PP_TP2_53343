package hilos;

import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.actividades.Actividad;

public class EnvioTicketsThread extends Thread {
    private EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento) {
        super("Hilo-Envio-Tickets");
        this.evento = evento;
    }

    @Override
    public void run() {
        System.out.println("\n>>> [" + getName() + "] Inicio del proceso concurrente de envío de tickets...");

        for (Actividad actividad : evento.getActividades()) {
            for (Inscripcion inscripcion : actividad.getInscripciones()) {
                if ("CONFIRMADA".equals(inscripcion.getEstado()) && inscripcion.getTicket() != null) {
                    inscripcion.getTicket().enviarTicket();
                    try {
                        // Pausa de 500ms para simular tiempo de transmisión e intercalar flujos en consola
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.err.println("Error en el hilo de envío: " + e.getMessage());
                    }
                }
            }
        }

        System.out.println(">>> [" + getName() + "] Fin del envío de tickets.\n");
    }
}