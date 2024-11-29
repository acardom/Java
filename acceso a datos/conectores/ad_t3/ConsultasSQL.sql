--------------------------------
--1 CONSULTAS SOBRE UNA TABLA --
--------------------------------

-- 1. Devuelve un listado con el código de oficina y la ciudad donde hay oficinas.
SELECT codigo_oficina, ciudad
FROM oficina;

-- 2. Devuelve un listado con la ciudad y el teléfono de las oficinas de España.
SELECT ciudad, telefono
FROM oficina
WHERE pais = 'España';

-- 3. Devuelve un listado con el nombre, apellidos y email de los empleados cuyo jefe tiene un código de jefe igual a 7.
SELECT nombre, apellido1, email
FROM empleado
WHERE codigo_jefe = 7;

-- 4. Devuelve el nombre del puesto, nombre, apellidos y email del jefe de la empresa.
SELECT puesto, nombre, apellido1, email
FROM empleado
WHERE codigo_empleado = (SELECT MIN(codigo_empleado) FROM empleado);

-- 5. Devuelve un listado con el nombre, apellidos y puesto de aquellos empleados que no sean representantes de ventas.
SELECT nombre, apellido1, puesto
FROM empleado
WHERE puesto != 'Representante de ventas';

-- 6. Devuelve un listado con el nombre de todos los clientes españoles.
SELECT nombre
FROM cliente
WHERE pais = 'España';

-- 7. Devuelve un listado con los distintos estados por los que puede pasar un pedido.
SELECT DISTINCT estado
FROM pedido;

-- 8. Devuelve un listado con el código de cliente de aquellos clientes que realizaron algún pago en 2008.
-- Utilizando la función YEAR de MySQL.
SELECT DISTINCT codigo_cliente
FROM pago
WHERE YEAR(fecha_pago) = 2008;

-- Utilizando la función DATE_FORMAT de MySQL.
SELECT DISTINCT codigo_cliente
FROM pago
WHERE DATE_FORMAT(fecha_pago, '%Y') = '2008';

-- Sin utilizar ninguna función.
SELECT DISTINCT codigo_cliente
FROM pago
WHERE fecha_pago BETWEEN '2008-01-01' AND '2008-12-31';

-- 9. Devuelve un listado con el código de pedido, código de cliente, fecha esperada y fecha de entrega de los pedidos que no han sido entregados a tiempo.
SELECT codigo_pedido, codigo_cliente, fecha_esperada, fecha_entrega
FROM pedido
WHERE fecha_entrega > fecha_esperada;

-- 10. Devuelve un listado con el código de pedido, código de cliente, fecha esperada y fecha de entrega de los pedidos cuya fecha de entrega ha sido al menos dos días antes de la fecha esperada.
-- Utilizando la función ADDDATE de MySQL.
SELECT codigo_pedido, codigo_cliente, fecha_esperada, fecha_entrega
FROM pedido
WHERE fecha_entrega <= ADDDATE(fecha_esperada, INTERVAL -2 DAY);

-- Utilizando la función DATEDIFF de MySQL.
SELECT codigo_pedido, codigo_cliente, fecha_esperada, fecha_entrega
FROM pedido
WHERE DATEDIFF(fecha_esperada, fecha_entrega) >= 2;

-- 11. Devuelve un listado de todos los pedidos que fueron rechazados en 2009.
SELECT *
FROM pedido
WHERE estado = 'Rechazado' AND fecha_pedido BETWEEN '2009-01-01' AND '2009-12-31';

-- 12. Devuelve un listado de todos los pedidos que han sido entregados en el mes de enero de cualquier año.
SELECT *
FROM pedido
WHERE MONTH(fecha_entrega) = 1;

-- 13. Devuelve un listado con todos los pagos que se realizaron en el año 2008 mediante Paypal. Ordene el resultado de mayor a menor.
SELECT *
FROM pago
WHERE forma_pago = 'Paypal' AND YEAR(fecha_pago) = 2008
ORDER BY cantidad DESC;

-- 14. Devuelve un listado con todas las formas de pago que aparecen en la tabla pago (sin repetidos).
SELECT DISTINCT forma_pago
FROM pago;

-- 15. Devuelve un listado con todos los productos que pertenecen a la gama Ornamentales y que tienen más de 100 unidades en stock.
-- El listado deberá estar ordenado por su precio de venta, mostrando en primer lugar los de mayor precio.
SELECT *
FROM producto
WHERE gama = 'Ornamentales' AND stock > 100
ORDER BY precio_venta DESC;

