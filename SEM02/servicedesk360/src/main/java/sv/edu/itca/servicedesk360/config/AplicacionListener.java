package sv.edu.itca.servicedesk360.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import sv.edu.itca.servicedesk360.service.Autenticador;
import sv.edu.itca.servicedesk360.service.ServicioAutenticacion;
import sv.edu.itca.servicedesk360.service.ServicioRegistro;
import sv.edu.itca.servicedesk360.service.ServicioTickets;
import sv.edu.itca.servicedesk360.service.ValidadorRegistro;
import sv.edu.itca.servicedesk360.storage.DirectorioCuentasEnMemoria;
import sv.edu.itca.servicedesk360.storage.DirectorioTicketsEnMemoria;

/**
 * Inicializa una sola vez los servicios compartidos de la aplicación
 * (Guía 3, Paso 13 + Guía 4, Paso 8) y los publica en el ServletContext
 * para que todos los Servlets los reutilicen sin crear instancias nuevas.
 */
@WebListener
public class AplicacionListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        ServletContext contexto = evento.getServletContext();

        // --- Cuentas / autenticación (Guía 3) ---
        DirectorioCuentasEnMemoria directorioCuentas = new DirectorioCuentasEnMemoria();
        ServicioRegistro servicioRegistro = new ServicioRegistro(
                directorioCuentas, directorioCuentas, new ValidadorRegistro());
        Autenticador autenticador = new ServicioAutenticacion(directorioCuentas);

        contexto.setAttribute("servicioRegistro", servicioRegistro);
        contexto.setAttribute("autenticador", autenticador);

        // --- Tickets (Guía 4) ---
        DirectorioTicketsEnMemoria directorioTickets = new DirectorioTicketsEnMemoria();
        ServicioTickets servicioTickets = new ServicioTickets(directorioTickets, directorioTickets);

        contexto.setAttribute("servicioTickets", servicioTickets);
    }

    @Override
    public void contextDestroyed(ServletContextEvent evento) {
        // No se requieren liberaciones adicionales en esta práctica.
    }
}
