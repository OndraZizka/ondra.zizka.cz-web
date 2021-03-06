package cz.oz.web._co.baseLayout;

import cz.oz.web._pg.ICountablePage;
import cz.oz.web.dao.CountDao;
import org.apache.wicket.Session;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.html.WebPage;
import cz.oz.web.security.OzCzAuthSession;
import javax.inject.Inject;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;


/**
 *  Base layout of all pages in this app.
 * 
 *  @author Ondrej Zizka
 */
public class BaseLayoutPage extends WebPage {

    @Inject private CountDao countDao;


    /** Adds CSS reference. */
    public void renderHead(IHeaderResponse response) {
        //response.renderCSSReference(new CssResourceReference( BaseLayoutPage.class, "dw_styles.css" )); // Wicket 1.5
        //response.render( CssHeaderItem.forReference(new CssResourceReference( BaseLayoutPage.class, "css/dw_styles.css" ))); // Wicket 1.6
        //response.render( CssHeaderItem.forReference(new CssResourceReference( BaseLayoutPage.class, "css/styl.css" ))); // Wicket 1.6
        //response.render( JavaScriptHeaderItem.forReference(new JavaScriptResourceReference( BaseLayoutPage.class, "js/.js" )));
        //response.render( .forReference(new ResourceReference( BaseLayoutPage.class, "_/img/favicon.gif" )));
    }


    // Set up the dynamic behavior for the page, widgets bound by id
    public BaseLayoutPage() {
        //add( new FavIconLink("favicon", "favicon.gif") );
        //add( new FavIconHeaderContributor( new SharedResourceReference("favicon") ) );

        String context = this.getRequest().getContextPath();
        add( new Label("contextVar", "var context = '"+context+"';").setEscapeModelStrings(false) );

        add( new DebugBar("debugBar") );
        
        add( new HeaderPanel("header") );
        
        add( new SidebarPanel("sidebar") );

        //add( new ContentPanel("obsah") );

        add( new PocitadlaPanel("pocitadla") );
    }

    // Moved here to have the page fully instantiated.
    @Override protected void onInitialize() {
        super.onInitialize();

        // Counter increment & get.
        if( this instanceof ICountablePage ){
            ICountablePage cp = (ICountablePage) this;
            try {
                long count = countDao.getCountAfterIncrement( cp.getCounterId() );
                cp.setCount(count);
            } catch (Exception ex){
            }
        }
    }
    
    
    /**
     *  Global helper to avoid casting everywhere.
     */
    public OzCzAuthSession getSession(){
        return (OzCzAuthSession) Session.get();
    }
    
}// class
