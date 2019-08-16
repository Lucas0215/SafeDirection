import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SafeDirectionGUI extends JFrame {

	public SafeDirectionGUI() {
		
		setTitle("안전길찾기 ver1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300,30);
		
		Container topPane = getContentPane();
		
		/*
		ImageIcon mapImageIcon = new ImageIcon("images/map_image.jpg");
		Image scaledImage = mapImageIcon.getImage().getScaledInstance(mapImageIcon.getIconWidth()*3/4,mapImageIcon.getIconHeight()*3/4,Image.SCALE_DEFAULT);
		ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
		JLabel mapLabel = new JLabel(scaledImageIcon);
		topPane.add(mapLabel,BorderLayout.CENTER);
		*/
		
		MapPanel mp = new MapPanel();
		topPane.add(mp,BorderLayout.CENTER);
		
		JPanel searchPane = new JPanel();
		searchPane.setBorder(new EmptyBorder(10,10,10,10));
		searchPane.setBackground(Color.LIGHT_GRAY);
		
		GridLayout searchLayout = new GridLayout(1,4);
		searchLayout.setVgap(5);
		searchPane.setLayout(searchLayout);
		
		JTextField startNameInput = new JTextField(15);
		JTextField endNameInput = new JTextField(15);
		JButton findPathBtn = new JButton("길 찾기");
		findPathBtn.setBounds(50,50,50,50);
		searchPane.add(startNameInput);
		searchPane.add(endNameInput);
		searchPane.add(findPathBtn);
		topPane.add(searchPane,BorderLayout.NORTH);
		
		JButton estEdgeBtn = new JButton("길 평가");
		topPane.add(estEdgeBtn,BorderLayout.SOUTH);
		
		setSize(540,700);
		setVisible(true);
		
	}
	
	class MapPanel extends JPanel {
		Image mapImage;
		int w;
		int h;
		
		public MapPanel() {
			setImage();
		}
		
		public void setImage() {
			File f = new File("images/map_image.jpg");
			try {
				mapImage = ImageIO.read(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(mapImage != null) {
				w = mapImage.getWidth(null)*3/4;
				h = mapImage.getHeight(null)*3/4;
				g.drawImage(mapImage,0,0,w,h,null);
			}
		}
	}
	
	public static void main(String[] args) {
		new SafeDirectionGUI();
	}

}
