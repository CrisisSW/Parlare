package com.parlare;

import java.io.IOException;
import com.parlare.server.Server;

public class ServerStart {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Server server = new Server();
		server.setVisible(true);
		server.start();
	}
}
