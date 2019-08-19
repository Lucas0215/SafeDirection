import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SafeDirectionGUI extends JFrame {
	
	private PedestrianEvalDialog pEvalDialog = null;
	private SettingsDialog settingsDialog = null;
	
	private JTextField startNameInput = null;
	private JTextField endNameInput = null;

	private MapGraph mg = null;
	
	final double scaleRatio = 0.75;
	
	private boolean pathFound = false;
	private List<String> markVList = new ArrayList<String>();
	private List<MapGraph.MapVertex> path = new ArrayList<MapGraph.MapVertex>();
	private List<MapGraph.MapVertex> defaultPath = new ArrayList<MapGraph.MapVertex>();
	
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
		JButton findPathBtn = new JButton("안전길찾기");
		JButton initInputBtn = new JButton("초기화");
		findPathBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	MapGraph.MapVertex v1 = mg.findVertexByName(startNameInput.getText());
            	MapGraph.MapVertex v2 = mg.findVertexByName(endNameInput.getText());
            	if(v1!=null && v2!=null) {
            		SafeAStarSearch astar = new SafeAStarSearch(mg);
            		path = astar.AStarSearch(v1.getId(), v2.getId(), true);
            		defaultPath = astar.AStarSearch(v1.getId(), v2.getId(), false);
            		updatePathFound(true);
            		repaint();
            	}
            }
		});
		initInputBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	startNameInput.setText("");
            	endNameInput.setText("");
            	updatePathFound(false);
            	repaint();
            }
		});
		searchPane.add(startNameInput);
		searchPane.add(endNameInput);
		searchPane.add(findPathBtn);
		searchPane.add(initInputBtn);
		topPane.add(searchPane,BorderLayout.NORTH);

		JPanel controlPane = new JPanel();
		controlPane.setBackground(Color.LIGHT_GRAY);
		
		GridLayout controlLayout = new GridLayout(1,2);
		controlLayout.setVgap(5);
		controlPane.setLayout(controlLayout);
		
		JButton estEdgeBtn = new JButton("길 평가");
		JButton settingsBtn = new JButton("설정");
		JFrame frame = this;
		estEdgeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
        		pEvalDialog = new PedestrianEvalDialog(frame,"길 안전 평가", mg, scaleRatio);
            	pEvalDialog.setVisible(true);
            }
		});
		settingsBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            	updatePathFound(false);
            	repaint();
        		settingsDialog = new SettingsDialog(frame,"설정", mg, scaleRatio);
        		settingsDialog.setVisible(true);
            }
		});
		controlPane.add(estEdgeBtn);
		controlPane.add(settingsBtn);
		
		topPane.add(controlPane,BorderLayout.SOUTH);
		
		setSize(540,700);
		setLocationByPlatform(true);
		setVisible(true);
		setResizable(false);
		
	}
	
	class MapPanel extends JPanel {
		private Image mapImage;
		private int w;
		private int h;
		
		public MapPanel() {
			setImage();
			addMouseListener(new MouseAdapter() {
				private Color background;
				private int x;
				private int y;

                @Override
                public void mousePressed(MouseEvent e) {
                	x = (int) (e.getX()/scaleRatio);
                	y = (int) (e.getY()/scaleRatio);
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
			File f = new File("images/map_image2.jpg");
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
				w = (int) (mapImage.getWidth(null)*scaleRatio);
				h = (int) (mapImage.getHeight(null)*scaleRatio);
				g.drawImage(mapImage,0,0,w,h,null);
				if(Settings.getDisplayMode()==0) {
					g.setColor(new Color(0,0,0,0));
				}
				else if(Settings.getDisplayMode()==1) {
					g.setColor(new Color(0,0,0,255));
				}
				for(MapGraph.MapEdge e : mg.getEdgeSet()) {
					if(pathIsFound()) {
						g.setColor(new Color(0,0,255,255));
						for(int i=0; i<path.size()-1; i++) {
							if(e.equals(mg.getEdge(path.get(i), path.get(i+1)))) {
								MapGraph.MapVertex a1 = mg.findVertexById(e.getAdjacentNode(0));
								MapGraph.MapVertex a2 = mg.findVertexById(e.getAdjacentNode(1));
								if(a1!=null && a2!=null)
									g.drawLine((int)(a1.getX()*scaleRatio), (int)(a1.getY()*scaleRatio), (int)(a2.getX()*scaleRatio), (int)(a2.getY()*scaleRatio));
							}
						}
						g.setColor(new Color(255,0,0,255));
						for(int i=0; i<defaultPath.size()-1; i++) {
							if(e.equals(mg.getEdge(defaultPath.get(i), defaultPath.get(i+1)))) {
								MapGraph.MapVertex a1 = mg.findVertexById(e.getAdjacentNode(0));
								MapGraph.MapVertex a2 = mg.findVertexById(e.getAdjacentNode(1));
								if(a1!=null && a2!=null)
									g.drawLine((int)(a1.getX()*scaleRatio), (int)(a1.getY()*scaleRatio), (int)(a2.getX()*scaleRatio), (int)(a2.getY()*scaleRatio));
							}
						}
						
					}
					else {
						MapGraph.MapVertex a1 = mg.findVertexById(e.getAdjacentNode(0));
						MapGraph.MapVertex a2 = mg.findVertexById(e.getAdjacentNode(1));
						if(Settings.getDisplayMode()/2%2 == 1) {
							if(a1!=null && a2!=null)
								g.drawLine((int)(a1.getX()*scaleRatio), (int)(a1.getY()*scaleRatio), (int)(a2.getX()*scaleRatio), (int)(a2.getY()*scaleRatio));
						}
					}
				}
				for(MapGraph.MapVertex v : mg.getVertexSet()) {
					if(pathIsFound()) {
						g.setColor(new Color(0,0,255,255));
						if(path.contains(v)) {
							g.fillOval((int)(v.getX()*scaleRatio)-5, (int)(v.getY()*scaleRatio)-5, 10, 10);
							g.drawString(v.getName(), (int)(v.getX()*scaleRatio), (int)(v.getY()*scaleRatio)+10);
						}
						g.setColor(new Color(255,0,0,255));
						if(defaultPath.contains(v)) {
							g.fillOval((int)(v.getX()*scaleRatio)-5, (int)(v.getY()*scaleRatio)-5, 10, 10);
							g.drawString(v.getName(), (int)(v.getX()*scaleRatio), (int)(v.getY()*scaleRatio)+10);
						}
					}
					else {
						if(Settings.getDisplayMode()%2 == 1) {
							g.fillOval((int)(v.getX()*scaleRatio)-5, (int)(v.getY()*scaleRatio)-5, 10, 10);
						}
						if(Settings.getDisplayMode()/2/2%2 == 1) {
							g.drawString(v.getName(), (int)(v.getX()*scaleRatio), (int)(v.getY()*scaleRatio)+10);
						}
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		MapGraph graph = new MapGraph();
		LoginUI ui = new LoginUI();
	}
	
	public boolean pathIsFound() {
		return pathFound;
	}
	
	public void updatePathFound(boolean newPathFound) {
		this.pathFound = newPathFound;
	}

}