-- 16. Devuelve un listado con todos los clientes que sean de la ciudad de Madrid y cuyo representante de ventas tenga el código de empleado 11 o 30.
SELECT *
FROM cliente
WHERE ciudad = 'Madrid' AND codigo_empleado_representante IN (11, 30);






--------------------------------------------------
-- 2 Consultas multitabla (Composición interna) --
--------------------------------------------------

-- 1. Devuelve el nombre de cada cliente y el nombre y apellido de su representante de ventas.
SELECT cliente.nombre_cliente AS nombre_cliente, empleado.nombre AS nombre_representante, empleado.apellido1 AS apellido_representante
FROM cliente
INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado;

-- 2. Muestra el nombre de los clientes que hayan realizado pagos junto con el nombre de sus representantes de ventas.
SELECT cliente.nombre_cliente AS nombre_cliente, empleado.nombre AS nombre_representante
FROM cliente
INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
INNER JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente;

-- 3. Muestra el nombre de los clientes que no hayan realizado pagos junto con el nombre de sus representantes de ventas.
SELECT cliente.nombre_cliente AS nombre_cliente, empleado.nombre AS nombre_representante
FROM cliente
INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
WHERE pago.codigo_cliente IS NULL;

-- 4. Devuelve el nombre de los clientes que han hecho pagos y el nombre de sus representantes junto con la ciudad de la oficina a la que pertenece el representante.
SELECT cliente.nombre_cliente AS nombre_cliente, empleado.nombre AS nombre_representante, empleado.apellido1 AS apellido_representante, oficina.ciudad
FROM cliente
INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
INNER JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina;

-- 5. Devuelve el nombre de los clientes que no hayan hecho pagos y el nombre de sus representantes junto con la ciudad de la oficina a la que pertenece el representante.
SELECT cliente.nombre_cliente AS nombre_cliente, empleado.nombre AS nombre_representante, empleado.apellido1 AS apellido_representante, oficina.ciudad
FROM cliente
INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
WHERE pago.codigo_cliente IS NULL;

-- 6. Lista la dirección de las oficinas que tengan clientes en Fuenlabrada.
SELECT DISTINCT oficina.linea_direccion1, oficina.linea_direccion2
FROM oficina
INNER JOIN cliente ON oficina.codigo_oficina = cliente.codigo_oficina
WHERE cliente.ciudad = 'Fuenlabrada';

-- 7. Devuelve el nombre de los clientes y el nombre de sus representantes junto con la ciudad de la oficina a la que pertenece el representante.
SELECT cliente.nombre_cliente AS nombre_cliente, empleado.nombre AS nombre_representante, empleado.apellido1 AS apellido_representante, oficina.ciudad
FROM cliente
INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina;

-- 8. Devuelve un listado con el nombre de los empleados junto con el nombre de sus jefes.
SELECT e.nombre AS nombre_empleado, e.apellido1 AS apellido_empleado, jefe.nombre AS nombre_jefe, jefe.apellido1 AS apellido_jefe
FROM empleado e
INNER JOIN empleado jefe ON e.codigo_jefe = jefe.codigo_empleado;

-- 9. Devuelve el nombre de los clientes a los que no se les ha entregado a tiempo un pedido.
SELECT cliente.nombre_cliente
FROM cliente
INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
WHERE pedido.fecha_entrega > pedido.fecha_esperada;

-- 10. Devuelve un listado de las diferentes gamas de producto que ha comprado cada cliente.
SELECT cliente.nombre_cliente AS nombre_cliente, GROUP_CONCAT(DISTINCT producto.gama) AS gamas_compradas
FROM cliente
INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
INNER JOIN detalle_pedido ON pedido.codigo_pedido = detalle_pedido.codigo_pedido
INNER JOIN producto ON detalle_pedido.codigo_producto = producto.codigo_producto
GROUP BY cliente.codigo_cliente;






--------------------------------------------------
-- 3 Consultas multitabla (Composición externa) --
--------------------------------------------------

-- 1. Devuelve un listado que muestre solamente los clientes que no han realizado ningún pago.
SELECT cliente.nombre_cliente
FROM cliente
LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
WHERE pago.codigo_cliente IS NULL;

-- 2. Devuelve un listado que muestre solamente los clientes que no han realizado ningún pedido.
SELECT cliente.nombre_cliente
FROM cliente
LEFT JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
WHERE pedido.codigo_pedido IS NULL;

