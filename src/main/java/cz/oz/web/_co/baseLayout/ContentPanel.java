
package cz.oz.web._co.baseLayout;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 *  @author Ondrej Zizka
 */
public class ContentPanel extends Panel {

    //@Inject private EntityManager em;

    public ContentPanel( String id ) {
        super( id );
    }

}
