package proyecto.modelo;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainWindowModel {
    private Empleado empleado = null;
    private OracleJBDC oracleJBDC = null;

    public MainWindowModel(Empleado empleado) throws SQLException {
        this.empleado = empleado;
        this.oracleJBDC = OracleJBDC.getInstancia();

        getListaTablas();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void getListaTablas() throws SQLException {

        System.out.println(this.oracleJBDC.getTablasUsuario());

    }
}
