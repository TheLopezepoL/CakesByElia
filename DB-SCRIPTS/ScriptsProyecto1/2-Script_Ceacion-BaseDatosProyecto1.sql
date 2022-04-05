/*
    Instituto Tecnologico de Costa Rica 
    Escuela de Ingenieria en Computacion
    Curso: Bases de Datos 2 
    Profesor. Alberto Shum 

    Script de la Base de Datos del Proyecto 1 
        -Creacion de tablas
        -Creacion de Secuencias
        -Creacion del Paquete de Modificacion 
        
    Grupo de Trabajo #6 

    
    Sebastían Lopez 
    José Murillo 
    Josue Chaves 

    II Semestre 2022 
*/


--*********************************************************************************
--********************************** Creacion de Tablas ***************************
--*********************************************************************************

DROP TABLE BITACORA;
DROP TABLE PEDIDO;
DROP TABLE CLIENTE;

DROP TABLE USUARIO;
DROP TABLE EMPLEADO;

DROP TABLE Precios;
DROP TABLE ListaIngredientes;
DROP TABLE Inventarios;
DROP TABLE Ingrediente;

DROP TABLE SUCURSAL;
DROP TABLE Producto;
DROP TABLE Proveedor;


CREATE TABLE Sucursal (
    id_Sucursal NUMBER,
    telefono_Sucursal VARCHAR2(10) NOT NULL,
    direccion_Sucursal VARCHAR2(30) NOT NULL,
    
    CONSTRAINT pk_Sucursal PRIMARY KEY (id_Sucursal)
    
);

CREATE TABLE Producto (
    Id_Producto NUMBER,
    Nombre_Producto VARCHAR2(50) NOT NULL,
    Descripcion_Producto VARCHAR2(50) NOT NULL,
    Receta_Producto VARCHAR2(500) NOT NULL,
    CONSTRAINT pk_Producto PRIMARY KEY (Id_Producto )
);


CREATE TABLE Cliente(
    id_Cliente INT,
    nombre VARCHAR2(30) NOT NULL,
    telefono VARCHAR2(12) NOT NULL,
    CONSTRAINT pk_Cliente PRIMARY KEY (id_Cliente)
);

CREATE TABLE Pedido(
    id_Pedido INT,
    id_ProductoFK INT,
    id_SucursalFK INT,
    id_ClienteFK INT,
    fecha_Pedido DATE NOT NULL,
    fecha_Entrega DATE NOT NULL,
    direccion_Entrega VARCHAR2(120) NOT NULL,
    CONSTRAINT pk_Pedido PRIMARY KEY (id_Pedido),
    CONSTRAINT fk_Producto FOREIGN KEY (id_ProductoFK) REFERENCES Producto(id_Producto),
    CONSTRAINT fk_Sucursal FOREIGN KEY (id_SucursalFK) REFERENCES Sucursal(id_Sucursal),
    CONSTRAINT fk_Cliente FOREIGN KEY (id_ClienteFK) REFERENCES Cliente(id_Cliente)
);

CREATE TABLE Bitacora(
    id_Bitacora INT,
    id_PedidoFK INT,
    tipo_Cambio VARCHAR2(15) NOT NULL,
    fecha DATE,
    CONSTRAINT pk_Bitacora PRIMARY KEY (id_Bitacora),
    CONSTRAINT fk_Pedido FOREIGN KEY (id_PedidoFK) REFERENCES Bitacora(id_Bitacora)
);

CREATE TABLE Empleado (
    id_Empleado NUMBER,
    id_SucursalFK NUMBER, 
    puesto_Empleado VARCHAR2(20) NOT NULL,
    nombre_Empleado VARCHAR2(20) NOT NULL,
    apellido1_Empleado VARCHAR2(20) NOT NULL,
    apellido2_Empleado VARCHAR2(20) NOT NULL,
    telefono_Empleado VARCHAR2(10) NOT NULL,
    
    CONSTRAINT pk_Empleado PRIMARY KEY (id_Empleado),
    CONSTRAINT fk_SucursalEmpleado FOREIGN KEY (id_SucursalFK) REFERENCES Sucursal(id_Sucursal)
);

