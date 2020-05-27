select usuario.id
from usuario
where usuario.id in (

select usuario.id
from usuario
inner join reserva on reserva.idusuario=usuario.id
where reserva.valortotal>=19500

) or usuario.id in (
select usuario.id
from usuario
inner join reserva on reserva.idusuario=usuario.id
inner join inmueble on reserva.idinmueble=inmueble.id
inner join habitacionhotel on habitacionhotel.id=inmueble.id
where inmueble.tipo='Habitacion Hotel' and habitacionhotel.tipo='Suite'
) or usuario.id in (
SELECT id
FROM 
(SELECT ID, COUNT(*) AS C
FROM
(SELECT USUARIO.ID, TRUNC(RESERVA.FECHAINICIO,'MONTH')
FROM USUARIO
INNER JOIN RESERVA ON USUARIO.ID = RESERVA.IDUSUARIO
INNER JOIN INMUEBLE ON RESERVA.IDINMUEBLE = INMUEBLE.ID
GROUP BY USUARIO.ID, TRUNC(RESERVA.FECHAINICIO,'MONTH'))
GROUP BY ID)
) 
group by usuario.id;