package Controlador;

import modelo.ConsultasLibro;
import modelo.Libro;
import vista.frmEliminar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.usuarios;
import vista.frmBiblioteca;

public class controladorEliminar implements ActionListener {

    private Libro libros;
    private ConsultasLibro conLibro;
    private frmEliminar frmE;
    private usuarios usr;

    public controladorEliminar(Libro libros, ConsultasLibro conLibro, frmEliminar frmE, usuarios usr) {
        this.libros = libros;
        this.conLibro = conLibro;
        this.frmE = frmE;
        this.usr = usr;
        this.frmE.btnEliminar.addActionListener((ActionListener) this);
        this.frmE.btnVolver.addActionListener((ActionListener) this);
    }

    public void iniciar() {
        frmE.setTitle("Buscar Libro");
        frmE.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmE.btnEliminar) {
            libros.setTitulo(frmE.txtEliminarTitulo.getText());

            if (conLibro.eliminarLibros(libros, usr)) {
                JOptionPane.showMessageDialog(null, "Libro Eliminado Exitosamente");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Erro al eliminar el libro");
                limpiar();
            }
        }
        if (e.getSource() == frmE.btnVolver) { 
            frmBiblioteca frmBiblio = new frmBiblioteca();
            Controlador cont = new Controlador(libros, conLibro, frmBiblio, usr);
            frmBiblio.setLocationRelativeTo(null);
            frmBiblio.setVisible(true);
            frmE.dispose();
        }
    }

    public void limpiar() {
        frmE.txtEliminarTitulo.setText(null);
    }

}
