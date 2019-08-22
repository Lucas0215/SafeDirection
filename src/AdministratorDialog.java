import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.w3c.dom.NodeList;

public class AdministratorDialog extends JDialog {
	
	private MapGraph mg = null;
	private double scaleRatio = 0.75;
	private int displayMode = 0;
	private MapGraph.MapEdge selectedEdge = null;
	private List<MapGraph.MapVertex> highlightedVertex = new ArrayList<>();
	private List<MapGraph.MapVertex> selectedVertex = new ArrayList<>();
	
	private JPanel infoPane = null;
	private JLabel wayInfo = null;
	private JPanel editPane = null;
	private JTextField[] editInputs = new JTextField[8];
	private JButton setBtn = null;
	
	public AdministratorDialog(JFrame frame, String title, MapGraph mg, double scaleRatio) {
		super(frame, title);
		this.mg = mg;
		this.scaleRatio = scaleRatio;
		MapPanel mp = new MapPanel();
		add(mp,BorderLayout.CENTER);

		infoPane = new JPanel();
		infoPane.setBackground(Color.LIGHT_GRAY);
		
		wayInfo = new JLabel();
		wayInfo.setFont(new Font("",Font.PLAIN,15));
		editPane = new JPanel();
		GridLayout editLayout = new GridLayout(10,1);
		editPane.setLayout(editLayout);
		editPane.add(new JPanel());
		editPane.add(new JPanel());
		for(int i=0; i<editInputs.length; i++) {
			editInputs[i] = new JTextField(3);
			editPane.add(editInputs[i]);
		}
		setBtn = new JButton("¼³Á¤");
		setBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateEdgeAttrs();
				selectEdge(null);
				updateWayInfo();
				selectedVertex.clear();
				highlightedVertex.clear();
				repaint();
			}
		});
		
		editPane.setVisible(false);
		setBtn.setVisible(false);
		
		infoPane.add(wayInfo);
		infoPane.add(editPane);
		infoPane.add(setBtn);
		
		add(infoPane,BorderLayout.SOUTH);
		
		setSize(540,700);
		setLocationByPlatform(true);
		setModalityType(DEFAULT_MODALITY_TYPE);
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
                	x = (int) (e.getX()/getScaleRatio());
                	y = (int) (e.getY()/getScaleRatio());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
					if(selectedVertex.size() > 1) {
						selectEdge(null);
						updateWayInfo();
						selectedVertex.clear();
						highlightedVertex.clear();
						repaint();
						return;
					}
    				for(MapGraph.MapVertex v : mg.getVertexSet()) {
    					if(Math.abs(v.getX()-x) < 15 && Math.abs(v.getY()-y) < 15) {
    						if(selectedVertex.size()==0) {
    							selectedVertex.add(v);
    							highlightedVertex.addAll(v.getNeighbors());
    						}
    						else if(selectedVertex.size()==1) {
    							if(highlightedVertex.contains(v)) {
    								selectedVertex.add(v);
    								highlightedVertex.clear();
    							}
    							else {
    								selectEdge(null);
    								updateWayInfo();
    								selectedVertex.clear();
    								highlightedVertex.clear();
    							}
    						}
    						repaint();
    						return;
    					}
    				}
					selectEdge(null);
					updateWayInfo();
					selectedVertex.clear();
					highlightedVertex.clear();
					repaint();
					return;
                }
			});
		}
		
		public void setImage() {
			InputStream is = this.getClass().getResourceAsStream("map_image.png");
			try {
				mapImage = ImageIO.read(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(mapImage != null) {
				w = (int) (mapImage.getWidth(null)*getScaleRatio());
				h = (int) (mapImage.getHeight(null)*getScaleRatio());
				g.drawImage(mapImage,0,0,w,h,null);
				Graphics2D g2 = (Graphics2D) g;
				for(MapGraph.MapEdge e : mg.getEdgeSet()) {
					MapGraph.MapVertex a1 = mg.findVertexById(e.getAdjacentNode(0));
					MapGraph.MapVertex a2 = mg.findVertexById(e.getAdjacentNode(1));
					if(selectedVertex.contains(a1) && selectedVertex.contains(a2)) {
						selectEdge(e);
						updateWayInfo();
						g.setColor(new Color(255,0,0,255));
						g2.setStroke(new BasicStroke(3));
					}
					else if((selectedVertex.contains(a1) && highlightedVertex.contains(a2)) || (highlightedVertex.contains(a1) && selectedVertex.contains(a2))) {
						g.setColor(new Color(0,0,255,255));
						g2.setStroke(new BasicStroke(3));
					}
					else {
						g.setColor(new Color(0,0,0,255));
						g2.setStroke(new BasicStroke(1));
					}
					if(a1 != null && a2 != null) {
						g.drawLine((int)(a1.getX()*getScaleRatio()), (int)(a1.getY()*getScaleRatio()), (int)(a2.getX()*getScaleRatio()), (int)(a2.getY()*getScaleRatio()));
					}
				}
				for(MapGraph.MapVertex v : mg.getVertexSet()) {
					if(selectedVertex.contains(v)) {
						g.setColor(new Color(255,0,0,255));
						g.fillOval((int)(v.getX()*getScaleRatio())-6, (int)(v.getY()*getScaleRatio())-6, 12, 12);
						g.drawString(v.getName(), (int)(v.getX()*getScaleRatio()), (int)(v.getY()*getScaleRatio())+10);
					}
					else if(highlightedVertex.contains(v)) {
						g.setColor(new Color(0,0,255,255));
						g.fillOval((int)(v.getX()*getScaleRatio())-6, (int)(v.getY()*getScaleRatio())-6, 12, 12);
						g.drawString(v.getName(), (int)(v.getX()*getScaleRatio()), (int)(v.getY()*getScaleRatio())+10);
					}
					else {
						g.setColor(new Color(0,0,0,255));
						g.fillOval((int)(v.getX()*getScaleRatio())-5, (int)(v.getY()*getScaleRatio())-5, 10, 10);
						g.drawString(v.getName(), (int)(v.getX()*getScaleRatio()), (int)(v.getY()*getScaleRatio())+10);
					}
				}
			}
		}
	}
	
	public void updateWayInfo() {
		if(selectedEdge == null) {
			wayInfo.setText("");
			editPane.setVisible(false);
			setBtn.setVisible(false);
		}
		else {
			wayInfo.setText("<html>" + selectedEdge.toString().replace("\n","<br/>") + "</html>");
			editPane.setVisible(true);
			setBtn.setVisible(true);
		}
	}
	
	public double getScaleRatio() {
		return scaleRatio;
	}
	
	public void selectEdge(MapGraph.MapEdge edgeSel) {
		selectedEdge = edgeSel;
	}
	
	public MapGraph.MapEdge getSelectedEdge() {
		return selectedEdge;
	}
	
	public void updateEdgeAttrs() {
		double newLength = Double.parseDouble(editInputs[0].getText());
		double newWidth = Double.parseDouble(editInputs[1].getText());
		int newCctv = Integer.parseInt(editInputs[2].getText());
		int newShelter = Integer.parseInt(editInputs[3].getText());
		int newConv = Integer.parseInt(editInputs[4].getText());
		double newBright = Double.parseDouble(editInputs[5].getText());
		int newAdult = Integer.parseInt(editInputs[6].getText());
		int newConst = Integer.parseInt(editInputs[7].getText());
		selectedEdge.update(newLength, newCctv, newShelter, newConv, newWidth, newBright, newAdult, newConst);
	}
}
