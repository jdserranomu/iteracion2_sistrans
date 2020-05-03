select * 
from reserva 
where fechainicio<=? 
order by idinmueble, fechainicio;

select * 
from inmueble  
where inmueble.id not in (select idinmueble from reserva);