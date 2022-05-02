package mutators;

	// interface to follow for analyzers
public abstract interface Analyzer {
	
	public String analyze() throws Exception;
	public void aggregateFunc();
	public String keyFinder(String line);
	
}
