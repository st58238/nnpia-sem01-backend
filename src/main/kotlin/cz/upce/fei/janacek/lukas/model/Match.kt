package cz.upce.fei.janacek.lukas.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

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