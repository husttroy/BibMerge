package edu.ucla.cs.bib.merge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class BibMerge {
	ArrayList<String> bibs;
	ArrayList<String> texs;
	HashMap<String, ArrayList<String>> labels; // title -> labels
	HashMap<String, String> articles; // label -> article
	HashMap<String, String> distinct_labels;
	
	public BibMerge(ArrayList<String> bibs, ArrayList<String> texs) {
		this.bibs = bibs;
		this.texs = texs;
		this.labels = new HashMap<String, ArrayList<String>>();
		this.articles = new HashMap<String, String>();
		this.distinct_labels = new HashMap<String, String>();
	}
	
	public void merge(String output) {
		load();
		
		// dump distinct articles to the output bib file
		String s = "";
		for(String title : labels.keySet()) {
			ArrayList<String> ls = labels.get(title);
			String article = "";
			String label = null;
			for(String l : ls) {
				if(l.equals("fowler:refactor99")) {
					System.out.println("yeh");
				}
				String content = this.articles.get(l);
				if(content.length() > article.length()) {
					// we prefer longer bib for the same article
					label = l;
					article = content;
				}
			}
			
			if(label != null) {
				s += article + System.lineSeparator();
				this.distinct_labels.put(title, label);
			}
		}
		
		FileUtils.writeStringToFile(s, output);
		
		// update the references in tex files
		for(String tex : texs) {
			String content = FileUtils.readFileToString(tex);
			for(String title : labels.keySet()) {
				ArrayList<String> ls = labels.get(title);
				String label = distinct_labels.get(title);
				if(ls.size() > 1) {
					// duplicated
					for(String l : ls) {
						if(!l.equals(label)) {
							if(l.equals("fowler:refactor99")) {
								System.out.println("yeh");
							}
							// replace the references to duplicated bib records
							// be careful when replacing them, because one ref can be a substring of another
							// just enumerate all cases
							content = content.replaceAll("\\{(\\s*)" + Pattern.quote(l) + "(\\s*),", "{" + label + ",");
							content = content.replaceAll("\\{(\\s*)" + Pattern.quote(l) + "(\\s*)\\}", "{" + label + "}");
							content = content.replaceAll(",(\\s*)" + Pattern.quote(l) + "(\\s*)\\}", "," + label + "}");
							content = content.replaceAll(",(\\s*)" + Pattern.quote(l) + "(\\s*),", "," + label + ",");
						}
					}
				}
			}
			
			// update the tex file
			FileUtils.writeStringToFile(content, tex);
		}
	}
	
	private void load() {
		for(String bib : bibs) {
			if(bib.equals("/home/troy/research/software_evolution_chapter/refs-wong.bib")) {
				System.out.println("yeh");
			}
			load(bib);
		}
	}
	
	private void load(String bib_path) {
		String s = "";
		try (BufferedReader br = new BufferedReader(new FileReader(new File(bib_path)))) {
			String line;
			String title = null;
			String label = null;
			while((line = br.readLine()) != null) {
				line = line.trim();
				if(line.startsWith("@") && line.endsWith(",")) {
					// the article starts here
					if(!s.isEmpty() && label != null && title != null) {
						// cache the previous article
						this.articles.put(label, s);
						ArrayList<String> ls;
						if(this.labels.containsKey(title)) {
							ls = this.labels.get(title);	
						} else {
							ls = new ArrayList<String>();
						}
						
						ls.add(label);
						this.labels.put(title, ls);
						
						// reset
						title = null;
						s = "";
					}
					
					label = line.substring(line.indexOf('{') + 1, line.indexOf(','));
				} else if (line.startsWith("title") || line.startsWith("Title")) {
					if(line.endsWith("},")) {
						title = line.substring(line.indexOf('{') + 1,  line.lastIndexOf("},")).toLowerCase();
					} else if (line.endsWith("\",")) {
						title = line.substring(line.indexOf('"') + 1,  line.lastIndexOf("\",")).toLowerCase();
					} else {
						// title spans multiple lines
						String line2;
						while((line2 = br.readLine()) != null) {
							line += " " + line2.trim();
							if(line.endsWith("},")) {
								title = line.substring(line.indexOf('{') + 1,  line.lastIndexOf("},")).toLowerCase();
								break;
							} else if (line.endsWith("\",")) {
								title = line.substring(line.indexOf('"') + 1,  line.lastIndexOf("\",")).toLowerCase();
								break;
							}
						}
					}
					
					if(title != null) {
						title = title.replaceAll("\\.", "");
						title = title.replaceAll("\\{", "");
						title = title.replaceAll("\\}", "");
					}
				}
				
				s += line + System.lineSeparator();
			}
			
			// process the last record
			if(!s.isEmpty() && label != null && title != null) {
				this.articles.put(label, s);
				ArrayList<String> ls;
				if(this.labels.containsKey(title)) {
					ls = this.labels.get(title);	
				} else {
					ls = new ArrayList<String>();
				}
				
				ls.add(label);
				this.labels.put(title, ls);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		
		ArrayList<String> bibs = new ArrayList<String>();
		bibs.add("/home/troy/research/software_evolution_chapter/chime.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/everton.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/faultracer.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/kim.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/kimrefactor.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/kimthesis.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/libsync.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/libsync2.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/mengna.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/miryung.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/reference.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/refs-kim.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/refs-wong.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/repair.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/spa.bib");
		bibs.add("/home/troy/research/software_evolution_chapter/tianyi.bib");
		
		ArrayList<String> texs = new ArrayList<String>();
		texs.add("/home/troy/research/software_evolution_chapter/bookchapter.tex");
		
		BibMerge bm = new BibMerge(bibs, texs);
		bm.merge("/home/troy/research/software_evolution_chapter/chapter.bib");
	}
}
