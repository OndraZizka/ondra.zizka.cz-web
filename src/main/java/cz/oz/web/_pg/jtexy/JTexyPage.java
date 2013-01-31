package cz.oz.web._pg.jtexy;

import cz.oz.web._co.baseLayout.BaseLayoutPage;
import cz.oz.web._pg.ICountablePage;
import cz.oz.web.dao.TexyFileDaoBean;
import javax.inject.Inject;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * Renders the Texy file. 
 * Either gets the content from a cache, or loads the file from filesystem and converts.
 * 
 * @author Ondrej Zizka
 *
 * TODO:  Split to TexyDocumentPanel.
 */
@SuppressWarnings("serial")
public class JTexyPage extends BaseLayoutPage implements ICountablePage {

    @Inject private transient TexyFileDaoBean dao;

    
    // ID of the page to show.
    private String reqPath;

    private Long viewCount;

    
    public JTexyPage(PageParameters params) {
        
        // Construct the path to the texy file.
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < params.getIndexedCount(); i++ ) {
            sb.append('/').append(params.get(i));
        }
        // Requested path. May be served from cache/db.
        this.reqPath = sb.toString();
        init( reqPath );
    }

    public JTexyPage( String path ) {
        this.init(path);
    }

    private void init( String path ) {
        add( new TexyDocumentPanel("document", path) );
    }


    // Counter stuff
    @Override public String getCounterId() {
        return "texyPage:" + this.reqPath;
    }
    @Override public void setCount( Long count ) { this.viewCount = count; }
    @Override public Long getCount() { return this.viewCount; }


}// class
