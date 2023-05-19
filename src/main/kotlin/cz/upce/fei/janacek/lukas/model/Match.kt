package cz.upce.fei.janacek.lukas.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

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
    @OneToMany
    val teamOnePlayers: Set<User>,
    @OneToMany
    val teamTwoPlayers: Set<User>
)