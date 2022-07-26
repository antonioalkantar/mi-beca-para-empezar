package mx.gob.cdmx.adip.beca.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CatEstatusDispersion generated by Antonio Alcantar Valencia
 */
@Entity
@Table(name = "cat_estatus_dispersion", schema = "mibecaparaempezar")
public class CatEstatusDispersion implements java.io.Serializable {
		
	private static final long serialVersionUID = -8493803739019289554L;
	private Integer idEstatusDispersion;
	private String descripcion;
	private Boolean estatus;

	public CatEstatusDispersion() {
	}

	public CatEstatusDispersion(Integer idEstatusDispersion, String descripcion, Boolean estatus) {
		this.idEstatusDispersion = idEstatusDispersion;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_estatus_dispersion", unique = true, nullable = false)
	public Integer getIdEstatusDispersion() {
		return this.idEstatusDispersion;
	}

	public void setIdEstatusDispersion(Integer idEstatusDispersion) {
		this.idEstatusDispersion = idEstatusDispersion;
	}

	@Column(name = "descripcion", nullable = false, length = 50)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "estatus")
	public Boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}


}