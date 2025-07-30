package services;

import models.User;
import models.RegularUser;
import models.Book;
import interfaces.Borrowable;
import database.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

public class UserService {
    public void addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(user);
        tx.commit();
        session.close();
    }

    public User getUserById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user = session.get(User.class, id);
        session.close();
        return user;
    }

    public List<User> getAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> users = session.createQuery("from User", User.class).list();
        session.close();
        return users;
    }

    public void updateUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(user);
        tx.commit();
        session.close();
    }

    public void deleteUser(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        tx.commit();
        session.close();
    }

    public boolean borrowBook(int userId, int bookId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, userId);
        Book book = session.get(Book.class, bookId);
        if (user == null || book == null) {
            session.close();
            return false;
        }
        if (book.getAvailableCopies() <= 0) {
            session.close();
            return false;
        }
        if (!(user instanceof Borrowable)) {
            session.close();
            return false;
        }
        Borrowable borrowableUser = (Borrowable) user;
        boolean success = borrowableUser.borrowBook(String.valueOf(bookId));
        if (success) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            session.update(user);
            session.update(book);
            tx.commit();
            session.close();
            return true;
        } else {
            session.close();
            return false;
        }
    }

    public boolean returnBook(int userId, int bookId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        User user = session.get(User.class, userId);
        Book book = session.get(Book.class, bookId);
        if (user == null || book == null) {
            session.close();
            return false;
        }
        if (!(user instanceof Borrowable)) {
            session.close();
            return false;
        }
        Borrowable borrowableUser = (Borrowable) user;
        boolean success = borrowableUser.returnBook(String.valueOf(bookId));
        if (success) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            session.update(user);
            session.update(book);
            tx.commit();
            session.close();
            return true;
        } else {
            session.close();
            return false;
        }
    }

    public Optional<User> getUserByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            User user = session.createQuery("from User where username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();
            return Optional.ofNullable(user);
        } finally {
            session.close();
        }
    }
}
