DROP TABLE USUARIO;
DROP TABLE EMPLEADO;
DROP TABLE SUCURSAL;



--Creacion de Tablas "LogIn"
CREATE TABLE Sucursal (
    id_Sucursal NUMBER,
    telefono_Sucursal VARCHAR2(10) NOT NULL,
    direccion_Sucursal VARCHAR2(30) NOT NULL,
    
    CONSTRAINT pk_Sucursal PRIMARY KEY (id_Sucursal)
    
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


--Creacion de Secuencias 
DROP SEQUENCE SUCURSAL_SEQ;
DROP SEQUENCE EMPLEADO_SEQ;
DROP SEQUENCE USUARIO_SEQ;

CREATE SEQUENCE Sucursal_Seq
    START WITH 1 
    INCREMENT BY 1;
    
CREATE SEQUENCE Empleado_Seq
    START WITH 1 
    INCREMENT BY 1;

CREATE SEQUENCE Usuario_Seq
    START WITH 1 
    INCREMENT BY 1;

--***************************   Procedimientos CRUD para Sucursal   ***************************--

--Create Sucursal 
CREATE OR REPLACE PROCEDURE createSucursal_Proc (pTelefono NUMBER, pDireccion VARCHAR2)
    IS
    BEGIN
    INSERT INTO Sucursal values (Sucursal_Seq.nextVAL, pTelefono, pDireccion);
    --COMMIT;
END;
/


-- Read Sucursal 
-- CREATE OR REPLACE PROCEDURE readSucursal_Proc (pId_Sucursal NUMBER)
--     IS
--     DECLARE
--         response VARCHAR2(20) := '';
--     BEGIN 
--     SELECT * INTO response  FROM Sucursal WHERE id_Sucursal = pId_Sucursal;
-- END;
-- /

--UPDATE Sucursal 
CREATE OR REPLACE PROCEDURE updateSucursal_Proc(pID_Sucursal NUMBER, pTelefono VARCHAR2, pDireccion VARCHAR2 )
    IS
    BEGIN
    UPDATE Sucursal SET 
        telefono_Sucursal = pTelefono,
        direccion_Sucursal = pDireccion
    WHERE id_Sucursal = pID_Sucursal;
    --COMMIT;
END;
/
    
--DELETE Sucursal
CREATE OR REPLACE PROCEDURE deleteSucursal_Proc (pId_Sucursal NUMBER)
    IS
    BEGIN 
    DELETE FROM Sucursal WHERE id_Sucursal = pId_Sucursal;
    --COMMIT;
END;
/
    

--***************************   Procedimientos CRUD para Empleado   ***************************--

--CREATE Empleado
CREATE OR REPLACE PROCEDURE createEmpleado_Proc(pFK_Sucursal NUMBER, pPuesto VARCHAR2, pNombre VARCHAr2, pApellido1 VARCHAR2, pApellido2 VARCHAR2, pTelefono VARCHAR2)
    IS
    BEGIN
    INSERT INTO Empleado VALUES (Empleado_Seq.nextval, pFK_Sucursal,pPuesto, pNombre, pApellido1, pApellido2, pTelefono);
    --COMMIT
END;
/

--READ Empleado
-- CREATE OR REPLACE PROCEDURE readEmpleado_Proc(pID_Empleado NUMBER)
--     IS
--         response VARCHAR(20) := '';
--     BEGIN
--         SELECT * INTO response FROM Empleado WHERE id_Empleado = pID_Empleado;
--     END;

--UPDATE Empleado
CREATE OR REPLACE PROCEDURE updateEmpleado_Proc(pID_Empleado NUMBER, pFK_Sucursal NUMBER,pNombre VARCHAr2, pApellido1 VARCHAR2, pApellido2 VARCHAR2, pTelefono VARCHAR2, pPuesto VARCHAR2)
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
END;
/

--DELETE Empleado
CREATE OR REPLACE PROCEDURE deleteEmpleado_Proc(pID_Empleado NUMBER)
    IS
    BEGIN
    DELETE FROM Empleado WHERE id_Empleado = pID_Empleado;
    --COMMIT;
END;
/

--***************************   Procedimientos CRUD para Usuario   ***************************--

--Create Usuario
CREATE OR REPLACE PROCEDURE createUsuario_Proc(pFK_Empleado NUMBER, pUsuario VARCHAR2, pContrasenia VARCHAR2 )
    IS
    BEGIN
    INSERT INTO Usuario VALUES (Usuario_Seq.nextval, pFK_Empleado, pUsuario, pContrasenia); 
    --COMMIT;
END;
/

--READ Usuario
-- CREATE OR REPLACE PROCEDURE readUsuarioID_Proc( pId_Usuario NUMBER )
--     IS
--     BEGIN
--     SELECT * FROM Usuario WHERE id_Usuario = pId_Usuario;
--     END;

--UPDATE Sucursal
CREATE OR REPLACE PROCEDURE updateUsuario_Proc( pId_Usuario NUMBER, pUsuario VARCHAr2, pContrasenia VARCHAR2 )
    IS
    BEGIN
    UPDATE Usuario SET
        usuario_Usuario = pUsuario,
        contrasenia_Usuario = pContrasenia
    WHERE id_Usuario = pId_Usuario;
    --COMMIT;
END;
/

--Delete Usuario 
CREATE OR REPLACE PROCEDURE deleteUsuario_Proc(pID_Usuario NUMBER)
IS
BEGIN 
DELETE FROM USUARIO WHERE id_Usuario = pId_Usuario;
--COMMIT;
END;
/

/*    
EXEC createsucursal_proc('88888888', 'San Jose');
EXEC createsucursal_proc('88888888', 'ALajuela');

EXEC createempleado_proc(1,'CEO','Ana','Rojas','Rojas','98989898' );
EXEC createempleado_proc(1,'Pastelero','Andres','Rojas','Blanco','76767676' );
EXEC createempleado_proc(2,'Pastelero','Jose Luis','Zandovar','Xochimilco','12122112' );


EXEC createusuario_proc(1, 'usr1', 'pass1');
EXEC createusuario_proc(2, 'usr2', 'pass2');
EXEC createusuario_proc(3, 'usr3', 'pass3');


SELECT * FROM USUARIO;

SELECT * FROM EMPLEADO;

Select * from sucursal;



--Para conocer las tablas del usuario

SELECT table_name FROM user_tables ORDER BY table_name;
*/


-- --Procedimiento para Obetner las tablas del usuario de la base de datos 

-- create or replace PROCEDURE obtenerTablasDBUser
-- IS
--   c1 SYS_REFCURSOR;  
-- BEGIN

--   OPEN c1 FOR 
  
--   SELECT table_name FROM user_tables;
--   DBMS_SQL.RETURN_RESULT(c1);

-- END obtenerTablasDBUser;
-- /

create or replace FUNCTION  valida_login(pUsuario VARCHAR2, pContrasenia VARCHAR2)
    RETURN NUMBER IS
        v_contrasenia VARCHAR2(10);
        v_id NUMBER;
    BEGIN 
        SELECT CONTRASENIA_USUARIO INTO v_contrasenia
        FROM USUARIO WHERE usuario.usuario_usuario = pUsuario;
        
        

        IF v_contrasenia = pContrasenia THEN 
            SELECT ID_EMPLEADOFK into v_id
            FROM usuario where usuario.usuario_usuario = pUsuario;
            return v_id;
        ELSE
            RETURN 0;
        END IF;
        
        EXCEPTION WHEN OTHERS THEN 
        RETURN 0;
    END;
    /
    
    --Creacion de Tablas 

DROP TABLE Ingrediente;
DROP TABLE Producto;
DROP TABLE Proveedor;
DROP TABLE Precios;
DROP TABLE ListaIngredientes;
DROP TABLE Inventarios;

CREATE TABLE Ingrediente (
    Id_Ingrediente NUMBER,
    Nombre_Ingrediente VARCHAR2(50) NOT NULL,
    Medida_Ingrediente VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_Ingrediente PRIMARY KEY (Id_Ingrediente)
);

CREATE TABLE Producto (
    Id_Producto NUMBER,
    Nombre_Producto VARCHAR2(50) NOT NULL,
    Descripcion_Producto VARCHAR2(50) NOT NULL,
    Receta_Producto VARCHAR2(500) NOT NULL,
    CONSTRAINT pk_Producto PRIMARY KEY (Id_Producto )
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

--Creacion de Secuencias 
DROP SEQUENCE Ingrediente_SEQ;
DROP SEQUENCE Producto_SEQ;
DROP SEQUENCE Proveedor_SEQ;
DROP SEQUENCE Precios_SEQ;
DROP SEQUENCE ListaIngredientes_SEQ;
DROP SEQUENCE Inventarios_SEQ;

CREATE SEQUENCE Ingrediente_Seq
    START WITH 1 
    INCREMENT BY 1;
    
CREATE SEQUENCE Producto_Seq
    START WITH 1 
    INCREMENT BY 1;

CREATE SEQUENCE Proveedor_Seq
    START WITH 1 
    INCREMENT BY 1;

--***************************   Procedimientos CRUD para Ingrediente   ***************************--

--Create Ingrediente 
CREATE OR REPLACE PROCEDURE createIngrediente_Proc (pNombre_Ingrediente VARCHAR2, pMedida_Ingrediente  VARCHAR2)
    IS
    BEGIN
    INSERT INTO Ingrediente values (Ingrediente_Seq.nextVAL, pNombre_Ingrediente, pMedida_Ingrediente);
    --COMMIT;
END;
/

--UPDATE Ingrediente 
CREATE OR REPLACE PROCEDURE updatepNombre_Ingrediente_Proc(pID_pNombre_Ingrediente NUMBER, pNombre VARCHAR2, pMedida VARCHAR2)
    IS
    BEGIN
    UPDATE Ingrediente SET 
            Nombre_Ingrediente  = pNombre,
            Medida_Ingrediente = pMedida
    WHERE Id_Ingrediente = pID_pNombre_Ingrediente;
    --COMMIT;
END;
/
    
--DELETE Ingrediente
CREATE OR REPLACE PROCEDURE deleteIngrediente_Proc (pID_pNombre_Ingrediente NUMBER)
    IS
    BEGIN 
    DELETE FROM Ingrediente WHERE Id_Ingrediente = pID_pNombre_Ingrediente;
    --COMMIT;
END;
/
    

--***************************   Procedimientos CRUD para Producto    ***************************--

--CREATE Producto 
CREATE OR REPLACE PROCEDURE createProducto_Proc(pNombre_Producto VARCHAR2, pDescripcion_Producto VARCHAr2, pReceta_Producto VARCHAR2)
    IS
    BEGIN
    INSERT INTO Producto VALUES (Producto_Seq.nextval, pNombre_Producto, pDescripcion_Producto, pReceta_Producto);
    --COMMIT
END;
/

--UPDATE Producto 
CREATE OR REPLACE PROCEDURE updateProducto_Proc(pId_Producto NUMBER, pNombre_Producto VARCHAr2, pDescripcion_Producto VARCHAR2, pReceta_Producto VARCHAR2)
    IS
    BEGIN
    UPDATE Producto SET
        Nombre_Producto = pNombre_Producto,
        Descripcion_Producto = pDescripcion_Producto,
        Receta_Producto = pReceta_Producto
    WHERE Id_Producto = pId_Producto;
    --COMMIT;
END;
/

--DELETE Producto 
CREATE OR REPLACE PROCEDURE deleteProducto_Proc(pId_Producto NUMBER)
    IS
    BEGIN
    DELETE FROM Producto WHERE Id_Producto = pId_Producto;
    --COMMIT;
END;
/

--***************************   Procedimientos CRUD para Proveedor   ***************************--

--Create Proveedor
CREATE OR REPLACE PROCEDURE createProveedor_Proc(pNombre_Proveedor VARCHAR2, pDireccion_Proveedor VARCHAR2 )
    IS
    BEGIN
    INSERT INTO Proveedor VALUES (Proveedor_Seq.nextval, pNombre_Proveedor, pDireccion_Proveedor); 
    --COMMIT;
END;
/

--UPDATE Proveedor
CREATE OR REPLACE PROCEDURE updateProveedor_Proc(pId_Proveedor NUMBER, pNombre_Proveedor VARCHAr2, pDireccion_Proveedor VARCHAR2 )
    IS
    BEGIN
    UPDATE Proveedor SET
        Nombre_Proveedor = pNombre_Proveedor,
        Direccion_Proveedor = pDireccion_Proveedor
    WHERE Id_Proveedor = pId_Proveedor;
    --COMMIT;
END;
/

--Delete Proveedor 
CREATE OR REPLACE PROCEDURE deleteProveedor_Proc(pId_Proveedor NUMBER)
IS
BEGIN 
DELETE FROM Proveedor WHERE Id_Proveedor = pId_Proveedor;
--COMMIT;
END;
/

--***************************   Procedimientos CRUD para Precios   ***************************--

--Create Precios 
CREATE OR REPLACE PROCEDURE createPrecios_Proc (pId_IngredienteFK NUMBER, pId_ProveedorFK NUMBER, pPrecio_Precios NUMBER, pCantidad_Precios NUMBER)
    IS
    BEGIN
    INSERT INTO Precios values (pId_IngredienteFK, pId_ProveedorFK, pPrecio_Precios, pCantidad_Precios);
    --COMMIT;
END;
/

--UPDATE Precios 
CREATE OR REPLACE PROCEDURE updatepNombre_Precios_Proc(pId_IngredienteFK NUMBER, pId_ProveedorFK NUMBER, pPrecio_Precios NUMBER, pCantidad_Precios NUMBER)
    IS
    BEGIN
    UPDATE Precios SET 
            Precio_Precios  = pPrecio_Precios,
            Cantidad_Precios = pCantidad_Precios
    WHERE Id_IngredienteFK = pId_IngredienteFK and Id_ProveedorFK = pId_ProveedorFK;
    --COMMIT;
END;
/
    
--DELETE Precios
CREATE OR REPLACE PROCEDURE deletePrecios_Proc (pId_IngredienteFK NUMBER, pId_ProveedorFK NUMBER)
    IS
    BEGIN 
    DELETE FROM Precios WHERE Id_IngredienteFK = pId_IngredienteFK and Id_ProveedorFK = pId_ProveedorFK;
    --COMMIT;
END;
/   

--***************************   Procedimientos CRUD para ListaIngredientes     ***************************--

--CREATE ListaIngredientes  
CREATE OR REPLACE PROCEDURE createListaIngredientes_Proc(pId_ProductoFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_ListaIngredientes NUMBER)
    IS
    BEGIN
    INSERT INTO ListaIngredientes VALUES (pId_ProductoFK, pId_IngredienteFK , pCantidad_ListaIngredientes);
    --COMMIT
END;
/

--UPDATE ListaIngredientes  
CREATE OR REPLACE PROCEDURE updateListaIngredientes_Proc(pId_ProductoFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_ListaIngredientes NUMBER)
    IS
    BEGIN
    UPDATE ListaIngredientes  SET
        Cantidad_ListaIngredientes = pCantidad_ListaIngredientes
    WHERE Id_ProductoFK = pId_ProductoFK And Id_IngredienteFK = pId_IngredienteFK;
    --COMMIT;
END;
/

--DELETE ListaIngredientes  
CREATE OR REPLACE PROCEDURE deleteListaIngredientes_Proc(pId_ProductoFK NUMBER, pId_IngredienteFK NUMBER)
    IS
    BEGIN
    DELETE FROM ListaIngredientes WHERE Id_ProductoFK = pId_ProductoFK And Id_IngredienteFK = pId_IngredienteFK;
    --COMMIT;
END;
/

--***************************   Procedimientos CRUD para Cantidad_Inventarios    ***************************--

--Create Cantidad_Inventarios 
CREATE OR REPLACE PROCEDURE createInventarios_Proc(pid_SucursalFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_Inventarios NUMBER)
    IS
    BEGIN
    INSERT INTO Inventarios VALUES(pid_SucursalFK, pId_IngredienteFK, pCantidad_Inventarios); 
    --COMMIT;
END;
/

--UPDATE Cantidad_Inventarios 
CREATE OR REPLACE PROCEDURE updateInventariosProc(pid_SucursalFK NUMBER, pId_IngredienteFK NUMBER, pCantidad_Inventarios NUMBER)
    IS
    BEGIN
    UPDATE Inventarios  SET
        Cantidad_Inventarios = pCantidad_Inventarios
    WHERE id_SucursalFK  = pid_SucursalFK And Id_IngredienteFK = pId_IngredienteFK;
    --COMMIT;
END;
/

--Delete Usuario 
CREATE OR REPLACE PROCEDURE deleteInventarios_Proc(pid_SucursalFK NUMBER, pId_IngredienteFK NUMBER)
IS
BEGIN 
DELETE FROM Inventarios WHERE id_SucursalFK  = pid_SucursalFK And Id_IngredienteFK = pId_IngredienteFK;
--COMMIT;
END;
/
