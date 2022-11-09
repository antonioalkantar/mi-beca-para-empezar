-- Se crea UNIQUE para la estructura encuesta para los campos de solicitud y ciclo escolar
ALTER TABLE mibecaparaempezar.encuesta ADD UNIQUE (id_solicitud, id_ciclo_escolar);