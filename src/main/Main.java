package main;

import main.Model.TextSimilarity;
import main.View.View;
import main.Controller.Controller;

public class Main {
    public static void main(String[] args) {
        TextSimilarity model = new TextSimilarity();
        View view = new View();
        new Controller(model, view);
    }

}
