/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication25;

import java.awt.Event;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.management.remote.rmi._RMIConnection_Stub;
import pac.controls.FlexTextField;
import tornadofx.control.DateTimePicker;

/**
 *
 * @author GvozdenSSD
 */
public class JavaFXApplication25 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        
        //prvi se izvrsava handler
        //moze imati vise handlera na isti dogadjaj
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            System.out.println("Event4");
        });
        
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            System.out.println("Event5");
        });
        
//        btn.setOnAction((event) -> {
//            System.out.println("Event1");
//        });
        
//        btn.setOnMouseClicked((event) -> {
//            System.out.println("Event2");
//        });
        


        DecimalFormat df = new DecimalFormat("###,###.##");
        Format f = new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } ;
        
        
        
        FlexTextField ftf = new FlexTextField(20, false, false, 0, "", "", "dd");
        

        TextField tf = new TextField();
        tf.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) -> {
            if(newPropertyValue){
                System.out.println("Textfield on focus");
            }
            else{
                System.out.println("Textfield out focus");
                Double broj = Double.parseDouble(tf.getText().replaceAll(",", ""));
                
                tf.setText(df.format(Double.parseDouble(broj.toString())));
            }
        });
        
//        tf.setOnKeyReleased((event) -> {
//            tf.setText(df.format(Double.parseDouble(tf.getText())));
//        });
        
//        tf.setText(df.format(Double.parseDouble(tf.getText())));
//        
//        tf.getText();
        
        DateTimePicker cf = new DateTimePicker();
        cf.setFormat("dd.MM.yyyy HH:mm");
        
//        cf.setConverter(new StringConverter<LocalDate>(){
//            private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.yyyy HH:MM");
//                @Override
//                public String toString(LocalDate localDate){
//                    if(localDate==null)
//                        return "";
//                    return dateTimeFormatter.format(localDate);
//                }
//
//                @Override
//                public LocalDate fromString(String dateString){
//                    if(dateString==null || dateString.trim().isEmpty()){
//                        return null;
//                    }
//                    return LocalDate.parse(dateString,dateTimeFormatter);
//                }
//            });
        

        VBox root = new VBox();
        root.getChildren().addAll(btn,cf);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
