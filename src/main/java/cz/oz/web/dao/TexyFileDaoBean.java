package cz.oz.web.dao;

import cz.oz.web.model.TexyDoc;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;


/**
 * A bean which manages Contact entities.
 */
@Stateless
public class TexyFileDaoBean {

    @PersistenceContext
    private EntityManager em;


    /**
     *  Get all Texy docs indexed in given dir.
     */
    public List<TexyDoc> getDocsFromDir( String dir ) {
        return em.createQuery("SELECT doc FROM TexyDoc doc WHERE doc.origPath LIKE CONCAT( ?1, '%'")
                .setParameter( 1, dir)
                .getResultList();
    }

    /**
     * Get Contact by ID.
     */
    public TexyDoc getDoc(Long id) {
        return em.find(TexyDoc.class, id);
    }



    /**
     * Remove a doc.
     */
    public void remove(TexyDoc doc) {
        TexyDoc managed = em.merge(doc);
        em.remove(managed);
        em.flush();
    }

    public TexyDoc findDocByPath( String path ) {
        try {
            return em.createQuery("SELECT doc FROM TexyDoc doc WHERE doc.origPath LIKE CONCAT('%', :path)", TexyDoc.class)
                .setParameter("path", path)
            //path = path.replace("'", "\'");
            //return em.createQuery("SELECT doc FROM TexyDoc doc WHERE doc.origPath LIKE '%"+path+"'", TexyDoc.class)
                .getSingleResult();
        } catch ( NoResultException ex ){
            return null;
        }
    }

    public void addTexyFile( TexyDoc texyFile ) {
        em.persist( texyFile );
    }

    public List<TexyDoc> getLatestDocs( int limit ) {
            return em.createQuery("SELECT doc FROM TexyDoc doc ORDER BY added DESC", TexyDoc.class)
                .setMaxResults( limit )
                .getResultList();
    }
    
}
