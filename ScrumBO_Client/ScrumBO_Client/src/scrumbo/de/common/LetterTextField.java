/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.common;

import javafx.scene.control.TextField;

/**
 *
 * @author Serdar
 */
public class LetterTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[A-Za-z]") || text == "") {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (text.matches("[A-Za-z]") || text == "") {
            super.replaceSelection(text);
        }
    }

}