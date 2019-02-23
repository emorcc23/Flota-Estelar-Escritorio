package recursos.modelo;

import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import recursos.dao.CuadernoDAO;

public class Cuaderno {

    private int id_cuaderno;
    private String fecha;
    private String descripcion;
    private String audio;
    private int id_nave;

    public Cuaderno() {
    }

    public Cuaderno(int id_cuaderno) {
        this.id_cuaderno = id_cuaderno;
    }

    public Cuaderno(int id_cuaderno, String fecha, String descripcion, String audio, int id_nave) {
        this.id_cuaderno = id_cuaderno;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.audio = audio;
        this.id_nave = id_nave;
    }

    public Cuaderno(String fecha, String descripcion, String audio, int id_nave) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.audio = audio;
        this.id_nave = id_nave;
    }

    public int getId_cuaderno() {
        return id_cuaderno;
    }

    public void setId_cuaderno(int id_cuaderno) {
        this.id_cuaderno = id_cuaderno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public int getId_nave() {
        return id_nave;
    }

    public void setId_nave(int id_nave) {
        this.id_nave = id_nave;
    }

    public void registrarCuaderno(JFrame index, JTextField txtFecha, JTextArea txtDescripcion, JTextField txtAudio, JComboBox comboNave) {
        String fecha = txtFecha.getText();
        String descripcion = txtDescripcion.getText();
        String audio = txtAudio.getText();
        int nave = Integer.parseInt((String) comboNave.getSelectedItem());

        Cuaderno cuaderno = new Cuaderno(fecha, descripcion, audio, nave);
        try {
            CuadernoDAO.getInstance().insertar(cuaderno);
            System.out.println("Cuaderno registrado con éxito");
            JOptionPane.showMessageDialog(index, "El cuaderno se ha registrado con éxito");
        } catch (Exception ex) {
            System.out.println("No se ha podido insertar. \t Error: " + ex);
            JOptionPane.showMessageDialog(index, "No se ha podido registrar correctamente. Revise los datos.");
        }
    }

    public void eliminarCuaderno(int id_cuaderno) {
        try {
            CuadernoDAO.getInstance().eliminar(id_cuaderno);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void actualizar(JPanel panel, JTextField txtIdCuadernoRegistrado, JTextField txtFechaCuadernoRegistrado, JTextArea txtDescripcionCuadernoSeleccionado, JTextField txtAudioCuadernoSeleccionado, JComboBox comboNaveCuadernoSeleccionada) {
        int id = Integer.parseInt(txtIdCuadernoRegistrado.getText());
        String fecha = txtFechaCuadernoRegistrado.getText();
        String descripcion = txtDescripcionCuadernoSeleccionado.getText();
        String audio = txtAudioCuadernoSeleccionado.getText();
        int nave = Integer.parseInt((String) comboNaveCuadernoSeleccionada.getSelectedItem());

        try {
            Cuaderno cuaderno = new Cuaderno(id, fecha, descripcion, audio, nave);
            CuadernoDAO.getInstance().actualizar(cuaderno);
            JOptionPane.showMessageDialog(panel, "El cuaderno se ha actualizado correctamente");
        } catch (SQLException ex) {
            System.out.println("No se ha podido actualizar en el catch de cuaderno");
        }
    }
}
