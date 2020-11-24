package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLUsuarios extends Conexion {

    public boolean registrar(usuarios usr) {

        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "INSERT INTO usuarios (usuario, password, nombre, correo) VALUES(?,?,?,?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            ps.setString(2, usr.getPassword());
            ps.setString(3, usr.getNombre());
            ps.setString(4, usr.getCorreo());
            //ps.setInt(5, usr.getId_tipo()); //quitar
            ps.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(SQLUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean login(usuarios usr) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT id, usuario, password, nombre FROM usuarios WHERE usuario = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getUsuario());
            rs = ps.executeQuery();

            if (rs.next()) {

                if (usr.getPassword().equals(rs.getString(3))) {

                    String sqlUpdate = "UPDATE usuarios SET last_session = ? WHERE id = ?";

                    ps = con.prepareStatement(sqlUpdate);
                    ps.setString(1, usr.getLast_session());
                    ps.setInt(2, rs.getInt(1));
                    ps.execute();

                    usr.setId(rs.getInt(1));
                    usr.setNombre(rs.getString(4));
                    return true;
                } else {
                    return false;
                }
            }

            return false;

        } catch (SQLException ex) {
            Logger.getLogger(SQLUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int existeUsuario(String usuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT count(id) FROM usuarios WHERE usuario = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 1;

        } catch (SQLException ex) {
            Logger.getLogger(SQLUsuarios.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }

    public boolean esEmail(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correo);
        return mather.find();
    }

    public usuarios buscarUsuario(String usuario) {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection con = getConexion();
        usuarios usrP = new usuarios();

        String sql = "SELECT * FROM paradox.usuarios WHERE usuario=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                usrP.setId(rs.getInt(1));
                usrP.setUsuario(rs.getString(2));
                usrP.setNombre(rs.getString(4));
                usrP.setCorreo(rs.getString(5));
                return usrP;
            }
            
        } catch (SQLException e) {
            System.err.println(e);
            return usrP;
           
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
                return usrP;
            }
        }
        return usrP;
        
    }
    public String buscandoCorreo (String usuario) {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection con = getConexion();
        String correo = "";

        String sql = "SELECT * FROM paradox.usuarios WHERE usuario=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            rs = ps.executeQuery();
            if (rs.next()) {
                correo = rs.getString(5);
            }
            
        } catch (SQLException e) {
            System.err.println(e);
           
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return correo;
    }
}
