package proyecto.modelo;

import java.sql.*;

//Class Singleton
public class OracleJBDC {
 
    Connection conexion = null;
    Statement sentencia = null;

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
    Función para probar la conexion a la base de datos
    */
    public void probarConexion (String url, String usuario, String contrasena){

        try {
            conexion = DriverManager.getConnection(url, usuario, contrasena);  //Hace la conexion con la base de datos
            sentencia = conexion.createStatement();

            DatabaseMetaData metaData = conexion.getMetaData();

            String productName = metaData.getDatabaseProductName();
            System.out.println("Información de la Base de Datos");
            System.out.println("Product Name: " + productName);

            System.out.println("Prueba Sentencia SQL");

            String testSQL = "SELECT * FROM prueba";
            ResultSet resultado = sentencia.executeQuery(testSQL);

            while (resultado.next()){
                System.out.println( resultado.getString("first_name") + "\t" + resultado.getString("last_name") );
            }

            resultado.close();
            sentencia.close();
            conexion.close();

        } catch (SQLException e) {
           // e.printStackTrace();
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
        }
    }

}
