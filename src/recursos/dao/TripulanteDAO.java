package recursos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import recursos.conexion.DBConexion;
import recursos.modelo.Tripulante;

public class TripulanteDAO {

	private Connection con = null;
	private static TripulanteDAO instance = null;

	private TripulanteDAO() throws SQLException {
		con = DBConexion.getConnection();
	}

	public static TripulanteDAO getInstance() throws SQLException {
		if (instance == null) {
			instance = new TripulanteDAO();
		}
		return instance;
	}

	public void insertar(Tripulante e) throws SQLException {
		try {
			String query = "INSERT INTO tripulante (id_tripulante,nombre,cargo,genero,experiencia,origen,raza,edad,id_nave) VALUES (?,?,?,?,?,?,?,?,?);";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, e.getId_tripulante());
			ps.setString(2, e.getNombre());
			ps.setString(3, e.getCargo());
			ps.setString(4, e.getGenero());
			ps.setInt(5, e.getExperiencia());
			ps.setString(6, e.getOrigen());
			ps.setString(7, e.getRaza());
			ps.setInt(8, e.getEdad());
			ps.setInt(9, e.getId_nave());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("TripulanteDAO --> Error: " + ex);
		}
	}

	public ArrayList<Tripulante> listar(DefaultTableModel modelo) throws SQLException {
		ArrayList<Tripulante> resultado = new ArrayList<>();
                modelo.setRowCount(0);
		try {
			String query = "SELECT * FROM tripulante;";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			while (rs.next()) {
				Object[] fila = new Object[9];
				for (int i = 0; i < 9; i++) {
					fila[i] = rs.getObject(i + 1);
				}
				modelo.addRow(fila);
			}
			ResultSetMetaData metaDatos = rs.getMetaData();
			int numeroColumnas = metaDatos.getColumnCount();
			Object[] etiquetas = new Object[numeroColumnas];
			for (int i = 0; i < numeroColumnas; i++) {
				etiquetas[i] = metaDatos.getColumnLabel(i + 1);
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("TripulanteDAO --> Error: " + ex);
		}
		return resultado;
	}

	public Tripulante listarPorPk(int id) {
		Tripulante resultado = new Tripulante();
		try {
			String query = "SELECT * FROM tripulante WHERE id_tripulante = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			if (rs.next()) {
				resultado = new Tripulante(rs.getInt("id_tripulante"), rs.getString("nombre"), rs.getString("cargo"), rs.getString("genero"), rs.getInt("experiencia"), rs.getString("origen"), rs.getString("raza"), rs.getInt("edad"), rs.getInt("id_nave"));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("TripulanteDAO --> Error: " + ex);
		}
		return resultado;
	}

	public void eliminar(Tripulante e) {
		try {
			eliminar(e.getId_tripulante());
		} catch (SQLException ex) {
			System.out.println("TripulanteDAO --> Error: " + ex);
		}
	}

	public void eliminar(int id) throws SQLException {
		try {
			if (id <= 0) {
				return;
			}
			String query = "DELETE FROM tripulante WHERE id_tripulante = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("TripulanteDAO --> Error: " + ex);
		}
	}

	public void actualizar(Tripulante e) {
		try {
			if (e.getId_tripulante() == 0) {
				return;
			}
			String query = "UPDATE tripulante SET id_tripulante=?,nombre=?,cargo=?,genero=?,experiencia=?,origen=?,raza=?,edad=?,id_nave=? WHERE id_tripulante=?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, e.getId_tripulante());
			ps.setString(2, e.getNombre());
			ps.setString(3, e.getCargo());
			ps.setString(4, e.getGenero());
			ps.setInt(5, e.getExperiencia());
			ps.setString(6, e.getOrigen());
			ps.setString(7, e.getRaza());
			ps.setInt(8, e.getEdad());
			ps.setInt(9, e.getId_nave());
			ps.setInt(10, e.getId_tripulante());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("TripulanteDAO --> Error: " + ex);
		}
	}

	public int extraerUltimoId() throws SQLException {
		int ultimoId = 0;
		try {
			String query = "SELECT id_tripulante FROM tripulante ORDER BY id_tripulante DESC LIMIT 1;";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ultimoId = rs.getInt("id_tripulante") + 1;
			}
		} catch (SQLException ex) {
			ultimoId = 0;
			System.out.println("TripulanteDAO --> Error: " + ex);
		}
		return ultimoId;
	}

	public Tripulante buscarPorPk(int id_tripulante) throws SQLException {
		Tripulante resultado = new Tripulante();
		try {
			String query = "SELECT * FROM tripulante WHERE id_tripulante = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id_tripulante);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			if (rs.next()) {
				resultado = new Tripulante(rs.getInt("id_tripulante"), rs.getString("nombre"), rs.getString("cargo"), rs.getString("genero"), rs.getInt("experiencia"), rs.getString("origen"), rs.getString("raza"), rs.getInt("edad"), rs.getInt("id_nave"));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("TripulanteDAO --> Error: " + ex);
		}

		return resultado;
	}
}
