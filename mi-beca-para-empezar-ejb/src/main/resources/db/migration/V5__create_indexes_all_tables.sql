CREATE INDEX apoyos_pendientes_entregar_id_solicitud_idx ON mibecaparaempezar.apoyos_pendientes_entregar (id_solicitud);

CREATE INDEX beneficiario_curp_beneficiario_idx ON mibecaparaempezar.beneficiario (curp_beneficiario);

CREATE INDEX beneficiario_dispersion_id_dispersion_idx ON mibecaparaempezar.beneficiario_dispersion (id_dispersion);
CREATE INDEX beneficiario_dispersion_id_dispersion_idx_2 ON mibecaparaempezar.beneficiario_dispersion (id_dispersion,id_nivel_educativo);
CREATE INDEX beneficiario_dispersion_curp_beneficiario_idx ON mibecaparaempezar.beneficiario_dispersion (curp_beneficiario);

CREATE INDEX beneficiario_sin_dispersion_id_dispersion_idx ON mibecaparaempezar.beneficiario_sin_dispersion (id_dispersion);
CREATE INDEX beneficiario_sin_dispersion_curp_beneficiario_idx ON mibecaparaempezar.beneficiario_sin_dispersion (curp_beneficiario);

CREATE INDEX crc_beneficiario_solicitud_id_beneficiario_idx ON mibecaparaempezar.crc_beneficiario_solicitud (id_beneficiario,id_solicitud);

CREATE INDEX det_cuenta_beneficiario_id_beneficiario_idx ON mibecaparaempezar.det_cuenta_beneficiario (id_beneficiario);

CREATE INDEX det_revision_solicitud_id_solicitud_idx ON mibecaparaempezar.det_revision_solicitud (id_solicitud);

DROP INDEX mibecaparaempezar.encuesta_id_ciclo_escolar_id_solicitud_idx; -- No hay consulta alguna en el sistema por esa columna
CREATE INDEX encuesta_id_solicitud_idx ON mibecaparaempezar.encuesta (id_solicitud);

CREATE INDEX padron_beneficiarios_continuidad_curp_idx ON mibecaparaempezar.padron_beneficiarios_continuidad (curp);

CREATE INDEX padron_externo_curp_idx ON mibecaparaempezar.padron_externo (curp);

CREATE INDEX solicitud_folio_solicitud_idx ON mibecaparaempezar.solicitud (folio_solicitud);
CREATE INDEX solicitud_cct_idx ON mibecaparaempezar.solicitud (cct);
CREATE INDEX solicitud_id_nivel_educativo_idx ON mibecaparaempezar.solicitud (id_nivel_educativo);
CREATE INDEX solicitud_es_nuevo_registro_idx ON mibecaparaempezar.solicitud (es_nuevo_registro);

CREATE INDEX tutor_curp_idx ON mibecaparaempezar.tutor (curp);
CREATE INDEX tutor_id_estatus_idx ON mibecaparaempezar.tutor (id_estatus);
CREATE INDEX tutor_enviado_a_pagatodo_idx ON mibecaparaempezar.tutor (enviado_a_pagatodo);
CREATE INDEX tutor_nombre_idx ON mibecaparaempezar.tutor (nombre,primer_apellido,segundo_apellido,id_estatus);

DROP INDEX mibecaparaempezar.usuario_id_usuario_llave_cdmx_idx; -- Esa columna es una PK, por lo tanto ya está indexada
