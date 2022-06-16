package co.empresa.odontologia.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {
	

	private Integer id;
	
	private String cedula;
	
	private String username;
	
	private Integer doc_id;
	
	private String email;
	
	private String telefono;

	
	public Paciente(String cedula, String username, String email, String telefono) {
		
		this.cedula = cedula;
		this.username = username;
		this.email = email;
		this.telefono = telefono;
	}
	
	public Paciente(String cedula, String username, String email, String telefono, Integer doc_id) {
		
		this.cedula = cedula;
		this.username = username;
		this.email = email;
		this.telefono = telefono;
		this.doc_id = doc_id;
	}
	
	
}
