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

--Ingrediente 
EXEC cud_pasteleria_pkg.createIngrediente_Proc('harina', 'kilogramo');
EXEC cud_pasteleria_pkg.createIngrediente_Proc('azucar', 'kilogramo');
EXEC cud_pasteleria_pkg.createIngrediente_Proc('huevos', 'unidad');
EXEC cud_pasteleria_pkg.createIngrediente_Proc('chocolate', 'KILOGRAMO');
EXEC cud_pasteleria_pkg.createIngrediente_Proc('vainilla', 'gramo');
EXEC cud_pasteleria_pkg.createIngrediente_Proc('leche', 'litro');

--Producto
EXEC cud_pasteleria_pkg.createProducto_Proc('PASTEL CHOCOLATE', 'Delicioso pastel de chocolate','haria+huevos+leche+chocolate');
EXEC cud_pasteleria_pkg.createProducto_Proc('PASTEL Vainilla', 'Delicioso pastel de Vainilla','haria+huevos+leche+vainilla');
EXEC cud_pasteleria_pkg.createProducto_Proc('Queque Seco', 'Delicioso Queque seco','haria+huevos+leche');

--Proveedor
EXEC cud_pasteleria_pkg.createProveedor_Proc('Dos Pinos', 'Llegando al aeropuerto');
EXEC cud_pasteleria_pkg.createProveedor_Proc('Palí', 'San Ramón');
EXEC cud_pasteleria_pkg.createProveedor_Proc('Pricesmart', 'Cartago');

--Precios
--La leche la conseguimos en la dos pinos a 600 colones el litro x
EXEC cud_pasteleria_pkg.createPrecios_Proc( 6, 1, 600, 1 );  
--La harina la conseguimos en pali a 1000 colones el kilo
EXEC cud_pasteleria_pkg.createPrecios_Proc( 1, 2, 1000, 1 ); 
--El azucar lo conseguimos en pali a 1000 colones el kilo
EXEC cud_pasteleria_pkg.createPrecios_Proc( 2, 2, 1000, 1 ); 
--Los huevo los conseguimos en pali a 200 colones la unidad
EXEC cud_pasteleria_pkg.createPrecios_Proc( 3, 2, 200, 1 );  
--chocolate lo conseguimos en pricesmart a 3000 colones 1 kilo 
EXEC cud_pasteleria_pkg.createPrecios_Proc( 4, 3, 3000, 1 ); 
--Vainilla la conseguimos en pricesmart a 4000 colones 1 kilo
EXEC cud_pasteleria_pkg.createPrecios_Proc( 5, 3, 4000, 1 ); 

--Lista Ingredientes 
--PARA EL QUEQUE DE CHOCOLATE ES 1 KILO DE HARINA, 2 LITROS DE LECHE, 1 KILO DE CHOCOLATE, 1 KILO DE AZUCAR, 12 HUEVOS 
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(1 , 1 , 1 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(1 , 6 , 3 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(1 , 4 , 1 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(1 , 2 , 1 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(1 , 3 , 12 );

--PARA EL QUEQUE DE VAINILLA ES 2 KILO DE HARINA, 3 LITROS DE LECHE, 1 KILO DE VAINILLA, 1 KILO DE AZUCAR, 13 HUEVOS 
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(2 , 1 , 2 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(2 , 6 , 3 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(2 , 5 , 1 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(2 , 2 , 1 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(2 , 3 , 13 );

--PARA EL QUEQUE SECO ES 1 KILO DE HARINA, 1 LITROS DE LECHE, 1 KILO DE AZUCAR, 2 HUEVOS 
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(3 , 1 , 1 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(3 , 6 , 1 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(3 , 2 , 1 );
EXEC cud_pasteleria_pkg.createListaIngredientes_Proc(3 , 3 , 2);


--INVENTARIOS 
--1-SAN JOSE 2-ALAJUELA
--SUCURSAL INGREDIENTE CANTIDAD 
--HARINA
--AZUCAR
--HUEVOS
--CHOCOLATE
--VAINILLA
--LECHE
EXEC cud_pasteleria_pkg.createInventarios_Proc(1, 1, 500);
EXEC cud_pasteleria_pkg.createInventarios_Proc(1, 2, 100);
EXEC cud_pasteleria_pkg.createInventarios_Proc(1, 3, 1000);
EXEC cud_pasteleria_pkg.createInventarios_Proc(1, 4, 80); 
EXEC cud_pasteleria_pkg.createInventarios_Proc(1, 5, 85); 
EXEC cud_pasteleria_pkg.createInventarios_Proc(1, 6, 100);

EXEC cud_pasteleria_pkg.createInventarios_Proc(2, 1, 501); 
EXEC cud_pasteleria_pkg.createInventarios_Proc(2, 2, 101); 
EXEC cud_pasteleria_pkg.createInventarios_Proc(2, 3, 1001);
EXEC cud_pasteleria_pkg.createInventarios_Proc(2, 4, 81);
EXEC cud_pasteleria_pkg.createInventarios_Proc(2, 5, 86);
EXEC cud_pasteleria_pkg.createInventarios_Proc(2, 6, 101);


--Cliente
EXEC cud_pasteleria_pkg.agrCliente('Alejandra', '11111111');
EXEC cud_pasteleria_pkg.agrCliente('Johnny', '22222222');
EXEC cud_pasteleria_pkg.agrCliente('Angela', '33333333');

--Pedido 
--PRODUCTO - SUCURSAL - CLIENTE - FECHA PEDIDO - FECHA ENTREGA - DIRECCION - FECHA
EXEC cud_pasteleria_pkg.agrPedido(1, 1, 1, TO_DATE('2022-04-21', 'yyyy-mm-dd'), TO_DATE('2022-04-27', 'yyyy-mm-dd'), 'San Jose', TO_DATE('2022-04-21', 'yyyy-mm-dd') );
EXEC cud_pasteleria_pkg.agrPedido(2, 1, 2, TO_DATE('2022-04-22', 'yyyy-mm-dd'), TO_DATE('2022-04-28', 'yyyy-mm-dd'), 'San Jose', TO_DATE('2022-04-21', 'yyyy-mm-dd') );
EXEC cud_pasteleria_pkg.agrPedido(3, 2, 3, TO_DATE('2022-04-23', 'yyyy-mm-dd'), TO_DATE('2022-04-29', 'yyyy-mm-dd'), 'Alajuela',TO_DATE('2022-04-21', 'yyyy-mm-dd') );
 
