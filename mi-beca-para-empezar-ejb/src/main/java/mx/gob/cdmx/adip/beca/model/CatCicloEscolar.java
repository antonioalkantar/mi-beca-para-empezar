package mx.gob.cdmx.adip.beca.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CatCicloEscolar generated by Antonio Alcantar Valencia
 */
@Entity
@Table(name = "cat_ciclo_escolar", schema = "mibecaparaempezar")
@NamedQueries({
	  @NamedQuery(name="CatCicloEscolar.findCicloVigente" 
	, query="SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.CatCicloEscolarDTO "
					+ "( "
					+ "c.idCicloEscolar, "
					+ "c.descripcion "
					+ ") "
					+ "FROM CatCicloEscolar c "
					+ "WHERE c.estatus = true "
					),
	@NamedQuery(name="CatCicloEscolar.findAll"
			, query="SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.CatCicloEscolarDTO "
					+ "( "
					+ "c.idCicloEscolar, "
					+ "c.descripcion, "
					+ "c.fechaInicio, "
					+ "c.fechaFin, "
					+ "c.estatus, "
					+ "c.fechaRegistro "
					+ ") "
					+ "FROM CatCicloEscolar c "
					+ "where c.estatus = true "
					+ "	ORDER BY c.descripcion ASC ")
})
public class CatCicloEscolar implements java.io.Serializable {
		
	private static final long serialVersionUID = -8493803739019289554L;
	private Integer idCicloEscolar;
	private String descripcion;
	private Date fechaInicio;
	private Date fechaFin;
	private Boolean estatus;
	private Date fechaRegistro;

	public CatCicloEscolar() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_ciclo_escolar", unique = true, nullable = false)
	public Integer getIdCicloEscolar() {
		return this.idCicloEscolar;
	}

	public void setIdCicloEscolar(Integer idCicloEscolar) {
		this.idCicloEscolar = idCicloEscolar;
	}

	@Column(name = "descripcion", nullable = false, length = 50)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicio", nullable = true)
	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_fin", nullable = true)
	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Column(name = "estatus")
	public Boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro", nullable = true)
	public Date getFechaRegistro() {
		return this.fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


}
