package cardcreator.aws.handlers;

import cardcreator.aws.util.FunctionHandler;
import cardcreator.data.Card;
import cardcreator.data.CardId;
import cardcreator.data.ErrorMessageException;
import cardcreator.service.Service;

public class NewCardHandler extends FunctionHandler<Card>
{
    private final Service service = new Service();

    public NewCardHandler()
    {
        super( Card.class );
    }

    @Override
    protected CardId apply( Card card ) throws ErrorMessageException
    {
        return service.newCard( card );
    }
}
