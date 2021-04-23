import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ConnectScreen {
	private JFrame frame;
	private JPanel connectionPanel;
	
	public ConnectScreen()
	{	
		connectionPanel = new JPanel();
        connectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Define JObjektus
        JLabel titleLabel = new JLabel("Connect to server:");
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel nameLabel = new JLabel("Name:");
        JLabel hostLabel = new JLabel("Host:");
        JLabel portLabel = new JLabel("Port:");
        JTextField  nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(120, 30));
        JTextField  hostField = new JTextField();
        hostField.setPreferredSize(new Dimension(120, 30));
        JTextField  portField = new JTextField();
        portField.setPreferredSize(new Dimension(120, 30));
        JButton connectButton = new JButton("Connect!");
		connectButton.addActionListener(e ->
		{
			//TODO: field validation
			Player newPlayer = new Player(nameField.getText());
			System.out.println(newPlayer.getPlayerName());
		});
        
        // Layout definicijas
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        connectionPanel.add(titleLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        connectionPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        connectionPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        connectionPanel.add(hostLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        connectionPanel.add(hostField, gbc);        
        gbc.gridx = 0;
        gbc.gridy = 3;
        connectionPanel.add(portLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        connectionPanel.add(portField, gbc);      
        gbc.gridx = 1;
        gbc.gridy = 4;
        connectionPanel.add(connectButton, gbc);
        
        frame = new JFrame("Connect to server");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(500, 500));
        frame.add(connectionPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
        
	}
	
	public static void main(String[] args) {
        new ConnectScreen();
    }
}
