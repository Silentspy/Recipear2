package mods.recipear;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

final class RecipearFormatter extends Formatter
{
    static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String format(LogRecord record)
    {
        StringBuilder msg = new StringBuilder();
        msg.append(this.dateFormat.format(Long.valueOf(record.getMillis())));
        Level lvl = record.getLevel();

        String name = lvl.getLocalizedName();
        if ( name == null )
        {
            name = lvl.getName();        	
        }

        if ( ( name != null ) && ( name.length() > 0 ) )
        {
            msg.append(" [" + name + "] ");
        }
        else
        {
            msg.append(" ");
        }
        msg.append(record.getMessage());
        msg.append(LINE_SEPARATOR);
        Throwable thr = record.getThrown();

        if (thr != null)
        {
            StringWriter thrDump = new StringWriter();
            thr.printStackTrace(new PrintWriter(thrDump));
            msg.append(thrDump.toString());
        }

        return msg.toString();
    }
}