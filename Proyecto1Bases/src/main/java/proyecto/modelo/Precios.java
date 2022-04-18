package proyecto.modelo;

public class Precios {
    private String id_ingrediente;
    private String id_proveedor;
    private String precios;
    private String cantidad;

    public Precios(String id_ingrediente, String id_proveedor, String precios, String cantidad) {
        this.id_ingrediente = id_ingrediente;
        this.id_proveedor = id_proveedor;
        this.precios = precios;
        this.cantidad = cantidad;
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

    public String getPrecios() {
        return precios;
    }

    public void setPrecios(String precios) {
        this.precios = precios;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Precios{" +
                "id_ingrediente='" + id_ingrediente + '\'' +
                ", id_proveedor='" + id_proveedor + '\'' +
                ", precios='" + precios + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
