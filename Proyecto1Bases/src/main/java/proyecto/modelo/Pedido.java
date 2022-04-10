package proyecto.modelo;

public class Pedido {
    private String id;
    private String id_producto;
    private String id_sucursal;
    private String id_cliente;
    private String fechaPedido;
    private String fechaEntrega;
    private String direccionEntrega;

    public Pedido(String id, String id_producto, String id_sucursal, String id_cliente, String fechaPedido, String fechaEntrega, String direccionEntrega) {
        this.id = id;
        this.id_producto = id_producto;
        this.id_sucursal = id_sucursal;
        this.id_cliente = id_cliente;
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.direccionEntrega = direccionEntrega;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(String id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Pedido{" +
                "id='" + id + '\'' +
                ", id_producto='" + id_producto + '\'' +
                ", id_sucursal='" + id_sucursal + '\'' +
                ", id_cliente='" + id_cliente + '\'' +
                ", fechaPedido='" + fechaPedido + '\'' +
                ", fechaEntrega='" + fechaEntrega + '\'' +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                '}';
    }
}