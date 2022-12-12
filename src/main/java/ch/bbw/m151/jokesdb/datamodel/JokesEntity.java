package ch.bbw.m151.jokesdb.datamodel;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import reactor.util.annotation.Nullable;

import javax.persistence.*;
import java.util.Date;

@Setter
@RequiredArgsConstructor
@Getter
@Entity
@Table(name = "jokes")
public class JokesEntity {

	@Id
	@GeneratedValue
	private int id;

	@Accessors(chain = true)
	@Column(nullable = false)
	private String joke;

	private String rating;

	@CreationTimestamp
	private Date createDate;

	@UpdateTimestamp
	private Date updateDate;

	public void rateJoke(String newRating) {
		setRating(newRating);
	}

}