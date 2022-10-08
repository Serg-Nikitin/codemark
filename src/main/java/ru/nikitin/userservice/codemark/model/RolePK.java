package ru.nikitin.userservice.codemark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePK implements Serializable {

    private String roleName;

    private User user;
}
