package cz.oz.web;

import cz.oz.web.util._co.LogResourceReference;
import cz.oz.web._pg.JTexyPage;
import cz.oz.web._pg.JTexyTestPage;
import cz.oz.web.model.User;
import cz.oz.web.qualifiers.CurrentSession;
import cz.oz.web.qualifiers.LoggedIn;
import cz.oz.web.security.OzCzAuthSession;
import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.ftlines.wicket.cdi.CdiConfiguration;
import static net.ftlines.wicket.cdi.ConversationPropagation.NONE;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.apache.wicket.util.io.IOUtils;
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
        return JTexyTestPage.class;
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
        mountPage("/test", JTexyTestPage.class);
        mountPage("/stranky", JTexyPage.class);
        mountResource("/log", new LogResourceReference());

        // Resources
        ResourceReference favicon = new SharedResourceReference("favicon.gif");
        //mount("/favicon.gif", rrefFavicon.getSharedResourceKey());
        //mountResource("favicon.gif", favicon);
        InputStream is = WicketJavaEEApplication.class.getClassLoader().getResourceAsStream("favicon.gif");
        try {
            getSharedResources().add( "favicon", new ByteArrayResource("image/gif", IOUtils.toByteArray(is) ) );
            //Application.get().getSharedResources().add( "favicon", new ResourceStreamResource("image/gif", ) );
            mountResource("favicon.gif", new SharedResourceReference("favicon"));
        } catch( IOException ex ) {
            log.error( ex.toString() );
        }
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

    
    @Override
    public Session newSession( Request request, Response response ) {
        return new OzCzAuthSession( request );
    }

    // CDI beans producers.
    @Produces @LoggedIn User getCurrentUser(){
        return ((OzCzAuthSession) Session.get()).getUser();
    }

    @Produces @CurrentSession OzCzAuthSession getCurrentSession(){
        return (OzCzAuthSession) Session.get();
    }

}// class
