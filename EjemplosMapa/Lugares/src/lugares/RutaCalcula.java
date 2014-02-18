package lugares;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import maps.java.Route;
import maps.java.StaticMaps;
import maps.java.StreetView;
import org.jsoup.Jsoup;

/**
 *
 * @author Luis Marcos
 */
public class RutaCalcula extends javax.swing.JFrame {

   private String direccionLlegada;
   private String direccionSalida;
   private Route.mode modoRuta=Route.mode.driving;
   private double fov=0.0;
   private Boolean flagVolver;
   Route ObjRuta=new Route();
   String[][] resultadoRuta;
   
    public RutaCalcula(String direccion,Boolean botonVolver) {
        initComponents();
        this.flagVolver=botonVolver;
        Ubicacion ObjUbicacion=new Ubicacion();
        direccionLlegada=direccion;
        direccionSalida=ObjUbicacion.getDireccion();
        try{
            this.calcularRuta();
        }catch(Exception ex){
        }
        if(botonVolver==true){
            JButton_Volver.setEnabled(true);
        }else{
            JButton_Volver.setEnabled(false);
        }
    }

    private RutaCalcula() {
    }

    
    private void calcularRuta() throws MalformedURLException, UnsupportedEncodingException{
        resultadoRuta=ObjRuta.getRoute(direccionSalida, direccionLlegada, null, true, modoRuta, Route.avoids.nothing);
        String[][] datosRuta=new String[resultadoRuta.length][3];
        //Extraemos sólo duración/distancia/indicaciones
        for(int i=0;i<datosRuta.length;i++){
            datosRuta[i][0]=resultadoRuta[i][0];
            datosRuta[i][1]=resultadoRuta[i][1];
            datosRuta[i][2]=Jsoup.parse(resultadoRuta[i][2]).text();
        }
        this.dibujarTabla(datosRuta);
        this.dibujarStreetView(Double.valueOf(resultadoRuta[0][3]), Double.valueOf(resultadoRuta[0][4]));
        this.dibujarMapa(ObjRuta.getPolilines().get(0));
        this.jTable_Ruta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.jTable_Ruta.setRowSelectionInterval(0, 0);
    }
  
    private void dibujarTabla(String[][] datosRuta){
        String[] columnas=new String[3];
        columnas[0]="Duración tramo";columnas[1]="Distancia tramo";columnas[2]="Indicaciones";
        TableModel tableModel=new DefaultTableModel(datosRuta, columnas);
        this.jTable_Ruta.setModel(tableModel);
    }
    
    private void dibujarMapa(String referencia) throws MalformedURLException, UnsupportedEncodingException{
        StaticMaps ObjStatic=new StaticMaps();
        Image imagenRuta=ObjStatic.getStaticMapRoute(new Dimension(300,200),
                1, StaticMaps.Format.png, StaticMaps.Maptype.roadmap, referencia);
        jLabel_Mapa.setIcon(new ImageIcon(imagenRuta));
    }
    
     private void dibujarStreetView(double latitud, double longitud) throws MalformedURLException, UnsupportedEncodingException {
         StreetView ObjStreet=new StreetView();
         String direccion=String.valueOf(latitud) + "," + String.valueOf(longitud);
         Image imagenStreet=ObjStreet.getStreetView(direccion, new Dimension(300,200),
                 fov, -1,-100);
         jLabel_ImagenStreet.setIcon(new ImageIcon(imagenStreet));
    }
    private void sumaAnguloFOV(double value){
        fov+=value;
        if(fov>=360){
            fov=0;
        }else if(fov<0){
            fov=315;
        }
    }
    
    private void volver(){
        this.setVisible(false);
        SelecionarAccion formulario=new SelecionarAccion();
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        formulario.setLocation((d.width/2)-(formulario.getWidth()/2), (d.height/2)-(formulario.getHeight()/2));
        formulario.setSize(new Dimension(400, 350));
        formulario.setVisible(true);
    }
    
    private void cerrarVentana(){
        if(flagVolver==true){
            this.volver();
        }else{
            this.setVisible(false);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Ruta = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel_Mapa = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel_ImagenStreet = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        JButton_Volver = new javax.swing.JButton();

        setAlwaysOnTop(true);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTable_Ruta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Duración", "Distancia", "Indicaciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_Ruta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable_RutaMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_Ruta);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel_Mapa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_Mapa, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_Mapa, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel_ImagenStreet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_ImagenStreet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_ImagenStreet, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Mapa general");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        JButton_Volver.setText("Volver");
        JButton_Volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JButton_VolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addGap(26, 26, 26)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(JButton_Volver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(111, 111, 111))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(JButton_Volver))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(106, 106, 106))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(108, 108, 108)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable_RutaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_RutaMousePressed
       try {
           this.dibujarMapa(ObjRuta.getPolilines().get(this.jTable_Ruta.getSelectedRow()));
           this.dibujarStreetView(Double.valueOf(resultadoRuta[this.jTable_Ruta.getSelectedRow()][3]), 
                   Double.valueOf(resultadoRuta[this.jTable_Ruta.getSelectedRow()][4]));
       } catch (Exception ex) {
       }
    }//GEN-LAST:event_jTable_RutaMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       sumaAnguloFOV(45);
       try {
           this.dibujarStreetView(Double.valueOf(resultadoRuta[this.jTable_Ruta.getSelectedRow()][3]),
                      Double.valueOf(resultadoRuta[this.jTable_Ruta.getSelectedRow()][4]));
       } catch (Exception ex) {
       }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        sumaAnguloFOV(-45);
       try {
           this.dibujarStreetView(Double.valueOf(resultadoRuta[this.jTable_Ruta.getSelectedRow()][3]),
                      Double.valueOf(resultadoRuta[this.jTable_Ruta.getSelectedRow()][4]));
       } catch (Exception ex) {
       }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       try {
           this.dibujarMapa(ObjRuta.getGeneralPolyline());
       } catch (Exception ex) {
       }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void JButton_VolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JButton_VolverActionPerformed
        this.volver();
    }//GEN-LAST:event_JButton_VolverActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.cerrarVentana();
    }//GEN-LAST:event_formWindowClosing

   
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RutaCalcula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RutaCalcula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RutaCalcula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RutaCalcula.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RutaCalcula().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton JButton_Volver;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel_ImagenStreet;
    private javax.swing.JLabel jLabel_Mapa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Ruta;
    // End of variables declaration//GEN-END:variables
}
