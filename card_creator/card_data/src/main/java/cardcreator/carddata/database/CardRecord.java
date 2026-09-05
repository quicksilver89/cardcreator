package cardcreator.carddata.database;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;

@DynamoDbBean
public class CardRecord
{
    private String id;
    private String gsiPk;
    private String createdAt;
    private String cardName;
    private String cardCost;
    private String cardText;

    @DynamoDbPartitionKey
    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "InsertionOrderIndex")
    public String getGsiPk()
    {
        return gsiPk;
    }

    public void setGsiPk( String gsiPk )
    {
        this.gsiPk = gsiPk;
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