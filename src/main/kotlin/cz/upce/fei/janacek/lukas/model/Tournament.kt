package cz.upce.fei.janacek.lukas.model

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Tournament (
    @Id
    @Column(name = "tournament_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column
    val name: String,
    @Column
    val description: String,
    @Column
    val startDateTime: LocalDateTime,
    @Column
    val endDateTime: LocalDateTime,
    @Column
    @OneToMany
    val participants: Set<Team>?
)