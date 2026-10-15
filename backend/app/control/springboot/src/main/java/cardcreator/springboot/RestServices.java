package cardcreator.springboot;

import cardcreator.data.Card;
import cardcreator.data.CardAndId;
import cardcreator.data.CardId;
import cardcreator.data.EditCard;
import cardcreator.data.ErrorMessageException;
import cardcreator.service.Service;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/service" )
public class RestServices
{
    private final Service service = new Service();

    @GetMapping( "listcards" )
    public List<CardAndId> listCards( @AuthenticationPrincipal Jwt jwt ) throws ErrorMessageException
    {
        return service.listCards( jwt.getSubject() );
    }

    @PostMapping( "getcard" )
    public Card getCard( @AuthenticationPrincipal Jwt jwt, @RequestBody CardId cardId ) throws ErrorMessageException
    {
        return service.getCard( jwt.getSubject(), cardId );
    }

    @PostMapping( "newcard" )
    public CardId newCard( @AuthenticationPrincipal Jwt jwt, @RequestBody Card card ) throws ErrorMessageException
    {
        return service.newCard( jwt.getSubject(), card );
    }

    @PostMapping( "editcard" )
    public void editCard( @AuthenticationPrincipal Jwt jwt, @RequestBody EditCard cardEdit ) throws ErrorMessageException
    {
        service.editCard( jwt.getSubject(), cardEdit );
    }

    @PostMapping( "deletecard" )
    public void deleteCard( @AuthenticationPrincipal Jwt jwt, @RequestBody CardAndId cardAndId ) throws ErrorMessageException
    {
        service.deleteCard( jwt.getSubject(), cardAndId );
    }
}
