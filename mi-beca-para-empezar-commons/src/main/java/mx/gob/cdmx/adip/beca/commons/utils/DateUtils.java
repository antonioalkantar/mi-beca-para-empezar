package mx.gob.cdmx.adip.beca.commons.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author raul
 */
public class DateUtils {
	
	private DateUtils() {
		/*Constructor privado para que no se pueda instanciar la clase*/
	}
	
	public static LocalDate convertDateToLocalDate(Date dateToConvert) {
	    return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static int getEdadByFechaNacimiento(final Date fechaNacimiento) {
		if(fechaNacimiento == null || fechaNacimiento.after(new Date())){
			throw new IllegalArgumentException("No se puede calcular la edad si la fecha de nacimiento es nula o es una fecha a futuro: "+ (fechaNacimiento == null ? "null" : fechaNacimiento.toString())) ;
		}
		return Period.between(convertDateToLocalDate(fechaNacimiento), LocalDate.now()).getYears();
	}
	
//	public static void main(String[] args) {
//		System.out.println("Edad:"+ getEdadByFechaNacimiento( new Date(85, 6, 21) ) ); //Ejemplo: Hoy es 20/Jul/22, si nació el 21/jul/85 = 36años
//		System.out.println("Edad:"+ getEdadByFechaNacimiento( new Date(130, 6, 21) ) ); //Ejemplo: Hoy es 20/Jul/22, si nació el 21/jul/30 = 36años
//		System.out.println("Edad:"+ getEdadByFechaNacimiento( null ) ); 
//	}
}
