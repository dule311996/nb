/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac.controls;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import pac.alerts.Alerts;

/**
 *
 * @author Dusan
 */
public class FlexTextField extends TextField{
    
    //broj karaktera
    private Integer broj_karaktera;
    //duzina polja=broj_karaktera+x
    //zabranjen unos
    private final Boolean zabranjen_unos;
    //generisanje sifre
    private final Boolean generisanje_sifre;
    //za generisanje sifre
    private final Integer duzina_sifre;
    private final String tabela;
    private final String polje;
    //tip polja(i-int, c-string, d-decimal, dd-2decimanamesta)
    private final String tip_polja;
    
    
    public FlexTextField(Integer broj_karaktera, Boolean zabranjen_unos, Boolean generisanje_sifre, Integer duzina_sifre, String tabela, String polje, String tip_polja){
        this.broj_karaktera = broj_karaktera;
        this.zabranjen_unos = zabranjen_unos;
        this.generisanje_sifre = generisanje_sifre;
        this.duzina_sifre = duzina_sifre;
        this.tabela = tabela;
        this.polje = polje;
        this.tip_polja = tip_polja;
        initialize();
    }

    public FlexTextField() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void setBrKaraktera(){
        if(broj_karaktera == null){
            broj_karaktera = 10;
        }
        Pattern pattern = Pattern.compile(".{0,"+broj_karaktera+"}");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        setTextFormatter(formatter);
    }
    
    private void setDuzina(){
        //font15
        //35=osnova 15=1 karakter
        
        int duzina = 35;
        if(broj_karaktera > 1){
            broj_karaktera--;
            duzina = 35+(broj_karaktera*15);
        }
        setMaxWidth(duzina);
        setMinWidth(35);
        
//        setPrefWidth(duzina);
        
//        setMinWidth(100);
//        setPrefWidth(100);
//        textProperty().addListener((ov, prevText, currText) -> {
//        // Do this in a Platform.runLater because of Textfield has no padding at first time and so on
//            Platform.runLater(() -> {
//            Text text = new Text(currText);
//            text.setFont(getFont()); // Set the same font, so the size is the same
//            double width = text.getLayoutBounds().getWidth() // This big is the Text in the TextField
//                + getPadding().getLeft() + getPadding().getRight() // Add the padding of the TextField
//                + 2d; // Add some spacing
//            setPrefWidth(width); // Set the width
//            positionCaret(getCaretPosition()); // If you remove this line, it flashes a little bit
//            });
//        });
        
//        textProperty().addListener(new ChangeListener<String>() {
//        @Override
//        public void changed(ObservableValue<? extends String> ob, String o,
//                String n) {
//            // expand the textfield
//             setPrefWidth(TextUtils.computeTextWidth(getFont(),
//                    getText(), 0.0D) + 10);
//        }
//        });
//setMinWidth(50);
//setPrefWidth(50);
//setMaxWidth(400);
//textProperty().addListener(new ChangeListener<String>() {
//    @Override
//    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//        setPrefWidth(getText().length() * broj_karaktera); // why 7? Totally trial number.
//    }
//});
    }
    
