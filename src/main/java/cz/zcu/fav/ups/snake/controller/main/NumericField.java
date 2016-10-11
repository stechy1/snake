package cz.zcu.fav.ups.snake.controller.main;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

import java.text.NumberFormat;

public class NumericField extends TextField {

    private final IntegerProperty number = new SimpleIntegerProperty();

    public NumericField() {
        super();

        init();
    }

    public NumericField(String text) {
        super(text);

        init();
    }

    private void init() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        Bindings.bindBidirectional(textProperty(), number, new NumberStringConverter(format));
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text) {
        return text.matches("[0-9]*");
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }
}
