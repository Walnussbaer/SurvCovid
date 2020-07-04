package org.hackathon.wirvswirus.thecouchdevs.SurvCovid.game.logic.service;

import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.Role;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.entity.enumeration.RoleName;
import org.hackathon.wirvswirus.thecouchdevs.SurvCovid.data.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role saveRole(Role role) {

        if (role == null) {
            throw new NullPointerException("role cannot be null");
        }
        this.roleRepository.save(role);

        return role;
    }

    public Optional<Role> findByName(RoleName roleName) {

      return this.roleRepository.findByName(roleName);

    }

}
