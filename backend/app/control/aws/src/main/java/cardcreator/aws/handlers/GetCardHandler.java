package cardcreator.aws.handlers;

import cardcreator.aws.util.FunctionHandler;
import cardcreator.data.Card;
import cardcreator.data.CardId;
import cardcreator.data.ErrorMessageException;
import cardcreator.service.Service;

public class GetCardHandler extends FunctionHandler<CardId>
{
    private final Service service = new Service();

    public GetCardHandler()
    {
        super( CardId.class );
    }

    @Override
    protected Card apply( CardId cardId ) throws ErrorMessageException
    {
        return service.getCard( cardId );
    }
}
