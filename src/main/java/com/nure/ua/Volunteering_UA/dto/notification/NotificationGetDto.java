package com.nure.ua.Volunteering_UA.dto.notification;

import com.nure.ua.Volunteering_UA.model.Notification;
import lombok.Data;
@Data
public class NotificationGetDto {


  private String message;

  public static NotificationGetDto toDto(Notification notification){
    NotificationGetDto notificationGetDto = new NotificationGetDto();
    notificationGetDto.setMessage(notification.getMessage());
    return notificationGetDto;
  }

}
