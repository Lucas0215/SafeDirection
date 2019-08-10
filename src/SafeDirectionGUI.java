import java.awt.*;
import javax.swing.*;

public class SafeDirectionGUI extends JFrame {

	public SafeDirectionGUI() {
		
		setTitle("Safe Direction");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setLocationRelativeTo(null);
		
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		
		ImageIcon mapImage = new ImageIcon("images/map_image.jpg");
		JLabel mapLabel = new JLabel(mapImage);
		pane.add(mapLabel);
		
		pack();
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new SafeDirectionGUI();
	}

}
