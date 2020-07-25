package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import domain.User;
import dto.UserLogin;
import service.UserService;
import service.impl.UserServiceImpl;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = UserServiceImpl.getUserService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = userService.getUserByEmail(email);

		if (user != null && user.getPassword().equals(password)) {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("Success");

//			UserLogin userLogin = new UserLogin();
//			userLogin.destionationUrl = "cabinet.jsp";
//			userLogin.userEmail = user.getEmail();
//			String json = new Gson().toJson(userLogin);
//			response.setContentType("application/json");
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().write(json);

		}
	}
}
