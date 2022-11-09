package mx.gob.cdmx.adip.beca.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FileUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

	private FileUtils() {
	}

	/**
	 * Lee los archivos del arreglo de rutas, los coloca en un zip y regresa un input stream.
	 * @param rutas
	 * @return
	 */
	public static InputStream obtenerZipInputStream(List<String> rutas) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zipOut = new ZipOutputStream(bos);
		InputStream inputStream = null;
		try {
			for (String srcFile : rutas) {
				File fileToZip = new File(srcFile);
				FileInputStream fis = new FileInputStream(fileToZip);
				String[] fileName = fileToZip.getName().split("_");
				ZipEntry zipEntry = new ZipEntry(fileName[1] + "_" + fileName[2]);
				zipOut.putNextEntry(zipEntry);
				
				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				try {
					zipOut.closeEntry();	
				} catch (ZipException ze) {
					LOGGER.warn("Ocurrio un error ZipException al cerrar el recurso ZipOutputStream: ", ze);
				} catch (IOException ioe) {
					LOGGER.warn("Ocurrio un error al cerrar el recurso ZipOutputStream: ", ioe);
				}
				try {
					fis.close();	
				} catch (IOException ie) {
					LOGGER.warn("Ocurrio un error al cerrar el recurso FileInputStream: ", ie);
				}
			}
			try {
				zipOut.finish();
			} catch (ZipException ze) {
				LOGGER.warn("Ocurrio un error ZipException al cerrar el recurso ZipOutputStream: ", ze);
			} catch (IOException ioe) {
				LOGGER.warn("Ocurrio un error al cerrar el recurso ZipOutputStream: ", ioe);
			}
			
			inputStream = new ByteArrayInputStream(bos.toByteArray());
			return inputStream;
		} catch (IOException ioe) {
			return null;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				LOGGER.warn("Ocurrio un error al cerrar el recurso InputStream: ", e);
			}
			try {
				zipOut.close();
			} catch (ZipException ze) {
				LOGGER.warn("Ocurrio un error ZipException al cerrar el recurso ZipOutputStream: ", ze);
			} catch (IOException ioe) {
				LOGGER.warn("Ocurrio un error al cerrar el recurso ZipOutputStream: ", ioe);
			}
			try {
				bos.close();
			} catch (IOException oe) {
				LOGGER.warn("Ocurrio un error al cerrar el recurso ByteArrayOutputStream: ", oe);
			}
		}
	}
}
