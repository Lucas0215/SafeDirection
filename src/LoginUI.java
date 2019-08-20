import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

class LoginUI extends JFrame {
   TextField IDBox = new TextField(30);
   TextField passwordBox = new TextField(30);

   public LoginUI() {
      setTitle("안전 길찾기 ver1.0");
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocation(550, 200);
      setLayout(null);

      JLabel loginLabel = new JLabel("LOGIN");
      loginLabel.setFont(new Font("돋움", Font.PLAIN, 50));
      loginLabel.setBounds(10, 10, 200, 50);
      add(loginLabel);

      JLabel IDLabel = new JLabel("ID               : ");
      JLabel passwordLabel = new JLabel("Password : ");
      IDLabel.setBounds(40, 100, 70, 20);
      passwordLabel.setBounds(40, 150, 70, 20);
      add(IDLabel);
      add(passwordLabel);

      IDBox.setFont(new Font("돋움", Font.PLAIN, 20));
      passwordBox.setFont(new Font("돋움", Font.PLAIN, 20));
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

      JButton registerBtn = new JButton("회원가입");
      JButton signInBtn = new JButton("로그인");
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
      if (searchUser(IDBox.getText(), passwordBox.getText())) {
         dispose();
         MapGraph graph = new MapGraph();
         SafeDirectionGUI gui = new SafeDirectionGUI(graph, new int[7]);
      }
   }

   public boolean searchUser(String id, String password) {
      // 만들어야할 것
      return true;
   }
}

class RegisterFrame extends JFrame {
   public RegisterFrame() {
      setTitle("안전 길찾기 ver1.0");
      setLocation(530, 180);
      setLayout(null);
      setAlwaysOnTop(true);

      JLabel registerLabel = new JLabel("회원가입");
      registerLabel.setFont(new Font("돋움", Font.PLAIN, 50));
      registerLabel.setBounds(140, 10, 400, 50);
      add(registerLabel);

      JLabel IDLabel = new JLabel("ID               : ");
      JLabel passwordLabel = new JLabel("Password : ");
      IDLabel.setBounds(40, 100, 70, 20);
      passwordLabel.setBounds(40, 150, 70, 20);
      add(IDLabel);
      add(passwordLabel);

      TextField IDBox = new TextField(100);
      TextField passwordBox = new TextField(100);
      IDBox.setFont(new Font("돋움", Font.PLAIN, 20));
      passwordBox.setFont(new Font("돋움", Font.PLAIN, 20));
      IDBox.setBounds(110, 97, 300, 30);
      passwordBox.setBounds(110, 147, 300, 30);
      passwordBox.setEchoChar('*');
      add(IDBox);
      add(passwordBox);

      JButton registerBtn = new JButton("가입하기");
      JButton cancelBtn = new JButton("취소");
      registerBtn.setBounds(20, 200, 220, 40);
      cancelBtn.setBounds(240, 200, 220, 40);
      registerBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            register(IDBox.getText(), passwordBox.getText());
            dispose();
         }
      });
      cancelBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });
      add(registerBtn);
      add(cancelBtn);

      setSize(500, 300);
      setResizable(false);
      setVisible(true);
   }
   
   private void register(String ID, String password) {
      //만들어야할것
   }
}