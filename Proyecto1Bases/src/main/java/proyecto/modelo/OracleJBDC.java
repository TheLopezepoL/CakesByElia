package proyecto.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

//Class Singleton
public class OracleJBDC {
    //private String instanciaURL = "jdbc:oracle:thin:@192.168.100.90:1521:grp06db";
    private String instanciaURL = "jdbc:oracle:thin:@172.20.10.3:1521:grp06db";
    private String instanciaUsuario = "C##userproyecto1";
    private String instaciaContrasenia = "oracle1";


    private static final OracleJBDC instancia;

    static {
        instancia = new OracleJBDC();
    }

    private OracleJBDC(){

    }

    public static OracleJBDC getInstancia(){
        return instancia;
    }

    /*
    Función para probar la conexion a la base de datos, no se va a utilizar al final
    */
    public void probarConexion (){
        Connection conexion = null;
        Statement sentencia = null;

        try {
            conexion = DriverManager.getConnection(instanciaURL, instanciaUsuario, instaciaContrasenia);  //Hace la conexion con la base de datos
            sentencia = conexion.createStatement();


            DatabaseMetaData metaData = conexion.getMetaData();

            String productName = metaData.getDatabaseProductName();
            System.out.println("Información de la Base de Datos");
            System.out.println("Product Name: " + productName);

            System.out.println("Prueba Sentencia SQL");

            String testSQL = "SELECT table_name FROM user_tables ORDER BY table_name";

            ResultSet resultado = sentencia.executeQuery(testSQL);


            while (resultado.next()){
                System.out.println( resultado.getString("table_name") );
            }

            resultado.close();
            sentencia.close();
            conexion.close();

        } catch (SQLException e) {
            // e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }
    }

    //Devuelve la coneccion a la base de datos para ejecutar queries
    private Connection conectarBaseDatos(  ){
        try {
            Connection conexion = DriverManager.getConnection(instanciaURL, instanciaUsuario, instaciaContrasenia);  //Hace la conexion con la base de datos
            DatabaseMetaData metaData = conexion.getMetaData();

            String productName = metaData.getDatabaseProductName();
            System.out.println("Información de la Base de Datos");
            System.out.println("Product Name: " + productName);

            return  conexion;

        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            return null;
        }
    }

    //Retorna True si pudo cerrar la conexion de la base de datos
    private boolean cerrarConexionBase(Connection pConeccion, Statement pSentencia, ResultSet pResultado) throws SQLException {
        try {
            pConeccion.close();
            pSentencia.close();
            pResultado.close();

            return true;
        }catch (SQLException e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            return false;
        }
    }

    //Retorna True si pudo cerrar la conexion de la base de datos
    private boolean cerrarConexionBase(Connection pConeccion, CallableStatement pSentencia) throws SQLException {
        try {
            pConeccion.close();
            pSentencia.close();

            return true;
        }catch (SQLException e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            return false;
        }
    }

    //Valida las credenciales con la base de datos y retorna el id del empleado
    public int validar_credenciales(String pUsuario, String pContrasenia) throws SQLException {
        try {
            Connection conBase = conectarBaseDatos();
            CallableStatement validarLogin_fn = conBase.prepareCall("{? = call valida_login(?,?)}");

            validarLogin_fn.registerOutParameter(1, Types.NUMERIC);
            validarLogin_fn.setString(2, pUsuario);
            validarLogin_fn.setString(3, pContrasenia);
            validarLogin_fn.execute();
            int idEmpleado = validarLogin_fn.getInt(1);

            cerrarConexionBase(conBase, validarLogin_fn);

            return idEmpleado;
        }catch (SQLException e){
            System.out.println("Error conectandose a la base de datos para traer el usuario");
            return 0;
        }
    }

    //Retorna toda la info del empleado en un objeto empleado
    public Empleado getEmpleado(int idEmpleado) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null){
            Statement sentenciaSQL = baseDatos.createStatement();
            System.out.println("Trayendo al Empleado con ID " + idEmpleado + " de la base de datos.");

            String getEmpelado = "SELECT * FROM empleado WHERE id_empleado = " + idEmpleado ;

            ResultSet resultado = sentenciaSQL.executeQuery(getEmpelado);
            Empleado empleado = null;
            while (resultado.next()){
                empleado = new Empleado( idEmpleado,
                        resultado.getString("puesto_empleado"),
                        resultado.getString("nombre_empleado"),
                        resultado.getString("apellido1_empleado") );
            }
            cerrarConexionBase(baseDatos, sentenciaSQL, resultado);
            return empleado;
        }else {
            return null;
        }
    }

    //Retorna lista de las tablas que el usuario tiene acceso
    public ArrayList<String> getTablasUsuario() throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null){
            System.out.println("Trayendo las tablas del usuario de la base de datos");


            Statement sentenciaProcedimiento = baseDatos.createStatement();
            String gettablas = "SELECT table_name FROM user_tables";   //Acá no sé si debe ser un Stored procedure


            ResultSet resultado = sentenciaProcedimiento.executeQuery(gettablas);


            ArrayList<String> listaTablas = new ArrayList<>();

            Empleado empleado = null;
            while (resultado.next() ){
                listaTablas.add(resultado.getString("TABLE_NAME"));
            }
            cerrarConexionBase(baseDatos, sentenciaProcedimiento, resultado);
            return listaTablas;

        }else {
            return null;
        }
    }
}
