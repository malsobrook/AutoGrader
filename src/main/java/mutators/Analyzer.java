package mutators;

import java.io.File;

	// interface to follow for analyzers
public abstract interface Analyzer {
	
	public String analyze(File file);
	public void aggregateFunc();
	public String keyFinder(String line);
	
}
