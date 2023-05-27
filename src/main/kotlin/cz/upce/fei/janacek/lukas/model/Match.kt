package cz.upce.fei.janacek.lukas.model

import jakarta.persistence.*

@Entity
class Match (
    @Id
    @Column(name = "match_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column
    val name: String,
    @Column
    val description: String?,
    @ManyToOne
    @JoinColumn(name = "team_one_id")
    val teamOne: Team,
    @ManyToOne
    @JoinColumn(name = "team_two_id")
    val teamTwo: Team,
    @ManyToMany
    val teamOnePlayers: Set<User>,
    @ManyToMany
    val teamTwoPlayers: Set<User>
)