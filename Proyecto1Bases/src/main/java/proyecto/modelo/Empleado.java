package proyecto.modelo;

public class Empleado {
    private String id;
    private String id_sucursal;
    private String puesto;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String telefono;


    public Empleado(String id, String id_sucursal, String puesto, String nombre, String apellido1, String apellido2, String telefono) {
        this.id = id;
        this.id_sucursal = id_sucursal;
        this.puesto = puesto;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.telefono = telefono;
    }

    public String getId() {
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

    public String getId_sucursal() {
        return id_sucursal;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId_sucursal(String id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id='" + id + '\'' +
                ", id_sucursal='" + id_sucursal + '\'' +
                ", puesto='" + puesto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
