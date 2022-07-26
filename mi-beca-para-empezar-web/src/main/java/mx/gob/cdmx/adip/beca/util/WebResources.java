/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mx.gob.cdmx.adip.beca.util;

import java.util.ResourceBundle;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class WebResources {

	private final static String ID_GLOBAL_MESSAGES = "messages"; //<p:messages id="messages" ...>
	
    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
    public static String getBundleMsg(final String clave) {
    	return ResourceBundle.getBundle("messages.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString(clave);
    }
    
    public static void addSuccessMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, getBundleMsg(Mensajes.MSJ_ACCION_CONFIRMADA), getBundleMsg(claveMensaje)));
		PrimeFaces.current().ajax().update(ID_GLOBAL_MESSAGES);
    }
    
    public static void addValidationMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, getBundleMsg(Mensajes.MSJ_ACCION_NO_CONFIRMADA), getBundleMsg(claveMensaje)));
		PrimeFaces.current().ajax().update(ID_GLOBAL_MESSAGES);
    }
    
    public static void addErrorMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, getBundleMsg(Mensajes.MSJ_ACCION_NO_CONFIRMADA), getBundleMsg(claveMensaje)));
		PrimeFaces.current().ajax().update(ID_GLOBAL_MESSAGES);
    }
    
    // Se integran métodos para los mensajes mostrados en las vistas sin el texto que indica el tipo de mensaje.
    public static void successMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, null, getBundleMsg(claveMensaje)));
		PrimeFaces.current().ajax().update(ID_GLOBAL_MESSAGES);
    }
    
    public static void validationMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, null, getBundleMsg(claveMensaje)));
		PrimeFaces.current().ajax().update(ID_GLOBAL_MESSAGES);
    }
    
    public static void errorMessage(String claveMensaje, boolean ... keepJsfMessage ) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages( keepJsfMessage == null || keepJsfMessage.length == Constantes.SIZE_ARRAY_EMPTY ? Boolean.FALSE : keepJsfMessage[0]);
		FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, null, getBundleMsg(claveMensaje)));
		PrimeFaces.current().ajax().update(ID_GLOBAL_MESSAGES);
    }
    
    
    public static void addErrorMessage(String claveMensaje, String component, boolean scrollToComponent) {
    	FacesContext.getCurrentInstance().addMessage(FacesContext.getCurrentInstance().getViewRoot().findComponent(component).getClientId(), 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, null, WebResources.getBundleMsg(claveMensaje)));
    	PrimeFaces.current().ajax().update(component);
		if(scrollToComponent) {
			PrimeFaces.current().scrollTo(component);
		}
    }
}