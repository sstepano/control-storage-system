package org.code_studio.main;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import static ch.qos.logback.core.pattern.color.ANSIConstants.*;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class HighlightingCompositeConverterEx extends ForegroundCompositeConverterBase<ILoggingEvent> {

  @Override
  protected String getForegroundColorCode(ILoggingEvent event) {
    Level level = event.getLevel();
    return switch (level.toInt()) {
      case Level.ERROR_INT -> RED_FG;
      case Level.WARN_INT  -> MAGENTA_FG;
      case Level.INFO_INT  -> WHITE_FG;
      case Level.DEBUG_INT -> YELLOW_FG;
      case Level.TRACE_INT -> WHITE_FG;
      default -> RED_FG;
    };
  }
}