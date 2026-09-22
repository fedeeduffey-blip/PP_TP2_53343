package modelo;

public class Estudiante {
    private String legajo;
    private String nombre;

    public Estudiante(String legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
    }

    public String getLegajo() {return legajo;}

    public String getNombre() {return nombre;}
    }
