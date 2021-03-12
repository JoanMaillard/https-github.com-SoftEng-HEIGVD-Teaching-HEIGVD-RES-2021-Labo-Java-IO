package ch.heigvd.res.labio.impl.filters;

import ch.heigvd.res.labio.impl.Utils;
import jdk.jshell.execution.Util;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    int count = 1;
    String lines[] = Utils.getNextLine(str);
    while (lines[0] != "") {
      out.write(count +"\t" + lines[0]);
      count += 1;
      lines = Utils.getNextLine(lines[1]);
    }
    if (lines[1] != "") {
      out.write(count + "\t" + lines[1]);
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    int count = 1;
    String lines[] = Utils.getNextLine(cbuf.toString());
    while (lines[0] != "") {
      out.write(count + "\t" + lines[0]);
      count += 1;
      lines = Utils.getNextLine(lines[1]);
    }
    if (lines[1] != "") {
      out.write(count + "\t" + lines[1]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    throw new UnsupportedOperationException("The student has not implemented this method yet.");
  }


}
