
package cz.oz.web._co.baseLayout;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 *  @author Ondrej Zizka
 */
public class PocitadlaPanel extends Panel {

    public PocitadlaPanel( String id ) {
        super( id );

        String context = this.getRequest().getContextPath();
        add( new Label("contextVar", "<script type=\"text/javascript\">var context = \""+context+"\";</script>").setEscapeModelStrings(false) );
    }

}
