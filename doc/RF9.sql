select *
from inmueble
where id not in(
select idinmueble
from reserva
where (RESERVA.FECHAINICIO<'01-May-2021' AND RESERVA.FECHAFIN>'01-May-2021') OR (RESERVA.FECHAINICIO<'01-Jun-2021' AND RESERVA.FECHAFIN>'01-Jun-2021') OR (RESERVA.FECHAINICIO BETWEEN '01-May-2021' AND '01-Jun-2021' AND RESERVA.FECHAFIN BETWEEN '01-May-2021' AND '01-Jun-2021')
group by reserva.idinmueble
) and disponible=1 and capacidad>=3;