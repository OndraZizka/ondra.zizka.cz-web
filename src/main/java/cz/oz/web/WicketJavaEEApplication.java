package cz.oz.web;

import cz.oz.web.pages.JTexyPage;
import static net.ftlines.wicket.cdi.ConversationPropagation.NONE;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.ftlines.wicket.cdi.CdiConfiguration;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Web app for http://ondra.zizka.cz
 * @author Ondrej Zizka
 */
public class WicketJavaEEApplication extends WebApplication {
    private static final Logger log = LoggerFactory.getLogger(WicketJavaEEApplication.class);
    
    private Settings settings = createSettings();

    
    @Override
    public Class<? extends Page> getHomePage() {
        return JTexyPage.class;
    }

    
    @Override
    protected void init() {
        super.init();
        
        log.info("Log Test");

        // Enable CDI
        BeanManager bm;
        try {
            bm = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
        } catch (NamingException e) {
            throw new IllegalStateException("Unable to obtain CDI BeanManager", e);
        }

        // Configure CDI, disabling Conversations as we aren't using them
        new CdiConfiguration(bm).setPropagation(NONE).configure(this);

        // Mount the InsertContact page at /insert
        //mountPage("/add", InsertContact.class);
        
        // Mount all paths to JTexy.
        mountPage("/pages", JTexyPage.class);
    }

    
    
    public Settings getSettings() {
        return settings;
    }

    
    private Settings createSettings() {
        Settings settings = new Settings();
        
        // Get the value from web.xml's <env-entry>.
        //settings.texyFilesRootPath = this.getServletContext().getResourceAsStream("texyFilesRootPath").toString();
        try {
            Context env = (Context)new InitialContext().lookup("java:comp/env");
            settings.texyFilesRootPath = (String)env.lookup("texyFilesRootPath");
        } catch( NamingException ex ){
            // TODO
        }
        
        return settings;
    }
    
}// class
