package sv.edu.itca.servicedesk360.persistence;

/** Estructura temporal de lectura (Guía 5, Paso 11). No confundir con un DAO. */
public class ClienteRegistro {

    private final long idCliente;
    private final String nombre;
    private final String correo;
    private final boolean activo;

    public ClienteRegistro(long idCliente, String nombre, String correo, boolean activo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
        this.activo = activo;
    }

    public long getIdCliente() { return idCliente; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public boolean isActivo() { return activo; }

    @Override
    public String toString() {
        return "ClienteRegistro{id=" + idCliente + ", nombre='" + nombre
                + "', correo='" + correo + "', activo=" + activo + '}';
    }
}
