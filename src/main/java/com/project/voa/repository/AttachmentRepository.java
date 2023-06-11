package com.project.voa.repository;

import com.project.voa.domain.Attachment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
}
