package proyecto.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

//Class Singleton
public class OracleJBDC {
    private String instanciaURL = "jdbc:oracle:thin:@192.168.100.90:1521:grp06db";
    //private String instanciaURL = "jdbc:oracle:thin:@172.20.10.3:1521:grp06db";
    private String instanciaUsuario = "C##userproyecto1";
    private String instaciaContrasenia = "oracle1";


    private static final OracleJBDC instancia;

    static {
        instancia = new OracleJBDC();
    }

    private OracleJBDC() {

    }

    public static OracleJBDC getInstancia() {
        return instancia;
    }

    /*
    Función para probar la conexion a la base de datos, no se va a utilizar al final
    */
    public void probarConexion() {
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


            while (resultado.next()) {
                System.out.println(resultado.getString("table_name"));
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
    private Connection conectarBaseDatos() {
        try {
            Connection conexion = DriverManager.getConnection(instanciaURL, instanciaUsuario, instaciaContrasenia);  //Hace la conexion con la base de datos

            System.out.println("Base de Datos Conectada");


            return conexion;

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
            System.out.println("Base de Datos Desconectada");

            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            return false;
        }
    }

    //Retorna True si pudo cerrar la conexion de la base de datos
    private boolean cerrarConexionBase(Connection pConeccion, CallableStatement pSentencia) throws SQLException {
        try {
            pConeccion.close();
            pSentencia.close();
            System.out.println("Base de Datos Desconectada");
            return true;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            return false;
        }
    }

    //Valida las credenciales con la base de datos y retorna el id del empleado
    public int validar_credenciales(String pUsuario, String pContrasenia) throws SQLException {

        try {
            Connection conBase = conectarBaseDatos();
            System.out.println("Validando credenciales en la Base de Datos");
            CallableStatement validarLogin_fn = conBase.prepareCall("{? = call CONSULTAS_PASTELERIA_PKG.valida_login(?,?)}");

            validarLogin_fn.registerOutParameter(1, Types.NUMERIC);
            validarLogin_fn.setString(2, pUsuario);
            validarLogin_fn.setString(3, pContrasenia);
            validarLogin_fn.execute();
            int idEmpleado = validarLogin_fn.getInt(1);

            cerrarConexionBase(conBase, validarLogin_fn);

            return idEmpleado;
        } catch (SQLException e) {
            System.out.println("Error conectandose a la base de datos para traer el usuario");
            return 0;
        }
    }


    //Retorna lista de las tablas que el usuario tiene acceso
    public ArrayList<String> getTablasUsuarioBaseDatos() throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Trayendo las tablas del usuario de la base de datos");

            Statement sentenciaProcedimiento = baseDatos.createStatement();
            String gettablas = "SELECT table_name FROM user_tables";   //Acá no sé si debe ser un Stored procedure

            ResultSet resultado = sentenciaProcedimiento.executeQuery(gettablas);

            ArrayList<String> listaTablas = new ArrayList<>();

            Empleado empleado = null;
            while (resultado.next()) {
                listaTablas.add(resultado.getString("TABLE_NAME"));
            }
            cerrarConexionBase(baseDatos, sentenciaProcedimiento, resultado);
            return listaTablas;

        } else {
            return null;
        }
    }

    //CRUD DE LAS TABLAS
    //************ EMPLEADO //************ EMPLEADO //************ EMPLEADO //************ EMPLEADO

    public ObservableList<Empleado> getTablaEmpleado() throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Trayendo Empleados de tabla EMPLEADO");

            Statement sentenciaSQL = baseDatos.createStatement();
            String getEmpleadosSQL = "SELECT * FROM EMPLEADO";   //Acá no sé si debe ser un Stored procedure

            ResultSet resultado = sentenciaSQL.executeQuery(getEmpleadosSQL);

            ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList(); //Esta lista se debe obtener desde el OJBDC

            while (resultado.next()) {
                Empleado empleado = new Empleado(resultado.getString("id_Empleado"),
                        resultado.getString("id_sucursalfk"),
                        resultado.getString("puesto_empleado"),
                        resultado.getString("nombre_empleado"),
                        resultado.getString("apellido1_empleado"),
                        resultado.getString("apellido2_empleado"),
                        resultado.getString("telefono_empleado")
                );
                listaEmpleados.add(empleado);
            }

