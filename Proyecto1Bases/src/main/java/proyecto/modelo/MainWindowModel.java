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

//        Empleado empleado1 = new Empleado("5","6","Pastelero","Prueba1", "Prueba1","Rod","89898989");
//        Empleado empleado2 = new Empleado("6","7","Pastelero","Prueba1", "Prueba1","Rod","89898989");
//        Empleado empleado3 = new Empleado("7","7","Pastelero","Prueba1", "Prueba1","Rod","89898989");
//
//        ObservableList< Empleado > empleados = FXCollections.observableArrayList(); //Esta lista se debe obtener desde el OJBDC
//        empleados.add(empleado1);
//        empleados.add(empleado2);
//        empleados.add(empleado3);

        /*
        todo:
        Funcion para hacer la lista de empleados desde el OJBDC
         */
        ObservableList< Empleado > empleados =  oracleJBDC.getTablaEmpleado();

        return empleados;
    }
}
