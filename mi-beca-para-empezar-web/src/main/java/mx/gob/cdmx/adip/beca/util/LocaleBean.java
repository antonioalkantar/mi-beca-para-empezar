package mx.gob.cdmx.adip.beca.util;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("localeBean")
@SessionScoped
public class LocaleBean implements Serializable{

	private static final long serialVersionUID = 4653289218173423056L;
	
	private String locale = "es";
	
	public String cambiarLenguage(String locale) {
		this.locale = locale;
		//Se debe cambiar el Locale a nivel de Contexto para que cuando se obtengan los mensajes desde codigo Java funcione adecuadamente.
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(locale));
		return "";
	}

	public String getLocale() {
		return locale;
	}
}
