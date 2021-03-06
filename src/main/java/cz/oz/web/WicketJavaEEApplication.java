package cz.oz.web;

import cz.oz.web.util._co.LogResourceReference;
import cz.oz.web._pg.jtexy.JTexyPage;
import cz.oz.web._pg.jtexy.JTexyTestPage;
import cz.oz.web._pg.doclist.DocListPage;
import cz.oz.web._pg.var.SiteMapXmlPage;
import cz.oz.web.ex.OzczException;
import cz.oz.web.model.User;
import cz.oz.web.qualifiers.CurrentSession;
import cz.oz.web.qualifiers.FromApp;
import cz.oz.web.qualifiers.LoggedIn;
import cz.oz.web.security.OzCzAuthSession;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.ftlines.wicket.cdi.CdiConfiguration;
import static net.ftlines.wicket.cdi.ConversationPropagation.NONE;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import res.ResourcesPackageMarker;


/**
 * Web app for http://ondra.zizka.cz
 * @author Ondrej Zizka
 */
public class WicketJavaEEApplication extends WebApplication {
    private static final Logger log = LoggerFactory.getLogger(WicketJavaEEApplication.class);
    
    private AppSettings appSettings;

    
    @Override
    public Class<? extends Page> getHomePage() {
        return JTexyPage.class;
    }

    
    @Override
    protected void init() {
        super.init();
        
        this.appSettings = createSettings();

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
        mountPage("/sitemap.xml", SiteMapXmlPage.class);
        mountPage("/test", JTexyTestPage.class);
        mountPage("/stranky", JTexyPage.class);
        //mountPage("/stranky", ContentDispatchPage.class);
        //mount(new MountedMapper("/stranky2", ContentDispatchPage.class, new PathPageParametersEncoder() ) );

        mountPage("/docs/#{limit}/#{offset}", DocListPage.class);
        mountResource("/log", new LogResourceReference());
        mountResource("/clipboard.swf", new PackageResourceReference( ResourcesPackageMarker.class, "js/syntaxhighlighter/scripts/clipboard.swf") );

        // Resources
        //ResourceReference favicon = new SharedResourceReference("favicon.gif");
        //mount("/favicon.gif", rrefFavicon.getSharedResourceKey());
        //mountResource("favicon.gif", favicon);
        /*InputStream is = WicketJavaEEApplication.class.getClassLoader().getResourceAsStream("favicon.gif");
        try {
            getSharedResources().add( "favicon", new ByteArrayResource("image/gif", IOUtils.toByteArray(is) ) );
            //Application.get().getSharedResources().add( "favicon", new ResourceStreamResource("image/gif", ) );
            mountResource("favicon.gif", new SharedResourceReference("favicon"));
        } catch( IOException ex ) {
            log.error( ex.toString() );
        }*/

        getMarkupSettings().setStripWicketTags(true);
    }

    
    @Produces @FromApp
    public AppSettings getSettings() {
        return appSettings = (appSettings == null ? createSettings() : appSettings);
    }

    private AppSettings createSettings() {
        AppSettings sett;
        try {
            return new AppSettings();
        } catch( OzczException ex ) {
            throw new RuntimeException(ex);
        }
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
