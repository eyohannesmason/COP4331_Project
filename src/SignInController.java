
public class SignInController {
    public SignInController() {

    }

    public static void main(String[] args) throws  Exception{
        String name="Jack Black", password = "mYpA5sw0Rd", type="musician";
        SignInController controller = new SignInController();
        controller.addUser(name, password, type);
    }

    public void addUser(String name, String password, String type) throws Exception {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Database.addUser(name, hashedPassword, type);
    }
}
