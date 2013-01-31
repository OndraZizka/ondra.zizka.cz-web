
package cz.oz.web._pg.doclist;

import cz.oz.web._pg.jtexy.JTexyPage;
import cz.oz.web.model.TexyDoc;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
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

    //private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // YYYY-MM-DDThh:mmTZD or YYYY-MM-DD.
    private static final DateConverter dc = new PatternDateConverter("yyyy-MM-dd", false);
    
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
        //add( DateLabel.forDatePattern("added", "yyyy-MM-dd") );
        add( new DateLabel("added", dc) );
        //add( new DateLabel("lastChanged", dc) );
        add( new Label("author") );
        add( new Label("path") );
    }

    TexyDoc getTexyDocFromModel(){
        return (TexyDoc) this.getDefaultModelObject();
    }
}
