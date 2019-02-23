package recursos.modelo;

import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import recursos.dao.UsuarioDAO;

public class Usuario {

    private int id_usuario;
    private String nombre;
    private String usuario;
    private String pass;

    public Usuario() {
    }

    public Usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Usuario(String usuario, String pass) {
        this.usuario = usuario;
        this.pass = pass;
    }

    public Usuario(String nombre, String usuario, String pass) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.pass = pass;
    }

    public Usuario(int id_usuario, String nombre, String usuario, String pass) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.usuario = usuario;
        this.pass = pass;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void registrarUsuario(JFrame Index, JTextField txtNombreNuevo, JTextField txtUsuarioNuevo, JTextField txtPassNuevo) {
        Usuario user = new Usuario(txtNombreNuevo.getText(), txtUsuarioNuevo.getText(), txtPassNuevo.getText());
        try {
            UsuarioDAO.getInstance().insertar(user);
            JOptionPane.showMessageDialog(Index, "El usuario " + user.getNombre() + " se ha registrado con éxito");
            comprobarLogin(Index, txtUsuarioNuevo, txtPassNuevo);
        } catch (SQLException ex) {
            System.out.println("No se ha podido registrar: " + ex);
        }
    }

    public boolean comprobarLogin(JFrame index, JTextField txtUsuario, JTextField txtPass) throws SQLException {
        boolean entra = false;
        String user = txtUsuario.getText();
        String pass = txtPass.getText();
        
        entra = UsuarioDAO.getInstance().login(txtUsuario, txtPass);
        return entra;
    }
        
}
