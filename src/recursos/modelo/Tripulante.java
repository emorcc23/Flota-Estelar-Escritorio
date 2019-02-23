package recursos.modelo;

import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import recursos.dao.TripulanteDAO;

public class Tripulante {

    private int id_tripulante;
    private String nombre;
    private String cargo;
    private String genero;
    private int experiencia;
    private String origen;
    private String raza;
    private int edad;
    private int id_nave;

    public Tripulante() {
    }

    public Tripulante(int id_tripulante) {
        this.id_tripulante = id_tripulante;
    }

    public Tripulante(String nombre, String cargo, String genero, int experiencia, String origen, String raza, int edad, int id_nave) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.genero = genero;
        this.experiencia = experiencia;
        this.origen = origen;
        this.raza = raza;
        this.edad = edad;
        this.id_nave = id_nave;
    }

    public Tripulante(int id_tripulante, String nombre, String cargo, String genero, int experiencia, String origen, String raza, int edad, int id_nave) {
        this.id_tripulante = id_tripulante;
        this.nombre = nombre;
        this.cargo = cargo;
        this.genero = genero;
        this.experiencia = experiencia;
        this.origen = origen;
        this.raza = raza;
        this.edad = edad;
        this.id_nave = id_nave;
    }

    public int getId_tripulante() {
        return id_tripulante;
    }

    public void setId_tripulante(int id_tripulante) {
        this.id_tripulante = id_tripulante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getId_nave() {
        return id_nave;
    }

    public void setId_nave(int id_nave) {
        this.id_nave = id_nave;
    }

    public void registrarTripulante(JFrame menu, JTextField txtNombre, JTextField txtCargo, JTextField txtGenero, JTextField txtExperiencia, JTextField txtOrigen, JTextField txtRaza, JTextField txtEdad, JComboBox comboNave) {
        String nombre = txtNombre.getText();
        String cargo = txtCargo.getText();
        String genero = txtGenero.getText();
        int experiencia = Integer.parseInt(txtExperiencia.getText());
        String origen = txtOrigen.getText();
        String raza = txtRaza.getText();
        int edad = Integer.parseInt(txtEdad.getText());
        int nave = Integer.parseInt((String) comboNave.getSelectedItem());

        Tripulante tripulante = new Tripulante(
                nombre,
                cargo,
                genero,
                experiencia,
                origen,
                raza,
                edad,
                nave
        );
        System.out.println("Tripulante id de la nave seleccionada: " + tripulante.getId_nave());
        try {
            TripulanteDAO.getInstance().insertar(tripulante);
            System.out.println("Tripulante registrado con éxito");
            JOptionPane.showMessageDialog(menu, "La nave " + nombre + " se ha registrado con éxito");
        } catch (Exception ex) {
            System.out.println("No se ha podido insertar. \t Error: " + ex);
            JOptionPane.showMessageDialog(menu, "No se ha podido registrar correctamente la nave. Revise los datos");
        }

    }

    public void eliminarTripulante(int id_tripulante) {
        try {
            TripulanteDAO.getInstance().eliminar(id_tripulante);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void actualizar(JPanel panel, JTextField txtIdTripulanteSeleccionado, JTextField txtNombreTripulanteSeleccionado, JTextField txtCargoTripulanteSeleccionado, JTextField txtGeneroTripulanteSeleccionado, JTextField txtExperienciaTripulanteSeleccionado, JTextField txtOrigenTripulanteSeleccionado, JTextField txtRazaTripulanteSeleccionado, JTextField txtEdadTripulanteSeleccionado, JComboBox comboIdNaveTripulanteSeleccionado) {
        int id = Integer.parseInt(txtIdTripulanteSeleccionado.getText());
        String nombre = txtNombreTripulanteSeleccionado.getText();
        String cargo = txtCargoTripulanteSeleccionado.getText();
        String genero = txtGeneroTripulanteSeleccionado.getText();
        int experiencia = Integer.parseInt(txtExperienciaTripulanteSeleccionado.getText());
        String origen = txtOrigenTripulanteSeleccionado.getText();
        String raza = txtRazaTripulanteSeleccionado.getText();
        int edad = Integer.parseInt(txtEdadTripulanteSeleccionado.getText());
        int nave = Integer.parseInt((String) comboIdNaveTripulanteSeleccionado.getSelectedItem());
       
        try {
            Tripulante tripulante = new Tripulante(id,nombre,cargo,genero,experiencia,origen,raza,edad,nave);
            TripulanteDAO.getInstance().actualizar(tripulante);
            JOptionPane.showMessageDialog(panel, "El tripulante se ha actualizado correctamente");
        } catch (SQLException ex) {
            System.out.println("Error: "+ex);
        }
    }

}
