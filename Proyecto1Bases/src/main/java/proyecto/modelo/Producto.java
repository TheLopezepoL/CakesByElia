package proyecto.modelo;

public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private String receta;

    public Producto(String id, String nombre, String descripcion, String receta) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.receta = receta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getReceta() {
        return receta;
    }

    public void setReceta(String receta) {
        this.receta = receta;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Producto{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", receta='" + receta + '\'' +
                '}';
    }
}
