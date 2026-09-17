package cardcreator.springboot;

import cardcreator.data.Card;
import cardcreator.data.CardAndId;
import cardcreator.data.CardId;
import cardcreator.data.EditCard;
import cardcreator.data.ErrorMessageException;
import cardcreator.service.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestServices
{
    private final Service service = new Service();

    @GetMapping( "/service/listcards" )
    public List<CardAndId> listCards() throws ErrorMessageException
    {
        return service.listCards();
    }

    @PostMapping( "/service/getcard" )
    public Card getCard( @RequestBody CardId cardId ) throws ErrorMessageException
    {
        return service.getCard( cardId );
    }

    @PostMapping( "/service/newcard" )
    public CardId newCard( @RequestBody Card card ) throws ErrorMessageException
    {
        return service.newCard( card );
    }

    @PostMapping( "/service/editcard" )
    public void editCard( @RequestBody EditCard cardEdit ) throws ErrorMessageException
    {
        service.editCard( cardEdit );
    }

    @PostMapping( "/service/deletecard" )
    public void deleteCard( @RequestBody CardAndId cardAndId ) throws ErrorMessageException
    {
        service.deleteCard( cardAndId );
    }
}