    private void setZabranjenUnos(){
        if(zabranjen_unos == true){
            setEditable(false);
        }
    }
    
//    private void setGenerisanjeSifre(){
//        if(generisanje_sifre == true){
//            if(duzina_sifre == null || duzina_sifre == 0){
//                Alerts.getErrorAlert("FleksTextField-setGenerisanjeSifre", "Duzina sifre je null ili je jednaka nuli. Zbog toga nije moguce generisanje sifre!");
//            }else{
//                String generisana_sifra = generisiSifru(tabela, polje, duzina_sifre);
//                setText(generisana_sifra);
//            }
//        }
//    }
//    public String generisiSifru(String tabela, String polje, int duzina){
//        String sifra = "";
//        String query = " SELECT "+polje+" FROM "+tabela+"; ";
//        try{
//            ResultSet rs = dao.getResultSet(query);
//
//            if(rs.next() == false){
//                int i = 0;
//                while(duzina>i){
//                    sifra += "0";
//                    i++;
//                }
//            }else{
//                do{
//                  if(rs.isLast()){
//                      if(rs.getString(polje) == null){
//                          Alerts.getErrorAlert("FlexTextField-generisiSifru", "Nije moguce generisanje sifre! Poslednja uneta vrednost u bazi je null!");
//                      }else if(rs.getString(polje).isEmpty()){
//                          Alerts.getErrorAlert("FlexTextField-generisiSifru", "Nije moguce generisanje sifre! Poslednja uneta vrednost u bazi je prazan string!");
//                      }else{
//                            int i = Integer.parseInt(rs.getString(polje).trim())+1;
//                            sifra = Integer.toString(i);
//                            if(sifra.length() < duzina){
//                                while(sifra.length() < duzina){
//                                    sifra = "0"+sifra;
//                                }
//                            }
//                      }
//                  }
//                }while(rs.next());
//            }
//            
//        }catch(NumberFormatException | SQLException e){
//            Alerts.getExceptionAlert("FlexTextField-generisiSifru", "Generisanje sifre nije uspelo!", e);
//        }
//        
//        return sifra;
//    }
    
    private void setTip(){
        switch(tip_polja){
            case "c":
                textProperty().addListener((observable, oldValue, newValue) -> {
//                    "[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^_{|}~]+"
                    if(newValue.contains("\\")){
                        setText(oldValue);
                    }
                    //asa
                });
            break;
            
            case "i":
                textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!newValue.matches("[0-9]*")){
                        setText(oldValue);
                    }
                });
            break;
            
            case "d":
                textProperty().addListener((observable, oldValue, newValue) -> {
                    if(!newValue.matches("\\d*(\\.\\d*)?")){
                        setText(oldValue);
                    }
                    focusedProperty().addListener(new ChangeListener<Boolean>(){
                        @Override
                        public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                            if(newPropertyValue){
                                //focus in
                            }else{
                                roundDecimal();
                            }
                        }
                    });
                    setOnKeyPressed(new EventHandler<KeyEvent>(){
                        @Override
                        public void handle(KeyEvent ke){
                            if(ke.getCode().equals(KeyCode.ENTER)){
                                roundDecimal();
                            }
                        }
                    });
                });
            break;
            
            case "dd":
                textProperty().addListener((observable, oldValue, newValue) -> {
                    if(newValue != null){
                        if(!newValue.matches("\\d*(\\.\\d*)?")){
                            setText(oldValue);
                        }
                    }
                });
                focusedProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
                        if(newPropertyValue){
                            //focus in
                        }else{
                            //focus out
                            roundTwoDecimal();
                        }
                    }
                });
                setOnKeyPressed(new EventHandler<KeyEvent>(){
                    @Override
                    public void handle(KeyEvent ke){
                        if(ke.getCode().equals(KeyCode.ENTER)){
                            roundTwoDecimal();
                        }
                    }
                });
            break;
            
            default:
            break;
        }
    }
    
    private void roundDecimal(){
        if(getText() != null){
            if(!(getText().isEmpty())){
                if(getText().equals(".")){
                    setText("0"+getText()+"0");
                }
                Double decimal = Double.parseDouble(getText());
                setText(decimal.toString());
            }
        }  
    }
    
    private void roundTwoDecimal(){
        if(getText() != null){
            if(!(getText().isEmpty())){
                if(!getText().equals("")){
                    if(getText().equals(".")){
                        double value = 0;
                        value = Math.round(value*100.0)/100.0;
                        setText(Double.toString(value));
                    }else{
                        double value = Double.parseDouble(getText());
                        value = Math.round(value*100.0)/100.0;
                        setText(Double.toString(value));
                    }
                } 
            }
        } 
    }
    
    
    private void initialize(){
        setBrKaraktera();
        setDuzina();
        setZabranjenUnos();
        setTip();
    }
   
}
