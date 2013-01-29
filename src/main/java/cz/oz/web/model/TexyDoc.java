package cz.oz.web.model;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.codec.digest.DigestUtils;


/**
 *  Texy document, inc. source and rendered HTML.
 * 
 *  @author Ondrej Zizka
 */
@SuppressWarnings("serial")
@Entity
public class TexyDoc implements Serializable {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(unique=true)
    private String origPath;

    @Temporal( TemporalType.TIMESTAMP )
    private Date added;

    @ManyToOne @JoinColumn(name = "author_id")
    private User author;

    @Column(columnDefinition = "CHAR", length = 32)
    private String contentHash;

    // Lazily loaded body.
    @Column(columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY )
    private String content;
    
    // Lazily loaded rendered html code.
    @Column(columnDefinition = "TEXT")
    @Basic(fetch = FetchType.LAZY )
    private String renderedHtml;

    private Exception parsingException;


    
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
        this.parsingException = parsingException;
    }

    
    private void rehashContent() {
        //this.contentHash = computeFileHash( new File( this.origPath ) );
        this.contentHash = DigestUtils.md5Hex( this.content );
    }
    
    
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
    }


    
    //<editor-fold defaultstate="collapsed" desc="get/set">
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    
}// class
