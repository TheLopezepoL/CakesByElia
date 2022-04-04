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

    public ArrayList<String> getListaTablas() throws SQLException {

        ArrayList<String> listaTablas = this.oracleJBDC.getTablasUsuario();
        System.out.println(listaTablas);
        return listaTablas;
    }

}
