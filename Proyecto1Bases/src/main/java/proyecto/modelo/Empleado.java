package proyecto.modelo;

public class Empleado {
    private int id;
    private String puesto;
    private String nombre;
    private String apellido1;

    public Empleado(int id, String puesto, String nombre, String apellido1) {
        this.id = id;
        this.puesto = puesto;
        this.nombre = nombre;
        this.apellido1 = apellido1;
    }

    public int getId() {
        return id;
    }

    public String getPuesto() {
        return puesto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", puesto='" + puesto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                '}';
    }
}
