package ru.nikitin.userservice.codemark.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@Table(name = "role")
@IdClass(RolePK.class)
@Data
@NoArgsConstructor
public class Role implements Persistable<RolePK> {

    @Id
    private String roleName;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "user_login", referencedColumnName = "login")})
    private User user;


    public Role(String roleName, User user) {
        this.roleName = roleName;
        this.user = user;
    }

    @Transient
    private boolean isNew = true;

    @Override
    public RolePK getId() {
        return new RolePK(roleName, user);
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
