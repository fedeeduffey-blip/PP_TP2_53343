import hilos.EnvioTicketsThread;
import excepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.Sala;
import modelo.actividades.Actividad;
import certificacion.Certificable;
import modelo.actividades.Charla;
import modelo.actividades.Taller;
import modelo.actividades.Curso;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   DEMOSTRACIÓN EJERCICIOS 1, 2 Y 3 - TP2");
        System.out.println("=================================================\n");

        Sala sala1 = new Sala(1, "Aula Magna");
        EventoUniversitario eventoPrincipal = new EventoUniversitario(
                "EVT-1",
                "Jornada de Tecnología",
                10000,
                false);

        eventoPrincipal.asignarSala(sala1);

        // Se crean las actividades dentro del evento
        eventoPrincipal.crearActividad(1, "Introducción a Algoritmos", 1, "charla");
        eventoPrincipal.crearActividad(2, "Taller de Java", 15, "taller");
        eventoPrincipal.crearActividad(3, "Curso de POO en Java", 10, "curso");

        EventoUniversitario copiaeventoPrincipal = new EventoUniversitario("EVT-2", "Jornada de Tecnología Copia", 10000, false);

        // Estudiantes existentes
        Estudiante e1 = new Estudiante("53343", "Federica Duffey");
        Estudiante e2 = new Estudiante("58731", "Tomás Herrera");
        Estudiante e3 = new Estudiante("53345", "Guadalupe Ríos");

        // Nuevos estudiantes agregados
        Estudiante e4 = new Estudiante("50003", "Martina");
        Estudiante e5 = new Estudiante("50004", "Lucía");
        Estudiante e6 = new Estudiante("50005", "Pedro");

        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(e1);
        estudiantes.add(e2);
        estudiantes.add(e3);
        estudiantes.add(e4);
        estudiantes.add(e5);
        estudiantes.add(e6);

        // Obtenemos la charla, taller y curso
        Actividad charla = eventoPrincipal.getActividades().get(0);
        Actividad taller = eventoPrincipal.getActividades().get(1);
        Actividad curso = eventoPrincipal.getActividades().get(2);

        // --- 1. INSCRIPCIONES CON MANEJO DE EXCEPCIONES ---
        System.out.println("\n--- PRUEBA DE INSCRIPCIONES Y MANEJO DE CUPO ---");

        // Inscripción 1 (Federica) -> Charla
        try {
            charla.inscribir(e1);
            System.out.println("Inscripción realizada correctamente para " + e1.getNombre());
        } catch (CupoExcedidoException e) {
            System.out.println("Error al inscribir a " + e1.getNombre() + ": " + e.getMessage());
        }

        // Inscripción 2 (Pedro) -> Charla (excede cupo)
        try {
            charla.inscribir(e6);
            System.out.println("Inscripción realizada correctamente para " + e6.getNombre());
        } catch (CupoExcedidoException e) {
            System.out.println("\nCaso fallido controlled:");
            System.out.println("No se puede inscribir al estudiante " + e6.getNombre() + ". Cupo máximo alcanzado.");
        }

        // Inscripciones en el taller
        try {
            taller.inscribir(e4);
            System.out.println("\nInscripción realizada correctamente para " + e4.getNombre() + " en el taller.");
        } catch (CupoExcedidoException e) {
            System.out.println("Error al inscribir a Martina en taller: " + e.getMessage());
        }

        try {
            taller.inscribir(e5);
            System.out.println("Inscripción realizada correctamente para " + e5.getNombre() + " en el taller.");
        } catch (CupoExcedidoException e) {
            System.out.println("Error al inscribir a Lucía en taller: " + e.getMessage());
        }

        // Inscripción en el curso (Ejercicio 2)
        try {
            curso.inscribir(e3);
            System.out.println("Inscripción realizada correctamente para " + e3.getNombre() + " en el curso.");
        } catch (CupoExcedidoException e) {
            System.out.println("Error al inscribir a Guadalupe en curso: " + e.getMessage());
        }

        // --- 2. PERSISTENCIA CON TRY-CATCH-FINALLY ---
        System.out.println("\n--- PRUEBA DE PERSISTENCIA (SERIALIZACIÓN) ---");
        try {
            System.out.println("Intentando guardar el evento en disco...");
            boolean guardado = eventoPrincipal.persistirEvento();
            if (guardado) {
                System.out.println("-> Evento guardado con éxito.");
            }

            System.out.println("Intentando recuperar el evento guardado...");
            EventoUniversitario eventoRecuperado = EventoUniversitario.recuperarEvento("EVT-1");
            if (eventoRecuperado != null) {
                System.out.println("-> Evento recuperado correctamente: " + eventoRecuperado.getTitulo());
            }
        } catch (Exception e) {
            System.out.println("[ERROR EN PERSISTENCIA]: " + e.getMessage());
        } finally {
            System.out.println("[FINALLY]: Bloque de limpieza ejecutado. Operación de persistencia finalizada.");
        }

        // --- 3. EMISIÓN DE CERTIFICADOS (EJERCICIO 2 REFINADO) ---
        System.out.println("\n===============================================");
        System.out.println("       EMISIÓN DE CERTIFICADOS DE ASISTENCIA");
        System.out.println("===============================================");

        for (Actividad act : eventoPrincipal.getActividades()) {
            // Uso de Pattern Matching (Java 16+) según Sección 7.5 del manual
            if (act instanceof Certificable certificable) {
                System.out.println("\n>>> CERTIFICADOS EMITIDOS PARA: " + act.getTitulo() + " (" + act.getTipo() + ") <<<");

                if (act.getInscripciones().isEmpty()) {
                    System.out.println("No hay alumnos inscriptos.");
                } else {
                    for (Inscripcion inc : act.getInscripciones()) {
                        System.out.println(certificable.generarCertificado(inc.getEstudiante()));
                        System.out.println("----------------------------------------");
                    }
                }
            } else {
                System.out.println("\n>>> " + act.getTitulo() + " (" + act.getTipo() + ") <<<");
                System.out.println("Las charlas no emiten certificados de asistencia.");
            }
        }

        // =========================================================
        // --- 4. FILTRADO Y CÁLCULO DE MATERIALES (EJERCICIO 3) ---
        // =========================================================
        System.out.println("\n===============================================");
        System.out.println("   FILTRADO Y CÁLCULO DE MATERIALES (EJERCICIO 3)");
        System.out.println("===============================================");

        // d y g. Filtrado devolviendo listas correctamente tipadas
        List<Charla> charlas = eventoPrincipal.filtrarActividadesPorTipo(Charla.class);
        List<Taller> talleres = eventoPrincipal.filtrarActividadesPorTipo(Taller.class);
        List<Curso> cursos = eventoPrincipal.filtrarActividadesPorTipo(Curso.class);

        // e. Cantidad de actividades de cada tipo creadas
        System.out.println("Cantidad de Charlas: " + charlas.size());
        System.out.println("Cantidad de Talleres: " + talleres.size());
        System.out.println("Cantidad de Cursos: " + cursos.size());

        // f. Cálculo del costo de materiales correspondiente a cada tipo con wildcards
        double costoCharlas = eventoPrincipal.calcularCostoMateriales(charlas);
        double costoTalleres = eventoPrincipal.calcularCostoMateriales(talleres);
        double costoCursos = eventoPrincipal.calcularCostoMateriales(cursos);

        System.out.println("\nCosto de materiales por tipo:");
        System.out.println("- Charlas: $" + costoCharlas);
        System.out.println("- Talleres: $" + costoTalleres);
        System.out.println("- Cursos: $" + costoCursos);

        // Cálculo global de todas las actividades del evento
        double costoTotalMateriales = eventoPrincipal.calcularCostoMateriales(eventoPrincipal.getActividades());
        System.out.println("\nCosto total de materiales en el evento: $" + costoTotalMateriales);

        // =========================================================
        // --- EMISIÓN Y ENVÍO CONCURRENTE DE TICKETS (EJERCICIO 4) ---
        // =========================================================
        System.out.println("\n===============================================");
        System.out.println("  PROCESO DE TICKETS E HILOS (EJERCICIO 4)");
        System.out.println("===============================================");

        // Confirmar inscripciones para generar sus tickets de acceso
        System.out.println("Confirmando inscripciones y generando tickets...");
        for (Actividad act : eventoPrincipal.getActividades()) {
            for (Inscripcion inc : act.getInscripciones()) {
                inc.confirmar(); // Cambia el estado a CONFIRMADA y crea el TicketDeAcceso anidado
            }
        }

        // Crear e iniciar el hilo concurrente en segundo plano
        EnvioTicketsThread hiloEnvio = new EnvioTicketsThread(eventoPrincipal);
        hiloEnvio.start(); // Inicia la ejecución asíncrona de run()

        // El Hilo Principal continúa su ejecución sin bloquearse
        System.out.println("\n[HILO PRINCIPAL] Continuando ejecución en paralelo...");

        // --- 5. MOSTRAR DATOS GENERALES ---
        System.out.println("\n===============================================");
        System.out.println("EVENTO PRINCIPAL");
        eventoPrincipal.mostrarDatos();

        System.out.println();

        System.out.println("COPIA DEL EVENTO");
        copiaeventoPrincipal.mostrarDatos();

        System.out.println("Eventos creados: " + EventoUniversitario.getCantidadEventos());
    }

}