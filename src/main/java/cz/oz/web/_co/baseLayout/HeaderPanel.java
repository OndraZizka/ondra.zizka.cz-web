
package cz.oz.web._co.baseLayout;

import cz.oz.web._pg.ICountablePage;
import cz.oz.web.util.Svatek;
import java.util.Date;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 *
 *  @author Ondrej Zizka
 */
public class HeaderPanel extends Panel {

    private static final PatternDateConverter dc = new PatternDateConverter("yyyy-MM-dd", false);

    
    public HeaderPanel( String id ) {
        super( id );
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // Datum, svátek.
        Date now  = new Date(this.getRequestCycle().getStartTime());
        Date tmrw = new Date(this.getRequestCycle().getStartTime() + 24*3600*1000 );
        add( new DateLabel("datum", new Model(now), dc));
        add( new Label("svatek",      Svatek.getSvatekByDate( now ) ));
        add( new Label("svatekZitra", Svatek.getSvatekByDate( tmrw ) ));

        // Title
        add( new Label("title", "Ondra<span>.</span>Zizka<span>.</span>cz").setEscapeModelStrings(false) );
        //add( new Label("seenCount", new SeenCountModel(this.getPage()) ));

        // View counter.
        Long count = getPage() instanceof ICountablePage ? ((ICountablePage) getPage()).getCount() : null;
        add( new Label("seenCount", count).setVisibilityAllowed(count != null) );
    }

}// class
