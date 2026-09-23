package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Taller;
import modelo.actividades.Curso; // 1. IMPORTANTE: Importar Curso
import excepciones.CupoExcedidoException;

public class EventoUniversitario implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String Id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;

    private Sala sala;
    private List<Actividad> actividades;
    private static int cantidadEventos;

    static {
        cantidadEventos = 0;
        System.out.println("Inicializador estático: se cargó la clase modelo.EventoUniversitario.");
    }

    public EventoUniversitario(String id, String nombre, double costo, boolean esGratuito) {
        this.Id = id;
        setTitulo(nombre);
        this.gratuito = esGratuito;
        this.costoBase = gratuito ? 0 : costo;
        cantidadEventos++;
        this.actividades = new ArrayList<>();
    }

    public EventoUniversitario(EventoUniversitario otroEvento) {
        this(
                otroEvento.Id + "-COPIA",
                otroEvento.titulo,
                otroEvento.costoBase,
                otroEvento.gratuito
        );
    }

    public String getId() {
        return Id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String nombre) {
        if (nombre != null && !nombre.isBlank())
            this.titulo = nombre;
    }

    public double calcularCostoEstimado() {
        if (this.gratuito) {
            return 0.0;
        }

        double costoTotal = costoBase;

        for (Actividad actividad : actividades) {
            costoTotal += actividad.calcularCostoMateriales();
        }

        return costoTotal * 1.21;
    }

    public Sala getSala() {
        return sala;
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void crearActividad(int id, String titulo, int cupo, String tipoActividad) {
        Scanner scanner = new Scanner(System.in);

        switch (tipoActividad.toLowerCase()) {
            case "charla":
                System.out.print("Ingrese el nombre del disertante para la charla " + titulo + " :  ");
                String disertante = scanner.nextLine();
                Actividad charla = new Charla(id, titulo, disertante, cupo);
                this.actividades.add(charla);
                break;
            case "taller":
                System.out.print("El taller " + titulo + " requiere el uso de Notebook? : S/N  ");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                boolean requiereNotebook = respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí");
                Actividad taller = new Taller(id, titulo, requiereNotebook, cupo);
                this.actividades.add(taller);
                break;
            case "curso": // 2. NUEVO CASE PARA CURSO
                System.out.print("Ingrese el nivel del curso " + titulo + " : ");
                int nivel = scanner.nextInt();
                Actividad curso = new Curso(id, titulo, nivel, cupo);
                this.actividades.add(curso);
                break;
            default:
                System.out.println("Error: Tipo de actividad no reconocido.");
        }
    }

    public List<Actividad> getActividades() {
        return Collections.unmodifiableList(actividades);
    }

    public void mostrarDatos() {
        System.out.println("===============================================");
        System.out.println("Evento codigo=" + Id);
        System.out.println("TÍtulo=" + titulo);
        System.out.println("Costo=" + this.calcularCostoEstimado());
        System.out.println("modelo.Sala asignada: " + (sala != null ? sala.getNombre() : "Sin sala") + "\n");
        System.out.println("Actividades:");
        System.out.println("____________");
        for (Actividad actividad : actividades) {
            actividad.mostrarIdentificacion();
            actividad.mostrarInscripciones();
        }
        System.out.println("===============================================");
    }

    public static int getCantidadEventos() {
        return cantidadEventos;
    }

    // =========================================================
    // MÉTODOS DE PERSISTENCIA
    // =========================================================

    public boolean persistirEvento() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.Id + ".dat"))) {
            oos.writeObject(this);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el evento: " + e.getMessage());
            return false;
        }
    }

    public static EventoUniversitario recuperarEvento(String id) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(id + ".dat"))) {
            return (EventoUniversitario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al recuperar el evento: " + e.getMessage());
            return null;
        }
    }
    // =========================================================
    // MÉTODOS DEL EJERCICIO 3: GENERICS Y WILDCARDS
    // =========================================================

    /**
     * Método genérico acotado para filtrar las actividades por un tipo específico.
     * Devuelve una lista fuertemente tipada (List<Charla>, List<Taller>, List<Curso>).
     */
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Actividad act : actividades) {
            if (tipo.isInstance(act)) {
                resultado.add(tipo.cast(act));
            }
        }
        return resultado;
    }

    /**
     * Método que utiliza un wildcard acotado superiormente (? extends Actividad)
     * para calcular el costo de materiales de cualquier lista de subtipos de Actividad.
     */
    public double calcularCostoMateriales(List<? extends Actividad> listaActividades) {
        double costoTotal = 0.0;
        for (Actividad act : listaActividades) {
            costoTotal += act.calcularCostoMateriales();
        }
        return costoTotal;
    }
}