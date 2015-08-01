package com.parlare;

import java.io.IOException;

import com.parlare.client.Client;

public class ClientStart {
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		Client client = new Client();
		client.setVisible(true);
		client.start();
	}
}
