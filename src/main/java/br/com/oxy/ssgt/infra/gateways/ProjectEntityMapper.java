package br.com.oxy.ssgt.infra.gateways;


import br.com.oxy.ssgt.domain.entities.project.Project;
import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.persistence.project.ProjectEntity;
import br.com.oxy.ssgt.infra.persistence.project.ProjectMemberEntity;
import br.com.oxy.ssgt.infra.persistence.project.ProjectRole;

import java.util.ArrayList;
import java.util.List;

public class ProjectEntityMapper {

    private final UserEntityMapper userMapper;

    public ProjectEntityMapper(UserEntityMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ProjectEntity toEntity(Project project){
        ProjectEntity projectEntity = new ProjectEntity(
                project.getId(),
                project.getName(),
                project.getDescription(),
                userMapper.toEntity(project.getOwner())
        );
        List<ProjectMemberEntity> members = new ArrayList<>();

        members.add(
                new ProjectMemberEntity(
                        null,
                        projectEntity,
                        userMapper.toEntity(project.getOwner()),
                        ProjectRole.ADMIN
                )
        );

        project.getMembers()
                .stream()
                .filter(member -> !member.getId().equals(project.getOwner().getId()))
                .map(member -> new ProjectMemberEntity(
                        null,
                        projectEntity,
                        userMapper.toEntity(member),
                        ProjectRole.MEMBER
                ))
                .forEach(members::add);
        projectEntity.setMembers(members);

        return projectEntity;
    }

    public Project toDomain(ProjectEntity projectEntity){

        List<User> members = projectEntity.getMembers()
                .stream()
                .map(member -> userMapper.toDomain(member.getUser()))
                .toList();

        return new Project(
                projectEntity.getId(),
                projectEntity.getName(),
                projectEntity.getDescription(),
                userMapper.toDomain(projectEntity.getOwner()),
                members
        );
    }
}
