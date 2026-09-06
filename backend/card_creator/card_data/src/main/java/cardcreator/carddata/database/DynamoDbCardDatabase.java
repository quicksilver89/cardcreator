package cardcreator.carddata.database;

import cardcreator.carddata.data.Card;
import cardcreator.carddata.data.CardAndId;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.DeleteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.IgnoreNullsMode;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ConditionalCheckFailedException;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DynamoDbCardDatabase implements CardDatabase
{
    private static final String GSI_PK_VALUE = "ALL";

    private final DynamoDbTable<CardRecord> table;

    public DynamoDbCardDatabase()
    {
        String endpoint = System.getenv( "DYNAMODB_ENDPOINT" );
        String tableName = System.getenv( "CARD_TABLE_NAME" );

        DynamoDbClientBuilder client = DynamoDbClient.builder();
        if( endpoint != null && !endpoint.isEmpty() )
        {
            client.endpointOverride( URI.create( endpoint ) );
            client.region( Region.US_EAST_1 );
            client.credentialsProvider( StaticCredentialsProvider.create( AwsBasicCredentials.create( "none", "none" ) ) );
        }

        DynamoDbEnhancedClient enhanced = DynamoDbEnhancedClient.builder()
            .dynamoDbClient( client.build() )
            .build();

        this.table = enhanced.table( tableName, TableSchema.fromBean( CardRecord.class ) );
    }

    @Override
    public List<CardAndId> listCards()
    {
        DynamoDbIndex<CardRecord> insertionTimeIndex = table.index( "InsertionOrderIndex" );

        QueryConditional queryConditional = QueryConditional.keyEqualTo( Key.builder().partitionValue( GSI_PK_VALUE ).build() );
        QueryEnhancedRequest queryRequest = QueryEnhancedRequest.builder()
            .queryConditional( queryConditional )
            .scanIndexForward( true )
            .build();

        List<CardAndId> cards = new ArrayList<>();

        for( Page<CardRecord> page : insertionTimeIndex.query( queryRequest ) )
        {
            for( CardRecord card : page.items() )
            {
                cards.add( new CardAndId( card.getId(), new Card( card.getCardName(), card.getCardCost(), card.getCardText() ) ) );
            }
        }

        return cards;
    }

    @Override
    public Card getCard( String id )
    {
        Key key = Key.builder() .partitionValue( id ).build();
        CardRecord result = table.getItem( r -> r.key( key ) );
        return result == null ? null : new Card( result.getCardName(), result.getCardCost(), result.getCardText() );
    }

    @Override
    public String addNewCard( Card card )
    {
        for( int i=0; i<10; i++ )
        {
            try
            {
                String id = UUID.randomUUID().toString();

                CardRecord record = new CardRecord();
                record.setId( id );
                record.setGsiPk( GSI_PK_VALUE );
                record.setCreatedAt( Instant.now().toString() );

                record.setCardName( card.name() );
                record.setCardCost( card.cost() );
                record.setCardText( card.text() );

                table.putItem( PutItemEnhancedRequest.builder( CardRecord.class )
                    .item(record)
                    .conditionExpression( Expression.builder().expression( "attribute_not_exists(id)" ).build() )
                    .build() );

                return id;
            }
            catch( ConditionalCheckFailedException e )
            {
            }
        }

        return null;
    }

    @Override
    public boolean editCard( String id, Card oldCard, Card newCard )
    {
        if( oldCard.equals( newCard ) )
        {
            return true;
        }

        try
        {
            CardRecord recordToUpdate = new CardRecord();
            recordToUpdate.setId( id );
            if( !newCard.name().equals( oldCard.name() ) )
            {
                recordToUpdate.setCardName( newCard.name() );
            }
            if( !newCard.cost().equals( oldCard.cost() ) )
            {
                recordToUpdate.setCardCost( newCard.cost() );
            }
            if( !newCard.text().equals( oldCard.text() ) )
            {
                recordToUpdate.setCardText( newCard.text() );
            }

            table.updateItem( UpdateItemEnhancedRequest.builder( CardRecord.class )
                .item( recordToUpdate )
                .ignoreNullsMode( IgnoreNullsMode.SCALAR_ONLY )
                .conditionExpression( createCardMatchingCondition( oldCard ) )
                .build() );
            return true;
        }
        catch( ConditionalCheckFailedException e )
        {
            return false;
        }
    }

    @Override
    public boolean deleteCard( String id, Card card )
    {
        try
        {
            table.deleteItem( DeleteItemEnhancedRequest.builder()
                .key( Key.builder().partitionValue( id ).build() )
                .conditionExpression( createCardMatchingCondition( card ) )
                .build() );
            return true;
        }
        catch( ConditionalCheckFailedException e )
        {
            return false;
        }
    }


    private static Expression createCardMatchingCondition( Card card )
    {
        List<String> conditions = new ArrayList<>();
        Expression.Builder expression = Expression.builder();

        conditions.add( createExpression( expression, "cardName", card.name() ) );
        conditions.add( createExpression( expression, "cardCost", card.cost() ) );
        conditions.add( createExpression( expression, "cardText", card.text() ) );

        expression.expression( String.join( " AND ", conditions ) );

        return expression.build();
    }

    private static String createExpression( Expression.Builder expression, String fieldName, String fieldValue )
    {
        if( fieldValue == null || fieldValue.isEmpty() )
        {
            expression.putExpressionValue( ":zero", AttributeValue.fromN( "0" ) );
            return "size(" + fieldName + ") = :zero";
        }
        else
        {
            expression.putExpressionValue( ":" + fieldName, AttributeValue.fromS( fieldValue ) );
            return fieldName + " = :" + fieldName;
        }
    }
}