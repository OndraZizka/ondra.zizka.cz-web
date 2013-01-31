
package cz.oz.web._pg.var;

import cz.oz.web.dao.TexyFileDaoBean;
import cz.oz.web.model.TexyDoc;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.inject.Inject;
import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.Url;

/**
 *  @see  http://support.google.com/webmasters/bin/answer.py?hl=en&answer=183668
 *  @author Ondrej Zizka
 */
public class SiteMapXmlPage extends WebPage {

    @Inject TexyFileDaoBean dao;

    public SiteMapXmlPage() {
        List<TexyDoc> docs = dao.getLatestDocs(1000);
        
        final String docPrefixUrl = formatDocPrefixUrl();

        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // YYYY-MM-DDThh:mmTZD or YYYY-MM-DD.

        add( new ListView<TexyDoc>("urls", docs) {
            @Override
            protected void populateItem( ListItem<TexyDoc> item ) {

                /*
                    <loc wicket:id="loc">http://www.example.com/</loc>
                    <lastmod wicket:id="lastMod">2005-01-01</lastmod>
                    <changefreq wicket:id="changeFreq">monthly</changefreq>
                    <priority wicket:id="priority">0.8</priority>
                 */
                TexyDoc doc = item.getModelObject();
                item.add( new Label("loc", docPrefixUrl + "/" + doc.getPath()) ); // Full url
                item.add( new Label("lastMod", df.format(doc.getLastChanged())) ); // YYYY-MM-DD
                item.add( new Label("changeFreq", ChangeFreq.MONTHLY) );
                item.add( new Label("priority").setVisibilityAllowed(false) );
            }
        } );
    }

    @Override
    public MarkupType getMarkupType() {
        return new MarkupType(".xml", "text/xml");
    }

    private String formatDocPrefixUrl() {
        Url url = getRequest().getUrl();
        //String cp = getRequest().getContextPath();
        // if( cp.startsWith("/") )  cp = cp.substring(1);
        return String.format("%s://%s:%d%s", url.getProtocol(), url.getHost(), url.getPort(), getRequest().getContextPath() );
    }


    
    public enum ChangeFreq {
        NEVER("never"),
        YEARLY("yearly"),
        MONTHLY("monthly"),
        WEEKLY("weekly"),
        DAILY("daily"),
        HOURLY("hourly"),
        ALWAYS("always");

        private String str;

        private ChangeFreq( String str ) {
            this.str = str;
        }

        @Override
        public String toString() {
            return this.str;
        }
    }

}
