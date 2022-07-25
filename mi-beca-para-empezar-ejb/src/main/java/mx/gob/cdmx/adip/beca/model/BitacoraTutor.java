package mx.gob.cdmx.adip.beca.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "bitacora_tutor", schema = "mibecaparaempezar")
@NamedQueries({

})
public class BitacoraTutor implements Serializable {

	private static final long serialVersionUID = -3794755094928486068L;

	private Long idBitacora;
	private Tutor tutor;
	private Long idUsuarioFidegar;
	private Date fechaCaptura;
	private CatEstatus catEstatusBitacora;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_bitacora", unique = true, nullable = false)
	public Long getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_llave_cdmx", nullable = false)
	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	@Column(name = "id_usuario_fidegar")
	public Long getIdUsuarioFidegar() {
		return idUsuarioFidegar;
	}

	public void setIdUsuarioFidegar(Long idUsuarioFidegar) {
		this.idUsuarioFidegar = idUsuarioFidegar;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_captura", nullable = false)
	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus", nullable = false)
	public CatEstatus getCatEstatusBitacora() {
		return catEstatusBitacora;
	}

	public void setCatEstatusBitacora(CatEstatus catEstatusBitacora) {
		this.catEstatusBitacora = catEstatusBitacora;
	}
}
