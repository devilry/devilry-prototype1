#!/usr/bin/env python
""" rst2latex.py produces latex with indented paragraphs. This script changes
this to vertical-space separated paragraphs. """

from os import linesep
from sys import argv

try:
	fn = argv[1]
except:
	raise SystemExit("usage: %s <latextfile>" % argv[0])

lines = open(fn, "rb").readlines()
lines.insert(17, r"\setlength{\parindent}{0.0in}" + linesep)
lines.insert(18, r"\setlength{\parskip}{0.1in}" + linesep)
open(fn, "wb").write("".join(lines))
