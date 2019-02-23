package recursos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import recursos.conexion.DBConexion;
import recursos.modelo.Cuaderno;

public class CuadernoDAO {

	private Connection con = null;
	private static CuadernoDAO instance = null;

	private CuadernoDAO() throws SQLException {
		con = DBConexion.getConnection();
	}

	public static CuadernoDAO getInstance() throws SQLException {
		if (instance == null) {
			instance = new CuadernoDAO();
		}
		return instance;
	}

	public void insertar(Cuaderno e) {
		try {
			String query = "INSERT INTO cuaderno (fecha,descripcion,audio,id_nave) VALUES (?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, e.getFecha());
			ps.setString(2, e.getDescripcion());
			ps.setString(3, e.getAudio());
			ps.setInt(4, e.getId_nave());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("CuadernoDAO --> Error: " + ex);
		}
	}

	public ArrayList<Cuaderno> listar(DefaultTableModel modelo) {
		ArrayList<Cuaderno> resultado = new ArrayList<>();
                modelo.setRowCount(0);
		try {
			String query = "SELECT * FROM cuaderno;";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			while (rs.next()) {
				Object[] fila = new Object[6];
				for (int i = 0; i < 5; i++) {
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
			System.out.println("CuadernoDAO --> Error: " + ex);
		}
		return resultado;
	}

	public Cuaderno listarPorPk(int id) {
		Cuaderno resultado = new Cuaderno();
		try {
			String query = "SELECT * FROM cuaderno WHERE id_cuaderno = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			if (rs.next()) {
				resultado = new Cuaderno(rs.getInt("id_nave"), rs.getString("fecha"), rs.getString("descripcion"), rs.getString("audio"), rs.getInt("id_nave"));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			resultado = null;
			System.out.println("CuadernoDAO --> Error: " + ex);
		}
		return resultado;
	}

	public void eliminar(Cuaderno e) {
		try {
			eliminar(e.getId_cuaderno());
			System.out.println("CuadernoDAO --> El cuaderno se ha eliminado correctamente");
		} catch (Exception ex) {
			System.out.println("CuadernoDAO --> Error: " + ex);
		}
	}

	public void eliminar(int id) {
		try {
			if (id <= 0) {
				return;
			}
			String query = "DELETE FROM cuaderno WHERE id_cuaderno = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("CuadernoDAO --> Error: " + ex);
		}
	}

	public void actualizar(Cuaderno e) {
		try {
			if (e.getId_cuaderno() == 0) {
				return;
			}
			String query = "UPDATE cuaderno SET id_cuaderno=?,fecha=?,descripcion=?,audio=?,id_nave=? WHERE id_cuaderno=?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, e.getId_cuaderno());
			ps.setString(2, e.getFecha());
			ps.setString(3, e.getDescripcion());
			ps.setString(4, e.getAudio());
			ps.setInt(5, e.getId_nave());
			ps.setInt(6, e.getId_cuaderno());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("CuadernoDAO --> Error: " + ex);
		}
	}

	public int extraerUltimoId() {
		int ultimoId = 0;
		try {
			String query = "SELECT id_cuaderno FROM cuaderno ORDER BY id_cuaderno DESC LIMIT 1;";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ultimoId = rs.getInt("id_cuaderno") + 1;
			}
			System.out.println("CuadernoDAO --> Se han extraido correctamente los id de cuadernos");
		} catch (SQLException ex) {
			ultimoId = 0;
			System.out.println("CuadernoDAO --> Error: " + ex);
		}
		return ultimoId;
	}

	public Cuaderno buscarPorPk(int id_cuaderno) throws SQLException {
		Cuaderno resultado = new Cuaderno();
		try {
			String query = "SELECT * FROM cuaderno WHERE id_cuaderno=?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id_cuaderno);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			if (rs.next()) {
				resultado = new Cuaderno(rs.getInt("id_cuaderno"), rs.getString("fecha"), rs.getString("descripcion"), rs.getString("audio"), rs.getInt("id_nave"));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("CuadernoDAO --> Error: " + ex);
		}
		return resultado;
	}
}
