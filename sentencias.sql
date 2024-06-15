-- A. Nombre de la oficina de la cual es gerente una persona, de la cual conocemos el nombre (no se posee el nombre completo)
SELECT nombre 
FROM OFICINA
WHERE gerente LIKE '%[nombre_parcial]%';

-- B. Nombre de todas las oficinas que posee la empresa. Incluir en la consulta el nombre de la ciudad y del departamento en el cual se encuentran.
SELECT O.nombre AS oficina, C.nombre AS ciudad, D.nombre AS departamento
FROM OFICINA O
JOIN CIUDADES C ON O.cod_ciudad = C.codigo
JOIN DEPARTAMENTO D ON C.cod_departamento = D.codigo;

-- C. Listado (nombre) de las oficinas en las cuales se encuentra un producto en particular, del cual se conoce parcialmente el NOMBRE
SELECT O.nombre AS oficina
FROM OFICINA O
JOIN INVENTARIO I ON O.codigo = I.cod_oficina
JOIN PRODUCTO P ON I.cod_producto = P.codigo
WHERE P.descripcion LIKE '%[nombre_parcial]%';

-- D. Nombre de la oficina que posee la mayor cantidad de existencia en toda la empresa (sumar todas las existencias de la oficina).
SELECT O.nombre AS oficina
FROM OFICINA O
JOIN INVENTARIO I ON O.codigo = I.cod_oficina
GROUP BY O.nombre
ORDER BY SUM(I.existencia) DESC
LIMIT 1;

-- E. Listado de las oficinas que poseen una existencia menor a "###". Incluir en el listado oficina, ciudad, producto y existencia
SELECT O.nombre AS oficina, C.nombre AS ciudad, P.descripcion AS producto, I.existencia
FROM OFICINA O
JOIN CIUDADES C ON O.cod_ciudad = C.codigo
JOIN INVENTARIO I ON O.codigo = I.cod_oficina
JOIN PRODUCTO P ON I.cod_producto = P.codigo
WHERE I.existencia < [###];

-- F. Sentencia que actualice la existencia de todos los productos del inventario que pertenezcan a una oficina de una ciudad en particular (se conoce el código de la ciudad).
UPDATE INVENTARIO I
SET existencia = [nuevo_valor]
WHERE I.cod_oficina IN (
    SELECT O.codigo
    FROM OFICINA O
    WHERE O.cod_ciudad = [codigo_ciudad]
);
