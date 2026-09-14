package sv.edu.itca.servicedesk360.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de laboratorio de la Guía 5 (Parte C): consultas parametrizadas
 * e inserción con clave generada. Será encapsulada en un DAO en la Guía 6.
 */
public class ServicioClientesJdbc {

    public ClienteRegistro buscarClientePorCorreo(String correo) throws SQLException {
        String sql = "SELECT id_cliente, nombre, correo, activo "
                + "FROM clientes WHERE correo = ?";
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return new ClienteRegistro(
                        rs.getLong("id_cliente"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getBoolean("activo"));
            }
        }
    }

    public long insertarCliente(String nombre, String correo) throws SQLException {
        String sql = "INSERT INTO clientes(nombre, correo) VALUES (?, ?)";
        try (Connection cn = ConexionBD.abrir();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) {
                    throw new SQLException("MySQL no devolvió la clave generada.");
                }
                return keys.getLong(1);
            }
        }
    }

    /** Prueba manual (Guía 5, Paso 13): una entrada especial se trata como dato, no como SQL. */
    public static void main(String[] args) throws SQLException {
        ServicioClientesJdbc servicio = new ServicioClientesJdbc();

        long id = servicio.insertarCliente("Cliente de prueba", "prueba@demo.local");
        System.out.println("Cliente insertado con id = " + id);

        ClienteRegistro encontrado = servicio.buscarClientePorCorreo("prueba@demo.local");
        System.out.println("Encontrado: " + encontrado);

        String correoSospechoso = "' OR '1'='1";
        ClienteRegistro resultado = servicio.buscarClientePorCorreo(correoSospechoso);
        System.out.println(resultado == null
                ? "Entrada tratada como dato: sin coincidencias (correcto)"
                : "REVISAR: la entrada alteró la consulta");
    }
}
