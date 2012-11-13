package cz.oz.web.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 *
 * @author Ondrej Zizka
 */
@SuppressWarnings("serial")
@Entity
public class TexyFile implements Serializable {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    @Column(unique=true)
    private String path;
    
    public TexyFile() {
    }

    public TexyFile(String path) {
        this.path = path;
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
        final TexyFile other = (TexyFile) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        return true;
    }
    //</editor-fold>
    
}// class
