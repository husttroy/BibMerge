package edu.ucla.cs.bib.merge;

import java.util.ArrayList;

public class UpdateBookChapter {
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
