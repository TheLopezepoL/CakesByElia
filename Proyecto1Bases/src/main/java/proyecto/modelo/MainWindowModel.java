package proyecto.modelo;

import java.sql.SQLException;
import java.util.ArrayList;

public class MainWindowModel {
    private Empleado empleado = null;
    private OracleJBDC oracleJBDC = null;
    private String tablaActual = "";

    public MainWindowModel(Empleado empleado) throws SQLException {
        this.empleado = empleado;
        this.oracleJBDC = OracleJBDC.getInstancia();
        this.tablaActual = "EMPLEADO";

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

    public String getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(String tablaActual) {
        this.tablaActual = tablaActual;
    }
}
