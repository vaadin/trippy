package com.vaadin.trippy;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Route;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;

@Route("")
@HtmlImport("frontend://HelloWorld.html")
@StyleSheet("context://styles.css")
@Tag("hello-world")
public class HelloWorld extends PolymerTemplate<TemplateModel> {

}
