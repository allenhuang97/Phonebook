import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

//import org.jdesktop.xswingx.PromptSupport;
//import org.jdesktop.xswingx.PromptSupport.FocusBehavior;

public class Phonebook extends JFrame implements ActionListener{
  //creating various GUI objects
  private static JPanel contentPane;
  private static JTable tablePhoneBook;
  private static JTextField txtFldFirstName;
  private static JTextField txtFldLastName;
  private static JTextField txtFldHomeNumber;
  private static JTextField txtFldMobileNumber;
  private static JTextField txtFldAddress;
  private static JTextField txtFldCity;
  private static JTextField txtFldPostalCode;
  private static JTextField txtFldSearch;
  private static JComboBox cbxGender;
  private static JComboBox cbxProvince;
  private static JComboBox cbxSearchType;
  private static JButton btnSearch;
  private static JComboBox cbxGenderSearch;
  private static JComboBox cbxProvinceSearch;
  
  private static JButton btnDelete = new JButton("Delete");
  private static JButton btnEdit = new JButton("Edit");
  private static JButton btnAdd = new JButton("Add");
  private static JButton btnSave = new JButton("Save");
  
  static boolean validEdits = false;
  static boolean editing = false;
  static int editNum;
  static int deleteNum;
  static int entryNum = 0;
  static int searchResultsFound = 0;
  static String fileName = "phoneBook.txt";
  static String [] phoneBookHeaders = new String[11];
  static String [][] phoneBook = new String [entryNum][11];
  static boolean [] searchFound = new boolean [entryNum];
  static boolean initialized = false;
  static String tempData[];
  static String inputText;
  private static JTextField txtFldEmail;
  
  /** 
   * Launch the application.
   */
  public static void main(String[] args){
    phoneBookHeaders();  
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Phonebook frame = new Phonebook();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    inputFromTextFile();  
  }
  
  /**
   * Create the frame.
   */
  public Phonebook() {
    //creating content pane
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1326, 656);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    //creating new Jpanel. Containing entry form
    JPanel panel = new JPanel();
    panel.setBounds(10, 11, 295, 539);
    panel.setBorder(new TitledBorder(null, "Data Entry", TitledBorder.LEADING, TitledBorder.TOP, null, null));
    contentPane.add(panel);
    panel.setLayout(null); 
    
    //creating various labels. To inform users the intended use of the textfield next to it
    JLabel lblFirstName = new JLabel("First Name:");
    lblFirstName.setBounds(26, 35, 131, 14);
    panel.add(lblFirstName);
    
    JLabel lblLastName = new JLabel("Last Name:");
    lblLastName.setBounds(26, 75, 120, 14);
    panel.add(lblLastName);
    
    JLabel lblHomeNumber = new JLabel("Home Number:");
    lblHomeNumber.setBounds(26, 115, 131, 14);
    panel.add(lblHomeNumber);
    
    JLabel lblGender = new JLabel("Gender:");
    lblGender.setBounds(26, 405, 46, 14);
    panel.add(lblGender);
    
    JLabel lblMobileNumber = new JLabel("Mobile Number:");
    lblMobileNumber.setBounds(26, 155, 131, 14);
    panel.add(lblMobileNumber);
    
    JLabel lblCity = new JLabel("City:");
    lblCity.setBounds(26, 285, 131, 14);
    panel.add(lblCity);
    
    JLabel lblAddress = new JLabel("Address:");
    lblAddress.setBounds(26, 245, 131, 14);
    panel.add(lblAddress);
    
    JLabel lblPostalCode = new JLabel("Postal Code:");
    lblPostalCode.setBounds(26, 325, 131, 14);
    panel.add(lblPostalCode);
    
    JLabel lblProvince = new JLabel("Province:");
    lblProvince.setBounds(26, 365, 85, 14);
    panel.add(lblProvince);
    
    JLabel lblEmail = new JLabel("E-Mail:");
    lblEmail.setBounds(26, 199, 46, 14);
    panel.add(lblEmail);
    
