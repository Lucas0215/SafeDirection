import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SafeDirectionGUI extends JFrame {

	public SafeDirectionGUI() {
		
		setTitle("안전길찾기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300,30);
		
		Container topPane = getContentPane();
		
		topPane.add(new JPanel(),BorderLayout.WEST);
		topPane.add(new JPanel(),BorderLayout.NORTH);
		
		ImageIcon mapImageIcon = new ImageIcon("images/map_image.jpg");
		Image scaledImage = mapImageIcon.getImage().getScaledInstance(mapImageIcon.getIconWidth()*3/4,mapImageIcon.getIconHeight()*3/4,Image.SCALE_DEFAULT);
		ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
		JLabel mapLabel = new JLabel(scaledImageIcon);
		mapLabel.setBorder(new EmptyBorder(50,50,50,50));
		topPane.add(mapLabel,BorderLayout.CENTER);
		
		JPanel searchPane = new JPanel();
		searchPane.setBorder(new EmptyBorder(200,0,50,50));
		
		GridLayout searchLayout = new GridLayout(10,1);
		searchLayout.setVgap(5);
		searchPane.setLayout(searchLayout);
		
		JTextField startNameInput = new JTextField(20);
		JTextField endNameInput = new JTextField(20);
		JButton findPathBtn = new JButton("길 찾기");
		searchPane.add(startNameInput);
		searchPane.add(endNameInput);
		searchPane.add(findPathBtn);
		topPane.add(searchPane,BorderLayout.EAST);
		
		JButton estEdgeBtn = new JButton("길 평가");
		topPane.add(estEdgeBtn,BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new SafeDirectionGUI();
	}

}
