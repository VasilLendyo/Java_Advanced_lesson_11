import domain.User;
import service.UserService;
import service.impl.UserServiceImpl;

public class Application {

	public static void main(String[] args) {
		
		UserService userService = new UserServiceImpl();
		userService.create(new User("levas09meta.ua", "Vasyl", "Lendo", "Admin"));
		System.out.println(userService.read(4));
		userService.update(new User(3, "asd", "asd", "asd", "asd"));
		userService.delete(1);
		System.out.println(userService.readAll());
	}
}
