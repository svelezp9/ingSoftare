
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.ConsultasLibro;
import modelo.Correo;
import modelo.Libro;
import modelo.SQLUsuarios;
import modelo.usuarios;
import vista.frmBiblioteca;
import vista.frmUsBiblioteca;
import vista.frmUsuarios;


public class controladorPrestamo implements ActionListener {
    
    private Libro libros;
    private ConsultasLibro conLibro;
    private frmUsBiblioteca frmUsB;
    private usuarios usrR; //Usuario recibe
    private usuarios usrP; //Usuario presta
    private Correo mail;
    
    public controladorPrestamo(Libro libros, ConsultasLibro conLibro, frmUsBiblioteca frmUsB, usuarios usrR, usuarios usrP, Correo mail) {
        this.libros = libros;
        this.conLibro = conLibro;
        this.frmUsB = frmUsB;
        this.usrR = usrR;
        this.usrP = usrP;
        this.mail = mail;
        this.frmUsB.btnPrestamo.addActionListener((ActionListener) this);
        this.frmUsB.btnVolver.addActionListener((ActionListener) this);
        this.frmUsB.btnMostrar.addActionListener((ActionListener) this);
    }
    
    public void iniciar() {
        frmUsB.setTitle("Biblioteca externa");
        frmUsB.setLocationRelativeTo(null);
    }
    
    private void limpiar() {
        frmUsB.txtPrestamo.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == frmUsB.btnVolver) {
            frmUsuarios frmU = new frmUsuarios();
            SQLUsuarios conSql = new SQLUsuarios();
            controladorUsuarios contu = new controladorUsuarios(libros, conLibro, frmU, usrR, conSql);
            frmU.setLocationRelativeTo(null);
            frmU.setVisible(true);
            frmUsB.dispose();
        }
        if (e.getSource() == frmUsB.btnMostrar) {
            if (conLibro.mostrarOLibros(libros, frmUsB, usrP)) {

            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar los libros de este usuario.");
            }
        }
        if (e.getSource() == frmUsB.btnPrestamo) {
            String titulo = frmUsB.txtPrestamo.getText();
            if(conLibro.existeLibro(titulo, usrP)){
                mail.solicitud(usrP, usrR, titulo);
                limpiar();
            }else {
                JOptionPane.showMessageDialog(null, "Titulo de libro incorrecto.");
            }
        }
    }
    
}