-- 3. Devuelve un listado que muestre los clientes que no han realizado ningún pago y los que no han realizado ningún pedido.
SELECT cliente.nombre_cliente
FROM cliente
LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
LEFT JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
WHERE pago.codigo_cliente IS NULL AND pedido.codigo_pedido IS NULL;

-- 4. Devuelve un listado que muestre solamente los empleados que no tienen una oficina asociada.
SELECT empleado.nombre, empleado.apellido1
FROM empleado
LEFT JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
WHERE oficina.codigo_oficina IS NULL;

-- 5. Devuelve un listado que muestre solamente los empleados que no tienen un cliente asociado.
SELECT empleado.nombre, empleado.apellido1
FROM empleado
LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
WHERE cliente.codigo_cliente IS NULL;

-- 6. Devuelve un listado que muestre solamente los empleados que no tienen un cliente asociado junto con los datos de la oficina donde trabajan.
SELECT empleado.nombre, empleado.apellido1, oficina.ciudad
FROM empleado
LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
LEFT JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
WHERE cliente.codigo_cliente IS NULL;

-- 7. Devuelve un listado que muestre los empleados que no tienen una oficina asociada y los que no tienen un cliente asociado.
SELECT empleado.nombre, empleado.apellido1
FROM empleado
LEFT JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
WHERE oficina.codigo_oficina IS NULL AND cliente.codigo_cliente IS NULL;

-- 8. Devuelve un listado de los productos que nunca han aparecido en un pedido.
SELECT producto.nombre
FROM producto
LEFT JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
WHERE detalle_pedido.codigo_producto IS NULL;

-- 9. Devuelve un listado de los productos que nunca han aparecido en un pedido. El resultado debe mostrar el nombre, la descripción y la imagen del producto.
SELECT producto.nombre, producto.descripcion, producto.imagen
FROM producto
LEFT JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
WHERE detalle_pedido.codigo_producto IS NULL;

-- 10. Devuelve las oficinas donde no trabajan ninguno de los empleados que hayan sido los representantes de ventas de algún cliente que haya realizado la compra de algún producto de la gama Frutales.
SELECT oficina.linea_direccion1, oficina.linea_direccion2
FROM oficina
LEFT JOIN empleado ON oficina.codigo_oficina = empleado.codigo_oficina
LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
LEFT JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
LEFT JOIN detalle_pedido ON pedido.codigo_pedido = detalle_pedido.codigo_pedido
LEFT JOIN producto ON detalle_pedido.codigo_producto = producto.codigo_producto
WHERE producto.gama = 'Frutales' AND empleado.codigo_empleado IS NULL;

-- 11. Devuelve un listado con los clientes que han realizado algún pedido pero no han realizado ningún pago.
SELECT cliente.nombre_cliente
FROM cliente
LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
WHERE pago.codigo_cliente IS NULL;

-- 12. Devuelve un listado con los datos de los empleados que no tienen clientes asociados y el nombre de su jefe asociado.
SELECT empleado.nombre, empleado.apellido1, jefe.nombre AS nombre_jefe, jefe.apellido1 AS apellido_jefe
FROM empleado
LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
LEFT JOIN empleado jefe ON empleado.codigo_jefe = jefe.codigo_empleado
WHERE cliente.codigo_cliente IS NULL;






-------------------------
-- 4 Consultas resumen --
-------------------------

-- 1. ¿Cuántos empleados hay en la compañía?
SELECT COUNT(*) AS total_empleados
FROM empleado;

-- 2. ¿Cuántos clientes tiene cada país?
SELECT pais, COUNT(*) AS total_clientes
FROM cliente
GROUP BY pais;

-- 3. ¿Cuál fue el pago medio en 2009?
SELECT AVG(total) AS pago_medio
FROM pago
WHERE YEAR(fecha_pago) = 2009;

-- 4. ¿Cuántos pedidos hay en cada estado? Ordena el resultado de forma descendente por el número de pedidos.
SELECT estado, COUNT(*) AS total_pedidos
FROM pedido
GROUP BY estado
ORDER BY total_pedidos DESC;

-- 5. Calcula el precio de venta del producto más caro y más barato en una misma consulta.
SELECT MAX(precio_venta) AS precio_maximo, MIN(precio_venta) AS precio_minimo
FROM producto;

-- 6. Calcula el número de clientes que tiene la empresa.
SELECT COUNT(*) AS total_clientes
FROM cliente;

