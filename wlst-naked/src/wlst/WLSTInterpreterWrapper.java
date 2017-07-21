package wlst;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import weblogic.management.scripting.utils.WLSTInterpreter;

public class WLSTInterpreterWrapper extends WLSTInterpreter {
	// For interpreter stdErr and stdOut
	private ByteArrayOutputStream baosErr = new ByteArrayOutputStream();
	private ByteArrayOutputStream baosOut = new ByteArrayOutputStream();
	private PrintStream stdErr = new PrintStream(baosErr);
	private PrintStream stdOut = new PrintStream(baosOut);

	// For redirecting JVM stderr/stdout when calling dumpStack()
	static PrintStream errSaveStream = System.err;
	static PrintStream outSaveStream = System.out;

	public WLSTInterpreterWrapper() {
		setErr(stdErr);
		setOut(stdOut);
	}

	// Wrapper function for the WLSTInterpreter.exec()
	// This will throw an Exception if a failure or exception occurs in
	// The WLST command or if the response contains the dumpStack() command
	public String exec1(String command) throws Exception {
		String output = null;
		try {
			output = exec2(command);
		} catch (Exception e) {
			throw e;
//			try {
//				synchronized (this) {
//					stdErr.flush();
//					baosErr.reset();
//					e.printStackTrace(stdErr);
//					output = baosErr.toString();
//					baosErr.reset();
//				}
//			} catch (Exception ex) {
//				output = null;
//			}

//			if (output == null) {
//				throw new WLSTException(e);
//			}
//
//			if (!output.contains(" dumpStack() ")) {
//				// A real exception any way
//				throw new WLSTException(output);
//			}
		}

		if (output.length() != 0) {
			if (output.contains(" dumpStack() ")) {
				// redirect the JVM stderr for the duration of this next call
				synchronized (this) {
					System.setErr(stdErr);
					System.setOut(stdOut);
					String _return = exec2("dumpStack()");
					System.setErr(errSaveStream);
					System.setOut(outSaveStream);
					throw new WLSTException(_return);
				}
			}
		}

		System.out.println(stripCRLF(output));
		return stripCRLF(output);
	}

	private String exec2(String command) {
		// Call down to the interpreter exec method
		exec(command);
		String err = baosErr.toString();
		String out = baosOut.toString();

		if (err.length() == 0 && out.length() == 0) {
			return "";
		}

		baosErr.reset();
		baosOut.reset();

		StringBuffer buf = new StringBuffer("");

		if (err.length() != 0) {
			buf.append(err);
		}

		if (out.length() != 0) {
			buf.append(out);
		}

		return buf.toString();
	}

	// Utility to remove the end of line sequences from the result if any.
	// Many of the response are terminated with either \r or \n or both and
	// some responses can contain more than one of them i.e. \n\r\n
	private String stripCRLF(String line) {
		if (line == null || line.length() == 0) {
			return line;
		}

		int offset = line.length();

		while (true && offset > 0) {
			char c = line.charAt(offset - 1);
			// Check other EOL terminators here

			if (c == '\r' || c == '\n') {
				offset--;
			} else {
				break;
			}
		}

		return line.substring(0, offset);
	}
}