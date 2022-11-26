package com.appresent.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String description;
	@Column(name = "start_date_hour")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dateHour;
	private String local;
	@Temporal(TemporalType.TIME)
	private LocalTime duration; 
	@OneToOne
	@JoinColumn(name = "modality_fk")
	private Modality modality;
	@ManyToMany
	@JoinTable(name = "participation",
		joinColumns = {@JoinColumn(name = "event_fk")},
		inverseJoinColumns = {@JoinColumn(name = "pearson_fk")})
	private List<Pearson> people;
}
