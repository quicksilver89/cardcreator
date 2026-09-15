package cardcreator.aws.handlers;

import cardcreator.aws.util.ConsumerHandler;
import cardcreator.data.CardAndId;
import cardcreator.data.ErrorMessageException;
import cardcreator.service.Service;

public class DeleteCardHandler extends ConsumerHandler<CardAndId>
{
    private final Service service = new Service();

    public DeleteCardHandler()
    {
        super( CardAndId.class );
    }

    @Override
    protected void accept( CardAndId cardAndId ) throws ErrorMessageException
    {
        service.deleteCard( cardAndId );
    }
}
