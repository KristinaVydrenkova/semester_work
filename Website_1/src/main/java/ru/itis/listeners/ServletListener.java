package ru.itis.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.database.*;
import ru.itis.exceptions.DBException;
import ru.itis.services.*;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@WebListener
public class ServletListener implements ServletContextListener {
    private final static String PROPERTIES_PATH = "C:\\Users\\Кристина\\Desktop\\programms\\SemesterWork\\Website_1\\src\\main\\java\\resources\\application.properties";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        Properties properties = new Properties();

        try {
            properties.load(new FileReader(PROPERTIES_PATH));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty("db.driver"));
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.user"));
        config.setPassword(properties.getProperty("db.password"));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.pool-size")));

        DataSource dataSource = new HikariDataSource(config);
        context.setAttribute("dataSource", dataSource);

        UserRepository userRepository = new UserRepositoryImpl(dataSource);

        SecurityService securityService = new SecurityServiceImpl(userRepository);
        context.setAttribute("securityService",securityService);

        TestsRepository testsRepository = new TestsRepositoryImpl(dataSource);

        TestCheckService testCheckService = new TestCheckServiceImpl(testsRepository);
        context.setAttribute("testCheckService",testCheckService);

        PhotoService photoService = new PhotoServiceImpl(userRepository);
        context.setAttribute("photoService",photoService);

        TeachRepository teachRepository = new TeachRepositoryImpl(dataSource);
        StudentsService studentsService = new StudentsServiceImpl(teachRepository,userRepository);
        context.setAttribute("studentsService",studentsService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        DataSource dataSource = (DataSource) context.getAttribute("DataSource");
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            throw new DBException("Connection error");
        }
    }
}
