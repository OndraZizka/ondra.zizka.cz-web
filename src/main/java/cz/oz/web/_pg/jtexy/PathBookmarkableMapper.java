
package cz.oz.web._pg.jtexy;

import cz.oz.web._pg.ContentDispatchPage;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.core.request.mapper.AbstractBookmarkableMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 *  Usage:
 *
 *    getRootRequestMapperAsCompound().add(new PathBookmarkableMapper("/pages", "/dir/with/files", ContentDispatchPage.class));
 * 
 *  @author Ondrej Zizka
 */
public class PathBookmarkableMapper extends AbstractBookmarkableMapper {

    private String mountPath;
    private String systemPath;
    private String pageClass;

    public PathBookmarkableMapper( String mountPath, String pageClass ) {
        this.mountPath = mountPath;
        this.systemPath = systemPath;
        this.pageClass = pageClass;
    }


    /**
     *  Parse request.
     */
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


    /**
     *  Build URL.
     */
    @Override
    protected Url buildUrl( AbstractBookmarkableMapper.UrlInfo info ) {
        String path = info.getPageParameters().get("path").toString();
        List<String> segments = new LinkedList();
		segments.add(getContext().getNamespace());
		segments.add(getContext().getBookmarkableIdentifier()); // ?
        segments.addAll( Arrays.asList( StringUtils.split(this.mountPath, '/') ) );
        segments.addAll( Arrays.asList( StringUtils.split(path, '/') ) );
        return new Url(segments, Charset.forName("UTF-8"));
    }

    /**
     *   Compatibility.
     */
    @Override public int getCompatibilityScore( Request request ) {

        //if( ! request.getUrl().getPath().startsWith( this.mountPath ) )
        if( ! matches( request.getUrl() ) )
            return 0;

        return Integer.MAX_VALUE;
    }

    @Override protected boolean pageMustHaveBeenCreatedBookmarkable() { return true; }


	private boolean matches(final Url url) {
		return (url.getSegments().size() >= 3 && urlStartsWith(url, getContext().getNamespace(),
			getContext().getBookmarkableIdentifier()));
	}

}// class
