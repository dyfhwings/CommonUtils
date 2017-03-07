package com.dyfh.log;

import org.apache.log4j.Logger;

/**
 *
 * @author tanjx
 *
 */
public class CommonLog
{

    private static Logger log = Logger.getLogger("commonlog");

    public static synchronized void error(Object message, Throwable e)
    {
        log.error(message, e);
    }

    public static synchronized void warn(Object message)
    {
        log.warn(message);
    }

    public static synchronized void info(Object message)
    {
        log.info(message);
    }

    public static synchronized void debug(Object message)
    {
        log.debug(message);
    }
}
