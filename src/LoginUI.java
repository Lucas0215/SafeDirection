import java.awt.Container;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class LoginUI extends JFrame {
   TextField IDBox = new TextField(30);
   TextField passwordBox = new TextField(30);

   public LoginUI() {
      setTitle("���� ��ã�� ver1.0");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocation(550, 200);
      setLayout(null);

      JLabel loginLabel = new JLabel("LOGIN");
      loginLabel.setFont(new Font("����", Font.PLAIN, 50));
      loginLabel.setBounds(10, 10, 200, 50);
      add(loginLabel);

      JLabel IDLabel = new JLabel("ID               : ");
      JLabel passwordLabel = new JLabel("Password : ");
      IDLabel.setBounds(40, 100, 70, 20);
      passwordLabel.setBounds(40, 150, 70, 20);
      add(IDLabel);
      add(passwordLabel);

      IDBox.setFont(new Font("����", Font.PLAIN, 20));
      passwordBox.setFont(new Font("����", Font.PLAIN, 20));
      IDBox.setBounds(110, 97, 300, 30);
      passwordBox.setBounds(110, 147, 300, 30);
      passwordBox.setEchoChar('*');
      passwordBox.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            login();
         }
      });
      add(IDBox);
      add(passwordBox);

      JButton registerBtn = new JButton("ȸ������");
      JButton signInBtn = new JButton("�α���");
      registerBtn.setBounds(20, 200, 220, 40);
      signInBtn.setBounds(240, 200, 220, 40);
      registerBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            new RegisterFrame();
         }
      });
      signInBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            login();
         }
      });
      add(registerBtn);
      add(signInBtn);

      setSize(500, 300);
      setResizable(false);
      setVisible(true);
   }

   private void login() {
      String ID = Utils.safeInput(IDBox.getText());
      String password = Utils.safeInput(passwordBox.getText());
      int userSet[] = Utils.findUser(ID, password);
      if (userSet[0] != -1) {
         Utils.ID = ID;
         dispose();
         MapGraph graph = new MapGraph();
         new SafeDirectionGUI(graph, userSet);
      } else
         JOptionPane.showMessageDialog(null, "���̵�� ��й�ȣ�� �ٸ��ϴ�.");
   }
}

class RegisterFrame extends JFrame {
   Container container;

   public RegisterFrame() {
      setTitle("���� ��ã�� ver1.0");
      setLocation(530, 180);
      setLayout(null);
      setAlwaysOnTop(true);
      container = getContentPane();

      JLabel registerLabel = new JLabel("ȸ������");
      registerLabel.setFont(new Font("����", Font.PLAIN, 50));
      registerLabel.setBounds(140, 10, 400, 50);
      add(registerLabel);

      JLabel IDLabel = new JLabel("ID                              : ");
      JLabel passwordLabel = new JLabel("Password                : ");
      JLabel passwordConfirmLabel = new JLabel("Password confirm :");
      IDLabel.setBounds(40, 100, 120, 20);
      passwordLabel.setBounds(40, 150, 120, 20);
      passwordConfirmLabel.setBounds(40, 200, 120, 20);
      add(IDLabel);
      add(passwordLabel);
      add(passwordConfirmLabel);

      TextField IDBox = new TextField(30);
      TextField passwordBox = new TextField(30);
      TextField passwordConfirmBox = new TextField(30);
      IDBox.setFont(new Font("����", Font.PLAIN, 20));
      passwordBox.setFont(new Font("����", Font.PLAIN, 20));
      passwordConfirmBox.setFont(new Font("����", Font.PLAIN, 20));
      IDBox.setBounds(160, 97, 300, 30);
      passwordBox.setBounds(160, 147, 300, 30);
      passwordConfirmBox.setBounds(160, 197, 300, 30);
      passwordBox.setEchoChar('*');
      passwordConfirmBox.setEchoChar('*');
      add(IDBox);
      add(passwordBox);
      add(passwordConfirmBox);

      JButton registerBtn = new JButton("�����ϱ�");
      JButton cancelBtn = new JButton("���");
      registerBtn.setBounds(20, 250, 245, 40);
      cancelBtn.setBounds(265, 250, 245, 40);
      registerBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            String ID = Utils.safeInput(IDBox.getText());
            String password = Utils.safeInput(passwordBox.getText());
            if (ID.length() > 15 || ID.length() < 10)
               JOptionPane.showMessageDialog(container, "ID�� 10���� 15�ڸ��� ���ڿ��̾�� �մϴ�.");
            else if (password.length() > 15 || password.length() < 10)
               JOptionPane.showMessageDialog(container, "��й�ȣ�� 10���� 15�ڸ��� ���ڿ��̾�� �մϴ�.");
            else if (password.equals(passwordConfirmBox.getText())) {
               if (Utils.addUser(container, ID, password)) {
                  JOptionPane.showMessageDialog(container, "ȸ������ ����!");
                  dispose();
               }
            } else
               JOptionPane.showMessageDialog(container, "�Ȱ��� ��й�ȣ�� �Է����ּ���.");
         }
      });
      cancelBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });
      add(registerBtn);
      add(cancelBtn);

      setSize(550, 350);
      setResizable(false);
      setVisible(true);
   }
}
