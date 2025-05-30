<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="LogLevel: %-5p | Date: %d{yyyy-MM-dd HH:mm:ss} | From: %-30c{1} | Message: %m%n" />
        </Console>

        <RollingFile name="ErrorLogFiles" fileName="./logs/error.log" filePattern="./logs/$${date:yyyy-MM}/error/%d{yyyyMMdd}-error.log.gz">
            <LevelRangeFilter minLevel="ERROR" maxLevel="ERROR" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout>
                <pattern>LogLevel: %-5p | Date: %d{yyyy-MM-dd HH:mm:ss} | From: %-30c{1} | Message: %m%n</pattern>
            </PatternLayout>
            <Policies>
                <OnStartupTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10 MB" />
                <TimeBasedTriggeringPolicy />
            </Policies>
        </RollingFile>
        <RollingFile name="WarnLogFiles" fileName="./logs/warn.log" filePattern="./logs/$${date:yyyy-MM}/warn/%d{yyyyMMdd}-warn.log.gz">
            <LevelRangeFilter minLevel="WARN" maxLevel="WARN" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout>
                <pattern>LogLevel: %-5p | Date: %d{yyyy-MM-dd HH:mm:ss} | From: %-30c{1} | Message: %m%n</pattern>
            </PatternLayout>
            <Policies>
                <OnStartupTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10 MB" />
                <TimeBasedTriggeringPolicy />
            </Policies>
        </RollingFile>
        <RollingFile name="InfoLogFiles" fileName="./logs/info.log" filePattern="./logs/$${date:yyyy-MM}/info/%d{yyyyMMdd}-info.log.gz">
            <LevelRangeFilter minLevel="INFO" maxLevel="INFO" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout>
                <pattern>LogLevel: %-5p | Date: %d{yyyy-MM-dd HH:mm:ss} | From: %-30c{1} | Message: %m%n</pattern>
            </PatternLayout>
            <Policies>
                <OnStartupTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10 MB" />
                <TimeBasedTriggeringPolicy />
            </Policies>
        </RollingFile>
        <RollingFile name="DebugLogFiles" fileName="./logs/debug.log" filePattern="./logs/$${date:yyyy-MM}/debug/%d{yyyyMMdd}-debug.log.gz">
            <LevelRangeFilter minLevel="DEBUG" maxLevel="DEBUG" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout>
                <pattern>LogLevel: %-5p | Date: %d{yyyy-MM-dd HH:mm:ss} | From: %-30c{1} | Message: %m%n</pattern>
            </PatternLayout>
            <Policies>
                <OnStartupTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10 MB" />
                <TimeBasedTriggeringPolicy />
            </Policies>
        </RollingFile>
        <RollingFile name="TraceLogFiles" fileName="./logs/trace.log" filePattern="./logs/$${date:yyyy-MM}/trace/%d{yyyyMMdd}-trace.log.gz">
            <LevelRangeFilter minLevel="TRACE" maxLevel="TRACE" onMatch="ACCEPT" onMismatch="DENY" />
            <PatternLayout>
                <pattern>LogLevel: %-5p | Date: %d{yyyy-MM-dd HH:mm:ss} | From: %-30c{1} | Message: %m%n</pattern>
            </PatternLayout>
            <Policies>
                <OnStartupTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10 MB" />
                <TimeBasedTriggeringPolicy />
            </Policies>
        </RollingFile>
        <RollingFile name="CompleteLogFiles" fileName="./logs/complete.log" filePattern="./logs/$${date:yyyy-MM}/complete/%d{yyyyMMdd}-complete.log.gz">
            <PatternLayout>
                <pattern>LogLevel: %-5p | Date: %d{yyyy-MM-dd HH:mm:ss} | From: %-30c{1} | Message: %m%n</pattern>
            </PatternLayout>
            <Policies>
                <OnStartupTriggeringPolicy />
                <SizeBasedTriggeringPolicy size="10 MB" />
                <TimeBasedTriggeringPolicy />
            </Policies>
        </RollingFile>
    </Appenders>

    <Loggers>
        <Root>
            <AppenderRef ref="ErrorLogFiles" />
            <AppenderRef ref="WarnLogFiles" />
            <AppenderRef ref="InfoLogFiles" />
            <AppenderRef ref="DebugLogFiles" />
            <AppenderRef ref="TraceLogFiles" />
            <AppenderRef ref="CompleteLogFiles" level="trace" />
            <AppenderRef ref="Console" level="info" />
        </Root>

        <Logger name="com.paymybuddy" level="trace"></Logger>
    </Loggers>
</Configuration>
