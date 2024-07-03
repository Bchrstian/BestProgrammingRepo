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

@WebServlet("/TestingServlet")
public class TestingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/bestservlet";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "A$aprocky08";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            String sql = "INSERT INTO users (id, firstname, lastname, password) VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, password);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                // Redirect to the welcome page with the user information
                request.setAttribute("id", id);
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.getRequestDispatcher("welcome.jsp").forward(request, response);
            } else {
                PrintWriter out = response.getWriter();
                out.println("Error registering user.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            PrintWriter out = response.getWriter();
            out.println("Database error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
