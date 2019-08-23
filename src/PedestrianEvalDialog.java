import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PedestrianEvalDialog extends JDialog {

	private MapGraph mg = null;
	private double scaleRatio = 0.75;
	private MapGraph.MapEdge selectedEdge = null;
	private List<MapGraph.MapVertex> highlightedVertex = new ArrayList<>();
	private List<MapGraph.MapVertex> selectedVertex = new ArrayList<>();

	private JPanel infoPane = null;
	private JLabel wayInfo = null;
	private JRadioButton[] scoreButtons = new JRadioButton[5];
	private JPanel evalPane = null;
	private JButton evaluateBtn = null;
	private JLabel guideLabel = new JLabel("<html><font size=4> &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp &emsp 평가할 길을 선택해 주세요<br><br> </font><html>");

	public PedestrianEvalDialog(JFrame frame, String title, MapGraph mg, double scaleRatio) {
		super(frame, title);
		this.mg = mg;
		this.scaleRatio = scaleRatio;
		MapPanel mp = new MapPanel();
		add(mp, BorderLayout.CENTER);

		infoPane = new JPanel();
		wayInfo = new JLabel();

		evalPane = new JPanel();
		ButtonGroup scoreGroup = new ButtonGroup();
		for (int i = 0; i < 5; i++) {
			scoreButtons[i] = new JRadioButton((i + 1) + "점");
			scoreGroup.add(scoreButtons[i]);
		}
		evaluateBtn = new JButton("평가하기");
		evaluateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getSelectedScore() != 0) {
					String v1 = selectedEdge.getAdjacentNode(0);
					String v2 = selectedEdge.getAdjacentNode(1);
					if (v1.compareTo(v2) < 0)
						Utils.saveReputation(Integer.parseInt(v1), Integer.parseInt(v2), getSelectedScore());
					else
						Utils.saveReputation(Integer.parseInt(v2), Integer.parseInt(v1), getSelectedScore());
					selectEdge(null);
					updateWayInfo();
					selectedVertex.clear();
					highlightedVertex.clear();
					repaint();
				} else
					JOptionPane.showMessageDialog(null, "이 길의 안전 점수를 선택해 주세요");
			}
		});
		infoPane.setLayout(new BorderLayout());
		infoPane.add(wayInfo, BorderLayout.NORTH);
		evalPane.add(new JLabel("안전도 평점 : "));
		for (int i = 0; i < 5; i++) {
			evalPane.add(scoreButtons[i]);
		}
		evalPane.add(evaluateBtn);
		infoPane.add(guideLabel, BorderLayout.CENTER);
		infoPane.add(evalPane, BorderLayout.SOUTH);
		evalPane.setVisible(false);

		add(infoPane, BorderLayout.SOUTH);

		setSize(515, 695);
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
				private int x;
				private int y;

				@Override
				public void mousePressed(MouseEvent e) {
					x = (int) (e.getX() / getScaleRatio());
					y = (int) (e.getY() / getScaleRatio());
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if (selectedVertex.size() > 1) {
						selectEdge(null);
						updateWayInfo();
						selectedVertex.clear();
						highlightedVertex.clear();
						repaint();
						return;
					}
					for (MapGraph.MapVertex v : mg.getVertexSet()) {
						if (Math.abs(v.getX() - x) < 15 && Math.abs(v.getY() - y) < 15) {
							if (selectedVertex.size() == 0) {
								selectedVertex.add(v);
								highlightedVertex.addAll(v.getNeighbors());
							} else if (selectedVertex.size() == 1) {
								if (highlightedVertex.contains(v)) {
									selectedVertex.add(v);
									highlightedVertex.clear();
								} else {
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
				JOptionPane.showMessageDialog(null, "알 수 없는 오류가 발생했습니다.");
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (mapImage != null) {
				w = (int) (mapImage.getWidth(null) * getScaleRatio());
				h = (int) (mapImage.getHeight(null) * getScaleRatio());
				g.drawImage(mapImage, 0, 0, w, h, null);
				Graphics2D g2 = (Graphics2D) g;
				
				g2.setStroke(new BasicStroke(3));
				g.setColor(new Color(0,120,0,255));
				for(int[] cctvs : mg.getRealCCTVList()) {
					g.drawOval((int)(cctvs[0]*scaleRatio)-6, (int)(cctvs[1]*scaleRatio)-6, 12, 12);
				}
				
				for (MapGraph.MapEdge e : mg.getEdgeSet()) {
					MapGraph.MapVertex a1 = mg.findVertexById(e.getAdjacentNode(0));
					MapGraph.MapVertex a2 = mg.findVertexById(e.getAdjacentNode(1));
					if (selectedVertex.contains(a1) && selectedVertex.contains(a2)) {
						selectEdge(e);
						updateWayInfo();
						g.setColor(new Color(255, 0, 0, 255));
						g2.setStroke(new BasicStroke(3));
					} else if ((selectedVertex.contains(a1) && highlightedVertex.contains(a2))
							|| (highlightedVertex.contains(a1) && selectedVertex.contains(a2))) {
						g.setColor(new Color(0, 0, 255, 255));
						g2.setStroke(new BasicStroke(3));
					} else {
						g.setColor(new Color(0, 0, 0, 255));
						g2.setStroke(new BasicStroke(1));
					}
					if (a1 != null && a2 != null) {
						g2.drawLine((int) (a1.getX() * getScaleRatio()), (int) (a1.getY() * getScaleRatio()),
								(int) (a2.getX() * getScaleRatio()), (int) (a2.getY() * getScaleRatio()));
					}
				}
				for (MapGraph.MapVertex v : mg.getVertexSet()) {
					if (selectedVertex.contains(v)) {
						g.setColor(new Color(255, 0, 0, 255));
						g.fillOval((int) (v.getX() * getScaleRatio()) - 6, (int) (v.getY() * getScaleRatio()) - 6, 12,
								12);
						// g.drawString(v.getName(), (int)(v.getX()*getScaleRatio()),
						// (int)(v.getY()*getScaleRatio())+10);
					} else if (highlightedVertex.contains(v)) {
						g.setColor(new Color(0, 0, 255, 255));
						g.fillOval((int) (v.getX() * getScaleRatio()) - 6, (int) (v.getY() * getScaleRatio()) - 6, 12,
								12);
						// g.drawString(v.getName(), (int)(v.getX()*getScaleRatio()),
						// (int)(v.getY()*getScaleRatio())+10);
					} else {
						g.setColor(new Color(0, 0, 0, 255));
						g.fillOval((int) (v.getX() * getScaleRatio()) - 5, (int) (v.getY() * getScaleRatio()) - 5, 10,
								10);
						// g.drawString(v.getName(), (int)(v.getX()*getScaleRatio()),
						// (int)(v.getY()*getScaleRatio())+10);
					}
				}
			}
		}
	}

	public void updateWayInfo() {
		if (selectedEdge == null) {
			wayInfo.setText("");
			guideLabel.setVisible(true);
			evalPane.setVisible(false);
		} else {
			double averageReput;
			String v1 = selectedEdge.getAdjacentNode(0);
			String v2 = selectedEdge.getAdjacentNode(1);
			if (v1.compareTo(v2) < 0)
				averageReput = Utils.getAverageReputation(v1, v2);
			else
				averageReput = Utils.getAverageReputation(v2, v1);
			guideLabel.setVisible(false);
			evalPane.setVisible(true);
			String text = "<html><center><font size=5>길 정보</font></center><br>&emsp;";
			String[] names = { "길이 : ", " &emsp &emsp 넓이 : ", "CCTV 개수 : ", " &emsp &emsp 지킴이집 개수 : ", "편의점 개수 : ",
					"밝기 : ", "술집 / 유흥가 개수 : ", "공사지점 개수 : " };
			for (int i = 0; i < 8; i++) {
				if (i == 4)
					text = text + "<br>&emsp;";
				text = text + "&emsp;" + names[i] + selectedEdge.getInfo()[i] + "&emsp;";
			}
			text = text + "<br><br><center><font size=4>평균평판 : "+averageReput+"</font></center>";
			wayInfo.setText(text);
			System.out.println(selectedEdge.toString());
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

	public int getSelectedScore() {
		for (int i = 0; i < scoreButtons.length; i++) {
			if (scoreButtons[i].isSelected())
				return i + 1;
		}
		return 0;
	}
}