    //creating various text fields. for user entry. To input into the phone book
    txtFldFirstName = new JTextField();
    txtFldFirstName.setBounds(121, 32, 140, 20);
    panel.add(txtFldFirstName);
    txtFldFirstName.setColumns(10);
    PromptSupport.setPrompt("First Name",txtFldFirstName);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, txtFldFirstName);
    
    txtFldLastName = new JTextField();
    txtFldLastName.setBounds(121, 72, 140, 20);
    panel.add(txtFldLastName);
    txtFldLastName.setColumns(10);
    PromptSupport.setPrompt("Last Name",txtFldLastName);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, txtFldLastName);
    
    txtFldHomeNumber = new JTextField();
    txtFldHomeNumber.setBounds(121, 112, 140, 20);
    panel.add(txtFldHomeNumber);
    txtFldHomeNumber.setColumns(10);
    PromptSupport.setPrompt("Phone Number",txtFldHomeNumber);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, txtFldHomeNumber);
    
    txtFldMobileNumber = new JTextField();
    txtFldMobileNumber.setColumns(10);
    txtFldMobileNumber.setBounds(121, 152, 140, 20);
    panel.add(txtFldMobileNumber);
    PromptSupport.setPrompt("Mobile Number",txtFldMobileNumber);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, txtFldMobileNumber); 
    
    txtFldAddress = new JTextField();
    txtFldAddress.setColumns(10);
    txtFldAddress.setBounds(121, 242, 140, 20);
    panel.add(txtFldAddress);
    PromptSupport.setPrompt("Address",txtFldAddress);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, txtFldAddress);  
    
    txtFldCity = new JTextField();
    txtFldCity.setColumns(10);
    txtFldCity.setBounds(121, 282, 140, 20);
    panel.add(txtFldCity);
    PromptSupport.setPrompt("City",txtFldCity);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, txtFldCity); 
    
    txtFldPostalCode = new JTextField();
    txtFldPostalCode.setColumns(10);
    txtFldPostalCode.setBounds(121, 325, 140, 20);
    panel.add(txtFldPostalCode);
    PromptSupport.setPrompt("Postal Code",txtFldPostalCode);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, txtFldPostalCode); 
    
    txtFldSearch = new JTextField();
    txtFldSearch.setBounds(655, 523, 199, 20);
    contentPane.add(txtFldSearch);
    txtFldSearch.setColumns(10);
    PromptSupport.setPrompt("Search",txtFldSearch);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, txtFldSearch); 
    txtFldSearch.setEnabled(false);
    
    txtFldEmail = new JTextField();
    txtFldEmail.setBounds(121, 196, 140, 20);
    panel.add(txtFldEmail);
    txtFldEmail.setColumns(10);
    PromptSupport.setPrompt("E-Mail",txtFldEmail);
    PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, txtFldEmail); 
    
    //creating combo box for gender input and province
    cbxGender = new JComboBox();
    cbxGender.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
    cbxGender.setBounds(85, 405, 85, 20);
    panel.add(cbxGender);
    
    cbxProvince = new JComboBox();
    cbxProvince.setFont(new Font("Tahoma", Font.PLAIN, 11));
    cbxProvince.setModel(new DefaultComboBoxModel(new String[] {"Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Nova Scotia", "Northwest Territories", "Nunavut", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan", "Yukon"}));
    cbxProvince.setBounds(121, 365, 140, 20);
    panel.add(cbxProvince);
    
    cbxGenderSearch = new JComboBox();
    cbxGenderSearch.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female"}));
    cbxGenderSearch.setBounds(655, 523, 112, 20);
    contentPane.add(cbxGenderSearch);
    cbxGenderSearch.setVisible(false);
    cbxGenderSearch.setSelectedIndex(-1);
    
    cbxProvinceSearch = new JComboBox();
    cbxProvinceSearch.setModel(new DefaultComboBoxModel(new String[] {"Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Nova Scotia", "Northwest Territories", "Nunavut", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan", "Yukon"}));
    cbxProvinceSearch.setSelectedIndex(-1);
    cbxProvinceSearch.setFont(new Font("Tahoma", Font.PLAIN, 11));
    cbxProvinceSearch.setBounds(655, 523, 140, 20);
    contentPane.add(cbxProvinceSearch);
    cbxProvinceSearch.setVisible(false);
    cbxProvinceSearch.setSelectedIndex(-1);
    
    cbxSearchType = new JComboBox();
    cbxSearchType.setModel(new DefaultComboBoxModel(new String[] {"Entry Number", "First Name", "Last Name", "Home Number", "Mobile Number", "E-Mail", "Address", "City", "Postal Code", "Province", "Gender"}));
    cbxSearchType.setBounds(526, 523, 119, 20);
    contentPane.add(cbxSearchType);
    cbxSearchType.setSelectedIndex(-1);
    cbxSearchType.addActionListener(this);
    cbxSearchType.setActionCommand("Search Select");
    
    //creating add button. To allow user to add their entry/contact
    btnAdd.setToolTipText("Add New Entry");
    btnAdd.setBounds(44, 446, 90, 28);
    panel.add(btnAdd);
    //btnAdd.setEnabled(true);
    //when the button is pressed set ActionCommand to "Add"
    btnAdd.addActionListener(this);
    btnAdd.setActionCommand("Add");
    
    //creating delete button. To allow user to delete an entry
    btnDelete.setToolTipText("Delete Entry");
    btnDelete.setBounds(43, 493, 90, 28);
    panel.add(btnDelete);
    //when the button is pressed set ActionCommand to "Delete"
    btnDelete.addActionListener(this);
    btnDelete.setActionCommand("Delete");
    
    //creating edit button. To allow user to edit the contact that they select
    btnEdit.setToolTipText("Edit Entry");
    btnEdit.setBounds(171, 446, 90, 28);
    panel.add(btnEdit);
    //when the button is pressed set ActionCommand to "Delete"
    btnEdit.addActionListener(this);
    btnEdit.setActionCommand("Edit");
    
    //creating save button. To allow user to save their edited contact entries
    btnSave.setToolTipText("Save Changes");
    btnSave.setBounds(170, 493, 90, 28);
    panel.add(btnSave);
    btnSave.setEnabled(false);
    //when the button is pressed set ActionCommand to "Delete"
    btnSave.addActionListener(this);
    btnSave.setActionCommand("Save");
    
    //creating exit button. To allow user to exit the program
    JButton btnExit = new JButton("Exit");
    btnExit.setBounds(106, 560, 90, 28);
    contentPane.add(btnExit);
    btnExit.setToolTipText("Exit Phonebook");
    //when the button is pressed set ActionCommand to "Exit"
    btnExit.addActionListener(this);
    btnExit.setActionCommand("Exit");
    
    //creating view original button. To allow user to view the phonebook without the search restrictions
    JButton btnViewOriginal = new JButton("View Original");
    btnViewOriginal.setBounds(1047, 522, 140, 23);
    btnViewOriginal.setToolTipText("Clear Search Restrictions");
    contentPane.add(btnViewOriginal);
    btnViewOriginal.addActionListener(this);
    btnViewOriginal.setActionCommand("View Original");
    
    btnSearch = new JButton("Search");
    btnSearch.setBounds(864, 522, 89, 23);
    contentPane.add(btnSearch);
    btnSearch.setToolTipText("Search Contacts");
    btnSearch.addActionListener(this);
    btnSearch.setActionCommand("Search");
    btnSearch.setEnabled(false);
    
    //creating new JTable to display the phonebook and setting it to non-editable
    tablePhoneBook = new JTable(phoneBook,phoneBookHeaders);
    tablePhoneBook.getColumnModel().getColumn(0).setPreferredWidth(92);
    tablePhoneBook.getColumnModel().getColumn(1).setPreferredWidth(114);
    tablePhoneBook.getColumnModel().getColumn(2).setPreferredWidth(172);
    tablePhoneBook.getColumnModel().getColumn(3).setPreferredWidth(169);
    tablePhoneBook.getColumnModel().getColumn(4).setPreferredWidth(293);
    tablePhoneBook.getColumnModel().getColumn(5).setPreferredWidth(139);
    tablePhoneBook.getColumnModel().getColumn(7).setPreferredWidth(53);
    tablePhoneBook.getColumnModel().getColumn(8).setPreferredWidth(51);
    tablePhoneBook.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    tablePhoneBook.getTableHeader().setReorderingAllowed(false);
    //creating scrollPane with Jtable and scroll bars within it
    JScrollPane scrollPane = new JScrollPane(tablePhoneBook, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setBounds(343, 40, 940, 468);
    contentPane.add(scrollPane);
    clearEntryData();
  }
  
  //actionPerformed method. To read and act appropriately when buttons are pressed
  public void actionPerformed(ActionEvent evt) {
    //if the exit button is pressed. Show a confirm. Then exit the program
    if(evt.getActionCommand().equals("Exit")){
      int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
      if(exit == 0)
        System.exit(0);
    }
    
    //if the add button is pressed.
    if(evt.getActionCommand().equals("Add")){
      //if the information in the contact form is not filled. Alert the user to complete the form.
      if((txtFldFirstName.getText().equals(""))&&(txtFldLastName.getText().equals(""))||(txtFldHomeNumber.getText().equals(""))&&(txtFldMobileNumber.getText().equals(""))){
        JOptionPane.showMessageDialog(null, "The Entry is Not Complete"); 
      }
      else{
        entryTest();
      }
    }
    if(evt.getActionCommand().equals("Search Select")){
      btnSearch.setEnabled(true);
      if((cbxSearchType.getSelectedIndex() >=0)&&cbxSearchType.getSelectedIndex()<9){
        txtFldSearch.setEnabled(true);
        txtFldSearch.setVisible(true);
        cbxGenderSearch.setVisible(false);
        cbxProvinceSearch.setVisible(false);
      }
      if(cbxSearchType.getSelectedIndex() == 9){
        cbxProvinceSearch.setSelectedIndex(-1);
        cbxProvinceSearch.setVisible(true);
        txtFldSearch.setEnabled(false);
        txtFldSearch.setVisible(false);
        cbxGenderSearch.setVisible(false);
      }
      if(cbxSearchType.getSelectedIndex() == 10){
        cbxGenderSearch.setSelectedIndex(-1);
        cbxGenderSearch.setVisible(true);
        txtFldSearch.setEnabled(false);
        txtFldSearch.setVisible(false);
        cbxProvinceSearch.setVisible(false);
      }
    }
    
    if(evt.getActionCommand().equals("Search")){
      clearSearchFound();
      searchPhonebook();
    }
    
    //if the delete is pressed. Confirm then delete the contact.
    if(evt.getActionCommand().equals("Delete")){
      boolean entryValid = false;
      try {
        deleteNum = Integer.parseInt(JOptionPane.showInputDialog("Please enter the entry number you would like to delete: "));
        for (int i = 0; i < phoneBook.length; i++) {
          if (Integer.parseInt(phoneBook[i][0]) == deleteNum) {
            entryValid = true;
          }
        }
        if (entryValid == true) {
          int delete = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this entry?");
          if(delete == 0) {
            deleteEntryData();
          }
        }
        else {
          JOptionPane.showMessageDialog(null, "Your entry number was not found."); 
        }
      }
      catch (NumberFormatException e) {
        
      }
    }
    if(evt.getActionCommand().equals("View Original")){
      displayPhoneBook();
    }
    
    if(evt.getActionCommand().equals("Edit")) {
      boolean entryValid = false;
      validEdits = false;
      editing = true;
      btnEdit.setEnabled(false);
      try {
        editNum = Integer.parseInt(JOptionPane.showInputDialog("Please enter the entry number you would like to edit: "));
        for (int i = 0; i < phoneBook.length; i++) {
          if (Integer.parseInt(phoneBook[i][0]) == editNum) {
            entryValid = true;
          }
        }
        if (entryValid == true) {
          btnAdd.setEnabled(false);
          editEntryData();
        }
        else {
          JOptionPane.showMessageDialog(null, "Your entry number was not found."); 
        }
      }
      catch (NumberFormatException e) {
        btnEdit.setEnabled(true);
        editing = false;
      }
    }
    if(evt.getActionCommand().equals("Save")) {
      entryTest();
      if (validEdits == true) {
        phoneBook[entryNum-1][0] = Integer.toString(entryNum);
        if(txtFldFirstName.getText().equals(""))
          phoneBook[entryNum-1][1] = " ";
        else
          phoneBook[entryNum-1][1] = txtFldFirstName.getText();
        
        if(txtFldLastName.getText().equals(""))
          phoneBook[entryNum-1][2] = " ";
        else
          phoneBook[entryNum-1][2] = txtFldLastName.getText();
        
        if(txtFldHomeNumber.getText().equals(""))
          phoneBook[entryNum-1][3] = " ";
        else
          phoneBook[entryNum-1][3] = txtFldHomeNumber.getText();
        
        if(txtFldMobileNumber.getText().equals(""))
          phoneBook[entryNum-1][4] = " ";
        else
          phoneBook[entryNum-1][4] = txtFldMobileNumber.getText();
        
        if(txtFldEmail.getText().equals(""))
          phoneBook[entryNum-1][5] = " ";
        else
          phoneBook[entryNum-1][5] = txtFldEmail.getText();
        
        if(txtFldAddress.getText().equals(""))
          phoneBook[entryNum-1][6] = " ";
        else
          phoneBook[entryNum-1][6] = txtFldAddress.getText();
        
        if(txtFldCity.getText().equals(""))
          phoneBook[entryNum-1][7] = " ";
        else
          phoneBook[entryNum-1][7] = txtFldCity.getText();
        
        if(txtFldPostalCode.getText().equals(""))
          phoneBook[entryNum-1][8] = " ";
        else
          phoneBook[entryNum-1][8] = txtFldPostalCode.getText();
        
        if(cbxProvince.getSelectedItem()==(null))
          phoneBook[entryNum-1][9] = " ";
        else
          phoneBook[entryNum-1][9] = (String) cbxProvince.getSelectedItem();
        
        if(cbxGender.getSelectedItem()==(null))
          phoneBook[entryNum-1][10] = " ";
        else
          phoneBook[entryNum-1][10] = (String) cbxGender.getSelectedItem();
        editing = false;
        btnSave.setEnabled(false);
        btnAdd.setEnabled(true);
        if(!phoneBook[entryNum-1][3].equals(" ")){
          String tempHomeNum = ("(" + phoneBook[entryNum-1][3].substring(0, 3) + ") " + phoneBook[entryNum-1][3].substring(3,6) + "-" + phoneBook[entryNum-1][3].substring(6,10));
          phoneBook[entryNum-1][3] = tempHomeNum;
        }
        if(!phoneBook[entryNum-1][4].equals(" ")){
          String tempMobileNum = ("(" + phoneBook[entryNum-1][4].substring(0, 3) + ") " + phoneBook[entryNum-1][4].substring(3,6) + "-" + phoneBook[entryNum-1][4].substring(6,10));
          phoneBook[entryNum-1][4] = tempMobileNum;
        }
        outputToTextFile();
        displayPhoneBook();
        btnEdit.setEnabled(true);
      }
    }
  }
  
  //phoneBookHeaders method. used to initialize the phoneBookHeades Array.
  static void phoneBookHeaders(){
    phoneBookHeaders[0]=("#");
    phoneBookHeaders[1]=("First Name");
    phoneBookHeaders[2]=("Last Name");
    phoneBookHeaders[3]=("Home Number");
    phoneBookHeaders[4]=("Mobile Number");
    phoneBookHeaders[5]=("E-Mail");
    phoneBookHeaders[6]=("Address");
    phoneBookHeaders[7]=("City");
    phoneBookHeaders[8]=("Postal Code");
    phoneBookHeaders[9]=("Province");
    phoneBookHeaders[10]=("Gender");
  }//end of phoneBookHeaders method
  
  //entryTest method. used to test if entries meet the criteria for their category. For example: is the phone number all numbers. is that name all letters.
  static void entryTest() {
    //if they entered data. doesn't meet the criteria. Show a dialog message informing the user of their mistake.
    try{
      int temp = Integer.parseInt(txtFldFirstName.getText());
      JOptionPane.showMessageDialog(null, "The First Name must only contain letters");
      if (editing == true) {
        validEdits = false;
      }
    }
    catch(NumberFormatException nfe) {
      try{
        int temp = Integer.parseInt(txtFldLastName.getText());
        JOptionPane.showMessageDialog(null, "The Last Name must only contain letters");
        if (editing == true) {
          validEdits = false;
        }
      }
      catch(NumberFormatException nfe1) {
        try{
          int temp = Integer.parseInt(txtFldCity.getText());
          JOptionPane.showMessageDialog(null, "The City must only contain letters");
          if (editing == true) {
            validEdits = false;
          }
        }
        catch(NumberFormatException nfe2) {
          try{
            if(!txtFldHomeNumber.getText().equals(" ")){
              if((Long.parseLong(txtFldHomeNumber.getText())/1000000000 !=0)){
                if(!txtFldMobileNumber.getText().equals(" ")){
                  if((Long.parseLong(txtFldMobileNumber.getText())/1000000000 !=0)){
                    long temp = Long.parseLong(txtFldHomeNumber.getText());
                    long temp1 = Long.parseLong(txtFldMobileNumber.getText());
                    if (editing == false) {
                      resizePhonebook();
                    }
                    if (editing == true){
                      validEdits = true;
                    }
                  }
                }
                else{
                  JOptionPane.showMessageDialog(null, "The Phone Number must be 10 numbers long");
                  if (editing == true) 
                    validEdits = false;
                }
              }
              else{
                JOptionPane.showMessageDialog(null, "The Phone Number must be 10 numbers long");
                if (editing == true) 
                  validEdits = false;
              }
            }
          }
          catch(NumberFormatException nfe3){
            JOptionPane.showMessageDialog(null, "The Phone Number must only contain numbers");
            if (editing == true) 
              validEdits = false;
          }
        }
      }
    }
  }//end of entryTest method
  
  
  //resizePhonebook method. used to resize phoneBook array. in order to make space for a new entry
  static void resizePhonebook() {
    //create a temp array to hold the data
    String[][] tempPhoneBook = new String [entryNum][11];
    if (initialized == true) {
      entryNum++;
    }
    for(int row = 0; row < phoneBook.length;row++){
      for(int column = 0; column < phoneBook[0].length;column++){
        tempPhoneBook[row][column] = phoneBook[row][column];
      }
    }
    //create a new phoneBook array. with increased size and copy the information back in.
    phoneBook = new String[entryNum][11];
    for(int row = 0; row < tempPhoneBook.length;row++){
      for(int column = 0; column < tempPhoneBook[0].length;column++){
        phoneBook[row][column] = tempPhoneBook[row][column];
      }
    }
    if (initialized == true) {
      phoneBookEntry();
    }
    else {
      phoneBookInitialize();
    }
  }//end of resizePhonebook method
  //phoneBookEntry method. Used to add a phone book entry to an the array.
  static void phoneBookEntry(){
    phoneBook[entryNum-1][0] = Integer.toString(entryNum);
    if(txtFldFirstName.getText().equals(""))
      phoneBook[entryNum-1][1] = " ";
    else
      phoneBook[entryNum-1][1] = txtFldFirstName.getText();
    
    if(txtFldLastName.getText().equals(""))
      phoneBook[entryNum-1][2] = " ";
    else
      phoneBook[entryNum-1][2] = txtFldLastName.getText();
    
    if(txtFldHomeNumber.getText().equals(""))
      phoneBook[entryNum-1][3] = " ";
    else
      phoneBook[entryNum-1][3] = txtFldHomeNumber.getText();
    
    if(txtFldMobileNumber.getText().equals(""))
      phoneBook[entryNum-1][4] = " ";
    else
      phoneBook[entryNum-1][4] = txtFldMobileNumber.getText();
    
    if(txtFldEmail.getText().equals(""))
      phoneBook[entryNum-1][5] = " ";
    else
      phoneBook[entryNum-1][5] = txtFldEmail.getText();
    
    if(txtFldAddress.getText().equals(""))
      phoneBook[entryNum-1][6] = " ";
    else
      phoneBook[entryNum-1][6] = txtFldAddress.getText();
    
    if(txtFldCity.getText().equals(""))
      phoneBook[entryNum-1][7] = " ";
    else
      phoneBook[entryNum-1][7] = txtFldCity.getText();
    
    if(txtFldPostalCode.getText().equals(""))
      phoneBook[entryNum-1][8] = " ";
    else
      phoneBook[entryNum-1][8] = txtFldPostalCode.getText();
    
    if(cbxProvince.getSelectedItem()==(null))
      phoneBook[entryNum-1][9] = " ";
    else
      phoneBook[entryNum-1][9] = (String) cbxProvince.getSelectedItem();
    
    if(cbxGender.getSelectedItem()==(null))
      phoneBook[entryNum-1][10] = " ";
    else
      phoneBook[entryNum-1][10] = (String) cbxGender.getSelectedItem(); 
    btnDelete.setEnabled(true);
    btnEdit.setEnabled(true);
    if(!phoneBook[entryNum-1][3].equals(" ")){
      String tempHomeNum = ("(" + phoneBook[entryNum-1][3].substring(0, 3) + ") " + phoneBook[entryNum-1][3].substring(3,6) + "-" + phoneBook[entryNum-1][3].substring(6,10));
      phoneBook[entryNum-1][3] = tempHomeNum;
    }
    if(!phoneBook[entryNum-1][4].equals(" ")){
      String tempMobileNum = ("(" + phoneBook[entryNum-1][4].substring(0, 3) + ") " + phoneBook[entryNum-1][4].substring(3,6) + "-" + phoneBook[entryNum-1][4].substring(6,10));
      phoneBook[entryNum-1][4] = tempMobileNum;
    }
    appendToText();
    displayPhoneBook();
  }//end of phoneBookEntry method
  
  //displayPhoneBook Method. Used to display the phonebook after a new entry has been added
  static void displayPhoneBook() {
    //btnAdd.setEnabled(true);
    //refresh the Jtable and set the data showing to the phoneBook array. 
    tablePhoneBook = new JTable(phoneBook,phoneBookHeaders){
      public boolean isCellEditable(int row,int column){
        return false;
      }
    };
    tablePhoneBook.getColumnModel().getColumn(0).setPreferredWidth(92);
    tablePhoneBook.getColumnModel().getColumn(1).setPreferredWidth(114);
    tablePhoneBook.getColumnModel().getColumn(2).setPreferredWidth(172);
    tablePhoneBook.getColumnModel().getColumn(3).setPreferredWidth(169);
    tablePhoneBook.getColumnModel().getColumn(4).setPreferredWidth(293);
    tablePhoneBook.getColumnModel().getColumn(5).setPreferredWidth(139);
    tablePhoneBook.getColumnModel().getColumn(7).setPreferredWidth(53);
    tablePhoneBook.getColumnModel().getColumn(8).setPreferredWidth(51);
    tablePhoneBook.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    tablePhoneBook.getTableHeader().setReorderingAllowed(false);
    //creating scrollPane with Jtable and scroll bars within it
    JScrollPane scrollPane = new JScrollPane(tablePhoneBook, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setBounds(343, 40, 940, 468);
    contentPane.add(scrollPane);
    if (initialized == true) {
      clearEntryData();
    }
  }//end of displayPhoneBook method
  
  //clearEntryData method. used to clear all the data in the text fields and combo boxes in the entry area
  static void clearEntryData(){
    txtFldFirstName.setText("");
    txtFldLastName.setText("");
    txtFldHomeNumber.setText("");
    txtFldMobileNumber.setText("");
    txtFldEmail.setText("");
    txtFldAddress.setText("");
    txtFldCity.setText("");
    txtFldPostalCode.setText("");
    cbxProvince.setSelectedIndex(-1);
    cbxGender.setSelectedIndex(-1);
  }//end of clearEntryData method
  
  //clearSearchFound Method. used to reset variables used in searching. To prepare for another search
  static void clearSearchFound(){
    searchFound = new boolean[entryNum];
    for(int i = 0; i < searchFound.length; i++){
      searchFound[i] = false;
    }
    searchResultsFound = 0;
  }//end of clearSearchFound method
  
  static void deleteEntryData() {
    for (int i = 0; i < phoneBook.length; i++) {
      phoneBook[deleteNum-1][i] = null;
    }
    resizeDownPhonebook();
  }
  
  //Method that resizes the phonebook and makes all adjustments necessary after entry was deleted
  static void resizeDownPhonebook(){
    entryNum--;
    if (deleteNum < phoneBook.length) {
      for (int k = deleteNum; k < phoneBook.length; k++) {
        phoneBook[k][0] = Integer.toString((Integer.parseInt(phoneBook[k][0])) - 1);
      }
    }
    for (int i = deleteNum - 1; i < phoneBook.length - 1; i++) {
      for (int x = 0; x < phoneBook[i].length; x++) {
        phoneBook[i][x] = phoneBook[i + 1][x];
      }
    }
    //Creates a temp phonebok to store the data
    String[][] tempPhoneBook = new String [phoneBook.length - 1][11];
    for (int row = 0; row < phoneBook.length - 1;row++) {
      for (int column = 0; column < phoneBook[row].length;column++) {
        tempPhoneBook[row][column] = phoneBook[row][column];
      }
    }
    
    //create a new phoneBook array. with decreased size and copy the information back in.
    phoneBook = new String[phoneBook.length - 1][11];
    for (int row = 0; row < tempPhoneBook.length;row++) {
      for (int column = 0; column < tempPhoneBook[0].length;column++) {
        phoneBook[row][column] = tempPhoneBook[row][column];
      }
    }
    //Displays phonebook
    outputToTextFile();
    displayPhoneBook();
  }//End of resizeDownPhonebook method
  
  //searchPhonebook Method. used to search the phone book and tag. entries that match the user's search criteria
  static void searchPhonebook(){
    switch(cbxSearchType.getSelectedIndex()){
      //entry Number
      case 0:
        if(Integer.parseInt(txtFldSearch.getText()) <= phoneBook.length){
        searchFound[Integer.parseInt(txtFldSearch.getText())-1] = true;
        searchResultsFound++;
        displaySearch();
      }
        else{
          JOptionPane.showMessageDialog(null, "The Entry Number you Searching for does not Exist");
        }
        
        break;
        //first name
      case 1:
        for(int i = 0; i < phoneBook.length; i++){
        if(txtFldSearch.getText().equalsIgnoreCase(phoneBook[i][1])){
          searchFound[i] = true;
          searchResultsFound++;
        }
      }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The First Name you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
        //last name
      case 2:
        for(int i = 0; i < phoneBook.length; i++){
        if(txtFldSearch.getText().equalsIgnoreCase(phoneBook[i][2])){
          searchFound[i] = true;
          searchResultsFound++;
        }
      }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The Last Name you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
        //home phone
      case 3:
        String tempHomeNum = ("(" + txtFldSearch.getText().substring(0, 3) + ") " + txtFldSearch.getText().substring(3,6) + "-" + txtFldSearch.getText().substring(6,10));   
        for(int i = 0; i < phoneBook.length; i++){
          if(tempHomeNum.equalsIgnoreCase(phoneBook[i][3])){
            searchFound[i] = true;
            searchResultsFound++;
          }
        }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The Home Number you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
        //mobile phone
      case 4:
        String tempMobileNum = ("(" + txtFldSearch.getText().substring(0, 3) + ") " + txtFldSearch.getText().substring(3,6) + "-" + txtFldSearch.getText().substring(6,10));
        for(int i = 0; i < phoneBook.length; i++){
          if(tempMobileNum.equalsIgnoreCase(phoneBook[i][4])){
            searchFound[i] = true;
            searchResultsFound++;
          }
        }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The Mobile Number you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
        //E-mail
      case 5:
        for(int i = 0; i < phoneBook.length; i++){
        if(txtFldSearch.getText().equalsIgnoreCase(phoneBook[i][5])){
          searchFound[i] = true;
          searchResultsFound++;
        }
      }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The E-mail you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
        //address
      case 6:
        for(int i = 0; i < phoneBook.length; i++){
        if(txtFldSearch.getText().equalsIgnoreCase(phoneBook[i][6])){
          searchFound[i] = true;
          searchResultsFound++;
        }
      }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The Address you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
        //city
      case 7:
        for(int i = 0; i < phoneBook.length; i++){
        if(txtFldSearch.getText().equalsIgnoreCase(phoneBook[i][7])){
          searchFound[i] = true;
          searchResultsFound++;
        }
      }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The City you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
        //postal code
      case 8:
        for(int i = 0; i < phoneBook.length; i++){
        if(txtFldSearch.getText().equalsIgnoreCase(phoneBook[i][8])){
          searchFound[i] = true;
          searchResultsFound++;
        }
      }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The Postal Code you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
        //province
      case 9:
        for(int i = 0; i < phoneBook.length; i++){
        if(((String)cbxProvinceSearch.getSelectedItem()).equalsIgnoreCase(phoneBook[i][9])){
          searchFound[i] = true;
          searchResultsFound++;
        }
      }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The Province you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
        //gender
      case 10:
        for(int i = 0; i < phoneBook.length; i++){
        if(((String)cbxGenderSearch.getSelectedItem()).equalsIgnoreCase(phoneBook[i][10])){
          searchFound[i] = true;
          searchResultsFound++;
        }
      }
        if(searchResultsFound == 0){
          JOptionPane.showMessageDialog(null, "The Gender you were Searching for was not Found");
        }
        else{
          displaySearch();
        }
        break;
    }
    txtFldSearch.setText("");
    cbxProvinceSearch.setSelectedIndex(-1);
    cbxGenderSearch.setSelectedIndex(-1);
  }//end of searchPhonebook Method
  
  //displaySearchMethod. used to display the phone book entries based on the parameters of the search
  static void displaySearch(){
    //set all the marked phone book entries as matching to a temp array
    int searchIdentified = 0;
    String[][] tempSearchData = new String [searchResultsFound][11];
    for(int i = 0; i < phoneBook.length; i++){
      if(searchFound[i] == true){   
        for(int k = 0; k < phoneBook[0].length; k++){
          tempSearchData[searchIdentified][k] = phoneBook[i][k];
        }
        searchIdentified++;
      }
    }
    //refresh the Jtable and set the data showing to the temp array containing the phone book entries that match the search criteria
    tablePhoneBook = new JTable(tempSearchData,phoneBookHeaders);
    tablePhoneBook.getColumnModel().getColumn(0).setPreferredWidth(92);
    tablePhoneBook.getColumnModel().getColumn(1).setPreferredWidth(114);
    tablePhoneBook.getColumnModel().getColumn(2).setPreferredWidth(172);
    tablePhoneBook.getColumnModel().getColumn(3).setPreferredWidth(169);
    tablePhoneBook.getColumnModel().getColumn(4).setPreferredWidth(293);
    tablePhoneBook.getColumnModel().getColumn(5).setPreferredWidth(139);
    tablePhoneBook.getColumnModel().getColumn(7).setPreferredWidth(53);
    tablePhoneBook.getColumnModel().getColumn(8).setPreferredWidth(51);
    tablePhoneBook.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
    tablePhoneBook.getTableHeader().setReorderingAllowed(false);
    //creating scrollPane with Jtable and scroll bars within it
    JScrollPane scrollPane = new JScrollPane(tablePhoneBook, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setBounds(343, 40, 940, 468);
    contentPane.add(scrollPane);
  }//end of displaySearch Method
  
  static void editEntryData(){
    if(phoneBook[editNum-1][1].equals(" ")){
      phoneBook[editNum-1][1] = "";
    }
    if(phoneBook[editNum-1][2].equals(" ")){
      phoneBook[editNum-1][2] = "";
    }
    if(phoneBook[editNum-1][3].equals(" ")){
      phoneBook[editNum-1][3] = "";
    }
    if(phoneBook[editNum-1][4].equals(" ")){
      phoneBook[editNum-1][4] = "";
    }
    if(phoneBook[editNum-1][5].equals(" ")){
      phoneBook[editNum-1][5] = "";
    }
    if(phoneBook[editNum-1][6].equals(" ")){
      phoneBook[editNum-1][6] = "";
    }
    if(phoneBook[editNum-1][7].equals(" ")){
      phoneBook[editNum-1][7] = "";
    }
    if(phoneBook[editNum-1][8].equals(" ")){
      phoneBook[editNum-1][8] = "";
    }
    if(phoneBook[editNum-1][9].equals(" ")){
      phoneBook[editNum-1][9] = "";
    }
    if(phoneBook[editNum-1][10].equals(" ")){
      phoneBook[editNum-1][10] = "";
    }
    txtFldFirstName.setText(phoneBook[editNum-1][1]);
    txtFldLastName.setText(phoneBook[editNum-1][2]);
    if(!phoneBook[editNum-1][3].equals("")){
      String homeNumTemp = (phoneBook[editNum-1][3].substring(1,4) + phoneBook[editNum-1][3].substring(6,9) + phoneBook[editNum-1][3].substring(10,14));
      phoneBook[editNum-1][3] = homeNumTemp;
    }
    if(!phoneBook[editNum-1][4].equals("")){
      String homeNumTemp = (phoneBook[editNum-1][4].substring(1,4) + phoneBook[editNum-1][4].substring(6,9) + phoneBook[editNum-1][4].substring(10,14));
      phoneBook[editNum-1][4] = homeNumTemp;
    }
    txtFldHomeNumber.setText(phoneBook[editNum-1][3]);
    txtFldMobileNumber.setText(phoneBook[editNum-1][4]);
    txtFldEmail.setText(phoneBook[editNum-1][5]);
    txtFldAddress.setText(phoneBook[editNum-1][6]);
    txtFldCity.setText(phoneBook[editNum-1][7]);
    txtFldPostalCode.setText(phoneBook[editNum-1][8]);
    cbxProvince.setSelectedItem(phoneBook[editNum-1][9]);
    cbxGender.setSelectedItem(phoneBook[editNum-1][10]);
    btnSave.setEnabled(true);
  }
  
  static void appendToText(){
    BufferedWriter bw = null;
    try{
      bw = new BufferedWriter(new FileWriter(fileName, true));
      bw.write(phoneBook[entryNum-1][0] + "\t" + phoneBook[entryNum-1][1] + "\t" + phoneBook[entryNum-1][2] + "\t" + phoneBook[entryNum-1][3] + "\t" + phoneBook[entryNum-1][4] + "\t"+ phoneBook[entryNum-1][5] + "\t"+ phoneBook[entryNum-1][6] + "\t"+ phoneBook[entryNum-1][7] + "\t"+ phoneBook[entryNum-1][8] + "\t"+ phoneBook[entryNum-1][9] + "\t" + phoneBook[entryNum-1][10]);
      bw.newLine();
      bw.flush();
    }
    catch(IOException e){
    }
    finally{
      if(bw !=null){
        try{
          bw.close();
        }
        catch(IOException e2){
        }
      }
    }
  }
  
  static void outputToTextFile(){
    FileOutputStream fileHandle;
    PrintWriter outputFile;
    //try printing information to the text file
    try{
      fileHandle = new FileOutputStream(fileName);
      outputFile = new PrintWriter(fileHandle);
      for(int i = 0; i < phoneBook.length; i++)
        outputFile.println(phoneBook[i][0] + "\t" + phoneBook[i][1] + "\t" + phoneBook[i][2] + "\t" + phoneBook[i][3] + "\t" + phoneBook[i][4] + "\t"+ phoneBook[i][5] + "\t"+ phoneBook[i][6] + "\t"+ phoneBook[i][7] + "\t"+ phoneBook[i][8] + "\t"+ phoneBook[i][9] + "\t"+ phoneBook[i][10]); 
      outputFile.close();
    }
    //catch errors and display them in-case the previous task could not be completed 
    catch(IOException e){
    }  
  }//end of outputToTextFile method
  
  static void inputFromTextFile(){
    try {
      FileReader inputFile = new FileReader(fileName);
      BufferedReader bufferReader = new BufferedReader(inputFile);
      
      //Loop to continue reading each line of text in file until there is none left
      while ((inputText = bufferReader.readLine()) != null) {
        //Prevents errors when splitting string
        try {
          //Removes the tabs from each line, and puts it into an array
          tempData = inputText.split("\t");
          entryNum = Integer.parseInt(tempData[0]);
        }
        catch (Exception e) {
        }
        resizePhonebook();
      }
      bufferReader.close();
    }
    //Prints error if try can't be run
    catch (IOException e) {
      e.printStackTrace();
    }
    displayPhoneBook();
    initialized = true;
    btnEdit.setEnabled(true);
    btnDelete.setEnabled(true);
  }
  
  //Sets values into the phonebook based on text input file
  static void phoneBookInitialize() {
    phoneBook[entryNum - 1][0] = tempData[0];
    phoneBook[entryNum - 1][1] = tempData[1];
    phoneBook[entryNum - 1][2] = tempData[2];
    phoneBook[entryNum - 1][3] = tempData[3];
    phoneBook[entryNum - 1][4] = tempData[4];
    phoneBook[entryNum - 1][5] = tempData[5];
    phoneBook[entryNum - 1][6] = tempData[6];
    phoneBook[entryNum - 1][7] = tempData[7];
    phoneBook[entryNum - 1][8] = tempData[8];
    phoneBook[entryNum - 1][9] = (String) tempData[9];
    phoneBook[entryNum - 1][10] = (String) tempData[10];
  }//end of initialize phoneBook method
}