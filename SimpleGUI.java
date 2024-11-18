import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGUI {
	
    public SimpleGUI() {
        JFrame frame = new JFrame("GUI");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel indexPanel = new JPanel();
        //JPanel queryPanel = new JPanel();
        indexPanel.setLayout(new FlowLayout());
        //queryPanel.setLayout(new FlowLayout());
        JTextField indexTextField = new JTextField(20);
        //JTextField queryTextField = new JTextField(20);
        JButton index = new JButton("Index");
        JButton inverseIndex = new JButton("Inverse Index");
        JButton bst = new JButton("BST");
        indexPanel.add(new JLabel("Enter Text for Indexing:"));
        indexPanel.add(indexTextField);
        indexPanel.add(index);
        indexPanel.add(inverseIndex);
        indexPanel.add(bst);
        //queryPanel.add(new JLabel("Enter Query:"));
        //queryPanel.add(queryTextField);
        
        //queryPanel.add(searchButton);
        bst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = indexTextField.getText();
                Long startTime = System.nanoTime();
                String result=ProjectMain.bstHandler(text);
                Long endTime = System.nanoTime();
                Long totalTime=(endTime-startTime)/1000;
                JOptionPane.showMessageDialog(frame, "System Found Result in "+totalTime+" millisecond \nResult:\n" + result );
            }
        });
        index.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = indexTextField.getText();
                Long startTime = System.nanoTime();
                String result= ProjectMain.indexHandler(text);
                Long endTime = System.nanoTime();
                Long totalTime=(endTime-startTime)/1000;
                JOptionPane.showMessageDialog(frame, "System Found Result in "+totalTime+" millisecond \nResult:\n" + result );
            }
        });

        inverseIndex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = indexTextField.getText();
                Long startTime = System.nanoTime();
                String result = ProjectMain.inverseHandler(text);
                Long endTime = System.nanoTime();
                Long totalTime=(endTime-startTime)/1000;
                JOptionPane.showMessageDialog(frame, "System Found Result in "+totalTime+" millisecond \nResult:\n" + result );
            }
        });
        frame.add(indexPanel, BorderLayout.CENTER);
        //frame.add(queryPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleGUI();  // Create and display the GUI
            }
        });
    }
}