CREATE TABLE Usuario (
    id_Usuario NUMBER,
    id_EmpleadoFK NUMBER,
    usuario_Usuario VARCHAR2(10) NOT NULL,
    contrasenia_Usuario VARCHAR2(10) NOT NULL,
    
    CONSTRAINT pk_USUARIO PRIMARY KEY (id_Usuario),
    CONSTRAINT fk_EmpleadoUsuario FOREIGN KEY (id_EmpleadoFK) REFERENCES Empleado(id_Empleado),
    CONSTRAINT unique_Usuario UNIQUE(usuario_Usuario)
);

CREATE TABLE Ingrediente (
    Id_Ingrediente NUMBER,
    Nombre_Ingrediente VARCHAR2(50) NOT NULL,
    Medida_Ingrediente VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_Ingrediente PRIMARY KEY (Id_Ingrediente)
);


CREATE TABLE Proveedor (
    Id_Proveedor NUMBER,
    Nombre_Proveedor VARCHAR2(50) NOT NULL,
    Direccion_Proveedor VARCHAR2(60) NOT NULL,
    CONSTRAINT pk_Proveedor PRIMARY KEY (Id_Proveedor )
);

CREATE TABLE Precios (
    Id_IngredienteFK NUMBER,
    Id_ProveedorFK NUMBER,
    Precio_Precios NUMBER,
    Cantidad_Precios NUMBER,
    CONSTRAINT pk_Precios PRIMARY KEY (Id_IngredienteFK, Id_ProveedorFK),
    CONSTRAINT FK_PreciosIngrediente foreign key (Id_IngredienteFK) references Ingrediente(Id_Ingrediente),
    CONSTRAINT FK_PreciosProveedor foreign key (Id_ProveedorFK) references Proveedor(Id_Proveedor)
);

CREATE TABLE ListaIngredientes (
    Id_ProductoFK NUMBER,
    Id_IngredienteFK NUMBER,
    Cantidad_ListaIngredientes NUMBER,
    CONSTRAINT pk_ListaIngredientes PRIMARY KEY (Id_ProductoFK, Id_IngredienteFK),
    CONSTRAINT FK_ListaIngredientesProducto foreign key (Id_ProductoFK) references Producto(Id_Producto),
    CONSTRAINT FK_ListaIn_Ingrediente foreign key (Id_IngredienteFK) references Ingrediente(Id_Ingrediente)
);

CREATE TABLE Inventarios (
    id_SucursalFK NUMBER,
    Id_IngredienteFK NUMBER,
    Cantidad_Inventarios NUMBER,
    CONSTRAINT pk_Inventarios PRIMARY KEY (id_SucursalFK, Id_IngredienteFK),
    CONSTRAINT FK_InventariosSucursal foreign key (id_SucursalFK) references Sucursal(id_Sucursal),
    CONSTRAINT FK_InventariosIngrediente foreign key (Id_IngredienteFK) references Ingrediente(Id_Ingrediente)
);

--*********************************************************************************
--********************************** Creacion de Secuencias ***********************
--*********************************************************************************
DROP SEQUENCE CLIENTE_Seq;
DROP SEQUENCE PEDIDO_Seq;
DROP SEQUENCE BITACORA_Seq;

DROP SEQUENCE SUCURSAL_SEQ;
DROP SEQUENCE EMPLEADO_SEQ;
DROP SEQUENCE USUARIO_SEQ;

DROP SEQUENCE Ingrediente_SEQ;
DROP SEQUENCE Producto_SEQ;
DROP SEQUENCE Proveedor_SEQ;
-- DROP SEQUENCE Precios_SEQ;
-- DROP SEQUENCE ListaIngredientes_SEQ;
-- DROP SEQUENCE Inventarios_SEQ;

--Clte Pedi Bita
CREATE SEQUENCE Cliente_Seq
    START WITH 1
    INCREMENT BY 1;
    
CREATE SEQUENCE Pedido_Seq
    START WITH 1
    INCREMENT BY 1;
    
CREATE SEQUENCE Bitacora_Seq
    START WITH 1
    INCREMENT BY 1;

--Sucursal Emp Usu
CREATE SEQUENCE Sucursal_Seq
    START WITH 1 
    INCREMENT BY 1;
    
CREATE SEQUENCE Empleado_Seq
    START WITH 1 
    INCREMENT BY 1;

CREATE SEQUENCE Usuario_Seq
    START WITH 1 
    INCREMENT BY 1;

