package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;

public class ServicioVerifClienteDTO implements Serializable{
	private static final long serialVersionUID = -5316791163756703285L;
	
	private String calcover1;
	private String calcover2;
	
	public ServicioVerifClienteDTO() {
		
	}

	public String getCalcover1() {
		return calcover1;
	}
	public void setCalcover1(String calcover1) {
		this.calcover1 = calcover1;
	}
	public String getCalcover2() {
		return calcover2;
	}
	public void setCalcover2(String calcover2) {
		this.calcover2 = calcover2;
	}

}
