package io.github.mat3e.lang;

import io.github.mat3e.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class LangRepository {
    List<Lang> findAll() {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction(); //ta trans jest tak na prawde tylko do odczytu

        var result = session.createQuery("from Lang", Lang.class).list(); //Lang.class-- do tej klasy zostanie zwrócony wynik

        transaction.commit();
        session.close();
        return result;
    }

    public Optional<Lang> findById(Integer id) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction(); //ta trans jest tak na prawde tylko do odczytu

        var result = Optional.ofNullable(session.get(Lang.class, id));

        transaction.commit();
        session.close();
        return result;
    }
}