package cz.oz.web.pages;

import org.apache.wicket.Session;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.CssResourceReference;
import org.jboss.essc.web._cp.pagePanes.HeaderPanel;
import org.jboss.essc.web._cp.pagePanes.SidebarPanel;
import cz.oz.web.security.EsscAuthSession;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;


/**
 *  Base layout of all pages in this app.
 * 
 *  @author Ondrej Zizka
 */
public class BaseLayoutPage extends WebPage {


    // Set up the dynamic behavior for the page, widgets bound by id
    public BaseLayoutPage() {
        
        add( new DebugBar("debugBar") );
        
        add( new HeaderPanel("header") );
        
        add( new SidebarPanel("sidebar") );
        
    }
    
    
    /**
     *  Global helper to avoid casting everywhere.
     */
    public EsscAuthSession getSession(){
        return (EsscAuthSession) Session.get();
    }
    
    
    /** Adds CSS reference. */
    public void renderHead(IHeaderResponse response) {
        //response.renderCSSReference(new CssResourceReference( BaseLayoutPage.class, "default.css" )); // Wicket 1.5
        response.render( CssHeaderItem.forReference(new CssResourceReference( BaseLayoutPage.class, "default.css" ))); // Wicket 1.6
    }

}// class
