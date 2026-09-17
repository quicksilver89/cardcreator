package cardcreator.springboot;

import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

/**
 * Puts all values from the config file onto System.properties
 */
public class SettingsImporter implements EnvironmentPostProcessor
{
    @Override
    public void postProcessEnvironment( ConfigurableEnvironment environment, SpringApplication application )
    {
        for( PropertySource<?> propertySource : environment.getPropertySources() )
        {
            if( propertySource instanceof OriginTrackedMapPropertySource source )
            {
                for( String key : source.getPropertyNames() )
                {
                    String value = environment.getProperty( key );
                    if( value != null && System.getProperty(key) == null )
                    {
                        System.setProperty( key, value );
                    }
                }
            }
        }
    }
}