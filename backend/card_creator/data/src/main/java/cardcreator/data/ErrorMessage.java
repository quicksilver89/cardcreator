package cardcreator.data;

public record ErrorMessage( String error )
{
    public ErrorMessage( ErrorMessageException error )
    {
        this( error.getMessage() );
    }

    public ErrorMessage( Exception error )
    {
        this( error.getClass().getSimpleName() + ": " + error.getMessage() );
    }
}
