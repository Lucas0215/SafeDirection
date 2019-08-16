import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class SafeDirectionGUI extends JFrame {
	
	private JTextField startNameInput = null;
	private JTextField endNameInput = null;

	private MapGraph mg = null;
	
	private int displayMode = 0;
	private List<String> markVList = new ArrayList<String>();
	List<MapGraph.MapVertex> path = new ArrayList<MapGraph.MapVertex>();
	
	private static MapPanel mapPanel = null;

	public SafeDirectionGUI(MapGraph graph) {
		
		setTitle("안전길찾기 ver1.0");
		
		mg = graph;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(300,30);
		
		Container topPane = getContentPane();
		
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
		JButton findPathBtn = new JButton("길 찾기");
		findPathBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	MapGraph.MapVertex v1 = mg.findVertexByName(startNameInput.getText());
            	MapGraph.MapVertex v2 = mg.findVertexByName(endNameInput.getText());
            	if(v1!=null && v2!=null) {
            		SafeAStarSearch astar = new SafeAStarSearch(mg);
            		path = astar.AStarSearch(v1.getId(), v2.getId());
            		for(MapGraph.MapVertex v : path) {
            			v.printVertex();
            		}
            		displayMode = 3;
            		repaint();
            	}
            }
		});
		searchPane.add(startNameInput);
		searchPane.add(endNameInput);
		searchPane.add(findPathBtn);
		topPane.add(searchPane,BorderLayout.NORTH);
		
		JButton estEdgeBtn = new JButton("길 평가");
		topPane.add(estEdgeBtn,BorderLayout.SOUTH);
		
		setSize(540,700);
		setVisible(true);
		setResizable(false);
		
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
				if(displayMode==0) {
					g.setColor(new Color(0,0,0,0));
				}
				else if(displayMode==1) {
					g.setColor(new Color(0,0,0,255));
				}
				else if(displayMode==3) {
					g.setColor(new Color(255,0,0,255));
				}
				for(MapGraph.MapVertex v : mg.getVertexSet()) {
					if(displayMode == 3) {
						if(path.contains(v)) {
							g.fillOval(v.getX()*3/4-5, v.getY()*3/4-5, 10, 10);
							g.drawString(v.getName(), v.getX()*3/4, v.getY()*3/4+10);
						}
					}
					else {
						g.fillOval(v.getX()*3/4-5, v.getY()*3/4-5, 10, 10);
						g.drawString(v.getName(), v.getX()*3/4, v.getY()*3/4+10);
					}
				}
				for(MapGraph.MapEdge e : mg.getEdgeSet()) {
					if(displayMode == 3) {
						for(int i=0; i<path.size()-1; i++) {
							if(e.equals(mg.getEdge(path.get(i), path.get(i+1)))) {
								MapGraph.MapVertex a1 = mg.findVertexById(e.getAdjacentNode(0));
								MapGraph.MapVertex a2 = mg.findVertexById(e.getAdjacentNode(1));
								g.drawLine(a1.getX()*3/4, a1.getY()*3/4, a2.getX()*3/4, a2.getY()*3/4);
							}
						}
					}
					else {
						MapGraph.MapVertex a1 = mg.findVertexById(e.getAdjacentNode(0));
						MapGraph.MapVertex a2 = mg.findVertexById(e.getAdjacentNode(1));
						g.drawLine(a1.getX()*3/4, a1.getY()*3/4, a2.getX()*3/4, a2.getY()*3/4);
					}
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
