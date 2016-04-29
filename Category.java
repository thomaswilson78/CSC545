package csc545project;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

public class Category {
    Connection conn = null;
    OraclePreparedStatement pst = null;
    OracleResultSet rs = null;
    public boolean addCategory(String cat) {
        conn = ConnectDB.setupConnnection();
        try {
            String sql = "insert into category values (?)";
            pst = (OraclePreparedStatement) conn.prepareStatement(sql);
            pst.setString(1, cat);
            pst.executeQuery();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
            return false;
        }
        finally {
            ConnectDB.close(conn);
            ConnectDB.close(pst);
            ConnectDB.close(rs);
        }
        return true;
    }
}
