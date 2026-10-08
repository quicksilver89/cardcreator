package cardcreator.database.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
public class CardRecord
{
    private String userId;
    private String cardId;
    private String createdAt;
    private String cardName;
    private String cardCost;
    private String cardText;

    @DynamoDbPartitionKey
    @DynamoDbSecondaryPartitionKey(indexNames = "InsertionOrderIndex")
    public String getUserId()
    {
        return userId;
    }

    public void setUserId( String userId )
    {
        this.userId = userId;
    }

    @DynamoDbSortKey
    public String getCardId()
    {
        return cardId;
    }

    public void setCardId( String cardId )
    {
        this.cardId = cardId;
    }

    @DynamoDbSecondarySortKey(indexNames = "InsertionOrderIndex")
    public String getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt( String createdAt )
    {
        this.createdAt = createdAt;
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