package mx.gob.cdmx.adip.commons.db;

import java.sql.SQLException;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author raul
 * 
 * Esta clase es otra implementación de Flyway para que haga las "migraciones". La que se usa en este proyecto es la clase FlywayIntegrator.java
 * 
 * A diferencia de la clase FlywayIntegrator.java que se ejecuta casi al finalizar de levantar el Wildfly, esta implementación se integra al ciclo de vida de Hibernate
 * y se ejecuta antes de que ocurra la etapa de validación de los entities.
 * 
 * Entonces, esta implementación aunque es más compleja de configurar, podría funcionar si en el persistence.xml se establece el parámetro hibernate.hbm2ddl.auto=validate
 * 
 * En este proyecto no es necesario validar la estructura de BD, por lo tanto no es requerido. Si se quisiera echar a andar esta clase, habría que comentar las anotaciones de la 
 * clase FlywayIntegrator.java para evitar que se ejecute y, por otra parte, habría que mover el archivo org.hibernate.integrator.spi.Integrator 
 * dentro de la carpeta /META-INF/services el cual se encargará de ejecutar esta clase
 * 
 * 
 * Esta clase está basada en este ejemplo: https://github.com/dobermai/Hibernate-Flyway-Integration
 * 
 * Errores comunes:
 * 1. Caused by: javax.ejb.EJBException: org.flywaydb.core.internal.license.FlywayEditionUpgradeRequiredException: Flyway Teams Edition or H2 upgrade required: H2 1.4.193 is no longer supported by Flyway Community Edition, but still supported by Flyway Teams Edition.
 * 
 * Este error sucede porque en el standalone.xml se tiene definido por default un datasource llamado ExampleDS en la línea: que usa una BD H2 que ya no soporta Flyway en su versión comunitaria, sino solo en su versión de paga (Teams Edition).
 * 		<default-bindings context-service="java:jboss/ee/concurrency/context/default" datasource="java:jboss/datasources/DefaultDS" ...
 * Cuando se hace el @Resource (sin indicar el lookup) trae primero ese Datasource por defecto, en lugar del datasource de la aplicación.
 * Por lo tanto, para solventarlo, hay 2 opciones:
 * OPCION A)
 * Hay que cambiar el datasource por default del standalone.xml:
 * <default-bindings context-service="java:jboss/ee/concurrency/context/default" datasource="java:jboss/datasources/miBecaParaEmpezarDS" ...
 * 
 * OPCION B)
 * En esta clase, en lugar de usar solo @Resource, indicar con el parámetro lookup el nombre exacto del datasource a usar:
 * @Resource(lookup="java:jboss/datasources/miBecaParaEmpezarDS") 
 *  
 *  2. Caused by: javax.ejb.EJBException: org.flywaydb.core.internal.exception.FlywaySqlException: Unable to obtain connection from database: javax.resource.ResourceException: IJ000453: Unable to get managed connection for java:jboss/datasources/miBecaParaEmpezarDS
 *    Caused by: javax.resource.ResourceException: IJ000453: Unable to get managed connection for java:jboss/datasources/miBecaParaEmpezarDS
    Caused by: javax.resource.ResourceException: IJ000655: No managed connections available within configured blocking timeout (30000 [ms])"}}
    
    Esto puede suceder si en el standalone se especifican muy pocas conexiones para el pool del datasource:
    <pool>
	    <min-pool-size>1</min-pool-size>
	    <max-pool-size>20</max-pool-size>
	    <prefill>true</prefill>
	</pool>
	
	 Para evitar este problema, asegúrese de tener un max-pool-size mayor a 5 conexiones.
 *
 * 3. Caused by: javax.ejb.EJBException: org.flywaydb.core.api.exception.FlywayValidateException: Validate failed: Migrations have failed validation
		Migration checksum mismatch for migration version 1
		-> Applied to database : 1461570782
		-> Resolved locally    : 275067163
 * Este problema ocurre si ya se ejecutó una migración y después de ejecutarla se modifica el archivo .sql flyway detectará que el checksum del archivo no corresponde con el ejecutado anteriormente y generará este error
 * Para evitar este problema, se debe evitar modificar los archivos .sql de las migraciones.
 * Si se modificó, tratar de recuperar el archivo modificado del repositorio. 
 * Si no se puede recuperar, PEDIR RESPALDO DE LA BD (Si es productiva) e intentar una de estas soluciones (SIN USAR CLEAN PORQUE BORRARÍA LA BD): https://stackoverflow.com/questions/41147768/caused-by-org-flywaydb-core-api-flywayexception-validate-failed-migration-che
 *
 *  4. Caused by: org.flywaydb.core.api.FlywayException: Found non-empty schema(s) "mibecaparaempezar_v2" but no schema history table. Use baseline() or set baselineOnMigrate to true to initialize the schema history table.
 *  Este error sucede si flyway se ejecuta en un schema que ya tiene objetos/tablas creados.
 *  Entonces se le puede indicar con el método: baselineOnMigrate(true) que ya tiene objetos
 *  Si ya tiene objetos, el primer script que se ejecute con Flyway debe estar nombrado como V2__mi_script.sql en lugar de V1__mi_script_sql
 *
 */
