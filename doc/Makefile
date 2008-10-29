SRC=prosjektbeskrivelse-devilry


RST2LATEX=`which rst2latex 2>/dev/null || which rst2latex.py`


help:
	@echo "html: Generate html"
	@echo "pdf:  Generate pdf via latex"

html:
	rst2html.py $(SRC).txt $(SRC).html


# PDF
latex:
	$(RST2LATEX) --documentoptions "11pt,a4paper" $(SRC).txt $(SRC).tex
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
