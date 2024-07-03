import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RetrieveByIdServlet")
public class RetrieveByIdServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/bestservlet";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "A$aprocky08";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            String sql = "SELECT id, firstname, lastname FROM users WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            out.println("<html><body>");
            if (rs.next()) {
                out.println("<h1>User Details</h1>");
                out.println("<p>ID: " + rs.getString("id") + "</p>");
                out.println("<p>First Name: " + rs.getString("firstname") + "</p>");
                out.println("<p>Last Name: " + rs.getString("lastname") + "</p>");
            } else {
                out.println("<p>User not found.</p>");
            }
            out.println("</body></html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("Database error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
