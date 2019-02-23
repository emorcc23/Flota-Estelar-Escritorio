package recursos.modelo;

import java.awt.HeadlessException;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import recursos.dao.MisionDAO;

public class Mision {

    private int id_mision;
    private String nombre;
    private String descripcion;
    private int id_nave;

    public Mision() {
    }

    public Mision(int id_mision) {
        this.id_mision = id_mision;
    }

    public Mision(String nombre, String descripcion, int id_nave) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_nave = id_nave;
    }

    public Mision(int id_mision, String nombre, String descripcion, int id_nave) {
        this.id_mision = id_mision;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_nave = id_nave;
    }

    public int getId_mision() {
        return id_mision;
    }

    public void setId_mision(int id_mision) {
        this.id_mision = id_mision;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId_nave() {
        return id_nave;
    }

    public void setId_nave(int id_nave) {
        this.id_nave = id_nave;
    }

    public void registrarMision(JFrame index, JTextField txtNombre, JTextArea txtDescripcion, JComboBox comboMision) {

        String nombre = txtNombre.getText();
        String descripcion = txtDescripcion.getText();
        int nave = Integer.parseInt((String) comboMision.getSelectedItem());
        Mision mision = new Mision(
                nombre,
                descripcion,
                nave
        );
        try {
            MisionDAO.getInstance().insertar(mision);
            System.out.println("Mision registrada con éxito");
            JOptionPane.showMessageDialog(index, "La misión " + mision.getNombre() + " se ha registrado con éxito");
        } catch (HeadlessException | SQLException ex) {
            System.out.println("No se ha podido insertar. \t Error: " + ex);
            JOptionPane.showMessageDialog(index, "No se ha podido registrar correctamente. Revise los datos.");
        }
    }

    public void eliminarMision(int id_mision) {
        try {
            MisionDAO.getInstance().eliminar(id_mision);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void actualizar(JPanel panel, JTextField txtIdMisionSeleccionada, JTextField txtNombreMisionSeleccionada, JTextArea txtDescripcionMisionSeleccionada, JComboBox comboNaveMisionSeleccionada) {
        int id = Integer.parseInt(txtIdMisionSeleccionada.getText());
        String nombre = txtNombreMisionSeleccionada.getText();
        String descripcion = txtDescripcionMisionSeleccionada.getText();
        int nave = Integer.parseInt((String) comboNaveMisionSeleccionada.getSelectedItem());

        try {
            Mision mision = new Mision(id,nombre,descripcion,nave);
            MisionDAO.getInstance().actualizar(mision);
            JOptionPane.showMessageDialog(panel, "La misión se ha actualizado correctamente");
        } catch (SQLException ex) {
            System.out.println("No se ha podido actualizar en el catch de mision");
        }
    }

}
