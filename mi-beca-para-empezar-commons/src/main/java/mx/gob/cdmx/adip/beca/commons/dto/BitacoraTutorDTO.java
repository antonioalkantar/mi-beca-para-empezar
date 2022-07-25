package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;
import java.util.Date;

public class BitacoraTutorDTO implements Serializable {

	private static final long serialVersionUID = -7092265855856973756L;

	private Long idBitacora;
	private TutorDTO tutorDTO;
	private Long idUsuarioFidegar;
	private Date fechaCaptura;
	private CatEstatusDTO catEstatusDTO;

	public BitacoraTutorDTO() {
		tutorDTO = new TutorDTO();
		catEstatusDTO = new CatEstatusDTO(); 
	}

	public Long getIdBitacora() {
		return idBitacora;
	}

	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}

	public TutorDTO getTutorDTO() {
		return tutorDTO;
	}

	public void setTutorDTO(TutorDTO tutorDTO) {
		this.tutorDTO = tutorDTO;
	}

	public Long getIdUsuarioFidegar() {
		return idUsuarioFidegar;
	}

	public void setIdUsuarioFidegar(Long idUsuarioFidegar) {
		this.idUsuarioFidegar = idUsuarioFidegar;
	}

	public Date getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(Date fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	public CatEstatusDTO getCatEstatusDTO() {
		return catEstatusDTO;
	}

	public void setCatEstatusDTO(CatEstatusDTO catEstatusDTO) {
		this.catEstatusDTO = catEstatusDTO;
	}

}
