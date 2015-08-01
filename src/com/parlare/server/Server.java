package com.parlare.server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Server extends JFrame implements ActionListener {

	// JComponents
	private final JButton send = new JButton("Send");
	private final JTextArea field = new JTextArea();
	private final JTextArea conversation = new JTextArea();

	// Server Stuff
	private Socket socket;
	private ServerSocket serverSocket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	// Strings
	private String message;

	public Server() throws IOException {
		super("Parlare - Server");
		componentDetails();
		frameDetails();
	}

	public void start() throws IOException, ClassNotFoundException {
		serverSocket = new ServerSocket(63444);
		while (true) {
			waitForConnection();
			setupStreams();
			getIncomingMessages();
		}
	}

	private void getIncomingMessages() throws ClassNotFoundException,
			IOException {
		do {
			message = (String) input.readObject(); 
			
	
			showMessage(message);
		
		} while(true);
	}

	private void showMessage(String message) {
		conversation.append(message);
		System.out.println(message);
	}

	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(socket.getOutputStream());
		output.flush();

		input = new ObjectInputStream(socket.getInputStream());

	}

	private void waitForConnection() throws IOException {
		socket = serverSocket.accept();
	}

	private void frameDetails() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 260);
		setResizable(false);
		setLayout(new FlowLayout());
		add(conversation);
		add(field);
		add(send);

	}

	private void componentDetails() {
		conversation.setEditable(false);
		conversation.setPreferredSize(new Dimension(618, 150));

		field.setPreferredSize(new Dimension(400, 50));
		field.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		send.setPreferredSize(new Dimension(150, 25));
		send.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(send)) {
			try {
				sendMessage(field.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			field.setText("");
		}

	}

	private void sendMessage(String message) throws IOException {
		output.writeObject("Server /n" + message); // when I, the server,
													// sends a message to
													// the client
		output.flush();

	}
}
