package cz.oz.web._co;

import cz.dynawest.jtexy.JTexy;
import cz.dynawest.jtexy.TexyException;
import cz.oz.web.WicketJavaEEApplication;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import res.ResourcesPackageMarker;

/**
 *
 *  @author Ondrej Zizka
 */
public class TexyDocumentPanel extends Panel {

    private transient Charset encoding = Charset.forName("utf-8");

    
    public TexyDocumentPanel( String id, String reqPath ) {
        super( id, new Model<String>(reqPath) );

        // Filesystem path.
        File rootPath = new File(((WicketJavaEEApplication)this.getApplication()).getSettings().getTexyFilesRootPath());
        File texyFile = new File(rootPath, reqPath);

        // Exists?
        if( ! texyFile.exists() ){
            add( new Label("substituteTitle", "Page not found."));
            add( new Label("body", "Doesn't exist: " + texyFile.getPath() ));
        }
        else {
            // Read.
            String src;
            try {
                src = FileUtils.readFileToString(texyFile, this.encoding);
            } catch (IOException ex) {
                add( new Label("body", "Error reading " + texyFile.getPath() + ": " + ex.toString() ));
                // TODO log
                return;
            }

            // Convert.
            String html;
            try {
                html = JTexy.create().process(src);
            } catch( TexyException ex ){
                add( new Label("body", "Error rendering " + texyFile.getPath() + ": " + ex.toString() ));
                // TODO log
                return;
            }

            // Show.
            add( new Label("body", html).setEscapeModelStrings(false));

            // Add title if absent in document.
            add( new Label("substituteTitle", guessTitle(texyFile.getName())).setVisibilityAllowed(false) );
        }
    }// const

    
    @Override
    public void renderHead( IHeaderResponse response ) {
        // Syntax Highlighter.
        response.render( new CssReferenceHeaderItem(
            new CssResourceReference( ResourcesPackageMarker.class, "js/syntaxhighlighter/styles/shThemeDefault.css"), null, null, null));
        response.render( new JavaScriptReferenceHeaderItem(
            new JavaScriptResourceReference( ResourcesPackageMarker.class, "js/syntaxhighlighter/scripts/shCore.js"), null, "shCore", true, null, null));
        response.render( new JavaScriptReferenceHeaderItem(
            new JavaScriptResourceReference( ResourcesPackageMarker.class, "js/syntaxhighlighter/scripts/shAutoloader.js"), null, "shAutoloader", true, null, null));
    }


    /**
     *  foo-bar-baz.texy --> Foo bar baz.
     */
    public static String guessTitle( String name ) {
        return StringUtils.capitalize( name ).replaceAll("-", " ");
    }

}// class TexyDocumentPanel