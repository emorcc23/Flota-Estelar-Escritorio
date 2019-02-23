package recursos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import recursos.conexion.DBConexion;
import recursos.modelo.Mision;

public class MisionDAO {

	private Connection con = null;
	private static MisionDAO instance = null;

	private MisionDAO() throws SQLException {
		con = DBConexion.getConnection();
	}

	public static MisionDAO getInstance() throws SQLException {
		if (instance == null) {
			instance = new MisionDAO();
		}
		return instance;
	}

	public void insertar(Mision e) throws SQLException {
		try {
			String query = "INSERT INTO mision(nombre,descripcion,id_nave) VALUES (?,?,?);";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, e.getNombre());
			ps.setString(2, e.getDescripcion());
			ps.setInt(3, e.getId_nave());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("MisionDAO --> Error: "+ex);
		}
	}

	public ArrayList<Mision> listar(DefaultTableModel modelo) {
		ArrayList<Mision> resultado = new ArrayList<>();
                modelo.setRowCount(0);
		try {
			String query = "SELECT * FROM mision";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			while (rs.next()) {
				Object[] fila = new Object[4]; 
				for (int i = 0; i < 4; i++) {
					fila[i] = rs.getObject(i + 1); 
				}
 				modelo.addRow(fila);
				ResultSetMetaData metaDatos = rs.getMetaData();
				int numeroColumnas = metaDatos.getColumnCount();
				Object[] etiquetas = new Object[numeroColumnas];
				for (int i = 0; i < numeroColumnas; i++) {
					etiquetas[i] = metaDatos.getColumnLabel(i + 1);
				}
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			resultado = null;
			System.out.println("MisionDAO --> Error: "+ex);
		}
		return resultado;
	}

	public Mision listarPorPk(int id) {
		Mision resultado = new Mision();
		try {
			String query = "SELECT * FROM mision WHERE id_mision = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			if (rs.next()) {
				resultado = new Mision(rs.getInt("id_mision"), rs.getString("nombre"), rs.getString("descripcion"), rs.getInt("id_nave"));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			resultado = null;
			System.out.println("MisionDAO --> Error: "+ex);
		}
		return resultado;
	}

	public void eliminar(Mision e) {
		try {
			eliminar(e.getId_mision());
		} catch (Exception ex) {
			System.out.println("MisionDAO --> Error: "+ex);
		}
	}

	public void eliminar(int id) {
		try {
			if (id <= 0) {
				return;
			}
			String query = "DELETE FROM mision WHERE id_mision = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("MisionDAO --> Error: "+ex);
		}

	}

	public void actualizar(Mision e) {
		try {
			if (e.getId_mision() == 0) {
				return;
			}
			String query = "UPDATE mision SET id_mision=?,nombre=?,descripcion=?,id_nave=? WHERE id_mision=?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, e.getId_mision());
			ps.setString(2, e.getNombre());
			ps.setString(3, e.getDescripcion());
			ps.setInt(4, e.getId_nave());
			ps.setInt(5, e.getId_mision());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("MisionDAO --> Error: "+ex);
		}
	}

	public Mision buscarPorPk(int id_mision) throws SQLException {
		Mision resultado = new Mision();
		try {
			String query = "SELECT * FROM mision WHERE id_mision = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id_mision);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			if (rs.next()) {
				resultado = new Mision(rs.getInt("id_mision"), rs.getString("nombre"), rs.getString("descripcion"), rs.getInt("id_nave"));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("MisionDAO --> Error: "+ex);
		}
		return resultado;
	}

}
