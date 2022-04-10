package proyecto.modelo;

public class Precios {
    private String id_ingrediente;
    private String id_proveedor;

    public Precios(String id_ingrediente, String id_proveedor) {
        this.id_ingrediente = id_ingrediente;
        this.id_proveedor = id_proveedor;
    }

    public String getId_ingrediente() {
        return id_ingrediente;
    }

    public void setId_ingrediente(String id_ingrediente) {
        this.id_ingrediente = id_ingrediente;
    }

    public String getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(String id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Precios{" +
                "id_ingrediente='" + id_ingrediente + '\'' +
                ", id_proveedor='" + id_proveedor + '\'' +
                '}';
    }
}
