package cz.oz.web;

import cz.oz.web.pages.JTexyPage;
import static net.ftlines.wicket.cdi.ConversationPropagation.NONE;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.ftlines.wicket.cdi.CdiConfiguration;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;


/**
 * Web app for http://ondra.zizka.cz
 * @author Ondrej Zizka
 */
public class WicketJavaEEApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return JTexyPage.class;
    }

    @Override
    protected void init() {
        super.init();

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
    
}
