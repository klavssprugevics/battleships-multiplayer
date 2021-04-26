import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class ClientSetupManager {

	private JFrame frame;
	private JPanel namePanel;
	private JPanel shipSelectPanel;
	private JButton continueButton;
	private JLabel descriptionLabel;
	private String playerInputName;
	private int[][] playerShips;
	private Player player;

	
	
	public ClientSetupManager() {
		
		// Setup Name Panel
		namePanel = new JPanel();
		namePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
		
        JLabel titleLabel = new JLabel("Enter your name:");
        titleLabel.setFont(new Font("Verdana", Font.PLAIN, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField  nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(120, 30));
        JButton nameButton = new JButton("Done");
        nameButton.addActionListener(e ->
		{
			String testInput = nameField.getText();
			if(testInput != null && !testInput.isBlank())
			{
				playerInputName = testInput;
				System.out.println(playerInputName);
				
				namePanel.setVisible(false);
				frame.setTitle("Ship setup!");
				frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				frame.add(shipSelectPanel, BorderLayout.CENTER);
			}
			else
			{
				JOptionPane.showMessageDialog(frame, "Name cannot be left blank.");
			}
				
		});
        
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        namePanel.add(titleLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        namePanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        namePanel.add(nameButton, gbc);
        
        
        // Setup Ship panel

	    
        // Initialize 2D array
        playerShips = new int[10][10]; // TODO: Fixed variables
        for (int i = 0; i < playerShips.length; i++) {
			for (int j = 0; j < playerShips[i].length; j++) {
				playerShips[i][j] = 0;
			}
		}
        
        

	    GameSetupGrid plyGrid = new GameSetupGrid(this);
	    
        shipSelectPanel = new JPanel();
        shipSelectPanel.setLayout(new GridBagLayout());
	    GridBagConstraints gbc2 = new GridBagConstraints();
        
	    
        JLabel titleLabel1 = new JLabel("Setup your ships - press 'MOUSE2' to rotate.");
        titleLabel1.setFont(new Font("Verdana", Font.PLAIN, 24));
        titleLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        
        descriptionLabel = new JLabel("Place ship with size: 4");
        descriptionLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton continueButton = new JButton("Continue");
        this.continueButton = continueButton;
        continueButton.setVisible(false);
        continueButton.addActionListener(e ->
		{
			this.player = new Player(nameField.getText());
			this.player.setPlayerField(plyGrid.returnField());
			this.player.printPlayerField();
		});
        
        gbc2.insets = new Insets(5,50,5,50);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        shipSelectPanel.add(titleLabel1, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        shipSelectPanel.add(descriptionLabel, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 2;
	    shipSelectPanel.add(plyGrid, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        shipSelectPanel.add(continueButton, gbc2);
        
        
        
        frame = new JFrame("Enter your name");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(300, 200));
        frame.add(namePanel, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	

	}
	
	public int[][] getPlayerShips()
	{
		return playerShips;
	}
	
	public void editDescription(String newLabel)
	{
		descriptionLabel.setText(newLabel);
	}
	
	public void enableContinueButton()
	{
		continueButton.setVisible(true);
	}

	public static void main(String[] args) {
		new ClientSetupManager();

	}
	

	


}
