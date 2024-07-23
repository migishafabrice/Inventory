/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventory;
/**
 *
 * @author RIIO
 */
import com.google.api.services.drive.model.File;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.core.ApiFuture;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
  private static final String APPLICATION_NAME = "RIIO APP";
  private static final String CREDENTIALS_FILE_PATH_DRIVE = "auth/credentials_auth.json";
  private static String names, usercode,department,useremail,title,folder,myPic,mySign;
  /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */

  /**
   * Global instance of the scopes required by this quickstart.
   * If modifying these scopes, delete your previously saved tokens/ folder.
     * @return 
     * @throws java.io.IOException
     * @throws java.security.GeneralSecurityException
   */

public static void initialize() throws IOException {
        GoogleCredentials credentials;
        try (InputStream credentialsStream = AssetsHome.class.getResourceAsStream(CREDENTIALS_FILE_PATH_DRIVE)) {
            credentials = GoogleCredentials.fromStream(credentialsStream);
        }
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(credentials)
            .build();

        FirebaseApp.initializeApp(options);
    }
 public Boolean checkLogin()throws IOException
 {
     String user=txtEmail.getText();
     String pass=String.valueOf(txtPassword.getPassword());
      // Build a new authorized API client service.
    Firestore db = FirestoreClient.getFirestore();
     CollectionReference collection = db.collection("Users");
    // Asynchronously retrieve all documents
    ApiFuture<QuerySnapshot> query = collection.get();
    try {
        // Get the query results
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        // Iterate through the documents and display the data
        for (DocumentSnapshot document : documents) {
            Map<String,Object>thisDoc=document.getData();
            if(thisDoc.get("UserEmail").equals(user) && thisDoc.get("UserPassword").equals(pass))
            {
            Date d=new Date();
            SimpleDateFormat dte=new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_SSS");
            names=(String) thisDoc.get("UserNames");
            usercode=(String) thisDoc.get("UserCode");
            useremail=(String) thisDoc.get("UserEmail");
            department=(String) thisDoc.get("UserDepartment");
            title=(String) thisDoc.get("UserTitle");
            String folderName=names.replace(" ","_")+"_"+dte.format(d);
            if(thisDoc.get("UserFolder").equals("None"))
            { 
              String folderId=createFolder(folderName,document.getId())  ;
              DocumentReference docRef = db.collection("Users").document(document.getId());
              Map<String, Object> data = new HashMap<>();
              data.put("UserFolder", folderId);
              docRef.update(data);
            }
            names=(String) thisDoc.get("UserNames");
            usercode=(String) thisDoc.get("UserCode");
            useremail=(String) thisDoc.get("UserEmail");
            department=(String) thisDoc.get("UserDepartment");
            title=(String) thisDoc.get("UserTitle");
            folder=(String) thisDoc.get("UserFolder");
            myPic=(String) thisDoc.get("UserPhoto");
            mySign=(String) thisDoc.get("UserSignature");
            AssetsHome home=new AssetsHome(names, useremail, usercode,department,title,folder,myPic,mySign);
            home.setVisible(true);
            //DASHBOARD dash=new DASHBOARD(names, useremail, usercode,department,title,folder,myPic,mySign);
           // dash.loadUser(usercode, pass, usercode, user);
            //dash.setVisible(true);
            Login log=new Login();
            log.setVisible(false);
            dispose(); 
            return true;
            }
     } 
     JOptionPane.showMessageDialog(this,"Access denied, Contact the Administrator","Login Failed",JOptionPane.ERROR_MESSAGE);
    return false;
    } catch (InterruptedException | ExecutionException e) {
        JOptionPane.showMessageDialog(null, e);
    }
return false;           
 }
public static String createFolder(String folderName,String docId) throws IOException {
    // Load pre-authorized user credentials from the environment.
    // TODO(developer) - See https://developers.google.com/identity for
    // guides on implementing OAuth2 for your application.
    GoogleCredentials credentials;
    try (InputStream credentialsStream = Login.class.getResourceAsStream(CREDENTIALS_FILE_PATH_DRIVE)) {
            credentials = GoogleCredentials.fromStream(credentialsStream);
        }
//LOGIN.class.getResourceAsStream(CREDENTIALS_FILE_PATH)
    credentials = credentials.createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
    HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
        credentials);

    // Build a new authorized API client service.
    Drive service = new Drive.Builder(new NetHttpTransport(),
        GsonFactory.getDefaultInstance(),
        requestInitializer)
        .setApplicationName(APPLICATION_NAME)
        .build();
    // File's metadata.
    File fileMetadata = new File();
    fileMetadata.setName(folderName);
    fileMetadata.setMimeType("application/vnd.google-apps.folder");
    fileMetadata.setParents(Collections.singletonList("1vBhrfeof4YDKCrKB5DuITujQCfuRv3jw"));
    try {
      File file = service.files().create(fileMetadata)
          .setFields("id,name")
          .execute();
      
      return file.getId();
    } 
    catch (GoogleJsonResponseException e) {
      // TODO(developer) - handle error appropriately
      JOptionPane.showMessageDialog(null, "Unable to create folder: " + e.getDetails());
      throw e;
    }
  }
public Login() throws IOException 
    {
        super("RIIO Inventory/Login");
        initComponents();
         Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        // Set the frame to full screen
        this.setBounds(screenSize);
        // Make sure the frame is always maximized
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        if (FirebaseApp.getApps().isEmpty()) {
        initialize();
        }
        txtPassword.setEchoChar('*');   
        
    }
   /**
   * Prints the names and majors of students in a sample spreadsheet:
   * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
   */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        chkPassword = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        setLocation(new java.awt.Point(300, 100));

        jPanel1.setBackground(new java.awt.Color(190, 215, 215));

        jPanel2.setBackground(new java.awt.Color(190, 215, 215));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setBackground(new java.awt.Color(147, 194, 202));
        jLabel1.setFont(new java.awt.Font("Calisto MT", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(83, 5, 25));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RIIO-INVENTORY");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1098, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(16, 16, 16))
        );

        jPanel7.setBackground(new java.awt.Color(190, 215, 215));

        txtPassword.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N

        btnLogin.setBackground(new java.awt.Color(167, 108, 108));
        btnLogin.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N
        btnLogin.setText("AUTHENTICATE");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel2.setText("Username[Email]:");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel3.setText("Password");

        txtEmail.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N

        chkPassword.setBackground(new java.awt.Color(190, 215, 215));
        chkPassword.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        chkPassword.setText("Show Password");
        chkPassword.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkPasswordItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chkPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEmail)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        try
        {
        checkLogin();
        }
        catch(IOException e)
        {
         JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void chkPasswordItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkPasswordItemStateChanged
        // TODO add your handling code here:
        if(chkPassword.isSelected())
        {
            txtPassword.setEchoChar((char)0);
        }
        else
        {
          txtPassword.setEchoChar('*');  
        }
    }//GEN-LAST:event_chkPasswordItemStateChanged

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Login().setVisible(true);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox chkPassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
