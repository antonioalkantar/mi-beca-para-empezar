package mx.gob.cdmx.adip.beca.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "cat_monto_apoyo", schema = "mibecaparaempezar")
@NamedQueries({
	@NamedQuery(name="CatMontoApoyo.findAll"
			, query="SELECT NEW mx.gob.cdmx.adip.beca.commons.dto.CatMontoApoyoDTO "
					+ "( "
					+ "c.idMontoApoyo, c.monto, "
					+ "cne.idNivel, cne.descripcion, "
					+ "cce.idCicloEscolar, cce.descripcion "					
					+ " ) "
					+ " FROM CatMontoApoyo c "
					+ " JOIN c.catCicloEscolar cce "
					+ " JOIN c.catNivelEducativo cne "
					+ " where c.estatus = true "
					+ " and cce.estatus = true" 
					+ " and cne.estatus = true" )
})
public class CatMontoApoyo implements java.io.Serializable {

	private static final long serialVersionUID = 4559552642385103368L;
	
	private Integer idMontoApoyo;
	private CatCicloEscolar catCicloEscolar;
	private CatNivelEducativo catNivelEducativo;
	private Double monto;
	private Boolean estatus;
	private Date fechaCreacion;

	public CatMontoApoyo() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_monto_apoyo", unique = true, nullable = false)
	public Integer getIdMontoApoyo() {
		return this.idMontoApoyo;
	}

	public void setIdMontoApoyo(Integer idMontoApoyo) {
		this.idMontoApoyo = idMontoApoyo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ciclo_escolar", nullable = false)
	public CatCicloEscolar getCatCicloEscolar() {
		return this.catCicloEscolar;
	}

	public void setCatCicloEscolar(CatCicloEscolar catCicloEscolar) {
		this.catCicloEscolar = catCicloEscolar;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_nivel_educativo", nullable = false)
	public CatNivelEducativo getCatNivelEducativo() {
		return this.catNivelEducativo;
	}

	public void setCatNivelEducativo(CatNivelEducativo catNivelEducativo) {
		this.catNivelEducativo = catNivelEducativo;
	}

	@Column(name = "monto", nullable = false)
	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	@Column(name = "estatus")
	public Boolean getEstatus() {
		return this.estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = true)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}

