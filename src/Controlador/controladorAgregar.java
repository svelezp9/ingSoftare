package Controlador;

import modelo.ConsultasLibro;
import modelo.Libro;
import vista.frmAgregar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.usuarios;
import vista.frmBiblioteca;

public class controladorAgregar implements ActionListener {

    private Libro libros;
    private ConsultasLibro conLibro;
    private frmAgregar frmA;
    private usuarios usr;

    public controladorAgregar(Libro libros, ConsultasLibro conLibro, frmAgregar frmA, usuarios usr) {
        this.libros = libros;
        this.conLibro = conLibro;
        this.frmA = frmA;
        this.usr = usr;
        this.frmA.btnAgregar.addActionListener((ActionListener) this);
        this.frmA.btnVolver.addActionListener((ActionListener) this);
    }

    public void iniciar() {
        frmA.setTitle("Buscar Libro");
        frmA.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmA.btnAgregar) {

            libros.setTitulo(frmA.txtTitulo.getText());
            libros.setNombreAutor(frmA.txtAutor.getText());
            libros.setCategoria(frmA.txtCategoria.getText());

            if (conLibro.agregarLibros(libros, usr)) {
                JOptionPane.showMessageDialog(null, "Libro guardado exitosamente");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el libro");
                limpiar();
            }
        }
        if (e.getSource() == frmA.btnVolver) {
            frmBiblioteca frmBiblio = new frmBiblioteca();
            Controlador cont = new Controlador(libros, conLibro, frmBiblio, usr);
            frmBiblio.setLocationRelativeTo(null);
            frmBiblio.setVisible(true);
            frmA.dispose();
        }
    }

    public void limpiar() {
        frmA.txtTitulo.setText(null);
        frmA.txtAutor.setText(null);
        frmA.txtCategoria.setText(null);
    }

}
