
package cz.oz.web._pg.doclist;

import cz.oz.web._pg.JTexyPage;
import cz.oz.web.model.TexyDoc;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

/**
 *
 *  @author Ondrej Zizka
 */
public class TexyDocStripePanel extends Panel {

    public TexyDocStripePanel( String id, final IModel<TexyDoc> docModel ) {
        super( id, new CompoundPropertyModel<TexyDoc>(docModel) );

        add( new Link("link") {
                @Override public void onClick() {
                    String path = getTexyDocFromModel().getPath();
                    setResponsePage( new JTexyPage( path ) );
                }
            }
            .add( new Label("titleOrFileName") )
        );
        add( new Label("added") );
        add( new Label("author") );
        add( new Label("path") );
    }

    TexyDoc getTexyDocFromModel(){
        return (TexyDoc) this.getDefaultModelObject();
    }
}
