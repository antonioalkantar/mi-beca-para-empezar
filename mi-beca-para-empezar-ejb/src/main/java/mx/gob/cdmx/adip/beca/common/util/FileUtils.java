package mx.gob.cdmx.adip.beca.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
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
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zipOut = new ZipOutputStream(bos);
			for (String srcFile : rutas) {
				File fileToZip = new File(srcFile);
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();
			bos.close();
			InputStream inputStream = new ByteArrayInputStream(bos.toByteArray());
			return inputStream;
		} catch (IOException ioe) {
			return null;
		}
	}
}
