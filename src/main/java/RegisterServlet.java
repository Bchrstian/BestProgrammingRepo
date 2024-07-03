import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public RegisterServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        if (saveUser(id, firstName, lastName, password)) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<script type='text/javascript'>");
            out.println("alert('Registration successful. Welcome!');");
            out.println("window.location.href='index.html';");
            out.println("</script>");
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<script type='text/javascript'>");
            out.println("alert('Registration failed. Please try again.');");
            out.println("window.history.back();");
            out.println("</script>");
        }
    }

    private boolean saveUser(String id, String firstName, String lastName, String password) {
        String url = "jdbc:postgresql://localhost:5432/student_portal";
        String user = "postgres";
        String dbPassword = "A$aprocky08";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String sql = "INSERT INTO users (id, firstName, lastName, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, password);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
