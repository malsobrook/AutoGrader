package mutators;

import java.io.File;
import java.io.FileNotFoundException;

	// interface to follow for analyzers
public abstract interface Analyzer {
	
	public String analyze() throws Exception;
	public void aggregateFunc();
	public String keyFinder(String line);
	
}
