package proyecto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proyecto.modelo.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class MainWindowController {

    MainWindowModel mainWindowModel = null;

    //Vista 1 Tablas
    @FXML
    private VBox vbox_botones_tablas;
    @FXML
    private Label label_Tablas;
    @FXML
    private Button btn_Tabla1;

    //Vista 2: Vista Principal de Las tablas y menu
    //Menu de opciones de la tabla que se está viendo en ese momento
    @FXML
    private Button btn_menu_nuevo;
    @FXML
    private Button btn_menu_actualizar;
    @FXML
    private Button btn_menu_borrar;
    @FXML
    private TextField tf_menu_buscar;
    @FXML
    private SplitMenuButton btn_menu_buscar;
    //Anchor Pane Para Tbala
    @FXML
    private AnchorPane anchorPane_paraTabla;

    @FXML
    private BorderPane borderPane_centerTable;

    //Vista3: Menú derecha, Reportes y Info de la sesión y Cerrar sesión
    //Reportes
    @FXML
    private Label label_Reportes;
    @FXML
    private Button btn_Reporte1;

    //Sesión
    @FXML
    private Label label_SesionActual;
    @FXML
    private Label label_NombreUsuario;
    @FXML
    private Label label_IdEmpleado;
    @FXML
    private Label label_PuestoEmpleado;
    @FXML
    private Button btn_CerrarSesion;



    //Constructor
    public void setInformacionSesion( Empleado pEmpleado ) throws SQLException {
        mainWindowModel = new MainWindowModel( pEmpleado );
        label_NombreUsuario.setText(mainWindowModel.getEmpleado().getNombre() + mainWindowModel.getEmpleado().getApellido1());
        label_IdEmpleado.setText( "" + mainWindowModel.getEmpleado().getId());
        label_PuestoEmpleado.setText("Id: " + mainWindowModel.getEmpleado().getPuesto());

        setBotonesTablas(this.mainWindowModel.getListaTablas()); //Carga los botones de la izquierda para acceder a las tablas
        cargarTabla("EMPLEADO");                                           // Carga una tabla en el centro de la pantalla, mientras es temporal

    }

    //Crea botones de las tablas y los agrega a la vista de los botones.
    private void setBotonesTablas(ArrayList<String> pListaTabaslUser){
        //vbox_botones_tablas.getChildren().removeAll();// //Elimina todos luego los pone
        for (String nombreTabla: pListaTabaslUser) {
            Button tempButton = new Button(nombreTabla);
            tempButton.setId("btn_" + nombreTabla);
            tempButton.setOnAction(this::handlerBotonesMain);
            vbox_botones_tablas.getChildren().add(tempButton);
        }
    }

    //Todos los botones, excepto el botón de buscar, que no sé como manejar ese todavía, fijo es parecido, pero who knows
    //Deben estar los botones de cada tabla
    //Que llaman a cargar la tabla de la GUI con el nombre de esa tabla.
    public void handlerBotonesMain(Event e ){

        String id = ((Node) e.getSource()).getId();

        System.out.println("Boton presionado: " + id);

        switch(id) {
            case "btn_menu_buscar":
                System.out.println("Iniciando Busqueda");
                busquedaTabla( btn_menu_buscar.getText() );
                break;
            case "btn_menu_nuevo":
                System.out.println("Insert en la tabla");
                insertarNuevo();
                break;
            case "btn_menu_actualizar":
                System.out.println("Update de la fila seleccionada en la tabla");
                actualizarElemento();
                break;
            case "btn_menu_borrar":
                System.out.println("Borrar la fila seleccionada en la tabla");
                eliminarElemento();
                break;
            case"btn_CerrarSesion":
                System.out.println("Cerrando Sesion");
                cerrarSesion();
                break;
            default:

                cargarTabla(id);  //Le da el Id para saber que tabla cargar
                break;
        }
    }


    //Ejemplo de como cargar la tabla
    private void cargarTabla(String id){
        //Crear una nueva Table View, del tipo de datos que se le va a ingresar,
        //Crear las columnas, con strings
        //Agregar el table view al anchor pane

        //Elimina la Tabla Actual antes de cargar la siguiente:
        try{
            borderPane_centerTable.getChildren().remove(1) ; //Index 0 es la barra de herramientas de arriba
        }catch (Exception e){
            System.out.println("Error al Eliminar tabla, No existe la tabla");
        }

        //Aca sabemos el nombre de la tabla a cargar

        String tablaCargar = id.replace("btn_","");

        switch (tablaCargar){
            case "EMPLEADO":
                //Acá debemos pedir al MainWindowModel la lista de ObservableList de la base de datos

                ObservableList<Empleado> empleados = null;
                try {
                    empleados = mainWindowModel.getEmpleadosTabla();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("No se pudo cargar la tabla de empleados");
                }
                //Le pasamos los datos y el nombre de la tabla a la funcion de cargar la tabla de empleado
                cargarTablaEmpleado(empleados, tablaCargar);
                break;
            case "SUCURSAL":
                //cargarTablaSucursal();
                break;
            case "PRODUCTO":
                //cargarTablaProducto();
                break;
            case "CLIENTE":
                //cargarTablaCliente();
                break;
            case "PEDIDO":
                //cargarTablaPedido();
                break;
            case "BITACORA":
                //cargarTablaBitacora();
                break;
            case "USUARIO":
                //cargarTablaUsuario();
                break;
            case "INGREDIENTE":
                //cargarTablaIngrediente();
                break;
            case "PROVEEDOR":
                //cargarTablaProveedor();
                break;
            case "PRECIOS":
                //cargarTablaPrecios();
                break;
            case "LISTAINGREDIENTES":
                //cargarTablaListaIngredientes();
                break;
            case "INVENTARIOS":
                //cargarTablaInventarios();
                break;
        }

    }

    //Carga la Tabla de Empleados en la Vista
    private void cargarTablaEmpleado( ObservableList<Empleado> listaEmpleados, String idTabla ){
        //
        System.out.println("Cargando Tabla EMPLEADO");

        //Le dice al Modelo la tabla que está a punto de cargar
        mainWindowModel.setTablaActual(idTabla);

        //Crea las columnas de la tabla
        TableColumn<Empleado, String> idColumn = new TableColumn<>("id_empleado");
        idColumn.setCellValueFactory( new PropertyValueFactory<>("id"));

        TableColumn<Empleado, String> idSucursalColumn = new TableColumn<>("id_sucursal");
        idSucursalColumn.setCellValueFactory( new PropertyValueFactory<>("id_sucursal"));
        idSucursalColumn.setEditable(true);                                                        //Para poder editar
        idSucursalColumn.setCellFactory(TextFieldTableCell.forTableColumn());                      //Para que cuando se presione se coloque un textfiel
        idSucursalColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() { //Cuando se edita el textfield entonces se cambia el elemento en la lista
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setId_sucursal(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableColumn<Empleado, String> puestoColum = new TableColumn<>("puesto");
        puestoColum.setCellValueFactory( new PropertyValueFactory<>("puesto"));
        puestoColum.setEditable(true);
        puestoColum.setCellFactory(TextFieldTableCell.forTableColumn());
        puestoColum.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setPuesto(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableColumn<Empleado, String> nombreColumn = new TableColumn<>("nombre");
        nombreColumn.setCellValueFactory( new PropertyValueFactory<>("nombre"));
        nombreColumn.setEditable(true);
        nombreColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nombreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setNombre(empleadoStringCellEditEvent.getNewValue());
            }
        });


        TableColumn<Empleado, String> ap1Column = new TableColumn<>("apellido1");
        ap1Column.setCellValueFactory( new PropertyValueFactory<>("apellido1"));
        ap1Column.setEditable(true);
        ap1Column.setCellFactory(TextFieldTableCell.forTableColumn());
        ap1Column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setApellido1(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableColumn<Empleado, String> ap2Column = new TableColumn<>("apellido2");
        ap2Column.setCellValueFactory( new PropertyValueFactory<>("apellido2"));
        ap2Column.setEditable(true);
        ap2Column.setCellFactory(TextFieldTableCell.forTableColumn());
        ap2Column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setApellido2(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableColumn<Empleado, String> telColumn = new TableColumn<>("telefono");
        telColumn.setCellValueFactory( new PropertyValueFactory<>("telefono"));
        telColumn.setEditable(true);
        telColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        telColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Empleado, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Empleado, String> empleadoStringCellEditEvent) {
                Empleado empleadoEditado = empleadoStringCellEditEvent.getRowValue();
                empleadoEditado.setTelefono(empleadoStringCellEditEvent.getNewValue());
            }
        });

        TableView<Empleado> tablaEmpleado = new TableView<>(); //Se crea un table view
        tablaEmpleado.setEditable(true);
        tablaEmpleado.setItems(listaEmpleados);                     //Se setean los datos al table view
        tablaEmpleado.setId("tablaActual");      //Tiene que ser siempre el mismo
        tablaEmpleado.getColumns().setAll(idColumn, idSucursalColumn, puestoColum, nombreColumn, ap1Column,ap2Column,telColumn); //Se agregan las columnas a la vista

        borderPane_centerTable.setCenter(tablaEmpleado); //Se coloca el Table View en el centro del Border Pane
    }

    //Consigue la tabla de MainTableWindowModel
    //Elimina el elemento
    private void eliminarElemento () {

        System.out.println("Eliminando Elemento");

        //Acá habría que poner un case para cada tabla
        switch (mainWindowModel.getTablaActual()){
            case "EMPLEADO":
                //Acá hay que llamar al tablewindowmodel que le diga al JBDC que elimine el elemento
                // Que luego nos devuelva la tabla que acaba de modificar
                //cargar la tabla de nuevo
                try {
                    Empleado empleadoAEliminar = ((TableView<Empleado>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    //Se manda a eliminar el elemento del empleado con el id
                    System.out.println("Eliminando Empleado con id "+ empleadoAEliminar.getId() + " y nombre " + empleadoAEliminar.getNombre());

                    mainWindowModel.deleteEmpleado( Integer.parseInt( empleadoAEliminar.getId() ) );  //ELIMINA EL EMPLEADO
                    cargarTabla("EMPLEADO");                                                      //VUELVE A CARGAR LA TABLA
                } catch (Exception e){
                    mostrarAlerta(1, "No ha seleccionado ningún Empleado","De click sobre el empleado que quiere eliminar").showAndWait();
                    System.out.println(e.toString());
                }
                break;
            case "SUCURSAL":
                try {
                    Sucursal sucursalEliminar = ((TableView<Sucursal>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    //Se manda a eliminar el elemento del empleado con el id
                    System.out.println("Eliminando Sucursal con id "+ sucursalEliminar.getId() + " y direccion " + sucursalEliminar.getDireccion());

                    mainWindowModel.deleteSucursal( Integer.parseInt( sucursalEliminar.getId() ) );  //ELIMINA EL elemento
                    cargarTabla("SUCURSAL");                                                      //VUELVE A CARGAR LA TABLA
                } catch (Exception e){
                    mostrarAlerta(1, "No ha seleccionado ningúna Sucursal o es posible que tenga empleados asociados y no se pueda eliminar","De click sobre la Sucursal que quiere eliminar").showAndWait();
                    System.out.println(e.toString());
                }
                break;

            case "PRODUCTO":
                try {
                    Producto productoEliminar = ((TableView<Producto>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    //Se manda a eliminar el elemento del empleado con el id
                    System.out.println("Eliminando Producto con id "+ productoEliminar.getId() + " y nombre " + productoEliminar.getNombre());

                    mainWindowModel.deleteProducto( Integer.parseInt( productoEliminar.getId() ) );  //ELIMINA EL elemento
                    cargarTabla("PRODUCTO");                                                      //VUELVE A CARGAR LA TABLA
                } catch (Exception e){
                    mostrarAlerta(1, "No ha seleccionado ningún Producto","De click sobre el Producto que quiere eliminar").showAndWait();
                    System.out.println(e.toString());
                }
                break;
            case "CLIENTE":

                try {
                    Cliente clienteAEliminar = ((TableView<Cliente>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    //Se manda a eliminar el elemento del empleado con el id
                    System.out.println("Eliminando Cliente con id "+ clienteAEliminar.getId() + " y nombre " + clienteAEliminar.getNombre());

                    mainWindowModel.deleteCliente( Integer.parseInt( clienteAEliminar.getId() ) );  //ELIMINA EL EMPLEADO
                    cargarTabla("CLIENTE");                                                      //VUELVE A CARGAR LA TABLA
                } catch (Exception e){
                    mostrarAlerta(1, "No ha seleccionado ningún Cliente","De click sobre el cliente que quiere eliminar").showAndWait();
                    System.out.println(e.toString());
                }
                break;
            case "PEDIDO":
                //cargarTablaPedido();
                break;
            case "BITACORA":
                //cargarTablaBitacora();
                break;
            case "USUARIO":

                try {
                    Usuario usuarioEliminar = ((TableView<Usuario>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    System.out.println("Eliminando Usuario con id " + usuarioEliminar.getId());
                    mainWindowModel.deleteUsuario( Integer.parseInt( usuarioEliminar.getId() ) );  //ELIMINA EL EMPLEADO
                    cargarTabla("USUARIO");                                                      //VUELVE A CARGAR LA TABLA
                } catch (Exception e){
                    mostrarAlerta(1, "No ha seleccionado ningún Usuario","De click sobre el usuario que quiere eliminar").showAndWait();
                    System.out.println(e.toString());
                }
                break;
            case "INGREDIENTE":

                try {
                    Ingrediente ingredienteEliminar = ((TableView<Ingrediente>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    //Se manda a eliminar el elemento del empleado con el id
                    System.out.println("Eliminando Ingrediente con id "+ ingredienteEliminar.getId() + " y nombre " + ingredienteEliminar.getNombre());

                    mainWindowModel.deleteIngrediente( Integer.parseInt( ingredienteEliminar.getId() ) );  //ELIMINA EL EMPLEADO
                    cargarTabla("INGREDIENTE");                                                      //VUELVE A CARGAR LA TABLA
                } catch (Exception e){
                    mostrarAlerta(1, "No ha seleccionado ningún Ingrediente","De click sobre el Ingrediente que quiere eliminar").showAndWait();
                    System.out.println(e.toString());
                }
                break;
            case "PROVEEDOR":
                //cargarTablaProveedor();
                break;
            case "PRECIOS":
                //cargarTablaPrecios();
                break;
            case "LISTAINGREDIENTES":
                //cargarTablaListaIngredientes();
                break;
            case "INVENTARIOS":
                //cargarTablaInventarios();
                break;
        }

    }

    private void actualizarElemento(){

        switch (mainWindowModel.getTablaActual()){
            case "EMPLEADO":
                //Hay que crear un alert view con textfields para cada columna de la tabla actual
                //Tomar los valores de ese alert view y mandarlo a actualizar a la base de datos.
                try{
                    Empleado empleadoAActualizar = ((TableView<Empleado>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    System.out.println("Nuevos Valores del empleado " + empleadoAActualizar);
                    //Alert para confirmar los cambios
                    Optional<ButtonType> result = mostrarAlerta(3, "Desea Actualizar el Empleado con los siguientes datos", empleadoAActualizar.toString()).showAndWait();
                    if (result.get() == ButtonType.OK){
                        // ... user chose OK
                        mainWindowModel.updateempleado(empleadoAActualizar);
                    }

                    cargarTabla("EMPLEADO"); //Vuelve a cargar la tabla
                }catch (Exception e){
                    mostrarAlerta(2, "Error al actualizar Empleado", "Debe tener seleccionado el empleado cuando rpesiona actualizar").showAndWait();
                    System.out.println(e.toString());
                }

                break;
            case "SUCURSAL":
                try{
                    Sucursal sucursalActualizar = ((TableView<Sucursal>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    System.out.println("Nuevos Valores de la Sucursal " + sucursalActualizar);
                    //Alert para confirmar los cambios
                    Optional<ButtonType> result = mostrarAlerta(3, "Desea Actualizar la Sucursal con los siguientes datos", sucursalActualizar.toString()).showAndWait();
                    if (result.get() == ButtonType.OK){
                        mainWindowModel.updateSucursal(sucursalActualizar);
                    }

                    cargarTabla("SUCURSAL"); //Vuelve a cargar la tabla
                }catch (Exception e){
                    mostrarAlerta(2, "Error al actualizar La sucursal", "Debe tener seleccionado  cuando presiona actualizar").showAndWait();
                    System.out.println(e.toString());
                }
                break;

            case "PRODUCTO":
                try{
                    Producto productoActualizar = ((TableView<Producto>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    System.out.println("Nuevos Valores del Producto " + productoActualizar);
                    //Alert para confirmar los cambios
                    Optional<ButtonType> result = mostrarAlerta(3, "Desea Actualizar el producto con los siguientes datos", productoActualizar.toString()).showAndWait();
                    if (result.get() == ButtonType.OK){
                        mainWindowModel.updateProducto(productoActualizar);
                    }

                    cargarTabla("PRODUCTO"); //Vuelve a cargar la tabla
                }catch (Exception e){
                    mostrarAlerta(2, "Error al actualizar el Producto", "Debe tener seleccionado el producto cuando presiona actualizar").showAndWait();
                    System.out.println(e.toString());
                }
                break;

            case "CLIENTE":
                try{
                    Cliente clienteAActualizar = ((TableView<Cliente>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    System.out.println("Nuevos Valores del cliente " + clienteAActualizar);
                    //Alert para confirmar los cambios
                    Optional<ButtonType> result = mostrarAlerta(3, "Desea Actualizar el Cliente con los siguientes datos", clienteAActualizar.toString()).showAndWait();
                    if (result.get() == ButtonType.OK){
                        mainWindowModel.updateCliente(clienteAActualizar);
                    }

                    cargarTabla("CLIENTE"); //Vuelve a cargar la tabla
                }catch (Exception e){
                    mostrarAlerta(2, "Error al actualizar Cliente", "Debe tener seleccionado el cliente cuando presiona actualizar").showAndWait();
                    System.out.println(e.toString());
                }
                break;

            case "PEDIDO":
                //cargarTablaPedido();
                break;
            case "BITACORA":
                //cargarTablaBitacora();
                break;
            case "USUARIO":
                try{
                    Usuario usuarioActualizar = ((TableView<Usuario>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    System.out.println("Nuevos Valores del Usuario " + usuarioActualizar);
                    //Alert para confirmar los cambios
                    Optional<ButtonType> result = mostrarAlerta(3, "Desea Actualizar el usuario con los siguientes datos", usuarioActualizar.toString()).showAndWait();
                    if (result.get() == ButtonType.OK){
                        mainWindowModel.updateUsuario(usuarioActualizar);
                    }

                    cargarTabla("USUARIO"); //Vuelve a cargar la tabla
                }catch (Exception e){
                    mostrarAlerta(2, "Error al actualizar el Usuario", "Debe tener seleccionado el usuario cuando presiona actualizar").showAndWait();
                    System.out.println(e.toString());
                }
                break;
            case "INGREDIENTE":
                try{
                    Ingrediente ingredienteActualizar = ((TableView<Ingrediente>) borderPane_centerTable.getChildren().get(1)).getSelectionModel().getSelectedItem();
                    System.out.println("Nuevos Valores del Ingrediente " + ingredienteActualizar);
                    //Alert para confirmar los cambios
                    Optional<ButtonType> result = mostrarAlerta(3, "Desea Actualizar el ingrediente con los siguientes datos", ingredienteActualizar.toString()).showAndWait();
                    if (result.get() == ButtonType.OK){
                        mainWindowModel.updateIngrediente(ingredienteActualizar);
                    }

                    cargarTabla("INGREDIENTE"); //Vuelve a cargar la tabla
                }catch (Exception e){
                    mostrarAlerta(2, "Error al actualizar el Ingrediente", "Debe tener seleccionado el Ingrediente cuando presiona actualizar").showAndWait();
                    System.out.println(e.toString());
                }
                break;
            case "PROVEEDOR":
                //cargarTablaProveedor();
                break;
            case "PRECIOS":
                //cargarTablaPrecios();
                break;
            case "LISTAINGREDIENTES":
                //cargarTablaListaIngredientes();
                break;
            case "INVENTARIOS":
                //cargarTablaInventarios();
                break;
        }
    }

    private void insertarNuevo (){
        //Saber primero que tabla es la que tenemos en la vista
        //Para sacar la lista de columnas
        String tablaActual = mainWindowModel.getTablaActual();
        System.out.println("Insertar Nuevo Elemento en " + tablaActual);

        TextInputDialog dialog = new TextInputDialog("Valores");
        dialog.setTitle("Nuevo Elemento");
        dialog.setContentText("Nuevo elemento: ");

        dialog.setHeaderText("Ingrese los valores necesarios para la Tabla, separados por coma(,).");

        //Toma el valor de la ventanita
        Optional<String> result = dialog.showAndWait();

        switch (mainWindowModel.getTablaActual()){
            case "EMPLEADO":
                dialog.setHeaderText("Ingrese idSucursal, puesto, nombre, apellido, apellido, telefono ");
                //Hay que crear un alert view con textfields para cada columna de la tabla actual
                //Tomar los valores de ese alert view y mandarlo a actualizar a la base de datos.
                result = dialog.showAndWait();
                if (result.isPresent()){
                    String valoresEmpleado[] = result.get().split(",");

                    try {
                        //Crea el nuevo empleado
                        Empleado nuevoEmpleado = new Empleado("0", valoresEmpleado[0], valoresEmpleado[1],
                                valoresEmpleado[2], valoresEmpleado[3], valoresEmpleado[4], valoresEmpleado[5]);

                        System.out.println("El nuevo elemento es " + nuevoEmpleado);
                        mainWindowModel.insertNuevoEmpleado(nuevoEmpleado);
                        cargarTabla("EMPLEADO"); //Vuelve a cargar la tabla para reflejar los cambios

                    }catch (Exception e){
                        System.out.println(e.toString());
                        mostrarAlerta(2, "No ingresó los valores necesarios o hubo un error con la base de datos", "Intentelo de nuevo").showAndWait();
                    }
                }else {
                    System.out.println("OK no vamos a realizar ningun cambio");
                }
                break;

            case "SUCURSAL":
                dialog.setHeaderText("Ingrese Telefono, Direccion");
                //Hay que crear un alert view con textfields para cada columna de la tabla actual
                //Tomar los valores de ese alert view y mandarlo a actualizar a la base de datos.
                result = dialog.showAndWait();
                if (result.isPresent()){
                    String[] valoresSucursal = result.get().split(",");
                    try {
                        //Crea el nuevo empleado
                        mainWindowModel.insertNuevoSucursal(valoresSucursal);
                        cargarTabla("SUCURSAL"); //Vuelve a cargar la tabla para reflejar los cambios
                    }catch (Exception e){
                        System.out.println(e.toString());
                        mostrarAlerta(2, "No ingresó los valores necesarios o hubo un error con la base de datos", "Intentelo de nuevo").showAndWait();
                    }
                }else {
                    System.out.println("OK no vamos a realizar ningun cambio");
                }
                break;
            case "PRODUCTO":
                dialog.setHeaderText("Ingrese Nombre, Descripcion, Receta");
                //Hay que crear un alert view con textfields para cada columna de la tabla actual
                //Tomar los valores de ese alert view y mandarlo a actualizar a la base de datos.
                result = dialog.showAndWait();
                if (result.isPresent()){
                    String[] valoresProducto = result.get().split(",");
                    try {
                        //Crea el nuevo empleado
                        mainWindowModel.insertNuevoProducto(valoresProducto);
                        cargarTabla("PRODUCTO"); //Vuelve a cargar la tabla para reflejar los cambios
                    }catch (Exception e){
                        System.out.println(e.toString());
                        mostrarAlerta(2, "No ingresó los valores necesarios o hubo un error con la base de datos", "Intentelo de nuevo").showAndWait();
                    }
                }else {
                    System.out.println("OK no vamos a realizar ningun cambio");
                }
                break;
            case "CLIENTE":
                dialog.setHeaderText("Ingrese Nombre, Telefono");
                //Hay que crear un alert view con textfields para cada columna de la tabla actual
                //Tomar los valores de ese alert view y mandarlo a actualizar a la base de datos.
                result = dialog.showAndWait();
                if (result.isPresent()){
                    String[] valoresCliente = result.get().split(",");
                    try {
                        //Crea el nuevo empleado
                        mainWindowModel.insertNuevoCliente(valoresCliente);
                        cargarTabla("CLIENTE"); //Vuelve a cargar la tabla para reflejar los cambios
                    }catch (Exception e){
                        System.out.println(e.toString());
                        mostrarAlerta(2, "No ingresó los valores necesarios o hubo un error con la base de datos", "Intentelo de nuevo").showAndWait();
                    }
                }else {
                    System.out.println("OK no vamos a realizar ningun cambio");
                }
                break;

            case "PEDIDO":
                //cargarTablaPedido();
                break;
            case "BITACORA":
                //cargarTablaBitacora();
                break;
            case "USUARIO":

                dialog.setHeaderText("Ingrese Id_Empleado, Nombre de Usuario, Contraseña");
                //Hay que crear un alert view con textfields para cada columna de la tabla actual
                //Tomar los valores de ese alert view y mandarlo a actualizar a la base de datos.
                result = dialog.showAndWait();
                if (result.isPresent()){
                    String[] valoresUsuario = result.get().split(",");
                    try {
                        //Crea el nuevo empleado
                        mainWindowModel.insertNuevoUsuario(valoresUsuario);
                        cargarTabla("USUARIO"); //Vuelve a cargar la tabla para reflejar los cambios
                    }catch (Exception e){
                        System.out.println(e.toString());
                        mostrarAlerta(2, "No ingresó los valores necesarios o hubo un error con la base de datos", "Intentelo de nuevo").showAndWait();
                    }
                }else {
                    System.out.println("OK no vamos a realizar ningun cambio");
                }
                break;
            case "INGREDIENTE":
                dialog.setHeaderText("Ingrese Nombre, Medida");
                //Hay que crear un alert view con textfields para cada columna de la tabla actual
                //Tomar los valores de ese alert view y mandarlo a actualizar a la base de datos.
                result = dialog.showAndWait();
                if (result.isPresent()){
                    String[] valoresIngrediente = result.get().split(",");
                    try {
                        //Crea el nuevo empleado
                        mainWindowModel.insertNuevoIngrediente(valoresIngrediente);
                        cargarTabla("INGREDIENTE"); //Vuelve a cargar la tabla para reflejar los cambios
                    }catch (Exception e){
                        System.out.println(e.toString());
                        mostrarAlerta(2, "No ingresó los valores necesarios o hubo un error con la base de datos", "Intentelo de nuevo").showAndWait();
                    }
                }else {
                    System.out.println("OK no vamos a realizar ningun cambio");
                }
                break;
            case "PROVEEDOR":
                //cargarTablaProveedor();
                break;
            case "PRECIOS":
                //cargarTablaPrecios();
                break;
            case "LISTAINGREDIENTES":
                //cargarTablaListaIngredientes();
                break;
            case "INVENTARIOS":
                //cargarTablaInventarios();
                break;
        }

       // ObservableList <TableColumn<Empleado,String>> columnasTablaActual = tableView_tablaActual.getColumns();
    }


    //Cuando se Presiona el boton de buscar acá se hace la lógica de la búsqueda
    private void busquedaTabla(String pBusqueda){
        String tablaActual = mainWindowModel.getTablaActual();
        String busqueda = tf_menu_buscar.getText();
        System.out.println("Buscando " + busqueda + " por: " + pBusqueda +" en la tabla: " + tablaActual);
    }

    //Cambia la busqueda con el menu button
    public void cambiarBusqueda(Event e){
        String textMenu = ((MenuItem) e.getSource() ).getText();
        btn_menu_buscar.setText(textMenu);
    }


    //Muestra una alerta al Usuario
    // 1 = Información
    // 2 = Error
    // 3 = Confirmacion
    public Alert mostrarAlerta(int tipoAlerta, String header, String Content){
        Alert alerta;
        switch (tipoAlerta){
            case 1:
                alerta = new Alert(Alert.AlertType.INFORMATION);
                break;
            case 2:
                alerta = new Alert(Alert.AlertType.ERROR);
                break;
            case 3:
                alerta = new Alert(Alert.AlertType.CONFIRMATION);
                break;
            default:
                alerta = new Alert(Alert.AlertType.NONE);
                break;
        }
        alerta.setTitle("Ooops!");
        alerta.setHeaderText(header);
        alerta.setContentText(Content);

        return alerta;
    }

    //Termina la ejecución del programa
    private void cerrarSesion(){

        if(mostrarAlerta(3, "Está a punto de Cerrar Sesión", "¿Quiere Salir?").showAndWait().get() == ButtonType.OK){
            Stage escenario = (Stage) borderPane_centerTable.getScene().getWindow();
            System.out.println("Cerrando Sesion");
            escenario.close();
        }
    }
}



