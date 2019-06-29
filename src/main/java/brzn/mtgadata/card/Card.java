package brzn.mtgadata.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;


@Entity
@Table(name = "cards")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    private long arenaId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @Getter
    @Setter
    private String setName;

    @Getter
    @Setter
    private URL imageUrl;

    @Getter
    @Setter
    private URL uri;

}