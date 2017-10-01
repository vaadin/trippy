package com.vaadin.trippy;

import com.vaadin.ui.Component;
import com.vaadin.ui.Tag;
import com.vaadin.ui.common.HtmlImport;

@HtmlImport("DirectionSearch.html")
@Tag("direction-search")
public class DirectionSearch extends Component {
    public void setStart(String start) {
        getElement().setProperty("start", start);
    }

    public void setEnd(String end) {
        getElement().setProperty("end", end);
    }
}
