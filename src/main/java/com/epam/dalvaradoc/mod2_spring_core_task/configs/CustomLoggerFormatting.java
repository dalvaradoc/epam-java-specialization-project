package com.epam.dalvaradoc.mod2_spring_core_task.configs;

import org.springframework.boot.logging.structured.StructuredLogFormatter;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class CustomLoggerFormatting implements StructuredLogFormatter<ILoggingEvent> {
  @Override
  public String format(ILoggingEvent event) {
		return "%clr(TIME=" + event.getInstant() + "){yellow} LEVEL=" + event.getLevel() + " MESSAGE=" + event.getMessage() + "\n";
  }

}
