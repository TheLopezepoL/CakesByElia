package proyecto.modelo;

public class Bitacora {
    private String id;
    private String id_pedido;
    private String tipoCambio;
    private String fecha;

    public Bitacora(String id, String id_pedido, String tipoCambio, String fecha) {
        this.id = id;
        this.id_pedido = id_pedido;
        this.tipoCambio = tipoCambio;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Ingrediente{" +
                "id='" + id + '\'' +
                ", id_pedido='" + id_pedido + '\'' +
                ", tipoCambio='" + tipoCambio + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
