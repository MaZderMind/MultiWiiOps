package de.mazdermind.MultiWiiOps;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class OneLineLogger extends Formatter {

	@Override
	public String format(LogRecord record) {
		return record.getSourceClassName()+":"+record.getSourceMethodName()+"() - "+record.getMessage()+"\n";
	}

}
