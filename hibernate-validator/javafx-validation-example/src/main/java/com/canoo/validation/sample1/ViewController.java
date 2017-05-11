package com.canoo.validation.sample1;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

/**
 *
 * @author Hendrik Ebbers
 */
public class ViewController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private Slider countSlider;

    @FXML
    private Button validateButton;

    @FXML
    private Label validateMessageLabel;

    public void initialize(final URL location, final ResourceBundle resources) {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Model model = new Model();
        final Validator validator = factory.getValidator();

        nameField.textProperty().bindBidirectional(model.nameProperty());
        model.countProperty().bind(Bindings.createObjectBinding(() ->
                        countSlider.valueProperty().intValue()
                , countSlider.valueProperty()));

        validateButton.setOnAction(e -> {
            final Set<ConstraintViolation<Model>> violations = validator.validate(model);



            validateMessageLabel.setText(violations.stream().map(v -> v.getMessage()).reduce("", (a, b) -> a + b));
        });
    }
}