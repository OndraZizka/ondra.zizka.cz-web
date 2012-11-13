package cz.oz.web.pages;

import cz.oz.web.dao.TexyFileDaoBean;
import java.io.File;
import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;


/**
 * Dynamic behavior for the ListContact page
 * 
 * @author Filippo Diotalevi
 */
@SuppressWarnings("serial")
public class JTexyPage extends WebPage {

    // Inject the ContactDao using @Inject
    @Inject
    private TexyFileDaoBean dao;


    // Set up the dynamic behavior for the page, widgets bound by id
    public JTexyPage(PageParameters params) {

        StringBuilder sb = new StringBuilder();
        for( int i = 0; i < params.getIndexedCount(); i++ ) {
            sb.append('/').append(params.get(i));
        }
        String path = sb.toString();
        
        File texyFile = new File(path);
        
        if( ! texyFile.exists() ){
            add(new Label("content", "Doesn't exist: " + texyFile.getPath() ));
        }
    }

}
