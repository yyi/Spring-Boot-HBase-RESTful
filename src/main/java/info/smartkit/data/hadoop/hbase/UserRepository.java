package info.smartkit.data.hadoop.hbase;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by smartkit on 2016/11/5.
 */
@Repository
public class UserRepository {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    private String tableName = "users";

    public static byte[] CF_INFO = Bytes.toBytes("cfInfo");

    private byte[] qUser = Bytes.toBytes("user");
    private byte[] qEmail = Bytes.toBytes("email");
    private byte[] qPassword = Bytes.toBytes("password");

    public List<User> findAll() {
        return hbaseTemplate.find(tableName, "cfInfo", (result, rowNum) -> new User(Bytes.toString(result.getValue(CF_INFO, qUser)),
                Bytes.toString(result.getValue(CF_INFO, qEmail)),
                Bytes.toString(result.getValue(CF_INFO, qPassword))));

    }

    public User save(final String userName, final String email,
                     final String password) {
        return hbaseTemplate.execute(tableName, table -> {
            User user = new User(userName, email, password);
            Put p = new Put(Bytes.toBytes(user.getName()));
            p.addColumn(CF_INFO, qUser, Bytes.toBytes(user.getName()));
            p.addColumn(CF_INFO, qEmail, Bytes.toBytes(user.getEmail()));
            p.addColumn(CF_INFO, qPassword, Bytes.toBytes(user.getPassword()));
            table.put(p);
            return user;

        });
    }

}
