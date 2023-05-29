package cz.upce.fei.janacek.lukas.model

import java.time.LocalDateTime
import jakarta.persistence.*

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
    @OneToMany
    val matches: Set<Match>,
    @OneToMany
    val participants: Set<Team>?
)