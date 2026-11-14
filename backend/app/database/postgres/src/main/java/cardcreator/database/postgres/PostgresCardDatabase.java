package cardcreator.database.postgres;

import cardcreator.data.Card;
import cardcreator.data.CardAndId;
import cardcreator.database.CardDatabase;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostgresCardDatabase implements CardDatabase
{
    private final SessionFactory session;

    public PostgresCardDatabase( SessionFactory session )
    {
        this.session = session;
    }

    @Override
    public List<CardAndId> listCards( String userId )
    {
        return session.fromTransaction( s ->
        {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<CardRecord> query = cb.createQuery( CardRecord.class );
            Root<CardRecord> card = query.from( CardRecord.class );

            query.select( card ).where( cb.equal( card.get( CardRecord_.userId ), userId ) );

            List<CardRecord> records = s.createQuery( query ).getResultList();
            List<CardAndId> cards = new ArrayList<>( records.size() );
            for( CardRecord record : records )
            {
                cards.add( new CardAndId( record.getCardId().toString(), toCard( record ) ) );
            }
            return cards;
        } );
    }

    @Override
    public Card getCard( String userId, String cardId )
    {
        return session.fromTransaction( s ->
        {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<CardRecord> query = cb.createQuery( CardRecord.class );
            Root<CardRecord> card = query.from( CardRecord.class );

            query.select( card ).where(
                cb.equal( card.get( CardRecord_.cardId ), UUID.fromString( cardId ) ),
                cb.equal( card.get( CardRecord_.userId ), userId ) );

            CardRecord record = s.createQuery( query ).getSingleResultOrNull();
            return record == null ? null : toCard( record );
        } );
    }

    @Override
    public String addNewCard( String userId, Card card )
    {
        return session.fromTransaction( s ->
        {
            CardRecord record = new CardRecord( userId, card );
            s.persist( record );
            return record.getCardId().toString();
        } );
    }

    @Override
    public boolean editCard( String userId, String cardId, Card oldCard, Card newCard )
    {
        return session.fromTransaction( s ->
        {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaUpdate<CardRecord> update = cb.createCriteriaUpdate( CardRecord.class );
            Root<CardRecord> card = update.from( CardRecord.class );

            update
                .set( card.get( CardRecord_.cardName ), newCard.name() )
                .set( card.get( CardRecord_.cardCost ), newCard.cost() )
                .set( card.get( CardRecord_.cardText ), newCard.text() )
                .where(
                    cb.equal( card.get( CardRecord_.cardId ), UUID.fromString( cardId ) ),
                    cb.equal( card.get( CardRecord_.userId ), userId ),
                    eqOrNull( cb, card.get( CardRecord_.cardName ), oldCard.name() ),
                    eqOrNull( cb, card.get( CardRecord_.cardCost ), oldCard.cost() ),
                    eqOrNull( cb, card.get( CardRecord_.cardText ), oldCard.text() ) );

            return s.createMutationQuery( update ).executeUpdate() > 0;
        } );
    }

    @Override
    public boolean deleteCard( String userId, String cardId, Card card )
    {
        return session.fromTransaction( s ->
        {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaDelete<CardRecord> delete = cb.createCriteriaDelete( CardRecord.class );
            Root<CardRecord> record = delete.from( CardRecord.class );

            delete
                .where(
                    cb.equal( record.get( CardRecord_.cardId ), UUID.fromString( cardId ) ),
                    cb.equal( record.get( CardRecord_.userId ), userId ),
                    eqOrNull( cb, record.get( CardRecord_.cardName ), card.name() ),
                    eqOrNull( cb, record.get( CardRecord_.cardCost ), card.cost() ),
                    eqOrNull( cb, record.get( CardRecord_.cardText ), card.text() ) );

            return s.createMutationQuery( delete ).executeUpdate() > 0;
        } );
    }

    private static Card toCard( CardRecord record )
    {
        return new Card( record.getCardName(), record.getCardCost(), record.getCardText() );
    }

    private static <T> Predicate eqOrNull( CriteriaBuilder cb, Path<T> path, T value )
    {
        return value == null ? cb.isNull( path ) : cb.equal( path, value );
    }
}