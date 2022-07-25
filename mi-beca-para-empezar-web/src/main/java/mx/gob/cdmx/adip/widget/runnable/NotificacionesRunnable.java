package mx.gob.cdmx.adip.widget.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.widget.indicadoresDTO.NotificacionDTO;
import mx.gob.cdmx.adip.widget.application.NotificacionesBean;
import mx.gob.cdmx.adip.widget.client.NotificacionesCdmxClient;

public class NotificacionesRunnable implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionesRunnable.class);
	
	private final NotificacionesBean notificacionesBean;
	
	public NotificacionesRunnable(final NotificacionesBean notificacionesBean) {
		this.notificacionesBean = notificacionesBean; 
	}
	
	@Override
	public void run() {
		try {
			LOGGER.info("------------------> Notificaciones: Intentando actualizar las notificaciones");
			NotificacionesCdmxClient notificacionesClient = new NotificacionesCdmxClient();
			NotificacionDTO notificacionDTO = notificacionesClient.consultaNotificaciones(); 
			
			notificacionesBean.setNotificacionDTO(notificacionDTO);
			LOGGER.info("------------------> Notificaciones: Se actualizaron correctamente las notificaciones");
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error en NotificacionesRunnable:", e);
		}
	}
}
