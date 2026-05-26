package br.com.dsin.cabeleleila.domain;

import br.com.dsin.cabeleleila.domain.security.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //OBS: campos name e email estao redundantes: existem em Client e User
    //Alternativa: delegar para User essa responsabilidade e unificar essas duas classes no ClientService
    //Isso envolve alterar DDL do banco
    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "client")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
