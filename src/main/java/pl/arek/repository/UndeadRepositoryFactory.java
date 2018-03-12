package pl.arek.repository;

import pl.arek.service.UndeadManagerImpl;

import java.sql.DriverManager;
import java.sql.SQLException;

public class UndeadRepositoryFactory {


    public static UndeadRepository getInstance(){
        try {
            String url = "jdbc:hsqldb:hsql://localhost/workdb";
            return new UndeadRepositoryImpl(DriverManager.getConnection(url));
        }
        catch (SQLException e){
            return null;
        }
    }
}
