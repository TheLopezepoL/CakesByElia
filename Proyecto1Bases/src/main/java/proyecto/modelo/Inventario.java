package proyecto.modelo;

public class Inventario {
    private String id_sucursal;
    private String id_ingrediente;
    private String cantidad;

    public Inventario(String id_sucursal, String id_ingrediente, String cantidad) {
        this.id_sucursal = id_sucursal;
        this.id_ingrediente = id_ingrediente;
        this.cantidad = cantidad;
    }

    public String getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(String id_sucursal) {
        this.id_sucursal = id_sucursal;
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
        return "Inventario{" +
                "id_sucursal='" + id_sucursal + '\'' +
                ", id_ingrediente='" + id_ingrediente + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
