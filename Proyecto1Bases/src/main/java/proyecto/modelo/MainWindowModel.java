package proyecto.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

        ArrayList<String> listaTablas = this.oracleJBDC.getTablasUsuarioBaseDatos();
        System.out.println(listaTablas);
        return listaTablas;
    }

    public String getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(String tablaActual) {
        this.tablaActual = tablaActual;
    }



    //Crud de tablas

    //Devuelve la lista de empleados, que trajo desde la base de datos
    //Para cargarlo en la tabla del view
    public ObservableList<Empleado> getEmpleadosTabla () throws SQLException {

        ObservableList< Empleado > empleados =  oracleJBDC.getTablaEmpleado();
        return empleados;
    }

    public void insertNuevoEmpleado(Empleado empleado) throws SQLException {
        //Acá habría que rodearlo de un try catch y retornar true so lo logró, false si no.
        oracleJBDC.insertEmpleado(empleado);
    }

    public void deleteEmpleado(int idEmpleado) throws SQLException {
        oracleJBDC.deleteEmpleado(idEmpleado);
    }
}
