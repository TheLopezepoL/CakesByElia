/*
    Instituto Tecnologico de Costa Rica 
    Escuela de Ingenieria en Computacion
    Curso: Bases de Datos 2 
    Profesor. Alberto Shum 

    Script de Inserts para poblar las Tablas de la Pasteleria 
        
    Grupo de Trabajo #6 
    
    Sebastían Lopez 
    José Murillo 
    Josue Chaves 

    II Semestre 2022 
*/

--Sucursal
EXEC cud_pasteleria_pkg.createsucursal_proc('88888888', 'San Jose');
EXEC cud_pasteleria_pkg.createsucursal_proc('77777777', 'ALajuela');

--Empleado
EXEC cud_pasteleria_pkg.createempleado_proc(1,'CEO','Ana','Rojas','Rojas','98989898' );
EXEC cud_pasteleria_pkg.createempleado_proc(1,'Pastelero','Andres','Rojas','Blanco','76767676' );
EXEC cud_pasteleria_pkg.createempleado_proc(2,'Pastelero','Jose Luis','Zandovar','Xochimilco','12122112' );

--Usuario
EXEC cud_pasteleria_pkg.createusuario_proc(1, 'usr1ana', 'pass1');
EXEC cud_pasteleria_pkg.createusuario_proc(2, 'usr2andres', 'pass2');
EXEC cud_pasteleria_pkg.createusuario_proc(3, 'usr3jose', 'pass3');


