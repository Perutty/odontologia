package co.empresa.odontologia.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.empresa.odontologia.dao.PacienteDao;
import co.empresa.odontologia.dao.PacienteDaoFactory;
import co.empresa.odontologia.modelo.Paciente;

/**
 * Servlet implementation class PacienteServlet
 */
@WebServlet(name="PacienteServlet", urlPatterns={"/PacienteServlet"})
public class PacienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	private PacienteDao pacienteDao;
	
    public PacienteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void init(ServletConfig config) throws ServletException {
		this.pacienteDao = PacienteDaoFactory.getPacienteDao("postgresql");
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException {
    	
    	doGet(request, response);
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		try {
			switch(action) {
			case "newPaciente":
				showNewForm(request, response);
				break;
			case "insert":
				insertarPaciente(request,response);
			case "list":
				listPacientes(request,response);
			case "delete":
				deletePaciente(request,response);
			case "cerrar":
				cerrarSesion(request,response);
			default:
				listPacientes(request, response);
		}
	}catch(SQLException e) {
			throw new ServletException(e);
   }
}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("registrarPaciente.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertarPaciente(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, SQLException, IOException {
		
	    int oId = (int) request.getSession().getAttribute("oId");
	    
	    String cedula = request.getParameter("cedula");
	    String username = request.getParameter("username");
		String email = request.getParameter("email");
		String telefono = request.getParameter("telefono");
		
		Paciente paciente = new Paciente(cedula, username, email, telefono, oId);
		pacienteDao.insert(paciente);
		request.getSession().setAttribute("pId", paciente.getId());
		request.getRequestDispatcher("PacienteServlet?action=list").forward(request,response);
	}
	
	private void deletePaciente(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, SQLException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		pacienteDao.delete(id);
		request.getRequestDispatcher("PacienteServlet?action=list").forward(request,response);
	}
	
	private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, SQLException, IOException {
		
		request.getSession().invalidate();
		request.getRequestDispatcher("UsuarioServlet?action=logeo").forward(request,response);
	}
	
	private void listPacientes(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, SQLException, IOException{
		
		List<Paciente> listPacientes = pacienteDao.selectAll();
		List<Paciente> listMostrar = new ArrayList<>();
		int user_id = (int) request.getSession().getAttribute("oId");
		listPacientes.forEach((billes) -> {
			if(billes.getDoc_id() == user_id) {
				listMostrar.add(billes);
			}
		});
		request.getSession().setAttribute("listBills", listMostrar);
		
		request.getRequestDispatcher("dashboard.jsp").forward(request, response);
		
	}


}
