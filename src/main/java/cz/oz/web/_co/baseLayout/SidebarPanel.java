
package cz.oz.web._co.baseLayout;

import cz.oz.web._pg.LoginPage;
import cz.oz.web.security.OzCzAuthSession;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

/**
 *
 *  @author Ondrej Zizka
 */
public class SidebarPanel extends Panel {

    public SidebarPanel( String id ) {
        super( id );

        OzCzAuthSession sess = (OzCzAuthSession)getSession();

        // User menu box.
        add( new WebMarkupContainer("userBox"){{
            add( new Label("user", "Franta")); // TODO
            add( new Label("role", "user"));
            add( new Link(("logout")) {
                @Override public void onClick() {
                    getSession().invalidate();
                }
            });
        }}.setVisibilityAllowed( sess.isSignedIn() ) );

        // Login link
        add( new WebMarkupContainer("noUserBox"){{
            add( new BookmarkablePageLink("loginLink", LoginPage.class)
                .add( new Label("label", "Login / Register") ) );
        }}).setVisible( ! sess.isSignedIn() );


        add( new MenuPanel("menu") );
    }

}// class