public class FlywayHibernateIntegrator implements Integrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlywayHibernateIntegrator.class);
    
    private static final String SCHEMA_NAME = "mibecaparaempezar";

    private javax.sql.DataSource dataSource;

	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
		try {
            Context ctx = new InitialContext();
            dataSource = (javax.sql.DataSource) ctx.lookup("java:jboss/datasources/miBecaParaEmpezarDS");
		} catch (NamingException e) {
	        LOGGER.error("No se encontró el Datasource con el nombre proporcionado: ", e);
	    }
        if (dataSource == null) {
        	LOGGER.error("FLYWAY - No se encontró un datasource para ejecutar las migraciones de BD con Flyway!");
        	throw new EJBException("FLYWAY - No se encontró un datasource para ejecutar las migraciones de BD con Flyway!");
        }

		try {
			LOGGER.info("FLYWAY - DATASOURCE INFO URL:" + dataSource.getConnection().getMetaData().getURL());
		} catch (SQLException e) {
			LOGGER.error("FLYWAY - No se pudo leer la metadata del datasource para ejecutar las migraciones de BD con Flyway!");
            throw new EJBException("FLYWAY - No se pudo leer la metadata del datasource para ejecutar las migraciones de BD con Flyway!");
		}
        
        Flyway flyway = Flyway.configure()
        		.dataSource(dataSource) //Se le indica el datasource que va a usar para establecer la conexión a la BD
        		.schemas(SCHEMA_NAME) //Se le indica el schema donde flyway creará su tabla de metadata para llevar el control de versiones
        		.baselineOnMigrate(true) //Se indica si el esquema está vacío (false) cuando se ejecute por primera vez flyway o si ya tiene objetos el schema de BD (true) como por ejemplo la BD se creó antes de que se llevará un control de versiones. Nota: Si ya existe (true) la primer migración debe llamarse V2__
        		.load(); // Carga la configuración 
        MigrationInfo migrationInfo = flyway.info().current();

        if (migrationInfo == null) {
            LOGGER.info("FLYWAY - No existe información de migraciones previas en el BD de la aplicación");
        }
        else {
            LOGGER.info("FLYWAY - Existen migraciones previas en la BD de la aplicación. Versión actual: {}", migrationInfo.getVersion() + " : " + migrationInfo.getDescription());
        }

        flyway.migrate();
        LOGGER.info("FLYWAY - Se ejecutó correctamente la migración de base de datos. Número de versión actual: {}", flyway.info().current().getVersion());
		
	}

	@Override
	public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
		// TODO Auto-generated method stub
	}
    
}