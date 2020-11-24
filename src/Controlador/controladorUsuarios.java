package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.ConsultasLibro;
import modelo.Correo;
import modelo.Libro;
import modelo.usuarios;
import vista.frmAgregar;
import vista.frmBiblioteca;
import vista.frmUsBiblioteca;
import vista.frmUsuarios;
import modelo.SQLUsuarios;

public class controladorUsuarios implements ActionListener {

    private Libro libros;
    private ConsultasLibro conLibro;
    private frmUsuarios frmU;
    private usuarios usr;
    private SQLUsuarios conSql;

    public controladorUsuarios(Libro libros, ConsultasLibro conLibro, frmUsuarios frmU, usuarios usr, SQLUsuarios conSql) {
        this.libros = libros;
        this.conLibro = conLibro;
        this.frmU = frmU;
        this.usr = usr;
        this.conSql = conSql;
        this.frmU.btnBiblio.addActionListener((ActionListener) this);
        this.frmU.btnVolver.addActionListener((ActionListener) this);
        this.frmU.btnMostrar.addActionListener((ActionListener) this);
    }

    public void iniciar() {
        frmU.setTitle("Otros Usuarios");
        frmU.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmU.btnBiblio) {
            frmUsBiblioteca frmUsB = new frmUsBiblioteca();
            usuarios usrP = conSql.buscarUsuario(frmU.txtOUsuarios.getText());

            if (usrP.getCorreo() != null) {
                Correo mail = new Correo();
                controladorPrestamo conPres = new controladorPrestamo(libros, conLibro, frmUsB, usr, usrP, mail);
                conPres.iniciar();
                frmUsB.setVisible(true);
                frmU.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Nombre de usuario equivocado.");
            }
        }
        if (e.getSource() == frmU.btnVolver) {
            frmBiblioteca frmBiblio = new frmBiblioteca();
            Controlador cont = new Controlador(libros, conLibro, frmBiblio, usr);
            frmBiblio.setLocationRelativeTo(null);
            frmBiblio.setVisible(true);
            frmU.dispose();
        }
        if (e.getSource() == frmU.btnMostrar) {
            if (conLibro.mostrarUsuarios(libros, frmU, usr)) {

            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el usuario");
            }
        }

    }

}
