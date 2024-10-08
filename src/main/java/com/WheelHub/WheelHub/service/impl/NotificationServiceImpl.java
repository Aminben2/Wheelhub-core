package com.WheelHub.WheelHub.service.impl;

import com.WheelHub.WheelHub.dto.notificationDtos.NotificationDto;
import com.WheelHub.WheelHub.entity.Notification;
import com.WheelHub.WheelHub.entity.User;
import com.WheelHub.WheelHub.mapper.NotificationMapper;
import com.WheelHub.WheelHub.repository.NotificationRepository;
import com.WheelHub.WheelHub.repository.UserRepository;
import com.WheelHub.WheelHub.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public NotificationDto createNotification(NotificationDto notificationDTO) {
    User user =
        userRepository
            .findById(notificationDTO.getUserId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "User not found for id:" + notificationDTO.getUserId()));

    Notification notification = NotificationMapper.dtoToEntity(notificationDTO);
    notification.setUser(user);

    notification = notificationRepository.save(notification);
    return NotificationMapper.entityToDTO(notification);
  }

  @Override
  public Notification findById(Long id) {
    return notificationRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Notification not found for id:" + id));
  }

  @Override
  public NotificationDto getNotificationById(Long id) {
    return notificationRepository
        .findById(id)
        .map(NotificationMapper::entityToDTO)
        .orElseThrow(() -> new EntityNotFoundException("Notification not found for id:" + id));
  }

  @Override
  public List<NotificationDto> getAllNotifications() {
    return notificationRepository.findAll().stream()
        .map(NotificationMapper::entityToDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public NotificationDto updateNotification(Long id, NotificationDto notificationDTO) {
    Notification existingNotification =
        notificationRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Notification not found for id:" + id));

    User user =
        userRepository
            .findById(notificationDTO.getUserId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "User not found for id:" + notificationDTO.getUserId()));

    existingNotification.setUser(user);
    existingNotification.setNotificationType(notificationDTO.getNotificationType());
    existingNotification.setDetails(notificationDTO.getDetails());

    Notification updatedNotification = notificationRepository.save(existingNotification);
    return NotificationMapper.entityToDTO(updatedNotification);
  }

  @Override
  public void deleteNotification(Long id) {
    Notification notification =
        notificationRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Notification not found for id:" + id));
    notificationRepository.delete(notification);
  }
}
