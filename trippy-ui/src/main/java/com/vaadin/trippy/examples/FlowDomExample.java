package com.vaadin.trippy.examples;

import com.vaadin.flow.dom.Element;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.router.Route;
import com.vaadin.ui.datepicker.DatePicker;
import com.vaadin.ui.html.Div;

@Route("example")
public class FlowDomExample extends Div {

    public FlowDomExample() {

        GoogleMap map = new GoogleMap();
        map.setLocation("60.4523521","22.297538");
        add(map);


        Element input = new Element("input");
        input.setAttribute("placeholder", "JVM System Property");
        input.synchronizeProperty("value", "change");

        Element button = new Element("button");
        button.setText("Read from server");
        button.addEventListener("click", e -> {
            getElement().appendChild(ElementFactory.createDiv(
                    input.getProperty("value") + "=" +
                            System.getProperty(input.getProperty("value"))));
        });

        getElement().appendChild(input, button);


    }
}