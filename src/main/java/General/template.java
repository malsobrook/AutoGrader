package General;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class template {

		private String f;
		private LocalDate date;
		private Reporter repo;
		private List<String> notes = new ArrayList<String>();
		private List<String> summaryFields = new ArrayList<String>();
		
		public template(String fileName, Reporter repo) {
			this.f = fileName;
			this.repo = repo;
		}
		
		public void GenerateReport() {
			String str = this.GenerateHeader();
			str += this.GenerateSummary();
			if(this.notes.size() > 0) {
				str += this.GenerateNotes();
			}
			str += this.GenerateFooter();
			
			try {
		      FileWriter myWriter = new FileWriter(this.f + date.now().toString() + ".html");
		      myWriter.write(str);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	        
		}
		
			// generate a html document that is the final report. name of the document will be the original document name + "report".
		private String GenerateHeader() {
			String str = 
			"<!DOCTYPE html>\r\n"
			+ "<html lang=\"en\">\r\n"
			+ "<meta charset=\"UTF-8\">\r\n"
			+ "<title>AutoGrader Results</title>\r\n"
			+ "\r\n"
			+ "<style>\r\n"
			+ "    .main {\r\n"
			+ "        max-width: 75%;\r\n"
			+ "        margin: auto;\r\n"
			+ "    }\r\n"
			+ "    h1 {\r\n"
			+ "        text-align: center;\r\n"
			+ "        margin: 0;\r\n"
			+ "    }\r\n"
			+ "    .reportInfo {\r\n"
			+ "        display: flex; \r\n"
			+ "        justify-content: space-between;\r\n"
			+ "        font-size: 18px;\r\n"
			+ "    }\r\n"
			+ "</style>\r\n"
			+ "\r\n"
			+ "<body>\r\n"
			+ "\r\n"
			+ "<div class=\"main\">\r\n"
			+ "    <h1>AutoGrader Results</h1>\r\n"
			+ "    <div class=\"reportInfo\">\r\n"
			+ "        <p style=\"padding-left: 25%\">"+this.f+"</p>\r\n"
			+ "        <p style=\"padding-right: 25%\">"+date.now()+"</p>\r\n"
			+ "    </div>";
			return str;
		}
		
		private String GenerateSummary() {
			String str = 
			"<div class=\"summaryField\">\r\n"
			+ "            <hr><h2>Summary</h2><hr>\r\n";

			//There are two options in which we can add fields to the summary section (1/2)
			//str += AddIndentationField();
			
			//There are two options in which we can add fields to the summary section (2/2)
			for (String entry : summaryFields) {
				str += entry;
			}
			
			str += "</div>";
			return str;
		}
		
		private String GenerateNotes() {
			String str = 
			"<div class=\"notesField\">\r\n"
			+ "            <hr><h2>Notes</h2><hr>\r\n"
			+ "            <ul>\r\n";
			
			for (String note : this.notes) {
				str += "<li>" + note + "</li>\r\n";
			}
			
			str += 
			"            </ul>\r\n"
			+ "        </div>";
			return str;
		}
		
		private String GenerateFooter() {
			String str = 
			"</div>\r\n"
			+ "\r\n"
			+ "</body>\r\n"
			+ "</html>";
			return str;
		}
		
		public void AddNote(String note) {
			notes.add(note);
		}
		
		//There are two options in which we can add fields to the summary section (1/2)
		//Non-generic and mostly hard-coded
		public String AddIndentationField() {
			String str = 
			"<h3>Indentation Consistency: %" + repo.getMajorityIdt() + "</h3>\r\n"
			+ "<p>&emsp;Majority Style:       %" + repo.getIdtStyle() + "</p>"
			+ "<p>&emsp;% Match with choice:  %" + repo.getIdtMatch() + "</p>"
			+ "<p>&emsp;% Correctness:		  %" + repo.getIdtCorrect() + "</p>";
			return str;
		}
		//There are two options in which we can add fields to the summary section (2/2)
		//Generic and less to hard-code in the report.
		public void AddSummaryLine(String parameterName, Map<String, ?> map) {
			String str = 
			"<h3>"+ parameterName +"</h3>\r\n"
			+"<table>";
			
			for (Map.Entry<String, ?> entry : map.entrySet()) {
				str +=
				"<tr>\r\n"
				+ "<td>" + entry.getKey() + "</td>"
				+ "<td>" + entry.getValue() + "</td>"
				+ "</tr>";
			}
			
			str += "</table>";
			this.summaryFields.add(str);
		}
}
