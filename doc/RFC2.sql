SELECT IDINMUEBLE, CNT
FROM
(SELECT INM.ID AS IDINMUEBLE, COUNT(*) AS CNT
FROM INMUEBLE INM
INNER JOIN RESERVA RES ON INM.ID=RES.IDINMUEBLE
GROUP BY INM.ID
ORDER BY COUNT(*) DESC)
WHERE ROWNUM  <= 20;