package cardcreator.springboot;

import cardcreator.data.ErrorMessage;
import cardcreator.data.ErrorMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler( ErrorMessageException.class )
    public ResponseEntity<ErrorMessage> handleErrorMessageException( ErrorMessageException e )
    {
        return new ResponseEntity<>( e.getErrorMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
    }
}
