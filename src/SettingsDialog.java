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
		
		JLabel importanceSet = new JLabel("������� �߿䵵");
		importanceSet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(importanceSet);
		settingsPanel.add(new JPanel());
		
		JLabel markCCTV = new JLabel("CCTV : �ִ밪 - CCTV ����(20m) ���� ������ �ʴ� �� 10m�� �����Ÿ� 1m �߰�");	
		JSlider slideCCTV = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getCctvImp());
		settingsPanel.add(markCCTV);
		settingsPanel.add(slideCCTV);
		
		JLabel markShelter = new JLabel("�����Ƚ���Ŵ���� : �ִ밪 - �����Ƚ���Ŵ���� ����(50m) ���� ������ �ʴ� �� 10m�� �����Ÿ� 1m �߰�");
		JSlider slideShelter = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getShelterImp());
		settingsPanel.add(markShelter);
		settingsPanel.add(slideShelter);
		
		JLabel markConv = new JLabel("24�� ������ : �ִ밪 - ������ ����(50m) ���� ������ �ʴ� �� 10m�� �����Ÿ� 1m �߰�");
		JSlider slideConv = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getConvenienceImp());
		settingsPanel.add(markConv);
		settingsPanel.add(slideConv);
		
		JLabel markWidth = new JLabel("���� �� : �ִ밪 - ���� ���� 1m �پ�� ������ �����Ÿ� �� ���̸�ŭ �߰�");	
		JSlider slideWidth = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getWidthImp());
		settingsPanel.add(markWidth);
		settingsPanel.add(slideWidth);
		
		JLabel markBrightness = new JLabel("���� ��� : �ִ밪 - ���� ��Ⱑ 0.1 �پ�� ������ �����Ÿ� �� ������ 1/5��ŭ �߰�");
		JSlider slideBrightness = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getBrightnessImp());
		settingsPanel.add(markBrightness);
		settingsPanel.add(slideBrightness);
		
		settingsPanel.add(new JPanel());
		settingsPanel.add(new JPanel());
		
		JLabel dangerSet = new JLabel("������ �߿䵵");			
		dangerSet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(dangerSet);
		settingsPanel.add(new JPanel());
		
		JLabel markAdult = new JLabel("����/���ﰡ : �ִ밪 - ����/���ﰡ ��� �ϳ��� �����Ÿ� 100m �߰�");
		JSlider slideAdult = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getAdultEntImp());
		settingsPanel.add(markAdult);
		settingsPanel.add(slideAdult);
		
		JLabel markConst = new JLabel("���� ��� : �ִ밪 - ���� ��� �ϳ��� �����Ÿ� 200m �߰�");
		JSlider slideConst = new JSlider(JSlider.HORIZONTAL,0,100,Settings.getConstructionImp());
		settingsPanel.add(markConst);
		settingsPanel.add(slideConst);
		
		settingsPanel.add(new JPanel());
		settingsPanel.add(new JPanel());
		
		JLabel displaySet = new JLabel("ǥ�� ���");
		displaySet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(displaySet);
		settingsPanel.add(new JPanel());
		JCheckBox seeVertices = new JCheckBox("��� ���̱�", Settings.getDisplayMode()%2>0?true:false);
		JCheckBox seeEdges = new JCheckBox("���� ���̱�", Settings.getDisplayMode()/2%2>0?true:false);
		JCheckBox seeNames = new JCheckBox("�̸� ���̱�", Settings.getDisplayMode()/2/2%2>0?true:false);
		settingsPanel.add(seeVertices);
		settingsPanel.add(seeEdges);
		settingsPanel.add(seeNames);
		
		add(settingsPanel, BorderLayout.CENTER);
		
		JPanel scPanel = new JPanel();
		scPanel.setBackground(Color.LIGHT_GRAY);
		
		GridLayout scLayout = new GridLayout(1,5);
		scLayout.setVgap(5);
		scPanel.setLayout(scLayout);
		
		JButton setBtn = new JButton("����");
		JButton cancelBtn = new JButton("���");
		JButton helpBtn = new JButton("����");
		JButton adminBtn = new JButton("������ ����");
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
				JOptionPane.showMessageDialog(null, "������ �ÿ��� ���� ���Ƿ� �����������\n������ ǥ�õ��� ������ ���� �ü��� �ǹ��մϴ�\n������ �� : �������\n��Ȳ�� �� : ����/���ﰡ\n�ʷϻ� �� : CCTV\n�Ķ��� �� : 24�ð� ������\n����� �� : �����Ƚ���Ŵ����\n������ �Ǽ� : ��Ⱑ ���� ��", "����", JOptionPane.PLAIN_MESSAGE);
			}
		});
		adminBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
        		adminDialog = new AdministratorDialog(frame,"������ ������", mg, scaleRatio);
        		adminDialog.setVisible(true);
			}
		});
		aboutUsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "ĳ�ߺ����� ����", "About Us", JOptionPane.PLAIN_MESSAGE);
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