-- 7. ¿Cuántos clientes tiene la ciudad de Madrid?
SELECT COUNT(*) AS total_clientes_madrid
FROM cliente
WHERE ciudad = 'Madrid';

-- 8. ¿Calcula cuántos clientes tiene cada una de las ciudades que empiezan por M?
SELECT ciudad, COUNT(*) AS total_clientes
FROM cliente
WHERE ciudad LIKE 'M%'
GROUP BY ciudad;

-- 9. Devuelve el nombre de los representantes de ventas y el número de clientes al que atiende cada uno.
SELECT empleado.nombre, empleado.apellido1, COUNT(cliente.codigo_cliente) AS total_clientes
FROM empleado
LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
GROUP BY empleado.codigo_empleado;

-- 10. Calcula el número de clientes que no tiene asignado representante de ventas.
SELECT COUNT(*) AS total_clientes_sin_representante
FROM cliente
WHERE codigo_empleado_rep_ventas IS NULL;

-- 11. Calcula la fecha del primer y último pago realizado por cada uno de los clientes. El listado deberá mostrar el nombre y los apellidos de cada cliente.
SELECT cliente.nombre_cliente, cliente.apellido_contacto,
       MIN(pago.fecha_pago) AS primer_pago,
       MAX(pago.fecha_pago) AS ultimo_pago
FROM cliente
INNER JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
GROUP BY cliente.codigo_cliente;

-- 12. Calcula el número de productos diferentes que hay en cada uno de los pedidos.
SELECT codigo_pedido, COUNT(DISTINCT codigo_producto) AS total_productos
FROM detalle_pedido
GROUP BY codigo_pedido;

-- 13. Calcula la suma de la cantidad total de todos los productos que aparecen en cada uno de los pedidos.
SELECT codigo_pedido, SUM(cantidad) AS total_cantidad
FROM detalle_pedido
GROUP BY codigo_pedido;

-- 14. Devuelve un listado de los 20 productos más vendidos y el número total de unidades que se han vendido de cada uno. El listado deberá estar ordenado por el número total de unidades vendidas.
SELECT producto.nombre, SUM(detalle_pedido.cantidad) AS total_unidades
FROM producto
INNER JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
GROUP BY producto.codigo_producto
ORDER BY total_unidades DESC
LIMIT 20;

-- 15. La facturación que ha tenido la empresa en toda la historia, indicando la base imponible, el IVA y el total facturado. La base imponible se calcula sumando el coste del producto por el número de unidades vendidas de la tabla detalle_pedido. El IVA es el 21 % de la base imponible, y el total la suma de los dos campos anteriores.
SELECT SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) AS base_imponible,
       SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 0.21) AS iva,
       SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 1.21) AS total_facturado
FROM detalle_pedido;

-- 16. La misma información que en la pregunta anterior, pero agrupada por código de producto.
SELECT detalle_pedido.codigo_producto,
       SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) AS base_imponible,
       SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 0.21) AS iva,
       SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 1.21) AS total_facturado
FROM detalle_pedido
GROUP BY detalle_pedido.codigo_producto;

-- 17. La misma información que en la pregunta anterior, pero agrupada por código de producto filtrada por los códigos que empiecen por OR.
SELECT detalle_pedido.codigo_producto,
       SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) AS base_imponible,
       SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 0.21) AS iva,
       SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 1.21) AS total_facturado
FROM detalle_pedido
WHERE detalle_pedido.codigo_producto LIKE 'OR%'
GROUP BY detalle_pedido.codigo_producto;

-- 18. Lista las ventas totales de los productos que hayan facturado más de 3000 euros. Se mostrará el nombre, unidades vendidas, total facturado y total facturado con impuestos (21% IVA).
SELECT producto.nombre,
       SUM(detalle_pedido.cantidad) AS total_unidades_vendidas,
       SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) AS total_facturado,
       SUM((detalle_pedido.precio_unidad * detalle_pedido.cantidad) * 1.21) AS total_facturado_con_iva
FROM producto
INNER JOIN detalle_pedido ON producto.codigo_producto = detalle_pedido.codigo_producto
GROUP BY producto.codigo_producto
HAVING SUM(detalle_pedido.precio_unidad * detalle_pedido.cantidad) > 3000;






--------------------
-- 5 sUBCONSULTAS --
--------------------

-- 1. Devuelve el nombre del cliente con mayor límite de crédito.
SELECT nombre_cliente
FROM cliente
WHERE limite_credito = (SELECT MAX(limite_credito) FROM cliente);

