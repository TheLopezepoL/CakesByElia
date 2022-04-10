package proyecto.modelo;

public class ListaIngredientes {
    private String id_producto;
    private String id_ingrediente;
    private String cantidad;

    public ListaIngredientes(String id_producto, String id_ingrediente, String cantidad) {
        this.id_producto = id_producto;
        this.id_ingrediente = id_ingrediente;
        this.cantidad = cantidad;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getId_ingrediente() {
        return id_ingrediente;
    }

    public void setId_ingrediente(String id_ingrediente) {
        this.id_ingrediente = id_ingrediente;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ListaIngredientes{" +
                "id_producto='" + id_producto + '\'' +
                ", id_ingrediente='" + id_ingrediente + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
