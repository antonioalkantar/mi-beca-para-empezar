package mx.gob.cdmx.adip.widget.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.widget.indicadoresDTO.ServicioClimaOpenClienteDTO;
import mx.gob.cdmx.adip.widget.application.WidgetBean;
import mx.gob.cdmx.adip.widget.client.ServicioClimaOpenCliente;

public class ClimaRunnable implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(ClimaRunnable.class);
	
	private final WidgetBean widgetBean;
	
	public ClimaRunnable(final WidgetBean widgetBean) {
		this.widgetBean = widgetBean; 
	}
	
	@Override
	public void run() {
		try {
			LOGGER.info("------------------> WIDGET: Intentando actualizar en el portal el clima desde openwheatermar.org ... ");
			ServicioClimaOpenCliente servicioConsultaClimaOpen = new ServicioClimaOpenCliente();
			ServicioClimaOpenClienteDTO climaopenDTO = servicioConsultaClimaOpen.consultaClimaOpen();
			
			if(climaopenDTO != null && climaopenDTO.getDescription() != null && !climaopenDTO.getDescription().isEmpty() ) {
				widgetBean.getSeccionIndicadores().getServicioClima().setServClimaOpenDTO(climaopenDTO);
			}
			LOGGER.info("------------------> WIDGET: Se actualizó correctamente en el portal el clima desde openwheatermar.org");			
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error en ClimaRunnable:", e);
		}
	}
}
