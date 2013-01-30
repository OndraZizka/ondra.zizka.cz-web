package cz.oz.web._pg;

import cz.oz.web._co.TexyDocumentPanel;
import cz.oz.web._co.baseLayout.BaseLayoutPage;
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
        add( new TexyDocumentPanel("document", reqPath) );
    }

    
    @Override public String getCounterId() {
        return "stranky:" + this.reqPath;
    }


}// class
