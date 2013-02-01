
package cz.oz.web._pg.jtexy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.IPageParametersEncoder;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 *  Creates just one param: "path", containing whole path.
 *  In combination with proper mapper (BookmarkableMapper?), it should give the part after mount path.
 * 
 *  I.e.            /pages/foo/bar.texy
 *  would become    /foo/bar.texy
 *
 *  @author Ondrej Zizka
 */
public class PathPageParametersEncoder implements IPageParametersEncoder {

    @Override public PageParameters decodePageParameters( Url url ) {
        return new PageParameters().add("path", url.getPath());
    }
    @Override public Url encodePageParameters( PageParameters pageParameters ) {
        return new Url(
                new ArrayList<String>( Arrays.asList( StringUtils.split(pageParameters.get("path").toString(""), '/') ) ),
                Collections.EMPTY_LIST // Query params
        );
    }

}// class
