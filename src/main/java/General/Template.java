package General;

import java.time.LocalDate;

public class Template {

		private String f;
		private LocalDate date;
		private Reporter repo;
		
		public Template(String fileName, Reporter repo) {
		this.f = fileName;
		this.repo = repo;
		}
		
		
			// generate a txt document that is the final report. name of the document will be the original document name + "report".
		public String generateSheet() {
			String str = 
			"*********************************************************\n" +
			"                    Autograder Results	                  \n" +
			"         " + f +   "                   " + date.now() + "\n" +
			"*********************************************************\n" +
			"***********************Summary***************************\n" +
			"\n" +
			"\n" +
			"Indentation Consistency: %" + repo.getMajorityIdt() + "\n" +
			"	Majority Style:       %" + repo.getIdtStyle() + "\n" +
			"	% Match with choice:  %" + repo.getIdtMatch() + "\n" +
			"	% Correctness:		  %" + repo.getIdtCorrect() + "\n" +
			"\n" +
			"=========================================================\n" +
			"\n" +
			"Bracket Consistency:    " +
			"	Majority Style:      " +
			"	% Match with choice: " ;
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			return str;
		}
}
