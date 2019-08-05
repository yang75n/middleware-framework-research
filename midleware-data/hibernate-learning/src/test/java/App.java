import com.yqw.hibernate.domain.User;
import com.yqw.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

/**
 * 测试Hibernate
 * Created by Qiwen on 2019/5/18.
 */
public class App {

    @Test
    public void testGetSession() {
        System.out.println("获取Session");
        Session session = HibernateUtil.getSession();
        System.out.println("session=" + session);
    }


    @Test
    public void testSave() {
        Session session = null;
        //瞬时状态
        User u = new User("西毒", 70);
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            //持久状态----做脏数据检查  将session数据和数据库同步
            session.save(u);
            u.setAge(80);
            //session.update(u);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        //对象u是游离状态
        System.out.println("======" + u);

        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            //删除
            session.delete(u);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {

                session.close();
            }
        }
        //瞬时状态
        System.out.println("======" + u);
    }

    @Test
    public void testGet() {
        Session session = null;
        User u = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            //持久状态
            /*
             * get 查询数据如果数据不存在则返回null
			 * load查询数据如果数据不存在则抛出异常
			 * */
            u = (User) session.get(User.class, 4);
            //u = (User)session.load(User.class, 50);
            //瞬时状态
            session.delete(u);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {

                session.close();
            }
        }
        System.out.println("====" + u);
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            //持久状态  save直接添加数据发出一条insert语句
            session.save(u);
            //saveOrUpdate 判断保存的对象是否有id,如果有则发出update语句
            //如果没有则发出insert语句
            //session.saveOrUpdate(u);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {

                session.close();
            }
        }
    }

    @Test
    public void testDelete() {
        Session session = null;
        User u = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            u = new User();
            u.setId(7);
            session.delete(u);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {

                session.close();
            }
        }
    }

    @Test
    public void testUpdate() {
        Session session = null;
        User u = null;
        try {
            session = HibernateUtil.getSession();
            session.beginTransaction();
            u = new User();
            u.setId(3);
            u.setName("南帝");
            u.setAge(66);
            session.update(u);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {

                session.close();
            }
        }
    }

    @Test
    public void testTransaction() {
        //1、读取hibernate.cfg.xml配置文件
        Configuration cfg = new Configuration().configure();

        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();
        SessionFactory factory = cfg.buildSessionFactory(registry);

        Session session = null;
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            User u = new User("yang qiwen", 22);
            session.save(u);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            if (session != null)
                session.close();
        }
        factory.close();
    }
}
