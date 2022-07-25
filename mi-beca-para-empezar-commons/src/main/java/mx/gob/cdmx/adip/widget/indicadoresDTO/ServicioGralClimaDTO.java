package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioGralClimaDTO implements Serializable{
	private static final long serialVersionUID = -6914933061461102103L;
	
	private ServicioClimaClienteDTO servClimaDTO;	
	private ServicioCalAireClienteDTO servCalAireDTO;
	private ServicioIUVClienteDTO servIuvDTO;
	private Integer codigo;
	private ServicioClimaOpenClienteDTO servClimaOpenDTO;
	
	public ServicioGralClimaDTO() {
		servClimaDTO = new ServicioClimaClienteDTO();
	}
	
	public ServicioClimaClienteDTO getServClimaDTO() {
		return servClimaDTO;
	}
	public void setServClimaDTO(ServicioClimaClienteDTO servClimaDTO) {
		this.servClimaDTO = servClimaDTO;
	}
	public ServicioCalAireClienteDTO getServCalAireDTO() {
		return servCalAireDTO;
	}
	public void setServCalAireDTO(ServicioCalAireClienteDTO servCalAireDTO) {
		this.servCalAireDTO = servCalAireDTO;
	}
	public ServicioIUVClienteDTO getServIuvDTO() {
		return servIuvDTO;
	}
	public void setServIuvDTO(ServicioIUVClienteDTO servIuvDTO) {
		this.servIuvDTO = servIuvDTO;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public ServicioClimaOpenClienteDTO getServClimaOpenDTO() {
		return servClimaOpenDTO;
	}
	public void setServClimaOpenDTO(ServicioClimaOpenClienteDTO servClimaOpenDTO) {
		this.servClimaOpenDTO = servClimaOpenDTO;
	}	

}
