SRC=prosjektbeskrivelse-devilry


html:
	rst2html.py $(SRC).txt $(SRC).html


# PDF
latex:
	rst2latex.py --documentoptions "11pt,a4paper" $(SRC).txt $(SRC).tex
fixlatex: latex
	python rst_fixlatex.py $(SRC).tex
pdf: fixlatex
	texi2pdf $(SRC).tex
	$(MAKE) cleanlatex


cleanlatex:
	@echo "Removing temporary latex files."
	rm -f *.out *.log *.tex *.aux
clean:
	rm -f *.html *.pdf
