/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.configs;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.springframework.boot.logging.structured.StructuredLogFormatter;

public class CustomLoggerFormatting implements StructuredLogFormatter<ILoggingEvent> {
    @Override
    public String format(ILoggingEvent event) {
        return "%clr(TIME="
                + event.getInstant()
                + ") LEVEL="
                + event.getLevel()
                + " MESSAGE="
                + event.getMessage()
                + "\n";
    }
}
