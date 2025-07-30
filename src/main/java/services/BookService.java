package services;

import models.Book;
import database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class BookService {
    public void addBook(Book book) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(book);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    public Book getBookById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Book.class, id);
        } finally {
            session.close();
        }
    }

    public List<Book> getAllBooks() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Book", Book.class).list();
        } finally {
            session.close();
        }
    }

    public void updateBook(Book book) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(book);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    public void deleteBook(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Book book = session.get(Book.class, id);
            if (book != null) {
                session.delete(book);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
        } finally {
            session.close();
        }
    }

    public Set<String> getAllGenres() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            List<Book> books = session.createQuery("from Book", Book.class).list();
            Set<String> genres = new HashSet<>();
            for (Book b : books) {
                genres.add(b.getGenre());
            }
            return genres;
        } finally {
            session.close();
        }
    }
}
