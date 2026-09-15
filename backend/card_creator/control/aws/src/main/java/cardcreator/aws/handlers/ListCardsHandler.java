package cardcreator.aws.handlers;

import cardcreator.aws.util.SupplierHandler;
import cardcreator.data.CardAndId;
import cardcreator.data.ErrorMessageException;
import cardcreator.service.Service;

import java.util.List;

public class ListCardsHandler extends SupplierHandler
{
    private final Service service = new Service();

    @Override
    protected List<CardAndId> get() throws ErrorMessageException
    {
        return service.listCards();
    }
}
