package co.empresa.odontologia.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.empresa.odontologia.modelo.Paciente;
import co.empresa.odontologia.utilidad.ConexionPostgreSQL;

public class PacienteDaoPostgreSQL implements PacienteDao {
	
private ConexionPostgreSQL conexion;
	
	private static final String INSERT_PACIENTE_SQL = "INSERT INTO paciente(username, email, pass) VALUES (?,?,?);";
	private static final String UPDATE_PACIENTE_SQL = "UPDATE paciente SET username = ?,email = ?, pass = ? WHERE id = ?;";
	private static final String DELETE_PACIENTE_SQL = "DELETE FROM paciente WHERE id = ?;";
	private static final String SELECT_PACIENTE_BY_ID = "SELECT * FROM paciente WHERE cedula = ?;";
	private static final String SELECT_ALL_PACIENTES = "SELECT * FROM paciente;";
	
	public PacienteDaoPostgreSQL() {
		this.conexion = ConexionPostgreSQL.getConexion();
	}
	
	public void insert(Paciente paciente) throws SQLException{
		try {
			PreparedStatement preparedStatement = conexion.setPreparedStatement(INSERT_PACIENTE_SQL);
			preparedStatement.setString(1, paciente.getCedula());
			preparedStatement.setString(2, paciente.getUsername());
			preparedStatement.setInt(3, paciente.getDoc_id());
			preparedStatement.setString(4, paciente.getEmail());
			preparedStatement.setString(5, paciente.getTelefono());
			conexion.execute();
		}catch (SQLException e) {
			
		}
	}
	
	
	public void update(Paciente paciente) throws SQLException{
		try {
			PreparedStatement preparedStatement = conexion.setPreparedStatement(UPDATE_PACIENTE_SQL);
			preparedStatement.setString(1, paciente.getUsername());
			preparedStatement.setString(2, paciente.getEmail());
			preparedStatement.setString(3, paciente.getTelefono());
			conexion.execute();
		}catch (SQLException e) {
			
		}
	}
	
	public void delete(int id) throws SQLException{
		try {
			PreparedStatement preparedStatement = conexion.setPreparedStatement(DELETE_PACIENTE_SQL);
			preparedStatement.setInt(1, id);
			
			conexion.execute();
		}catch (SQLException e){
		
		}
	}
	
	public List<Paciente> selectAll(){
		List <Paciente> pacientes = new ArrayList<>();
		
		try {
			PreparedStatement preparedStatement = conexion.setPreparedStatement(SELECT_ALL_PACIENTES);
			ResultSet rs = conexion.query();
			
			while(rs.next()) {
				String cedula = rs.getString("cedula");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String telefono = rs.getString("telefono");
				pacientes.add(new Paciente(cedula, username, email, telefono));
			}
			
		}catch(SQLException e) {
			
		}
		
		return pacientes;
 	}
	
	public Paciente select(String cedula){
		Paciente p = null;
		
		try {
			PreparedStatement preparedStatement = (PreparedStatement) conexion.setPreparedStatement(SELECT_PACIENTE_BY_ID);
			preparedStatement.setString(1, cedula);
			ResultSet rs = conexion.query();
			
			while(rs.next()) {
				String ced = rs.getString("cedula");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String telefono = rs.getString("telefono");
				p = new Paciente(ced,username,email,telefono);
			}
			
		}catch(SQLException e) {
			
		}
		return p;
 	}

	
}
