select usuario.tipo as tipo, count(reserva.id) as numeroReservas, sum(reserva.fechafin - reserva.fechainicio) as nochesContratado, sum(valortotal) as valorPagado 
from usuario 
left outer join reserva on usuario.id = reserva.idusuario 
group by usuario.tipo;