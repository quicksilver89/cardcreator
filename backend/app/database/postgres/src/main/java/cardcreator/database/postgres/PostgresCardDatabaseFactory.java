package cardcreator.database.postgres;

import cardcreator.database.CardDatabase;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public final class PostgresCardDatabaseFactory
{
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
        Properties properties = new Properties();
        setProperty( properties, "POSTGRES_URL", AvailableSettings.JAKARTA_JDBC_URL, null );
        setProperty( properties, "POSTGRES_USER", AvailableSettings.JAKARTA_JDBC_USER, null );
        setProperty( properties, "POSTGRES_PASSWORD", AvailableSettings.JAKARTA_JDBC_PASSWORD, null );
        setProperty( properties, null, AvailableSettings.HBM2DDL_AUTO, "validate" );
        setProperty( properties, null, "hibernate.hikari.maximumPoolSize", "10" );
        setProperty( properties, null, "hibernate.show_sql", "false" );
        return properties;
    }

    private static void setProperty( Properties properties, String envName, String propertyName, String defaultVal )
    {
        if( envName == null )
        {
            envName = propertyName.toUpperCase().replace( '.', '_' );
        }

        String val = System.getenv( envName );
        if( val == null )
        {
            val = System.getProperty( propertyName );
            if( val == null )
            {
                if( defaultVal == null )
                {
                    throw new IllegalStateException( "Missing required env \"" + envName + "\" or property \"" + propertyName + "\"" );
                }
                val = defaultVal;
            }
        }

        properties.setProperty( propertyName, val );
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