-- 2. Devuelve el nombre del producto que tenga el precio de venta más caro.
SELECT nombre
FROM producto
WHERE precio_venta = (SELECT MAX(precio_venta) FROM producto);

-- 3. Devuelve el nombre del producto del que se han vendido más unidades.
-- Primero calculamos las unidades vendidas de cada producto.
SELECT producto.nombre
FROM producto
WHERE codigo_producto = (
    SELECT codigo_producto
    FROM detalle_pedido
    GROUP BY codigo_producto
    ORDER BY SUM(cantidad) DESC
    LIMIT 1
);

-- 4. Los clientes cuyo límite de crédito sea mayor que los pagos que haya realizado. (Sin utilizar INNER JOIN).
SELECT nombre_cliente
FROM cliente
WHERE limite_credito > (
    SELECT SUM(total)
    FROM pago
    WHERE pago.codigo_cliente = cliente.codigo_cliente
);

-- 5. Devuelve el producto que más unidades tiene en stock.
SELECT nombre
FROM producto
WHERE cantidad_en_stock = (SELECT MAX(cantidad_en_stock) FROM producto);

-- 6. Devuelve el producto que menos unidades tiene en stock.
SELECT nombre
FROM producto
WHERE cantidad_en_stock = (SELECT MIN(cantidad_en_stock) FROM producto);

-- 7. Devuelve el nombre, los apellidos y el email de los empleados que están a cargo de Alberto Soria.
SELECT nombre, apellido1, apellido2, email
FROM empleado
WHERE codigo_empleado IN (
    SELECT codigo_jefe
    FROM empleado
    WHERE nombre = 'Alberto' AND apellido1 = 'Soria'
);

-- 8. Devuelve el nombre del cliente con mayor límite de crédito.                                                                                                                                                                               
-- Subconsulta con ALL
SELECT nombre_cliente
FROM cliente
WHERE limite_credito = ALL (SELECT limite_credito FROM cliente);

-- 9. Devuelve el nombre del producto que tenga el precio de venta más caro.
-- Subconsulta con ANY
SELECT nombre
FROM producto
WHERE precio_venta = ANY (SELECT MAX(precio_venta) FROM producto);

-- 10. Devuelve el producto que menos unidades tiene en stock.
-- Subconsulta con ANY
SELECT nombre
FROM producto
WHERE cantidad_en_stock = ANY (SELECT MIN(cantidad_en_stock) FROM producto);

-- 11. Devuelve el nombre, apellido1 y cargo de los empleados que no representen a ningún cliente.
SELECT nombre, apellido1, puesto
FROM empleado
WHERE codigo_empleado NOT IN (SELECT codigo_empleado_rep_ventas FROM cliente);

-- 12. Devuelve un listado que muestre solamente los clientes que no han realizado ningún pago.
SELECT nombre_cliente
FROM cliente
WHERE codigo_cliente NOT IN (SELECT codigo_cliente FROM pago);

-- 13. Devuelve un listado que muestre solamente los clientes que sí han realizado ningún pago.
SELECT nombre_cliente
FROM cliente
WHERE codigo_cliente IN (SELECT codigo_cliente FROM pago);

-- 14. Devuelve un listado de los productos que nunca han aparecido en un pedido.
SELECT nombre
FROM producto
WHERE codigo_producto NOT IN (SELECT codigo_producto FROM detalle_pedido);

-- 15. Devuelve el nombre, apellidos, puesto y teléfono de la oficina de aquellos empleados que no sean representante de ventas de ningún cliente.
SELECT empleado.nombre, empleado.apellido1, empleado.apellido2, empleado.puesto, oficina.telefono
FROM empleado
LEFT JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
WHERE empleado.codigo_empleado NOT IN (SELECT codigo_empleado_rep_ventas FROM cliente);

-- 16. Devuelve las oficinas donde no trabajan ninguno de los empleados que hayan sido los representantes de ventas de algún cliente que haya realizado la compra de algún producto de la gama Frutales.
SELECT oficina.linea_direccion1, oficina.linea_direccion2
FROM oficina
WHERE codigo_oficina NOT IN (
    SELECT empleado.codigo_oficina
    FROM empleado
    INNER JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
    INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
    INNER JOIN detalle_pedido ON pedido.codigo_pedido = detalle_pedido.codigo_pedido
    INNER JOIN producto ON detalle_pedido.codigo_producto = producto.codigo_producto
    WHERE producto.gama = 'Frutales'
);

