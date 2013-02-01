
package cz.oz.web._pg.jtexy;

import cz.oz.web._pg.ContentDispatchPage;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.core.request.mapper.AbstractBookmarkableMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 *
 *  @author Ondrej Zizka
 */
public class PathBookmarkableMapper extends AbstractBookmarkableMapper {

    @Override
    protected AbstractBookmarkableMapper.UrlInfo parseRequest( Request request ) {
        return new UrlInfo(
            /*new PageComponentInfo(
                new PageInfo(),
                new ComponentInfo( 1, null, null, 1)
            ),*/ null,
            ContentDispatchPage.class,
            new PageParameters()
                .add("path", request.getUrl().getPath() )
                .add("edit", request.getUrl().getQueryParameter("edit") != null )
        );
    }

    @Override
    protected Url buildUrl( AbstractBookmarkableMapper.UrlInfo info ) {
        String path = info.getPageParameters().get("path").toString();
        List<String> segments = Arrays.asList( StringUtils.split(path, '/') );
        return new Url(segments, Charset.forName("UTF-8"));
    }

    @Override protected boolean pageMustHaveBeenCreatedBookmarkable() { return false; }

    @Override public int getCompatibilityScore( Request request ) { return 10; }

}// class
