package Controlador;

import static java.util.Objects.hash;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Correo;
import modelo.SQLUsuarios;
import modelo.usuarios;
import vista.login;
import vista.registro;

public class controladorRegistro implements ActionListener {

    private usuarios mod;
    private SQLUsuarios modSql;
    private registro frmReg;
    private Correo mail;
    
    private login frmLog; //m

    public controladorRegistro(usuarios mod, SQLUsuarios modSql, registro frmReg, Correo mail) {
        this.mod = mod;
        this.modSql = modSql;
        this.frmReg = frmReg;
        this.mail = mail;
        this.frmReg.btnRegistrar.addActionListener((ActionListener) this);
    }
    
    public void iniciar(){
        frmReg.setTitle("Registro de usuario");
        frmReg.setLocationRelativeTo(null);
        
    }
    
    private void limpiar() {
        frmReg.txtUsuario.setText("");
        frmReg.txtPassword.setText("");
        frmReg.txtConfirmarPassword.setText("");
        frmReg.txtNombre.setText("");
        frmReg.txtCorreo.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmReg.btnRegistrar) {

            String pass = new String(frmReg.txtPassword.getPassword());
            String passCon = new String(frmReg.txtConfirmarPassword.getPassword());

            if (frmReg.txtUsuario.getText().equals("") || pass.equals("") || passCon.equals("")
                    || frmReg.txtNombre.getText().equals("") || frmReg.txtCorreo.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Hay campos vacios, debe llenar todos los campos.");
            } else {

                if (pass.equals(passCon)) {

                    if (modSql.existeUsuario(frmReg.txtUsuario.getText()) == 0) {

                        if (modSql.esEmail(frmReg.txtCorreo.getText())) {

                            String nuevoPass = hash.sha1(pass);

                            mod.setUsuario(frmReg.txtUsuario.getText());
                            mod.setPassword(nuevoPass);
                            mod.setNombre(frmReg.txtNombre.getText());
                            mod.setCorreo(frmReg.txtCorreo.getText());

                            if (modSql.registrar(mod)) {
                                
                                mail.enviar(mod); //Para enviar el correo de bienvenida...
                                frmReg.dispose();
                                JOptionPane.showMessageDialog(null, "Registro guardado!");
                                limpiar();
                                
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al guardar");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Correo electrónico inválido.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Este usuario ya existe.");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
                }
            }
        }
    }

}
