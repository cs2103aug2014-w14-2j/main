package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.event.EventHandler;

public class UiComponent {

    private Scene scene;
    private BorderPane rootPane;
    private TextArea timeTaskedDisplay, floatingTaskedDisplay, summaryDisplay, pendingDisplay;
    private TextField cmdInput;
    public String input_text;  //added
    public String output_text = ""; //added
    public Parser parser;    //added
    public Command cmd;// added
    public int floating_task_counter = 0; //added
    public ArrayList<String> floating_tasks = new ArrayList<String>();

    public UiComponent() {
        initializeComponents();
        setupScene();
        initializeStyleSheetToComponents();
    }

    private void initializeStyleSheetToComponents() {
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        rootPane.getStyleClass().add("rootPane");
    }

    private void setupScene() {
        scene = new Scene(rootPane,950,500);
    }

    private void initializeComponents() {
        rootPane = new BorderPane();
        rootPane.setLeft(getLeftHBox());
        rootPane.setCenter(getCenterHBox());
        rootPane.setRight(getRightHBox());
        rootPane.setBottom(getBottomHBox());
    }

    private HBox getBottomHBox() {
        HBox hbox = new HBox();

        VBox vbox = new VBox(-10);
        vbox.setAlignment(Pos.BASELINE_LEFT);
        vbox.setPadding(new Insets(0,15,25,15));

        cmdInput = new TextField();
        cmdInput.setPrefColumnCount(900);
        cmdInput.setFocusTraversable(false);
        cmdInput.setPrefHeight(35);

        cmdInput.setPromptText("Write here");

        cmdInput.setOnKeyPressed(new EventHandler<KeyEvent>()
                {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    input_text = cmdInput.getText();
                    Controller.runCommandInput(input_text);
                    
                    cmdInput.clear();
                    rootPane.setCenter(getCenterHBox());   // display what is entered on Floating Task Box
                }
            }
                });

        input_text = cmdInput.getText();   //retrieve input text

        Text suggestionText = new Text(" Command : ");
        suggestionText.setTextAlignment(TextAlignment.JUSTIFY);
        suggestionText.setFont(Font.font("Ariel", FontWeight.NORMAL, 15));

        vbox.getChildren().addAll(suggestionText,cmdInput);
        hbox.getChildren().addAll(vbox);

        return hbox;
    }

    private HBox getRightHBox() {
        HBox hbox = new HBox();

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15,20,0,-20));

        summaryDisplay = new TextArea();
        summaryDisplay.setPrefRowCount(10);
        summaryDisplay.setPrefColumnCount(100);
        summaryDisplay.setWrapText(true);
        summaryDisplay.setPrefWidth(300);
        summaryDisplay.setPrefHeight(250);
        summaryDisplay.setEditable(false);
        summaryDisplay.setFocusTraversable(false);

        pendingDisplay = new TextArea();
        pendingDisplay.setPrefRowCount(10);
        pendingDisplay.setPrefColumnCount(100);
        pendingDisplay.setWrapText(true);
        pendingDisplay.setPrefWidth(300);
        pendingDisplay.setPrefHeight(250);
        pendingDisplay.setText("SURPRISE");
        pendingDisplay.setEditable(false);
        pendingDisplay.setFocusTraversable(false);

        vbox.getChildren().addAll(summaryDisplay,pendingDisplay);
        hbox.getChildren().addAll(vbox);

        return hbox;
    }

    private HBox getCenterHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 0, 0, -20));
        hbox.setMaxHeight(500);
        hbox.setMaxWidth(300);

        floatingTaskedDisplay = new TextArea();
        floatingTaskedDisplay.setPrefRowCount(10);
        floatingTaskedDisplay.setPrefColumnCount(100);
        floatingTaskedDisplay.setWrapText(true);
        floatingTaskedDisplay.setPrefWidth(300);
        floatingTaskedDisplay.setEditable(false);
        floatingTaskedDisplay.setFocusTraversable(false);
        floatingTaskedDisplay.setText(output_text); //get task name from controller then display here 

        hbox.getChildren().addAll(floatingTaskedDisplay);
        return hbox;
    }

    private HBox getLeftHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 0, 0, 15));
        hbox.setMaxHeight(500);
        hbox.setMaxWidth(300);

        timeTaskedDisplay = new TextArea();
        timeTaskedDisplay.setPrefRowCount(10);
        timeTaskedDisplay.setPrefColumnCount(100);
        timeTaskedDisplay.setWrapText(true);
        timeTaskedDisplay.setPrefWidth(300);
        timeTaskedDisplay.setEditable(false);
        timeTaskedDisplay.setFocusTraversable(false);

        hbox.getChildren().addAll(timeTaskedDisplay);
        return hbox;
    }

    public Scene getScene() {
        return scene;
    }

    public void focusCommandInputBox() {
        cmdInput.requestFocus();
    }
    
    public void updateFloatingTasks(ArrayList<FloatingTask> floatingTasks) {
        output_text = "";
        for (int i = 0; i <floatingTasks.size();i++) {
            output_text += "F" + Integer.toString(i+1)+" "+floatingTasks.get(i).getDescription()+"\n";
        }
    }
    
    


}
