package beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BeanConnect {

    private static DataSource ds;

    public BeanConnect() {
        try {
            InitialContext context = new InitialContext();
            ds = (DataSource) context.lookup("Librairie/Resource");
        } catch (NamingException ex) {
            Logger.getLogger(BeanConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getInstance() {
        if (ds == null) {
            try {
                InitialContext context = new InitialContext();
                ds = (DataSource) context.lookup("Librairie/Resource");
            } catch (NamingException ex) {
                Logger.getLogger(BeanConnect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Connection connexion = null;
        
        try {
            System.out.println(ds.toString());
            connexion = ds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BeanConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connexion;
    }
}
