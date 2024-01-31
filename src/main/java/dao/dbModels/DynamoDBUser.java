package dao.dbModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class DynamoDBUser {
    public String user_id;
    public String password;
    public String fullname;
    public String last_login;
    public String date_registered;
}
