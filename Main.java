import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args){

         /*try {
            MainGUI.loadFromFile("reservations.txt");
        } catch (Exception e) {
            System.err.println("Could not load reservations: " + e.getMessage());
        }*/
    
        SwingUtilities.invokeLater(() -> {
        MainGUI gui = new MainGUI();
        gui.setVisible(true);
    });

    
  }
}
