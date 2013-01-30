package cz.oz.web.model;

import cz.oz.web._co.TexyDocumentPanel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.apache.commons.codec.digest.DigestUtils;


/**
 *  Texy document, including source and rendered HTML.
 * 
 *  @author Ondrej Zizka
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "texydoc")
public class TexyDoc implements Serializable {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Long id;

    private String title;
    
    @Column(unique=true)
    private String origPath;

    @Temporal( TemporalType.TIMESTAMP )
    private Date added;

    @Temporal( TemporalType.TIMESTAMP )
    private Date lastChanged;

    @ManyToOne @JoinColumn(name = "author_id")
    private User author;

    @Column(columnDefinition = "CHAR(32)", length = 32)
    private String contentHash;

    // Lazily loaded body.
    @Column(columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY )
    private String content;
    
    // Lazily loaded rendered html code.
    @Column(columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY )
    private String renderedHtml;

    @Column(columnDefinition = "TEXT")
    private String parsingException;


    
    public TexyDoc() {
    }

    public TexyDoc( String path, String src, String html ) {
        this.origPath = path;
        this.content  = src;
        this.renderedHtml = html;
        this.rehashContent();
    }

    public TexyDoc( String origPath, Exception parsingException ) {
        this.origPath = origPath;
        if( parsingException != null )
            this.parsingException = parsingException.toString();
    }

    
    private void rehashContent() {
        //this.contentHash = computeFileHash( new File( this.origPath ) );
        this.contentHash = DigestUtils.md5Hex( this.content );
    }
    
    
    @Transient public String getTitleOrFileName(){
        return title != null ?  title : TexyDocumentPanel.guessTitle( new File(origPath).getName() );
    }

    @Transient boolean isParsingFailed(){ return parsingException != null; }

    @Transient
    public String getPath() {
        return origPath;
    }


    //<editor-fold defaultstate="collapsed" desc="get/set">
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle( String title ) { this.title = title; }
    public String getOrigPath() { return origPath; }
    public void setOrigPath( String origPath ) { this.origPath = origPath; }
    public Date getAdded() { return added; }
    public TexyDoc setAdded( Date added ) { this.added = added; return this; }
    public Date getLastChanged() { return lastChanged != null ? lastChanged : added; }
    public void setLastChanged( Date lastChanged ) { this.lastChanged = lastChanged; }
    public User getAuthor() { return author; }
    public void setAuthor( User author ) { this.author = author; }
    public String getContentHash() { return contentHash; }
    public void setContentHash( String contentHash ) { this.contentHash = contentHash; }
    public String getContent() { return content; }
    public void setContent( String content ) { this.content = content; }
    public String getRenderedHtml() { return renderedHtml; }
    public void setRenderedHtml( String renderedHtml ) { this.renderedHtml = renderedHtml; }
    public String getParsingException() { return parsingException; }
    public void setParsingException( String parsingException ) { this.parsingException = parsingException; }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="hash/eq">
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TexyDoc other = (TexyDoc) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.origPath == null) ? (other.origPath != null) : !this.origPath.equals(other.origPath)) {
            return false;
        }
        return true;
    }
    //</editor-fold>

    /*
    private static String computeFileHash( File file ) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream( file );
            return DigestUtils.md5Hex(fis);
        } catch( FileNotFoundException ex ) {
            return null;
        } catch( IOException ex ) {
            throw ex;
        } finally {
            try { fis.close(); } catch( IOException ex ) {}
        }
    }*/

}// class
