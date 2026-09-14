package sv.edu.itca.servicedesk360.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Guía 5, Parte D: transacción ticket + seguimiento con commit/rollback,
 * y Parte E: listado JDBC de validación con JOIN.
 */
public class ServicioTicketsJdbc {

    public long registrarTicketConSeguimiento(
            long idCliente, Long idEquipo, Long idTecnico, long idCategoria,
            String titulo, String descripcion, String prioridad,
            String detalleInicial) throws SQLException {

        String sqlTicket = "INSERT INTO tickets "
                + "(id_cliente,id_equipo,id_tecnico,id_categoria,titulo,descripcion,prioridad) "
                + "VALUES (?,?,?,?,?,?,?)";
        String sqlSeg = "INSERT INTO seguimientos(id_ticket, detalle) VALUES (?,?)";

        try (Connection cn = ConexionBD.abrir()) {
            cn.setAutoCommit(false);
            try {
                long idTicket;
                try (PreparedStatement ps = cn.prepareStatement(
                        sqlTicket, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, idCliente);
                    if (idEquipo == null) ps.setNull(2, Types.BIGINT); else ps.setLong(2, idEquipo);
                    if (idTecnico == null) ps.setNull(3, Types.BIGINT); else ps.setLong(3, idTecnico);
                    ps.setLong(4, idCategoria);
                    ps.setString(5, titulo);
                    ps.setString(6, descripcion);
                    ps.setString(7, prioridad);
                    ps.executeUpdate();
                    try (ResultSet k = ps.getGeneratedKeys()) {
                        if (!k.next()) throw new SQLException("Sin id_ticket generado");
                        idTicket = k.getLong(1);
                    }
                }
                try (PreparedStatement ps = cn.prepareStatement(sqlSeg)) {
                    ps.setLong(1, idTicket);
                    ps.setString(2, detalleInicial);
                    ps.executeUpdate();
                }
                cn.commit();
                return idTicket;
            } catch (SQLException ex) {
                cn.rollback();
                throw ex;
            } finally {
                cn.setAutoCommit(true);
            }
        }
    }

    public List<TicketVista> listarTickets() throws SQLException {
        String sql = "SELECT t.id_ticket, t.titulo, t.prioridad, t.estado, "
                + "t.fecha_creacion, c.nombre AS cliente, cat.nombre AS categoria "
                + "FROM tickets t "
                + "JOIN clientes c ON c.id_cliente = t.id_cliente "
                + "JOIN categorias cat ON cat.id_categoria = t.id_categoria "
                + "ORDER BY t.fecha_creacion DESC";

        List<TicketVista> salida = new ArrayList<>();
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                salida.add(new TicketVista(
                        rs.getLong("id_ticket"),
                        rs.getString("titulo"),
                        rs.getString("prioridad"),
                        rs.getString("estado"),
                        rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                        rs.getString("cliente"),
                        rs.getString("categoria")));
            }
        }
        return salida;
    }

    /** Prueba manual (Guía 5, Pasos 15-16): éxito y luego un fallo forzado con rollback. */
    public static void main(String[] args) throws SQLException {
        ServicioTicketsJdbc servicio = new ServicioTicketsJdbc();

        long id = servicio.registrarTicketConSeguimiento(
                1L, null, 1L, 1L,
                "Error al ingresar al portal",
                "El usuario recibe un mensaje al autenticar.",
                "MEDIA",
                "Ticket registrado desde la Guía 5");
        System.out.println("Ticket confirmado: " + id);

        try {
            // id_categoria = 999999 no existe: debe fallar por FK y revertir TODO (rollback)
            servicio.registrarTicketConSeguimiento(
                    1L, null, 1L, 999999L,
                    "Prueba de rollback",
                    "Esta operación debe revertirse por completo.",
                    "BAJA",
                    "Seguimiento que nunca debe quedar guardado");
            System.out.println("ERROR: debió lanzar SQLException por FK inexistente.");
        } catch (SQLException ex) {
            System.out.println("OK, rollback ejecutado. Mensaje técnico: " + ex.getMessage());
        }

        System.out.println("--- Listado actual ---");
        for (TicketVista t : servicio.listarTickets()) {
            System.out.println(t.getIdTicket() + " | " + t.getTitulo() + " | "
                    + t.getCliente() + " | " + t.getCategoria() + " | " + t.getEstado());
        }
    }
}
