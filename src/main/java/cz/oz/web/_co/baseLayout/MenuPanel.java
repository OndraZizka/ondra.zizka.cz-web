
package cz.oz.web._co.baseLayout;

import cz.oz.web._pg.ICountablePage;
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
public class MenuPanel extends Panel {

    public MenuPanel( String id ) {
        super( id );

        add( new Label("title", "Ondra<span>.</span>Zizka<span>.</span>cz").setEscapeModelStrings(false) );
        add( new Label("seenCount", new SeenCountModel(this.getPage()) ));
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

    }

}
