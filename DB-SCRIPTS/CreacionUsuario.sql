/* 
 Primero ingresar desde consola:
    $ sqlplus sys/oracle@//localhost:1521/grp06db as sysdba
*/
    
--ya dentro ejecutar el siguiente comando para crear el usuario:
-- USER SQL
CREATE USER "C##userproyecto1" IDENTIFIED BY "oracle1";  


--Luego desde el SQL Developer ingresar igual como sysdba 
--Ir a la barra de la izquierda, expandir la base de datos de la sesión de Sysdba, 
--expandir la seccion de hasta abajo que dice Other Users, 
--Buscar el usuario que acabamos de crear: 

-- 1. Click derecho -> Edit User
-- 2. Ir a la pestaña de System Privileges, 
-- 3. Dar click en Grant All, 
-- 4. Manualmente quitar los privilegios de: 
--                      SYSBACKUP
--                      SYSDBA
--                      SYSDG
--                      SYSKM
--                      SYSOPER
--                      SYSRAC
-- 5. DAR CLICK EN APPLY 


/*
    Ingresar nuevamente con el nuevo usuario creado, 
    y ejecutar los scripts de las tablas y los troggers y los SP que fuimos creando. 
*/ 

