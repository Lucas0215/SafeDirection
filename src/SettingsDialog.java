import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SettingsDialog extends JDialog {
	private MapGraph mg = null;
	private double scaleRatio = 0.75;
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
		
		JLabel markCCTV = new JLabel("CCTV :");	
		JSlider slideCCTV = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markCCTV);
		settingsPanel.add(slideCCTV);
		
		JLabel markShelter = new JLabel("�����Ƚ���Ŵ���� :");
		JSlider slideShelter = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markShelter);
		settingsPanel.add(slideShelter);
		
		JLabel markConv = new JLabel("24�� ������ :");
		JSlider slideConv = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markConv);
		settingsPanel.add(slideConv);
		JLabel markWidth = new JLabel("���� �� :");	
		JSlider slideWidth = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markWidth);
		settingsPanel.add(slideWidth);
		JLabel markIllum = new JLabel("���� ��� :");
		JSlider slideIllum = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markIllum);
		settingsPanel.add(slideIllum);
		
		settingsPanel.add(new JPanel());
		settingsPanel.add(new JPanel());
		
		JLabel dangerSet = new JLabel("������ �߿䵵");			
		dangerSet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(dangerSet);
		settingsPanel.add(new JPanel());
		
		JLabel markAdult = new JLabel("����/���ﰡ :");
		JSlider slideAdult = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markAdult);
		settingsPanel.add(slideAdult);
		JLabel markConst = new JLabel("���� ��� :");
		JSlider slideConst = new JSlider(JSlider.HORIZONTAL,0,100,50);
		settingsPanel.add(markConst);
		settingsPanel.add(slideConst);
		
		settingsPanel.add(new JPanel());
		settingsPanel.add(new JPanel());
		
		JLabel displaySet = new JLabel("ǥ�� ���");
		displaySet.setFont(new Font("",Font.BOLD,20));
		settingsPanel.add(displaySet);
		settingsPanel.add(new JPanel());
		JCheckBox seeVertices = new JCheckBox("��� ���̱�", false);
		JCheckBox seeEdges = new JCheckBox("���� ���̱�", false);
		JCheckBox seeNames = new JCheckBox("�̸� ���̱�", false);
		seeVertices.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
               if(e.getStateChange()==ItemEvent.DESELECTED);
            }
         });
		settingsPanel.add(seeVertices);
		settingsPanel.add(seeEdges);
		settingsPanel.add(seeNames);
		
		add(settingsPanel, BorderLayout.CENTER);
		
		JPanel scPanel = new JPanel();
		scPanel.setBackground(Color.LIGHT_GRAY);
		
		GridLayout scLayout = new GridLayout(1,2);
		scLayout.setVgap(5);
		scPanel.setLayout(scLayout);
		
		JButton setBtn = new JButton("����");
		JButton cancelBtn = new JButton("���");
		JButton adminBtn = new JButton("������ ������");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		scPanel.add(setBtn);
		scPanel.add(cancelBtn);
		scPanel.add(adminBtn);
		
		add(scPanel, BorderLayout.SOUTH);
		
		setSize(500,600);
		setLocationByPlatform(true);
		setModalityType(DEFAULT_MODALITY_TYPE);
		setResizable(false);
	}
}
