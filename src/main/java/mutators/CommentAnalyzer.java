package mutators;

import java.io.File;

public class CommentAnalyzer implements Analyzer{

	private String filepath;
	
	public CommentAnalyzer(String filepath) {
		this.filepath = filepath;
	}
	
	@Override
	public String analyze(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggregateFunc() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String keyFinder(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
