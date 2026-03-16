DROP PROCEDURE IF EXISTS ActualizarClasificacion;

DELIMITER $$
CREATE PROCEDURE `ActualizarClasificacion` (IN `temporada_in` VARCHAR(10))
BEGIN
    -- Lógica para la temporada 24-25
    IF `temporada_in` = '24-25' THEN
        DELETE FROM `sailkapena_24_25`;
        INSERT INTO `sailkapena_24_25` (taldea, JP, IrP, BerP, GaP, AG, KG, puntuak)
        SELECT 
            taldea,
            COUNT(*) as JP,
            SUM(CASE WHEN resultado = 'GANADO' THEN 1 ELSE 0 END) as IrP,
            SUM(CASE WHEN resultado = 'EMPATE' THEN 1 ELSE 0 END) as BerP,
            SUM(CASE WHEN resultado = 'PERDIDO' THEN 1 ELSE 0 END) as GaP,
            SUM(goles_favor) as AG,
            SUM(goles_contra) as KG,
            (SUM(CASE WHEN resultado = 'GANADO' THEN 1 ELSE 0 END) * 2 + 
             SUM(CASE WHEN resultado = 'EMPATE' THEN 1 ELSE 0 END)) as puntuak
        FROM (
            SELECT 
                Talde_lokala as taldea, Golak_lokala as goles_favor, Golak_kanpokoak as goles_contra,
                CASE WHEN Golak_lokala > Golak_kanpokoak THEN 'GANADO' WHEN Golak_lokala = Golak_kanpokoak THEN 'EMPATE' ELSE 'PERDIDO' END as resultado
            FROM partidua 
            WHERE denboraldia = '24-25' AND Golak_lokala IS NOT NULL
            UNION ALL
            SELECT 
                Kampoko_taldea as taldea, Golak_kanpokoak as goles_favor, Golak_lokala as goles_contra,
                CASE WHEN Golak_kanpokoak > Golak_lokala THEN 'GANADO' WHEN Golak_kanpokoak = Golak_lokala THEN 'EMPATE' ELSE 'PERDIDO' END as resultado
            FROM partidua 
            WHERE denboraldia = '24-25' AND Golak_lokala IS NOT NULL
        ) as t
        GROUP BY taldea
        ORDER BY puntuak DESC, (SUM(goles_favor) - SUM(goles_contra)) DESC;
    END IF;

    -- Lógica para la temporada 25-26
    IF `temporada_in` = '25-26' THEN
        DELETE FROM `sailkapena_25_26`;
        INSERT INTO `sailkapena_25_26` (taldea, JP, IrP, BerP, GaP, AG, KG, puntuak)
        SELECT 
            taldea,
            COUNT(*) as JP,
            SUM(CASE WHEN resultado = 'GANADO' THEN 1 ELSE 0 END) as IrP,
            SUM(CASE WHEN resultado = 'EMPATE' THEN 1 ELSE 0 END) as BerP,
            SUM(CASE WHEN resultado = 'PERDIDO' THEN 1 ELSE 0 END) as GaP,
            SUM(goles_favor) as AG,
            SUM(goles_contra) as KG,
            (SUM(CASE WHEN resultado = 'GANADO' THEN 1 ELSE 0 END) * 2 + 
             SUM(CASE WHEN resultado = 'EMPATE' THEN 1 ELSE 0 END)) as puntuak
        FROM (
            SELECT 
                Talde_lokala as taldea, Golak_lokala as goles_favor, Golak_kanpokoak as goles_contra,
                CASE WHEN Golak_lokala > Golak_kanpokoak THEN 'GANADO' WHEN Golak_lokala = Golak_kanpokoak THEN 'EMPATE' ELSE 'PERDIDO' END as resultado
            FROM partidua 
            WHERE denboraldia = '25-26' AND Golak_lokala IS NOT NULL
            UNION ALL
            SELECT 
                Kampoko_taldea as taldea, Golak_kanpokoak as goles_favor, Golak_lokala as goles_contra,
                CASE WHEN Golak_kanpokoak > Golak_lokala THEN 'GANADO' WHEN Golak_kanpokoak = Golak_lokala THEN 'EMPATE' ELSE 'PERDIDO' END as resultado
            FROM partidua 
            WHERE denboraldia = '25-26' AND Golak_lokala IS NOT NULL
        ) as t
        GROUP BY taldea
        ORDER BY puntuak DESC, (SUM(goles_favor) - SUM(goles_contra)) DESC;
    END IF;
END$$
DELIMITER ;