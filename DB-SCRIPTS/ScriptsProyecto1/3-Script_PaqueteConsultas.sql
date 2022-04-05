/*
    Instituto Tecnologico de Costa Rica 
    Escuela de Ingenieria en Computacion
    Curso: Bases de Datos 2 
    Profesor. Alberto Shum 

    Script del Paquete de Consultas a la Base de Datos
        
    Grupo de Trabajo #6 

    
    Sebastían Lopez 
    José Murillo 
    Josue Chaves 

    II Semestre 2022 
*/


CREATE OR REPLACE PACKAGE CONSULTAS_PASTELERIA_PKG AS

    --Valida el LogIn del Usuario, si todo esta correcto, devuelve el id del empleado
    FUNCTION  valida_login(pUsuario VARCHAR2, pContrasenia VARCHAR2) RETURN NUMBER;
    --Imprime las Tablas a las cuales tiene acceso el Usuario de la Base de datos, creado anteriormente
    PROCEDURE obtenerTablasDBUser;


END CONSULTAS_PASTELERIA_PKG;
/


CREATE OR REPLACE PACKAGE BODY CONSULTAS_PASTELERIA_PKG AS

    FUNCTION  valida_login(pUsuario VARCHAR2, pContrasenia VARCHAR2)
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
    END valida_login;


    PROCEDURE obtenerTablasDBUser 
        IS 
        c1 SYS_REFCURSOR;  
        BEGIN
        OPEN c1 FOR 
        
        SELECT table_name FROM user_tables;

        DBMS_SQL.RETURN_RESULT(c1);
    END obtenerTablasDBUser;


END CONSULTAS_PASTELERIA_PKG;
/






