package Controlador;

import modelo.ConsultasLibro;
import modelo.Libro;
import vista.frmBuscar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.usuarios;
import vista.frmBiblioteca;

public class controladorBuscar implements ActionListener {

    private Libro libros;
    private ConsultasLibro conLibro;
    private frmBuscar frmB;
    private usuarios usr;

    public controladorBuscar(Libro libros, ConsultasLibro conLibro, frmBuscar frmB, usuarios usr) {
        this.libros = libros;
        this.conLibro = conLibro;
        this.frmB = frmB;
        this.usr = usr;
        this.frmB.btnBuscar.addActionListener((ActionListener) this);
        this.frmB.btnVolver.addActionListener((ActionListener) this);
    }

    public void iniciar() {
        frmB.setTitle("Buscar Libro");
        frmB.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmB.btnBuscar) {
            libros.setTitulo(frmB.txtBuscar.getText());

            if (conLibro.buscarLibros(libros, usr)) {
                JOptionPane.showMessageDialog(null, "El libro se encuentra en su biblioteca");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Este libro no esta en su bliblioteca");
                limpiar();
            }
        }
        if (e.getSource() == frmB.btnVolver) {
            frmBiblioteca frmBiblio = new frmBiblioteca();
            Controlador cont = new Controlador(libros, conLibro, frmBiblio, usr);
            frmBiblio.setLocationRelativeTo(null);
            frmBiblio.setVisible(true);
            frmB.dispose();
        }
    }

    public void limpiar() {
        frmB.txtBuscar.setText(null);
    }

}
