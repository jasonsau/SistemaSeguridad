package com.example.demo.paswword;

import com.example.demo.user.UserEmployee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PasswordHistory {

    @Id
    @SequenceGenerator(
            name = "password_history_sequence",
            initialValue = 1,
            sequenceName = "password_history_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "password_history_sequence"
    )
    private LocalDateTime createAt;
    private LocalDateTime expiredAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "id_user_password_history"
    )
    private UserEmployee userEmployee;

    public PasswordHistory(LocalDateTime createAt,
                           LocalDateTime expiredAt,
                           UserEmployee userEmployee) {
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.userEmployee = userEmployee;
    }

}
