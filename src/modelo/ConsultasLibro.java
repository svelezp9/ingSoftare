package modelo;

import vista.frmBiblioteca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import vista.frmUsBiblioteca;
import vista.frmUsuarios;

public class ConsultasLibro extends Conexion {

    public boolean agregarLibros(Libro libros, usuarios usr) {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection con = getConexion();

        String bsql = "SELECT titulo FROM libros WHERE titulo=?";

        try {
            ps = con.prepareStatement(bsql);
            ps.setString(1, libros.getTitulo());
            rs = ps.executeQuery();

            if (rs.next()) {
                bsql = "INSERT INTO biblioteca (usuarios_id, libros_titulo) VALUES (?,?)";
                ps = con.prepareStatement(bsql);
                ps.setInt(1, usr.getId());
                ps.setString(2, libros.getTitulo());
                ps.execute();
                return true;
            }
            String sql = "INSERT INTO libros (titulo, nombreAutor, categoria) VALUES (?,?,?)";

            try {

                ps = con.prepareStatement(sql);
                ps.setString(1, libros.getTitulo());
                ps.setString(2, libros.getNombreAutor());
                ps.setString(3, libros.getCategoria());
                ps.execute();

                sql = "INSERT INTO biblioteca (usuarios_id, libros_titulo) VALUES (?,?)";

                ps = con.prepareStatement(sql);
                ps.setInt(1, usr.getId());
                ps.setString(2, libros.getTitulo());
                ps.execute();
                return true;

            } catch (SQLException e) {
                System.err.println(e);
                return false;
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
        return false;
    }

    public boolean eliminarLibros(Libro libros, usuarios usr) {
        PreparedStatement ps;
        Connection con = getConexion();

        String sql = "DELETE FROM biblioteca WHERE usuarios_id=?  AND libros_titulo=? ";

        try {

            ps = con.prepareStatement(sql);
            ps.setInt(1, usr.getId());
            ps.setString(2, libros.getTitulo());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean buscarLibros(Libro libros, usuarios usr) {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM biblioteca WHERE libros_titulo=? AND usuarios_id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, libros.getTitulo());
            ps.setInt(2, usr.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean mostrarLibros(Libro libros, frmBiblioteca biblioteca, usuarios usr) {

        try {
            DefaultTableModel model = new DefaultTableModel();
            biblioteca.tablaMostrar.setModel(model);
            PreparedStatement ps;
            ResultSet rs;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion();

            String sql = "SELECT libros.titulo, libros.nombreAutor, libros.categoria FROM biblioteca INNER JOIN libros ON biblioteca.libros_titulo = libros.titulo WHERE biblioteca.usuarios_id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, usr.getId());
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            model.addColumn("Titulo");
            model.addColumn("Nombre Autor");
            model.addColumn("Categoria");

            while (rs.next()) {

                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {

                    filas[i] = rs.getObject(i + 1);

                }
                model.addRow(filas);
            }
            return true;
        } catch (SQLException e) {
            System.err.print(e);
            return false;
        }
    }

    public boolean mostrarUsuarios(Libro libros, frmUsuarios biblioteca, usuarios usr) {

        try {
            DefaultTableModel model = new DefaultTableModel();
            biblioteca.tablaUsuarios.setModel(model);
            PreparedStatement ps;
            ResultSet rs;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion();

            String sql = "SELECT usuario FROM paradox.usuarios WHERE id!=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, usr.getId());
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            model.addColumn("Usuario");

            while (rs.next()) {

                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {

                    filas[i] = rs.getObject(i + 1);

                }
                model.addRow(filas);
            }
            return true;
        } catch (SQLException e) {
            System.err.print(e);
            return false;
        }
    }

    public boolean mostrarOLibros(Libro libros, frmUsBiblioteca frmUsB, usuarios usr) {

        //frmBiblioteca biblioteca = new frmBiblioteca();
        try {
            DefaultTableModel model = new DefaultTableModel();
            frmUsB.tablaUsLibros.setModel(model);
            PreparedStatement ps;
            ResultSet rs;
            Conexion conn = new Conexion();
            Connection con = conn.getConexion();

            String sql = "SELECT libros.titulo, libros.nombreAutor, libros.categoria FROM biblioteca INNER JOIN libros ON biblioteca.libros_titulo = libros.titulo WHERE biblioteca.usuarios_id=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, usr.getId());
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            model.addColumn("Titulo");
            model.addColumn("Autor");
            model.addColumn("Categoria");

            while (rs.next()) {

                Object[] filas = new Object[cantidadColumnas];

                for (int i = 0; i < cantidadColumnas; i++) {

                    filas[i] = rs.getObject(i + 1);

                }
                model.addRow(filas);
            }
            return true;
        } catch (SQLException e) {
            System.err.print(e);
            return false;
        }
    }

    public boolean existeLibro(String titulo, usuarios usrP) {
        PreparedStatement ps;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM biblioteca WHERE libros_titulo=? AND usuarios_id=?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setInt(2, usrP.getId());
            rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }
}
