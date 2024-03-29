package botvn.views;

import botvn.botconfig.BotConfigLocal;
import botvn.botconfig.BotMessageObject;
import botvn.botconfig.BotMessageTemplate;
import botvn.languages.LanguagesSupport;
import botvn.languages.LanguagesSupport.Languages;
import botvn.libraries.MessageBox;
import java.util.ResourceBundle;

/**
 *
 * @author vanvo
 */
public class FormAddMessage extends javax.swing.JDialog {

    /**
     * 
     */
    private String mContent;
    private long mCurrent;
    
    private boolean update = false;
    private BotMessageObject mMessage;
    private static Languages mLanguages = Languages.VIETNAMESE;
    
    /**
     * Creates new form FormAddMessage
     * @param parent
     * @param modal
     */
    public FormAddMessage(java.awt.Frame parent, boolean modal, Languages language) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        mLanguages = language;
        UpdateLanguage(language);
    }
    
    public FormAddMessage(java.awt.Frame parent, boolean modal, Languages language, BotMessageObject message) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        mMessage = message;
        jAutoMessageContent.setText(message.toString());
        update = true;
        mLanguages = language;
        UpdateLanguage(language);
    }
    
    private void UpdateLanguage(Languages languages){
        ResourceBundle bundle = LanguagesSupport.setLanguage(languages);
        jLabel1.setText(bundle.getString("FormAddMessage.jLabel1.text"));
        jButton1.setText(bundle.getString("FormAddMessage.jButton1.text"));
        jButton2.setText(bundle.getString("FormAddMessage.jButton2.text"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jAutoMessageContent = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("botvn/languages/language_vi_VN"); // NOI18N
        jLabel1.setText(bundle.getString("FormAddMessage.jLabel1.text")); // NOI18N

        jAutoMessageContent.setColumns(20);
        jAutoMessageContent.setRows(5);
        jScrollPane1.setViewportView(jAutoMessageContent);

        jButton1.setText(bundle.getString("FormAddMessage.jButton1.text")); // NOI18N
        jButton1.setSize(new java.awt.Dimension(100, 29));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(bundle.getString("FormAddMessage.jButton2.text")); // NOI18N
        jButton2.setSize(new java.awt.Dimension(100, 29));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(138, 138, 138)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * Write a message to configurate file
     * @param evt 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(BotConfigLocal.isFileExist(BotConfigLocal.getMessageFile())){
            String msg = jAutoMessageContent.getText();
            if(msg.length() == 0){
                MessageBox.Show(null, "Bạn không thể lưu một tin nhắn trống", "BOTV", MessageBox.Buttons.OK);
                return;
            }
            if(!update){
                mCurrent = System.currentTimeMillis();
                BotMessageTemplate.Append(mCurrent, msg);
            }else{
                BotMessageTemplate.Update(mMessage.getKey(), msg);
            }
            mContent = msg;
            dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * 
     * @return 
     */
    public BotMessageObject getValue(){
        return new BotMessageObject(String.valueOf(mCurrent), mContent);
    }
    
    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(FormAddMessage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAddMessage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAddMessage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAddMessage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormAddMessage dialog = new FormAddMessage(new javax.swing.JFrame(), true, mLanguages);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea jAutoMessageContent;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
