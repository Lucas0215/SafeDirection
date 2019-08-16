import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SafeDirectionGUI extends JFrame {
	
	JTextField startNameInput = null;
	JTextField endNameInput = null;

	private MapGraph mg = null;
	
	private static SafeAStarSearch sas = null;
	private static MapPanel mapPanel = null;

	public SafeDirectionGUI(MapGraph graph) {
		
		setTitle("������ã�� ver1.0");
		
		mg = graph;
		
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
		
		mapPanel = new MapPanel();
		topPane.add(mapPanel,BorderLayout.CENTER);
		
		JPanel searchPane = new JPanel();
		searchPane.setBorder(new EmptyBorder(10,10,10,10));
		searchPane.setBackground(Color.LIGHT_GRAY);
		
		GridLayout searchLayout = new GridLayout(1,4);
		searchLayout.setVgap(5);
		searchPane.setLayout(searchLayout);
		
		startNameInput = new JTextField(15);
		endNameInput = new JTextField(15);
		JButton findPathBtn = new JButton("�� ã��");
		findPathBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	System.out.println("��ã��");
            }
		});
		searchPane.add(startNameInput);
		searchPane.add(endNameInput);
		searchPane.add(findPathBtn);
		topPane.add(searchPane,BorderLayout.NORTH);
		
		JButton estEdgeBtn = new JButton("�� ��");
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
			addMouseListener(new MouseAdapter() {
				private Color background;
				private int x;
				private int y;

                @Override
                public void mousePressed(MouseEvent e) {
                	x = e.getX()*4/3;
                	y = e.getY()*4/3;
                }

                @Override
                public void mouseReleased(MouseEvent e) {
    				for(MapGraph.MapVertex v : mg.getVertexSet()) {
    					if(Math.abs(v.getX()-x) < 15 && Math.abs(v.getY()-y) < 15) {
    						if(startNameInput.getText().equals(""))
    							startNameInput.setText(v.getName());
    						else
    							endNameInput.setText(v.getName());
    						return;
    					}
    				}
                }
			});
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
				for(MapGraph.MapVertex v : mg.getVertexSet()) {
					g.fillOval(v.getX()*3/4 - 5, v.getY()*3/4 - 5, 10, 10);
					g.drawString(v.getName(), v.getX()*3/4, v.getY()*3/4 + 10);
				}
				for(MapGraph.MapEdge e : mg.getEdgeSet()) {
					MapGraph.MapVertex a1 = mg.findVertexById(e.getAdjacentNode(0));
					MapGraph.MapVertex a2 = mg.findVertexById(e.getAdjacentNode(1));
					g.drawLine(a1.getX()*3/4, a1.getY()*3/4, a2.getX()*3/4, a2.getY()*3/4);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		MapGraph graph = new MapGraph();
		SafeAStarSearch sass = new SafeAStarSearch(graph);
		SafeDirectionGUI gui = new SafeDirectionGUI(graph);
	}

}
