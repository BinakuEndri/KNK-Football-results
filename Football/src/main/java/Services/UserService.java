package Services;

import Models.Users;
import Repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    public static Users login(String username,String password) throws SQLException {
            Users user = UserRepository.getByUsername(username);
            if(user == null){
                return null;
            }
            boolean isPasswordCorrect = PasswordHasher.compareSaltedHash(
                    password,user.getSalt(),user.getPassword());

            if(isPasswordCorrect){
                return user;
            }

        return null;

    }
}
