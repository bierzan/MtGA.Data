package brzn.mtgadata.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<Card, Long> {

    @Query(value = "SELECT c FROM Card c WHERE c.arenaId=?1")
    Card findOneByArenaId(Long id);

    Integer countCardsBySetNameAndRarity(String setName, Rarity rarity);
}
