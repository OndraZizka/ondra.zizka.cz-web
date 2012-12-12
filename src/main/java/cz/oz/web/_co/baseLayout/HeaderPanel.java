
package cz.oz.web._co.baseLayout;

import cz.oz.web._pg.ICountablePage;
import cz.oz.web.util.Svatek;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 *
 *  @author Ondrej Zizka
 */
public class HeaderPanel extends Panel {

    public HeaderPanel( String id ) {
        super( id );

        // Datum, svátek.
        Date now  = new Date(this.getRequestCycle().getStartTime());
        Date tmrw = new Date(this.getRequestCycle().getStartTime() + 24*3600*1000 );
        add( new Label("datum", new SimpleDateFormat().format( now ) ));
        add( new Label("svatek",      Svatek.getSvatekByDate( now ) ));
        add( new Label("svatekZitra", Svatek.getSvatekByDate( tmrw    ) ));

        // Nadpis, počitadlo.
        add( new Label("title", "Ondra<span>.</span>Zizka<span>.</span>cz").setEscapeModelStrings(false) );
        //add( new Label("seenCount", new SeenCountModel(this.getPage()) ));
        add( new Label("seenCount", "12345" ));
    }


    /**
     *  Model for the counter, giving number of views for given page.
     *  @see ICountablePage
     */
    private static class SeenCountModel extends AbstractReadOnlyModel<String> {

        @Inject private EntityManager em;

        private Page page;


        private SeenCountModel( Page page ) {
            this.page = page;
        }

        @Override
        public String getObject() {

            String pageCounterId = (page instanceof ICountablePage) 
                    ? ((ICountablePage)page).getCounterId()
                    : page.getClass().getName();

            page.getClass();
            Integer count = (Integer) em.createNativeQuery("SELECT count FROM counter WHERE id=:id")
                    .setParameter("id", pageCounterId)
                    .getSingleResult();

            return count.toString();
        }

    }// SeenCountModel

}
