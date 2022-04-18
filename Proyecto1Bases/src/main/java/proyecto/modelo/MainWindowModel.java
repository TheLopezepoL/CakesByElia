package proyecto.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.Inet4Address;
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

    public void updateempleado(Empleado empleado) throws SQLException {
        oracleJBDC.updateEmpleado(empleado);
    }


    //************ Cliente //************ Cliente //************ Cliente //************ Cliente ************ //

    public void insertNuevoCliente( String[] infoCliente ) throws SQLException  {
        Cliente nuevoCliente = new Cliente("0", infoCliente[0], infoCliente[1]);
        System.out.println("El nuevo elemento es " + nuevoCliente);
        oracleJBDC.insertCliente(nuevoCliente);
    }

    public void updateCliente( Cliente clienteMod ) throws SQLException  {
        oracleJBDC.updateCliente(clienteMod);
    }

    public void deleteCliente( int id_cliente_del ) throws SQLException  {
        oracleJBDC.deleteCliente(id_cliente_del);
    }

    //************ SUCURSAL //************ SUCURSAL //************ SUCURSAL //************ SUCURSAL ************ //
    public void insertNuevoSucursal( String[] infoSucursal ) throws SQLException  {
        Sucursal nuevaSucursal = new Sucursal("0", infoSucursal[0], infoSucursal[1]);
        System.out.println("El nuevo elemento es " + nuevaSucursal);
        oracleJBDC.insertSucursal(nuevaSucursal);
    }

    public void updateSucursal( Sucursal sucursalMod ) throws SQLException  {
        oracleJBDC.updateSucursal(sucursalMod);
    }

    public void deleteSucursal( int idSucursal ) throws SQLException  {
        oracleJBDC.deleteSucursal(idSucursal);
    }

    //************ PRODUCTO //************ PRODUCTO //************ PRODUCTO //************ PRODUCTO ************ //
    public void insertNuevoProducto( String[] infoProducto ) throws SQLException  {
        Producto nuevoProducto = new Producto("0", infoProducto[0], infoProducto[1], infoProducto[2]);
        System.out.println("El nuevo elemento es " + nuevoProducto);
        oracleJBDC.insertProducto(nuevoProducto);
    }

    public void updateProducto( Producto productoMod ) throws SQLException  {
        oracleJBDC.updateProducto(productoMod);
    }

    public void deleteProducto( int idProducto ) throws SQLException  {
        oracleJBDC.deleteProducto(idProducto);
    }

    //************ USUARIO //************ USUARIO //************ USUARIO //************ USUARIO ************ //
    public void insertNuevoUsuario( String[] infoUsuario ) throws SQLException  {
        Usuario nuevoUsuario = new Usuario("0", infoUsuario[0], infoUsuario[1], infoUsuario[2]);
        System.out.println("El nuevo elemento es " + nuevoUsuario);
        oracleJBDC.insertUsuario(nuevoUsuario);
    }

    public void updateUsuario( Usuario usuarioMod ) throws SQLException  {
        oracleJBDC.updateUsuario(usuarioMod);
    }

    public void deleteUsuario( int idUsuario ) throws SQLException  {
        oracleJBDC.deleteUsuario(idUsuario);
    }

    //************ INGREDIENTE //************ INGREDIENTE //************ INGREDIENTE //************ INGREDIENTE ************ //
    public void insertNuevoIngrediente( String[] infoIngrediente ) throws SQLException  {
        Ingrediente nuevoIngrediente = new Ingrediente("0", infoIngrediente[0], infoIngrediente[1]);
        System.out.println("El nuevo elemento es " + nuevoIngrediente);
        oracleJBDC.insertIngrediente(nuevoIngrediente);
    }

    public void updateIngrediente( Ingrediente ingredienteMod ) throws SQLException  {
        oracleJBDC.updateIngrediente(ingredienteMod);
    }

    public void deleteIngrediente( int idIngrediente ) throws SQLException  {
        oracleJBDC.deleteIngrediente(idIngrediente);
    }

    //************ PROVEEDOR //************ PROVEEDOR //************ PROVEEDOR //************ PROVEEDOR ************ //
    public void insertNuevoProveedor( String[] infoProveedor ) throws SQLException  {
        Proveedor nuevoProveedor = new Proveedor("0", infoProveedor[0], infoProveedor[1]);
        System.out.println("El nuevo elemento es " + nuevoProveedor);
        oracleJBDC.insertProveedor(nuevoProveedor);
    }

    public void updateProveedor( Proveedor proveedorMod ) throws SQLException  {
        oracleJBDC.updateProveedor(proveedorMod);
    }

    public void deleteProveedor( int idProveedor ) throws SQLException  {
        oracleJBDC.deleteProveedor(idProveedor);
    }

    //************ PRECIOS //************ PRECIOS //************ PRECIOS //************ PRECIOS ************ //
    public void insertNuevoPrecios( String[] infoPrecios ) throws SQLException  {
        Precios nuevoPrecios = new Precios(infoPrecios[0], infoPrecios[1], infoPrecios[2],infoPrecios[3]);
        System.out.println("El nuevo elemento es " + nuevoPrecios);
        oracleJBDC.insertPrecios(nuevoPrecios);
    }

    public void updatePrecios( Precios preciosMod ) throws SQLException  {
        oracleJBDC.updatePrecios(preciosMod);
    }

    public void deletePrecios(int idIngrediente, int idProveedor ) throws SQLException  {
        oracleJBDC.deletePrecios(idIngrediente,idProveedor);
    }

    //************ INVENTARIO //************ INVENTARIO //************ INVENTARIO //************ INVENTARIO ************ //
    public void insertNuevoInventario( String[] infoInventario ) throws SQLException  {
        Inventario nuevoInventario = new Inventario(infoInventario[0], infoInventario[1], infoInventario[2]);
        System.out.println("El nuevo elemento es " + nuevoInventario);
        oracleJBDC.insertInventario(nuevoInventario);
    }

    public void updateInventario( Inventario inventarioMod ) throws SQLException  {
        oracleJBDC.updateInventario(inventarioMod);
    }

    public void deleteInventario(int idSucursal, int idIngrediente ) throws SQLException  {
        oracleJBDC.deleteInventario(idSucursal, idIngrediente);
    }

}

