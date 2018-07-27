package app.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JPasswordField pass;
	private JPasswordField passConfirm;

	//Constructor to draw main structure of panel
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300,300,418,312);
		contentPane=new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLoginPage = new JLabel("Login Page");
		lblLoginPage.setForeground(Color.DARK_GRAY);
		lblLoginPage.setFont(new Font("Times New Roman", Font.PLAIN, 25));
		lblLoginPage.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginPage.setBounds(40, 11, 215, 47);
		contentPane.add(lblLoginPage);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(27, 79, 63, 14);
		contentPane.add(lblUsername);

		user = new JTextField();
		user.setBounds(131, 76, 199, 20);
		contentPane.add(user);
		user.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(27, 116, 63, 14);
		contentPane.add(lblPassword);

		pass = new JPasswordField();
		pass.setBounds(131, 146, 199, 20);
		contentPane.add(pass);

		JLabel passConf = new JLabel("Password Confirm");
		passConf.setBounds(27, 149, 94, 14);
		contentPane.add(passConf);

		passConfirm = new JPasswordField();
		passConfirm.setBounds(131, 113, 199, 20);
		contentPane.add(passConfirm);

		JButton logButton = new JButton("Log in");
		logButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/loginDB","root","");
					Statement statement=con.createStatement();
					@SuppressWarnings("deprecation")
					String sql="Select * from tblogin where Username='"+user.getText()+"' and Password='"+pass.getText().toString()+"'";
					ResultSet rs=statement.executeQuery(sql);
					if(rs.next()){
						JOptionPane.showMessageDialog(null, "Login Successful");						
					}else
						JOptionPane.showMessageDialog(null, "Credentials doesn't match.");
					con.close();
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}
			}
		});
		logButton.setBounds(131, 185, 76, 23);
		getContentPane().add(logButton);

		//Create new Account button
		JButton getAccount = new JButton("New Account");
		getAccount.addActionListener(new ActionListener() {			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					Class.forName("com.mysql.jdbc.Driver");
					Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/loginDB","root","");
					Statement statement=con.createStatement();
					if(pass.getText().length()==0|| passConfirm.getText().length()==0 || user.getText().length()==0){
						JOptionPane.showMessageDialog(null, "None of couldn't blanked");
						return;
					}
					if(!pass.getText().toString().equals(passConfirm.getText().toString())){
						JOptionPane.showMessageDialog(null, "Password Confirmation Fail");
						return;
					}else{
						String sql="Select * from tblogin where Username='"+user.getText()+"' and Password='"+pass.getText().toString()+"'";
						ResultSet rs=statement.executeQuery(sql);						
						while(rs.next())
							if(rs.getString(1).equals(user.getText())){
								JOptionPane.showMessageDialog(null, "Username already exists\n Please try another one");
								return;
							}
						sql="INSERT INTO tblogin (Username,Password) VALUES ('"+user.getText()+"','"+pass.getText().toString()+"')";
						//statement.executeQuery(sql);	//Cannot do data manupilation with executeQuery()
						statement.executeUpdate(sql);
						JOptionPane.showMessageDialog(null, "Registration Successful");
					}
					con.close();
				}catch(Exception ex){
					System.out.println(ex.getMessage());
				}					
			}
		});
		getAccount.setBounds(213, 185, 117, 23);
		contentPane.add(getAccount);
	}	

	/**
	 * Launch application
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					Login frame =new Login();
					frame.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}	
}
