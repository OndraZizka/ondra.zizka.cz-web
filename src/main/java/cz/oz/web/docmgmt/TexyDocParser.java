
package cz.oz.web.docmgmt;

import cz.dynawest.jtexy.JTexy;
import cz.dynawest.jtexy.TexyException;
import cz.oz.web.model.TexyDoc;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;

/**
 *  Parses Texy file into TexyFile entity.
 * 
 *  @author Ondrej Zizka
 */
public class TexyDocParser {

    public TexyDoc createTexyDoc( File file ) {
        return this.createTexyDoc( file, Charset.forName("utf-8") );
    }

    public TexyDoc createTexyDoc( File texyFile, Charset encoding ) {
        
        try {
            // Read.
            String src = FileUtils.readFileToString(texyFile, encoding);
            // Convert.
            String html = JTexy.create().process(src);

            return new TexyDoc( texyFile.getPath(), src, html );
        }
        catch( IOException ex ) {
            return new TexyDoc( texyFile.getPath(), ex );
        }
        catch( TexyException ex ) {
            return new TexyDoc( texyFile.getPath(), ex );
        }

    }// createTexyDoc()


}// class
