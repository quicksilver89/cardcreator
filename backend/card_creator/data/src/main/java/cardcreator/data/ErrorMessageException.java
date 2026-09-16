package cardcreator.data;

public class ErrorMessageException extends Exception
{
    public ErrorMessageException( String message )
    {
        super( message );
    }

    public ErrorMessage getErrorMessage()
    {
        return new ErrorMessage( getMessage() );
    }
}
