import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.*;
import java.awt.BorderLayout;

public class MainGUI extends JFrame /*implements ActionListener*/{
    
  //fields
  private JTextArea mainTextArea;
  private JScrollPane mainScroll;

  //constructor
  public MainGUI(){
    setTitle("Hospital Appointment Manager");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900,600);
    setLocationRelativeTo(null);

    menuBar();

    // main area 
    mainTextArea = new JTextArea();
    mainTextArea.setEditable(false);
    mainScroll = new JScrollPane(mainTextArea);
    getContentPane().add(mainScroll, BorderLayout.CENTER);
  }

  //menu bar (File, View, Help)=================================================================

  private void menuBar(){
    JMenuBar menuBar = new JMenuBar();


    //file menu

    JMenu fileMenu = new JMenu("File");
    JMenuItem newFile = new JMenuItem("New");
    JMenuItem saveFile = new JMenuItem("Save");
    JMenuItem loadFile = new JMenuItem("Load");
    JMenuItem exitFile = new JMenuItem("Exit");
    newFile.addActionListener(e -> onNew());
    saveFile.addActionListener(e -> onSave());
    loadFile.addActionListener(e -> onLoad());
    exitFile.addActionListener(e -> onExit());
    fileMenu.add(newFile);
    fileMenu.add(saveFile);
    fileMenu.add(loadFile);
    fileMenu.addSeparator(); //separator line to differentiate the exit option
    fileMenu.add(exitFile);

    //view menu

    JMenu viewMenu = new JMenu("View");
    JMenuItem listAllItem = new JMenuItem("List All");
    JMenuItem calendarViewItem = new JMenuItem("Calendar View");
    listAllItem.addActionListener(e -> onListAll());
    calendarViewItem.addActionListener(e -> onCalendarView());
    viewMenu.add(listAllItem);
    viewMenu.add(calendarViewItem);

    //help menu

    JMenu helpMenu = new JMenu("Help");
    JMenuItem aboutItem = new JMenuItem("About");
    aboutItem.addActionListener(e -> onAbout());
    helpMenu.add(aboutItem);

    //adding them to the menu bar
    
    menuBar.add(fileMenu);
    menuBar.add(viewMenu);
    menuBar.add(helpMenu);

    setJMenuBar(menuBar);
  }

  //menu action for File=============================================================================

  // fields
    private JTextField reservationID;
    private JTextField patientName;
    private JTextField patientID;
    private JTextField reservationDate;
    private JTextField doctorName;
    private JTextField insuranceCode;
    private JTextField serviceType;

  private void onNew(){
    
    JFrame reservationForm = new JFrame("Reservation Form");
    reservationForm.setSize(600, 600);
    reservationForm.setLocationRelativeTo(null);
    reservationForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    
  }

  private void onSave(){
      // TODO: implement saving
      
    }

  private void onLoad(){
      // TODO: implement loading
      
    }

  private void onExit(){
    dispose();
    System.exit(0);
  }

  // =========================================================================================
  private void onListAll(){

  }

  private void onCalendarView(){
    JOptionPane.showMessageDialog(this, "Switching to calendar view (not implemented).", "View", JOptionPane.INFORMATION_MESSAGE);
  }

  private void onAbout(){
    String aboutMessage = "Hospital Appointment System\nVersion 1.0\nAuthor: (your name)";
    JOptionPane.showMessageDialog(this, aboutMessage, "About", JOptionPane.INFORMATION_MESSAGE);
  }
}
