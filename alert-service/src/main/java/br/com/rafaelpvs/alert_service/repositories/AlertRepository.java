package br.com.rafaelpvs.alert_service.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelpvs.alert_service.entities.AlertEntity;
import br.com.rafaelpvs.alert_service.entities.AlertStatus;
import br.com.rafaelpvs.alert_service.entities.AlertType;

public interface AlertRepository extends JpaRepository<AlertEntity, UUID> {

  boolean existsByEventIdAndType(UUID eventId, AlertType type);

  List<AlertEntity> findAllByOrderByCreatedAtDescIdDesc();

  List<AlertEntity> findByDeviceIdOrderByCreatedAtDescIdDesc(UUID deviceId);

  List<AlertEntity> findByStatusOrderByCreatedAtDescIdDesc(AlertStatus status);

  List<AlertEntity> findByDeviceIdAndStatusOrderByCreatedAtDescIdDesc(
      UUID deviceId,
      AlertStatus status);
}
