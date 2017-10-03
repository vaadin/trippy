package com.vaadin.trippy;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.Tag;
import com.vaadin.ui.UI;
import com.vaadin.ui.common.HtmlImport;

@HtmlImport("DirectionSearch.html")
@Tag("direction-search")
public class DirectionSearch extends Component {
    private DirectionSearch() {
        // Prevent accidentally constructing without associating with a UI
    }

    public static DirectionSearch getCurrent() {
        // Fetch from a session attribute as a workaround for not missing UI
        // scope support
        UI ui = UI.getCurrent();
        VaadinSession session = ui.getSession();

        String attributeName = DirectionSearch.class.getName() + ui.getUIId();
        DirectionSearch directionSearch = (DirectionSearch) session
                .getAttribute(attributeName);
        if (directionSearch == null) {
            directionSearch = new DirectionSearch();
            ui.getSession().setAttribute(attributeName, directionSearch);
        }
        return directionSearch;
    }

    public void setStart(String start) {
        getElement().setProperty("start", start);
    }

    public void setEnd(String end) {
        getElement().setProperty("end", end);
    }
}
