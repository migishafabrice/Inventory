/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inventory;

/**
 *
 * @author RIIO
 */
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.itextpdf.text.BadElementException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import javax.swing.table.DefaultTableModel;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;
public final class AssetsHome extends javax.swing.JFrame {
private static final String CREDENTIALS_FILE_PATH_DRIVE = "auth/credentials_auth.json";
DefaultTableModel modelData,searchTable,modelDataMoved,searchTableMoved;
ArrayList retreivedMoved,retreivedDocuments,searchedDocuments;
private static String names,department,useremail,usercode,title,folderId,docType="",docDesc="",regOn="",myPhoto,mySign;
    /**
     * Creates new form AssetsHom
     * @throws java.io.IOException
     */
    public AssetsHome()  {
        initComponents();
       // Get the screen size using Toolkit
       Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
     
    }
    public AssetsHome(String username,String email,String userCode,String dep,String titles,String folder,String pic,String sign)
            throws IOException
    {
      super("RIIO Inventory:[DashBoard]");
      initComponents();
      names=username;
      department=dep;
      useremail=email;
      title=titles;
      usercode=userCode;
      folderId=folder;
      myPhoto=pic;
      mySign=sign;
       // Get the screen size using Toolkit
       Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        // Set the frame to full screen
        this.setBounds(screenSize);
        // Make sure the frame is always maximized
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        lbUserNames.setText(names);
        lbUserEmail.setText(useremail);
        lbUserDep.setText(department);
        lbUserTitle.setText(title);
        if (FirebaseApp.getApps().isEmpty()) {
        initialize();
        }
        retreiveData();  
    }
    /*public void displayPhoto(String pic)
    {
        myPic.setText("");
         GoogleCredentials credentials = null;
    try (InputStream credentialsStream = Login.class.getResourceAsStream(CREDENTIALS_FILE_PATH_DRIVE)) {
            credentials = GoogleCredentials.fromStream(credentialsStream);
        } catch (IOException ex) {
         JOptionPane.showMessageDialog(null, ex);
      }
//LOGIN.class.getResourceAsStream(CREDENTIALS_FILE_PATH)
    credentials = credentials.createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
    HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
        credentials);
    // Build a new authorized API client service.
    Drive service = new Drive.Builder(new NetHttpTransport(),
        GsonFactory.getDefaultInstance(),
        requestInitializer)
        .setApplicationName("RIIO-INVENTORY")
        .build();
       try
       {
           
     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        service.files().get(pic).executeMediaAndDownloadTo(outputStream);
     byte[] imageBytes = outputStream.toByteArray();
        ImageIcon icon = new ImageIcon(imageBytes);
        java.awt.Image pi=icon.getImage().getScaledInstance(myPic.getWidth(),
                    myPic.getHeight(), java.awt.Image.SCALE_SMOOTH);
            ImageIcon rp=new ImageIcon(pi);
            myPic.setIcon(rp);
        
      } catch (IOException  ex) {
          JOptionPane.showMessageDialog(null, ex);
      }
    }*/
public void filterDepartment()
    {
      cboAssetLocRep.removeAllItems();
        cboAssetCatRep.removeAllItems();
        cboAssetStatRep.removeAllItems();
        if(!cboAssetDepRep.getSelectedItem().equals("Select Department"))
        {
            searchTable=new DefaultTableModel(new String[]{"#","NAME OF ASSET","ASSET CODE","ASSET SN","ASSET CATEGORY","ASSET QUANTITY","ASSET STATUS","ASSET DEPARTMENT","ASSET LOCATION"}, 0)
                     {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are not editable
                return false;
            }
        };
               Map<Object,Object> locations=new HashMap();
              int n=0; 
         for(int i=0;i<modelData.getRowCount();i++)
                {
                    
 if((String.valueOf(modelData.getValueAt(i, 7)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetDepRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase())))
                        {
                       n++;
                            Vector<String> foundRow=new Vector();
                            for(int k=0;k<modelData.getColumnCount();k++)
                            {
                                if(k==0)
                                {
                                 foundRow.add(String.valueOf(n));   
                                }
                                else
                                {
                                foundRow.add(String.valueOf(modelData.getValueAt(i, k)));
                                }
                            }
                            searchTable.addRow(foundRow);
                            locations.put(modelData.getValueAt(i, 8), modelData.getValueAt(i, 8));
                        
                         }
                }
                cboAssetLocRep.addItem("Select Location");
                for(Object loc:locations.values())
                {
                 cboAssetLocRep.addItem(loc.toString());
                }
                tbAssetsReport.removeAll();
                tbAssetsReport.setModel(searchTable);

        }
        else
        {
      
      tbAssetsReport.removeAll();
      tbAssetsReport.setModel(modelData);
        }   
    }
   public void filterLocation()
   {
        cboAssetCatRep.removeAllItems();
        cboAssetStatRep.removeAllItems();
        if(cboAssetLocRep.getItemCount()>1)
        {
          
        if(!cboAssetLocRep.getSelectedItem().equals("Select Location"))
        {
            searchTable=new DefaultTableModel(new String[]{"#","NAME OF ASSET","ASSET CODE","ASSET SN","ASSET CATEGORY","ASSET QUANTITY","ASSET STATUS","ASSET DEPARTMENT","ASSET LOCATION"}, 0)
                     {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are not editable
                return false;
            }
        };
               Map<Object,Object> categories=new HashMap();
              int n=0; 
         for(int i=0;i<modelData.getRowCount();i++)
                {  
 if((String.valueOf(modelData.getValueAt(i, 7)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetDepRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase()))
         && (String.valueOf(modelData.getValueAt(i, 8)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetLocRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase())))
                        {
                           n++; 
                            Vector<String> foundRow=new Vector();
                            for(int k=0;k<modelData.getColumnCount();k++)
                            {
                                if(k==0)
                                {
                                 foundRow.add(String.valueOf(n));   
                                }
                                else
                                {
                                foundRow.add(String.valueOf(modelData.getValueAt(i, k)));
                                }
                            }
                            searchTable.addRow(foundRow);
                            categories.put(modelData.getValueAt(i, 4), modelData.getValueAt(i, 4));
                        
                         }
                }
                cboAssetCatRep.addItem("Select Category");
                for(Object loc:categories.values())
                {
                 cboAssetCatRep.addItem(loc.toString());
                }
                tbAssetsReport.removeAll();
                tbAssetsReport.setModel(searchTable);

            

        }
        else
        {
      filterDepartment();
        }
}
   }
    public void filterCategory()
   {
        cboAssetStatRep.removeAllItems();
        if(cboAssetCatRep.getItemCount()>1)
        {
        if(!cboAssetCatRep.getSelectedItem().equals("Select Category"))
        {
            searchTable=new DefaultTableModel(new String[]{"#","NAME OF ASSET","ASSET CODE","ASSET SN","ASSET CATEGORY","ASSET QUANTITY","ASSET STATUS","ASSET DEPARTMENT","ASSET LOCATION"}, 0)
                     {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are not editable
                return false;
            }
        };
               Map<Object,Object> status=new HashMap();
               int n=0;
         for(int i=0;i<modelData.getRowCount();i++)
                {  
if((String.valueOf(modelData.getValueAt(i, 7)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetDepRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase()))
         && (String.valueOf(modelData.getValueAt(i, 8)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetLocRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase()))
        && (String.valueOf(modelData.getValueAt(i, 4)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetCatRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase())))
                        {
                            n++;
                           Vector<String> foundRow=new Vector();
                            for(int k=0;k<modelData.getColumnCount();k++)
                            {
                                if(k==0)
                                {
                                 foundRow.add(String.valueOf(n));   
                                }
                                else
                                {
                                foundRow.add(String.valueOf(modelData.getValueAt(i, k)));
                                }
                            }
                            searchTable.addRow(foundRow);
                            status.put(modelData.getValueAt(i, 6), modelData.getValueAt(i, 6));
                        
                         }

                }
                cboAssetStatRep.addItem("Select Status");
                for(Object loc:status.values())
                {
                 cboAssetStatRep.addItem(loc.toString());
                }
                tbAssetsReport.removeAll();
                tbAssetsReport.setModel(searchTable);

            

        }
        else
        {
      filterLocation();
        }
}
   }
     public void filterStatus()
   {
        if(cboAssetStatRep.getItemCount()>1)
        {
        if(!cboAssetStatRep.getSelectedItem().equals("Select Status"))
        {
        searchTable=new DefaultTableModel(new String[]{"#","NAME OF ASSET","ASSET CODE","ASSET SN","ASSET CATEGORY","ASSET QUANTITY","ASSET STATUS","ASSET DEPARTMENT","ASSET LOCATION"}, 0)
                 {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are not editable
                return false;
            }
        };
        int n=0; 
        for(int i=0;i<modelData.getRowCount();i++)
                {  
if((String.valueOf(modelData.getValueAt(i, 7)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetDepRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase()))
         && (String.valueOf(modelData.getValueAt(i, 8)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetLocRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase()))
        && (String.valueOf(modelData.getValueAt(i, 4)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetCatRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase()))
        && (String.valueOf(modelData.getValueAt(i, 6)).replaceAll("\\s", "").toLowerCase()).equals((String.valueOf(cboAssetStatRep.getSelectedItem()).replaceAll("\\s", "").toLowerCase())))
                        {
                            n++;
                           Vector<String> foundRow=new Vector();
                            for(int k=0;k<modelData.getColumnCount();k++)
                            {
                                if(k==0)
                                {
                                 foundRow.add(String.valueOf(n));   
                                }
                                else
                                {
                                foundRow.add(String.valueOf(modelData.getValueAt(i, k)));
                                }
                            }
                            searchTable.addRow(foundRow);
                           
                        
                         }
                }
                
                tbAssetsReport.removeAll();
                tbAssetsReport.setModel(searchTable);
  }
        else
        {
      filterCategory();
        }
}
   }
     public void generatePDFReport() throws DocumentException, FileNotFoundException, BadElementException, IOException
     {
       Document document = new Document(PageSize.A4.rotate());
       String fileName = JOptionPane.showInputDialog(null, "Enter filename:", "");
       JFileChooser file=new JFileChooser();
       file.setSelectedFile(new File(fileName+".pdf"));
       file.showSaveDialog(null);
        PdfWriter.getInstance(document, new FileOutputStream(file.getSelectedFile().getParent()+"/"+fileName+".pdf"));
        document.open();
        Image image = Image.getInstance("photo/simple.jpg");
        image.setAlignment(Image.ALIGN_CENTER);
        document.add(image);
         document.add(new Paragraph(" "));
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.UNDERLINE);
        Paragraph titleParagraph = new Paragraph("INVENTORY REPORT", titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        document.add(new Paragraph(" "));
        PdfPTable pdfTable = new PdfPTable(modelData.getColumnCount());
        pdfTable.setWidthPercentage(100);
        // Add table header
        titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 11,Font.BOLD);
        for (int col = 0; col < searchTable.getColumnCount(); col++) {
            PdfPCell cell = new PdfPCell(new Paragraph(String.valueOf(searchTable.getColumnName(col)),titleFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfTable.addCell(cell);
        }
        titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 11);
        // Add table rows
        for (int row = 0; row < searchTable.getRowCount(); row++) {
            for (int col = 0; col < searchTable.getColumnCount(); col++) {
                PdfPCell cell = new PdfPCell(new Paragraph(String.valueOf(searchTable.getValueAt(row, col)),titleFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell);
            }
        }

        document.add(pdfTable);
        document.close();
        JOptionPane.showMessageDialog(null,"Report downloaded", "Download", JOptionPane.INFORMATION_MESSAGE);
     }
     public void generatePDFReportMoved() throws DocumentException, FileNotFoundException, BadElementException, IOException
     {
       Document document = new Document(PageSize.A4.rotate());
       String fileName = JOptionPane.showInputDialog(null, "Enter filename:", "");
       JFileChooser file=new JFileChooser();
       file.setSelectedFile(new File(fileName+".pdf"));
       file.showSaveDialog(null);
        PdfWriter.getInstance(document, new FileOutputStream(file.getSelectedFile().getParent()+"/"+fileName+".pdf"));
        document.open();
        Image image = Image.getInstance("photo/simple.jpg");
        image.setAlignment(Image.ALIGN_CENTER);
        document.add(image);
         document.add(new Paragraph(" "));
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.UNDERLINE);
        Paragraph titleParagraph = new Paragraph("LIST OF MOVED ASSETS", titleFont);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(titleParagraph);
        document.add(new Paragraph(" "));
        PdfPTable pdfTable = new PdfPTable(modelDataMoved.getColumnCount());
        pdfTable.setWidthPercentage(100);
        // Add table header
        titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 11,Font.BOLD);
        for (int col = 0; col < searchTableMoved.getColumnCount(); col++) {
            PdfPCell cell = new PdfPCell(new Paragraph(String.valueOf(searchTableMoved.getColumnName(col)),titleFont));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfTable.addCell(cell);
        }
        titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 11);
        // Add table rows
        for (int row = 0; row < searchTableMoved.getRowCount(); row++) {
            for (int col = 0; col < searchTableMoved.getColumnCount(); col++) {
                PdfPCell cell = new PdfPCell(new Paragraph(String.valueOf(searchTableMoved.getValueAt(row, col)),titleFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(cell);
            }
        }

        document.add(pdfTable);
        document.close();
        JOptionPane.showMessageDialog(null,"Report downloaded", "Download", JOptionPane.INFORMATION_MESSAGE);
     }
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
public void updateData(int row,String name,String code,String serialNumber,
        String category,String department,String quantity,String status,String location,String description) throws InterruptedException, ExecutionException
{
     Firestore db = FirestoreClient.getFirestore();
 // Reference to the document you want to update
        DocumentReference docRef = db.collection("AssetInfo").document(String.valueOf(searchedDocuments.get(row)));
// Data to update
        Map<String, Object> data = new HashMap<>();
        data.put("AssetCode", code);
        data.put("AssetSN", serialNumber);
        data.put("AssetName", name);
        data.put("AssetCategory", category);
        data.put("AssetQuantity", quantity);
        data.put("AssetStatus", status);
        data.put("AssetDepartment", department);
        data.put("AssetLocation", location);
        data.put("AssetDescription", description);
        data.put("AssetRegisteredBy", "Admin");
        //data.put("AssetRegisteredOn",dte.format(d) );

        // Asynchronously update the document
        ApiFuture<WriteResult> writeResult = docRef.update(data);
         JOptionPane.showMessageDialog(null, "Asset Updated Successfully");
}
public static void insertData(String name,String code,String serialNumber,
        String category,String department,String quantity,String status,String location,String description) throws IOException, InterruptedException, ExecutionException
{
   //Initialize database firebase
        
    // Get Firestore instance
    
        Firestore db = FirestoreClient.getFirestore();
        SimpleDateFormat dte=new SimpleDateFormat("dd/MM/yyyy");
        Date d=new Date();
        // Example data to be added
        Map<String, Object> data = new HashMap<>();
        data.put("AssetCode", code);
        data.put("AssetSN", serialNumber);
        data.put("AssetName", name);
        data.put("AssetCategory", category);
        data.put("AssetDepartment", department);
        data.put("AssetQuantity", quantity);
        data.put("AssetStatus", status);
        data.put("AssetLocation", location);
        data.put("AssetDescription", description);
        data.put("AssetRegisteredBy", "Admin");
        data.put("AssetRegisteredOn",dte.format(d) );
        // Add a new document with a generated ID
        DocumentReference ref = db.collection("AssetInfo").document();
        ref.set(data).get(); // .get() is optional if you want to wait for the operation to complete
        JOptionPane.showMessageDialog(null,"Asset Saved","SYSTEM INFO",JOptionPane.INFORMATION_MESSAGE);
        
}
public void removeDocumentData(int row,String reason)
{
  Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("AssetInfo").document(String.valueOf(searchedDocuments.get(row)));
       // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
          try {  
              DocumentSnapshot document = future.get();
              if (document.exists()) {
                // Document data
        Map<String, Object> data = document.getData();
        Map<String, Object> dataNew = new HashMap<>();
        SimpleDateFormat dte=new SimpleDateFormat("dd/MM/yyyy");
        Date d=new Date();
        dataNew.put("AssetCode",data.get("AssetCode"));
        dataNew.put("AssetSN", data.get("AssetSN"));
        dataNew.put("AssetName", data.get("AssetName"));
        dataNew.put("AssetCategory", data.get("AssetCategory"));
        dataNew.put("AssetDepartment", data.get("AssetDepartment"));
        dataNew.put("AssetLocation", data.get("AssetLocation"));
        dataNew.put("AssetDescription", data.get("AssetDescription"));
        dataNew.put("AssetRemovalReason", reason);
        dataNew.put("AssetRegisteredBy", "Admin");
        dataNew.put("AssetRemovedOn",dte.format(d) );
        // Add a new document with a generated ID
        DocumentReference ref = db.collection("AssetRemoved").document();
        ref.set(dataNew).get(); // .get() is optional if you want to wait for the operation to complete
        docRef.delete();
        JOptionPane.showMessageDialog(null,"Asset Removed","SYSTEM INFO",JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null,"No such document!");
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null, e);
        }
}
public void loadDocumentData(int row)
{
    Firestore db = FirestoreClient.getFirestore();
    DocumentReference docRef = db.collection("AssetInfo").document(String.valueOf(searchedDocuments.get(row)));
       // Asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
          try {
            // Get the document snapshot
            DocumentSnapshot document = future.get();
              if (document.exists()) {
                // Document data
                Map<String, Object> data = document.getData();
                txtAssetName.setText(String.valueOf(data.get("AssetName")));
                txtAssetCode.setText(String.valueOf(data.get("AssetCode")));
                txtAssetSN.setText(String.valueOf(data.get("AssetSN")));
                txtAssetCat.setText(String.valueOf(data.get("AssetCategory")));
                txtAssetQuantity.setText(String.valueOf(data.get("AssetQuantity")));
                txtAssetStatus.setText(String.valueOf(data.get("AssetStatus")));
                for(int i=0;i<cboAssetDep.getItemCount();i++)
                {
                    if((String.valueOf(cboAssetDep.getItemAt(i)).replaceAll("\\s", "")).equalsIgnoreCase(String.valueOf(data.get("AssetDepartment")).replaceAll("\\s", "")))
                    {
                        cboAssetDep.setSelectedIndex(i);
                        break;
                    }
                }
                txtAssetLoc.setText(String.valueOf(data.get("AssetLocation")));
                txtAssetDesc.setText(String.valueOf(data.get("AssetDescription")));
            } else {
                JOptionPane.showMessageDialog(null,"No such document!");
            }
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null,e);
        }
}
public void retreiveData()
{
     //Initialize database firebase
      // Get Firestore instance
      Firestore db = FirestoreClient.getFirestore();
     CollectionReference collection = db.collection("AssetInfo");
    // Asynchronously retrieve all documents
    ApiFuture<QuerySnapshot> query = collection.get();
    try {
        // Get the query results
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        tbAssets.removeAll();
        tbAssetsReport.removeAll();
        modelData=new DefaultTableModel(new String[]{"#","NAME OF ASSET","ASSET CODE","ASSET SN","ASSET CATEGORY","ASSET QUANTITY","ASSET STATUS","ASSET DEPARTMENT","ASSET LOCATION"}, 0)
                 {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are not editable
                return false;
            }
        };;
        // Iterate through the documents and display the data
        int n=1;
        retreivedDocuments=new ArrayList();
        
        for (DocumentSnapshot document : documents) 
        {
            Map<String,Object>thisDoc=document.getData();
            modelData.addRow(new Object[]{n,thisDoc.get("AssetName"),thisDoc.get("AssetCode"),thisDoc.get("AssetSN"),
            thisDoc.get("AssetCategory"),thisDoc.get("AssetQuantity"),thisDoc.get("AssetStatus"),thisDoc.get("AssetDepartment"),thisDoc.get("AssetLocation")});
            retreivedDocuments.add(document.getId());
            n++;
        }
        searchTable=modelData;
        searchedDocuments=new ArrayList();
        searchedDocuments=retreivedDocuments;
        tbAssets.setModel(modelData);
        tbAssetsReport.setModel(modelData);
        collection = db.collection("MovedAssets");
        query = collection.get();
        querySnapshot = query.get();
        documents = querySnapshot.getDocuments();
        modelDataMoved=new DefaultTableModel(new String[]{"#","NAME OF ASSET","ASSET CODE","ASSET SN",
            "ASSET STATUS","ASSET DEPARTMENT","ASSET MOVED TO","ASSET MOVED BY","ASSET MOVED ON",
            "ASSET ESTIMATED RETURN ON","ASSET RETURNED BY",
            "ASSET RETURNED ON","ASSET RETURNED IN CONDITION"}, 0)
                 {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are not editable
                return false;
            }
        };
        // Iterate through the documents and display the data
        n=1;
        retreivedMoved=new ArrayList();
        for (DocumentSnapshot document : documents) 
        {
         Map<String,Object>thisDoc=document.getData();
         DocumentReference docRefInit = db.collection("AssetInfo").document(String.valueOf(thisDoc.get("MovedAssetId")));
         // Asynchronously retrieve the document
         ApiFuture<DocumentSnapshot> futureInit = docRefInit.get();
         // Get the document snapshot
         DocumentSnapshot documentInit = futureInit.get();
         if (documentInit.exists())
         {
             Map<String, Object> dataInit = documentInit.getData();
             modelDataMoved.addRow(new Object[]{n,dataInit.get("AssetName"),dataInit.get("AssetCode"),dataInit.get("AssetSN"),
                 dataInit.get("AssetStatus"),dataInit.get("AssetDepartment"),thisDoc.get("MovedAssetToDepartment")
             ,thisDoc.get("MovedAssetBy"),thisDoc.get("MovedAssetOn"),thisDoc.get("MovedAssetEstimatedOn"),
             thisDoc.get("MovedAssetReturnedBy"),thisDoc.get("MovedAssetReturnedOn"),thisDoc.get("MovedAssetReturnedCondition")});
            retreivedMoved.add(document.getId());
             n++;
         }
    }
        searchTableMoved=modelDataMoved;
        tbAssetsReportMoved.setModel(modelDataMoved);
        //tbAssetsReport.setModel(modelData);
    }
    catch (InterruptedException | ExecutionException e) 
    {
        JOptionPane.showMessageDialog(null, e);
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

        jPanel6 = new javax.swing.JPanel();
        workPlace = new javax.swing.JTabbedPane();
        pnAssetInfo = new javax.swing.JPanel();
        pnFileToFolder = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnSaveAsset = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAssetDesc = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        txtAssetName = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtAssetCat = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        cboAssetDep = new javax.swing.JComboBox<>();
        txtAssetCode = new javax.swing.JTextField();
        txtAssetSN = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtAssetLoc = new javax.swing.JTextArea();
        btnSaveAsset1 = new javax.swing.JButton();
        btnUpdateAsset = new javax.swing.JButton();
        btnRemoveAsset = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        txtAssetQuantity = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        txtAssetStatus = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbAssets = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtSearchAsset = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        pnAssetReport = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbAssetsReport = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cboAssetStatRep = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        cboAssetLocRep = new javax.swing.JComboBox<>();
        cboAssetDepRep = new javax.swing.JComboBox<>();
        cboAssetCatRep = new javax.swing.JComboBox<>();
        btnAssetReportDownload = new javax.swing.JButton();
        pnAssetReport1 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbAssetsReportMoved = new javax.swing.JTable();
        jLabel45 = new javax.swing.JLabel();
        btnAssetReportDownloadMoved = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtSearchAssetMoved = new javax.swing.JTextField();
        btnAssetReturn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbUserNames = new javax.swing.JLabel();
        lbUserEmail = new javax.swing.JLabel();
        lbUserDep = new javax.swing.JLabel();
        lbUserTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        workPlace.setBackground(new java.awt.Color(255, 255, 255));
        workPlace.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        workPlace.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        workPlace.setPreferredSize(new java.awt.Dimension(638, 638));

        pnAssetInfo.setPreferredSize(new java.awt.Dimension(851, 573));

        pnFileToFolder.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel3.setText("Asset Code");

        btnSaveAsset.setBackground(new java.awt.Color(167, 108, 108));
        btnSaveAsset.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        btnSaveAsset.setText("Save");
        btnSaveAsset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAssetActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel15.setText("Asset description");

        txtAssetDesc.setColumns(20);
        txtAssetDesc.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetDesc.setRows(100);
        jScrollPane3.setViewportView(txtAssetDesc);

        jLabel11.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel11.setText("Asset Department");

        txtAssetName.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetNameActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel16.setText("Asset SN");

        jLabel17.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel17.setText("Asset Category");

        txtAssetCat.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetCat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetCatActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel29.setText("Asset Name");

        cboAssetDep.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        cboAssetDep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Department", "RIIO SCHOOL", "RIIO iHOSPITAL - iTechClinic", "RIIO iHOSPITAL - iCHECKS", "KIBAGABAGA Hospital" }));
        cboAssetDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboAssetDepActionPerformed(evt);
            }
        });

        txtAssetCode.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetCodeActionPerformed(evt);
            }
        });

        txtAssetSN.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetSN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetSNActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel27.setText("Asset Location");

        txtAssetLoc.setColumns(20);
        txtAssetLoc.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetLoc.setRows(100);
        jScrollPane6.setViewportView(txtAssetLoc);

        btnSaveAsset1.setBackground(new java.awt.Color(167, 108, 108));
        btnSaveAsset1.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        btnSaveAsset1.setText("Move Asset Temporary");
        btnSaveAsset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveAsset1ActionPerformed(evt);
            }
        });

        btnUpdateAsset.setBackground(new java.awt.Color(167, 108, 108));
        btnUpdateAsset.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        btnUpdateAsset.setText("Update");
        btnUpdateAsset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateAssetActionPerformed(evt);
            }
        });

        btnRemoveAsset.setBackground(new java.awt.Color(167, 108, 108));
        btnRemoveAsset.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        btnRemoveAsset.setText("Remove From List");
        btnRemoveAsset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAssetActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel28.setText("Asset Quantity");

        txtAssetQuantity.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetQuantityActionPerformed(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel42.setText("Asset Status");

        txtAssetStatus.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        txtAssetStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAssetStatusActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(83, 5, 25));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ASSET INFORMATION");
        jLabel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAssetQuantity)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                            .addComponent(jScrollPane6)
                            .addComponent(cboAssetDep, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAssetCat)
                            .addComponent(txtAssetSN)
                            .addComponent(txtAssetCode)
                            .addComponent(txtAssetName)
                            .addComponent(btnRemoveAsset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaveAsset, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnUpdateAsset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSaveAsset1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAssetStatus))))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtAssetName, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAssetCode, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtAssetSN, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(txtAssetCat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboAssetDep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAssetQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAssetStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveAsset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUpdateAsset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveAsset1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemoveAsset)
                .addGap(30, 30, 30))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tbAssets.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N
        tbAssets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAssets.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tbAssets.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAssetsMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tbAssets);

        jLabel5.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        jLabel5.setText("Search Keyword");

        txtSearchAsset.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        txtSearchAsset.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchAssetKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(83, 5, 25));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LIST OF ASSETS");
        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 926, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchAsset, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSearchAsset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnFileToFolderLayout = new javax.swing.GroupLayout(pnFileToFolder);
        pnFileToFolder.setLayout(pnFileToFolderLayout);
        pnFileToFolderLayout.setHorizontalGroup(
            pnFileToFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnFileToFolderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnFileToFolderLayout.setVerticalGroup(
            pnFileToFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnFileToFolderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnFileToFolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnAssetInfoLayout = new javax.swing.GroupLayout(pnAssetInfo);
        pnAssetInfo.setLayout(pnAssetInfoLayout);
        pnAssetInfoLayout.setHorizontalGroup(
            pnAssetInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnFileToFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnAssetInfoLayout.setVerticalGroup(
            pnAssetInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAssetInfoLayout.createSequentialGroup()
                .addComponent(pnFileToFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        workPlace.addTab("MANAGE INVENTORY", pnAssetInfo);

        pnAssetReport.setBackground(new java.awt.Color(255, 255, 255));

        tbAssetsReport.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N
        tbAssetsReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAssetsReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAssetsReportMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tbAssetsReport);

        jLabel9.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        jLabel9.setText("Asset Department");

        jLabel44.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(83, 5, 25));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("REPORT OF ASSETS");
        jLabel44.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel44.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel10.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        jLabel10.setText("Asset Status");

        jLabel12.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        jLabel12.setText("Asset Category");

        cboAssetStatRep.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        cboAssetStatRep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboAssetStatRepItemStateChanged(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        jLabel13.setText("Asset Location");

        cboAssetLocRep.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        cboAssetLocRep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboAssetLocRepItemStateChanged(evt);
            }
        });

        cboAssetDepRep.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        cboAssetDepRep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Department", "RIIO SCHOOL", "RIIO iHOSPITAL - iTechClinic", "RIIO iHOSPITAL - iChecks", "KIBAGABAGA Hospital" }));
        cboAssetDepRep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboAssetDepRepItemStateChanged(evt);
            }
        });

        cboAssetCatRep.setFont(new java.awt.Font("Calisto MT", 0, 12)); // NOI18N
        cboAssetCatRep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboAssetCatRepItemStateChanged(evt);
            }
        });

        btnAssetReportDownload.setBackground(new java.awt.Color(167, 108, 108));
        btnAssetReportDownload.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        btnAssetReportDownload.setText("Donwload");
        btnAssetReportDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssetReportDownloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnAssetReportLayout = new javax.swing.GroupLayout(pnAssetReport);
        pnAssetReport.setLayout(pnAssetReportLayout);
        pnAssetReportLayout.setHorizontalGroup(
            pnAssetReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAssetReportLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnAssetReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane9)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnAssetReportLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboAssetDepRep, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboAssetLocRep, 0, 184, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboAssetCatRep, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboAssetStatRep, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAssetReportDownload)
                        .addGap(6, 6, 6)))
                .addContainerGap())
        );
        pnAssetReportLayout.setVerticalGroup(
            pnAssetReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAssetReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnAssetReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAssetReportLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(pnAssetReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnAssetReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboAssetDepRep, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                            .addGroup(pnAssetReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboAssetLocRep, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnAssetReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboAssetStatRep, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboAssetCatRep, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnAssetReportLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAssetReportDownload)
                        .addGap(10, 10, 10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        workPlace.addTab("REPORT OF INVENTORY", pnAssetReport);

        pnAssetReport1.setBackground(new java.awt.Color(255, 255, 255));

        tbAssetsReportMoved.setFont(new java.awt.Font("Calisto MT", 0, 14)); // NOI18N
        tbAssetsReportMoved.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbAssetsReportMoved.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAssetsReportMovedMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tbAssetsReportMoved);

        jLabel45.setFont(new java.awt.Font("Bell MT", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(83, 5, 25));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("LIST OF MOVED ASSETS");
        jLabel45.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jLabel45.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnAssetReportDownloadMoved.setBackground(new java.awt.Color(167, 108, 108));
        btnAssetReportDownloadMoved.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        btnAssetReportDownloadMoved.setText("Donwload Report");
        btnAssetReportDownloadMoved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssetReportDownloadMovedActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Bell MT", 1, 14)); // NOI18N
        jLabel18.setText("Search Keyword");

        txtSearchAssetMoved.setFont(new java.awt.Font("Bell MT", 0, 14)); // NOI18N
        txtSearchAssetMoved.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchAssetMovedKeyReleased(evt);
            }
        });

        btnAssetReturn.setBackground(new java.awt.Color(167, 108, 108));
        btnAssetReturn.setFont(new java.awt.Font("Calisto MT", 1, 12)); // NOI18N
        btnAssetReturn.setText("Return Asset");
        btnAssetReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAssetReturnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnAssetReport1Layout = new javax.swing.GroupLayout(pnAssetReport1);
        pnAssetReport1.setLayout(pnAssetReport1Layout);
        pnAssetReport1Layout.setHorizontalGroup(
            pnAssetReport1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAssetReport1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnAssetReport1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAssetReport1Layout.createSequentialGroup()
                        .addGap(0, 610, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearchAssetMoved, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAssetReturn, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAssetReportDownloadMoved))
                    .addComponent(jScrollPane10)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnAssetReport1Layout.setVerticalGroup(
            pnAssetReport1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAssetReport1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnAssetReport1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnAssetReport1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(pnAssetReport1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearchAssetMoved, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnAssetReport1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAssetReturn)
                        .addComponent(btnAssetReportDownloadMoved)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addContainerGap())
        );

        workPlace.addTab("REPORT OF MOVED ASSETS", pnAssetReport1);

        jPanel2.setBackground(new java.awt.Color(190, 215, 215));

        jLabel2.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel2.setText("Names:");

        jLabel4.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel4.setText("Email:");

        jLabel6.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel6.setText("Department:");

        jLabel7.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        jLabel7.setText("Title:");

        lbUserNames.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        lbUserNames.setText("-");

        lbUserEmail.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        lbUserEmail.setText("-");

        lbUserDep.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        lbUserDep.setText("-");

        lbUserTitle.setFont(new java.awt.Font("Calisto MT", 1, 14)); // NOI18N
        lbUserTitle.setText("-");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbUserNames, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbUserDep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbUserTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(620, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbUserDep, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbUserTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbUserNames, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(workPlace, javax.swing.GroupLayout.DEFAULT_SIZE, 1316, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(workPlace, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbAssetsReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAssetsReportMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbAssetsReportMouseClicked

    private void tbAssetsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAssetsMouseClicked
        // TODO add your handling code here:
        int row=tbAssets.getSelectedRow();
        loadDocumentData(row);
    }//GEN-LAST:event_tbAssetsMouseClicked

    private void txtAssetStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetStatusActionPerformed

    private void txtAssetQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetQuantityActionPerformed

    private void btnRemoveAssetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAssetActionPerformed
        // TODO add your handling code here:

        String m=JOptionPane.showInputDialog(null,"Reason To Remove The Asset", "Reason",  JOptionPane.WARNING_MESSAGE);
        if(m!=null && !m.isEmpty())
        {
            int row=tbAssets.getSelectedRow();
            removeDocumentData(row,m);
            retreiveData();
            txtAssetName.setText("");
            txtAssetCode.setText("");
            txtAssetSN.setText("");
            txtAssetCat.setText("");
            txtAssetLoc.setText("");
            cboAssetDep.setSelectedIndex(0);
            txtAssetDesc.setText("");
        }
    }//GEN-LAST:event_btnRemoveAssetActionPerformed

    private void btnUpdateAssetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateAssetActionPerformed
        // TODO add your handling code here:
        String name=txtAssetName.getText();
        String code=txtAssetCode.getText();
        String serialNumber=txtAssetSN.getText();
        String category=txtAssetCat.getText();
        String quantity=txtAssetQuantity.getText();
        String status=txtAssetStatus.getText();
        String location=txtAssetLoc.getText();
        String department=String.valueOf(cboAssetDep.getSelectedItem());
        String description=txtAssetDesc.getText();
        int row=tbAssets.getSelectedRow();
        try {
            updateData(row,name,code,serialNumber,
                category,department,quantity,status,location,description);
            retreiveData();

            txtAssetName.setText("");
            txtAssetCode.setText("");
            txtAssetSN.setText("");
            txtAssetCat.setText("");
            txtAssetLoc.setText("");
            cboAssetDep.setSelectedIndex(0);
            txtAssetDesc.setText("");
        } catch (InterruptedException | ExecutionException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnUpdateAssetActionPerformed

    private void btnSaveAsset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAsset1ActionPerformed
        // TODO add your handling code here:
        
    try {
        int row=tbAssets.getSelectedRow();
        
        MoveAsset move;
        move = new MoveAsset(searchedDocuments.get(row));
        move.setVisible(true);
    } catch (IOException ex) {
        JOptionPane.showMessageDialog( null, ex);
    }
        
    }//GEN-LAST:event_btnSaveAsset1ActionPerformed

    private void txtAssetSNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetSNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetSNActionPerformed

    private void txtAssetCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetCodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetCodeActionPerformed

    private void cboAssetDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboAssetDepActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboAssetDepActionPerformed

    private void txtAssetCatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetCatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetCatActionPerformed

    private void txtAssetNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAssetNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAssetNameActionPerformed

    private void btnSaveAssetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveAssetActionPerformed
        String name=txtAssetName.getText();
        String code=txtAssetCode.getText();
        String serialNumber=txtAssetSN.getText();
        String category=txtAssetCat.getText();
        String location=txtAssetLoc.getText();
        String quantity=txtAssetQuantity.getText();
        String status=txtAssetStatus.getText();
        String department=String.valueOf(cboAssetDep.getSelectedItem());
        String description=txtAssetDesc.getText();
        try {
            insertData(name,code,serialNumber,
                category,department,quantity,status,location,description);
            retreiveData();
            txtAssetName.setText("");
            txtAssetCode.setText("");
            txtAssetSN.setText("");
            txtAssetCat.setText("");
            txtAssetQuantity.setText("");
            txtAssetStatus.setText("");
            txtAssetLoc.setText("");
            cboAssetDep.setSelectedIndex(0);
            txtAssetDesc.setText("");
        } catch (IOException | InterruptedException | ExecutionException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
    }//GEN-LAST:event_btnSaveAssetActionPerformed

    private void cboAssetDepRepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboAssetDepRepItemStateChanged
        // TODO add your handling code here:
       filterDepartment();
    }//GEN-LAST:event_cboAssetDepRepItemStateChanged

    private void cboAssetLocRepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboAssetLocRepItemStateChanged
        // TODO add your handling code here:
        if(cboAssetLocRep.getItemCount()>1)
        {
       filterLocation();
        }
    }//GEN-LAST:event_cboAssetLocRepItemStateChanged

    private void cboAssetCatRepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboAssetCatRepItemStateChanged
        // TODO add your handling code here:
        if(cboAssetCatRep.getItemCount()>1)
        {
       filterCategory();
        }
    }//GEN-LAST:event_cboAssetCatRepItemStateChanged

    private void cboAssetStatRepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboAssetStatRepItemStateChanged
        // TODO add your handling code here:
        if(cboAssetStatRep.getItemCount()>1)
        {
       filterStatus();
        }
    }//GEN-LAST:event_cboAssetStatRepItemStateChanged

    private void btnAssetReportDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssetReportDownloadActionPerformed
    try {
        // TODO add your handling code here:
        generatePDFReport();
    } catch (DocumentException | FileNotFoundException ex) {
       JOptionPane.showMessageDialog( null, ex);
    } catch (IOException ex) {
        JOptionPane.showMessageDialog( null, ex);
    } 
    }//GEN-LAST:event_btnAssetReportDownloadActionPerformed

    private void tbAssetsReportMovedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAssetsReportMovedMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbAssetsReportMovedMouseClicked

    private void btnAssetReportDownloadMovedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssetReportDownloadMovedActionPerformed
        // TODO add your handling code here:
        try {
        // TODO add your handling code here:
        generatePDFReportMoved();
    } catch (DocumentException | FileNotFoundException ex) {
       JOptionPane.showMessageDialog( null, ex);
    } catch (IOException ex) {
        JOptionPane.showMessageDialog( null, ex);
    } 
    }//GEN-LAST:event_btnAssetReportDownloadMovedActionPerformed

    private void btnAssetReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAssetReturnActionPerformed
        // TODO add your handling code here:
         try 
         {
        int row=tbAssetsReportMoved.getSelectedRow();
        ReturnAsset returned;
        returned = new ReturnAsset(retreivedMoved.get(row));
        returned.setVisible(true);
         } 
        catch (IOException ex) {
        JOptionPane.showMessageDialog( null, ex);
    }
    }//GEN-LAST:event_btnAssetReturnActionPerformed

    private void txtSearchAssetMovedKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchAssetMovedKeyReleased
        // TODO add your handling code here:
         String searchKey=txtSearchAssetMoved.getText();
         int n=0;
        if(txtSearchAssetMoved.getText().trim().length()>0)
        {
            if(modelDataMoved.getRowCount()>0)
            {
                //JOptionPane.showMessageDialog(null, model.getRowCount());
                searchTableMoved=new DefaultTableModel(new String[]{"#","NAME OF ASSET","ASSET CODE","ASSET SN",
            "ASSET STATUS","ASSET DEPARTMENT","ASSET MOVED TO","ASSET MOVED BY","ASSET MOVED ON",
            "ASSET ESTIMATED RETURN ON","ASSET RETURNED BY",
            "ASSET RETURNED ON","ASSET RETURNED IN CONDITION"}, 0)
                         {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are not editable
                return false;
            }
        };
                for(int i=0;i<modelDataMoved.getRowCount();i++)
                {
                    for(int j=0;j<modelDataMoved.getColumnCount();j++)
                    {
                        if(String.valueOf(modelDataMoved.getValueAt(i, j)).toLowerCase().contains(searchKey.toLowerCase()))
                        {
                             n++;
                           Vector<String> foundRow=new Vector();
                            for(int k=0;k<modelDataMoved.getColumnCount();k++)
                            {
                                if(k==0)
                                {
                                 foundRow.add(String.valueOf(n));   
                                }
                                else
                                {
                                foundRow.add(String.valueOf(modelDataMoved.getValueAt(i, k)));
                                }
                            }
                            searchTableMoved.addRow(foundRow);
                            break;
                        }
                    }
                }
                tbAssetsReportMoved.removeAll();
                tbAssetsReportMoved.setModel(searchTableMoved);
            }
        }
        else
        {
            tbAssetsReportMoved.removeAll();
            tbAssetsReportMoved.setModel(modelDataMoved);
        }
    }//GEN-LAST:event_txtSearchAssetMovedKeyReleased

    private void txtSearchAssetKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchAssetKeyReleased
        // TODO add your handling code here:
          String searchKey=txtSearchAsset.getText();
          
         // searchedDocuments=retreivedDocuments;
          int n=0;
        if(txtSearchAsset.getText().trim().length()>0)
        {
            if(modelData.getRowCount()>0)
            {
                //JOptionPane.showMessageDialog(null, model.getRowCount());
                searchTable=new DefaultTableModel(new String[]{"#","NAME OF ASSET","ASSET CODE","ASSET SN","ASSET CATEGORY","ASSET DEPARTMENT","ASSET LOCATION"}, 0)
                 {
            @Override
            public boolean isCellEditable(int row, int column) {
                // All cells are not editable
                return false;
            }
        };
                 searchedDocuments=new ArrayList();
                for(int i=0;i<modelData.getRowCount();i++)
                {
                    for(int j=0;j<modelData.getColumnCount();j++)
                    {
                        if(String.valueOf(modelData.getValueAt(i, j)).toLowerCase().contains(searchKey.toLowerCase()))
                        {
                             n++;
                           Vector<String> foundRow=new Vector();
                            for(int k=0;k<modelData.getColumnCount();k++)
                            {
                                if(k==0)
                                {
                                 foundRow.add(String.valueOf(n));   
                                }
                                else
                                {
                                foundRow.add(String.valueOf(modelData.getValueAt(i, k)));
                                }
                            }
                            searchTable.addRow(foundRow);
                            searchedDocuments.add(retreivedDocuments.get(i));
                            break;
                        }
                    }
                }
                tbAssets.removeAll();
                tbAssets.setModel(searchTable);
            }
        }
        else
        {
            tbAssets.removeAll();
            tbAssets.setModel(modelData);
        }
    }//GEN-LAST:event_txtSearchAssetKeyReleased

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
            java.util.logging.Logger.getLogger(AssetsHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AssetsHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AssetsHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AssetsHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                    new AssetsHome().setVisible(true);
                
            }
        });
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the retrieveData method to run every 5 seconds
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
              
                    AssetsHome h=new AssetsHome();
                   h.retreiveData();
            }
        }, 0, 120, TimeUnit.SECONDS);

        // Add a shutdown hook to properly terminate the scheduler
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssetReportDownload;
    private javax.swing.JButton btnAssetReportDownloadMoved;
    private javax.swing.JButton btnAssetReturn;
    private javax.swing.JButton btnRemoveAsset;
    private javax.swing.JButton btnSaveAsset;
    private javax.swing.JButton btnSaveAsset1;
    private javax.swing.JButton btnUpdateAsset;
    private javax.swing.JComboBox<String> cboAssetCatRep;
    private javax.swing.JComboBox<String> cboAssetDep;
    private javax.swing.JComboBox<String> cboAssetDepRep;
    private javax.swing.JComboBox<String> cboAssetLocRep;
    private javax.swing.JComboBox<String> cboAssetStatRep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lbUserDep;
    private javax.swing.JLabel lbUserEmail;
    private javax.swing.JLabel lbUserNames;
    private javax.swing.JLabel lbUserTitle;
    private javax.swing.JPanel pnAssetInfo;
    private javax.swing.JPanel pnAssetReport;
    private javax.swing.JPanel pnAssetReport1;
    private javax.swing.JPanel pnFileToFolder;
    private javax.swing.JTable tbAssets;
    private javax.swing.JTable tbAssetsReport;
    private javax.swing.JTable tbAssetsReportMoved;
    private javax.swing.JTextField txtAssetCat;
    private javax.swing.JTextField txtAssetCode;
    private javax.swing.JTextArea txtAssetDesc;
    private javax.swing.JTextArea txtAssetLoc;
    private javax.swing.JTextField txtAssetName;
    private javax.swing.JTextField txtAssetQuantity;
    private javax.swing.JTextField txtAssetSN;
    private javax.swing.JTextField txtAssetStatus;
    private javax.swing.JTextField txtSearchAsset;
    private javax.swing.JTextField txtSearchAssetMoved;
    private javax.swing.JTabbedPane workPlace;
    // End of variables declaration//GEN-END:variables
}
