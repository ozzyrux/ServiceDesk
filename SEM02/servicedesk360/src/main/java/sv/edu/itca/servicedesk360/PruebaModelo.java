package sv.edu.itca.servicedesk360;

import sv.edu.itca.servicedesk360.model.EstadoTicket;
import sv.edu.itca.servicedesk360.model.PrioridadTicket;
import sv.edu.itca.servicedesk360.model.Solicitante;
import sv.edu.itca.servicedesk360.model.Tecnico;
import sv.edu.itca.servicedesk360.model.TicketSoporte;

/**
 * Prueba de escritorio del modelo (Guía 3, Paso 17). No requiere Tomcat
 * ni MySQL: se ejecuta directamente con java o "Run File" en NetBeans.
 */
public class PruebaModelo {

    public static void main(String[] args) {
        Solicitante solicitante = new Solicitante(
                1, "Ana López", "ana@empresa.com", "Innovación S.A.", "7000-0000");
        Tecnico tecnico = new Tecnico(
                2, "Carlos Pérez", "carlos@soporte.com", "Infraestructura", 3);

        TicketSoporte ticket = new TicketSoporte(
                1, "Servidor sin respuesta",
                "El servicio principal no responde.",
                solicitante, PrioridadTicket.CRITICA);

        ticket.asignarTecnico(tecnico);
        ticket.cambiarEstado(EstadoTicket.EN_PROCESO);

        System.out.println("Título: " + ticket.getTitulo());
        System.out.println("Estado: " + ticket.getEstado());
        System.out.println("Técnico asignado: " + ticket.getTecnicoAsignado().getNombreCompleto());

        // Prueba negativa: un técnico de nivel 1 no puede atender un ticket CRITICA
        Tecnico tecnicoJunior = new Tecnico(3, "Luis Ramos", "luis@soporte.com", "Redes", 1);
        TicketSoporte ticketCritico = new TicketSoporte(
                2, "Caída total del enlace", "El enlace principal está caído.",
                solicitante, PrioridadTicket.CRITICA);
        try {
            ticketCritico.asignarTecnico(tecnicoJunior);
            System.out.println("ERROR: no debió permitir la asignación.");
        } catch (IllegalStateException ex) {
            System.out.println("OK, excepción esperada: " + ex.getMessage());
        }
    }
}
