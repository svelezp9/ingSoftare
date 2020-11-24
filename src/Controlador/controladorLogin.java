
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.frmBiblioteca;
import vista.login;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.util.Objects.hash;
import javax.swing.JOptionPane;
import modelo.ConsultasLibro;
import modelo.Correo;
import modelo.Libro;
import modelo.SQLUsuarios;
import modelo.usuarios;
import vista.frmAgregar;
import vista.frmBuscar;
import vista.frmEliminar;
import vista.registro;

public class controladorLogin implements ActionListener {

    private usuarios mod;
    private SQLUsuarios modSql;
    private login frmLog;
    

    public controladorLogin(usuarios mod, SQLUsuarios modSql, login frmLog) {
        this.mod = mod;
        this.modSql = modSql;
        this.frmLog = frmLog;
        this.frmLog.btnIngresar.addActionListener((ActionListener) this);
        this.frmLog.btnRegistrar.addActionListener((ActionListener) this);
    }

    public void iniciar() {
        frmLog.setTitle("Login");
        frmLog.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmLog.btnIngresar) {

            Date date = new Date();
            DateFormat fechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String pass = new String(frmLog.txtPassword.getPassword()); //Pasando la contraseña a string.

            if (!frmLog.txtUsuario.getText().equals("") && !pass.equals("")) { //Cuando el Usuario y la Contraseña no sean nulos.

                String nuevoPass = hash.sha1(pass);

                mod.setUsuario(frmLog.txtUsuario.getText());
                mod.setPassword(nuevoPass);
                mod.setLast_session(fechaHora.format(date));
                mod.setCorreo(modSql.buscandoCorreo(mod.getUsuario()));

                if (modSql.login(mod)) {

                    frmLog.dispose();

                    frmBiblioteca frmBiblio = new frmBiblioteca();
                    ConsultasLibro conLibro = new ConsultasLibro();
                    Libro libros = new Libro();
                    Controlador cont = new Controlador(libros, conLibro, frmBiblio, mod);
                    frmBiblio.setLocationRelativeTo(null);
                    frmBiblio.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Datos incorrectos.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar sus datos.");
            }
        }
        if (e.getSource() == frmLog.btnRegistrar) {
                registro frmReg = new registro();
                Correo mail = new Correo();
                controladorRegistro registro = new controladorRegistro(mod, modSql, frmReg, mail);
                frmReg.setLocationRelativeTo(null);
                frmReg.setVisible(true);
        }
    }

}
