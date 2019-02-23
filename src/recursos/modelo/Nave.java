package recursos.modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import recursos.dao.NaveDAO;

public class Nave {

    private int id_nave;
    private String capitan;
    private String nombre;
    private String matricula;
    private String tipo;

    public Nave() {
    }

    public Nave(int id_nave) {
        this.id_nave = id_nave;
    }

    public Nave(String capitan, String nombre, String matricula, String tipo) {
        this.capitan = capitan;
        this.nombre = nombre;
        this.matricula = matricula;
        this.tipo = tipo;
    }

    public Nave(int id_nave, String capitan, String nombre, String matricula, String tipo) {
        this.id_nave = id_nave;
        this.capitan = capitan;
        this.nombre = nombre;
        this.matricula = matricula;
        this.tipo = tipo;
    }

    public int getId_nave() {
        return id_nave;
    }

    public void setId_nave(int id_nave) {
        this.id_nave = id_nave;
    }

    public String getCapitan() {
        return capitan;
    }

    public void setCapitan(String capitan) {
        this.capitan = capitan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void eliminarNave(int id_nave) {
        try {
            NaveDAO.getInstance().eliminar(id_nave);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void registrarNave(JPanel panelRegistrarNave, JTextField txtCapitan, JTextField txtNombre, JTextField txtMatricula, JTextField txtTipo) {
        String capitan = txtCapitan.getText();
        String nombre = txtNombre.getText();
        String matricula = txtMatricula.getText();
        String tipo = txtTipo.getText();

        try {
            Nave nave = new Nave(
                    capitan,
                    nombre,
                    matricula,
                    tipo
            );

            NaveDAO.getInstance().insertar(nave);
            System.out.println("Nave inserada");
            JOptionPane.showMessageDialog(panelRegistrarNave, "La nave " + nombre + " se ha registrado con éxito");
            panelRegistrarNave.setVisible(false);

        } catch (Exception ex) {
            System.out.println("No se ha podido insertar la nave. \t Error: " + ex);
        }

    }

    public void rellenarCombo(JComboBox combo) {
        try {
            ArrayList<Nave> listaNaves = NaveDAO.getInstance().extraerIdNaves();
            for (int i = 0; i < listaNaves.size(); i++) {
                combo.addItem(Integer.toString(listaNaves.get(i).getId_nave()));
            }
        } catch (Exception ex) {
            System.out.println("Error al rellenar el JComboBox con los nombres de las naves. \t Error: " + ex);
        }
    }

    public void actualizar(JPanel panel, JTextField txtIdSeleccionado, JTextField txtCapitanSeleccionado, JTextField txtNombreSeleccionado, JTextField txtMatriculaSeleccionado, JTextField txtTipoSeleccionado) {
        int id = Integer.parseInt(txtIdSeleccionado.getText());
        String capitan = txtCapitanSeleccionado.getText();
        String nombre = txtNombreSeleccionado.getText();
        String matricula = txtMatriculaSeleccionado.getText();
        String tipo = txtTipoSeleccionado.getText();

        try {
            Nave nave = new Nave(id,capitan,nombre,matricula,tipo);
            NaveDAO.getInstance().actualizar(nave);
            JOptionPane.showMessageDialog(panel, "La nave se ha actualizado correctamente");
        } catch (SQLException ex) {
            System.out.println("No se ha podido actualizar en el catch de nave");
        }
    }

}
