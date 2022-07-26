package mx.gob.cdmx.adip.beca.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CatTiposAsentamiento generated by hbm2java
 */
@Entity
@Table(name = "cat_tipos_asentamiento", schema = "mibecaparaempezar")
public class CatTiposAsentamiento implements java.io.Serializable {

	private static final long serialVersionUID = -6795842420653251612L;
	
	private int idTipoAsentamiento;
	private String descripcion;

	public CatTiposAsentamiento() {
	}

	public CatTiposAsentamiento(int idTipoAsentamiento, String descripcion) {
		this.idTipoAsentamiento = idTipoAsentamiento;
		this.descripcion = descripcion;
	}

	@Id
	@Column(name = "id_tipo_asentamiento", unique = true, nullable = false)
	public int getIdTipoAsentamiento() {
		return this.idTipoAsentamiento;
	}

	public void setIdTipoAsentamiento(int idTipoAsentamiento) {
		this.idTipoAsentamiento = idTipoAsentamiento;
	}

	@Column(name = "descripcion", nullable = false, length = 30)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
