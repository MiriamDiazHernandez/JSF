 
package lugares;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;

/**
 *
 * @author Luis Marcos
 */
public class Lugares {
 
    public static void main(String[] args) {
        for(UIManager.LookAndFeelInfo laf:UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(laf.getName()))
                try {
                UIManager.setLookAndFeel(laf.getClassName());
            } catch (Exception ex) {
            }
        }
        FormularioInicio mainF=new FormularioInicio();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        mainF.setLocation((d.width/2)-(mainF.getWidth()/2), (d.height/2)-(mainF.getHeight()/2));
//        mainF.setSize(550, 630);
        mainF.setVisible(true);
    }
}
