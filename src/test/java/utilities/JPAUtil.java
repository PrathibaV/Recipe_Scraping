package utilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    
    private static final EntityManagerFactory emFactory;
    
    static {
        emFactory = Persistence.createEntityManagerFactory("recipePU");
    }

    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }
    
    public static void close() {
        emFactory.close();
    }
}
