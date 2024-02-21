package com.swingcrud;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.*;
import javax.swing.table.TableModel;

public class App {

	private JFrame app;
	private JTextField nI;
	private JTextField eI;
	private JTable table;
	private int UID = -1;

	private Connection conn = null;
	private PreparedStatement pst;
	private ResultSet rs;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.app.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public App() {
		initialize();
		connect();
		load();
	}

	private void load() {
		try {
			pst = conn.prepareStatement("SELECT * FROM user_master;");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void connect() {
		final String JDBC_URL = "jdbc:mysql://localhost:3306/SWING_CRUD";
		final String USER = "root";
		final String PASSWORD = "system";
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
			System.out.println("Connected to the database");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		conn = connection;
	}

	private void initialize() {
		app = new JFrame();
		app.setTitle("IDK! Put Anything!");
		app.getContentPane().setFont(new Font("Garamond", Font.PLAIN, 14));
		app.getContentPane().setLayout(null);
		app.setSize(450, 700);

		JLabel label = new JLabel("UMS");
		label.setBounds(220, 20, 200, 60);
		app.getContentPane().add(label);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(20, 20, 400, 220);
		app.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel name = new JLabel("Name");
		name.setBounds(100, 80, 100, 35);
		panel.add(name);

		JLabel mail = new JLabel("Mail");
		mail.setBounds(100, 115, 100, 35);
		panel.add(mail);

		nI = new JTextField();
		nI.setBounds(150, 80, 200, 25);
		panel.add(nI);

		eI = new JTextField();
		eI.setBounds(150, 115, 200, 25);
		panel.add(eI);

		JButton save = new JButton("SAVE");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String getName = nI.getText();
				String getEmail = eI.getText();
				if (getName == null || getName.isEmpty() || getName.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Name can't be empty!");
					nI.requestFocus();
					return;
				}
				if (getEmail == null || getEmail.isEmpty() || getEmail.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Email can't be empty!");
					eI.requestFocus();
					return;
				}
				if (!Validator.isValidEmail(getEmail)) {
					JOptionPane.showMessageDialog(null, "Email is Invalid!");
					eI.requestFocus();
					return;
				}
				try {
					String query = "INSERT INTO user_master (name,email) VALUES(?,?)";
					pst = conn.prepareStatement(query);
					pst.setString(1, getName);
					pst.setString(2, getEmail);
					if (pst.executeUpdate() >= 1) {
						JOptionPane.showMessageDialog(null, "Data Inserted!");
					} else {
						JOptionPane.showMessageDialog(null, "Error occurred");
					}
					ClearData();
					load();

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Error occurred");
				}
			}
		});
		save.setBounds(110, 175, 70, 30);
		panel.add(save);

		JButton update = new JButton("Update");
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String getName = nI.getText();
				String getEmail = eI.getText();
				if (getName == null || getName.isEmpty() || getName.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Name can't be empty!");
					nI.requestFocus();
					return;
				}
				if (getEmail == null || getEmail.isEmpty() || getEmail.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Email can't be empty!");
					eI.requestFocus();
					return;
				}
				if (!Validator.isValidEmail(getEmail)) {
					JOptionPane.showMessageDialog(null, "Email is Invalid!");
					eI.requestFocus();
					return;
				}
				try {
					String query = "UPDATE user_master SET name=? , email=? WHERE uid=?";
					pst = conn.prepareStatement(query);
					pst.setString(1, getName);
					pst.setString(2, getEmail);
					pst.setInt(3, UID);
					if (pst.executeUpdate() >= 1) {
						JOptionPane.showMessageDialog(null, "Data Updated!");
					} else {
						JOptionPane.showMessageDialog(null, "Error occurred");
					}
					ClearData();
					load();

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Error occurred");
				}
			}
		});
		update.setBounds(190, 175, 80, 30);
		update.setEnabled(false);
		panel.add(update);

		JButton clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClearData();
				save.setEnabled(true);
				update.setEnabled(false);
			}
		});
		clear.setBounds(30, 175, 70, 30);
		panel.add(clear);
		
		JButton delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					String query = "DELETE FROM user_master WHERE uid=?";
					pst = conn.prepareStatement(query);
					pst.setInt(1, UID);
					if (pst.executeUpdate() >= 1) {
						JOptionPane.showMessageDialog(null, "Data Deleted!");
					} else {
						JOptionPane.showMessageDialog(null, "Error occurred");
					}
					ClearData();
					load();

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Error occurred");
				}
			}
		});
		delete.setBounds(290, 175, 80, 30);
		delete.setEnabled(false);
		panel.add(delete);
		

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 270, 400, 400);
		app.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				int index = table.getSelectedRow();
				TableModel model = table.getModel();
				UID = Integer.parseInt(model.getValueAt(index, 0).toString());
				nI.setText(model.getValueAt(index, 1).toString());
				eI.setText(model.getValueAt(index, 2).toString());
				save.setEnabled(false);
				update.setEnabled(true);
				delete.setEnabled(true);
			}
		});
		table.setRowHeight(30);
		scrollPane.setViewportView(table);
//		panel.add(table);
	}

	public void ClearData() {
		nI.setText("");
		nI.requestFocus();
		eI.setText("");
		table.clearSelection();
	}
}
