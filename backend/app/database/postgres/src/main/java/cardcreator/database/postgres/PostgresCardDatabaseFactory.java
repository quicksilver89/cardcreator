package cardcreator.database.postgres;

import cardcreator.database.CardDatabase;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public final class PostgresCardDatabaseFactory
{
    private static final String CONFIG_FILE = "/card-database.properties";

    private PostgresCardDatabaseFactory()
    {
    }

    public static CardDatabase create()
    {
        Properties properties = loadProperties();
        migrate( properties );

        SessionFactory sessionFactory = new Configuration()
                .addProperties( properties )
                .addAnnotatedClass( CardRecord.class )
                .buildSessionFactory();

        PostgresCardDatabase database = new PostgresCardDatabase( sessionFactory );
        Runtime.getRuntime().addShutdownHook( new Thread( sessionFactory::close ) );

        return database;
    }

    private static Properties loadProperties()
    {
        Properties props = new Properties();
        try( InputStream in = PostgresCardDatabaseFactory.class.getResourceAsStream( CONFIG_FILE ) )
        {
            props.load( in );
        }
        catch( IOException e )
        {
            throw new UncheckedIOException( "Could not read card database configuration", e );
        }

        props.setProperty( AvailableSettings.HBM2DDL_AUTO, "validate" );

        return props;
    }

    private static void migrate( Properties properties )
    {
        Flyway.configure()
            .dataSource(
                properties.getProperty( AvailableSettings.JAKARTA_JDBC_URL ),
                properties.getProperty( AvailableSettings.JAKARTA_JDBC_USER ),
                properties.getProperty( AvailableSettings.JAKARTA_JDBC_PASSWORD ) )
            .locations( "classpath:db/migration" )
            .load()
            .migrate();
    }
}