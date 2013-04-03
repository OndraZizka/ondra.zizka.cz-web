package cz.oz.web._pg.jtexy;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 *  @author Ondrej Zizka
 */
public class TexyDocumentErrorPanel extends Panel {

    
    public TexyDocumentErrorPanel( String id, String title, String message, int httpStatus ) {
        super( id );

        add( new Label("substituteTitle", title));
        add( new Label("body", message ));
        getWebResponse().setStatus(httpStatus);
    }// const

}// class TexyDocumentPanel
