package cz.oz.web.pages;

import org.apache.wicket.Session;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.resource.CssResourceReference;
import org.jboss.essc.web._cp.pagePanes.HeaderPanel;
import org.jboss.essc.web._cp.pagePanes.SidebarPanel;
import cz.oz.web.security.EsscAuthSession;
import cz.oz.web.wicket.FavIconHeaderContributor;
import cz.oz.web.wicket.FavIconLink;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.SharedResourceReference;


/**
 *  Base layout of all pages in this app.
 * 
 *  @author Ondrej Zizka
 */
public class BaseLayoutPage extends WebPage {

    /** Adds CSS reference. */
    public void renderHead(IHeaderResponse response) {
        //response.renderCSSReference(new CssResourceReference( BaseLayoutPage.class, "default.css" )); // Wicket 1.5
        response.render( CssHeaderItem.forReference(new CssResourceReference( BaseLayoutPage.class, "_/css/default.css" ))); // Wicket 1.6
        response.render( JavaScriptHeaderItem.forReference(new JavaScriptResourceReference( BaseLayoutPage.class, "_/js/.js" )));
        //response.render( .forReference(new ResourceReference( BaseLayoutPage.class, "_/img/favicon.gif" )));
    }


    // Set up the dynamic behavior for the page, widgets bound by id
    public BaseLayoutPage() {
        add( new FavIconLink("favicon", "_/img/favicon.gif") );
        //add( new FavIconHeaderContributor( new SharedResourceReference("favion.gif") ) );

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
    
    

}// class
