package cardcreator.aws.handlers;

import cardcreator.aws.util.ConsumerHandler;
import cardcreator.data.EditCard;
import cardcreator.data.ErrorMessageException;
import cardcreator.service.Service;

public class EditCardHandler extends ConsumerHandler<EditCard>
{
    private final Service service = new Service();

    public EditCardHandler()
    {
        super( EditCard.class );
    }

    @Override
    protected void accept( EditCard cardEdit ) throws ErrorMessageException
    {
        service.editCard( cardEdit );
    }
}
