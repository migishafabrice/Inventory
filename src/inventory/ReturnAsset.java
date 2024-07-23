/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventory;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.FirestoreClient;
import static inventory.AssetsHome.initialize;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;

/**
 *
 * @author RIIO
 */
public final class ReturnAsset extends javax.swing.JFrame {

    /**
     * Creates new form ReturnAsset
     */
    String id;
     public ReturnAsset() {
        initComponents();
      }
    public ReturnAsset(Object docid) throws IOException {
        super("Return Asset");
        initComponents();
         if (FirebaseApp.getApps().isEmpty()) {
        initialize();
        }
         loadDocumentData(String.valueOf(docid));
         id=docid.toString();
    }
public static void returnedData(String id,String returnedBy,String returnedOn, String condition) throws IOException, InterruptedException, ExecutionException
{
        Firestore db = FirestoreClient.getFirestore();
        SimpleDateFormat dte=new SimpleDateFormat("dd/MM/yyyy");
        Date d=new Date();
        // Example data to be added
        Map<String, Object> data = new HashMap<>();
        data.put("MovedAssetReturnedBy", returnedBy);
        data.put("MovedAssetReturnedOn", returnedOn);
        data.put("MovedAssetReturnedCondition", condition);
        // Add a new document with a generated ID
        DocumentReference ref = db.collection("MovedAssets").document(id);
        ref.update(data); // .get() is optional if you want to wait for the operation to complete
        JOptionPane.showMessageDialog(null,"Asset Returned","System Info",JOptionPane.INFORMATION_MESSAGE);  
}
public void loadDocumentData(String docid)
{
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("MovedAssets").document(docid);
    
       // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
          try {
            // Get the document snapshot
            DocumentSnapshot document = future.get();
              if (document.exists()) {
                // Document data
                Map<String, Object> data = document.getData();
                DocumentReference docRefInit = db.collection("AssetInfo").document(data.get("MovedAssetId").toString());
                ApiFuture<DocumentSnapshot> futureInit = docRefInit.get();
                DocumentSnapshot documentInit = futureInit.get();
              if (documentInit.exists())
              {
                Map<String, Object> dataInit = documentInit.getData();
                txtAssetName.setText(String.valueOf(dataInit.get("AssetName")));
                txtAssetCode.setText(String.valueOf(dataInit.get("AssetCode")));
                txtAssetSN.setText(String.valueOf(dataInit.get("AssetSN")));
                txtAssetCat.setText(String.valueOf(dataInit.get("AssetCategory")));
                txtAssetDepTo.setText(String.valueOf(dataInit.get("AssetDepartment")));
                txtAssetDepFrom.setText(String.valueOf(data.get("MovedAssetToDepartment")));
                txtAssetMovedBy.setText(String.valueOf(data.get("MovedAssetMovedBy")));
                txtAssetReason.setText(String.valueOf(data.get("MovedAssetReason")));
                txtAssetApprovedBy.setText(String.valueOf(data.get("MovedAssetApprovedBy")));
                txtAssetMovedOn.setText(String.valueOf(data.get("MovedAssetOn")));
                txtAssetEstOn.setText(String.valueOf(data.get("MovedAssetEstimatedOn")));
                
                //txtAssetQuantity.setText(String.valueOf(data.get("AssetQuantity")));
                //txtAssetStatus.setText(String.valueOf(data.get("AssetStatus")));
                /*for(int i=0;i<cboAssetDep.getItemCount();i++)
                {
                    if((String.valueOf(cboAssetDep.getItemAt(i)).replaceAll("\\s", "")).equalsIgnoreCase(String.valueOf(data.get("AssetDepartment")).replaceAll("\\s", "")))
                    {
                        cboAssetDep.setSelectedIndex(i);
                        break;
                    }
                }*/
                //txtAssetLoc.setText(String.valueOf(data.get("AssetLocation")));
                //txtAssetReason.setText(String.valueOf(data.get("AssetDescription")));
              }
              else {
                JOptionPane.showMessageDialog(null,"No such Asset Found");
            }
            } else {
                JOptionPane.showMessageDialog(null,"No such Moved Asset Found");
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null,e);
        }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel13 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtAssetApprovedBy = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jdAssetReturnedOn = new com.toedter.calendar.JDateChooser();
        txtAssetMovedOn = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtAssetDepTo = new javax.swing.JTextField();
        txtAssetName = new javax.swing.JTextField();
        txtAssetSN = new javax.swing.JTextField();
        txtAssetCode = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtAssetEstOn = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAssetReason = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtAssetReturnedCondition = new javax.swing.JTextArea();
        txtAssetReturnedBy = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtAssetCat = new javax.swing.JTextField();
        txtAssetDepFrom = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtAssetMovedBy = new javax.swing.JTextField();
        btnMoveAsset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtAssetApprovedBy.setEditable(false);
        txtAssetApprovedBy.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetApprovedBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetApprovedByActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel17.setText("Asset Category");

        txtAssetMovedOn.setEditable(false);
        txtAssetMovedOn.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetMovedOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetMovedOnActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel32.setText("Estimated Return Date");

        txtAssetDepTo.setEditable(false);
        txtAssetDepTo.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetDepTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetDepToActionPerformed(evt);
            }
        });

        txtAssetName.setEditable(false);
        txtAssetName.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetNameActionPerformed(evt);
            }
        });

        txtAssetSN.setEditable(false);
        txtAssetSN.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetSN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetSNActionPerformed(evt);
            }
        });

        txtAssetCode.setEditable(false);
        txtAssetCode.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetCodeActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel11.setText("Asset Department- Returned From");

        txtAssetEstOn.setEditable(false);
        txtAssetEstOn.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel33.setText("Asset Returned By");

        txtAssetReason.setEditable(false);
        txtAssetReason.setColumns(20);
        txtAssetReason.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetReason.setRows(100);
        jScrollPane3.setViewportView(txtAssetReason);

        jLabel16.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel16.setText("Asset SN");

        txtAssetReturnedCondition.setColumns(20);
        txtAssetReturnedCondition.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetReturnedCondition.setRows(100);
        jScrollPane4.setViewportView(txtAssetReturnedCondition);

        txtAssetReturnedBy.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetReturnedBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetReturnedByActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel15.setText("Reason For Movement");

        jLabel30.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel30.setText("Asset Move Approved By");

        jLabel3.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel3.setText("Asset Code");

        jLabel12.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel12.setText("Asset Department- Returned To");

        jLabel31.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel31.setText("Moved On");

        jLabel34.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel34.setText("Returned Date");

        jLabel27.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel27.setText("Asset Moved By");

        txtAssetCat.setEditable(false);
        txtAssetCat.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetCatActionPerformed(evt);
            }
        });

        txtAssetDepFrom.setEditable(false);
        txtAssetDepFrom.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetDepFrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetDepFromActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel18.setText("Condition on Return ");

        jLabel29.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel29.setText("Asset Name");

        jLabel8.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(83, 5, 25));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ASSET RETURN");
        jLabel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        txtAssetMovedBy.setEditable(false);
        txtAssetMovedBy.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetMovedBy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetMovedByActionPerformed(evt);
            }
        });

        btnMoveAsset.setBackground(new java.awt.Color(167, 108, 108));
        btnMoveAsset.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        btnMoveAsset.setText("Save");
        btnMoveAsset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoveAssetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAssetMovedOn)
                            .addComponent(txtAssetDepTo)
                            .addComponent(jdAssetReturnedOn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(txtAssetReturnedBy)
                            .addComponent(txtAssetCat)
                            .addComponent(txtAssetSN)
                            .addComponent(txtAssetCode)
                            .addComponent(txtAssetName)
                            .addComponent(btnMoveAsset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAssetMovedBy, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAssetDepFrom, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAssetApprovedBy, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAssetEstOn))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAssetName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetCode, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetSN, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetCat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetDepFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetDepTo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetMovedBy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetApprovedBy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetMovedOn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetEstOn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAssetReturnedBy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdAssetReturnedOn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)))
                .addComponent(btnMoveAsset)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 578, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMoveAssetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoveAssetActionPerformed
        String returnedBy=txtAssetReturnedBy.getText();
        String returnedOn=jdAssetReturnedOn.getDate().toString();
        String condition=txtAssetReturnedCondition.getText();
        try {
            returnedData(id,returnedBy,returnedOn,condition);
            dispose();
         } 
        catch (IOException | InterruptedException | ExecutionException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }

    }//GEN-LAST:event_btnMoveAssetActionPerformed

    private void txtAssetNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetNameActionPerformed

    private void txtAssetCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetCatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetCatActionPerformed

    private void txtAssetCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetCodeActionPerformed

    private void txtAssetSNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetSNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetSNActionPerformed

    private void txtAssetMovedByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetMovedByActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetMovedByActionPerformed

    private void txtAssetDepFromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetDepFromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetDepFromActionPerformed

    private void txtAssetApprovedByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetApprovedByActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetApprovedByActionPerformed

    private void txtAssetReturnedByActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetReturnedByActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetReturnedByActionPerformed

    private void txtAssetDepToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetDepToActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetDepToActionPerformed

    private void txtAssetMovedOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetMovedOnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetMovedOnActionPerformed

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
            java.util.logging.Logger.getLogger(ReturnAsset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnAsset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnAsset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnAsset.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnAsset().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoveAsset;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private com.toedter.calendar.JDateChooser jdAssetReturnedOn;
    private javax.swing.JTextField txtAssetApprovedBy;
    private javax.swing.JTextField txtAssetCat;
    private javax.swing.JTextField txtAssetCode;
    private javax.swing.JTextField txtAssetDepFrom;
    private javax.swing.JTextField txtAssetDepTo;
    private javax.swing.JTextField txtAssetEstOn;
    private javax.swing.JTextField txtAssetMovedBy;
    private javax.swing.JTextField txtAssetMovedOn;
    private javax.swing.JTextField txtAssetName;
    private javax.swing.JTextArea txtAssetReason;
    private javax.swing.JTextField txtAssetReturnedBy;
    private javax.swing.JTextArea txtAssetReturnedCondition;
    private javax.swing.JTextField txtAssetSN;
    // End of variables declaration//GEN-END:variables
}
