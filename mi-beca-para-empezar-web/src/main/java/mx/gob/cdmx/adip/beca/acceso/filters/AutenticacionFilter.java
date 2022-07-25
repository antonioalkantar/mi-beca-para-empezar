package mx.gob.cdmx.adip.beca.acceso.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.cdmx.adip.beca.commons.utils.Constantes;

/**
 * Filtro de seguridad que verifica todas las llamadas realizadas al aplicativo cuenten con un Token de Sesión.
 * Este filtro detecta si la petición fue enviada desde una de las URL's que requieran autenticación (Todas aquellas que no estén en la lista de URL's excluidas)
 * 
 * @author raul
 */
@WebFilter(filterName = "autenticacionFilter",
		   dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ASYNC},
		   urlPatterns = { "/protected/*"},
		   initParams = { @WebInitParam(name = "excludedExt", value = "jpeg jpg png pdf ico gif svg js css"),
				          @WebInitParam(name = "excludedUrl", value = "/index.xhtml /error.xhtml /denied.xhtml /home.xhtml /welcome.xhtml") })
public class AutenticacionFilter implements Filter{

	private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacionFilter.class);
	
	private static Set<String> excludedExtensions = null;
	private static Set<String> excludedUrls = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludedString = filterConfig.getInitParameter("excludedExt");
        if (excludedString != null) {
            excludedExtensions = Collections.unmodifiableSet(
                new HashSet<>(Arrays.asList(excludedString.split(" ", 0))));
        } else {
            excludedExtensions = Collections.<String>emptySet();
        }
        
        String urlsExcluidas = filterConfig.getInitParameter("excludedUrl");
        if (urlsExcluidas != null) {
        	excludedUrls = Collections.unmodifiableSet(
                new HashSet<>(Arrays.asList(urlsExcluidas.split(" ", 0))));
        } else {
        	excludedUrls = Collections.<String>emptySet();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if(!validaSesionactiva(httpRequest)) {
        	LOGGER.debug("----------> Cierre de sesión por inactividad.");
        	sendRedirect(httpRequest, httpResponse);
        } else if (isExtensionExcluded(httpRequest) || isUrlExcluded(httpRequest)) {
        	LOGGER.debug("------> Es URL y Extensión excluida, es decir no requiere autenticación y puede continuar");
            chain.doFilter(request, response);
        }else {
        	LOGGER.debug("-------> NO es URL y/o Extensión excluida, por lo tanto sí requiere autenticación");
        	if(validaAutenticacion(httpRequest)) {
        		LOGGER.debug("----------> El usuario SÍ tiene el token de sesión.");
        		chain.doFilter(request, response);
        	}else {
        		LOGGER.debug("----------> El usuario NO tiene el token de sesión.");
        		sendRedirect(httpRequest, httpResponse);
        	}
        }
    }
    
    private void sendRedirect(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse) throws IOException {
    	if ("partial/ajax".equals(httpRequest.getHeader("Faces-Request"))) {
    		httpResponse.setContentType("text/xml");
    		httpResponse.getWriter()
                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", httpRequest.getContextPath() + "/sessionExpired.xhtml");
        } else {
        	httpResponse.sendRedirect(httpRequest.getContextPath() + "/sessionExpired.xhtml");
        }
    }
    
    private boolean isExtensionExcluded(HttpServletRequest request) {
        String path = request.getRequestURI();
        LOGGER.debug("Filter URL 1:"+path);
        String extension = path.substring(path.indexOf('.', path.lastIndexOf('/')) + 1).toLowerCase();
        return excludedExtensions.contains(extension);
    }
    
    private boolean isUrlExcluded(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        LOGGER.debug("Filter URL 2:"+path);
        boolean allowedPath = path.isEmpty() || excludedUrls.contains(path);
        return allowedPath;
    }
    
    private boolean validaAutenticacion(HttpServletRequest httpRequest) {
    	HttpSession session = httpRequest.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute(Constantes.TOKEN_SESSION) != null);
        LOGGER.debug("se verifica la existencia del token" + session.getAttribute(Constantes.TOKEN_SESSION));
    	return loggedIn;
    }
    
    private boolean validaSesionactiva(HttpServletRequest httpRequest) {
    	HttpSession session = httpRequest.getSession(false);
    	boolean loggedOut = (session != null);
    	return loggedOut;
    }
}
