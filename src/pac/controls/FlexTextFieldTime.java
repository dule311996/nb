/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac.controls;

import java.awt.Toolkit;
import javafx.scene.control.TextField;

/**
 *
 * @author GvozdenSSD
 */
public class FlexTextFieldTime extends TextField{
    
    public FlexTextFieldTime(String init) {
        super(init);
        focusedProperty().addListener((o, oldV, newV) -> changed(newV));
    }

    public FlexTextFieldTime() {
        this("23:59");
    }

    private void changed(boolean focus) {
        if (!focus) {
            if (!validate()) { 
                setText("12:00");
                selectAll();
                requestFocus();
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    public boolean validate()  {
        return getText()
        .matches("(0?[0-9]|1[0-9]|2[0-3]):[0-5][0-9](:[0-5][0-9])?");
    }    
}
