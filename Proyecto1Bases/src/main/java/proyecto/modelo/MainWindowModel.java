package proyecto.modelo;

public class MainWindowModel {
    private Empleado empleado = null;
    private OracleJBDC oracleJBDC = null;

    public MainWindowModel(Empleado empleado) {
        this.empleado = empleado;
        this.oracleJBDC = OracleJBDC.getInstancia();
    }

    public Empleado getEmpleado() {
        return empleado;
    }
}
