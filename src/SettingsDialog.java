import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

public class SettingsDialog extends JDialog {
	private MapGraph mg = null;
	private double scaleRatio = 0.75;
	
	private AdministratorDialog adminDialog = null;
	
	public SettingsDialog(JFrame frame, String title, MapGraph mg, double scaleRatio) {
		super(frame, title);
		this.mg = mg;
		this.scaleRatio = scaleRatio;
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(new EmptyBorder(20,50,50,50));
		BoxLayout settingsLayout = new BoxLayout(settingsPanel,BoxLayout.Y_AXIS);
		settingsPanel.setLayout(settingsLayout);
		
		JLabel importanceSet = new JLabel("안전요소 중요도");
		importanceSet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(importanceSet);
		settingsPanel.add(new JPanel());
		
		JLabel markCCTV = new JLabel("CCTV : 최대값 - CCTV 범위(20m) 내에 들어오지 않는 길 10m당 안전거리 1m 추가");	
		JSlider slideCCTV = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getCctvImp());
		settingsPanel.add(markCCTV);
		settingsPanel.add(slideCCTV);
		
		JLabel markShelter = new JLabel("여성안심지킴이집 : 최대값 - 여성안심지킴이집 범위(50m) 내에 들어오지 않는 길 10m당 안전거리 1m 추가");
		JSlider slideShelter = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getShelterImp());
		settingsPanel.add(markShelter);
		settingsPanel.add(slideShelter);
		
		JLabel markConv = new JLabel("24시 편의점 : 최대값 - 편의점 범위(50m) 내에 들어오지 않는 길 10m당 안전거리 1m 추가");
		JSlider slideConv = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getConvenienceImp());
		settingsPanel.add(markConv);
		settingsPanel.add(slideConv);
		
		JLabel markWidth = new JLabel("길의 폭 : 최대값 - 길의 폭이 1m 줄어들 때마다 안전거리 총 길이만큼 추가");	
		JSlider slideWidth = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getWidthImp());
		settingsPanel.add(markWidth);
		settingsPanel.add(slideWidth);
		
		JLabel markBrightness = new JLabel("길의 밝기 : 최대값 - 길의 밝기가 0.1 줄어들 때마다 안전거리 총 길이의 1/5만큼 추가");
		JSlider slideBrightness = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getBrightnessImp());
		settingsPanel.add(markBrightness);
		settingsPanel.add(slideBrightness);
		
		settingsPanel.add(new JPanel());
		settingsPanel.add(new JPanel());
		
		JLabel dangerSet = new JLabel("위험요소 중요도");			
		dangerSet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(dangerSet);
		settingsPanel.add(new JPanel());
		
		JLabel markAdult = new JLabel("술집/유흥가 : 최대값 - 술집/유흥가 장소 하나당 안전거리 100m 추가");
		JSlider slideAdult = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getAdultEntImp());
		settingsPanel.add(markAdult);
		settingsPanel.add(slideAdult);
		
		JLabel markConst = new JLabel("공사 장소 : 최대값 - 공사 장소 하나당 안전거리 200m 추가");
		JSlider slideConst = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getConstructionImp());
		settingsPanel.add(markConst);
		settingsPanel.add(slideConst);
		
		settingsPanel.add(new JPanel());
		settingsPanel.add(new JPanel());
		
		JLabel displaySet = new JLabel("표시 방식");
		displaySet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(displaySet);
		settingsPanel.add(new JPanel());
		JCheckBox seeVertices = new JCheckBox("노드 보이기", Settings.getDisplayMode()%2>0?true:false);
		JCheckBox seeEdges = new JCheckBox("간선 보이기", Settings.getDisplayMode()/2%2>0?true:false);
		JCheckBox seeNames = new JCheckBox("이름 보이기", Settings.getDisplayMode()/2/2%2>0?true:false);
		settingsPanel.add(seeVertices);
		settingsPanel.add(seeEdges);
		settingsPanel.add(seeNames);
		
		add(settingsPanel, BorderLayout.CENTER);
		
		JPanel scPanel = new JPanel();
		scPanel.setBackground(Color.LIGHT_GRAY);
		
		GridLayout scLayout = new GridLayout(1,5);
		scLayout.setVgap(5);
		scPanel.setLayout(scLayout);
		
		JButton setBtn = new JButton("설정");
		JButton cancelBtn = new JButton("취소");
		JButton helpBtn = new JButton("도움말");
		JButton adminBtn = new JButton("관리자 설정");
		JButton aboutUsBtn = new JButton("About Us");
		setBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Settings.setDisplayMode((seeVertices.isSelected()?1:0)+(seeEdges.isSelected()?2:0)+(seeNames.isSelected()?4:0));
				Settings.setCctvImp(slideCCTV.getValue());
				Settings.setShelterImp(slideShelter.getValue());
				Settings.setConvenienceImp(slideConv.getValue());
				Settings.setWidthImp(slideWidth.getValue());
				Settings.setBrightnessImp(slideBrightness.getValue());
				Settings.setAdultEntImp(slideAdult.getValue());
				Settings.setConstructionImp(slideConst.getValue());
				setVisible(false);
				frame.repaint();
			}
		});
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		helpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "지도는 시연을 위해 임의로 만들어졌으며\n각각의 표시들은 다음과 같은 시설을 의미합니다\n빨간색 원 : 공사장소\n주황색 원 : 술집/유흥가\n초록색 원 : CCTV\n파란색 원 : 24시간 편의점\n보라색 원 : 여성안심지킴이집\n검은색 실선 : 밝기가 낮은 길", "도움말", JOptionPane.PLAIN_MESSAGE);
			}
		});
		adminBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
        		adminDialog = new AdministratorDialog(frame,"관리자 페이지", mg, scaleRatio);
        		adminDialog.setVisible(true);
			}
		});
		aboutUsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "캐발보안의 해적", "About Us", JOptionPane.PLAIN_MESSAGE);
			}
		});
		scPanel.add(setBtn);
		scPanel.add(cancelBtn);
		scPanel.add(helpBtn);
		scPanel.add(adminBtn);
		scPanel.add(aboutUsBtn);
		
		add(scPanel, BorderLayout.SOUTH);
		
		setSize(750,600);
		setLocationByPlatform(true);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(false);
	}
}
