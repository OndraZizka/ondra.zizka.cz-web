package cz.oz.web.dao;


import cz.oz.web.model.Count;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * A bean which manages Contact entities.
 */
@Stateless
public class CountDao {

    @PersistenceContext
    private EntityManager em;

    public long getCountAfterIncrement( String id ){
        // TODO: How to surround with a transaction? em.getTransaction() can't be used for JTA.
        int affected = em.createQuery("UPDATE Count cnt SET cnt.count = cnt.count +1 WHERE cnt.id = ?1").setParameter(1, id).executeUpdate();
        if( affected != 0 )
            return getCount( id );

        //em.createQuery("INSERT INTO Count (id, count) SELECT 1, ?").setParameter(1, id).executeUpdate();
        // QuerySyntaxException: expecting OPEN, found ')' - COUNT is a keyword
        em.persist( new Count( id, 1 ) );
        return 1;
    }

    public long getCount( String id ){
        return em.createQuery("SELECT cnt.count FROM Count cnt WHERE cnt.id = ?1", Long.class )
                .setParameter(1, id).getSingleResult();
    }
    
}// class
