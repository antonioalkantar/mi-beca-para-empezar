package mx.gob.cdmx.adip.beca.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URLConnection;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

/**
 * @author raul
 * 
 * Clase auxiliar para descargar archivos en JSF.
 * 
 * Contiene un buffer que evita saturar la memoria al momento de descargar archivos.
 * 
 * Así mismo, evita tener en producción logs con excepciones que son provocadas por el cliente al desconectarse repentinamente.
 */
@Named
@RequestScoped
public class DescargaBean implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(DescargaBean.class);
	
	private static final long serialVersionUID = 6509553154786306544L;
	
	/**
	 * Método que permite ver el documento en nueva pestaña
	 * 
	 * @param pathArchivo el path completo en el filesystem donde se encuentra el archivo a mandar a descarga
	 * @param forzarDescarga true para que se fuerce al navegador a descargar el arcivo, false para que el navegador intente mostrar el archivo
	 */
	public void descargar(final String pathArchivo, boolean forzarDescarga) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		
		File file = new File(pathArchivo);

		response.setContentType(URLConnection.guessContentTypeFromName(file.getName())); //Por ejemplo: image/png o application/pdf
	    response.setHeader("Content-disposition", (forzarDescarga ? "attachment;" : "inline;") + " filename="+pathArchivo.substring(pathArchivo.lastIndexOf(Constantes.SEPARADOR_RUTA)+1, pathArchivo.length()));
		
		/*
		 * El try with resources cerrará el "inputStream".
		 * El try with resources también cerrará el "outputStream" y al realizar el "close" hace el flush:
		 *   https://stackoverflow.com/questions/5043657/do-i-need-to-flush-the-servlet-outputstream
		 *   https://stackoverflow.com/questions/32324492/is-flush-call-necessary-when-using-try-with-resources
		 */
        try (FileInputStream inputStream = FileUtils.openInputStream(file);
        	 OutputStream outputStream = response.getOutputStream()) {
        	
        	byte[] buffer = new byte[1024]; //1KB
        	int bytesRead = 0;
        	int totalBytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;
                if (totalBytesRead > 1024 * 1024) { // flush cada 1MB (1024*1024)
                	totalBytesRead = 0;
                    outputStream.flush();
                }
                /* Con el siguiente código se podría probar alentar la descarga de un archivo y cerrar el navegador para obtener un broken pipe 
                try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
            }

            facesContext.responseComplete();
            facesContext.renderResponse();
		} catch (IOException e) {
			/*
			 * Cuando un ciudadano cierra la pestaña de su navegador o todo su navegador o pierde la conexión a internet
			 * o por alguna otra razón ya no esperó a que ocurriera la descarga de su archivo, en los logs aparecen estos errores:
			 * 
			 * java.io.IOException: Broken pipe
			 * ...
			 * ...
			 * Caused by: java.lang.IllegalStateException: UT010006: Cannot call getWriter(), getOutputStream() already called
			 * ...
			 * 
			 * Por cada "broken pipe" aparecen 2 errores "UT010006"
			 * 
			 * Con el propósito de no saturar los logs con esta situación solo se envía un WARN de una línea en lugar de todo el stacktrace.
			 * 
			 * No obstante, si es muy recurrente este error, se debería de analizar más a fondo si no hay algo en la red que corte la descarga del archivo,
			 * como un firewall, proxy, etc.
			 * 
			 * Por último, notar las sentencias facesContext.responseComplete(); facesContext.renderResponse(); las cuales si no se
			 * mandan a llamar  provoca que en el flujo de la petición siga apareciendo el error "UT010006: Cannot call getWriter(), ..."
			 * el cual ni agregando un Filter en el proyecto se puede cachar para suprimirlo de los logs.
			 */
			if(e.getMessage().contains("Broken pipe")){ 
				LOGGER.warn("El ciudadano no esperó a que terminara la descarga. Tamaño(MB):"+ (file.length() / 1024 / 2014) );
				facesContext.responseComplete();
	            facesContext.renderResponse();
			} else {
				LOGGER.error("Ocurrió un error '"+e.getMessage()+"' al descargar el documento:", e);	
			}
		}
	}

}
