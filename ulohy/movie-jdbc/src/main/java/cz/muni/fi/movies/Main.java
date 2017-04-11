package cz.muni.fi.movies;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * Main class
 */
public class Main {

    final static Logger log = LoggerFactory.getLogger(Main.class);

    public static DataSource createMemoryDatabase() {
        BasicDataSource bds = new BasicDataSource();
        //set JDBC driver and URL
        bds.setDriverClassName(EmbeddedDriver.class.getName());
        bds.setUrl("jdbc:derby:memory:MojeDB;create=true");
        //populate db with tables and data
        new ResourceDatabasePopulator(
                new ClassPathResource("schema-javadb.sql"),
                new ClassPathResource("test-data.sql"))
                .execute(bds);
        return bds;
    }

    public static void main(String[] args) throws MovieException, IOException {

        log.info("zaciname");

        DataSource dataSource = createMemoryDatabase();
        MovieManager movieManager = new MovieManagerImpl(dataSource);

        List<Movie> allMovies = movieManager.getAllMovies();
        System.out.println("allMovies = " + allMovies);
        /**
        Properties myconf = new Properties();
        myconf.load(Main.class.getResourceAsStream("/myconf.properties"));

        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(myconf.getProperty("jdbc.url"));
        ds.setUsername(myconf.getProperty("jdbc.user"));
        ds.setPassword(myconf.getProperty("jdbc.password"));

        MovieManager movieManager = new MovieManagerImpl(ds);
        Movie movie = new Movie(null, "Mafia", 1980, null,
                null, null);
        movieManager.createMovie(movie);

        CustomerManager customerManager = new CustomerManagerImpl(ds);
        Customer customer = new Customer(null, "Tomáš", LocalDate.of(2000, Month.JANUARY, 1), null,
                null, null);
        customerManager.createCustomer(customer);

        LeaseManagerImpl leaseManager = new LeaseManagerImpl(ds);
        leaseManager.setMovieManager(movieManager);
        leaseManager.setCustomerManager(customerManager);
        Lease lease = new Lease(null, movie, customer, 200,
                LocalDate.of(2000, Month.JANUARY, 1), LocalDate.of(2001, Month.JANUARY, 1),
                LocalDate.of(2000, Month.FEBRUARY, 1));
        leaseManager.createLease(lease);
        System.out.println(leaseManager.getLease(lease.getId()));

        List<Movie> allMovies = movieManager.getAllMovies();
        allMovies.forEach(System.out::println);
        List<Customer> allCustomers = customerManager.getAllCustomers();
        allCustomers.forEach(System.out::println);
        List<Lease> allLeases = leaseManager.getAllLeases();
        allLeases.forEach(System.out::println);

        movieManager.deleteMovie(movie);
        customerManager.deleteCustomer(customer);*/
    }
}
