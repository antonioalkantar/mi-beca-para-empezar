package mx.gob.cdmx.adip.beca.commons.dto;

import java.io.Serializable;

public class DocumentoLlaveDTO implements Serializable {

	private static final long serialVersionUID = 1097200289483315477L;

	private Integer idDocumento;
	private Integer idSubtipoDocumento;
	private String nombreDocumento;
	private Long fechaValidacionDocumento;
	private boolean documentoValido;
	private String documentoBase64;

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public Long getFechaValidacionDocumento() {
		return fechaValidacionDocumento;
	}

	public void setFechaValidacionDocumento(Long fechaValidacionDocumento) {
		this.fechaValidacionDocumento = fechaValidacionDocumento;
	}

	public boolean isDocumentoValido() {
		return documentoValido;
	}

	public void setDocumentoValido(boolean documentoValido) {
		this.documentoValido = documentoValido;
	}

	public String getDocumentoBase64() {
		return documentoBase64;
	}

	public void setDocumentoBase64(String documentoBase64) {
		this.documentoBase64 = documentoBase64;
	}

	public Integer getIdSubtipoDocumento() {
		return idSubtipoDocumento;
	}

	public void setIdSubtipoDocumento(Integer idSubtipoDocumento) {
		this.idSubtipoDocumento = idSubtipoDocumento;
	}
	
	

}
