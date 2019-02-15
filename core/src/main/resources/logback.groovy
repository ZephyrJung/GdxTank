import ch.qos.logback.classic.filter.ThresholdFilter

import java.nio.charset.Charset

statusListener(OnConsoleStatusListener)

def PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%-25thread] [%X{ip},%X{traceId},%X{appName}] %-5level %logger{36}.%M\\(%line\\) - %msg%n"

def PATH = "/export/Logs/org.b3log.zephyr"

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
        charset = Charset.forName("utf-8")
    }
}

appender("FILE-ALL", RollingFileAppender) {
    file = "${PATH}/file-all.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
        charset = Charset.forName("utf-8")
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${PATH}/file-all.log.%d"
        maxHistory = 6
    }
}


appender("FILE-ERROR", RollingFileAppender) {
    file = "${PATH}/file-error.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
        charset = Charset.forName("utf-8")
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${PATH}/file-error.log.%d"
        maxHistory = 6
    }
    filter(ThresholdFilter) {
        level = WARN
    }
}

appender("BINLOG_BIZ_LOG", RollingFileAppender) {
    file = "${PATH}/binlog_biz.log"
    append = true
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
        charset = Charset.forName("utf-8")
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${PATH}/binlog_biz.log.%d"
        maxHistory = 6
    }
}


logger("com.xstore.order.middleware.server.infrastructure.binlake.biz", INFO, ["BINLOG_BIZ_LOG"], true)
logger("com.xstore.order.middleware.server.infrastructure.binlake.model", INFO, ["BINLOG_BIZ_LOG"], true)
logger("com.xstore.order.middleware.server.infrastructure.order.message.OrderMessageNotify", INFO, ["BINLOG_BIZ_LOG"], true)
logger("com.xstore.order.middleware.server.infrastructure.order.message.OrderUmsNotify", INFO, ["BINLOG_BIZ_LOG"], true)


root(DEBUG, ["CONSOLE", "FILE-ALL", "FILE-ERROR"])