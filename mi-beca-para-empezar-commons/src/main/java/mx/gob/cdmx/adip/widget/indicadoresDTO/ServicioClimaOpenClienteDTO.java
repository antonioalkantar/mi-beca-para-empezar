package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioClimaOpenClienteDTO implements Serializable{
	private static final long serialVersionUID = -1313229333511561720L;
	
	private String main;
	private String description;
	private String icon;
	public Integer codigo;
	
	public ServicioClimaOpenClienteDTO() {
		
	}
	
	public String getMain() {
		return main;
	}
	public void setMain(String main) {
		this.main = main;
	}
	public String getDescription() {
		return description != null ? description.replace("Muy nuboso", "Muy nublado").replace("Nubes", "Nublado") : " ";
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

}