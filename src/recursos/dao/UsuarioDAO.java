package recursos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import recursos.conexion.DBConexion;
import recursos.modelo.Usuario;

public class UsuarioDAO {

      private Connection con = null;
      private static UsuarioDAO instance = null;

      private UsuarioDAO() throws SQLException {
            con = DBConexion.getConnection();
      }

      public static UsuarioDAO getInstance() throws SQLException {
            if (instance == null) {
                  instance = new UsuarioDAO();
            }
            return instance;
      }

      public void insertar(Usuario e) throws SQLException {
            try {
                  PreparedStatement ps = con.prepareStatement("INSERT INTO usuario (nombre,usuario,pass) VALUES (?,?,?);");
                  ps.setString(1, e.getNombre());
                  ps.setString(2, e.getUsuario());
                  ps.setString(3, e.getPass());
                  ps.executeUpdate();
                  ps.close();
            } catch (SQLException ex) {
                  System.out.println("ERROR CONTROLADO: \n " + "\t Clase: UsuarioDAO \n" + "\t Metodo: insertar(Usuario e) \n"
                          + "\t Error: " + ex);
            }
      }
      
      public boolean login(JTextField txtUser, JTextField txtPass) {
          boolean entrar = false;
          try {
              PreparedStatement ps = con.prepareStatement("SELECT * FROM usuario WHERE usuario=? AND pass=?;");
              ps.setString(1, txtUser.getText());
              ps.setString(2, txtPass.getText());
              ResultSet rs = ps.executeQuery();
              if(rs.next()) {
                  JOptionPane.showMessageDialog(null, "¡Bienvenido, "+txtUser.getText()+"!");
                  entrar = true;
              } else {
                  JOptionPane.showMessageDialog(null, "¡Oops, los datos que has introducido son erroneos! Vuelve a intentarlo");
                  entrar = false;
              }
              rs.close();
              ps.close();
          } catch (SQLException ex) {
              System.out.println("Error: "+ex);
          }
          return entrar;
      }
}