--Ingr Prod Prove Prec List Inve
CREATE SEQUENCE Ingrediente_Seq
    START WITH 1 
    INCREMENT BY 1;
    
CREATE SEQUENCE Producto_Seq
    START WITH 1 
    INCREMENT BY 1;

CREATE SEQUENCE Proveedor_Seq
    START WITH 1 
    INCREMENT BY 1;



--*********************************************************************************
--********************************** Stored Procedures para CRUD de las Tablas ****
--*********************************************************************************
--Definicion del Paquete CUD_PASTELERIA (CREATE UPDATE DELETE)
CREATE OR REPLACE PACKAGE CUD_PASTELERIA_PKG AS
    --Sucursal
    PROCEDURE createSucursal_Proc (pTelefono NUMBER, pDireccion VARCHAR2);
    PROCEDURE updateSucursal_Proc(pID_Sucursal NUMBER, pTelefono VARCHAR2, pDireccion VARCHAR2 );
    PROCEDURE deleteSucursal_Proc (pId_Sucursal NUMBER);
    --Empleado
    PROCEDURE createEmpleado_Proc(pFK_Sucursal NUMBER, pPuesto VARCHAR2, pNombre VARCHAr2, pApellido1 VARCHAR2, pApellido2 VARCHAR2, pTelefono VARCHAR2);
    PROCEDURE updateEmpleado_Proc(pID_Empleado NUMBER, pFK_Sucursal NUMBER,pNombre VARCHAr2, pApellido1 VARCHAR2, pApellido2 VARCHAR2, pTelefono VARCHAR2, pPuesto VARCHAR2);
    PROCEDURE deleteEmpleado_Proc(pID_Empleado NUMBER);
    --Usuario
    PROCEDURE createUsuario_Proc(pFK_Empleado NUMBER, pUsuario VARCHAR2, pContrasenia VARCHAR2 );
    PROCEDURE updateUsuario_Proc( pId_Usuario NUMBER, pUsuario VARCHAr2, pContrasenia VARCHAR2 );
    PROCEDURE deleteUsuario_Proc(pID_Usuario NUMBER);
    --Ingrediente
    PROCEDURE createIngrediente_Proc (pNombre_Ingrediente VARCHAR2, pMedida_Ingrediente  VARCHAR2);
    PROCEDURE updatepNombre_Ingrediente_Proc(pID_pNombre_Ingrediente NUMBER, pNombre VARCHAR2, pMedida VARCHAR2);
    PROCEDURE deleteIngrediente_Proc (pID_pNombre_Ingrediente NUMBER);
    --Producto
    PROCEDURE createProducto_Proc(pNombre_Producto VARCHAR2, pDescripcion_Producto VARCHAr2, pReceta_Producto VARCHAR2);
    PROCEDURE updateProducto_Proc(pId_Producto NUMBER, pNombre_Producto VARCHAr2, pDescripcion_Producto VARCHAR2, pReceta_Producto VARCHAR2);
    PROCEDURE deleteProducto_Proc(pId_Producto NUMBER);
    --Proveedor
    PROCEDURE createProveedor_Proc(pNombre_Proveedor VARCHAR2, pDireccion_Proveedor VARCHAR2 );
    PROCEDURE updateProveedor_Proc(pId_Proveedor NUMBER, pNombre_Proveedor VARCHAr2, pDireccion_Proveedor VARCHAR2 );
    PROCEDURE deleteProveedor_Proc(pId_Proveedor NUMBER);
    --Precios
    PROCEDURE createPrecios_Proc (pId_IngredienteFK NUMBER, pId_ProveedorFK NUMBER, pPrecio_Precios NUMBER, pCantidad_Precios NUMBER);
    PROCEDURE updatepNombre_Precios_Proc(pId_IngredienteFK NUMBER, pId_ProveedorFK NUMBER, pPrecio_Precios NUMBER, pCantidad_Precios NUMBER);
    PROCEDURE deletePrecios_Proc (pId_IngredienteFK NUMBER, pId_ProveedorFK NUMBER);
    --Lista Ingredientes
    PROCEDURE createListaIngredientes_Proc(pId_ProductoFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_ListaIngredientes NUMBER);
    PROCEDURE updateListaIngredientes_Proc(pId_ProductoFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_ListaIngredientes NUMBER);
    PROCEDURE deleteListaIngredientes_Proc(pId_ProductoFK NUMBER, pId_IngredienteFK NUMBER);
    --Cantidad_Inventario
    PROCEDURE createInventarios_Proc(pid_SucursalFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_Inventarios NUMBER);
    PROCEDURE updateInventariosProc(pid_SucursalFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_Inventarios NUMBER);
    PROCEDURE deleteInventarios_Proc(pid_SucursalFK NUMBER, pId_IngredienteFK NUMBER);

    --Bitacora
    

