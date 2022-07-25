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
@Table(name = "cat_nivel_educativo", schema = "mibecaparaempezar")
@NamedQueries({
	@NamedQuery(name="CatNivelEducativo.findAll"
			, query="SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.CatNivelEducativoDTO "
					+ "( "
					+ "c.idNivel, "
					+ "c.descripcion, "
					+ "c.estatus "
					+ ") "
					+ "FROM CatNivelEducativo c "
					+ "where c.estatus = true "
					+ " ORDER BY c.descripcion ASC ")
})
public class CatNivelEducativo implements Serializable {

	private static final long serialVersionUID = 5520974165568512892L;
	private Integer idNivel;
	private String descripcion;
	private Boolean estatus;
	
	public CatNivelEducativo() {}

	public CatNivelEducativo(Integer idNivel, String descripcion, Boolean estatus) {
		this.idNivel = idNivel;
		this.descripcion = descripcion;
		this.estatus = estatus;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_nivel", unique = true, nullable = false)
	public Integer getIdNivel() {
		return this.idNivel;
	}

	public void setIdNivel(Integer idNivel) {
		this.idNivel = idNivel;
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
