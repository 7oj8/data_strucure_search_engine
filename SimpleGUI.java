import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGUI {

    // Constructor to set up the GUI components
    public SimpleGUI() {
        // Create a new JFrame (window)
        JFrame frame = new JFrame("Index and Query GUI");

        // Set the size of the window
        frame.setSize(600, 400);

        // Define the action when the window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a new JPanel (main container)
        //JPanel mainPanel = new JPanel();
        //mainPanel.setLayout(new FlowLayout());

        // Create the two main buttons: Index and Query & Ranking

        //JButton indexButton = new JButton("Index");
        //JButton queryButton = new JButton("Query and Ranking");

        // Create panels for additional controls (initially hidden)
        JPanel indexPanel = new JPanel();
        JPanel queryPanel = new JPanel();

        // Set the layout of the panels
        indexPanel.setLayout(new FlowLayout());
        queryPanel.setLayout(new FlowLayout());

        // Create a JTextField for the text box
        JTextField indexTextField = new JTextField(20);
        JTextField queryTextField = new JTextField(20);

        // Create buttons for index and inverse index
        JButton indexActionButton = new JButton("Index");
        JButton inverseIndexActionButton = new JButton("Inverse Index");

        indexPanel.add(new JLabel("Enter Text for Indexing:"));
        indexPanel.add(indexTextField);
        indexPanel.add(indexActionButton);
        indexPanel.add(inverseIndexActionButton);
        //frame.revalidate();
        //frame.repaint();

        // Add action listeners to the buttons
//        indexButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Show the index panel with the text box and buttons
//                indexPanel.removeAll();
//
//            }
//        });

        //queryPanel.removeAll();
        queryPanel.add(new JLabel("Enter Query:"));
        queryPanel.add(queryTextField);
        JButton searchButton = new JButton("Query and Ranking");
        queryPanel.add(searchButton);

        //frame.revalidate();
        //frame.repaint();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform an index operation (e.g., display text)
                String text = queryTextField.getText();
                JOptionPane.showMessageDialog(frame, "Indexing: " + ProjectMain.handleInput(text) );
            }
        });
        // Add action listeners for Index and Inverse Index buttons
        indexActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform an index operation (e.g., display text)
                String text = indexTextField.getText();
                String result="";
                try {
                    result = ProjectMain.Printindex(Integer.parseInt(text));
                }
                catch (Exception error) {
                    JOptionPane.showMessageDialog(frame, "Please Make Sure That The Input is an integer");
                    return;
                }
                JOptionPane.showMessageDialog(frame, "Indexing: " + result);
            }
        });

        inverseIndexActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform inverse index operation (e.g., display text)
                String text = indexTextField.getText();
                JOptionPane.showMessageDialog(frame, "Inverse Indexing: " + ProjectMain.printInvertedIndex(text));
            }
        });

        // Set up the layout and add the buttons to the main panel
        //mainPanel.add(indexButton);
        //mainPanel.add(queryButton);

        // Add the main panel and the dynamic panels (indexPanel and queryPanel) to the frame
        //frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(indexPanel, BorderLayout.CENTER);
        frame.add(queryPanel, BorderLayout.SOUTH);

        // Make the window visible
        frame.setVisible(true);
    }

    // Main method to instantiate the GUI class
    public static void main(String[] args) {
        //Create an instance of SimpleGUI to launch the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleGUI();  // Create and display the GUI
            }
        });
    }
}
