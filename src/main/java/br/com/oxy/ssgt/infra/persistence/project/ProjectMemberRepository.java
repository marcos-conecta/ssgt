package br.com.oxy.ssgt.infra.persistence.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, Long> {

    boolean existsByProject_IdAndUser_IdAndRole(Long projectId, Long userId, ProjectRole role);

    boolean existsByProject_IdAndUser_Id(Long projectId, Long currentUserId);
}
