package io.github.mat3e.todo;

import io.github.mat3e.HibernateUtil;
import io.github.mat3e.lang.Lang;

import java.util.List;
import java.util.Optional;

public class TodoRepository {
    List<Todo> findAll() {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction(); //ta trans jest tak na prawde tylko do odczytu

        var result = session.createQuery("from Todo", Todo.class).list(); //Lang.class-- do tej klasy zostanie zwrócony wynik

        transaction.commit();
        session.close();
        return result;
    }

    Todo toggleTodo(Integer id) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();

        var result = session.get(Todo.class, id);
        result.setDone(!result.isDone());

        transaction.commit();
        session.close();
        return result;
    }
    Todo addTodo(Todo newTodo) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();


        session.persist(newTodo);

        transaction.commit();
        session.close();
        return newTodo;

    }
}