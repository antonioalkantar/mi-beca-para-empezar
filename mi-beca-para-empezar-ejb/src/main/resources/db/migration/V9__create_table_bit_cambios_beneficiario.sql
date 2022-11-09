-- Se crea tabla para guardar los cambios actualizados de los beneficiarios vs el WS de Autoridad Educativa
CREATE SEQUENCE bitacora_cambios_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1;


CREATE TABLE bit_cambios_beneficiarios (
	id_bit_cambio_beneficiario int8 NOT NULL,
	id_beneficiario int8 NOT NULL,
	id_solicitud int8 NOT NULL,
	id_dispersion int8 NOT NULL,
	cct_anterior character varying(15) NULL,
	nombre_cct_anterior character varying(200) NULL,
	calle_cct_anterior character varying(200) NULL,
	colonia_cct_anterior character varying(100) NULL,
	codigo_postal_cct_anterior character varying(5) NULL,
	id_alcaldia_cct_anterior int4 NULL,
	id_turno_anterior character varying(100) NULL,
	id_nivel_educativo_anterior int8 NULL,
	grado_escolar_anterior character varying(50) NULL,
	fecha_registro timestamp NOT NULL,
	CONSTRAINT bitacora_cambios_beneficiario_pk PRIMARY KEY (id_bit_cambio_beneficiario),
	CONSTRAINT beneficiario_id_beneficiario_bcb_fk FOREIGN KEY (id_beneficiario) REFERENCES beneficiario(id_beneficiario),
	CONSTRAINT solicitud_id_solicitud_bcb_fk FOREIGN KEY (id_solicitud) REFERENCES solicitud (id_solicitud),
	CONSTRAINT dispersion_id_dispersion_bcb_fk FOREIGN KEY (id_dispersion) REFERENCES dispersion(id_dispersion)
);

ALTER TABLE bit_cambios_beneficiarios ALTER COLUMN id_bit_cambio_beneficiario SET DEFAULT nextval('bitacora_cambios_seq');

INSERT INTO cat_motivo_no_dispersion(id_motivo_no_dispersion,descripcion,estatus) VALUES(1,'Beneficiario no inscrito',true);
INSERT INTO cat_motivo_no_dispersion(id_motivo_no_dispersion,descripcion,estatus) VALUES(2,'Tutor no aprobado',true);
INSERT INTO cat_motivo_no_dispersion(id_motivo_no_dispersion,descripcion,estatus) VALUES(3,'Sin número de cuenta',true);
INSERT INTO cat_motivo_no_dispersion(id_motivo_no_dispersion,descripcion,estatus) VALUES(4,'Fallo en WS de Autoridad Educativa',true);