            cerrarConexionBase(baseDatos, sentenciaSQL, resultado);
            return listaEmpleados;

        } else {
            return null;
        }

    }

    //Retorna toda la info del empleado en un objeto empleado
    public Empleado getEmpleado(int idEmpleado) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            Statement sentenciaSQL = baseDatos.createStatement();
            System.out.println("Trayendo al Empleado con ID " + idEmpleado + " de la base de datos.");

            String getEmpelado = "SELECT * FROM empleado WHERE id_empleado = " + idEmpleado;

            ResultSet resultado = sentenciaSQL.executeQuery(getEmpelado);
            Empleado empleado = null;
            while (resultado.next()) {
                empleado = new Empleado("" + idEmpleado, resultado.getString("id_sucursalfk"),
                        resultado.getString("puesto_empleado"),
                        resultado.getString("nombre_empleado"),
                        resultado.getString("apellido1_empleado"),
                        resultado.getString("apellido2_empleado"),
                        resultado.getString("telefono_empleado")
                );
            }
            cerrarConexionBase(baseDatos, sentenciaSQL, resultado);
            return empleado;
        } else {
            return null;
        }
    }

    public void insertEmpleado(Empleado empleado) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Empleado en tabla EMPLEADO");


            CallableStatement insertEmpleado = baseDatos.prepareCall("{call cud_pasteleria_pkg.createEmpleado_Proc(?,?,?,?,?,?)");

            insertEmpleado.setInt(1, Integer.parseInt(empleado.getId_sucursal()));
            insertEmpleado.setString(2, empleado.getPuesto());
            insertEmpleado.setString(3, empleado.getNombre());
            insertEmpleado.setString(4, empleado.getApellido1());
            insertEmpleado.setString(5, empleado.getApellido2());
            insertEmpleado.setString(6, empleado.getTelefono());

            insertEmpleado.execute();

            cerrarConexionBase(baseDatos, insertEmpleado);
        }

    }

    public void deleteEmpleado(int idEmpleado) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Empleado en tabla EMPLEADO");


            CallableStatement delEmpleado = baseDatos.prepareCall("{call cud_pasteleria_pkg.deleteEmpleado_Proc(?)");

            delEmpleado.setInt(1, idEmpleado);
            delEmpleado.execute();


            cerrarConexionBase(baseDatos, delEmpleado);
        }

    }

    public void updateEmpleado(Empleado empleado) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Empleado en tabla EMPLEADO");


            CallableStatement updateEmpleado = baseDatos.prepareCall("{call cud_pasteleria_pkg.updateEmpleado_Proc(?,?,?,?,?,?,?)");

            updateEmpleado.setInt(1, Integer.parseInt(empleado.getId()));
            updateEmpleado.setInt(2, Integer.parseInt(empleado.getId_sucursal()));
            updateEmpleado.setString(3, empleado.getNombre());
            updateEmpleado.setString(4, empleado.getApellido1());
            updateEmpleado.setString(5, empleado.getApellido2());
            updateEmpleado.setString(6, empleado.getTelefono());
            updateEmpleado.setString(7, empleado.getPuesto());

            updateEmpleado.execute();

            cerrarConexionBase(baseDatos, updateEmpleado);
        }
    }

    public ObservableList<Cliente> getTableCliente() throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Trayendo Clientes de tabla CLIENTE");

            Statement sentenciaSQL = baseDatos.createStatement();
            String getClientesSQL = "SELECT * FROM CLIENTE";   //Acá no sé si debe ser un Stored procedure

            ResultSet resultado = sentenciaSQL.executeQuery(getClientesSQL);

            ObservableList<Cliente> listaClientes = FXCollections.observableArrayList(); //Esta lista se debe obtener desde el OJBDC

            while (resultado.next()) {
                Cliente cliente = new Cliente(resultado.getString("id_Cliente"),
                        resultado.getString("nombre"),
                        resultado.getString("telefono"));
                listaClientes.add(cliente);
            }

            cerrarConexionBase(baseDatos, sentenciaSQL, resultado);
            return listaClientes;

        } else
            return null;

    }
    //************ Cliente //************ Cliente //************ Cliente //************ Cliente

    //getTabla()

    /**
     * Inserta un nuevo cliente en la base de datos
     * @param cliente El cliente a guardar en la base de datos
     * @throws SQLException
     */
    public void insertCliente(Cliente cliente) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Empleado en tabla EMPLEADO");


            CallableStatement insertCliente = baseDatos.prepareCall("{call cud_pasteleria_pkg.agrCliente(?,?)");

            insertCliente.setString(1, cliente.getNombre());
            insertCliente.setString(2, cliente.getTelefono());
            insertCliente.execute();

            cerrarConexionBase(baseDatos, insertCliente);
        }
    }

    /**
     * Modifica un cliente en la base de datos
     * @param cliente CLiente a modificar
     * @throws SQLException
     */
    public void updateCliente( Cliente cliente) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Cliente");

            CallableStatement updateCliente = baseDatos.prepareCall("{call cud_pasteleria_pkg.modCliente(?,?,?)");

            updateCliente.setInt(1, Integer.parseInt(cliente.getId()));
            updateCliente.setString(2, cliente.getNombre());
            updateCliente.setString(3, cliente.getTelefono());

            updateCliente.execute();
            cerrarConexionBase(baseDatos, updateCliente);
        }
    }

    /**
     * Elimina un cliente de la base de datos con su id
     * @param idCliente
     * @throws SQLException
     */
    public void deleteCliente(int idCliente) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Empleado en tabla EMPLEADO");


            CallableStatement delCliente = baseDatos.prepareCall("{call cud_pasteleria_pkg.elmCliente(?)");

            delCliente.setInt(1, idCliente);
            delCliente.execute();


            cerrarConexionBase(baseDatos, delCliente);
        }

    }


    //************ SUCURSAL //************ SUCURSAL //************ SUCURSAL //************ SUCURSAL ************ //

    //getTabla()

    /**
     * Inserta un nuevo Sucursal en la base de datos
     * @param sucursal Sucursal a guardar en la base de datos
     * @throws SQLException
     */
    public void insertSucursal(Sucursal sucursal) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Sucursal en tabla SUCURSAL");


            CallableStatement insertSucursal = baseDatos.prepareCall("{call cud_pasteleria_pkg.createSucursal_Proc(?,?)");

            insertSucursal.setString(1, sucursal.getTelefono());
            insertSucursal.setString(2, sucursal.getDireccion());
            insertSucursal.execute();

            cerrarConexionBase(baseDatos, insertSucursal);
        }
    }

    /**
     * Modifica un Sucursal en la base de datos
     * @param sucursal Sucursal a modificar
     * @throws SQLException
     */
    public void updateSucursal( Sucursal sucursal) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Sucursal");

            CallableStatement updateSucursal = baseDatos.prepareCall("{call cud_pasteleria_pkg.updateSucursal_Proc(?,?,?)");

            updateSucursal.setInt(1, Integer.parseInt(sucursal.getId()));
            updateSucursal.setString(2, sucursal.getTelefono());
            updateSucursal.setString(3, sucursal.getDireccion());

            updateSucursal.execute();
            cerrarConexionBase(baseDatos, updateSucursal);
        }
    }

    /**
     * Elimina una Sucursal de la base de datos con su id
     * @param idSucursal
     * @throws SQLException
     */
    public void deleteSucursal(int idSucursal) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Eliminando Sucursal");


            CallableStatement delSucursal = baseDatos.prepareCall("{call cud_pasteleria_pkg.deleteSucursal_Proc(?)");

            delSucursal.setInt(1, idSucursal);
            delSucursal.execute();


            cerrarConexionBase(baseDatos, delSucursal);
        }

    }

    //************ PRODUCTO //************ PRODUCTO //************ PRODUCTO //************ PRODUCTO ************ //

    //getTabla()

    /**
     * Inserta un nuevo Producto en la base de datos
     * @param producto Producto a guardar en la base de datos
     * @throws SQLException
     */
    public void insertProducto(Producto producto) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Producto en tabla PRODUCTO");


            CallableStatement insertProducto = baseDatos.prepareCall("{call cud_pasteleria_pkg.createProducto_Proc(?,?,?)");

            insertProducto.setString(1, producto.getNombre());
            insertProducto.setString(2, producto.getDescripcion());
            insertProducto.setString(3, producto.getReceta());

            insertProducto.execute();

            cerrarConexionBase(baseDatos, insertProducto);
        }
    }

    /**
     * Modifica un Producto en la base de datos
     * @param producto producto a modificar
     * @throws SQLException
     */
    public void updateProducto( Producto producto) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Producto");

            CallableStatement updateProducto = baseDatos.prepareCall("{call cud_pasteleria_pkg.updateProducto_Proc(?,?,?,?)");

            updateProducto.setInt(1, Integer.parseInt(producto.getId()));
            updateProducto.setString(2, producto.getNombre());
            updateProducto.setString(3, producto.getDescripcion());
            updateProducto.setString(4, producto.getReceta());

            updateProducto.execute();
            cerrarConexionBase(baseDatos, updateProducto);
        }
    }

    /**
     * Elimina una Producto de la base de datos con su id
     * @param idProducto
     * @throws SQLException
     */
    public void deleteProducto(int idProducto) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Eliminando Producto");


            CallableStatement delProducto = baseDatos.prepareCall("{call cud_pasteleria_pkg.deleteProducto_Proc(?)");

            delProducto.setInt(1, idProducto);
            delProducto.execute();


            cerrarConexionBase(baseDatos, delProducto);
        }

    }


    //************ USUARIO //************ USUARIO //************ USUARIO //************ USUARIO ************ //
    //getTabla()

    /**
     * Inserta un nuevo Usuario en la base de datos
     * @param usuario Usuario a guardar en la base de datos
     * @throws SQLException
     */
    public void insertUsuario(Usuario usuario) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Usuario en tabla USUARIO");


            CallableStatement insertUsuario = baseDatos.prepareCall("{call cud_pasteleria_pkg.createUsuario_Proc(?,?,?)");

            insertUsuario.setInt(1, Integer.parseInt(usuario.getId_empleado()));
            insertUsuario.setString(1, usuario.getUsuario());
            insertUsuario.setString(2, usuario.getContrasenia());


            insertUsuario.execute();

            cerrarConexionBase(baseDatos, insertUsuario);
        }
    }

    /**
     * Modifica un Usuario en la base de datos
     * @param usuario producto a modificar
     * @throws SQLException
     */
    public void updateUsuario( Usuario usuario) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Producto");

            CallableStatement updateUsuario = baseDatos.prepareCall("{call cud_pasteleria_pkg.updateUsuario_Proc(?,?,?)");

            updateUsuario.setInt(1, Integer.parseInt(usuario.getId()));
            updateUsuario.setString(2, usuario.getUsuario());
            updateUsuario.setString(3, usuario.getContrasenia());

            updateUsuario.execute();
            cerrarConexionBase(baseDatos, updateUsuario);
        }
    }

    /**
     * Elimina un Usuario de la base de datos con su id
     * @param idUsuario
     * @throws SQLException
     */
    public void deleteUsuario(int idUsuario) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Eliminando Usuario");


            CallableStatement delUsuario = baseDatos.prepareCall("{call cud_pasteleria_pkg.deleteUsuario_Proc(?)");

            delUsuario.setInt(1, idUsuario);
            delUsuario.execute();


            cerrarConexionBase(baseDatos, delUsuario);
        }

    }


    //************ INGREDIENTE //************ INGREDIENTE //************ INGREDIENTE //************ INGREDIENTE ************ //
    //getTabla()

    /**
     * Inserta un nuevo ingrediente en la base de datos
     * @param ingrediente ingreidnete a guardar en la base de datos
     * @throws SQLException
     */
    public void insertIngrediente(Ingrediente ingrediente) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Ingrediente en tabla Ingrediente");


            CallableStatement insertIngrediente = baseDatos.prepareCall("{call cud_pasteleria_pkg.createIngrediente_Proc(?,?)");

            insertIngrediente.setString(1, ingrediente.getNombre());
            insertIngrediente.setString(2, ingrediente.getMedida());

            insertIngrediente.execute();

            cerrarConexionBase(baseDatos, insertIngrediente);
        }
    }

    /**
     * Modifica un ingrediente en la base de datos
     * @param ingrediente ingredinete a modificar
     * @throws SQLException
     */
    public void updateIngrediente( Ingrediente ingrediente) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Ingrediente");

            CallableStatement updateIngrediente = baseDatos.prepareCall("{call cud_pasteleria_pkg.updatepNombre_Ingrediente_Proc(?,?,?)");

            updateIngrediente.setInt(1, Integer.parseInt(ingrediente.getId()));
            updateIngrediente.setString(2, ingrediente.getNombre());
            updateIngrediente.setString(3, ingrediente.getMedida());

            updateIngrediente.execute();
            cerrarConexionBase(baseDatos, updateIngrediente);
        }
    }

    /**
     * Elimina un Ingrediente de la base de datos con su id
     * @param idIngrediente
     * @throws SQLException
     */
    public void deleteIngrediente(int idIngrediente) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Eliminando Ingrediente");


            CallableStatement delIngrediente = baseDatos.prepareCall("{call cud_pasteleria_pkg.deleteIngrediente_Proc(?)");

            delIngrediente.setInt(1, idIngrediente);
            delIngrediente.execute();


            cerrarConexionBase(baseDatos, delIngrediente);
        }

    }


    //************ PROVEEDOR //************ PROVEEDOR //************ PROVEEDOR //************ PROVEEDOR ************ //
    //getTabla()

    /**
     * Inserta un nuevo proveedor en la base de datos
     * @param proveedor proveedor a guardar en la base de datos
     * @throws SQLException
     */
    public void insertProveedor(Proveedor proveedor) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Proveedor en tabla PROVEEDOR");


            CallableStatement insertProveedor = baseDatos.prepareCall("{call cud_pasteleria_pkg.createProveedor_Proc(?,?)");

            insertProveedor.setString(1, proveedor.getNombre());
            insertProveedor.setString(2, proveedor.getDireccion());

            insertProveedor.execute();

            cerrarConexionBase(baseDatos, insertProveedor);
        }
    }

    /**
     * Modifica un proveedor en la base de datos
     * @param proveedor proveedor a modificar
     * @throws SQLException
     */
    public void updateProveedor( Proveedor proveedor) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Proveedor");

            CallableStatement updateProveedor = baseDatos.prepareCall("{call cud_pasteleria_pkg.updateProveedor_Proc(?,?,?)");

            updateProveedor.setInt(1, Integer.parseInt(proveedor.getId()));
            updateProveedor.setString(2, proveedor.getNombre());
            updateProveedor.setString(3, proveedor.getDireccion());

            updateProveedor.execute();
            cerrarConexionBase(baseDatos, updateProveedor);
        }
    }

    /**
     * Elimina un proveedor de la base de datos con su id
     * @param idProveedor
     * @throws SQLException
     */
    public void deleteProveedor(int idProveedor) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Eliminando Proveedor");


            CallableStatement delProveedor = baseDatos.prepareCall("{call cud_pasteleria_pkg.deleteIngrediente_Proc(?)");

            delProveedor.setInt(1, idProveedor);
            delProveedor.execute();

            cerrarConexionBase(baseDatos, delProveedor);
        }

    }


    //************ PRECIOS //************ PRECIOS //************ PRECIOS //************ PRECIOS ************ //
    //getTabla()

    /**
     * Inserta un nuevo precios en la base de datos
     * @param precios precios a guardar en la base de datos
     * @throws SQLException
     */
    public void insertPrecios(Precios precios) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Precios en tabla PRECIOS");


            CallableStatement insertPrecios = baseDatos.prepareCall("{call cud_pasteleria_pkg.createPrecios_Proc(?,?,?,?)");

            insertPrecios.setInt(1, Integer.parseInt(precios.getId_proveedor()));
            insertPrecios.setInt(2, Integer.parseInt(precios.getId_ingrediente()));
            insertPrecios.setInt(3, Integer.parseInt(precios.getPrecios()));
            insertPrecios.setInt(4, Integer.parseInt(precios.getCantidad()));


            insertPrecios.execute();

            cerrarConexionBase(baseDatos, insertPrecios);
        }
    }

    /**
     * Modifica un precios en la base de datos
     * @param precios precios a modificar
     * @throws SQLException
     */
    public void updatePrecios( Precios precios) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Precios");

            CallableStatement updatePrecios = baseDatos.prepareCall("{call cud_pasteleria_pkg.updatepNombre_Precios_Proc(?,?,?,?,?)");

            updatePrecios.setInt(1, Integer.parseInt(precios.getId_proveedor()));
            updatePrecios.setInt(2, Integer.parseInt(precios.getId_ingrediente()));
            updatePrecios.setInt(3, Integer.parseInt(precios.getPrecios()));
            updatePrecios.setInt(4, Integer.parseInt(precios.getCantidad()));

            updatePrecios.execute();
            cerrarConexionBase(baseDatos, updatePrecios);
        }
    }

    /**
     * Elimina un Precio de la base de datos con su id
     * @param idProveedor
     * @param idIngrediente
     * @throws SQLException
     */
    public void deletePrecios(int idIngrediente, int idProveedor) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Eliminando Precios");


            CallableStatement delPrecios = baseDatos.prepareCall("{call cud_pasteleria_pkg.deletePrecios_Proc(?,?)");

            delPrecios.setInt(1, idIngrediente);
            delPrecios.setInt(2, idProveedor);
            delPrecios.execute();

            cerrarConexionBase(baseDatos, delPrecios);
        }

    }

    //************ INVENTARIO //************ INVENTARIO //************ INVENTARIO //************ INVENTARIO ************ //
    //getTabla()

    /**
     * Inserta un nuevo inventario en la base de datos
     * @param inventario inventario a guardar en la base de datos
     * @throws SQLException
     */
    public void insertInventario(Inventario inventario) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Inventario en tabla INVENTARIO");


            CallableStatement insertInventario = baseDatos.prepareCall("{call cud_pasteleria_pkg.createInventarios_Proc(?,?,?)");

            insertInventario.setInt(1, Integer.parseInt(inventario.getId_sucursal()));
            insertInventario.setInt(2, Integer.parseInt(inventario.getId_ingrediente()));
            insertInventario.setInt(3, Integer.parseInt(inventario.getCantidad()));

            insertInventario.execute();

            cerrarConexionBase(baseDatos, insertInventario);
        }
    }

    /**
     * Modifica un inventario en la base de datos
     * @param inventario inventario a modificar
     * @throws SQLException
     */
    public void updateInventario( Inventario inventario) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Inventario");

            CallableStatement updateInventario = baseDatos.prepareCall("{call cud_pasteleria_pkg.updateInventariosProc(?,?,?)");

            updateInventario.setInt(1, Integer.parseInt(inventario.getId_sucursal()));
            updateInventario.setInt(2, Integer.parseInt(inventario.getId_ingrediente()));
            updateInventario.setInt(3, Integer.parseInt(inventario.getCantidad()));

            updateInventario.execute();
            cerrarConexionBase(baseDatos, updateInventario);
        }
    }

    /**
     * Elimina un Inventari de la base de datos con su id
     * @param idSucursal
     * @param idIngrediente
     * @throws SQLException
     */
    public void deleteInventario(int idSucursal, int idIngrediente ) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Eliminando Inventario");


            CallableStatement delInventario = baseDatos.prepareCall("{call cud_pasteleria_pkg.deleteInventarios_Proc(?,?)");

            delInventario.setInt(1, idSucursal);
            delInventario.setInt(2, idIngrediente);
            delInventario.execute();

            cerrarConexionBase(baseDatos, delInventario);
        }

    }


    //************ LISTA_INGREDIENTES //************ LISTA_INGREDIENTES //************ LISTA_INGREDIENTES //************ LISTA_INGREDIENTES ************ //
    //getTabla()

    /**
     * Inserta un nuevo listaIngredientes en la base de datos
     * @param listaIngredientes listaIngredientes a guardar en la base de datos
     * @throws SQLException
     */
    public void insertListaIngredientes(ListaIngredientes listaIngredientes) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Lista Ingredientes en tabla LISTAINGREDIENTES");


            CallableStatement insertListaIngredientes = baseDatos.prepareCall("{call cud_pasteleria_pkg.createListaIngredientes_Proc(?,?,?)");

            insertListaIngredientes.setInt(1, Integer.parseInt(listaIngredientes.getId_producto()));
            insertListaIngredientes.setInt(2, Integer.parseInt(listaIngredientes.getId_ingrediente()));
            insertListaIngredientes.setInt(3, Integer.parseInt(listaIngredientes.getCantidad()));

            insertListaIngredientes.execute();

            cerrarConexionBase(baseDatos, insertListaIngredientes);
        }
    }

    /**
     * Modifica un listaIngredientes en la base de datos
     * @param listaIngredientes listaIngredientes a modificar
     * @throws SQLException
     */
    public void updateListaIngredientes( ListaIngredientes listaIngredientes) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Lista Ingredientes");

            CallableStatement updateListaIngredientes = baseDatos.prepareCall("{call cud_pasteleria_pkg.updateListaIngredientes_Proc(?,?,?)");

            updateListaIngredientes.setInt(1, Integer.parseInt(listaIngredientes.getId_producto()));
            updateListaIngredientes.setInt(2, Integer.parseInt(listaIngredientes.getId_ingrediente()));
            updateListaIngredientes.setInt(3, Integer.parseInt(listaIngredientes.getCantidad()));

            updateListaIngredientes.execute();
            cerrarConexionBase(baseDatos, updateListaIngredientes);
        }
    }

    /**
     * Elimina un Lista Ingrediente de la base de datos con su id
     * @param idProducto
     * @param idIngrediente
     * @throws SQLException
     */
    public void deleteListaIngredientes(int idProducto, int idIngrediente ) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Eliminando Lista Ingredientes");


            CallableStatement delListaIngredientes = baseDatos.prepareCall("{call cud_pasteleria_pkg.deleteListaIngredientes_Proc(?,?)");

            delListaIngredientes.setInt(1, idProducto);
            delListaIngredientes.setInt(2, idIngrediente);
            delListaIngredientes.execute();

            cerrarConexionBase(baseDatos, delListaIngredientes);
        }

    }


    //************ PEDIDO //************ PEDIDO //************ PEDIDO //************ PEDIDO ************ //
    //getTabla()

    /**
     * Inserta un nuevo pedido en la base de datos
     * @param pedido pedido a guardar en la base de datos
     * @throws SQLException
     */
    public void insertPedido(Pedido pedido) throws SQLException {

        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Insertando Pedido en tabla PEDIDO");


            CallableStatement insertPedido = baseDatos.prepareCall("{call cud_pasteleria_pkg.agrPedido(?,?,?,?,?,?,?)");

            insertPedido.setInt(1, Integer.parseInt(pedido.getId_producto()));
            insertPedido.setInt(2, Integer.parseInt(pedido.getId_sucursal()));
            insertPedido.setInt(3, Integer.parseInt(pedido.getId_cliente()));

            insertPedido.setDate(4, Date.valueOf(pedido.getFechaPedido()));   //"YYYY-MM-DD"
            insertPedido.setDate(5, Date.valueOf(pedido.getFechaEntrega()));

            insertPedido.setString(6, pedido.getDireccionEntrega());

            insertPedido.setDate(7, Date.valueOf(pedido.getFechaPedido()));   //La fecha en la que se hizo el pedido, para la bitacora


            insertPedido.execute();

            cerrarConexionBase(baseDatos, insertPedido);
        }
    }

    /**
     * Modifica un pedido en la base de datos
     * @param pedido pedido a modificar
     * @throws SQLException
     */
    public void updatePedido( Pedido pedido) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Modificando Pedido");

            CallableStatement updatePedido = baseDatos.prepareCall("{call cud_pasteleria_pkg.modPedido(?,?,?,?,?,?,?,?)");

            LocalDateTime dtm = LocalDateTime.now(); //Fecha Actual

            updatePedido.setInt(1, Integer.parseInt(pedido.getId()));
            updatePedido.setInt(2, Integer.parseInt(pedido.getId_producto()));
            updatePedido.setInt(3, Integer.parseInt(pedido.getId_sucursal()));
            updatePedido.setInt(4, Integer.parseInt(pedido.getId_cliente()));

            updatePedido.setDate(5, Date.valueOf(pedido.getFechaPedido()));   //"YYYY-MM-DD"
            updatePedido.setDate(6, Date.valueOf(pedido.getFechaEntrega()));

            updatePedido.setString(7, pedido.getDireccionEntrega());

            updatePedido.setDate(8, Date.valueOf(dtm.toLocalDate()));   //La fecha en la que se hizo el pedido, para la bitacora

            updatePedido.execute();
            cerrarConexionBase(baseDatos, updatePedido);
        }
    }

    /**
     * Elimina un  idPedido de la base de datos con su id
     * @param idPedido
     * @throws SQLException
     */
    public void deletePedido(int idPedido ) throws SQLException {
        Connection baseDatos = conectarBaseDatos();

        if (baseDatos != null) {
            System.out.println("Eliminando Pedido");


            CallableStatement delPedido = baseDatos.prepareCall("{call cud_pasteleria_pkg.elmPedido(?,?)");

            LocalDateTime dtm = LocalDateTime.now(); //Fecha Actual

            delPedido.setInt(1, idPedido);
            delPedido.setDate(2, Date.valueOf(dtm.toLocalDate()));   //La fecha en la que se hizo el pedido, para la bitacora

            delPedido.execute();

            cerrarConexionBase(baseDatos, delPedido);
        }
    }
}


