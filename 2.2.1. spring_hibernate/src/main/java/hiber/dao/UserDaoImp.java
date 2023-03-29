package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserIfCar(String modelCar, int seriesCar) {
      TypedQuery<Car> query = sessionFactory.getCurrentSession()
              .createQuery("from Car where model=:modelCar and series=:seriesCar")
              .setParameter("modelCar", modelCar)
              .setParameter("seriesCar", seriesCar);
      try {
         return query.getSingleResult().getUser();
      } catch (NoResultException e) {
         return null;
      }
   }

}
