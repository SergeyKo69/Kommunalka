package ru.macrohome.server;

import javafx.scene.control.Alert;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.macrohome.common.*;
import ru.macrohome.entity.DatasEntity;
import ru.macrohome.entity.PaymentsEntity;
import ru.macrohome.entity.SettingsEntity;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class DataBaseUtility {
    public static class Parameters{
        public Session session;
        public Condition[] conditions;

        public Parameters(Session session, Condition[] conditions) {
            this.session = session;
            this.conditions = conditions;
        }

        public Parameters(Session session) {
            this.session = session;
        }
    }

    public static Answer saveEntity(Entities[] entityArr){
        Session session = null;
        SessionFactory sessionFactory = Connector.getSessionFactory();
        if (sessionFactory == null){
            return new Answer(Answers.ERROR,"Error get database session");
        }
        try {
            session = sessionFactory.openSession();
            for (int i = 0; i < entityArr.length; i++) {
                Entities entity = entityArr[i];
                if (entity == null){
                    continue;
                }
                session.beginTransaction();
                session.saveOrUpdate(entity);
                session.flush();
                session.getTransaction().commit();
            }

        }catch (Exception e){
            session.getTransaction().rollback();
            return new Answer(Answers.ERROR,e.getMessage());
        }finally {
            if (session != null){
                session.close();
            }
        }
        return new Answer(Answers.OK);
    }

    public static Answer getSettingsFirstValueByDateView(Date date, int id_view) {
        Session session = null;
        try {
            SessionFactory sessionFactory = Connector.getSessionFactory();
            if (sessionFactory == null) {
                return new Answer(Answers.ERROR, "Error get database session");
            }
            session = sessionFactory.openSession();
            List<Entities> list = null;
            Query query = session.createQuery("FROM SettingsEntity WHERE " +
                    "date <= :date AND viewId = :viewId ORDER BY date DESC, id DESC", SettingsEntity.class)
                    .setParameter("date", date)
                    .setParameter("viewId", id_view)
                    .setFirstResult(0)
                    .setMaxResults(1);
            list = query.getResultList();
            return new Answer(Answers.OK, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Answer(Answers.ERROR, e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static Answer getPaymentFirstValueByDateView(Date date, int id_view){

        Session session = null;
        try {
            SessionFactory sessionFactory = Connector.getSessionFactory();
            if (sessionFactory == null) {
                return new Answer(Answers.ERROR, "Error get database session");
            }
            session = sessionFactory.openSession();
            List<Entities> list = null;
            Query query = session.createQuery("FROM PaymentsEntity WHERE " +
                    "date <= :date AND viewId = :viewId ORDER BY date DESC, id DESC", PaymentsEntity.class)
                    .setParameter("date", date)
                    .setParameter("viewId", id_view)
                    .setFirstResult(0)
                    .setMaxResults(1);
            list = query.getResultList();
            return new Answer(Answers.OK, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Answer(Answers.ERROR, e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public static Answer getList(Tables table, Condition[] conditions) {
        Session session = null;
        try {
            SessionFactory sessionFactory = Connector.getSessionFactory();
            if (sessionFactory == null) {
                return new Answer(Answers.ERROR, "Error get database session");
            }
            session = sessionFactory.openSession();
            switch (table) {
                case DATAS:
                    return getDatas(new Parameters(session, conditions));
                case VIEWS:
                    return getViews(new Parameters(session, conditions));
                case PAYMENTS:
                    return getPayments(new Parameters(session, conditions));
                case SETTINGS:
                    return getSettings(new Parameters(session, conditions));
                default:
                    return new Answer(Answers.ERROR, "Unknown type table");
            }
        } catch (Exception e) {
            return new Answer(Answers.ERROR, e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    private static String getQuery(Condition[] conditions, String table) {
        String str = "";
        if (conditions.length > 0) {
            str += "FROM " + table + " AS t WHERE ";
            for (int i = 0; i < conditions.length; i++) {
                Condition condition = conditions[i];
                if (i > 0) {
                    str += " AND ";
                }
                str += "t." + condition.name + " = " + condition.value;
            }
        } else {
            str = "from " + table;
        }
        return str;
    }

    private static Answer getSettings(Parameters parameters) {
        List<Entities> list = null;
        try {
            list = parameters.session.createQuery(getQuery(parameters.conditions, "SettingsEntity")).list();
        } catch (Exception e) {
            return new Answer(Answers.ERROR, e.getMessage());
        }
        return new Answer(Answers.OK, list);
    }

    private static Answer getPayments(Parameters parameters) {
        List<Entities> list = null;
        try {
            list = parameters.session.createQuery(getQuery(parameters.conditions, "PaymentsEntity")).list();
        } catch (Exception e) {
            return new Answer(Answers.ERROR, e.getMessage());
        }
        return new Answer(Answers.OK, list);
    }

    private static Answer getViews(Parameters parameters) {
        List<Entities> list = null;
        try {
            list = parameters.session.createQuery(getQuery(parameters.conditions, "ViewsEntity")).list();
        } catch (Exception e) {
            return new Answer(Answers.ERROR, e.getMessage());
        }
        return new Answer(Answers.OK, list);
    }

    private static Answer getDatas(Parameters parameters) {
        List<Entities> list = null;
        try {
            list = parameters.session.createQuery(getQuery(parameters.conditions, "DatasEntity")).list();
        } catch (Exception e) {
            return new Answer(Answers.ERROR, e.getMessage());
        }
        return new Answer(Answers.OK, list);
    }

    public static Answer delete(Entities entity) {
        Session session = null;
        SessionFactory sessionFactory = Connector.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(entity);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            return new Answer(Answers.ERROR,e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return new Answer(Answers.OK);
    }

    public static int saveFile(File file){
        byte[] fileData = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileData);
            fileInputStream.close();
        } catch (IOException e) {
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error save file",e.getMessage());
            return 0;
        }
        DatasEntity[] entity = new DatasEntity[1];
        entity[0] = new DatasEntity();
        entity[0].setName(file.getName());
        entity[0].setData(fileData);
        Answer answ = saveEntity(entity);
        if (answ.answ == Answers.ERROR){
            InterfaceBoxes.showMessage(Alert.AlertType.ERROR,"Error save file", answ.description);
            return 0;
        }
        return entity[0].getId();
    }

}
