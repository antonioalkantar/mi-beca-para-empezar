package mx.gob.cdmx.adip.beca.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cat_codigos_respuesta_pagatodo", schema = "mibecaparaempezar")
public class CatCodigosRespuestaPagatodo implements java.io.Serializable {

	private static final long serialVersionUID = -4776987499310124001L;
	
	private Long idCodigoPagatodo;
	private String descripcion;
	private Boolean estatus;

	public CatCodigosRespuestaPagatodo() {
	}	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_codigo_pagatodo", unique = true, nullable = false)
	public Long getIdCodigoPagatodo() {
		return this.idCodigoPagatodo;
	}

	public void setIdCodigoPagatodo(Long idCodigoPagatodo) {
		this.idCodigoPagatodo = idCodigoPagatodo;
	}

	@Column(name = "descripcion", length = 200)
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

