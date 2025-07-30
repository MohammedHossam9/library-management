package services;

import database.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class SearchService<T> {
    public T searchById(Class<T> entity, int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(entity, id);
        } finally {
            session.close();
        }
    }

    public List<T> searchByName(Class<T> entity, String nameField, String nameValue) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String query = "from " + entity.getSimpleName() + " where " + nameField + " = :nameValue";
            return session.createQuery(query, entity)
                    .setParameter("nameValue", nameValue)
                    .list();
        } finally {
            session.close();
        }
    }
}
