package mx.gob.cdmx.adip.beca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name = "cat_comprobante_domicilio", schema = "mibecaparaempezar")
@NamedQueries({
	@NamedQuery(name = "CatComprobanteDomicilio.findByEstatusActive", query = "SELECT new mx.gob.cdmx.adip.beca.commons.dto.CatComprobanteDomicilioDTO("
			+ "	c.idComprobanteDomicilio,"
			+ "	c.descripcion,"
			+ "	c.estatus"
			+ ")"
			+ "	FROM CatComprobanteDomicilio c"
			+ "	WHERE c.estatus = TRUE")
})
public class CatComprobanteDomicilio implements Serializable {
	
	private static final long serialVersionUID = -7828960294706881043L;

	private Integer idComprobanteDomicilio;
	private String descripcion;
	private Boolean estatus;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_comprobante_domicilio", unique = true, nullable = false)
	public Integer getIdComprobanteDomicilio() {
		return idComprobanteDomicilio;
	}

	public void setIdComprobanteDomicilio(Integer idComprobanteDomicilio) {
		this.idComprobanteDomicilio = idComprobanteDomicilio;
	}
	@Column(name = "descripcion", nullable = false, length = 100)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Column(name = "estatus")
	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
}
