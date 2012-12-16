package cz.oz.web._pg;

import cz.dynawest.jtexy.JTexy;
import cz.dynawest.jtexy.TexyException;
import cz.oz.web.WicketJavaEEApplication;
import cz.oz.web._co.baseLayout.BaseLayoutPage;
import cz.oz.web.dao.TexyFileDaoBean;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * Renders the Texy file. 
 * Either gets the content from a cache, or loads the file from filesystem and converts.
 * 
 * @author Ondrej Zizka
 */
@SuppressWarnings("serial")
public class JTexyPage extends BaseLayoutPage implements ICountablePage {

    @Inject private transient TexyFileDaoBean dao;


    private transient Charset encoding = Charset.forName("utf-8");

    // ID of the page to show.
    private String reqPath;


    
    // Set up the dynamic behavior for the page, widgets bound by id
    public JTexyPage(PageParameters params) {

        
        // Construct the path to the texy file.
        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < params.getIndexedCount(); i++ ) {
            sb.append('/').append(params.get(i));
        }
        // Requested path. May be served from cache/db.
        this.reqPath = sb.toString();
        
        // Filesystem path.
        File rootPath = new File(((WicketJavaEEApplication)this.getApplication()).getSettings().getTexyFilesRootPath());
        File texyFile = new File(rootPath, reqPath);

        if( ! texyFile.exists() ){
            add( new Label("substituteTitle", "Page not found."));
            add( new Label("content", "Doesn't exist: " + texyFile.getPath() ));
        }
        else {
            // Read.
            String src;
            try {
                src = FileUtils.readFileToString(texyFile, this.encoding);
            } catch (IOException ex) {
                add( new Label("content", "Error reading " + texyFile.getPath() + ": " + ex.toString() ));
                // TODO log
                return;
            }
            
            // Convert.
            String html;
            try {
                html = JTexy.create().process(src);
            } catch( TexyException ex ){
                add( new Label("content", "Error rendering " + texyFile.getPath() + ": " + ex.toString() ));
                // TODO log
                return;
            }
            
            // Show.
            add( new Label("content", html).setEscapeModelStrings(false));

            // Add title if absent in document.
            add( new Label("substituteTitle", guessTitle(texyFile.getName())).setVisibilityAllowed(false) );
        }
    }

    /**
     *  foo-bar-baz.texy --> Foo bar baz.
     */
    private String guessTitle( String name ) {
        return StringUtils.capitalize( name ).replaceAll("-", " ");
    }


    @Override public String getCounterId() {
        return "stranky:" + this.reqPath;
    }

}// class
