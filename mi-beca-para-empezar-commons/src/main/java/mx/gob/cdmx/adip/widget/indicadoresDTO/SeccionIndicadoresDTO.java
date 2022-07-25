package mx.gob.cdmx.adip.widget.indicadoresDTO;

import java.io.Serializable;
import java.util.Date;

public class SeccionIndicadoresDTO implements Serializable{
	private static final long serialVersionUID = -2757000350990201713L;

	private ServicioGralClimaDTO servicioClima;
	
	private ServicioGralDTO servicioGral;
	private ServicioSemaforoEpDTO servicioSemaforoEpDTO;
	private String msgProxSabado;
	private Date fechaActual;
	private String fecha;
	private String fechasVerificacion;
	private HoyNoCirculaDTO hoyNoCircula;
	
	//SEMAFORO EPIDEMIOLOGICO
	private ServicioSemaforoEpDTO semaforoEpi;
	
	public SeccionIndicadoresDTO() {
		servicioSemaforoEpDTO = new ServicioSemaforoEpDTO();
		servicioClima = new ServicioGralClimaDTO();
	}
	
	/*public List<CarouselDTO> getImagesCaroussel() {
		return imagesCaroussel;
	}
	public void setImagesCaroussel(List<CarouselDTO> imagesCaroussel) {
		this.imagesCaroussel = imagesCaroussel;
	}*/
	
	public ServicioGralClimaDTO getServicioClima() {
		return servicioClima;
	}
	public void setServicioClima(ServicioGralClimaDTO servicioClima) {
		this.servicioClima = servicioClima;
	}
	public ServicioGralDTO getServicioGral() {
		return servicioGral;
	}
	public void setServicioGral(ServicioGralDTO servicioGral) {
		this.servicioGral = servicioGral;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMsgProxSabado() {
		return msgProxSabado;
	}

	public void setMsgProxSabado(String msgProxSabado) {
		this.msgProxSabado = msgProxSabado;
	}

	public String getFechasVerificacion() {
		return fechasVerificacion;
	}

	public void setFechasVerificacion(String fechasVerificacion) {
		this.fechasVerificacion = fechasVerificacion;
	}

	public ServicioSemaforoEpDTO getSemaforoEpi() {
		return semaforoEpi;
	}

	public void setSemaforoEpi(ServicioSemaforoEpDTO semaforoEpi) {
		this.semaforoEpi = semaforoEpi;
	}

	public ServicioSemaforoEpDTO getServicioSemaforoEpDTO() {
		return servicioSemaforoEpDTO;
	}

	public void setServicioSemaforoEpDTO(ServicioSemaforoEpDTO servicioSemaforoEpDTO) {
		this.servicioSemaforoEpDTO = servicioSemaforoEpDTO;
	}

	public HoyNoCirculaDTO getHoyNoCircula() {
		return hoyNoCircula;
	}

	public void setHoyNoCircula(HoyNoCirculaDTO hoyNoCircula) {
		this.hoyNoCircula = hoyNoCircula;
	}
	
}
