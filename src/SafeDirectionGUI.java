import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SafeDirectionGUI extends JFrame {

	public SafeDirectionGUI() {
		
		setTitle("Safe Direction");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setLocationRelativeTo(null);
		
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(1,2));
		
		ImageIcon mapImage = new ImageIcon("images/map_image.jpg");
		JLabel mapLabel = new JLabel(mapImage);
		pane.add(mapLabel);
		
		JPanel controlPane = new JPanel();
		Border border = controlPane.getBorder();
		Border margin = new EmptyBorder(30,30,30,30);
		controlPane.setBorder(new CompoundBorder(border, margin));
		
		GridLayout controlLayout = new GridLayout(7,1);
		controlLayout.setVgap(5);
		controlPane.setLayout(controlLayout);
		
		JTextField startName = new JTextField();
		JTextField endName = new JTextField();
		JButton findPathBtn = new JButton("길찾기");
		JButton setEdgeBtn = new JButton("평판");
		controlPane.add(startName);
		controlPane.add(endName);
		controlPane.add(findPathBtn);
		controlPane.add(setEdgeBtn);
		pane.add(controlPane);
		
		pack();
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new SafeDirectionGUI();
	}

}
