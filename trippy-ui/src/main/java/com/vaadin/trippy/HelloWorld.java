package com.vaadin.trippy;

import com.vaadin.flow.model.TemplateModel;
import com.vaadin.router.Route;
import com.vaadin.ui.Tag;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.common.StyleSheet;
import com.vaadin.ui.polymertemplate.PolymerTemplate;

@Route("hello")
@HtmlImport("HelloWorld.html")
@StyleSheet("../styles.css")
@Tag("hello-world")
public class HelloWorld extends PolymerTemplate<TemplateModel> {

}
