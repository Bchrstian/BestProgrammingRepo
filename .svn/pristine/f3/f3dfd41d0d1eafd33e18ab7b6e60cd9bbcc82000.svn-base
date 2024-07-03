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

@WebServlet("/RetrieveDataServlet")
public class RetrieveDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/bestservlet";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "A$aprocky08";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            String sql = "SELECT id, firstname, lastname FROM users";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            out.println("<html><body>");
            out.println("<h1>All Users</h1>");
            out.println("<table border='1'><tr><th>ID</th><th>First Name</th><th>Last Name</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getString("id") + "</td><td>" + rs.getString("firstname") + "</td><td>" + rs.getString("lastname") + "</td></tr>");
            }
            out.println("</table>");
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
