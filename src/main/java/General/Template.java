package General;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Gui.UserSettings;

public class Template {

		private String fileName;
		private List<String> notes = new ArrayList<String>();
		private String header = 
		"<!DOCTYPE html>"
		+ "<html lang=\"en\">"
		+ "<meta charset=\"UTF-8\">"
		+ "<title>AutoGrader Results</title>"
		+ "<style>"
		+ "    .main {"
		+ "        max-width: 75%;"
		+ "        margin: auto;"
		+ "    }"
		+ "    h1 {"
		+ "        text-align: center;"
		+ "        margin: 0;"
		+ "    }"
		+ "    .reportInfo {"
		+ "        display: flex;"
		+ "        justify-content: space-between;"
		+ "        font-size: 18px;"
		+ "    }"
		+ "</style>"
		+ "<body>"
		+ "<div class=\"main\">"
		+ "    <h1>AutoGrader Results</h1>";
		private String indentationField;
		private String bracketField;
		private String miscField;
		private final String footer = 
		"</div>"
		+ "</body>"
		+ "</html>";
		
		public Template(String fileName) {
			this.fileName = fileName;
			this.header += "    <div class=\"reportInfo\">"
						+ "        <p style=\"padding-left: 25%\">"+this.fileName+"</p>"
						+ "        <p style=\"padding-right: 25%\">"+LocalDate.now()+"</p>"
						+ "    </div>";
		}
		
		public void GenerateReport() {
			StringBuilder reportStringBuilder = new StringBuilder();
			reportStringBuilder.append(this.header);
			reportStringBuilder.append(this.GenerateSummary());
			reportStringBuilder.append(this.notes.size() > 0 ? this.GenerateNotes() : "");
			reportStringBuilder.append(this.footer);
			
			try {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
				FileWriter writer;
				
				String writerName = "AG_" + this.fileName.substring(0, this.fileName.lastIndexOf('.')) + "_" + dtf.format(LocalDate.now()) + ".html";
				String reportDirectory = "";
				
				if(UserSettings.getInstance().getReportDirectory() != null && 
						!UserSettings.getInstance().getReportDirectory().equals("")) {
					reportDirectory = UserSettings.getInstance().getReportDirectory();
				}
				
				writer = new FileWriter(reportDirectory + writerName);
			    writer.write(reportStringBuilder.toString());
			    writer.close();
		    } catch (IOException e) {
		    	System.out.println("An error occurred.");
		        e.printStackTrace();
		    }
		}
		
		public void AddNote(String note) {
			notes.add(note);
		}
		
		public void AddIndentationField(double indentationScore, double indentConsistency, String indentationStyle, double choiceConsistency, double indentCorrectness) {
			this.indentationField = "<h3>Indentation Score: " + indentationScore + "%</h3>"
									+ "<table>"
									+ "<tr><td>&emsp;Indentation Consistency:</td><td>" + indentConsistency + "%</td></tr>"
									+ "<tr><td>&emsp;Indentation Style Chosen:</td><td>" + indentationStyle + "</td></tr>"
									+ "<tr><td>&emsp;Consistent with Choice:</td><td>" + choiceConsistency + "%</td></tr>"
									+ "<tr><td>&emsp;Indentation Correctness:</td><td>" + indentCorrectness + "%</td></tr>"
									+ "</table>";
		}
		
		public void AddBracketField(double bracketScore, double bracketConsistency, String bracketStyle, double choiceConsistency, double bracketCorrectness) {
			this.bracketField = "<h3>Bracket Score: " + bracketScore + "%</h3>"
								+ "<table>"
								+ "<tr><td>&emsp;Bracket Consistency:</td><td>" + bracketConsistency + "%</td></tr>"
								+ "<tr><td>&emsp;Bracket Style Chosen:</td><td>" + bracketStyle + "</td></tr>"
								+ "<tr><td>&emsp;Consistent with Choice:</td><td>" + choiceConsistency + "%</td></tr>"
								+ "<tr><td>&emsp;Bracket Correctness:</td><td>" + bracketCorrectness + "%</td></tr>"
								+ "</table>";
		}
		
		public void AddMiscField(double miscScore, boolean importsAtTop, boolean commentAtTop) {
			this.miscField = "<h3>Miscellaneous Score: " + miscScore + "%</h3>"
							+ "<table>"
							+ "<tr><td>&emsp;All Import At Top:</td><td>" + (importsAtTop ? "Y" : "N") + "</td></tr>"
							+ "<tr><td>&emsp;Comment Block At Top:</td><td>" + (commentAtTop ? "Y" : "N") + "</td></tr>"
							+ "</table>";
		}
		
		private String GenerateSummary() {
			StringBuilder summaryStringBuilder = new StringBuilder();
			summaryStringBuilder.append("<div class=\"summaryField\">"
										+ "<hr><h2>Summary</h2><hr>");
			
			summaryStringBuilder.append(this.indentationField == null ? "" : this.indentationField);
			summaryStringBuilder.append(this.bracketField == null ? "" : this.bracketField);
			summaryStringBuilder.append(this.miscField == null ? "" : this.miscField);
			
			summaryStringBuilder.append("</div>");
			return summaryStringBuilder.toString();
		}
		
		private String GenerateNotes() {
			StringBuilder noteStringBuilder = new StringBuilder();
			noteStringBuilder.append("<div class=\"notesField\">"
									+ "<hr><h2>Notes</h2><hr>"
									+ "<ul>");
			
			for (String note : this.notes) {
				noteStringBuilder.append("<li>" + note + "</li>");
			}
			
			noteStringBuilder.append("</ul>"
									+ "</div>");
			return noteStringBuilder.toString();
		}
}
