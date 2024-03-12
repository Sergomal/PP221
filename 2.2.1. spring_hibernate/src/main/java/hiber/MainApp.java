package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//===============================
//|||||||||||||||||||||||||||||||
import javax.persistence.NoResultException;
//|||||||||||||||||||||||||||||||
//===============================
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru"));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }


        User u = new User("Anton", "Antonof", "antonof@mail.me");
        Car c = new Car("Audi", 4);
        userService.add(u.setCar(c).setUser(u));

        u = new User("Boris", "Borisof", "borisof@mail.me");
        c = new Car("BMW", 5);
        userService.add(u.setCar(c).setUser(u));

        u = new User("Clim", "Climof", "climof@mail.me");
        c = new Car("Chery", 7);
        userService.add(u.setCar(c).setUser(u));

        u = new User("David", "Davidof", "davidof@mail.me");
        c = new Car("DeLorean", 12);
        userService.add(u.setCar(c).setUser(u));

        c = new Car("Lada", 2101);

        for (User user : userService.listUsers()) {
            System.out.println(user + " - владелец - " + user.getCar());
        }

        try {
            System.out.println(userService.getUserByCar("DeLorean", 12) +
                    " хозяин DeLorean 12");
            System.out.println(userService.getUserByCar(c.getModel(), c.getSeries()));

        } catch (NoResultException e) {
            System.out.println("Хозяин " + c.getModel() + " " + c.getSeries() + " не найден.");
        }

        context.close();
    }
}
