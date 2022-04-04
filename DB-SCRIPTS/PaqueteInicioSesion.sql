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


--Procedimiento para hacer log in 