-- 17. Devuelve un listado con los clientes que han realizado algún pedido pero no han realizado ningún pago.
SELECT cliente.nombre_cliente
FROM cliente
INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
WHERE pago.codigo_cliente IS NULL;

-- 18. Devuelve un listado que muestre solamente los clientes que no han realizado ningún pago.
SELECT nombre_cliente
FROM cliente
WHERE NOT EXISTS (SELECT 1 FROM pago WHERE cliente.codigo_cliente = pago.codigo_cliente);

-- 19. Devuelve un listado que muestre solamente los clientes que sí han realizado ningún pago.
SELECT nombre_cliente
FROM cliente
WHERE EXISTS (SELECT 1 FROM pago WHERE cliente.codigo_cliente = pago.codigo_cliente);

-- 20. Devuelve un listado de los productos que nunca han aparecido en un pedido.
SELECT nombre
FROM producto
WHERE NOT EXISTS (SELECT 1 FROM detalle_pedido WHERE producto.codigo_producto = detalle_pedido.codigo_producto);

-- 21. Devuelve un listado de los productos que han aparecido en un pedido alguna vez.
SELECT nombre
FROM producto
WHERE EXISTS (SELECT 1 FROM detalle_pedido WHERE producto.codigo_producto = detalle_pedido.codigo_producto);






--------------------------
-- 6 Consultas variadas --
--------------------------

-- 1. Devuelve el listado de clientes indicando el nombre del cliente y cuántos pedidos ha realizado. 
-- Tenga en cuenta que pueden existir clientes que no han realizado ningún pedido.
SELECT cliente.nombre_cliente, COUNT(pedido.codigo_pedido) AS total_pedidos
FROM cliente
LEFT JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
GROUP BY cliente.codigo_cliente;

-- 2. Devuelve un listado con los nombres de los clientes y el total pagado por cada uno de ellos. 
-- Tenga en cuenta que pueden existir clientes que no han realizado ningún pago.
SELECT cliente.nombre_cliente, COALESCE(SUM(pago.total), 0) AS total_pagado
FROM cliente
LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
GROUP BY cliente.codigo_cliente;

-- 3. Devuelve el nombre de los clientes que hayan hecho pedidos en 2008 ordenados alfabéticamente de menor a mayor.
SELECT DISTINCT cliente.nombre_cliente
FROM cliente
INNER JOIN pedido ON cliente.codigo_cliente = pedido.codigo_cliente
WHERE YEAR(pedido.fecha_pedido) = 2008
ORDER BY cliente.nombre_cliente ASC;

-- 4. Devuelve el nombre del cliente, el nombre y primer apellido de su representante de ventas y el número de teléfono de la oficina del representante de ventas, 
-- de aquellos clientes que no hayan realizado ningún pago.
SELECT cliente.nombre_cliente, empleado.nombre AS nombre_representante, empleado.apellido1 AS apellido_representante, oficina.telefono
FROM cliente
INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
LEFT JOIN pago ON cliente.codigo_cliente = pago.codigo_cliente
WHERE pago.codigo_cliente IS NULL;

-- 5. Devuelve el listado de clientes donde aparezca el nombre del cliente, el nombre y primer apellido de su representante de ventas y la ciudad donde está su oficina.
SELECT cliente.nombre_cliente, empleado.nombre AS nombre_representante, empleado.apellido1 AS apellido_representante, oficina.ciudad
FROM cliente
INNER JOIN empleado ON cliente.codigo_empleado_rep_ventas = empleado.codigo_empleado
INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina;

-- 6. Devuelve el nombre, apellidos, puesto y teléfono de la oficina de aquellos empleados que no sean representante de ventas de ningún cliente.
SELECT empleado.nombre, empleado.apellido1, empleado.apellido2, empleado.puesto, oficina.telefono
FROM empleado
LEFT JOIN cliente ON empleado.codigo_empleado = cliente.codigo_empleado_rep_ventas
INNER JOIN oficina ON empleado.codigo_oficina = oficina.codigo_oficina
WHERE cliente.codigo_cliente IS NULL;

-- 7. Devuelve un listado indicando todas las ciudades donde hay oficinas y el número de empleados que tiene.
SELECT oficina.ciudad, COUNT(empleado.codigo_empleado) AS total_empleados
FROM oficina
LEFT JOIN empleado ON oficina.codigo_oficina = empleado.codigo_oficina
GROUP BY oficina.ciudad;
