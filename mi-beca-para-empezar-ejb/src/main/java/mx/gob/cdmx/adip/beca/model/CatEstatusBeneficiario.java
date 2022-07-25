package mx.gob.cdmx.adip.beca.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cat_estatus_beneficiario", schema = "mibecaparaempezar")
@NamedQueries({
	@NamedQuery(name="CatEstatusBeneficiario.findAll"
			, query="SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.CatEstatusBeneficiarioDTO "
					+ "( "
					+ "c.idEstatusBeneficiario, "
					+ "c.descripcion, "
					+ "c.estatus "
					+ ") "
					+ "FROM CatEstatusBeneficiario c "
					+ "where c.estatus = true "
					+ " ORDER BY c.descripcion ASC ")
})
public class CatEstatusBeneficiario implements java.io.Serializable {

	private static final long serialVersionUID = 5220289892734072718L;

	private Integer idEstatusBeneficiario;
	private String descripcion;
	private Boolean estatus;

	public CatEstatusBeneficiario() {
	}

	public CatEstatusBeneficiario(Integer idEstatusBeneficiario, String descripcion) {
		this.idEstatusBeneficiario = idEstatusBeneficiario;
		this.descripcion = descripcion;
	}

	public CatEstatusBeneficiario(Integer idEstatusBeneficiario, String descripcion, Boolean estatus) {
		this.idEstatusBeneficiario = idEstatusBeneficiario;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}

	@Id
	@Column(name = "id_estatus_beneficiario", unique = true, nullable = false)
	public Integer getIdEstatusBeneficiario() {
		return this.idEstatusBeneficiario;
	}

	public void setIdEstatusBeneficiario(Integer idEstatusBeneficiario) {
		this.idEstatusBeneficiario = idEstatusBeneficiario;
	}

	@Column(name = "descripcion", nullable = false, length = 60)
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