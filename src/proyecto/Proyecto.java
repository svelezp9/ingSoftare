
package proyecto;

import Controlador.controladorAgregar;
import Controlador.controladorBuscar;
import Controlador.controladorEliminar;
import Controlador.controladorLogin;
import modelo.ConsultasLibro;
import modelo.Libro;
import modelo.SQLUsuarios;
import modelo.usuarios;
import vista.frmAgregar;
import vista.frmBuscar;
import vista.frmEliminar;
import vista.login;


public class Proyecto {

    public static void main(String[] args) {

        login frmLogin = new login();
        SQLUsuarios modSql = new SQLUsuarios();
        usuarios mod = new usuarios();
        controladorLogin log = new controladorLogin(mod, modSql, frmLogin);
        frmLogin.setLocationRelativeTo(null);
        frmLogin.setVisible(true);
    }

}
