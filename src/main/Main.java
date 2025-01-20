package main;

import main.Controller.Controller;
import main.View.View;

public class Main {
	public static void main(String[] args) {
		View view = new View();
		new Controller(view);
	}
}