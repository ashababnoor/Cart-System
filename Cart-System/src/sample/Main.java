package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

class MenuItem{
    String name;
    int quantity;
    double price;

    MenuItem(String name, int quantity, double price){
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    double totalPrice(){
        return quantity*price;
    }
}

class AddItemScene extends GridPane {
    public AddItemScene(Stage primaryStage, ArrayList<MenuItem> items){
        GridPane grid = this;
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(15);

        TextField itemField = new TextField();
        itemField.setPromptText("Enter item name");
        grid.add(itemField, 0, 0);


        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        grid.add(quantityField, 0,1);

        TextField priceField = new TextField();
        priceField.setPromptText("Enter unit price");
        grid.add(priceField, 0, 2);

        Button addToCartButton = new Button("Add to cart");
        grid.add(addToCartButton, 0, 4);

        addToCartButton.setOnAction(e-> {
            String itemName = itemField.getText();
            int quantity;
            double price;

            try{
                quantity = Integer.parseInt(quantityField.getText());
            }catch (Exception exception){
                quantity = 0;
            }
            try{
                price = Double.parseDouble(priceField.getText());
            }catch (Exception exception){
                price = 0.0;
            }

            MenuItem item = new MenuItem(itemName, quantity, price);
            if (item.name.length() != 0){
                items.add(item);
            }

            Scene cartScene = new Scene(new CartScene(primaryStage, items), 500, 400);
            primaryStage.setScene(cartScene);
            primaryStage.setTitle("Welcome to your Cart");
            primaryStage.show();
        });
    }
}

class CartScene extends GridPane{

    double totalPriceOfAllItems(ArrayList<MenuItem> items){
        double total = 0;
        for (MenuItem item:items){
            total += item.totalPrice();
        }
        return total;
    }

    public CartScene(Stage primaryStage, ArrayList<MenuItem> items){
        GridPane layout = this;
        layout.setVgap(5);
        layout.setHgap(40);

        layout.setAlignment(Pos.BASELINE_LEFT);
        Button addNewItemButton = new Button("Add New Item");
        layout.add(addNewItemButton, 0, 0);

        layout.setAlignment(Pos.CENTER);
        layout.add(new Label(" "), 0, 1); //empty line for decoration purposes

        Label menu = new Label("Menu Item");
        layout.add(menu, 0, 2);
        Label quantityLabel = new Label("Quantity");
        layout.add(quantityLabel, 1, 2);
        Label priceLabel = new Label("Price");
        layout.add(priceLabel, 2, 2);

        int itemNum = items.size();

        for (int i=0; i<itemNum; i++){
            Label itemName = new Label(items.get(i).name);
            Label quantity = new Label(items.get(i).quantity+"");
            Label price = new Label(items.get(i).totalPrice()+"");

            layout.add(itemName, 0, (i+3));
            layout.add(quantity, 1, (i+3));
            layout.add(price, 2, (i+3));
        }

        String totalPrice = totalPriceOfAllItems(items)+"";
        Label totalPriceLabel = new Label("Total Price: ");
        Label totalAmount = new Label(totalPrice);

        layout.add(totalPriceLabel, 0, (itemNum+3));
        layout.add(totalAmount, 2, (itemNum+3));

        addNewItemButton.setOnAction(e->{
            Scene scene = new Scene(new AddItemScene(primaryStage, items), 500, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Welcome to your Cart");
            primaryStage.show();
        });
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Welcome to your Cart");
        primaryStage.getIcons().add(new Image("cart.png"));

        ArrayList<MenuItem> items = new ArrayList<>();

        primaryStage.setScene(new Scene(new CartScene(primaryStage, items), 500, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