END CUD_PASTELERIA_PKG;
/


--Definicion del Package Body 
CREATE OR REPLACE PACKAGE BODY CUD_PASTELERIA_PKG AS

    --***************************   Procedimientos CRUD para Sucursal   ***************************--

    --Create Sucursal 
    PROCEDURE createSucursal_Proc (pTelefono NUMBER, pDireccion VARCHAR2)
        IS
        BEGIN
        INSERT INTO Sucursal values (Sucursal_Seq.nextVAL, pTelefono, pDireccion);
        --COMMIT;
    END createSucursal_Proc;
    

    --UPDATE Sucursal 
    PROCEDURE updateSucursal_Proc(pID_Sucursal NUMBER, pTelefono VARCHAR2, pDireccion VARCHAR2 )
        IS
        BEGIN
        UPDATE Sucursal SET 
            telefono_Sucursal = pTelefono,
            direccion_Sucursal = pDireccion
        WHERE id_Sucursal = pID_Sucursal;
        --COMMIT;
    END updateSucursal_Proc;

    
    --DELETE Sucursal
    PROCEDURE deleteSucursal_Proc (pId_Sucursal NUMBER)
        IS
        BEGIN 
        DELETE FROM Sucursal WHERE id_Sucursal = pId_Sucursal;
        --COMMIT;
    END deleteSucursal_Proc;
    

    --***************************   Procedimientos CRUD para Empleado   ***************************--

    --CREATE Empleado
    PROCEDURE createEmpleado_Proc(pFK_Sucursal NUMBER, pPuesto VARCHAR2, pNombre VARCHAr2, pApellido1 VARCHAR2, pApellido2 VARCHAR2, pTelefono VARCHAR2)
        IS
        BEGIN
        INSERT INTO Empleado VALUES (Empleado_Seq.nextval, pFK_Sucursal,pPuesto, pNombre, pApellido1, pApellido2, pTelefono);
        --COMMIT
    END createEmpleado_Proc;
    

    PROCEDURE updateEmpleado_Proc(pID_Empleado NUMBER, pFK_Sucursal NUMBER,pNombre VARCHAr2, pApellido1 VARCHAR2, pApellido2 VARCHAR2, pTelefono VARCHAR2, pPuesto VARCHAR2)
        IS
        BEGIN
        UPDATE Empleado SET
            id_SucursalFK = pFK_Sucursal,
            telefono_Empleado = pTelefono,
            nombre_Empleado = pNombre,
            apellido1_Empleado = pApellido1,
            apellido2_Empleado = pApellido2,
            puesto_Empleado = pPuesto
        WHERE id_Empleado = pID_Empleado;
        --COMMIT;
    END updateEmpleado_Proc;


    --DELETE Empleado
    PROCEDURE deleteEmpleado_Proc(pID_Empleado NUMBER)
        IS
        BEGIN
        DELETE FROM Empleado WHERE id_Empleado = pID_Empleado;
        --COMMIT;
    END deleteEmpleado_Proc;
    

    --***************************   Procedimientos CRUD para Usuario   ***************************--

    --Create Usuario
    PROCEDURE createUsuario_Proc(pFK_Empleado NUMBER, pUsuario VARCHAR2, pContrasenia VARCHAR2 )
        IS
        BEGIN
        INSERT INTO Usuario VALUES (Usuario_Seq.nextval, pFK_Empleado, pUsuario, pContrasenia); 
        --COMMIT;
    END createUsuario_Proc;

    --UPDATE Sucursal
    PROCEDURE updateUsuario_Proc( pId_Usuario NUMBER, pUsuario VARCHAr2, pContrasenia VARCHAR2 )
        IS
        BEGIN
        UPDATE Usuario SET
            usuario_Usuario = pUsuario,
            contrasenia_Usuario = pContrasenia
        WHERE id_Usuario = pId_Usuario;
        --COMMIT;
    END updateUsuario_Proc;
    

    --Delete Usuario 
    PROCEDURE deleteUsuario_Proc(pID_Usuario NUMBER)
    IS
    BEGIN 
    DELETE FROM USUARIO WHERE id_Usuario = pId_Usuario;
    --COMMIT;
    END deleteUsuario_Proc;
    

    --***************************   Procedimientos CRUD para Ingrediente   ***************************--

    --Create Ingrediente 
    PROCEDURE createIngrediente_Proc (pNombre_Ingrediente VARCHAR2, pMedida_Ingrediente  VARCHAR2)
        IS
        BEGIN
        INSERT INTO Ingrediente values (Ingrediente_Seq.nextVAL, pNombre_Ingrediente, pMedida_Ingrediente);
        --COMMIT;
    END createIngrediente_Proc;
    

    --UPDATE Ingrediente 
    PROCEDURE updatepNombre_Ingrediente_Proc(pID_pNombre_Ingrediente NUMBER, pNombre VARCHAR2, pMedida VARCHAR2)
        IS
        BEGIN
        UPDATE Ingrediente SET 
                Nombre_Ingrediente  = pNombre,
                Medida_Ingrediente = pMedida
        WHERE Id_Ingrediente = pID_pNombre_Ingrediente;
        --COMMIT;
    END updatepNombre_Ingrediente_Proc;
    
    
    --DELETE Ingrediente
    PROCEDURE deleteIngrediente_Proc (pID_pNombre_Ingrediente NUMBER)
        IS
        BEGIN 
        DELETE FROM Ingrediente WHERE Id_Ingrediente = pID_pNombre_Ingrediente;
        --COMMIT;
    END deleteIngrediente_Proc;
    

    --***************************   Procedimientos CRUD para Producto    ***************************--

    --CREATE Producto 
    PROCEDURE createProducto_Proc(pNombre_Producto VARCHAR2, pDescripcion_Producto VARCHAr2, pReceta_Producto VARCHAR2)
        IS
        BEGIN
        INSERT INTO Producto VALUES (Producto_Seq.nextval, pNombre_Producto, pDescripcion_Producto, pReceta_Producto);
        --COMMIT
    END createProducto_Proc;
    

    --UPDATE Producto 
    PROCEDURE updateProducto_Proc(pId_Producto NUMBER, pNombre_Producto VARCHAr2, pDescripcion_Producto VARCHAR2, pReceta_Producto VARCHAR2)
        IS
        BEGIN
        UPDATE Producto SET
            Nombre_Producto = pNombre_Producto,
            Descripcion_Producto = pDescripcion_Producto,
            Receta_Producto = pReceta_Producto
        WHERE Id_Producto = pId_Producto;
        --COMMIT;
    END updateProducto_Proc;
    

    --DELETE Producto 
    PROCEDURE deleteProducto_Proc(pId_Producto NUMBER)
        IS
        BEGIN
        DELETE FROM Producto WHERE Id_Producto = pId_Producto;
        --COMMIT;
    END deleteProducto_Proc;
    

    --***************************   Procedimientos CRUD para Proveedor   ***************************--

    --Create Proveedor
    PROCEDURE createProveedor_Proc(pNombre_Proveedor VARCHAR2, pDireccion_Proveedor VARCHAR2 )
        IS
        BEGIN
        INSERT INTO Proveedor VALUES (Proveedor_Seq.nextval, pNombre_Proveedor, pDireccion_Proveedor); 
        --COMMIT;
    END createProveedor_Proc;
    

    --UPDATE Proveedor
    PROCEDURE updateProveedor_Proc(pId_Proveedor NUMBER, pNombre_Proveedor VARCHAr2, pDireccion_Proveedor VARCHAR2 )
        IS
        BEGIN
        UPDATE Proveedor SET
            Nombre_Proveedor = pNombre_Proveedor,
            Direccion_Proveedor = pDireccion_Proveedor
        WHERE Id_Proveedor = pId_Proveedor;
        --COMMIT;
    END updateProveedor_Proc;
    

    --Delete Proveedor 
    PROCEDURE deleteProveedor_Proc(pId_Proveedor NUMBER)
    IS
    BEGIN 
    DELETE FROM Proveedor WHERE Id_Proveedor = pId_Proveedor;
    --COMMIT;
    END deleteProveedor_Proc;
    


    --***************************   Procedimientos CRUD para Precios   ***************************--

    --Create Precios 
    PROCEDURE createPrecios_Proc (pId_IngredienteFK NUMBER, pId_ProveedorFK NUMBER, pPrecio_Precios NUMBER, pCantidad_Precios NUMBER)
        IS
        BEGIN
        INSERT INTO Precios values (pId_IngredienteFK, pId_ProveedorFK, pPrecio_Precios, pCantidad_Precios);
        --COMMIT;
    END createPrecios_Proc;
    

    --UPDATE Precios 
    PROCEDURE updatepNombre_Precios_Proc(pId_IngredienteFK NUMBER, pId_ProveedorFK NUMBER, pPrecio_Precios NUMBER, pCantidad_Precios NUMBER)
        IS
        BEGIN
        UPDATE Precios SET 
                Precio_Precios  = pPrecio_Precios,
                Cantidad_Precios = pCantidad_Precios
        WHERE Id_IngredienteFK = pId_IngredienteFK and Id_ProveedorFK = pId_ProveedorFK;
        --COMMIT;
    END updatepNombre_Precios_Proc;
    
        
    --DELETE Precios
    PROCEDURE deletePrecios_Proc (pId_IngredienteFK NUMBER, pId_ProveedorFK NUMBER)
        IS
        BEGIN 
        DELETE FROM Precios WHERE Id_IngredienteFK = pId_IngredienteFK and Id_ProveedorFK = pId_ProveedorFK;
        --COMMIT;
    END deletePrecios_Proc;


    --***************************   Procedimientos CRUD para ListaIngredientes     ***************************--

    --CREATE ListaIngredientes  
    PROCEDURE createListaIngredientes_Proc(pId_ProductoFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_ListaIngredientes NUMBER)
        IS
        BEGIN
        INSERT INTO ListaIngredientes VALUES (pId_ProductoFK, pId_IngredienteFK , pCantidad_ListaIngredientes);
        --COMMIT
    END createListaIngredientes_Proc;
    

    --UPDATE ListaIngredientes  
    PROCEDURE updateListaIngredientes_Proc(pId_ProductoFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_ListaIngredientes NUMBER)
        IS
        BEGIN
        UPDATE ListaIngredientes  SET
            Cantidad_ListaIngredientes = pCantidad_ListaIngredientes
        WHERE Id_ProductoFK = pId_ProductoFK And Id_IngredienteFK = pId_IngredienteFK;
        --COMMIT;
    END updateListaIngredientes_Proc;
    

    --DELETE ListaIngredientes  
    PROCEDURE deleteListaIngredientes_Proc(pId_ProductoFK NUMBER, pId_IngredienteFK NUMBER)
        IS
        BEGIN
        DELETE FROM ListaIngredientes WHERE Id_ProductoFK = pId_ProductoFK And Id_IngredienteFK = pId_IngredienteFK;
        --COMMIT;
    END deleteListaIngredientes_Proc;
    

    --***************************   Procedimientos CRUD para Cantidad_Inventarios    ***************************--

    --Create Cantidad_Inventarios 
    PROCEDURE createInventarios_Proc(pid_SucursalFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_Inventarios NUMBER)
        IS
        BEGIN
        INSERT INTO Inventarios VALUES(pid_SucursalFK, pId_IngredienteFK, pCantidad_Inventarios); 
        --COMMIT;
    END createInventarios_Proc;
    

    --UPDATE Cantidad_Inventarios 
    PROCEDURE updateInventariosProc(pid_SucursalFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_Inventarios NUMBER)
        IS
        BEGIN
        UPDATE Inventarios  SET
            Cantidad_Inventarios = pCantidad_Inventarios
        WHERE id_SucursalFK  = pid_SucursalFK And Id_IngredienteFK = pId_IngredienteFK;
        --COMMIT;
    END updateInventariosProc;
    

    --Delete Usuario 
    PROCEDURE deleteInventarios_Proc(pid_SucursalFK NUMBER, pId_IngredienteFK NUMBER)
    IS
    BEGIN 
    DELETE FROM Inventarios WHERE id_SucursalFK  = pid_SucursalFK And Id_IngredienteFK = pId_IngredienteFK;
    --COMMIT;
    END deleteInventarios_Proc; 

END CUD_PASTELERIA_PKG;
/


