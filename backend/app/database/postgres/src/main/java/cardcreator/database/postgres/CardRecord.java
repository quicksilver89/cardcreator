package cardcreator.database.postgres;

import cardcreator.data.Card;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table( name="cards" )
public class CardRecord
{
    @Id
    @UuidGenerator
    @Column( name="card_id" )
    private UUID cardId;

    @Column( name="user_id" )
    private String userId;

    @Column( name="card_name" )
    private String cardName;

    @Column( name="card_cost" )
    private String cardCost;

    @Column( name="card_text" )
    private String cardText;

    public CardRecord()
    {
    }

    public CardRecord( String userId, Card card )
    {
        this.userId = userId;
        this.cardName = card.name();
        this.cardCost = card.cost();
        this.cardText = card.text();
    }

    public UUID getCardId()
    {
        return cardId;
    }

    public void setCardId( UUID cardId )
    {
        this.cardId = cardId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

    public String getCardName()
    {
        return cardName;
    }

    public void setCardName( String cardName )
    {
        this.cardName = cardName;
    }

    public String getCardCost()
    {
        return cardCost;
    }

    public void setCardCost( String cardCost )
    {
        this.cardCost = cardCost;
    }

    public String getCardText()
    {
        return cardText;
    }

    public void setCardText( String cardText )
    {
        this.cardText = cardText;
    }
}