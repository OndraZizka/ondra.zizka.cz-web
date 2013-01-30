
package cz.oz.web.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 *  @author Ondrej Zizka
 */
@Entity
@Table(name = "counter")
public class Count implements Serializable {

    @Id
    private String id;

    @Column(columnDefinition = "INT UNSIGNED")
    private int count;



    //<editor-fold defaultstate="collapsed" desc="get/set/override">
    public String getId() { return id; }
    public void setId( String id ) { this.id = id; }
    public int getCount() { return count; }
    public void setCount( int count ) { this.count = count; }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals( Object object ) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if( !(object instanceof Count) ) {
            return false;
        }
        Count other = (Count) object;
        if( (this.id == null && other.id != null) || (this.id != null && !this.id.equals( other.id )) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cz.oz.web.model.Count[ id=" + id + " ]";
    }
    //</editor-fold>
}
