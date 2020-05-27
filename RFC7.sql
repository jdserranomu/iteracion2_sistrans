-- fecha de mayor demanda
 Select to_char(fechainicio, 'YYYY-MM') as meses, count (*) as cuantos
    from reserva
    where estado=0 and idInmueble in (
        select id
        from inmueble
        where tipo='Apartamento'
    )
    group by to_char(fechainicio, 'YYYY-MM')
    having count(*) in (
        Select max(cuantos)
        from(
            Select to_char(fechainicio, 'YYYY-MM') as meses, count (*) as cuantos
            from reserva
            where estado=0 and idInmueble in (
                select id
                from inmueble
                where tipo='Apartamento'
            )
            group by to_char(fechainicio, 'YYYY-MM')
        )  
    );



--fecha de mayor ingresos
 Select to_char(fechainicio, 'YYYY-MM') as meses, sum (valortotal) as cuantos
    from reserva
    where estado=0 and idInmueble in (
        select id
        from inmueble
        where tipo='Apartamento'
    )
    group by to_char(fechainicio, 'YYYY-MM')
    having sum(valortotal) in (
        Select  max(dinero)
        from(
            Select to_char(fechainicio, 'YYYY-MM') as meses, sum (valortotal) as dinero
            from reserva
            where estado=0 and idInmueble in (
                select id
                from inmueble
                where tipo='Apartamento'
            )
            group by to_char(fechainicio, 'YYYY-MM')
        )  
    );



--fecha menor ocupacion

 Select to_char(fechainicio, 'YYYY-MM') as meses, count (*) as cuantos
    from reserva
    where estado=0 and idInmueble in (
        select id
        from inmueble
        where tipo='Apartamento'
    )
    group by to_char(fechainicio, 'YYYY-MM')
    having count(*) in (
        Select min(cuantos)
        from(
            Select to_char(fechainicio, 'YYYY-MM') as meses, count (*) as cuantos
            from reserva
            where estado=0 and idInmueble in (
                select id
                from inmueble
                where tipo='Apartamento'
            )
            group by to_char(fechainicio, 'YYYY-MM')
        )  
    );