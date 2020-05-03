select * 
from usuario 
where id in
( select idusuario  
from  
(select idinmueble, idusuario, count(*) as cnt, sum(fechafin-fechainicio) as suma
from reserva
where estado<>2 and idinmueble = ? 
group by idinmueble, idusuario) 
where cnt >= 3 or suma>=15);