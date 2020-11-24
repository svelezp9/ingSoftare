package Controlador;

import modelo.ConsultasLibro;
import modelo.Libro;
import vista.frmAgregar;
import vista.frmBuscar;
import vista.frmEliminar;
import vista.frmBiblioteca;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Conexion;
import modelo.SQLUsuarios;
import modelo.usuarios;
import vista.frmBiblioteca;
import vista.frmUsuarios;
import vista.login;
import vista.registro;

public class Controlador implements ActionListener {

    private Libro libros;
    private ConsultasLibro conLibro;
    private frmBiblioteca biblioteca;
    private usuarios usr;

    public Controlador(Libro libros, ConsultasLibro conLibro, frmBiblioteca biblioteca, usuarios usr) {
        this.libros = libros;
        this.conLibro = conLibro;
        this.usr = usr;
        this.biblioteca = biblioteca;
        this.biblioteca.btnIrAgregar.addActionListener((ActionListener) this);
        this.biblioteca.btnIrBuscar.addActionListener((ActionListener) this);
        this.biblioteca.btnIrEliminar.addActionListener((ActionListener) this);
        this.biblioteca.btnMostrar.addActionListener((ActionListener) this);
        this.biblioteca.btnCerrar.addActionListener((ActionListener) this);
        this.biblioteca.btnOtros.addActionListener((ActionListener) this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == biblioteca.btnIrAgregar) {
            frmAgregar frmA = new frmAgregar();
            controladorAgregar agregar = new controladorAgregar(libros, conLibro, frmA, usr);
            agregar.iniciar();
            frmA.setVisible(true);
            biblioteca.dispose();
        }

        if (e.getSource() == biblioteca.btnIrBuscar) {
            frmBuscar frmB = new frmBuscar();
            controladorBuscar buscar = new controladorBuscar(libros, conLibro, frmB, usr);
            buscar.iniciar();
            frmB.setVisible(true);
            biblioteca.dispose();
        }

        if (e.getSource() == biblioteca.btnIrEliminar) {
            frmEliminar frmE = new frmEliminar();
            controladorEliminar eliminar = new controladorEliminar(libros, conLibro, frmE, usr);
            eliminar.iniciar();
            frmE.setVisible(true);
            biblioteca.dispose();
        }
        if (e.getSource() == biblioteca.btnMostrar) {
            if (conLibro.mostrarLibros(libros, biblioteca, usr)) {

            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el libro");
            }
        }
        if (e.getSource() == biblioteca.btnCerrar) {
            
            login frmLogin = new login();
            SQLUsuarios modSql = new SQLUsuarios();
            usuarios mod = new usuarios();
            controladorLogin log = new controladorLogin(mod, modSql, frmLogin);
            frmLogin.setLocationRelativeTo(null);
            frmLogin.setVisible(true);
            biblioteca.dispose();
        }
        if (e.getSource() == biblioteca.btnOtros) {
            frmUsuarios frmU = new frmUsuarios();
            SQLUsuarios conSql = new SQLUsuarios();
            controladorUsuarios conUsu = new controladorUsuarios(libros, conLibro, frmU, usr, conSql);
            conUsu.iniciar();
            frmU.setVisible(true);
            biblioteca.dispose();
        }
    }
}
