package proyecto.modelo;

public class Ingrediente {
    private String id;
    private String nombre;
    private String medida;

    public Ingrediente(String id, String nombre, String medida) {
        this.id = id;
        this.nombre = nombre;
        this.medida = medida;
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

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", medida='" + medida + '\'' +
                '}';
    }
}
