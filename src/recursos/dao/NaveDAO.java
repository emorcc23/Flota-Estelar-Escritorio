package recursos.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import recursos.conexion.DBConexion;
import recursos.modelo.Nave;

public class NaveDAO {

	private Connection con = null;
	private static NaveDAO instance = null;

	private NaveDAO() throws SQLException {
		con = DBConexion.getConnection();
	}

	public static NaveDAO getInstance() throws SQLException {
		if (instance == null) {
			instance = new NaveDAO();
		}
		return instance;
	}

	public void insertar(Nave e) {
		try {
			String query = "INSERT INTO nave (capitan,nombre,matricula,tipo) VALUES (?,?,?,?);";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, e.getCapitan());
			ps.setString(2, e.getNombre());
			ps.setString(3, e.getMatricula());
			ps.setString(4, e.getTipo());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("NaveDAO --> Error: " + ex);
		}
	}

	public ArrayList<Nave> listar(DefaultTableModel modelo) throws SQLException {
		ArrayList<Nave> resultado = new ArrayList<>();
		modelo.setRowCount(0);
		try {
			String query = "SELECT * FROM nave";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			while (rs.next()) {
				Object[] fila = new Object[6];
				for (int i = 0; i < 5; i++) {
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
			resultado = null;
			System.out.println("NaveDAO --> Error: " + ex);
		}
		return resultado;
	}

	public Nave listarPorPk(int id) {
		Nave resultado = new Nave();
		try {
			String query = "SELECT * FROM nave WHERE id_nave = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			if (rs.next()) {
				resultado = new Nave(rs.getInt("id_nave"), rs.getString("capitan"), rs.getString("nombre"), rs.getString("matricula"), rs.getString("tipo"));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			resultado = null;
			System.out.println("NaveDAO --> Error: " + ex);
		}
		return resultado;
	}

	public void eliminar(Nave e) {
		try {
			eliminar(e.getId_nave());
		} catch (Exception ex) {
			System.out.println("NaveDAO --> Error: " + ex);
		}
	}

	public void eliminar(int id) {
		try {
			if (id <= 0) {
				return;
			}
			String query = "DELETE FROM nave WHERE id_nave = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("NaveDAO --> Error: " + ex);
		}
	}

	public void actualizar(Nave e) {
		try {
			if (e.getId_nave() == 0) {
				return;
			}
			String query = "UPDATE nave SET id_nave=?,capitan=?,nombre=?,matricula=?,tipo=? WHERE id_nave=?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, e.getId_nave());
			ps.setString(2, e.getCapitan());
			ps.setString(3, e.getNombre());
			ps.setString(4, e.getMatricula());
			ps.setString(5, e.getTipo());
			ps.setInt(6, e.getId_nave());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("NaveDAO --> Error: " + ex);
		}
	}

	public ArrayList<Nave> extraerIdNaves() throws SQLException {
		ArrayList<Nave> resultado = new ArrayList<>();
		try {
			String query = "SELECT id_nave FROM nave;";
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			while (rs.next()) {
				if (resultado == null) {
					resultado = new ArrayList<>();
				}
				resultado.add(new Nave(rs.getInt("id_nave")));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			resultado = null;
			System.out.println("NaveDAO --> Error: " + ex);
		}
		return resultado;
	}

	public Nave buscarPorPk(int id_nave) throws SQLException {
		Nave resultado = new Nave();
		try {
			String query = "SELECT * FROM nave WHRE id_nave = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id_nave);
			ResultSet rs = ps.executeQuery();
			resultado = null;
			if (rs.next()) {
				resultado = new Nave(rs.getInt("id_nave"), rs.getString("capitan"), rs.getString("nombre"), rs.getString("matricula"), rs.getString("tipo"));
			}
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			System.out.println("NaveDAO --> Error: "+ex);
		}
		return resultado;
	}

}
