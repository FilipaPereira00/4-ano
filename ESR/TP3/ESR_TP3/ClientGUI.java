import javax.swing.*;

public class ClientGUI extends JFrame {

    public ClientGUI() {

        setTitle("My Gui");
        setSize(400, 400);

        // Create JButton and JPanel
        JButton button = new JButton("Click here!");
        JPanel panel = new JPanel();

        // Add button to JPanel
        panel.add(button);
        // And JPanel needs to be added to the JFrame itself!
        this.getContentPane().add(panel);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}